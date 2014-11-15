import logic.FileItem;
import xml.JsefaXmlUtils;
import xml.XmlUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlUtilsTest {

    File xmlFile;

    XmlUtils xmlUtils;

    @Before
    public void setUp() throws Exception {
        xmlFile = new File("src/test/resources/xml.xml");
        xmlUtils = new JsefaXmlUtils(xmlFile);
        xmlFile.delete();
    }

    @After
    public void tearDown() throws Exception {
        xmlFile.delete();
    }

    @Test
    public void testWriteToXmlFile() throws Exception {
        List<FileItem> list = new ArrayList<FileItem>();

        FileItem fi1 = new FileItem(new File("file1"), 2);
        FileItem fi2 = new FileItem(new File("file2"), 3);

        list.add(fi1);
        list.add(fi2);

        xmlUtils.writeToXml(list);

        List<FileItem> caused = xmlUtils.readFromXml();

        Assert.assertEquals(list.get(0).getFile().getName(), caused.get(0).getFile().getName());
        Assert.assertEquals(list.get(0).getDate(), caused.get(0).getDate());

        Assert.assertEquals(list.get(1).getFile().getName(), caused.get(1).getFile().getName());
        Assert.assertEquals(list.get(1).getDate(), caused.get(1).getDate());
    }

    /**
     * Тестируем "Если файла не существует, то вернуть пустой список"
     * @throws Exception
     */
    @Test
    public void testReadFromXmlFileNotExisting() throws Exception {
        xmlFile.delete();
        List<FileItem> list = xmlUtils.readFromXml();
        Assert.assertTrue(list.size()==0);
    }
}