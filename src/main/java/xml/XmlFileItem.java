package xml;

import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

import java.io.File;

/**
 * Содержит переопределённые hashCode() и equals(),
 * в которых участвует дата последнего изменения.
 * Т. о. мы можем использовать инстансы FileItem
 * в Collection.removeAll()
 */
@XmlDataType(defaultElementName = "fileItem")
public class XmlFileItem {
    @XmlElement(name = "absoluteFile")
    private String file;
    @XmlElement
    private long date;

    public XmlFileItem(){};

    public XmlFileItem(String file, long date) {
        this.file = file;
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "FileItem{" +
                "file=" + file +
                ", date=" + date +
                '}';
    }
}
