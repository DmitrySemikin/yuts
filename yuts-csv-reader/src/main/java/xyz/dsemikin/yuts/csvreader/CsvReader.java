package xyz.dsemikin.yuts.csvreader;

import xyz.dsemikin.yuts.data.RawTimeSheetRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {

    public List<RawTimeSheetRecord> readCsv(final Path csvFilePath) {
        final List<String> csvLines;
        try {
            csvLines = Files.readAllLines(csvFilePath); // Very inefficient but OK for our case
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return csvLines.stream()
                .skip(1) // First line is header - we skip it.
                .map(this::parseCsvLine)
                .collect(Collectors.toList());
    }

    private RawTimeSheetRecord parseCsvLine(final String csvLine) {
        final String[] words = csvLine.split(";");
        if (words.length != 5) {
            throw new RuntimeException("Unexpected number of words in CSV Line. Line: '" + csvLine + "', words: '" + Arrays.toString(words) + "' ");
        }
        // Structure of the line:
        // Icon;Zeit;Ereignisart;Terminal Name;Fehlercode
        // 0    1    2           3             4
        // we ignore "icon" - words[0]
        return new RawTimeSheetRecord(words[1], words[2], words[3], words[4]);
    }
}
