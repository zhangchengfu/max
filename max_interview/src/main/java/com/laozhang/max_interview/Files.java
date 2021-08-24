package com.laozhang.max_interview;

import java.io.File;

public class Files {

    /**
     * 参数名称
     */
    private String name;

    /**
     * 文件
     */
    private File file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Files(String name, File file) {
        this.name = name;
        this.file = file;
    }
}
