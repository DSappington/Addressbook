package data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogFileHandler {
    public void writeLog(String content, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
