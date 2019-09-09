package com.manik;

import com.manik.utils.LogLevel;
import com.manik.utils.LogReaderImpl;
import com.manik.utils.LogServiceUtilImpl;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String pathToTheLogs;
        String pathToTheResultLog;
        String samplingPeriod;
        int day = 0;
        int hour = 0;
        int min = 0;
        boolean isParameterCorrect = false;
        String filteringLevel;

        //I do not check input values. We assume that the user entered them correctly.
        Scanner usersParameters = new Scanner(System.in);

        System.out.println("Please enter a path to a dir with logs: ");
        pathToTheLogs = usersParameters.nextLine();
        System.out.println("Please enter the path where to save the logs: ");
        pathToTheResultLog = usersParameters.nextLine();
        System.out.println("Please type filtering level (ERROR, WARNING, INFO)");
        filteringLevel = usersParameters.nextLine();
        System.out.println("Please enter the interval for count");
        System.out.println("days (0 - if your interval is less)");
        day = usersParameters.nextInt();
        System.out.println("hours (0 - if your interval is less)");
        hour = usersParameters.nextInt();
        System.out.println("mins (minimum value should be 1)");
        min = usersParameters.nextInt();


        LogLevel level = LogLevel.valueOf(filteringLevel);

        LogReaderImpl logReaderWriter = new LogReaderImpl();
        Set<String> filesSet = logReaderWriter.readLogsFilesNames(pathToTheLogs);

        Set<String> logsSet = logReaderWriter.extractStringFromLogFile(filesSet);

        LogServiceUtilImpl logServiceUtil = new LogServiceUtilImpl();
        Set<String> logsByLevel =  logServiceUtil.logExtractorByLogLevel(logsSet, level);

        Map<LocalDateTime, Integer> logsCount = new TreeMap<>();

        int interval = day * 24 + hour * 60 + min;

        if (day == 0) {
            if(hour == 0) {
                logsCount = logServiceUtil.logCountingByTimeIntervals(logsByLevel, min);
            } else {
                logsCount = logServiceUtil.logCountingByTimeIntervals(logsByLevel, hour, min);
            }
        } else {
            logsCount = logServiceUtil.logCountingByTimeIntervals(logsByLevel, day, hour, min);
        }

        logServiceUtil.logSaver(logsCount, Paths.get(pathToTheResultLog +
                "\\logResult_d_" + day + "_h_" + hour + "_m_" + min + ".txt"), level, interval);


    }
}
