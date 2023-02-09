import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * A test class for the Settings class.
 * @author Wout Heijne
 * @date Feb 5, 2023
 */
public class SettingsTest {
    
    public SettingsTest() {}
    
    /**
     * Test constructor with empty settings file.
     */
    @Test
    public void testConstructorEmptySettings() {
        System.out.println("Test constructor with empty settings file");
        try {
            Settings s = S("C:\\Users\\20212831\\Documents\\NetBeansProjects\\BunkerGame\\src\\test\\TestSettingsEmpty.txt");
            assertEquals(0, s.size(), "0 = size");
        } catch (IOException e) {
            fail("Empty settings constructor with exception: " + e.getMessage());
        }
    }
    
    /**
     * Test constructor with string, integer and Boolean setting values.
     */
    @Test
    public void testConstructor() {
        System.out.println("Test constructor with settings of type string, integer and boolean");
        try {
            Settings s = S("C:\\Users\\20212831\\Documents\\NetBeansProjects\\BunkerGame\\src\\test\\TestConstructorSettings.txt");
            System.out.println(s.getMap());
            assertEquals(50, s.get("one"), "Setting one = 50");
            assertEquals(true, s.get("two"), "Setting two = true");
            assertEquals("3TS", s.get("THREE"), "Setting three = 3TS");
            assertEquals(3, s.size(), "Size = 3");
        } catch (IOException e) {
            fail("Failed with exception " + e.getMessage());
        }
    }
    
    /**
     * Test add method.
     */
    @Test
    public void testAdd() {
        System.out.println("Test the add method");
        try {
            Settings s = S("C:\\Users\\20212831\\Documents\\NetBeansProjects\\BunkerGame\\src\\test\\TestConstructorSettings.txt");
            s.add("four", new Setting("abc"));
            assertEquals("abc", s.get("four"), "setting four = abc");
            assertEquals(50, s.get("one"), "Setting one = 50");
            assertEquals(true, s.get("two"), "Setting two = true");
            assertEquals("3TS", s.get("THREE"), "Setting three = 3TS");
            assertEquals(4, s.size(), "Size = 4");
        } catch (IOException e) {
            fail("Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test remove method, setting present.
     */
    @Test
    public void testRemovePresent() {
        System.out.println("Test the remove method with a present setting");
        try {
            Settings s = S("C:\\Users\\20212831\\Documents\\NetBeansProjects\\BunkerGame\\src\\test\\TestConstructorSettings.txt");
            s.remove("one");
            assertEquals(true, s.get("two"), "Setting two = true");
            assertEquals("3TS", s.get("THREE"), "Setting three = 3TS");
            assertEquals(2, s.size(), "Size = 2");
        } catch (IOException e) {
            fail("Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test remove method, setting not present.
     */
    @Test
    public void testRemoveNotPresent() {
        System.out.println("Test the remove method with a non-present setting");
        try {
            Settings s = S("C:\\Users\\20212831\\Documents\\NetBeansProjects\\BunkerGame\\src\\test\\TestConstructorSettings.txt");
            s.remove("kajushdbkasdb");
            assertEquals(50, s.get("one"), "Setting one = 50");
            assertEquals(true, s.get("two"), "Setting two = true");
            assertEquals("3TS", s.get("THREE"), "Setting three = 3TS");
            assertEquals(3, s.size(), "Size = 3");
        } catch (IOException e) {
            fail("Exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testConstructorEmptyLine() {
        System.out.println("Test the constructor with an empty line in the Settings file");
        try {
            Settings s = S("C:\\Users\\20212831\\Documents\\NetBeansProjects\\BunkerGame\\src\\test\\TestConstructorEmptyLine.txt");
            assertEquals(50, s.get("one"), "Setting one = 50");
            assertEquals("3TS", s.get("THREE"), "Setting three = 3TS");
            assertEquals(2, s.size(), "Size = 2");
        } catch (IOException e) {
            fail("Exception: " + e.getMessage());
        }
    }
    
    private Settings S(String l) throws IOException {
        return new Settings(new BufferedReader(new FileReader(new File(l))));
    }
}
