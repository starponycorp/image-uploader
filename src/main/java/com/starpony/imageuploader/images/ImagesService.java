package com.starpony.imageuploader.images;

import com.starpony.imageuploader.Configuration;
import com.starpony.imageuploader.images.repositories.ImageRepository;

import com.starpony.imageuploader.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;


@Service
public class ImagesService {
    private final Configuration configuration;
    private final ImageRepository imageRepository;

    @Autowired
    public ImagesService(Configuration configuration, ImageRepository imageRepository) {
        this.configuration = configuration;
        this.imageRepository = imageRepository;
    }

    /**
     * Изменяет разрешение входного изображения и сохраняет
     * @param path Путь по которому будет сохранено изображение
     * @param imageStream стрим входного изображения
     * @return URL сохраненного изображения
     */
    public String resizeAndSave(String path, InputStream imageStream) {
        return save(path, resize(path, imageStream));
    }

    private InputStream resize(String path, InputStream imageStream) {
        Configuration.ImageFormat imageFormat = configuration.getFormat(path);
        try {
            return ImageUtils.resize(imageStream, imageFormat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String save(String path, InputStream imageStream) {
        Configuration.ImageFormat imageFormat = configuration.getFormat(path);
        String filename = String.format("%s.%s", UUID.randomUUID(), imageFormat.getType());
        try {
            imageRepository.save(path, filename, imageStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return String.format("/%s/%s", path, filename);
    }
}
