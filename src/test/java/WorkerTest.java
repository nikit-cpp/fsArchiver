import junit.framework.TestCase;
import logic.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class WorkerTest {

    File inputDir = new File("src/test/resources");
    File outputDir = new File("src/test/out-resources");

    @Before
    public void setUp() throws Exception {
        outputDir.delete();
        outputDir.mkdir();
    }

    @After
    public void tearDown() throws Exception {
        //outputDir.delete();
    }

    @Test
    public void testScenario() throws Exception {
        //create stub files in input directory(already exists, test/resources)
        //get list of files in input directory
        File[] listInput1 = inputDir.listFiles();
        //get count of files in input directory
        int count1 = listInput1.length;
        //create argument string

        Worker worker = new Worker(inputDir, outputDir);

        //call businnes logic function
        worker.work();
        //assertThat files in output directory present
        assertTrue(outputDir.listFiles().length!=0);
        //get count of files in output directory
        //get list of files in output directory
        //assertThat count of files in input directory == count of files in output directory
        //assertThat list of files in input directory equals list of files in output directory

        //call businnes logic function again
        //get count of files in output directory
        //get list of files in output directory
        //assertThat count of files in output directory is same as previous
        //assertThat list of files in output directory is same as previous

        //create new file in in input directory
        //get count of files in output directory
        //get list of files in output directory
        //call businnes logic function again
        //get count of files in output directory
        //get list of files in output directory
        //assertThat count of files in output directory is ++
        //assertThat list of files in output directory is ++
    }
}