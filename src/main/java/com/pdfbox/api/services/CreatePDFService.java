package com.pdfbox.api.services;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePDFService {
    @Value("${spring.application.pdf.create_read.path}")
    private String filePath ;
    public String createEmptyPDF(String filename) throws IOException {
        try{
        PDDocument document = new PDDocument();
            PDPage page=new PDPage();
            //Empty page added because after we created the pdf without a single page it will show error when we try to open it
            document.addPage(page);
            document.save(filePath +"/"+ filename +".pdf");
            document.close();
            return filePath + filename;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;

        }

    }
    // adding an empty single page and copy sample.pdf into the firstpdf.pdf
    public String loadAndUpdatePDF(String filename){
        try{
            File existingPdf = new File(filePath +"/"+ filename +".pdf" );
            PDDocument document =  PDDocument.load(existingPdf);
            PDPage page=new PDPage();
            document.addPage(page);
            document.save(filePath +"/"+ "firstpdf" +".pdf");
            document.close();
            return filePath + "firstpdf.pdf";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;

        }

    }
    //first try the following error happened for some sample pdf
  //  Cannot invoke "org.apache.pdfbox.pdmodel.PDPage.getCOSObject()" because the return value of "org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination.getPage()" is null
// handle the file and folder creation and make sure if the file or folder exist before accessing or creating anything
    public List<String> splitPDFToPages(String filename) throws IOException {
        List<String> pagePaths = new ArrayList<>();

        try {
            File existingPdf = new File(filePath + "/" + filename + ".pdf");
            PDDocument document = PDDocument.load(existingPdf);

            System.out.println("1");
            Splitter splitter = new Splitter();
            System.out.println("2");
            List<PDDocument> splitedPages = splitter.split(document);
            System.out.println("3");

            int pageNumber = 1;

            for (PDDocument page : splitedPages) {
                System.out.println(pageNumber);
                String pagePath = filePath + "/" + filename + "/" + "page" + pageNumber + ".pdf";
                page.save(pagePath);
                pagePaths.add(pagePath);
                pageNumber++;
                page.close();
            }

            document.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return pagePaths;
    }

// check if the start and the end page is exist before proceeding
    // check if actually start is less or equals to the end
    public String splitPDFToRangePages(String filename, int start,int end) throws IOException {


        try {
            File existingPdf = new File(filePath + "/" + filename + ".pdf");
            File newDestinationPath = new File(filePath + "/" + filename + "/range_split");
            newDestinationPath.mkdirs();
            PDDocument document = PDDocument.load(existingPdf);
int maxSize=document.getNumberOfPages();
if(start > maxSize || end >maxSize){
    return "starting or ending of the pdf is larger than the pdf max page number";
}
            System.out.println("1");
            Splitter splitter = new Splitter();
            splitter.setStartPage(start);
            splitter.setEndPage(end);
            System.out.println("2");
            List<PDDocument> splitedPages = splitter.split(document);
            System.out.println("3");

            PDDocument rangeSplitPDF = new PDDocument();


            for (PDDocument page : splitedPages) {
                System.out.println(start);
              rangeSplitPDF.addPage(page.getPage(0));
            }
            rangeSplitPDF.save(newDestinationPath+"/"+filename + ".pdf");
            rangeSplitPDF.close();


            document.close();
            return newDestinationPath.getAbsolutePath();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }
    public String deleteSinglePagePDF(String filename, int pageNumber) throws IOException {


        try {
            File existingPdf = new File(filePath + "/" + filename + ".pdf");
            File newDestinationPath = new File(filePath + "/" + filename + "/delete");
            newDestinationPath.mkdirs();
            PDDocument document = PDDocument.load(existingPdf);
            int maxSize=document.getNumberOfPages();
            if(pageNumber > maxSize ){
                return "deleting page number should be less than the max pdf page number";
            }
            System.out.println("1");
document.removePage(pageNumber);
document.save(newDestinationPath+"/"+filename+".pdf");
            System.out.println("2");

            document.close();
            return newDestinationPath.getAbsolutePath();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

    public String deleteRangePagePDF(String filename, int start,int end) throws IOException {


        try {
            File existingPdf = new File(filePath + "/" + filename + ".pdf");
            File newDestinationPath = new File(filePath + "/" + filename + "/deleteRange");
            newDestinationPath.mkdirs();
            PDDocument document = PDDocument.load(existingPdf);
            int maxSize=document.getNumberOfPages();
            if(start > maxSize || end >maxSize){
                return "starting or ending of the pdf is larger than the pdf max page number";
            }
            System.out.println("1");
            // better to delete backward or in reverse order
            for(int pageNumber=end; pageNumber>=start; pageNumber--){
            document.removePage(pageNumber-1);
            }
            document.save(newDestinationPath+"/"+filename+".pdf");
            System.out.println("2");

            document.close();
            return newDestinationPath.getAbsolutePath();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }
// Check if every file exist before proceeding
    // in the first try the following error was happened .\pdfContainer\3firstpdf.pdf (The system cannot find the file specified)
    public String mergePdfs(String fileName,String fileName1,String fileName2,String fileName3) throws IOException {


        try {
            File existingPdf = new File(filePath + "/" + fileName + ".pdf");
            File existingPdf1 = new File(filePath + "/" + fileName1 + ".pdf");

            File existingPdf2 = new File(filePath + "/" + fileName2 + ".pdf");

            File existingPdf3 = new File(filePath + "/" + fileName3 + ".pdf");

            File newDestinationPath = new File(filePath  + "/merge");
            newDestinationPath.mkdirs();

            PDFMergerUtility utility = new PDFMergerUtility();
            utility.setDestinationFileName(newDestinationPath + "/" + "merge.pdf");
            utility.addSource(existingPdf);
            utility.addSource(existingPdf1);
            utility.addSource(existingPdf2);
            utility.addSource(existingPdf3);
            utility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            return newDestinationPath.getAbsolutePath();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

}
