package xyz.dsemikin.yuts.simpletextreportwriter;

import xyz.dsemikin.yuts.data.TimesheetRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class TimesheetRecordsCsvWriter {

    public void writeTimesheetRecordsAsCsv(final List<TimesheetRecord> timesheetRecords, final Path outputCsvFilePath) {
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        final String csvSeparator = "\t";
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvFilePath.toFile(), StandardCharsets.UTF_8))) {
            writer.write("Date");
            writer.write(csvSeparator);
            writer.write("Time");
            writer.write(csvSeparator);
            writer.write("Event");
            writer.write(csvSeparator);
            writer.write("Terminal");
            writer.write("\n");
            final List<TimesheetRecord> sortedTimesheetRecords = timesheetRecords.stream()
                    .sorted(Comparator.comparing(TimesheetRecord::dateTime)).toList();
            for (final TimesheetRecord timesheetRecord : sortedTimesheetRecords) {
                writer.write(timesheetRecord.dateTime().format(dateFormatter));
                writer.write(csvSeparator);
                writer.write(timesheetRecord.dateTime().format(timeFormatter));
                writer.write(csvSeparator);
                writer.write(timesheetRecord.typeOfEvent().toString());
                writer.write(csvSeparator);
                writer.write(timesheetRecord.terminalName());
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
