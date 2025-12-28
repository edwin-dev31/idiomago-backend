package com.linguanova.idiomago.external.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CloudinaryResponse {
    private final String imageUrl;
    private final String publicId;
}
