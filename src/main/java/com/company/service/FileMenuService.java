package com.company.service;

import com.company.domain.FileItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileMenuService {
    /**
     * 根据目录获取文件目录
     * @param path
     * @return
     */
    public List<FileItem> getFileMenu(String path);

    /**
     * 获取主目录
     * @return
     */
    public List<FileItem> getMainMenu();
}
