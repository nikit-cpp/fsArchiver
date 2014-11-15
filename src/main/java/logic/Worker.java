package logic;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.log4j.Logger;
import xml.XmlUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by nik on 09.11.14.
 * Содержит логику обработки входной директории.
 * Будет вызываться периодически каждые n секунд из другого класса.
 * Этот класс выделен для удобства тестировния.
 */
public class Worker {
    private File inputDir;
    private File outputDir;
    private FsPool fsPool;

    private static Logger LOGGER = Logger.getLogger(Runner.class);

    private ExecutorService service;

    public Worker(File inputDir, File outputDir, ExecutorService service, XmlUtils xmlUtils) {
        this.inputDir=inputDir;
        this.outputDir=outputDir;
        this.fsPool = new FsPool(xmlUtils);
        this.service = service;
    }

    public void work() throws ZipException, InterruptedException{
        List<File> files = fsPool.getNewFiles(inputDir);
        LOGGER.info("Найдено " + files.size() + " новых файлов");
        zip(files, outputDir);
    }

    // TODO Возможен перенос в отдельный класс при необходимости
    private void zip(final List<File> inputFilesList, final File outputDir) throws ZipException, InterruptedException {
        final ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        // МНОГОПОТОЧНОСТЬ
        List<Callable<Void>> processFileTasks = new ArrayList<Callable<Void>>();
        for(final File inputFile: inputFilesList){

            processFileTasks.add(new Callable<Void>() {
                public Void call() throws Exception {
                    LOGGER.info("'" + inputFile + "' обрабатывается...");
                    File outFile = convertInputFileToOutputFile(inputFile, outputDir);
                    ZipFile zipFile = new ZipFile(outFile);

                    if(inputFile.isDirectory()){
                        zipFile.addFolder(inputFile, parameters);
                    }else {
                        zipFile.addFile(inputFile, parameters);
                    }
                    LOGGER.info("'"+inputFile + "' -> '" + outFile + "' успешно");
                    return null;
                }
            });
        }

        service.invokeAll(processFileTasks);
    }

    /**
     * Прелобазует файловые объекты в Java, не работает с файловой системой.
     * @param inputFile
     * @param outputDir
     * @return
     */
    public static File convertInputFileToOutputFile(File inputFile, File outputDir){
        return new File(outputDir, inputFile.getName() + ".zip");
    }
}
