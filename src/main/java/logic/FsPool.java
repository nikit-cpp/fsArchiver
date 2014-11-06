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

    private Set<File> existes=new HashSet<File>();

    public List<File> getNewFiles(File dir){
        Set<File> current = new HashSet<File>();

        for(File file: dir.listFiles()){
            current.add(file);
        }

        List<File> returned = new ArrayList<File>(current);

        //returned.removeAll(existes);

        for(int i=0; i<returned.size(); ){
            if(existes.contains(returned.get(i))){
                // TODO если в элементе current дата новее
                returned.remove(i);
                continue;
            }
            ++i;
        }

        existes.addAll(current);

        return returned;
    }

}
