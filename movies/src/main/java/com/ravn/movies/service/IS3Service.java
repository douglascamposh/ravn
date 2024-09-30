package com.ravn.movies.service;

import com.ravn.movies.dto.ImageResponseDTO;
import com.ravn.movies.dto.ImageUploadDTO;

public interface IS3Service {
    ImageResponseDTO getImage(String imageId);
    ImageResponseDTO uploadImageProperties(ImageUploadDTO imageUploadDTO);
    void deleteImageProperties(String imageId);
}
