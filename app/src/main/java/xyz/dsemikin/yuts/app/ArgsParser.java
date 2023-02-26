package xyz.dsemikin.yuts.app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgsParser {
    public YutsArgs parseArgs(final String[] args) {
        // args[0] - input csv (must exist)
        // args[1] - output csv (not allowed to exist)
        if (args.length != 2) {
            throw new RuntimeException("Exactly two arguments are expected. First - input CSV. Second - Output CSV.");
        }

        final Path inputCsvFilePath = Paths.get(args[0]);
        if (!Files.isRegularFile(inputCsvFilePath)) {
            throw new RuntimeException("First argument must point to the existing file - input csv file");
        }

        final Path outputCsvFilePath = Paths.get(args[1]);
        if (Files.exists(outputCsvFilePath)) {
            throw new RuntimeException("Second argument is path of output file. File should not exist, but it exists.");
        }

        if (!Files.isDirectory(outputCsvFilePath.getParent())) {
            throw new RuntimeException("Parent directory of the specified output CSV file does not exist.");
        }

        return new YutsArgs(inputCsvFilePath, outputCsvFilePath);
    }
}
