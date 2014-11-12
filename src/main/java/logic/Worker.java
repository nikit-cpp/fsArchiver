package logic;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private ExecutorService service;

    public Worker(File inputDir, File outputDir, ExecutorService service) {
        this.inputDir=inputDir;
        this.outputDir=outputDir;
        this.fsPool = new FsPool();
        this.service = service;
    }

    public void work() throws ZipException, InterruptedException{
        List<File> files = fsPool.getNewFiles(inputDir);
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
                    File outFile = convertInputFileToOutputFile(inputFile, outputDir);
                    ZipFile zipFile = new ZipFile(outFile);

                    if(inputFile.isDirectory()){
                        zipFile.addFolder(inputFile, parameters);
                    }else {
                        zipFile.addFile(inputFile, parameters);
                    }
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
