package xyz.dsemikin.yuts.app;

import java.nio.file.Path;

public record YutsArgs (
        Path inputCsvFilePath,
        Path outputCsvFilePath
) {
    // nothing here
}
