package xml;

import logic.FileItem;
import org.apache.log4j.Logger;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nik on 15.11.14.
 */
public class XStreamXmlUtils implements XmlUtils {

	private static Logger LOGGER = Logger.getLogger(XStreamXmlUtils.class);
	// Крутой сериализатор, не требующий аннотирования.
	private XStream xstream;

	private File xmlFile;
	private Path pathXmlFile;

	public XStreamXmlUtils(File xmlFile) {
		xstream = new XStream(new DomDriver());
		this.xmlFile = xmlFile;
		pathXmlFile = xmlFile.toPath();
	}

	@Override
	public void writeToXml(List<FileItem> items) {
		Writer writer=null;
		ObjectOutputStream out=null;
		try {
			xmlFile.delete();
			xmlFile.createNewFile();
			writer = Files.newBufferedWriter(pathXmlFile,
					Charset.forName("UTF8"), StandardOpenOption.WRITE);

			out = xstream.createObjectOutputStream(writer, "rootNodeName");

			// call serializer.write for every object to serialize
			for (FileItem item : items) {
				out.writeObject(item);
			}
		} catch (IOException e) {
			LOGGER.error("Some errors while writing List to xml file", e);
		}finally{
			try {
				out?.close();
				writer?.close();
			} catch (IOException e) {
				LOGGER.error("Some errors while closing resources", e);
			}
		}
	}

	@Override
	public List<FileItem> readFromXml() {
		Reader reader=null;
		ObjectInputStream ins=null;
		try {
			reader = Files.newBufferedReader(pathXmlFile,
					Charset.forName("utf8"));

			ins = xstream.createObjectInputStream(reader);
			List<FileItem> list = new ArrayList<FileItem>();
			
			try{
				while (true) {
					FileItem obj = (FileItem)ins.readObject();
					list.add(obj);
				}
			}catch(java.io.EOFException e){
				// EOF reached
			}

			LOGGER.info("Загружена информация о " + list.size()
					+ " ранее обработанных файлах");
			for (FileItem item : list) {
				LOGGER.debug(item);
			}
			return list;
		} catch (Exception e) {
			LOGGER.info("Не найдена информация о ранее архивированных файлах");
			return new ArrayList<FileItem>();
		}finally{
			try {
				ins?.close();
				reader?.close();
			} catch (IOException e) {
				LOGGER.error("Some errors while closing resources", e);
			}
		}

	}
}
