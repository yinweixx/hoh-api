package com.anyun.cloud.demo.monitor.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.JLineShellComponent;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */

public class HelloWorldCommands implements CommandMarker {

    private boolean simpleCommandExecuted = false;

    @Autowired
    private JLineShellComponent shell;

    @CliAvailabilityIndicator({"hw simple"})
    public boolean isSimpleAvailable() {
        //always available
        return true;
    }

    @CliAvailabilityIndicator({"hw complex", "hw enum"})
    public boolean isComplexAvailable() {
        if (simpleCommandExecuted) {
            return true;
        } else {
            return false;
        }
    }

    @CliCommand(value = "hw simple", help = "Print a simple hello world message")
    public String simple(
            @CliOption(key = {"message"}, mandatory = true, help = "The hello world message") final String message,
            @CliOption(key = {"location"}, mandatory = false, help = "Where you are saying hello", specifiedDefaultValue = "At work") final String location) {
        System.out.println("SHELL : " + shell);
        simpleCommandExecuted = true;
        return "Message = [" + message + "] Location = [" + location + "]";
    }

    @CliCommand(value = "hw complex", help = "Print a complex hello world message (run 'hw simple' once first)")
    public String hello(
            @CliOption(key = {"message"}, mandatory = true, help = "The hello world message") final String message,
            @CliOption(key = {"name1"}, mandatory = true, help = "Say hello to the first name") final String name1,
            @CliOption(key = {"name2"}, mandatory = true, help = "Say hello to a second name") final String name2,
            @CliOption(key = {"time"}, mandatory = false, specifiedDefaultValue = "now", help = "When you are saying hello") final String time,
            @CliOption(key = {"location"}, mandatory = false, help = "Where you are saying hello") final String location) {
        return "Hello " + name1 + " and " + name2 + ". Your special message is " + message + ". time=[" + time + "] location=[" + location + "]";
    }

    @CliCommand(value = "hw enum", help = "Print a simple hello world message from an enumerated value (run 'hw simple' once first)")
    public String eenum(
            @CliOption(key = {"message"}, mandatory = true, help = "The hello world message") final MessageType message) {
        return "Hello.  Your special enumerated message is " + message;
    }

    enum MessageType {
        Type1("type1"),
        Type2("type2"),
        Type3("type3");

        private String type;

        private MessageType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}