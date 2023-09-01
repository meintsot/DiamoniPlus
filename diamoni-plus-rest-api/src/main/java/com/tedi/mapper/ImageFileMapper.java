package com.tedi.mapper;

import com.tedi.dto.ImageFileType;
import com.tedi.model.ImageFile;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class ImageFileMapper {

    public ImageFileType toImageFileType(ImageFile imageFile) {

        ImageFileType imageFileType = new ImageFileType();
        imageFileType.setBinaryIdentification(imageFile.getBinaryIdentification());
        imageFileType.setData(Base64.getEncoder().encodeToString(imageFile.getData()));
        imageFileType.setFilename(imageFile.getFilename());
        imageFileType.setMime(imageFile.getMime());

        return imageFileType;
    }

    public ImageFile toImageFile(ImageFileType imageFileType) {
        ImageFile imageFile = new ImageFile();

        imageFile.setBinaryIdentification(Objects.nonNull(imageFileType.getBinaryIdentification()) ?
                imageFileType.getBinaryIdentification() : UUID.randomUUID().toString());
        imageFile.setData(Base64.getDecoder().decode(imageFileType.getData()));
        imageFile.setFilename(imageFileType.getFilename());
        imageFile.setMime(imageFileType.getMime());
        imageFile.setCreatedAt(LocalDateTime.now());
        imageFile.setUpdatedAt(LocalDateTime.now());

        return imageFile;
    }
}
