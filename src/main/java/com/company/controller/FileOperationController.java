package com.company.controller;


import com.company.domain.FileItem;
import com.company.service.FileFinderService;
import com.company.service.FileOperationService;
import com.company.service.enumPackage.SortType;
import com.company.service.impl.FileOperationServiceImpl;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class FileOperationController {

    @Autowired
    FileOperationServiceImpl fileOperationService;

    @Autowired
    FileFinderService fileFinderService;

    /**
     * 文件下载
     *
     * @param filePath
     * @return
     * @throws IOException
     */
//    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletResponse response,String filePath) throws IOException {
//        获取文件名
        String[] split = filePath.split("/");
        String fileName = split[split.length-1];
        System.out.println(fileName);
//        定义请求头并封装
        HttpHeaders headers = new HttpHeaders();
        File file = new File(filePath);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    @RequestMapping("/download")
    public void download2(HttpServletResponse response,String filePath) throws IOException {
        fileOperationService.download(response,filePath);
    }

    @RequestMapping("/getDownloadPercent")
    public String getDownloadPercent(String filename){
        return fileOperationService.getDownloadPercent(filename);
    }

    /**
     * 创建文件夹
     * @param path
     * @param fileFolderName
     */
    @RequestMapping("/mkdir")
    public void mkdir(String path,String fileFolderName){
        System.out.println(path+fileFolderName);
        fileOperationService.mkdir(path,fileFolderName);
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    @RequestMapping("/delete")
    public boolean delete(String path){
        return fileOperationService.delete(path);
    }

    /**
     * 根据文件类型
     * 1.图片  2.音乐  3.视频
     * 4.种子  5.压缩  6.文档  7.其他
     * @param num
     * @param path
     * @return
     */
    @RequestMapping("/findFileByType")
    public List<FileItem> findFileByType(int num,String path){
        switch (num){
            case 1:{
                return fileFinderService.findFileByImg(path);
            }
            case 2:{
               return fileFinderService.findFileByMusic(path);
            }
            case 3:{
                return fileFinderService.findFileByVedio(path);
            }
            case 4:{
                return fileFinderService.findFileByTorrent(path);
            }
            case 5:{
                return fileFinderService.findFileByZip(path);
            }
            case 6:{
                return fileFinderService.findFileByDoc(path);
            }
            case 7:{
                return fileFinderService.findFileByOthers(path);
            }

        }
        return null;
    }

    /**
     * 文件排序（文件大小从大到小）
     * @param path
     * @return
     */
    @RequestMapping("/sortFileBySizeUp")
    public List<FileItem> sortFileBySizeUp(String path){
       return fileOperationService.sortBySortType(path, SortType.FILESIZEUP);
    }

    @RequestMapping("/sortFileBySizeDown")
    public List<FileItem> sortFileBySizeDown(String path){
       return fileOperationService.sortBySortType(path, SortType.FILESIZEDOWN);
    }

    @RequestMapping("/sortFileByDateUp")
    public List<FileItem> sortFileByDateUp(String path){
        return fileOperationService.sortBySortType(path, SortType.FILEDATEUP);
    }

    @RequestMapping("/sortFileByDateDown")
    public List<FileItem> sortFileByDateDown(String path){
        return fileOperationService.sortBySortType(path, SortType.FILEDATEDOWN);
    }

    /**
     * 关键字搜索
     * @param path
     * @param keyword
     * @return
     */
    @RequestMapping("/findFileByKey")
    public List<FileItem> findFileByKey(String path,String keyword){
        return fileFinderService.findFilesByKeyword(path, keyword);
    }
}
