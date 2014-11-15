package logic;

import java.io.File;

/**
 * Содержит переопределённые hashCode() и equals(),
 * в которых участвует дата последнего изменения.
 * Т. о. мы можем использовать инстансы FileItem
 * в Collection.removeAll()
 */
public class FileItem{
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

    @Override
    public String toString() {
        return "FileItem{" +
                "file=" + file +
                ", date=" + date +
                '}';
    }
}
