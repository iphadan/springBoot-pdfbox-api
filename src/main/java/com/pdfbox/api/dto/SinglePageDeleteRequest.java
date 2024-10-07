package com.pdfbox.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SinglePageDeleteRequest {
    private String fileName;
    private int pageNumber;

}
