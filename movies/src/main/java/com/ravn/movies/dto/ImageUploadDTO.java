package com.ravn.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ImageUploadDTO {
    MultipartFile image;
    private String filename;
}
