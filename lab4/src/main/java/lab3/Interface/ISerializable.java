package lab3.Interface;

import java.io.IOException;
import java.util.List;

public interface ISerializable<T> {

    String serialize(T object) throws IOException;
    String serialize(List<T> objectCollection) throws IOException;

    T deserialize(String object) throws IOException;
    List<T> deserializeToList (String objectCollection) throws IOException;

    void writeToFile(T object, String fileName) throws IOException;
    void writeToFile(List<T> objectCollection, String fileName) throws IOException;

    List<T> readFromFile(String fileName) throws IOException;
}
