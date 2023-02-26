package xyz.dsemikin.yuts.simpletextreportwriter;

import xyz.dsemikin.yuts.data.WorkPeriod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WorkPeriodsCsvWriter {

    public void writeWorkPeriodsAsCsv(final List<WorkPeriod> workPeriods, final Path outputCsvFilePath) {
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        final String csvSeparator = "\t";
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvFilePath.toFile(), StandardCharsets.UTF_8))) {
            writer.write("Begin Date");
            writer.write(csvSeparator);
            writer.write("Begin Time");
            writer.write(csvSeparator);
            writer.write("End Date");
            writer.write(csvSeparator);
            writer.write("End Time");
            writer.write(csvSeparator);
            writer.write("Duration in hours");
            writer.write(csvSeparator);
            writer.write("Length in minutes");
            writer.write("\n");
            for (final WorkPeriod workPeriod : workPeriods) {
                writer.write(workPeriod.getPeriodBegin().format(dateFormatter));
                writer.write(csvSeparator);
                writer.write(workPeriod.getPeriodBegin().format(timeFormatter));
                writer.write(csvSeparator);
                writer.write(workPeriod.getPeriodEnd().format(dateFormatter));
                writer.write(csvSeparator);
                writer.write(workPeriod.getPeriodEnd().format(timeFormatter));
                writer.write(csvSeparator);
                writer.write(String.format("%.2f", workPeriod.getFullDurationInHours()));
                writer.write(csvSeparator);
                writer.write(String.format("%.2f", workPeriod.getFullDurationInMinutes()));
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
