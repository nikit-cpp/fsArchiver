package logic;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nik on 05.11.14.
 */
public class Runner {
    static String filesDir = "/home/nik/images";
    public static Logger LOGGER = Logger.getLogger(Runner.class);

    static String INPUT_DIR_OPTION = "input";
    static String OUTPUT_DIR_OPTION = "output";
    static String SLEEP_TIME_OPTION = "sleep";
    static String HELP_OPTION = "help";

    /**
     *
     * @param args входная и выходная директории
     *
     */
    public static void main(String... args){
        Options options = new Options();

        Option inputDirOption = OptionBuilder.withLongOpt(INPUT_DIR_OPTION)
                .withArgName("input directory")
                .hasArg()
                .withDescription("Directory which contains input files. This directory will be scanned.")
                .isRequired()
                .create("i");

        Option outputDirOption = OptionBuilder.withLongOpt(OUTPUT_DIR_OPTION)
                .withArgName("output directory")
                .hasArg()
                .withDescription("Directory which will be contains output files.")
                .isRequired()
                .create("o");

        Option sleepTimeOption = OptionBuilder.withLongOpt(SLEEP_TIME_OPTION)
                .withArgName("seconds")
                .hasArg()
                .withDescription("interval in seconds, on expiry it scanning input directory will be invoked.")
                .isRequired()
                .create("s");

        Option helpOption = OptionBuilder.withLongOpt(HELP_OPTION)
                .withDescription("show this help :)")
                .create("h");

        HelpFormatter formatter = new HelpFormatter();

        options.addOption(inputDirOption);
        options.addOption(outputDirOption);
        options.addOption(sleepTimeOption);
        options.addOption(helpOption);

        CommandLineParser cmdLinePosixParser = new PosixParser();// создаем Posix парсер

        try {
            CommandLine commandLine = cmdLinePosixParser.parse(options, args);// парсим командную строку
            if(commandLine.hasOption(HELP_OPTION)){
                formatter.printHelp("fsArchiver", options);
            }

            String inputDirValue = commandLine.getOptionValue(INPUT_DIR_OPTION);// если такая опция есть, то получаем переданные ей аргументы
            System.out.println("We try to inputDir: " + inputDirValue);// выводим переданный логин
        } catch (ParseException e) {
            LOGGER.error("Error on process commandline args: " + e.getLocalizedMessage());
            formatter.printHelp("fsArchiver", options);
        }
    }
}
