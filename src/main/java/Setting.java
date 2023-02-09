/**
 * An abstract data type that models a Setting.
 * @author Wout Heijne
 * @param <T> The type of the setting's value
 * @date Feb 5, 2023
 */
public class Setting<T> {
    private T value;
    
    /**
     * Initiates a setting object.
     */
    public Setting() {
        
    }
    
    /**
     * Initiates a setting object.
     * @param value a T value
     */
    public Setting(T value) {
        this.value = value;
    }
    
    /**
     * Sets the setting's value.
     * @param value a T value
     */
    public void setValue(T value) {
        this.value = value;
    }
    
    /**
     * Gets the setting's value.
     * @return the value of the Setting
     */
    public T getValue() {
        return value;
    }
    
    /**
     * Represents the data in a string.
     */
    @Override
    public String toString() {
        return (value.getClass() + ": " + value);
    }
}
