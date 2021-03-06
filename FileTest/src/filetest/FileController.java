package filetest;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileController {

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容  
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path 文件地址
     * @return true删除成功 false删除失败
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            //有分隔符直接删，没有分隔符加一个分隔符
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件  
                delFolder(path + "/" + tempList[i]);//再删除空文件夹  
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @param destDirName 文件地址
     * @return true创建成功 false创建失败
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录  
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    /**
     * 利用随机流在文件f的p处插入字符串s
     * @param f
     * @param p
     * @param s
     */
    public static void insert(File f, int p, String s) {
        RandomAccessFile in = null;
        try {
            in = new RandomAccessFile(f, "rw");
            in.seek(p);
            in.writeBytes(s);
            in.close();
        } catch (IOException e) {
        }
    }
}
