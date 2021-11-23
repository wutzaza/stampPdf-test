package com.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.HashMap;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.*;
import javax.swing.*;
import java.awt.*;

import com.aspose.pdf.*;
import com.aspose.pdf.Document;
import com.aspose.pdf.License;
import com.aspose.pdf.Page;
import com.aspose.pdf.FileSpecification;
import com.aspose.pdf.PKCS1;
import com.aspose.pdf.facades.PdfFileSignature;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        // Debug
        // Initialize License Instance
        License license = new com.aspose.pdf.License();
        try {
            license.setLicense("C:/temp/stamp/Aspose.Pdf.lic");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Check if license has been validated
        // if (com.aspose.pdf.Document.isLicensed()) {
        // System.out.println("License is Set!");
        // }

        // Path to Directory
        // String myDir = "PathToDir";
        // create PdfFileSignature object and bind input PDF files
        PdfFileSignature pdfSign = new PdfFileSignature();
        pdfSign.bindPdf("C:\\temp\\stamp\\checkout\\catia_2020_R3-edit-1-10-63.pdf");
        // create a rectangle for signature location
        java.awt.Rectangle rect = new java.awt.Rectangle(100, 100, 200, 100);
        // set signature appearance
        pdfSign.setSignatureAppearance("C:\\temp\\stamp\\1.png");
        // create any of the three signature types
        PKCS1 signature = new PKCS1("C:\\temp\\stamp\\Wuttichai.pfx", "Passw0rd");
        // PKCS7 signature = new PKCS7(myDir + "temp.pfx", "password"); // PKCS#7 or
        // PKCS7Detached signature = new PKCS7Detached("temp.pfx", "password"); //
        // PKCS#7 detached
        pdfSign.sign(1, "Signature Reason", "Contact", "Location", true, rect, signature);
        // save output PDF file
        pdfSign.save("C:\\temp\\stamp\\checkin\\output.pdf");

        // System.out.println("Hello World!");
        // License license = new com.aspose.pdf.License();
        // license.setLicense("C:/temp/stamp/Aspose.Pdf.lic");
        // String sSignPath = "C:\\temp\\stamp\\sign\\";
        // String sCheckOutPath = "C:\\temp\\stamp\\checkout";
        // String sCheckInPath = "C:\\temp\\stamp\\checkin\\";
        // String sPictureName = "1.png";

        // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // Date date = new Date();
        // System.out.println(dateFormat.format(date));
        // String pathIn = "C:\\temp\\stamp\\checkin";
        // // String pathOut = "C:\\temp\\stamp\\checkout";
        // deleteFile(pathIn);
        // // deleteFile(pathOut);
        // try {
        // addPicToPDF(sCheckOutPath, sCheckInPath, sSignPath, sPictureName, 480, 665);
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    // private static void deleteFile(String dir) {
    // String files;
    // File file = new File(dir);
    // System.out.println("Path file " + file);
    // File[] listOfFiles = file.listFiles();
    // for (int i = 0; i < listOfFiles.length; i++) {
    // if (listOfFiles[i].isFile()) {
    // files = listOfFiles[i].getName();
    // System.out.println(files);
    // if (!files.equalsIgnoreCase("Scan.pdf")) {
    // boolean issuccess = new File(listOfFiles[i].toString()).delete();
    // System.err.println("Deletion Success " + issuccess);
    // }
    // }
    // }
    // }

    // public static void addPicToPDF(String sCheckOutPath, String sCheckInPath,
    // String sSignPath, String sPictureName,
    // int xOrdinate, int yOrdinate) throws Exception {
    // // Gen Date Time
    // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    // Date date = new Date();
    // // System.out.println(dateFormat.format(date));

    // File directory = new File(sCheckOutPath);
    // File[] fList = directory.listFiles();
    // Document pdfDocument = new Document(sCheckOutPath + "//" +
    // fList[0].getName());
    // ImageStamp imageStamp = new ImageStamp(sSignPath + sPictureName);
    // imageStamp.setBackground(false);
    // imageStamp.setXIndent(xOrdinate);
    // imageStamp.setYIndent(yOrdinate);
    // pdfDocument.getPages().get_Item(1).addStamp(imageStamp);

    // Page pdfPage = pdfDocument.getPages().get_Item(1);
    // // create text fragment
    // TextFragment textFragment = new TextFragment(dateFormat.format(date));
    // textFragment.setPosition(new Position(480, 490));

    // // set text properties
    // textFragment.getTextState().setFont(FontRepository.findFont("Verdana"));
    // textFragment.getTextState().setFontSize(10);
    // textFragment.getTextState().setForegroundColor(Color.getBlue());

    // // create TextBuilder object
    // TextBuilder textBuilder = new TextBuilder(pdfPage);
    // // append the text fragment to the PDF page
    // textBuilder.appendText(textFragment);

    // pdfDocument.save(sCheckInPath + fList[0].getName());

    // }
}
