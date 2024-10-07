package com.pdfbox.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RangeSplitRequest {
    private String fileName;
    private int start;
    private int end;
}
