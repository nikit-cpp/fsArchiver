package logic;

import xml.XmlUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nik on 06.11.14.
 */
public class FsPool {

    private Set<FileItem> existes = new HashSet<FileItem>();

    private XmlUtils xmlUtils;

    // Dependency injection через конструктор :)
    // В тестах сделаем фейковый XmlUtils
    public FsPool(XmlUtils xmlUtils){
        this.xmlUtils = xmlUtils;
        // Считываем данные из файла
        existes.addAll(this.xmlUtils.readFromXml());
    }

    public List<File> getNewFiles(File dir){
        Set<FileItem> current = new HashSet<FileItem>();

        for(File file: dir.listFiles()){
            current.add(new FileItem(file, file.lastModified()));
        }

        List<FileItem> preReturned = new ArrayList<FileItem>(current);

        preReturned.removeAll(existes);

        existes.addAll(current);

        List<File> returned = new ArrayList<File>();
        for(FileItem fi: preReturned){
            returned.add(fi.getFile());
        }

        // Записываем данные в файл
        xmlUtils.writeToXml(new ArrayList<FileItem>(existes));

        return returned;
    }
}

