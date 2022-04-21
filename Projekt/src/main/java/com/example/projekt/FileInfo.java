package com.example.projekt;

public class FileInfo {
    private Integer size;
    private boolean isBlocked;

    public FileInfo(Integer size, boolean isBlocked) {
        this.size = size;
        this.isBlocked = isBlocked;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
