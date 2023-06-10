package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.exceptions.BadRequestApiException;
import com.bikkadit.electronic.store.service.FileServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
        String fileNamewithExtension = randomFileName + extension;

        //full path
        String fullPathwithfileName = path  + fileNamewithExtension;

        log.info(" full image path :{}",fullPathwithfileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            log.info(" file extension :{}",extension);
            //file Save

            File f = new File(path);

            if (!f.exists()) {
                //folder creation upto multiple level
                f.mkdirs();
            }
            //upload Image
            Files.copy(file.getInputStream(), Paths.get(fullPathwithfileName));

            return fileNamewithExtension;
        } else {
            throw new BadRequestApiException(" File with this " + extension + " not allowed ...");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        log.info(" Request Starting to serve image ");
        String fullpath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;
    }
}
