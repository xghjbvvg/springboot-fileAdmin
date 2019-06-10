package com.company.service.adapter;

import com.company.domain.FileItem;
import com.company.service.FileFinderService;
import com.company.service.enumPackage.TypeName;
import com.company.service.impl.FileMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

public abstract class FileFinderServiceAdapter implements FileFinderService {


    /**
     * 根据路径及文件类型查找，应用find（）方法
     * @param path
     * @param typeName
     * @return
     */
    public List<FileItem> findFile(String path,TypeName typeName){
        List<FileItem> fileItemList = new ArrayList<>();
        return  find(fileItemList,new File(path),typeName);
    }
    @Autowired
    FileMenuServiceImpl fileMenuService;

    /**
     *根据路径及文件类型查找实际操作并把结果装入集合中
     * @param fileItemList
     * @param file
     * @param typeName
     * @return
     */
    public List<FileItem> find(List<FileItem> fileItemList,File file,TypeName typeName){
//        列举当前目录所有文件
        File[] files = file.listFiles();
        if(files == null || files.length == 0){
            return null;
        }
        for(File f : files ){
            if(f != null){
//                文件为目录是递归操作
                if(f.isDirectory()){
                    this.find(fileItemList,f,typeName);
                }else{
//                    文件名后缀
                    String[] split = f.getName().split("\\.");
                    String regex = null ;
//                    枚举匹配正则表达式
                    switch (typeName){
                        case IMAGE:{
                            regex = "gif|jpg|peg|bmp|png";
                            break;
                        }
                        case ZIP:{
                            regex = "zip|jar|rar|cab|iso|ace|7z|tar|gz|arj|lzh|uue|bz2|z";
                            break;
                        }
                        case DOCUMENT:{
                            regex = "doc|dot|docx|dotx|docm|dotm|xls|xlt|xla|xlsx|xltx|xlsm|xltm|xlam|xlsb|ppt|pot|pps|ppa|pptx|potx|ppsx|ppam|pptm|potm|ppsm|txt|md";
                            break;
                        }
                        case TORRENT:{
                            regex = "torrent";
                            break;
                        }
                        case MUSIC:{
                            regex = "mp3|midi|wma|vqf|amr";
                            break;
                        }
                        case VEDIO:{
                            regex = "mp4|flv|avi|rm|rmvb|mpeng1-4|mov|mtv|dat|wmv|3gb|amv|dmv|wmv";
                            break;
                        }
                        default:{
                            regex = "((?!mp3|midi|wma|vqf|amr|zip|jar|rar|cab|iso|ace|7z|tar|gz|arj|lzh|uue|bz2|z|torrent|gif|jpg|peg|bmp|png|mp4|flv|avi|rm|rmvb|mpeng1-4|mov|mtv|dat|wmv|3gb|amv|dmv|wmv|doc|dot|docx|dotx|docm|dotm|xls|xlt|xla|xlsx|xltx|xlsm|xltm|xlam|xlsb|ppt|pot|pps|ppa|pptx|potx|ppsx|ppam|pptm|potm|ppsm|txt|md).)*";
                            break;
                        }

                    }
                    if(split.length > 1){
                        boolean matches = Pattern.matches(regex, split[split.length-1]);
                        if (matches) {
                            fileItemList.add(fileMenuService.fileItem(f));
                        }
                    }
                }
            }
        }
        return fileItemList;
    }

    /**
     * 根据关键字查找
     * @param fileItemList
     * @param file
     * @param key
     * @return
     */
    public List<FileItem> findByKey(List<FileItem> fileItemList,File file,String key){
        File[] files = file.listFiles();
        if(files == null || files.length == 0){
            return null;
        }

        for(File f:files){
            if(f != null){
                if(f.getName().toLowerCase().contains(key.toLowerCase())){
                    fileItemList.add(fileMenuService.fileItem(f));
                }
                if(f.isDirectory()){
                    findByKey(fileItemList,f,key);
                }
            }

        }

        return fileItemList;
    }
}
