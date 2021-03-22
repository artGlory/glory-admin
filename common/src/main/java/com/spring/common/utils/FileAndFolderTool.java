package com.spring.common.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件工具类
 *
 * @author Glory
 */
public class FileAndFolderTool {
    /**
     * 是一个存在的文件
     *
     * @param path
     * @return
     */
    public static boolean isExistFile(String path) {
        File file = new File(path);
        if (file.exists())
            if (!file.isDirectory())
                return true;
        return false;
    }

    /**
     * 是一个已经存在的文件夹
     *
     * @param path
     * @return
     */
    public static boolean isExistDirectory(String path) {
        File file = new File(path);
        if (file.exists())
            if (file.isDirectory())
                return true;
        return false;
    }

    /**
     * 通过路径名称创建文件夹或父类文件夹 创建文件夹的时候要以File.separatorChar结尾
     * 
     * @param path
     * @return
     */
    public static boolean makeDirectoryOrMatherDirectory(final String path) {
        if (!(path.contains("\\") || path.contains("/"))) {
            throw new IllegalArgumentException("exc：路径没有目录；" + path);
        }
        String path1 = path.replace("\\", String.valueOf(File.separatorChar));
        path1 = path1.replace("/", String.valueOf(File.separatorChar));
        int lastIndex = path1.lastIndexOf(File.separatorChar);
        if (lastIndex == 0) {
            return true;
        }
        String matherPath = path1.substring(0, lastIndex);
        File matherFile = new File(matherPath);
        if (matherFile.exists()) {
            if (!matherFile.isDirectory())
                return false;
        } else {
            matherFile.mkdirs();
        }
        return true;
    }

    /**
     * 通过文件名字返回文件存储名称 例如：123.txt=>2018/07/14/UUID/.txt
     *
     * @param name
     * @return
     */
    public static String getSubPath(String name) {
        if (!name.contains(".")) {
            throw new IllegalArgumentException("exc-文件格式错误-" + name);
        }
        String path1 = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
        String path2 = UUID.randomUUID().toString().replaceAll("-", "");
        String path3 = name.substring(name.lastIndexOf("."));
        return path1 + path2 + path3;
    }

    /**
     * 文件大小转换工具 参数为字节大小
     *
     * @param fileSize
     *            文件字节数
     * @return
     */
    public static String formetFileSize(long fileSize) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
