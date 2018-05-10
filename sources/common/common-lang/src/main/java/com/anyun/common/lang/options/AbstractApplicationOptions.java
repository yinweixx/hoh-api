/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.anyun.common.lang.options;

import com.anyun.common.lang.StringUtils;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 10/10/16
 */
public abstract class AbstractApplicationOptions implements ApplicationOptions {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractApplicationOptions.class);
    private CommandLine commandLine;
    private Options options = new Options();

    public AbstractApplicationOptions(String[] args) {
        buildOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption("h")) {
                printUsage("");
            }
        } catch (ParseException exp) {
            LOGGER.error("Parsing failed.  Reason: ", exp.getMessage(), exp);
            System.exit(0);
        }
    }

    @Override
    public void printUsage(String name) {
        if (StringUtils.isNotEmpty(name))
            name = "usage";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(name, options);
        System.exit(0);
    }

    protected abstract void buildOptions();

    @Override
    public CommandLine getCommandLine() {
        return commandLine;
    }

    @Override
    public Options getOptions() {
        return options;
    }
}
