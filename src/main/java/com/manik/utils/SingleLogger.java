package com.manik.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleLogger {
    private static final Logger LOGGER = Logger.getLogger(SingleLogger.class.getName());
    Handler fileHandler;

    {
        try {
            fileHandler = new FileHandler("./logExtractorLogger.log");
            LOGGER.addHandler(fileHandler);
            fileHandler.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SingleLogger() {}

    public static SingleLogger getInstance() {
        return LoggerHolder.INSTANCE;
    }

    private static class LoggerHolder {
        private final static SingleLogger INSTANCE = new SingleLogger();
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }

    public static void log(Level level, String message, String additionMessage) {
        LOGGER.log(level, message, additionMessage);
    }
}
