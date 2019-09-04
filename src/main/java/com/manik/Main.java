package com.manik;

import com.manik.utils.LogLevel;
import com.manik.utils.LogServiceUtil;
import com.manik.utils.LogServiceUtilImpl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Please, point a path to the logs in the command line argument");
            System.out.println("You can point a path to the result log in the second parameter of the command line");
            System.exit(2);
        }

        LogServiceUtil logReader = new LogServiceUtilImpl();
        Path path = Paths.get(args[0]);

        HashSet<String> extractedLogs = (HashSet<String>) logReader.logExtractorByLogLevel(path, LogLevel.INFO);
        System.out.println(extractedLogs);

        Map<LocalDateTime, Integer> temp = new TreeMap<>();
        temp = (Map<LocalDateTime, Integer>) logReader.logCountByHours(extractedLogs);
        System.out.println(temp);

        String resultPath = args[0] + "\\result";

        if (args.length > 1) {
            resultPath = new File(args[1]).toString();
        }

        new File(resultPath).mkdir();

        logReader.logSaver(temp, Paths.get(resultPath + "\\logResult.txt"), LogLevel.ERROR);
    }
}
