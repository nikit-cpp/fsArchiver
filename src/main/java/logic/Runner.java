package logic;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nik on 05.11.14.
 */
public class Runner {
    static String filesDir = "/home/nik/images";

    public static void main(String... args) throws ZipException {
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression

        // Set the compression level. This value has to be in between 0 to 9
        // Several predefined compression levels are available
        // DEFLATE_LEVEL_FASTEST - Lowest compression level but higher speed of compression
        // DEFLATE_LEVEL_FAST - Low compression level but higher speed of compression
        // DEFLATE_LEVEL_NORMAL - Optimal balance between compression level/speed
        // DEFLATE_LEVEL_MAXIMUM - High compression level with a compromise of speed
        // DEFLATE_LEVEL_ULTRA - Highest compression level but low speed
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        File dir = new File(filesDir);

        for(File file: dir.listFiles()){
            ZipFile zipFile = new ZipFile(file.getAbsoluteFile() + ".zip");
            //file.lastModified();
            if(file.isDirectory()){
                zipFile.addFolder(file, parameters);
            }else {
                zipFile.addFile(file, parameters);
            }
        }
    }
}
