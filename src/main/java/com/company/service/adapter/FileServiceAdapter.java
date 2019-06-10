package com.company.service.adapter;

import com.company.domain.File;
import com.company.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract class FileServiceAdapter implements FileService {
    @Override
    public void upload(String name, String md5, MultipartFile file) throws Exception {

    }

    @Override
    public void uploadWithBlock(String name, String md5, Long size, Integer chunks, Integer chunk, MultipartFile file) throws Exception {

    }


}
