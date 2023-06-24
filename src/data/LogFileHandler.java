package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogFileHandler {
	
	final String LOG_FILE = "addressbook.log";

	public void writeLog(String content) {
    	
        File file = new File(LOG_FILE);

        // if the file does not exist create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
