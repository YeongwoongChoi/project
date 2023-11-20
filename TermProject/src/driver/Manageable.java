package driver;
import java.io.BufferedReader;
import java.io.IOException;

public interface Manageable {
    void read(String [] row);
    void print();
    boolean matches(String name, String phoneNumber);
}
