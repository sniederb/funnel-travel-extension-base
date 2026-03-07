package ch.want.funnel.extension.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerFactory;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Use this class when reading any externally provided XML.
 *
 * See https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html#java
 */
public final class SecureXmlFactories {

    private static final DocumentBuilderFactory DOC_BUILDER_FACTORY;
    private static final DocumentBuilderFactory NSAWARE_DOC_BUILDER_FACTORY;
    private static final XMLInputFactory XML_INPUT_FACTORY;
    private static final TransformerFactory TRANSFORMER_FACTORY;
    static {
        try {
            DOC_BUILDER_FACTORY = harden(DocumentBuilderFactory.newInstance());
            NSAWARE_DOC_BUILDER_FACTORY = harden(DocumentBuilderFactory.newInstance());
            NSAWARE_DOC_BUILDER_FACTORY.setNamespaceAware(true);
            XML_INPUT_FACTORY = harden(XMLInputFactory.newFactory());
            TRANSFORMER_FACTORY = harden(TransformerFactory.newInstance());
        } catch (final ParserConfigurationException e) {
            throw new IllegalArgumentException("Failed to create DocumentBuilderFactory", e);
        }
    }

    private SecureXmlFactories() {
    }

    public static DocumentBuilderFactory documentBuilderFactory() {
        return DOC_BUILDER_FACTORY;
    }

    public static DocumentBuilderFactory documentBuilderFactory(final boolean namespaceAware) {
        return namespaceAware ? NSAWARE_DOC_BUILDER_FACTORY : DOC_BUILDER_FACTORY;
    }

    public static XMLInputFactory xmlInputFactory() {
        return XML_INPUT_FACTORY;
    }

    public static TransformerFactory transformerFactory() {
        return TRANSFORMER_FACTORY;
    }

    /**
     * XML security on JAXB unmarshaling is slightly different in that not the JAXBContext needs hardening, but the source itself. This
     * class unmarshals via secure {@link XMLStreamReader}. This method assumes UTF-8 encoding.
     *
     * @return
     * @throws JAXBException
     */
    public static <T> T unmarshall(final Unmarshaller unmarshaller, final byte[] data, final Class<T> targetClass) throws JAXBException {
        return unmarshall(unmarshaller, data, "UTF-8", targetClass);
    }

    public static <T> T unmarshall(final Unmarshaller unmarshaller, final byte[] data, final String encoding, final Class<T> targetClass) throws JAXBException {
        try (InputStream dataStream = new ByteArrayInputStream(data)) {
            final XMLStreamReader streamReader = xmlInputFactory().createXMLStreamReader(dataStream, encoding);
            return targetClass.cast(unmarshaller.unmarshal(streamReader));
        } catch (XMLStreamException | IOException e) {
            throw new JAXBException(e.getMessage(), e);
        }
    }

    static DocumentBuilderFactory harden(final DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setXIncludeAware(false);
        return documentBuilderFactory;
    }

    static XMLInputFactory harden(final XMLInputFactory xmlInputFactory) {
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        return xmlInputFactory;
    }

    static TransformerFactory harden(final TransformerFactory transformerFactory) {
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        return transformerFactory;
    }
}
