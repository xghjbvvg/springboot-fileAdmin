package com.company.service.adapter;

import com.company.domain.FileItem;
import com.company.service.FileMenuService;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class FileMenuServiceAdapter implements FileMenuService {

    @Value("${upload.path}")
    public String  basePath;
    @Value("${upload.environment")
    public String environment;

    @Override
    public List<FileItem> getFileMenu(String path) {
        return null;
    }

    @Override
    public List<FileItem> getMainMenu() {
        return null;
    }


    /**
     * 封装目录所有文件
     * @param path
     * @return
     */
    public List<FileItem> getAllFileTItems(String path){
        File file = new File(path);
        File[] list = file.listFiles();
        List<FileItem> allFile = new ArrayList<FileItem>();
        if(list.length != 0){
            for (File f : list) {
                FileItem fileItem = fileItem(f);
                allFile.add(fileItem);
            }
        }
        return allFile;
    }

    /**
     * 封装fileItem
     * @param f
     * @return
     */
    public FileItem fileItem(File f){
        FileItem fileItem= new FileItem();
        //名字
        fileItem.setName(f.getName());
        //绝对路径
        int length = basePath.length();
        String uri = f.toURI().getPath();
        String split = null;
        if (environment.equals("win")){
            split = uri.substring(length+1, uri.length());

        }else{
            split = uri.substring(length, uri.length());
        }
//        System.out.println(split);
        fileItem.setAbsolutePath(split);
        //父路径
        fileItem.setParentPath(basePath);
        //修改时间
        fileItem.setDateMillis(f.lastModified());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String format = dateFormat.format(f.lastModified());
        fileItem.setDate(format);
        //大小
        fileItem.setRealSize(f.length());
        String size = null;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (f.length() < 1024) {
            size = decimalFormat.format(f.length()) + "BT";
        } else if (f.length() < 1048576) {
            size = decimalFormat.format((double) f.length() / 1024) + "KB";
        } else if (f.length() < 1073741824) {
            size = decimalFormat.format((double) f.length() / 1048576) + "MB";
        } else {
            size = decimalFormat.format((double) f.length() / 1073741824) +"GB";
        }

        //判断是否为目录
        boolean directory = f.isDirectory();
        if(directory){
            size = "-";
        }
        fileItem.setSize(size);
        fileItem.setIsFolder(directory);
//        System.out.println(fileItem);
        return fileItem;
    }
}
