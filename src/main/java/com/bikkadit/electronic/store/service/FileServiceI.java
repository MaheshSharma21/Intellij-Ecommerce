package com.bikkadit.electronic.store.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileServiceI {

    //user Image
    String uploadImage(MultipartFile file, String path) throws IOException;

    InputStream getResource(String path, String name) throws FileNotFoundException;

    //category Image
    String uploadCoverImage(MultipartFile file ,String path) throws IOException;

    InputStream getCoverImage(String path ,String name) throws FileNotFoundException;

    //Product Image

    String uploadProductImage(MultipartFile file ,String path) throws IOException;

    InputStream getProductImage(String path ,String name) throws FileNotFoundException;
}
