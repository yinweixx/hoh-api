package com.anyun.cloud.demo.monitor.shell;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/06/2017
 */
public class ShellPrinter {

    /**
     * @param errorMessage
     */
    public static void error(String errorMessage) {
        System.out.println("  " + AnsiConstants.ANSI_RED + errorMessage + AnsiConstants.ANSI_RESET);
    }

    /**
     * @param content
     */
    public static void print(String content) {
        System.out.println(AnsiConstants.ANSI_WHITE + content + AnsiConstants.ANSI_RESET);
    }
}
