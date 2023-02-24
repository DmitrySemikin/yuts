package xyz.dsemikin.yuts.kernel;

import xyz.dsemikin.yuts.csvreader.CsvReader;
import xyz.dsemikin.yuts.data.TimeSheetRecordRaw;
import xyz.dsemikin.yuts.simpletextreportwriter.SimpleTextReportWriter;

import java.util.List;

public class YutsKernel {

    public void doTheJob() {
        final CsvReader csvReader = new CsvReader();
        final List<TimeSheetRecordRaw> timeSheetRecords = csvReader.readCsv();
        final SimpleTextReportWriter reportWriter = new SimpleTextReportWriter();
        reportWriter.writeSimpleTextReport();
    }
}
