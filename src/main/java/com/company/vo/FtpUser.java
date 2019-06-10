package com.company.vo;

public class FtpUser {
    /**
     * 获取IP地址
     */
    private String ipAddress = "120.78.88.169";

    /**
     * 端口号
     */
    private Integer port = 21;

    /**
     * 用户名
     */
    private String username = "root";

    /**
     * 密码
     */
    private String password = "19971030Hcx";

    /**
     * 基本路径，用户图片
     */
    private String basePath = "/usr/local/webserver/nginx/html/images";

    /**
     * 下载地址地基础url，这个是配置的图片服务器的地址,最后访问图片时候，需要用该基础地址
     */
    private String baseUrl = "http://120.78.88.169:81/images/";

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "FtpUser{" +
                "ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", basePath='" + basePath + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
