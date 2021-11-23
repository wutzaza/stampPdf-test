package com.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.HashMap;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.*;
import javax.swing.*;
import java.awt.*;
import com.aspose.pdf.*;
import com.aspose.pdf.Color;
import com.aspose.pdf.Image;
import com.aspose.pdf.Rectangle;
import com.aspose.pdf.facades.PdfFileSignature;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {

        // Initialize License Instance
        License license = new com.aspose.pdf.License();
        try {
            license.setLicense("C:/temp/stamp/Aspose.Pdf.lic");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // String sSignPath = "C:\\temp\\stamp\\";
        // String sPictureName = "Pitchsiri.png";
        // String sCheckOutPath = "C:\\temp\\stamp\\checkout";
        // String sCheckInPath = "C:\\temp\\stamp\\checkin";
        String sSignPath = "C:/temp/stamp//";
        String sCheckOutPath = "C:/temp/stamp/checkout";
        String sCheckInPath = "C:/temp/stamp/checkin//";
        String sPictureName = "Pitchsiri.png";

        // String sCheckOutPath = "C:\\temp\\stamp\\checkout\\Test_Report_Template.pdf";
        // String sCheckInPath = "C:\\temp\\stamp\\checkin\\output.pdf";
        // String sPictureName = "C:\\temp\\stamp\\Pitchsiri.png";
        // String tag = "Eng1";

        try {
            addPicToPDF(sCheckOutPath, sCheckInPath, sSignPath, sPictureName, 500, 675);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            deleteFile(sCheckOutPath);
            // deleteFile(sCheckInPath);
        }
    }

    public static void addPicToPDF(String sCheckOutPath, String sCheckInPath, String sSignPath, String sPictureName,
            int xOrdinate, int yOrdinate) throws Exception {
        // Gen Date Time
        DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        File directory = new File(sCheckOutPath);
        File[] fList = directory.listFiles();
        System.out.println("List file " + fList[0].getName());
        Document pdfDocument = new Document(sCheckOutPath + "//" + fList[0].getName());
        ImageStamp imageStamp = new ImageStamp(sSignPath + sPictureName);
        imageStamp.setBackground(false);
        imageStamp.setXIndent(xOrdinate);
        imageStamp.setYIndent(yOrdinate);
        imageStamp.setWidth(35);
        imageStamp.setHeight(18);
        pdfDocument.getPages().get_Item(1).addStamp(imageStamp);

        Page pdfPage = pdfDocument.getPages().get_Item(1);
        // create text fragment
        TextFragment textFragment = new TextFragment(dateFormat.format(date));
        textFragment.setPosition(new Position(500, 668));

        // set text properties
        textFragment.getTextState().setFont(FontRepository.findFont("Verdana"));
        textFragment.getTextState().setFontSize(8);
        textFragment.getTextState().setForegroundColor(Color.getBlue());

        // create TextBuilder object
        TextBuilder textBuilder = new TextBuilder(pdfPage);
        // append the text fragment to the PDF page
        textBuilder.appendText(textFragment);

        pdfDocument.save(sCheckInPath + fList[0].getName());

    }

    private static void deleteFile(String dir) {
        String files;
        File file = new File(dir);
        // System.out.println("Path file " + file);
        File[] listOfFiles = file.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                System.out.println(files);
                // if (!files.equalsIgnoreCase("Scan.pdf")) {
                // boolean issuccess = new File(listOfFiles[i].toString()).delete();
                // System.err.println("Deletion Success " + issuccess);
                // }
            }
        }
    }
}
