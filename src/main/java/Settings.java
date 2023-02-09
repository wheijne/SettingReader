
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A class representing a collection of settings. It takes the path to some
 * settings file, should be readable by a {@link BufferedReader} object. Each
 * line should be one of the following:
 * <ul>
 * <li>An empty line,
 * <li>A line containing a comment, so starting with {@code //},
 * <li>A line containing a setting, formatted as follows:
 * {@code key splitter value}.
 * </ul>
 * The value will be checked for type in the following order:
 * <ul>
 * <li>Double
 * <li>Integer
 * <li>Boolean
 * <li>String
 * </ul>
 * The get method returns an {@link Object}, so a cast should be added to the
 * preferred type. An alternative is to use the parameterized method.
 *
 * @author Wout Heijne
 * @date Feb 5, 2023
 */
public class Settings {

    private HashMap<String, Setting> settings = new HashMap<>();

    /**
     * Constructs an empty instance of settings.
     */
    public Settings() {

    }

    /**
     * Constructs a new instance of Settings.
     *
     * @param fileLocation a String pointing to a file containing settings.
     * @throws FileNotFoundException if the file does not exist, is a directory
     * rather than a regular file, or for some other reason cannot be opened for
     * reading.
     * @throws IOException if an I/O error occurs.
     */
    public Settings(String fileLocation) throws FileNotFoundException, IOException {
        mapBR(new BufferedReader(new FileReader(fileLocation)), ":");
    }

    /**
     * Constructs a new instance of Settings.
     *
     * @param fileLocation a String pointing to a file containing settings.
     * @param splitter a Character that is used to differentiate between setting
     * and value.
     * @throws FileNotFoundException if the file does not exist, is a directory
     * rather than a regular file, or for some other reason cannot be opened for
     * reading.
     * @throws IOException if an I/O error occurs.
     * @throws NullPointerException if {@code fileLocation == null}.
     */
    public Settings(String fileLocation, String splitter) throws FileNotFoundException, IOException {
        if (fileLocation == null) {
            throw new NullPointerException("fileLocation should not be null");
        }
        mapBR(new BufferedReader(new FileReader(fileLocation)), splitter);
    }

    /**
     * Construct a new instance of Settings.
     *
     * @param file a File object.
     * @throws IOException if an I/O error occurs.
     * @throws NullPointerException if {@code file == null}.
     */
    public Settings(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("file should not be null");
        }
        mapBR(new BufferedReader(new FileReader(file)), ":");
    }

    /**
     * Construct a new instance of Settings.
     *
     * @param file a File object.
     * @param splitter The character used to differentiate between key and
     * value.
     * @throws IOException if file can not be read
     * @throws NullPointerException if {@code file == null}.
     */
    public Settings(File file, String splitter) throws IOException, NullPointerException {
        if (file == null) {
            throw new NullPointerException("file should not be null");
        }
        mapBR(new BufferedReader(new FileReader(file)), splitter);
    }

    /**
     * Constructs a new instance of Settings.
     *
     * @param br a BufferedReader object
     * @throws IOException if {@code br} can not be read
     */
    public Settings(BufferedReader br) throws IOException {
        mapBR(br, ":");
    }

    /**
     * Constructs a new instance of Settings.
     *
     * @param br a BufferedReader object.
     * @param splitter The character used to differentiate between key and
     * value.
     * @throws IOException if {@code br} can not be read
     */
    public Settings(BufferedReader br, String splitter) throws IOException {
        mapBR(br, splitter);
    }

    /**
     * Constructs a new instance of Settings.
     *
     * @param list an array of Settings
     */
    public Settings(HashMap<String, Setting> list) {
        settings = new HashMap<>(list);
    }

    /**
     * Reads a BufferedReader object and maps it into a settings collection.
     *
     * @param br a BufferedReader object to be read.
     * @throws IOException if {@code br} can not be read.
     * @pre {@code br != null}.
     */
    private void mapBR(BufferedReader br, String splitter) throws IOException {
        String line = br.readLine();
        while (line != null) {
            
            String[] arr = line.split(splitter, 2);
            Setting S;
            if (arr.length > 1 && !line.startsWith("//")) {
                mapLine(line, splitter);
            }
            line = br.readLine();
        }
        br.close();
    }

    /**
     * Converts some String to a Setting and adds it to Settings.
     */
    private void mapLine(String line, String splitter) {
        String[] arr = line.split(splitter, 0);
        Setting S;
        try {
            Double d = Double.valueOf(arr[1]);
            if ((d == Math.floor(d)) && !Double.isInfinite(d)) {
                S = new Setting<>((int) Math.round(d));
            } else {
                S = new Setting<>(d);
            }
        } catch (NumberFormatException e) {
            S = switch (arr[1].strip().toLowerCase()) {
                case "true", "t" ->
                    new Setting<>(true);
                case "false", "f" ->
                    new Setting<>(false);
                default ->
                    new Setting<>(arr[1].strip());
            };
        }
        settings.put(arr[0].strip(), S);
    }

    /**
     * Adds a setting to the collection.
     *
     * @param key a String.
     * @param setting a Setting object to be added.
     * @throws IllegalArgumentException if the collection already contains a
     * mapping with the {@code key}.
     */
    public void add(String key, Setting setting) {
        if (settings.containsKey(key)) {
            throw new IllegalArgumentException("Key already exists in collection");
        }
        settings.put(key, setting);
    }

    /**
     * Removes the setting with key {@code key}. If it is not in the collection,
     * it changes nothing.
     *
     * @param key A string.
     */
    public void remove(String key) {
        settings.remove(key);
    }

    /**
     * Clears the collection.
     */
    public void clear() {
        settings.clear();
    }

    /**
     * Gets the value of key {@code key}.
     *
     * @param key a String value
     * @return the value
     */
    public Object get(String key) {
        return settings.get(key).getValue();
    }

    /**
     * Gets the setting as an Integer.
     *
     * @param <T> the type of returned Setting.
     * @param key The key for the setting.
     * @param type The class of the type.
     * @return {@code T} object.
     */
    public <T extends Object> T get(String key, Class<T> type) {
        return (T) get(key);
    }

    /**
     * Gets the full settings map.
     *
     * @return the settings map
     */
    public HashMap<String, Setting> getMap() {
        return settings;
    }

    /**
     * Gets the size of the collection.
     *
     * @return size of collection
     */
    public int size() {
        return settings.size();
    }

    /**
     * Represents the data in a string.
     */
    @Override
    public String toString() {
        String s = "";
        for (Entry<String, Setting> entry : settings.entrySet()) {
            s += entry.getKey();
            s += ": ";
            s += entry.getValue().toString();
            s += ", ";
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }

}
