package xyz.dsemikin.yuts.app;

import xyz.dsemikin.yuts.kernel.YutsKernel;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        final String timestamp = timestampString();

        final ArgsParser argsParser = new ArgsParser();
        final YutsArgs yutsArgs = argsParser.parseArgs(args);

        final Path inputCsvFilePath = yutsArgs.inputCsvFilePath();
        final String inputFileName = inputCsvFilePath.getFileName().toString();
        final String baseFileName = inputFileName.substring(0, inputFileName.length() - 4);

        final Path outputTimesheetRecordsCsvFilePath = inputCsvFilePath.resolveSibling(baseFileName + "_" + timestamp + "_timesheet-records.csv");
        final Path outputWorkPeriodsCsvFilePath = inputCsvFilePath.resolveSibling(baseFileName + "_" + timestamp + "_work-periods.csv");
        YutsKernel kernel = new YutsKernel();
        kernel.doTheJob(inputCsvFilePath, outputTimesheetRecordsCsvFilePath, outputWorkPeriodsCsvFilePath);
        System.out.println("Yuts - Done.");
    }

    private static String timestampString() {
        final LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
    }

}
