package com.lzy.demo.model;

import java.io.Serializable;
import java.util.List;

import static android.R.attr.author;
import static com.lzy.demo.R.id.des;
import static com.lzy.demo.R.id.upload;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/4/7
 * 描    述：我的Github地址  https://github.com/jeasonlzy
 * 修订历史：
 * ================================================
 */
public class FirstModel implements Serializable{
    private static final long serialVersionUID = -998322761336296999L;

    public class CoverInfo {
        public String id;
        public String name;
        public String cover;
        public String link;
    }

    public List<String> images;
    public List<CoverInfo> periods;
    public List<CoverInfo> syncourses;
    public List<CoverInfo> specialcourses;
    public List<CoverInfo> expandcourses;

    @Override
    public String toString() {
        return "FirstModel{" +
                "images='" + images.toString() + '\'' +
                ", expandcourses=" + expandcourses.toString() +
                '}';
    }
}
