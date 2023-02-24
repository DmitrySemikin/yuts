package xyz.dsemikin.yuts.app;

import xyz.dsemikin.yuts.kernel.YutsKernel;

public class App {
    public static void main(String[] args) {
        YutsKernel kernel = new YutsKernel();
        kernel.doTheJob(args[0]);
    }
}
