package com.starpony.imageuploader.web;

import com.starpony.imageuploader.web.dto.SavedImageDTO;
import com.starpony.imageuploader.images.ImagesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class MainController {
    private final static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final ImagesService imagesService;

    @Autowired
    public MainController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping(path = "/upload/{path}")
    public SavedImageDTO uploadImage(@PathVariable String path,
                                 @RequestParam MultipartFile image) throws IOException {

        LOGGER.info(String.format("[/upload/%s] Upload file %s", path, image.getOriginalFilename()));

        return new SavedImageDTO(imagesService.resizeAndSave(path, image.getInputStream()));
    }
}
