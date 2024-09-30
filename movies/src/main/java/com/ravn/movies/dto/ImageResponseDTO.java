package com.ravn.movies.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageResponseDTO {
    private String imageId;
    private String url;
}
