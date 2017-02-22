import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by schauhan on 2/22/17.
 */
public class HdfsDemo {
    public static void main(String[] args) throws IOException {

        Configuration conf = new Configuration();
        conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
        conf.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));
        FileSystem fs = FileSystem.get(conf);
        System.out.println("Enter the directory name : ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Path path = new Path(br.readLine());
        displayDirectoryContents(fs, path);
        fs.close();
    }

    private static void displayDirectoryContents(FileSystem fs, Path rootDir) {
        // TODO Auto-generated method stub
        try {

            FileStatus[] status = fs.listStatus(rootDir);
            for (FileStatus file : status) {
                long epoch = file.getModificationTime();
                Date lastmodification = new Date( epoch * 1000 );

                if (file.isDir()) {
                    System.out.println("DIRECTORY : " + file.getPath() + " - Last modification time : " + lastmodification);
                    displayDirectoryContents(fs, file.getPath());
                } else {
                    System.out.println("FILE : " + file.getPath() + " - Last modification time : " + lastmodification);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
