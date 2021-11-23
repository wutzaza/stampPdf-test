import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.HashMap;

import java.io.IOException;
import java.util.logging.*;

import com.aspose.pdf.*;
import com.aspose.pdf.Document;
import com.aspose.pdf.License;
import com.aspose.pdf.Page;
import com.aspose.pdf.FileSpecification;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        // License license = new com.aspose.pdf.License();
        // license.setLicense("C:/temp/stamp/Aspose.Pdf.lic");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        String pathIn = "C:\\temp\\stamp\\checkin";
        String pathOut = "C:\\temp\\stamp\\checkout";
        // deleteFile(pathIn);
        // deleteFile(pathOut);
        replaceTags("C:\\temp\\stamp\\checkin\\Proposal_FCS_Rev01-2020.pdf",
                "C:\\temp\\stamp\\checkout\\Proposal_FCS_Rev01-2020.pdf", "Rev03", "C:\\temp\\stamp\\1.png");

    }

    private static void deleteFile(String dir) {
        String files;
        File file = new File(dir);
        System.out.println("Path file " + file);
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

    private static void replaceTags(String inPdf, String outPdf, String tag, String imageName)
            throws FileNotFoundException, IOException {
        try {
            // Open document
            Document pdfDocument = new Document(inPdf);

            for (Page page : pdfDocument.getPages()) {

                java.io.FileInputStream imageStream = new java.io.FileInputStream(new java.io.File(imageName));

                // Add an image to the Images collection of the page resources
                page.getResources().getImages().add(imageStream);
                // Using the GSave operator: this operator saves current graphics state
                page.getContents().add(new Operator.GSave());

                // Create TextAbsorber object to find all instances of the input search phrase
                TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(tag);
                page.accept(textFragmentAbsorber);
                // Get the extracted text fragments into collection
                TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();
                // Loop through the fragments
                for (TextFragment textFragment : textFragmentCollection) {

                    Rectangle rectangle = textFragment.getRectangle();

                    XImage ximage = page.getResources().getImages().get_Item(page.getResources().getImages().size());

                    Matrix matrix = new Matrix(new double[] { ximage.getWidth(), 0, 0, ximage.getHeight(),
                            rectangle.getLLX(), rectangle.getLLY() });

                    page.getContents().add(new Operator.ConcatenateMatrix(matrix));

                    // Using Do operator: this operator draws image
                    page.getContents().add(new Operator.Do(ximage.getName()));
                    // Using GRestore operator: this operator restores graphics state
                    page.getContents().add(new Operator.GRestore());

                    // Remove textFragment
                    textFragment.setText("");
                }
                imageStream.close();

            }
            // Save the updated PDF file
            pdfDocument.save(outPdf);
        } catch (Exception e) {
            System.out.println("Exception addimage" + e.toString());
        }
    }
}