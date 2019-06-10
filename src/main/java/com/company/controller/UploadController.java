package com.company.controller;

import com.company.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class UploadController {

    @RequestMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        file.transferTo(new File("F://images//"+filename));
        return "success";
    }


    @Autowired
    FileServiceImpl fileService;

    @RequestMapping("/fileUpload")
    public void upload(String name,
                       String md5,
                       Long size,
                       Integer chunks,
                       Integer chunk,
                       String relativePath,
                       MultipartFile file) throws Exception {

        System.out.println(relativePath);
        if (chunks != null && chunks != 0) {
            fileService.uploadWithBlock(name, md5,size,chunks,chunk,file);
        } else {
            fileService.upload(name, md5,file);
        }
    }

    @RequestMapping("/checkIsExist")
    public Boolean checkIsExist(String md5){
       return  fileService.checkMd5(md5);
    }

}
