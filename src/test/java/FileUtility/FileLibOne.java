package FileUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileLibOne {

    public static String getPropertyDataGoldenRama(String key) throws IOException {
        FileInputStream fis = new FileInputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreTicketIdGoldenRama");
        Properties p = new Properties();
        p.load(fis);
        return p.getProperty(key);
    }

    public static void writeDataIntoPropertyFileGoldenRama(String value, String value1) {
        try {
            FileOutputStream fos = new FileOutputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreTicketIdGoldenRama");
            Properties p = new Properties();
            p.setProperty("ticketIdGoldenRama", value);
            p.setProperty("taskIdGoldenRama", value1);
            p.store(fos, "WrittenData");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyDataDana(String key) throws IOException {
        FileInputStream fis = new FileInputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreTicketIdDana");
        Properties p = new Properties();
        p.load(fis);
        return p.getProperty(key);
    }

    public static void writeDataIntoPropertyFileDana(String value, String value1) {
        try {
            FileOutputStream fos = new FileOutputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreTicketIdDana");
            Properties p = new Properties();
            p.setProperty("ticketIdDana", value);
            p.setProperty("taskIdDana", value1);
            p.store(fos, "WrittenData");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

