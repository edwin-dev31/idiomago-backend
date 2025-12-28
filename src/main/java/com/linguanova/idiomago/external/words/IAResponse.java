package com.linguanova.idiomago.external.words;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IAResponse {
    private String word;
    private String example;
    private String description;
}
