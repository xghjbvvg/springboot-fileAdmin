package com.company.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    /**
     * 上传文件（不分块）
     * @param name
     * @param md5
     * @param file
     * @throws IOException
     */
    public void upload(String name, String md5, MultipartFile file) throws Exception;

    /**
     * 上传文件（分块）
     * @param name
     * @param md5
     * @param size
     * @param chunks
     * @param chunk
     * @param file
     */
    public void uploadWithBlock(String name, String md5, Long size, Integer chunks, Integer chunk, MultipartFile file) throws Exception;

    }
