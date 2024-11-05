package FileUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileLib {
    public static String getPropertyData(String key) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\backendtests\\backendtests\\src\\test\\java\\FileUtility\\ToStoreCookies");
        Properties p = new Properties();
        p.load(fis);
        return p.getProperty(key);
    }

    public static void writeDataIntoPropertyFile(String value) {
        try {
            FileOutputStream fos= new FileOutputStream("C:\\backendtests\\backendtests\\src\\test\\java\\FileUtility\\ToStoreCookies");
            Properties p=new Properties();
            p.setProperty("StoredCookies",value);
            p.store(fos,"WrittenData");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getPropertyDataBigBasket(String key) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\kapture Restassured\\backendtests\\src\\test\\java\\FileUtility\\BigBasketCookiesStore");
        Properties p = new Properties();
        p.load(fis);
        return p.getProperty(key);
    }

    public static void writeDataIntoPropertyFileBigBasket(String value) {
        try {
            FileOutputStream fos= new FileOutputStream("C:\\kapture Restassured\\backendtests\\src\\test\\java\\FileUtility\\BigBasketCookiesStore");
            Properties p=new Properties();
            p.setProperty("StoredCookies",value);
            p.store(fos,"WrittenData");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
