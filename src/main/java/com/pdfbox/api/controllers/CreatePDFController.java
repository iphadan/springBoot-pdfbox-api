package com.pdfbox.api.controllers;

import com.pdfbox.api.dto.CreatePDFRequest;
import com.pdfbox.api.dto.MergeRequest;
import com.pdfbox.api.dto.RangeSplitRequest;
import com.pdfbox.api.dto.SinglePageDeleteRequest;
import com.pdfbox.api.services.CreatePDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("examples")
public class CreatePDFController {
    @Autowired
    private  CreatePDFService createPDFService;
    @PostMapping("/createEmptyPDF")
    public ResponseEntity<String> createEmptyPDF(@RequestBody CreatePDFRequest request) throws IOException {
return ResponseEntity.ok(createPDFService.createEmptyPDF(request.getFileName()));
    }
    @PostMapping("/addEmptyPageOnExistingPDF")
    public ResponseEntity<String> addEmptyPageOnExistingPDF(@RequestBody CreatePDFRequest request) throws IOException {
        return ResponseEntity.ok(createPDFService.loadAndUpdatePDF(request.getFileName()));
    }
    @PostMapping("/splitPDFToPages")
    public ResponseEntity<List<String>> splitPDFToPages(@RequestBody CreatePDFRequest request) throws IOException {
        return ResponseEntity.ok(createPDFService.splitPDFToPages(request.getFileName()));
    }
    @PostMapping("/splitPDFToRangePages")
    public ResponseEntity<String> splitPDFToRangePages(@RequestBody RangeSplitRequest request) throws IOException {
        return ResponseEntity.ok(createPDFService.splitPDFToRangePages(request.getFileName(),request.getStart(),request.getEnd()));
    }
    @PostMapping("/deleteSinglePagePDF")
    public ResponseEntity<String> deleteSinglePagePDF(@RequestBody SinglePageDeleteRequest request) throws IOException {
        return ResponseEntity.ok(createPDFService.deleteSinglePagePDF(request.getFileName(),request.getPageNumber()));
    }
    @PostMapping("/deleteRangePagePDF")
    public ResponseEntity<String> deleteRangePagePDF(@RequestBody RangeSplitRequest request) throws IOException {
        return ResponseEntity.ok(createPDFService.deleteRangePagePDF(request.getFileName(),request.getStart(),request.getEnd()));
    }
    @PostMapping("/mergePdfs")
    public ResponseEntity<String> deleteRangePagePDF(@RequestBody MergeRequest request) throws IOException {
        return ResponseEntity.ok(createPDFService.mergePdfs(request.getFileName(),request.getFileName1(),request.getFileName2(),request.getFileName3()));
    }
}
