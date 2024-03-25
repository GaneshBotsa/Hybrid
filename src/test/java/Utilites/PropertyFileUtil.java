package Utilites;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileUtil {
public static String getValueForKey(String key) throws Throwable	{
	Properties conpro= new Properties();
	conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	return conpro.getProperty(key);
}

}
