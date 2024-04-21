package com.bustransport;

import lombok.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.SimpleFormatter;

@Getter
@Setter
public class LoggingController {

    public String createLog(String logMsg) {

        Logger logger = Logger.getLogger("MyLog");
        FileHandler fileHandler;

        try {
            fileHandler = new FileHandler("log.txt");
            logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.info(logMsg);
            return logMsg;
        } catch (SecurityException | IOException e) {
             e.printStackTrace();
            return e.getMessage();
        }
    }
}