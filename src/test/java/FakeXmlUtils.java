import logic.FileItem;
import logic.XmlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nik on 15.11.14.
 */
public class FakeXmlUtils implements XmlUtils {
    @Override
    public void writeToXml(List<FileItem> items) {

    }

    @Override
    public List<FileItem> readFromXml() {
        return new ArrayList<FileItem>();
    }
}
