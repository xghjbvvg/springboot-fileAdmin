package com.company.service.impl;

import com.company.domain.FileItem;
import com.company.service.adapter.FileOperationServiceAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileOperationServiceImpl extends FileOperationServiceAdapter {


    private ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();

    /**
     * 创建文件夹
     *
     * @param path
     * @param filename
     */
    @Override
    public void mkdir(String path, String filename) {
        File file = new File(path + filename);
        file.mkdirs();
    }

    /**
     * 下载操作
     * @param response
     * @param path
     */
    @Override
    public void download(HttpServletResponse response, String path) {
        try {
            response.setCharacterEncoding("UTF-8");
            //response.setContentType("application/force-download");//应用程序强制下载
            File file = new File(path);
            long totleSize = file.length();
//如果文件不存在
            if (file == null || !file.exists()) {
                String msg = "文件不存在!";
                System.out.println(msg);
                PrintWriter out = response.getWriter();
                out.write(msg);
                out.flush();
                out.close();
                return;
            }
            String simpleName = file.getName().substring(file.getName().lastIndexOf("/") + 1);
            String newFileName = new String(simpleName.getBytes(), "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + newFileName);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[512];
            int length;
            System.out.println(totleSize);
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            long sum = 0;
            while ((length = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
                sum += length;
                String percent = decimalFormat.format(sum*1.0/totleSize);
                map.put(newFileName,percent);
            }
            if (bis != null){
                bis.close();
            }
            if (bos != null){
                bos.close();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查看下载百分比
     * @return
     */
    public String getDownloadPercent(String filename){
        System.out.println("percent:"+filename);
        return map.get(filename);
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    @Override
    public boolean delete(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                if (!f.delete()) {
                    System.out.println(f.getAbsolutePath() + " delete error!");
                    return false;
                }
            } else {
                if (!this.delete(f.getAbsolutePath())) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    /**
     * 从大到小
     *
     * @param files
     * @return
     */
    @Override
    public List<FileItem> sortFileBySizeUp(List<FileItem> files) {
        Collections.sort(files, (f1, f2) -> {
            try {
                if (f1.getRealSize() < f2.getRealSize()) {
                    return 1;
                } else if (f1.getRealSize() > f2.getRealSize()) {
                    return -1;
                } else {
                    return 0;
                }
            } catch (NumberFormatException e) {
                return 0;
            }
        });
        return files;
    }

    /**
     * 从小到大
     *
     * @param files
     * @return
     */
    @Override
    public List<FileItem> sortFileBySizeDown(List<FileItem> files) {
        Collections.sort(files, (f1, f2) -> {
            try {
                if (f1.getRealSize() > f2.getRealSize()) {
                    return 1;
                } else if (f1.getRealSize() < f2.getRealSize()) {
                    return -1;
                } else {
                    return 0;
                }
            } catch (NumberFormatException e) {
                return 0;
            }

        });
        return files;
    }

    @Override
    public List<FileItem> sortFileByTypeUp(List<FileItem> files) {
        return null;
    }

    @Override
    public List<FileItem> sortFileByTypeDown(List<FileItem> files) {
        return null;
    }

    /**
     * 文件按照日期排序（从晚到早
     *
     * @param files
     * @return
     */
    @Override
    public List<FileItem> sortFileByDateUp(List<FileItem> files) {
        Collections.sort(files, (f1, f2) -> {
            try {
                if (f1.getDateMillis() < f2.getDateMillis()) {
                    return 1;
                } else if (f1.getDateMillis() > f2.getDateMillis()) {
                    return -1;
                } else {
                    return 0;
                }
            } catch (NumberFormatException e) {
                return 0;
            }

        });
        return files;
    }

    /**
     * 文件按照日期排序（从早到晚
     *
     * @param files
     * @return
     */
    @Override
    public List<FileItem> sortFileByDateDown(List<FileItem> files) {
        Collections.sort(files, (f1, f2) -> {
            try {
                if (f1.getDateMillis() > f2.getDateMillis()) {
                    return 1;
                } else if (f1.getDateMillis() < f2.getDateMillis()) {
                    return -1;
                } else {
                    return 0;
                }
            } catch (NumberFormatException e) {
                return 0;
            }

        });
        return files;
    }
}
