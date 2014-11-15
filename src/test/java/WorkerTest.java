import junitx.framework.ListAssert;
import logic.Worker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xml.FakeXmlUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

public class WorkerTest {

    File inputDir = new File("src/test/resources");
    File outputDir = new File("src/test/out-resources");
    ExecutorService service;

    @Before
    public void setUp() throws Exception {
        service = Executors.newFixedThreadPool(4);
        //outputDir.delete();
        outputDir.mkdir();
    }

    @After
    public void tearDown() throws Exception {
        //outputDir.delete();
        service.shutdown();
    }

    @Test
    public void testScenario() throws Exception {
        //create stub files in input directory(already exists, test/resources)
        //get list of files in input directory
        File[] listInput1 = inputDir.listFiles();
        //get count of files in input directory
        int count1 = listInput1.length;
        //create argument string

        Worker worker = new Worker(inputDir, outputDir, service, new FakeXmlUtils());

        //call businnes logic function
        worker.work();
        //get list of files in output directory
        File[] listOutput1 = outputDir.listFiles();
        //get count of files in output directory
        int count2 = listOutput1.length;
        //assertThat files in output directory are present
        Assert.assertTrue(count2 != 0);
        //assertThat count of files in input directory == count of files in output directory
        Assert.assertEquals(count1, count2);
        //assertThat list of files in input directory equals list of files in output directory
        assertInputOutputFileEquals(listInput1, listOutput1);

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

    public void assertInputOutputFileEquals(File[] input, File[] output){
        List<File> causedOutList = new ArrayList<File>();
        for(File i: input) {
            File causedOutFile = Worker.convertInputFileToOutputFile(i, outputDir);
            causedOutList.add(causedOutFile);
        }
        List<File> expectedOutList = Arrays.asList(output);
        // Сравнивает списки, игнорируя порядок
        ListAssert.assertEquals(expectedOutList, causedOutList);
    }
}