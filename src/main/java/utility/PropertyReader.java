package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    Properties properties = new Properties();

    public PropertyReader(String path){
        try{
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public String getValue(String key){
        return properties.getProperty(key);
    }
}
