package com.company.service.impl;

import com.company.domain.FileItem;
import com.company.service.adapter.FileFinderServiceAdapter;
import com.company.service.enumPackage.TypeName;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Service
public class FileFinderServiceImpl extends FileFinderServiceAdapter {
    @Override
    public List<FileItem> findFileByImg(String path) {
        return findFile(path, TypeName.IMAGE);
    }

    @Override
    public List<FileItem> findFileByZip(String path) {
        return findFile(path, TypeName.ZIP);
    }

    @Override
    public List<FileItem> findFileByDoc(String path) {
        return findFile(path, TypeName.DOCUMENT);
    }

    @Override
    public List<FileItem> findFileByTorrent(String path) {
        return findFile(path, TypeName.TORRENT);
    }

    @Override
    public List<FileItem> findFileByVedio(String path) {
       return findFile(path, TypeName.VEDIO);
    }

    @Override
    public List<FileItem> findFileByMusic(String path) {
        return findFile(path, TypeName.MUSIC);
    }

    @Override
    public List<FileItem> findFileByOthers(String path) {
        return findFile(path, TypeName.OTHERS);
    }

    /**
     * 关键字查找
     * @param path
     * @param keyword
     * @return
     */
    @Override
    public List<FileItem> findFilesByKeyword(String path, String keyword) {
        return findByKey(new ArrayList<FileItem>(),new File(path),keyword);
    }
}
