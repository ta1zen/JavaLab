package lab5.Serializers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lab5.Interface.ISerializable;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Generic XML serializer/deserializer class that implements ISerializable interface
 * to handle object serialization and deserialization using Jackson ObjectMapper.
 *
 * @param <T> The type of objects that will be serialized and deserialized.
 */
public class XMLSerializer<T> implements ISerializable<T> {
    private final XmlMapper xmlMapper;
    private final Class<T> tClass;

    /**
     * Constructor that initializes the XMLSerializer with a specific class type.
     * It also registers the JavaTimeModule to support Java 8 time/date classes.
     *
     * @param tClass The class of the type to be serialized/deserialized.
     */
    public XMLSerializer(Class<T> tClass) {
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.registerModule(new JavaTimeModule());
        this.tClass = tClass;
    }

    /**
     * Serializes a single object of type T into its XML string representation.
     *
     * @param object The object to serialize.
     * @return The XML string representation of the object.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Override
    public String serialize(T object) throws IOException {
        return xmlMapper.writeValueAsString(object);

    }

    /**
     * Serializes a list of objects of type T into its XML string representation.
     *
     * @param objectCollection The list of objects to serialize.
     * @return The XML string representation of the object list.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Override
    public String serialize(List<T> objectCollection) throws IOException {
        return xmlMapper.writeValueAsString(objectCollection);
    }

    /**
     * Deserializes an XML string into a single object of type T.
     *
     * @param object The XML string representing the object.
     * @return The deserialized object of type T.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    @Override
    public T deserialize(String object) throws IOException {
        return xmlMapper.readValue(object, tClass);
    }

    /**
     * Deserializes an XML string into a list of objects of type T.
     *
     * @param objectCollection The XML string representing the list of objects.
     * @return The deserialized list of objects of type T.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    @Override
    public List<T> deserializeToList(String objectCollection) throws IOException {
        return xmlMapper.readValue(objectCollection, xmlMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }

    /**
     * Writes a single object of type T to a file in XML format.
     *
     * @param object The object to be written to the file.
     * @param fileName The name of the file to write to.
     * @throws IOException If an I/O error occurs during writing to the file.
     */
    @Override
    public void writeToFile(T object, String fileName) throws IOException {
        xmlMapper.writeValue(new File(fileName), object);
    }

    /**
     * Writes a list of objects of type T to a file in XML format.
     *
     * @param objectCollection The list of objects to be written to the file.
     * @param fileName The name of the file to write to.
     * @throws IOException If an I/O error occurs during writing to the file.
     */
    @Override
    public void writeToFile(List<T> objectCollection, String fileName) throws IOException {
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), objectCollection);
    }

    /**
     * Reads and deserializes a list of objects of type T from an XML file.
     *
     * @param fileName The name of the file to read from.
     * @return The list of deserialized objects of type T.
     * @throws IOException If an I/O error occurs during reading or deserialization.
     */
    @Override
    public List<T> readFromFile(String fileName) throws IOException {
        return xmlMapper.readValue(new File(fileName), xmlMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }
}