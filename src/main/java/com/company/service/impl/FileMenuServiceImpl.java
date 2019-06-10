package com.company.service.impl;

import com.company.domain.FileItem;
import com.company.service.adapter.FileMenuServiceAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileMenuServiceImpl extends FileMenuServiceAdapter {

    @Override
    public List<FileItem> getFileMenu(String path) {
        return super.getAllFileTItems(path);
    }

    @Override
    public List<FileItem> getMainMenu() {
        return super.getAllFileTItems(basePath);
    }
}
