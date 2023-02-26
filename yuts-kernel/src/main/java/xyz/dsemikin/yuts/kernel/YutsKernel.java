package xyz.dsemikin.yuts.kernel;

import xyz.dsemikin.yuts.csvreader.CsvReader;
import xyz.dsemikin.yuts.data.RawTimesheetRecord;
import xyz.dsemikin.yuts.data.TimesheetRecord;
import xyz.dsemikin.yuts.simpletextreportwriter.TimesheetRecordsCsvWriter;

import java.nio.file.Path;
import java.util.List;

public class YutsKernel {


    public void doTheJob(final Path inputCsvFilePath, final Path outputCsvFilePath) {

        final CsvReader csvReader = new CsvReader();
        final List<RawTimesheetRecord> rawTimesheetRecords = csvReader.readCsv(inputCsvFilePath);

        RawTimesheetRecordsParser parser = new RawTimesheetRecordsParser();
        final List<TimesheetRecord> timesheetRecords = parser.parseRecordsData(rawTimesheetRecords);

        final TimesheetRecordListCleaner cleaner = new TimesheetRecordListCleaner();
        final List<TimesheetRecord> timesheetRecordsCleaned = cleaner.cleanupTimesheetRecordsList(timesheetRecords);

        final TimesheetRecordListValidator validator = new TimesheetRecordListValidator();
        validator.validateTimesheetRecordList(timesheetRecordsCleaned);

        final TimesheetRecordsCsvWriter timesheetRecordsCsvWriter = new TimesheetRecordsCsvWriter();
        timesheetRecordsCsvWriter.writeTimesheetRecordsAsCsv(timesheetRecordsCleaned, outputCsvFilePath);
    }


}
