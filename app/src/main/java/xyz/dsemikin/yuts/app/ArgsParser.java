package xyz.dsemikin.yuts.app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgsParser {
    public YutsArgs parseArgs(final String[] args) {
        // args[0] - input csv (must exist)
        // args[1] - output csv (not allowed to exist)
        if (args.length != 1) {
            throw new RuntimeException("Exactly one arguments is expected: input CSV.");
        }

        final Path inputCsvFilePath = Paths.get(args[0]);
        if (!Files.isRegularFile(inputCsvFilePath)) {
            throw new RuntimeException("First argument must point to the existing file - input csv file");
        }

        return new YutsArgs(inputCsvFilePath);
    }

}
