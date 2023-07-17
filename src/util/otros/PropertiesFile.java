package util.otros;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

	public static final String config = "C:/opt/util.properties";
	
	public static void main(String[] args) {
		
		Properties prop = new Properties();
		Integer folio = 0;
		try {
			prop.load(new FileInputStream(config));
			folio = Integer.valueOf(prop.getProperty("OpenMarket_Folio_Count"));
			prop.setProperty("OpenMarket_Folio_Count", (++folio).toString());
			prop.store(new FileOutputStream(config),null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(folio);
		
	}

}
