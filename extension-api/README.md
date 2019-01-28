# Guide to writing a funnel.travel extension

Last updated: Jan 17th, 2019

## Minimal requirements

The extension will run in the JVM context of the funnel.travel server using JRE 8. The minimum dependency is to

```xml
<dependency>
	<groupId>ch.want.funnel</groupId>
	<artifactId>extension-api</artifactId>
	<version>1.0.0</version>
</dependency>
```

The extension must:
* implement the `FunnelExtension` interface
* provide a file named `funnel-extension.json` (content see below)
* implement at least one of the following interfaces:
    * TripDataProducer
    * TripDataModifier
    * TripDataConsumer
    * TripDataWebhook

### funnel-extension.json

The `funnel-extension.json` file tells the funnel.travel server about the extension.

```json
{
  "Implementation": "ch.want.funnel.extension.email.EmailExtension",
  "Name": "Confirmation E-mails",
  "Author": "WaNT GmbH",
  "Version": "1.0.0",
  "Description": "Read confirmation e-mails from a POP3/IMAP e-mail server."
  "URL": "http://www.want.ch/emailextension.html"
}
```

Mandatory properties are 'Implementation', 'Name', 'Author', 'Version', and 'Description'. If the optional 'URL' is provided, 
a link to the URL will be shown to the user.

(The logo URL is _not_ provided by the JSON file, but by the interface implementation. The reason is that many implementations
will provide the logo as part of the JAR, ie. implementations will use Class#getResource(String).)

## Settings

By implementing the `FunnelExtension` interface, the extension must provide some methods related to settings:

```java
List<SettingItem> getSettings();
void validateSettings(Map<String, Object> settingValues, Locale locale) throws IllegalSettingException;
String getLabelForKey(String settingItemKey, Locale locale);
```

This mechanism allows for the extension to tell funnel.travel which settings are needed to operate. The `validateSettings`
implementation must ensure that all mandatory settings are provided and valid.

When calling produce / modify / consume, the provided map is subsequently scanned for changed 'INTERNAL' values. These are
then saved back the to account extension settings. In this way, an extension can maintain a account-wide state between
executions. 

## Types of extensions

### TripDataProducer

A 'producer' extension create travel data to be stored in funnel.travel. It takes only the extension settings and retrieves
travel data from an external source. The call sequence has two phases:

```java
List<byte[]> getRawSources(Map<String, Object> settings, Locale locale);
```
.. retrieves raw sources, and passes these to funnel.travel. The raw source will be stored together with the trip object.

```java
String convertRawSourceToTripData(byte[] rawSource, Map<String, Object> settings, Locale locale);
```
... converts a single raw source to a funnel.travel trip object.

This interface is suitable for pull systems such as retrieving e-mails.

### TripDataTwoPhasedProducer

Some 'producer' mechanisms works such that in a first step, the extension triggers data production asynchronously. Once ready,
the data is then delivered via webhook. The interface `TripDataTwoPhasedProducer` supports this process by mandating
that the producer's 

```java
List<byte[]> getRawSources(Map<String, Object> settings, Locale locale);
````

does *not* produce full raw sources, but simply a list of unique, external IDs (returned as String.getBytes(UTF-8)). 
These will be stored by funnel.travel in a "pending" state. As webhook, the external system must then call

```
https://www.funnel.travel/p/publicapi/extension/webhook/glb/<classname of the extension>
```

Subsequently, the extension is called first to extract the unique, external ID from the payload, and then called 

```java
String convertRawSourceToTripData(byte[] rawSource, Map<String, Object> settings, Locale locale);
```

to convert the payload to a trip object structure.

### TripDataModifier

A 'modifier' extension alters travel data. This can mean augmenting existing booking data with eg. flight statistics or more
detailed hotel information, but it can also entirely change the booking structure by eg. rebooking a flight.

This interface is suitable for modifications based on timed events or as reaction to trip events.

#### Internal execution management

User settings along with funnel.travel interna define which extensions get called when. The scenario of an "endless modification loop",
however, needs to be taken into account when implementing a `TripDataModifier`.

A `TripDataModifier` might get called whenever booking data has changed. If there are multiple such extensions, they might trigger
calling each other in an endless loop. The implementation must therefore verify if a modification is necessary and taking place,
and return an empty Optional if no modification has occurred.

```java
Optional<String> modify(String tripData, Map<String, Object> settings, Locale locale);
```

_*TODO*_ describe mechanism to store internal data in settings! 


### TripDataConsumer

A 'consumer' extension only receives travel data, without any feedback to funnel.travel. Typical examples are reporting or data feeds
to accounting systems.

### TripDataWebhook

A 'webhook' extension is typically either a producer or modifier where execution is triggered by a third-party system.

This interface is suitable for push systems such as notification calls from booking systems.

## The Booking#extensionData field

Each Booking object holds a `extensionData` JSON field, which extensions can use in varying ways. The root node holds a key
per extension classname. *Data from other extensions will be available to all extensions, so do not store sensitive data here*. 

### Additional data for extension

The data structure passed to funnel.travel in the `extensionData` field will be passed back to the extension unchanged. Thus an extension can hold 
additional state in this field, eg: 

```json
{
	"ch.want.funnel.extension.email.EmailExtension": {
	    "lastExecution": "20190121T14:55:23Z",
	    "lastGeneratedId": 662323667
    }
}
```

### Additional data for user

An extension can add data to be presented to the user by adding a `ui` node with a predefined structure:

```json
{
	"ch.want.funnel.extension.email.EmailExtension": {
	    "lastExecution": "20190121T14:55:23Z",
	    "lastGeneratedId": 662323667,
	    "ui": [
	    	{
	    		"contextType": "SEGMENT",
	    		"contextUuid": "c8ec4565-d7d1-4c48-8ff7-a5eac3a5778f",
	    		"labelKey": "myextension.label1",
	    		"valueType": "STRING",
	    		"value": "This segment was verified by Foobar"
	    	}
	    ]
	}
}

```

The following rules apply:

* contextType must be one of the following: "BOOKING" | "SERVICE" | "SEGMENT"
* contextUuid must be derived by the extension from the provided trip data structure.
* labelKey will eventually be sent back to the extension in the `getLabelForKey` method for translation
* valueType must be one of the following: "STRING", "DATE", "INT", "BOOLEAN"

Beware that extensions must not provide some sort of execution status as additional UI field, as that status is already displayed natively by funnel.travel. 

## Library dependencies

The extension will run in the JVM context of the funnel.travel, and as such will have available the runtime libraries provided by funnel.travel.

* ch.qos.logback, Version 1.1.11
* com.google.code.gson, Version 2.8.2
* com.googlecode.json-simple, Version 1.1.1
* commons-beanutils, Version 1.9.3
* commons-collections, Version 3.2.2
* commons-codec, Version 1.10
* javax.mail, Version 1.5.6
* com.sun.mail, Version 1.5.6
* org.apache.httpcomponents (httpclient), Version 4.5.5
* org.apache.httpcomponents (httpcore), Version 4.4.9
* org.apache.httpcomponents (httpmime), Version 4.5.5
* com.fasterxml.jackson.core (jackson-core), Version 2.8.11
* com.fasterxml.jackson.core (jackson-dataformat-csv), Version 2.8.11
* com.fasterxml.jackson.core (jackson-dataformat-xml), Version 2.8.11
* com.fasterxml.jackson.core (jackson-dataformat-yaml), Version 2.8.11
* org.freemarker, Version 2.3.28
* ch.want.funnel (extension-util), Version 1.0.0

