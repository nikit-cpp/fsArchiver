package logic;

import org.jsefa.xml.XmlDeserializer;
import org.jsefa.xml.XmlIOFactory;
import org.jsefa.xml.XmlSerializer;
import org.jsefa.xml.namespace.QName;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nik on 15.11.14.
 * https://github.com/oasits/JSefa/tree/master/samples/xml/yellow_pages/src/xml/yellowpages
 */
public class JsefaXmlUtils implements XmlUtils {

    private File xmlFile;
    private Path pathXmlFile;

    private XmlSerializer serializer;
    private XmlDeserializer deserializer;
    private Writer writer;
    private Reader reader;

    public JsefaXmlUtils(File xmlFile) {
        serializer = XmlIOFactory.createFactory(XmlFileItem.class).createSerializer();
        deserializer = XmlIOFactory.createFactory(XmlFileItem.class).createDeserializer();
        this.xmlFile = xmlFile;
        pathXmlFile = xmlFile.toPath();
    }

    @Override
    public void writeToXml(List<FileItem> items){
        try {
            xmlFile.delete();
            xmlFile.createNewFile();
            writer = Files.newBufferedWriter(pathXmlFile, Charset.forName("UTF8"), StandardOpenOption.WRITE);
        } catch (IOException e) {
            //e.printStackTrace();
        }

        serializer.open(writer);
        serializer.getLowLevelSerializer().writeXmlDeclaration("1.0", "UTF-8");
        serializer.getLowLevelSerializer().writeStartElement(QName.create("fileItems"));
        // call serializer.write for every object to serialize
        for(XmlFileItem item : convertToXml(items)){
            serializer.write(item);
        }
        serializer.getLowLevelSerializer().writeEndElement();

        serializer.close(true);
    }

    @Override
    public List<FileItem> readFromXml() {
        try {
            reader = Files.newBufferedReader(pathXmlFile);

            deserializer.open(reader);

            List<XmlFileItem> list = new ArrayList<XmlFileItem>();
            while (deserializer.hasNext()) {
                XmlFileItem p = deserializer.next();
                list.add(p);
            }

            deserializer.close(true);
            return convertToObj(list);
        } catch (IOException e) {
            return new ArrayList<FileItem>();
        }
    }

    private List<FileItem> convertToObj(List<XmlFileItem> list){
        List<FileItem> out = new ArrayList<FileItem>();
        for(XmlFileItem x: list){
            out.add(new FileItem(new File(x.getFile()), x.getDate()));
        }
        return out;
    }

    private List<XmlFileItem> convertToXml(List<FileItem> list){
        List<XmlFileItem> out = new ArrayList<XmlFileItem>();

        for(FileItem i: list){
            out.add(new XmlFileItem(i.getFile().getAbsolutePath(), i.getDate()));
        }
        return out;
    }
}
