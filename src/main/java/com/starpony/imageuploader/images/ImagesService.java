package com.starpony.imageuploader.images;

import com.starpony.imageuploader.Configuration;
import com.starpony.imageuploader.images.exceptions.InvalidPathException;
import com.starpony.imageuploader.images.models.ImageFormat;
import com.starpony.imageuploader.images.repositories.ImageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;


@Service
public class ImagesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesService.class);

    private final Configuration configuration;
    private final ImageRepository imageRepository;

    @Autowired
    public ImagesService(
            Configuration configuration,
            ImageRepository imageRepository) {
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
        return ImageUtils.processImage(imageStream, getPathImageFormat(path));
    }

    private String save(String path, InputStream imageStream) {
        ImageFormat imageFormat = getPathImageFormat(path);
        String filename = String.format("%s.%s", UUID.randomUUID(), imageFormat.getType());

        imageRepository.save(path, filename, imageStream);

        return String.format("/%s/%s", path, filename);
    }

    private ImageFormat getPathImageFormat(String path) {
        return configuration.getFormat(path).orElseThrow(
                () -> new InvalidPathException(String.format("Path \"%s\" not found", path)));
    }
}
