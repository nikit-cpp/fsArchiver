package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nik on 06.11.14.
 */
public class FsPool {

    private Set<FileItem> existes=new HashSet<FileItem>();

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

        return returned;
    }

}

/**
 * Содержит переопределённые hashCode() и equals(),
 * в которых участвует дата последнего изменения.
 * Т. о. мы можем использовать инстансы FileItem
 * в Collection.removeAll()
 */
class FileItem{
    private File file;
    private long date;

    public FileItem(File file, long date) {
        this.file = file;
        this.date = date;
    }

    public File getFile() {
        return file;
    }

    public long getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileItem)) return false;

        FileItem fileItem = (FileItem) o;

        if (date != fileItem.date) return false;
        if (file != null ? !file.equals(fileItem.file) : fileItem.file != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = file != null ? file.hashCode() : 0;
        result = 31 * result + (int) (date ^ (date >>> 32));
        return result;
    }
}