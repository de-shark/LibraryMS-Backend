package me.deshark.lms.domain.model.common;

import lombok.Getter;

import java.io.InputStream;

@Getter
public class FileData {
    private final String filename;
    private final String contentType;
    private final long size;
    private final InputStream inputStream;

    public FileData(String filename, String contentType, long size, InputStream inputStream) {
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.inputStream = inputStream;
    }
}