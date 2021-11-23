import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelectList;
import matrix.db.BusinessType;
import matrix.db.Context;
import matrix.db.JPO;
import matrix.db.Policy;
import matrix.db.PolicyItr;
import matrix.db.PolicyList;
import matrix.db.RelationshipType;
import matrix.db.Vault;
import matrix.db.StateRequirementList;
import matrix.db.StateRequirement;
import matrix.util.List;
import matrix.util.MatrixException;
import matrix.util.SelectList;
import matrix.util.StringItr;
import matrix.util.StringList;
import com.matrixone.jsystem.util.StringUtils;
import com.matrixone.apps.common.Company;
import com.matrixone.apps.common.Person;
import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.DomainRelationship;
import com.matrixone.apps.domain.DomainSymbolicConstants;
import com.matrixone.apps.domain.util.ContextUtil;
import com.matrixone.apps.domain.util.EnoviaResourceBundle;
import com.matrixone.apps.domain.util.FrameworkException;
import com.matrixone.apps.domain.util.FrameworkProperties;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.domain.util.MqlUtil;
import com.matrixone.apps.domain.util.OrganizationUtil;
import com.matrixone.apps.domain.util.PersonUtil;
import com.matrixone.apps.domain.util.PropertyUtil;
import com.matrixone.apps.domain.util.VaultUtil;
import com.matrixone.apps.domain.util.XSSUtil;
import com.matrixone.apps.domain.util.eMatrixDateFormat;
import com.matrixone.apps.domain.util.i18nNow;
import com.matrixone.apps.framework.ui.UINavigatorUtil;
import com.matrixone.apps.manufacturerequivalentpart.Part;
import com.matrixone.apps.framework.ui.UIUtil;
import java.util.HashMap;

import java.io.IOException;
import java.util.logging.*;

import com.aspose.pdf.*;
import com.aspose.pdf.Document;
import com.aspose.pdf.License;
import com.aspose.pdf.Page;

import java.io.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import matrix.db.*;

public class routeSTP2021_mxJPO {
    public routeSTP2021_mxJPO(Context context, String[] args) throws Exception {

    }

    public void stampPDF(Context context, String[] args) throws Exception {
        String sObjectId = args[0];

        String sSignPath = "C:/temp/stamp/sign//";
        String sCheckOutPath = "C:/temp/stamp/checkout";
        String sCheckInPath = "C:/temp/stamp/checkin//";
        String sPictureName = "Cat01.png";
        try {
            License license = new com.aspose.pdf.License();
            license.setLicense(
                    "C:/DassaultSystemes/R2019x/3DSpace/win_b64/code/tomee/webapps/3dspace/WebClient/Aspose.Pdf.lic");

            MqlUtil.mqlCommand(context, "checkout bus $1 file all $2", true, new String[] { sObjectId, sCheckOutPath });
            addPicToPDF(sCheckOutPath, sCheckInPath, sSignPath, sPictureName, 480, 665);

            // addPicToPDF(sCheckOutPath, sCheckInPath, sSignPath, sPictureName, 480, 665);
            File directory = new File(sCheckOutPath);
            File[] fList = directory.listFiles();
            MqlUtil.mqlCommand(context, "checkin bus $1 $2", true,
                    new String[] { sObjectId, sCheckInPath + fList[0].getName() });
        } catch (Exception e) {
            System.out.println("Exception stamp" + e.toString());
            throw e;
        } finally {
            deleteFile(sCheckOutPath);
            deleteFile(sCheckInPath);
        }
    }

    // create internal method for stamping
    /*
     * Method addPicToPDF
     * 
     * @param {string} >> checkout path
     * 
     * @param {string} >> checkin path
     * 
     * @param {string} >> picture path
     * 
     * @param {string} >> pictureName
     * 
     * @param int} >> x Coordinate
     * 
     * @param {int} >> y Coordinate return void
     */
    public static boolean addPicToPDF(String sCheckOutPath, String sCheckInPath, String sSignPath, String sPictureName,
            int xOrdinate, int yOrdinate) throws Exception {
        // Gen Date Time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        // System.out.println(dateFormat.format(date));

        File directory = new File(sCheckOutPath);
        File[] fList = directory.listFiles();
        Document pdfDocument = new Document(sCheckOutPath + "//" + fList[0].getName());
        ImageStamp imageStamp = new ImageStamp(sSignPath + sPictureName);
        imageStamp.setBackground(false);
        imageStamp.setXIndent(xOrdinate);
        imageStamp.setYIndent(yOrdinate);
        imageStamp.setFixWidth(100);
        imageStamp.setFixHeight(100);
        pdfDocument.getPages().get_Item(1).addStamp(imageStamp);

        Page pdfPage = pdfDocument.getPages().get_Item(1);
        // create text fragment
        TextFragment textFragment = new TextFragment(dateFormat.format(date));
        textFragment.setPosition(new Position(510, 490));

        // set text properties
        textFragment.getTextState().setFont(FontRepository.findFont("Verdana"));
        textFragment.getTextState().setFontSize(10);
        textFragment.getTextState().setForegroundColor(Color.getBlue());

        // create TextBuilder object
        TextBuilder textBuilder = new TextBuilder(pdfPage);
        // append the text fragment to the PDF page
        textBuilder.appendText(textFragment);

        pdfDocument.save(sCheckInPath + fList[0].getName());
        return true;
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
                if (!files.equalsIgnoreCase("Scan.pdf")) {
                    boolean issuccess = new File(listOfFiles[i].toString()).delete();
                    System.err.println("Deletion Success " + issuccess);
                }
            }
        }
    }

}