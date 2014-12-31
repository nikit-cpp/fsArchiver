package xml;

import logic.FileItem;
import java.util.List;

/**
 * Created by nik on 15.11.14.
 */
public interface XmlUtils {
    /**
     * Если файл не существует, то создать его.
     * Если файл существует, то перезаписать его
     */
    void writeToXml(List<FileItem> items);

    /**
     * Если файла не существует, то вернуть пустой список
     * @return
     */
    List<FileItem> readFromXml();
}
