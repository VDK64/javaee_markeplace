package marketplace.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Basic property loader for loading properties from application.properties file.
 */
public class PropertyReader {
    private static final String PROPERTY_FILE = "application.properties";
    public static final Properties PROPERTIES = new Properties();

    /*
      All properties loading performing in this block.
     */
    static {
        InputStream inputStream = PropertyReader.class.getClassLoader()
                .getResourceAsStream(PROPERTY_FILE);
        try {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
