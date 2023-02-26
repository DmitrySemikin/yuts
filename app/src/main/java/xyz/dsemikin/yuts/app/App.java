package xyz.dsemikin.yuts.app;

import xyz.dsemikin.yuts.kernel.YutsKernel;

public class App {
    public static void main(String[] args) {
        final ArgsParser argsParser = new ArgsParser();
        final YutsArgs yutsArgs = argsParser.parseArgs(args);
        YutsKernel kernel = new YutsKernel();
        kernel.doTheJob(yutsArgs.inputCsvFilePath(), yutsArgs.outputCsvFilePath());
        System.out.println("Yuts - Done.");
    }
}
