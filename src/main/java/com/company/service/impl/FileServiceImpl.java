package com.company.service.impl;

import com.company.config.UploadConfig;
import com.company.dao.FileMapper;
import com.company.domain.File;
import com.company.exception.UploadException;
import com.company.service.adapter.FileServiceAdapter;
import com.company.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

import static com.company.util.FileUtils.generateFileName;
import static com.company.util.UploadUtils.*;
import static com.company.util.UploadUtils.removeKey;

@Service
public class FileServiceImpl extends FileServiceAdapter {

    @Autowired
    FileMapper fileMapper;


    /**
     * 上传文件
     * @param md5
     * @param file
     */
    @Override
    public void upload(String name,
                       String md5,
                       MultipartFile file) throws Exception {
        String[] split = name.split("\\.");
        String path = UploadConfig.path + generateFileName()+"."+split[1];
        try{
            FileUtils.write(path, file.getInputStream());
            fileMapper.save(new File(name, md5,path, new Date()));
        }catch(Exception e){
            System.out.println(e.getMessage());
            java.io.File f = new java.io.File(path);
            System.out.println(f.exists());
            if(f.exists()){
                f.delete();
            }
            throw new UploadException("上传出错");
        }

    }

    /**
     * 分块上传文件
     * @param md5
     * @param size
     * @param chunks
     * @param chunk
     * @param file
     * @throws IOException
     */
    @Override
    public void uploadWithBlock(String name,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws Exception {

        String[] split = name.split("\\.");
        String path = UploadConfig.path + generateFileName()+"."+split[1];
        String fileName = getFileName(md5, chunks)+"."+split[1];
        try{
            FileUtils.writeWithBlok(UploadConfig.path + name, size, file.getInputStream(), file.getSize(), chunks, chunk);
            addChunk(md5,chunk);
            if (isUploaded(md5)) {
                removeKey(md5);
                fileMapper.save(new File(name, md5,UploadConfig.path + fileName, new Date()));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            java.io.File f = new java.io.File(path);
            System.out.println(f.exists());
            if(f.exists()){
                f.delete();
            }
            throw new UploadException("上传出错");
        }
    }


    /**
     * 检查Md5判断文件是否已上传
     * false表示已经存在
     * true表示不存在
     * @param md5
     * @return
     */
    public boolean checkMd5(String md5) {
        File file = new File();
        file.setMd5(md5);
        return fileMapper.getByFile(file) == null;
    }
}
