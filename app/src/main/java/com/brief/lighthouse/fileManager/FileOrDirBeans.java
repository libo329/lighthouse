package com.brief.lighthouse.fileManager;

public class FileOrDirBeans {
    String isFile, name, isCheckVisible, isChecked, absolutePath ;

    public FileOrDirBeans(String... params) {
        this.isFile = params[0];
        this.name = params[1];
        this.isCheckVisible = params[2];
        this.isChecked = params[3];
        this.absolutePath = params[4];
    }

    public String getIsFile() {
        return isFile;
    }

    public void setIsFile(String isFile) {
        this.isFile = isFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsCheckVisible() {
        return isCheckVisible;
    }

    public void setIsCheckVisible(String isCheckVisible) {
        this.isCheckVisible = isCheckVisible;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
