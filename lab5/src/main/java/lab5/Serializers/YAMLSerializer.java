package lab5.Serializers;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lab5.Interface.ISerializable;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Generic JSON serializer/deserializer class that implements ISerializable interface
 * to handle object serialization and deserialization using Jackson ObjectMapper.
 *
 * @param <T> The type of objects that will be serialized and deserialized.
 */
public class YAMLSerializer<T> implements ISerializable<T> {
    private final YAMLMapper yamlMapper;
    private final Class<T> tClass;

    /**
     * Constructor that initializes the YAMLSerializer with a specific class type.
     * It also registers the JavaTimeModule to support Java 8 time/date classes.
     *
     * @param tClass The class of the type to be serialized/deserialized.
     */
    public YAMLSerializer(Class<T> tClass) {
        yamlMapper = new YAMLMapper();
        this.yamlMapper.registerModule(new JavaTimeModule());
        this.tClass = tClass;
    }

    /**
     * Serializes a single object of type T into its YAML string representation.
     *
     * @param object The object to serialize.
     * @return The YAML string representation of the object.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Override
    public String serialize(T object) throws IOException{
        return yamlMapper.writeValueAsString(object);
    }

    /**
     * Serializes a list of objects of type T into its YAML string representation.
     *
     * @param objectCollection The list of objects to serialize.
     * @return The YAML string representation of the object list.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Override
    public String serialize(List<T> objectCollection) throws IOException {
        return yamlMapper.writeValueAsString(objectCollection);
    }

    /**
     * Deserializes a YAML string into a single object of type T.
     *
     * @param object The YAML string representing the object.
     * @return The deserialized object of type T.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    @Override
    public T deserialize(String object) throws IOException {
        return yamlMapper.readValue(object, tClass);
    }

    /**
     * Deserializes a YAML string into a list of objects of type T.
     *
     * @param objectCollection The YAML string representing the list of objects.
     * @return The deserialized list of objects of type T.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    @Override
    public List<T> deserializeToList(String objectCollection) throws IOException {
        return yamlMapper.readValue(objectCollection, yamlMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }

    /**
     * Writes a single object of type T to a file in YAML format.
     *
     * @param object The object to be written to the file.
     * @param fileName The name of the file to write to.
     * @throws IOException If an I/O error occurs during writing to the file.
     */
    @Override
    public void writeToFile(T object, String fileName) throws IOException {
        yamlMapper.writeValue(new File(fileName), object);
    }

    /**
     * Writes a list of objects of type T to a file in YAML format.
     *
     * @param objectCollection The list of objects to be written to the file.
     * @param fileName The name of the file to write to.
     * @throws IOException If an I/O error occurs during writing to the file.
     */
    @Override
    public void writeToFile(List<T> objectCollection, String fileName) throws IOException {
        yamlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), objectCollection);
    }

    /**
     * Reads and deserializes a list of objects of type T from a YAML file.
     *
     * @param fileName The name of the file to read from.
     * @return The list of deserialized objects of type T.
     * @throws IOException If an I/O error occurs during reading or deserialization.
     */
    @Override
    public List<T> readFromFile(String fileName) throws IOException {
        return yamlMapper.readValue(new File(fileName), yamlMapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }
}