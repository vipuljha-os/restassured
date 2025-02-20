package FileUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileLib {
    public static String getPropertyDataGoldenRama(String key) throws IOException {
        FileInputStream fis = new FileInputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreCookiesGoldenRama");
        Properties p = new Properties();
        p.load(fis);
        return p.getProperty(key);
    }

    public static void writeDataIntoPropertyFileGoldenRama(String value) {
        try {
            FileOutputStream fos= new FileOutputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreCookiesGoldenRama");
            Properties p=new Properties();
            p.setProperty("StoredCookies",value);
            p.store(fos,"WrittenData");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getPropertyDataDana(String key) throws IOException {
        FileInputStream fis = new FileInputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreCookiesDana");
        Properties p = new Properties();
        p.load(fis);
        return p.getProperty(key);
    }

    public static void writeDataIntoPropertyFileDana(String value) {
        try {
            FileOutputStream fos= new FileOutputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreCookiesDana");
            Properties p=new Properties();
            p.setProperty("StoredCookies",value);
            p.store(fos,"WrittenData");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
