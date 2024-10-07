package com.pdfbox.api.services.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    @Value("${spring.application.file.create_read.path")
    private String filePath ;
}
