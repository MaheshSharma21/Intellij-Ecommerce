package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.exceptions.BadRequestApiException;
import com.bikkadit.electronic.store.service.FileServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileServiceI {
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {

        log.info(" Request Starting for  upload image  ");
        //get original filename
        String originalFilename = file.getOriginalFilename();
        log.info("FileName :{}", originalFilename);

        //random Image name generate
        String randomFileName = UUID.randomUUID().toString();

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = randomFileName + extension;

        //full path
        String fullPathWithFileName = path + fileNameWithExtension;

        log.info(" full image path :{}", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            log.info(" file extension :{}", extension);
            //file Save

            File f = new File(path);

            if (!f.exists()) {
                //folder creation up to multiple level
                f.mkdirs();
            }
            //upload Image
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

            return fileNameWithExtension;
        } else {
            throw new BadRequestApiException(" File with this " + extension + " not allowed ...");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        log.info(" Request Starting to serve image ");
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

    @Override
    public String uploadcoverImage(MultipartFile file, String path) throws IOException {
        log.info(" Request Starting for  upload coverImage  ");
        //get original filename
        String originalFilename = file.getOriginalFilename();
        log.info("FileName :{}", originalFilename);

        //random coverImage name generate
        String randomFileName = UUID.randomUUID().toString();

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = randomFileName + extension;

        //full path
        String fullPathWithFileName = path + fileNameWithExtension;

        log.info(" full image path :{}", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            log.info(" file extension :{}", extension);
            //file Save
            File f = new File(path);

            if (!f.exists()) {
                //folder creation up to multiple level
                f.mkdirs();
            }
            //upload coverImage
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

            return fileNameWithExtension;
        } else {
            throw new BadRequestApiException(" File with this " + extension + " not allowed ...");
        }

    }

    @Override
    public InputStream getcoverImage(String path, String name) throws FileNotFoundException {

        log.info(" Request Starting to serve coverImage ");
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;

    }
}
