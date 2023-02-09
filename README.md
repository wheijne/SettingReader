# SettingReader
A program that takes a settings file and parses it for settings.

It has two classes, Setting and Settings

The Setting class represents a single setting. It only has a parameterized type and a value. 
It also has some standard getters and setters.

The Settings class represents as a collection of Setting objects. For this, it uses a HashMap.
It doubles as a document parser. The constructor takes a BufferedReader, File or String object. 
If a String is passes, it should be the path to an existing, openable file, for example txt.
Each line of the file must be one of the following:
 - An empty line
 - A comment, indicated the starting of the line with //
 - A Setting formatted as follows: KEY SPLITTER VALUE. KEY will become the key that is used to get the setting's value, 
    namely VALUE. The splitter is some String that seperates the two. It can be anything from the default ":" to a long sentance.
    The splitter can be passed to the constructor of Settings.
The settings will be checked for the types Integer, Double and boolean. If the value is not one of those, it will be stored and returned 
as a String. If a value is written as a Double, but is a whole number, it will be stored and returned as an Integer. A boolean true can be
written as true, t, TRUE or T. 
