package com.company.service;

import com.company.domain.FileItem;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public interface FileOperationService {
    /**
     * 创建文件夹
     * @param path
     * @param filename
     */
    public void mkdir(String path,String filename);

    /**
     * 下载
     * @param path
     */
    public void download(HttpServletResponse response, String path);

    /**
     * 删除文件
     *
     * @param path
     */
    public boolean delete(String path);

    /**
     * 按照文件大小排序，从上到下
     * @param files
     * @return
     */
    public List<FileItem> sortFileBySizeUp(List<FileItem> files);

    /**
     * 按照文件大小排序，从下到上
     * @param files
     * @return
     */
    public List<FileItem> sortFileBySizeDown(List<FileItem> files);


    /**
     * 按照文件类型，文件夹到文件
     * @param files
     * @return
     */
    public List<FileItem> sortFileByTypeUp(List<FileItem> files);

    /**
     * 文件到文件夹
     * @param files
     * @return
     */
    public List<FileItem> sortFileByTypeDown(List<FileItem> files);


    /**
     * 按照文件时间从早到晚
     * @param files
     * @return
     */
    public List<FileItem> sortFileByDateUp(List<FileItem> files);

    /**
     * 从晚到早
     * @param files
     * @return
     */
    public List<FileItem> sortFileByDateDown(List<FileItem> files);


}
