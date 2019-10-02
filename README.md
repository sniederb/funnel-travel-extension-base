# Guide to writing a funnel.travel extension

Last updated: Sep 15th, 2019

## Minimal requirements

The extension will run in the JVM context of the funnel.travel server using JRE 8. The minimum dependency is to add

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
  "URL": "http://www.want.ch/emailextension.html",
  "Permissions": [
  	{
  	  "Socket": "my.datahub.com"
  	}
  ]
}
```

Mandatory properties are 'Implementation', 'Name', 'Author', 'Version', and 'Description'. If the optional 'URL' is provided, 
a link to the URL will be shown to the user.

(The logo URL is _not_ provided by the JSON file, but by the interface implementation. The reason is that many implementations
will provide the logo as part of the JAR, ie. implementations will use Class#getResource(String).)

The extension must declare its interaction with the "outside" world through the `permissions` array. These will be listed on
the UI. In the above example, the extension will exchange data with the `my.datahub.com` server. 

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

### Execution environment
The provided settings will always contain some environment-specific keys:
    * The key `SettingItem.KEY_SERVERCONTEXT` will hold the server-path, eg. `/be/prod`. 
    * The key `SettingItem.KEY_FILESTORE` holds a string pointing to the server location for storing files. If the extension implementation creates files, it must do so using `SettingItem.KEY_FILESTORE`. A few files can be stored directly at that location; if the extension produces more than 10 files, it should create a subdirectory.
    * The key `SettingItem.KEY_ISPRODUCTION` holds a Boolean indicating whether the current context is production or not

## Types of extensions

### TripDataProducer

A 'producer' extension create travel data to be stored in funnel.travel. It takes only the extension settings and retrieves
travel data from an external source. The call sequence has two phases:

```java
List<byte[]> getRawSources(Map<String, Object> settings, Locale locale);
```
.. retrieves raw sources, and passes these to funnel.travel. The raw source will be stored together with the trip object.

```java
Booking convertRawSourceToTripData(byte[] rawSource, Map<String, Object> settings, Locale locale);
```
... converts a single raw source to a funnel.travel booking object.

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
Booking convertRawSourceToTripData(byte[] rawSource, Map<String, Object> settings, Locale locale);
```

to convert the payload to a booking object structure.

**Beware** that with a two-phased producer, there is a time-period during which the original payload has been retrieved, but the second phase has not been executed. The extension implementation must ensure that if triggered again, it will not _again_ retrieve the original payload.   

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
Optional<Booking> modify(Booking bookingData, Map<String, Object> settings, Locale locale);
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

## Date/time handling

* Timestamps which denote a server-event (such as 'last login' or a changelog event) are serialized in UTC using the ISO 8601 format, e.g `2019-04-15T13:09:00.710Z`.
* Dates are serialized in XML format, eg. `2019-04-19`. This is mainly relevant for departure / arrival dates
* Date/Times for departures / arrivals of travel segments (flight, train etc) are serialized in ISO 8601 format **but without a timezone**, eg. `2019-04-20T04:56:00`. The time here is always interpreted as local to the departing / arriving destination. If an extension sends (or modifies) trip data, it must ensure that the timestamp is delivered either **without timezone**, or that the time is adjusted accordingly. 


## Library dependencies

The extension will run in the JVM context of the funnel.travel, and as such will have available the runtime libraries provided by funnel.travel.

* slf4j-api, version1.7.25
** Use jcl-over-slf4j for an implementation in tests
* ch.qos.logback, version1.1.11
* com.google.code.gson, version2.8.2
* com.googlecode.json-simple, version1.1.1
* commons-beanutils, version1.9.3
* commons-collections, version3.2.2
* commons-codec, version1.10
* commons-io, version 2.6
* javax.mail, version1.5.6
* com.sun.mail, version1.5.6
* org.apache.httpcomponents (httpclient), version4.5.5
* org.apache.httpcomponents (httpcore), version4.4.9
* org.apache.httpcomponents (httpmime), version4.5.5
* com.fasterxml.jackson.core (jackson-core), version2.8.11
* com.fasterxml.jackson.core (jackson-dataformat-csv), version2.8.11
* com.fasterxml.jackson.core (jackson-dataformat-xml), version2.8.11
* com.fasterxml.jackson.core (jackson-dataformat-yaml), version2.8.11
* org.freemarker, version2.3.28
* ch.want.funnel (extension-util), version1.0.0

