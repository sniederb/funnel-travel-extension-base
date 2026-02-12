# Guide to writing a funnel.travel extension

Last updated: Feb 12th, 2026

## Minimal requirements

The extension will run in the JVM context of the funnel.travel server using JRE 8. The minimum dependency is to add

```xml
<dependency>
	<groupId>ch.want.funnel</groupId>
	<artifactId>extension-api</artifactId>
	<version>1.0.6</version>
</dependency>
```

The extension must:
- implement the `FunnelExtension` interface
- provide a file named `funnel-extension.json` (content see below)
- implement at least one of the following interfaces:
    - TripDataProducer
    - TripDataModifier
    - TripDataConsumer
    - TripDataWebhook

### funnel-extension.json

The `funnel-extension.json` file tells the funnel.travel server about the extension.

```json
{
  "Implementation": "ch.want.funnel.extension.email.EmailExtension",
  "Name": "Confirmation E-mails",
  "Author": "WaNT GmbH",
  "BookingSource": "Domain of booking platform",
  "CustomerSystem": "System name under which customer references are used",
  "Version": "1.0.0",
  "Description": "Read confirmation e-mails from a POP3/IMAP e-mail server.",
  "URL": "http://www.want.ch/emailextension.html",
  "PrivacyPolicyURL": "http://www.want.ch/privacy.html",
  "Permissions": [
  	{
  	  "Socket": "my.datahub.com"
  	}
  ]
}
```

Mandatory properties are 'Implementation', 'Name', 'Author', 'Version', 'Description' and 'PrivacyPolicyURL'. If the optional 'URL' is provided, 
a link to the URL will be shown to the user. **Producer** extensions are recommended to supply 'System' (was: 'BookingSource'); if this is not a constant value, then
describe what sources the extension will provide.

(The logo URL is _not_ provided by the JSON file, but by the interface implementation. The reason is that many implementations
will provide the logo as part of the JAR, ie. implementations will use Class#getResource(String).)

The extension must declare its interaction with the "outside" world through the `permissions` array. These will be listed on
the UI. In the above example, the extension will exchange data with the `my.datahub.com` server. 

## Presentation of the extension in funnel.travel

Each extension is presented in a standard layout.

![Presentation of the ACME extension](https://www.funnel.travel/be/prod/doc/screenshots/extension-presentation.png)

- "Test Producer" is taken from the "Name" in `funnel-extension.json`
- "WaNT GmbH" is taken from the "Author" in `funnel-extension.json`
- "1.0.0" is taken from the "Version" in `funnel-extension.json`
- "Produce test trips from pre-defined JSON files. Only used in testing." is taken from the "Description" in `funnel-extension.json`
- "More" points to the link defined by "URL" in `funnel-extension.json`
- The logo is read by FunnelExtension#getLogoUrl(), and rendered with `max-width: 70px; max-height: 70px;`


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

### Producer (converter) extensions
"Privacy by design" asks to reduce the data scope where possible. In order to accommodate that principal, implementations of `TripRawDataConverter` **must** provide a small set of settings are conveniently pre-defined in the `MinimalConverterSettings` class.  


### Execution environment
The provided settings will always contain some environment-specific keys:

- The key `SettingItem.KEY_SERVERCONTEXT` will hold the server-path, eg. `/be/prod`. 
- The key `SettingItem.KEY_FILESTORE` holds a string pointing to the server location for storing files. If the extension 
      implementation creates files, it must do so using `SettingItem.KEY_FILESTORE`. Note that the directory is *independent 
      of the account*, ie. it is in the extension's responsibility to manage directories per account if this would be necessary.
- The key `SettingItem.KEY_ISPRODUCTION` holds a Boolean indicating whether the current context is production or not
- TripDataProducer will additionally receive `SettingItem.KEY_ACCOUNT_UUID` as UUID

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

#### Design considerations

- All producer settings should extend `MinimalConverterSettings` and honor the GDPR-settings.
- Don't tailor data towards an anticipated consumer. Import data as-is, and let the consumer implementation figure out what is needed.
- Don't preemptively filter data with an anticipated consumer in mind.
- Use extension data for extension-internal data, use custom fields to share data with other extensions 
- Ticket-related fees should be mapped to individual tickets, even if that means the producer needs to split the fee

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

### TripDataConsumer

A 'consumer' extension only receives travel data, without any feedback to funnel.travel. Typical examples are reporting or data feeds
to accounting systems.

### TripDataWebhook

A 'webhook' extension is typically either a producer or modifier where execution is triggered by a third-party system.

This interface is suitable for push systems such as notification calls from booking systems.


### Updating internal settings and/or booking state

*All* extension types (even consumers) can write back:

1. internal settings
2. extension state on the booking

The first is useful when an extension needs to maintain eg. an accounting sequence number over all executions. The extension simply needs to replace the desired key 
in the `settings` map, and mark the key as `SettingItemValueType.INTERNAL`.

The second one is useful for extensions which need to write back a tracking ID or similar. This, again, could be an accounting sequence number, or a tracking ID etc.
See the following chapter on how to handle such Booking#extensionData.  

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

Any extension, also consumers, can update `extensionData`. The implementation must consider the case where Booking#getExtensionData() returns null.

```java
private void updateExtensionData(final Booking booking, final String value) {
    ObjectNode extensionData = (ObjectNode) booking.getExtensionData();
    if (extensionData == null) {
        extensionData = JsonNodeFactory.instance.objectNode();
        booking.setExtensionData(extensionData);
    }
    ObjectNode internalData = (ObjectNode) extensionData.get(this.getClass().getName());
    if ((internalData == null) || (internalData.isNull())) {
        internalData = JsonNodeFactory.instance.objectNode();
        extensionData.set(this.getClass().getName(), internalData);
    }
    internalData.put("trackingId", value);
}
```

### Shared extension data

In rare cases, sharing data between extensions is critical, and not just a matter of configuring additional functionality. In such cases, the providing extension can
put data in a `Booking.SHARED_EXTENSIONDATA_KEY` key, from which the consuming extension can read it. funnel.travel handles the `Booking.SHARED_EXTENSIONDATA_KEY` specially,
making sure it never gets deleted. 


```json
{
	"ch.want.funnel.extension._shared_": {
	    "product": {
    		"refId": "64f83bd13400007cd0b7cf61",
            "supplier": "wantgmbh"
    	}
	}
}

```

### Additional data for user

A modify or consumer extension can add data to be presented to the user by adding a `ui` node with a predefined structure in the booking extension data:

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

## Common issues

### My extension needs to be called per trip, not per booking

Extension management runs per booking, states are also maintained per booking. However, the associated trip is passed to consumer extensions. Thus for consumer extensions, the implementation
should add a marker in the booking extension data when first called, and then check for that marker on all bookings of the trip to see if the extension was already executed.

### The destinations don't work for me

The destination on a booking and service level are determined based on the organization's location (if associated users are identified). For some extensions, this destination 'calculation'
might be insufficient, or the extension needs to handle multi-stay trips (traveling salesman). For such cases, the `SegmentSplitter` from extension-utils can be used to take
all segments from a service and split it into legs based on the stay at each destination.


## Library dependencies

The extension will run in the JVM context of the funnel.travel, and as such will have available the runtime libraries provided by funnel.travel.

* slf4j-api, version1.7.36
    * Use jcl-over-slf4j for an implementation in tests
* ch.qos.logback, version 1.4.14
* com.google.code.gson, version 2.10.1
* com.google.guava (guava), version 33.0.0-jre
* commons-codec, version 1.16.0
* commons-io, version 2.6
* commons-net, version 3.6
* com.jcraft, jsch, version 0.1.55
* jakarta.mail, version 2.0.2
* jakarta.xml, 4.0.1
* jaxb runtime 4.0.4
* org.apache.httpcomponents.client5 (httpclient5), version 5.2.3
* org.apache.httpcomponents.core5 (httpcore5-reactive), version 5.2.4
* org.apache.cxf, version 3.3.3
* com.fasterxml.jackson.core (jackson-core), version 2.15.3
* com.fasterxml.jackson.core (jackson-dataformat-csv), version 2.15.3
* com.fasterxml.jackson.core (jackson-dataformat-xml), version 2.15.3
* com.fasterxml.jackson.core (jackson-dataformat-yaml), version 2.15.3
* org.freemarker, version 2.3.32
* ch.want.funnel (extension-util), newest 2.x version

