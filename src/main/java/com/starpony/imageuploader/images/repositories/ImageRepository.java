package com.starpony.imageuploader.images.repositories;

import java.io.InputStream;


public interface ImageRepository {
    void save(String path, String filename, InputStream inputStream) ;
}
