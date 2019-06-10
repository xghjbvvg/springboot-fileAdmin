package com.company.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileItem implements Serializable {
    private String name;
    private String parentPath;
    private String absolutePath;
    private Long dateMillis;
    private String date;
    private String size;
    private Long realSize;
    private Boolean isFolder;
}
