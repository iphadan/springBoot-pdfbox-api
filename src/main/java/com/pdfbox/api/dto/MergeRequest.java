package com.pdfbox.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MergeRequest {
    private String fileName1;
    private String fileName;
    private String fileName2;
    private String fileName3;
}
