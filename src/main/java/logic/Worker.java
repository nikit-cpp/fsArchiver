package logic;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import java.io.File;
import java.util.List;

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

    public Worker(File inputDir, File outputDir){
        this.inputDir=inputDir;
        this.outputDir=outputDir;
        this.fsPool = new FsPool();
    }

    public void work() throws Exception{
        List<File> files = fsPool.getNewFiles(inputDir);
        zip(files, outputDir);
    }

    // TODO Возможен перенос в отдельный класс при необходимости
    private void zip(List<File> inputFilesList, File outputDir) throws ZipException {
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        for(File inputFile: inputFilesList) {
            File outFile = convertInputFileToOutputFile(inputFile, outputDir);
            ZipFile zipFile = new ZipFile(outFile);

            if(inputFile.isDirectory()){
                zipFile.addFolder(inputFile, parameters);
            }else {
                zipFile.addFile(inputFile, parameters);
            }
        }
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
