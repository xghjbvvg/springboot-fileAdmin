package com.company.service.adapter;

import com.company.domain.FileItem;
import com.company.service.FileMenuService;
import com.company.service.FileOperationService;
import com.company.service.enumPackage.SortType;
import com.company.service.impl.FileMenuServiceImpl;
import javafx.scene.control.TableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public abstract class FileOperationServiceAdapter implements FileOperationService {
    @Override
    public void download(HttpServletResponse response, String path)  {

    }
    @Autowired
    FileMenuServiceImpl fileMenuService;

    /**
     * 文件排序，调用sort（）
     * @param path
     * @param sortType
     * @return
     */
    public List<FileItem> sortBySortType(String path, SortType sortType){
        File file = new File(path);
        List<FileItem> fileItemList = new ArrayList<>();
        List<FileItem> fileTItems = fileMenuService.getAllFileTItems(path);
        return sort(fileTItems,sortType);
    }

    /**
     * 排序实际操作
     * @param files
     * @param sortType
     * @return
     */
    private List<FileItem> sort(List<FileItem> files, SortType sortType) {
        switch(sortType){
            case FILETYPEUP:{
                return sortFileByTypeUp(files);
            }
            case FILETYPEDOWN:{
                return sortFileByTypeDown(files);
            }
            case FILESIZEUP:
                return sortFileBySizeUp(files);
            case FILESIZEDOWN:
                return sortFileBySizeDown(files);
            case FILEDATEUP:
                return sortFileByDateUp(files);
            case FILEDATEDOWN:
                return sortFileByDateDown(files);
            default:
                return null;
        }
    }


}
