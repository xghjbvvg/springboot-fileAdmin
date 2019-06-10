package com.company.util;

import com.company.vo.FtpUser;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class FtpUpload {

    public static String upload(String filename, String id, InputStream inputStream) {
        FtpUser ftpUser = new FtpUser();
        FTPClient ftp = new FTPClient();

        //连接ftp
        try {
            ftp.connect(ftpUser.getIpAddress(), ftpUser.getPort());
            // 登入
            ftp.login(ftpUser.getUsername(), ftpUser.getPassword());
            int reply = ftp.getReplyCode();
            System.out.println(reply);
            //判断是否登入成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                //没有则断开连接
                ftp.disconnect();
                return null;
            }
            Calendar calendar = Calendar.getInstance();
//        文件目录
            String basePath = ftpUser.getBasePath();
            String filePath =   "upload" + "/" + id + "/" +calendar.get(Calendar.YEAR ) + "/" +(calendar.get(Calendar.MONTH )+1) + "/" +calendar.get(Calendar.DAY_OF_MONTH) ;
            //判断是否能够修改当前工作目录，不能的话，创建该目录
            System.out.println(filePath);
            System.out.println(ftp.changeWorkingDirectory(basePath));
            boolean mkdir = mkdir(ftp, basePath, filePath);
            if(mkdir){
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();// 这个设置允许被动连接--访问远程ftp时需要
                // 上传文件
//                ftp.sto
//                ftp.s
                if (!ftp.storeFile(filename, inputStream)) {
                    return null;
                }
                inputStream.close();
                ftp.logout();
            }
            System.out.println(ftpUser.getBaseUrl()+filePath);
            return ftpUser.getBaseUrl()+filePath+"/"+filename;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }finally{
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }

    }


    public static boolean mkdir(FTPClient ftp,String basePath,String filePath) throws IOException {
        if (!ftp.changeWorkingDirectory(basePath+filePath)) {
            // 如果目录不存在，创建目录
            String[] dirs = filePath.split("\\/");
            String tempPath = basePath;
            for (String dir : dirs) {
                if (dir == null || "".equals(dir)) {
                    continue;
                }
                tempPath += "/" + dir;
                System.out.println("currentPath"+tempPath);
                if (!ftp.changeWorkingDirectory(tempPath)) {
                    boolean b = ftp.makeDirectory(tempPath);
                    System.out.println(b);
                    if (!b) {
                        return false;
                    } else {
                        ftp.changeWorkingDirectory(tempPath);
                    }
                }
            }
        }
        return true;
    }
}
