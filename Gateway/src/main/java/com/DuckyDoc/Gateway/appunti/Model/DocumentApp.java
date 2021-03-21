package com.DuckyDoc.Gateway.appunti.Model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class DocumentApp implements Serializable {
    private MultipartFile file;
    private String doc;

    public DocumentApp(MultipartFile file, String doc) {
        this.file = file;
        this.doc = doc;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getDoc() {
        return doc;
    }
}
