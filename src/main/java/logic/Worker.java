package logic;

import java.io.File;
import java.util.List;

/**
 * Created by nik on 09.11.14.
 * Содержит логику обработки входной директории.
 * Будет вызываться периодически каждые n секунд из другого класса
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

    public void work(){
        List<File> files = fsPool.getNewFiles(inputDir);
    }
}
