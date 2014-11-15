package logic;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nik on 05.11.14.
 */
public class Runner {
    static final int EXIT_ERROR = 1;
    public static Logger LOGGER = Logger.getLogger(Runner.class);

    public static String xmlFileName = "fileItems.xml";

    static String INPUT_DIR_OPTION = "input";
    static String OUTPUT_DIR_OPTION = "output";
    static String SLEEP_TIME_OPTION = "sleep";
    static String HELP_OPTION = "help";
    static String NUM_THREADS_OPTION = "threads";

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
                .withType(File.class)
                .create("i");

        Option outputDirOption = OptionBuilder.withLongOpt(OUTPUT_DIR_OPTION)
                .withArgName("output directory")
                .hasArg()
                .withDescription("Directory which will be contains output files.")
                .isRequired()
                .withType(File.class)
                .create("o");

        // http://stackoverflow.com/questions/5585634/apache-commons-cli-option-type-and-default-value
        // http://svn.apache.org/viewvc/commons/proper/cli/trunk/src/main/java/org/apache/commons/cli/TypeHandler.java?view=markup
        Option sleepTimeOption = OptionBuilder.withLongOpt(SLEEP_TIME_OPTION)
                .withArgName("seconds")
                .hasArg()
                .withDescription("interval in seconds, on expiry it scanning input directory will be invoked.")
                .isRequired()
                .withType(Number.class)
                .create("s");

        Option numThreadsOption = OptionBuilder.withLongOpt(NUM_THREADS_OPTION)
                .withArgName("number")
                .hasArg()
                .withDescription("number of threads, >= 1")
                .isRequired()
                .withType(Number.class)
                .create("n");

        Option helpOption = OptionBuilder.withLongOpt(HELP_OPTION)
                .withDescription("show this help :)")
                .create("h");

        HelpFormatter formatter = new HelpFormatter();

        options.addOption(inputDirOption);
        options.addOption(outputDirOption);
        options.addOption(sleepTimeOption);
        options.addOption(helpOption);
        options.addOption(numThreadsOption);

        int numThreads = -1;
        int sleepTime = -1; // in seconds
        File inputDir = null;
        File outputDir = null;

        CommandLineParser cmdLinePosixParser = new PosixParser();// создаем Posix парсер

        try {
            CommandLine commandLine = cmdLinePosixParser.parse(options, args);// парсим командную строку
            if(commandLine.hasOption(HELP_OPTION)){
                formatter.printHelp("fsArchiver", options);
            }

            inputDir = ((File)commandLine.getParsedOptionValue(INPUT_DIR_OPTION));
            outputDir = ((File)commandLine.getParsedOptionValue(OUTPUT_DIR_OPTION));
            numThreads = ((Number)commandLine.getParsedOptionValue(NUM_THREADS_OPTION)).intValue();
            sleepTime = ((Number)commandLine.getParsedOptionValue(SLEEP_TIME_OPTION)).intValue();

            if(!inputDir.exists())
                throw new FileNotFoundException("Input dir '" + inputDir + "' not exists");

            if(!outputDir.exists())
                throw new FileNotFoundException("Output dir '" + outputDir  + "' not exists");

            LOGGER.debug("input dir: " + inputDir);
            LOGGER.debug("output dir: " + outputDir);
            LOGGER.debug("num of threads: " + numThreads);
            LOGGER.debug("sleep time: " + sleepTime);
        } catch (ParseException e) {
            LOGGER.error("Error on process commandline args: " + e.getLocalizedMessage());
            formatter.printHelp("fsArchiver", options);
            System.exit(EXIT_ERROR);
        } catch (FileNotFoundException e) {
            LOGGER.error("Error on getting File object: " + e.getLocalizedMessage());
            System.exit(EXIT_ERROR);
        }

        ExecutorService service = null;
        XmlUtils xmlUtils = new JsefaXmlUtils(new File("existed.xml"));
        try {
            service = Executors.newFixedThreadPool(numThreads);
            Worker worker = new Worker(inputDir, outputDir, service, xmlUtils);

            for(;;) {
                worker.work();
                Thread.sleep(1000 * sleepTime);
            }
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted", e);
        } catch (ZipException e) {
            LOGGER.error("Zip error", e);
        } finally {
            service.shutdown();
        }
    }
}
