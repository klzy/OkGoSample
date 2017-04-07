package com.lzy.demo.okserver;

import com.lzy.okserver.download.DownloadInfo;

/**
 * Created by KLZY on .
 */

public class ApkEntity {

    private String name;
    private String icon;
    private String url;
    private DownloadInfo downInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DownloadInfo getDownInfo() {
        return downInfo;
    }

    public void setDownInfo(DownloadInfo downInfo) {
        this.downInfo = downInfo;
    }
}

