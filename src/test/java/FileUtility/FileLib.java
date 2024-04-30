package FileUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileLib {
    public static String getPropertyData(String key) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\KapRest\\backendtests\\src\\test\\java\\FileUtility\\Cookies");
        Properties p = new Properties();
        p.load(fis);
        return p.getProperty(key);
    }
    public static void writeDataIntoPropertyFile(String value){
        try {
            FileOutputStream fos= new FileOutputStream("C:\\KapRest\\backendtests\\src\\test\\java\\FileUtility\\ToStoreTicketId");
            Properties p=new Properties();
            p.setProperty("createdTicket",value);
            p.store(fos,"WrittenData");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
