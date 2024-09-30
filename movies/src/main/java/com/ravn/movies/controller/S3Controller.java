package com.ravn.movies.controller;

import com.ravn.movies.dto.ImageResponseDTO;
import com.ravn.movies.dto.ImageUploadDTO;
import com.ravn.movies.service.IS3Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(S3Controller.BASE_CTRL_URL)
public class S3Controller {
    public static final String BASE_CTRL_URL = "api/v1/s3";
    private static final Logger logger = LoggerFactory.getLogger(S3Controller.class);
    private final IS3Service s3Service;

    @GetMapping("/images/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ImageResponseDTO getImageS3(@Valid @NotNull @PathVariable("id") String id) {
        logger.info("Getting image path from S3 imageId: " + id);
        return s3Service.getImage(id);
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ImageResponseDTO imageUploadDTO(@ModelAttribute ImageUploadDTO imageUploadDTO) {
        logger.info("uploadListingImage starting... filename {}", imageUploadDTO.getFilename());
        return s3Service.uploadImageProperties(imageUploadDTO);
    }

    @DeleteMapping(value = "/delete/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImageS3(@NotNull @PathVariable("imageId") String imageId) {
        logger.info("deleting starting... filename {}", imageId);
        s3Service.deleteImageProperties(imageId);
    }
}
