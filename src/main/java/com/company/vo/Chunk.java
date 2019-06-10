package com.company.vo;

import lombok.Data;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class Chunk implements Serializable {
    private Integer chunkNumber;
    private Long chunkSize;
    private Long currentChunkSize;
    private Long totalSize;
    private String identifier;
    private String filename;
    private String relativePath;
    private Integer totalChunks;
    private MultipartFile file;
}
