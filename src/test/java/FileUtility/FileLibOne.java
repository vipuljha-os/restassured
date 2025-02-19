package FileUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileLibOne {

    public static String getPropertyData(String key) throws IOException {
        FileInputStream fis = new FileInputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreTicketId");
        Properties p = new Properties();
        p.load(fis);
        return p.getProperty(key);
    }

    public static void writeDataIntoPropertyFile(String value, String value1) {
        try {
            FileOutputStream fos = new FileOutputStream("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/ToStoreTicketId");
            Properties p = new Properties();
            p.setProperty("ticketId", value);
            p.setProperty("taskId", value1);
            p.store(fos, "WrittenData");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

