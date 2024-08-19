package FileUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileeLib {

        public static String getPropertyData(String key) throws IOException {
            FileInputStream fis = new FileInputStream("C:\\backendtests\\backendtests\\src\\test\\java\\FileUtility\\Id");
            Properties p = new Properties();
            p.load(fis);
            return p.getProperty(key);
        }
        public static void writeDataIntoPropertyFile(String value, String value1){
            try {
                FileOutputStream fos= new FileOutputStream("C:\\backendtests\\backendtests\\src\\test\\java\\FileUtility\\Id");
                Properties p=new Properties();
                p.setProperty("ticketId", value);
                p.setProperty("taskId", value1);
                p.store(fos,"WrittenData");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public static String getPropertyDataZepto(String key) throws IOException {
            FileInputStream fis = new FileInputStream("C:\\backendtests\\backendtests\\src\\test\\java\\FileUtility\\Id");
            Properties p = new Properties();
            p.load(fis);
            return p.getProperty(key);
        }

    }

