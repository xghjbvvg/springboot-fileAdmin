package com.company.controller;

import com.company.domain.FileItem;
import com.company.service.FileMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileController {

    @Autowired
    FileMenuService fileMenuService;

    /**
     * 获取制定目录下文件
     * @param path
     * @return
     */
    @RequestMapping("/getMenuByPath")
    public List<FileItem> getFileMenu(String path){
        System.out.println(path);
        return fileMenuService.getFileMenu(path);
    }

    /**
     * 获取主目录文件
     * @return
     */
    @RequestMapping("/getMainMenu")
    public List<FileItem> getMainMenu(){
        return fileMenuService.getMainMenu();
    }
}
