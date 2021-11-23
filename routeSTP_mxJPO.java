import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.lang.String;
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
import com.aspose.pdf.License;
import com.aspose.pdf.Page;

import java.io.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import matrix.db.*;

public class routeSTP_mxJPO {
    public routeSTP_mxJPO(Context context, String[] args) throws Exception {

    }

    public void stampPDF(Context context, String[] args) throws Exception {

        String sObjectId = args[0];
        String checkRouteJava = "relationship\\[Initiating Route Template\\].businessobject.attribute\\[XP_Route_Template_Ext.Flow_Test_Report_Template\\] = ";
        String checkRouteAtt = "relationship[Initiating Route Template].businessobject.attribute[XP_Route_Template_Ext.Flow_Test_Report_Template]";
        String mqlCodeUser = "relationship[Route Node].to";
        String mqlCodeSeq = "relationship[Route Node].attribute[Route Sequence]";
        String mqlCodeDate = "relationship[Route Node].attribute[Actual Completion Date]";
        String mqlCodeObjectid = "relationship[Object Route].from.id";
        String javaCodeUser = "relationship\\[Route Node\\].to =";
        String javaCodeSeq = "relationship\\[Route Node\\].attribute\\[Route Sequence\\] =";
        String javaCodeDate = "relationship\\[Route Node\\].attribute\\[Actual Completion Date\\] =";
        String javaCodeObjectid = "relationship\\[Object Route\\].from.id =";

        String testOut;
        String getSignPic = "";

        getSignPic = "sign1.jpg";
        String sCheckOutPath = "C:/temp/in";
        String sCheckInPath = "C:/temp/out//";

        // Addlog
        // Logger logger = Logger.getLogger("MyLog");
        // FileHandler fh;

        try {

            // Initialize License Instance
            License license = new com.aspose.pdf.License();
            // Call setLicense method to set license
            license.setLicense(
                    "C:/DassaultSystemes/R2019x/3DSpace/win_b64/code/tomee/webapps/3dspace/WebClient/Aspose.Pdf.lic");

            // MqlUtil.mqlCommand(context, "checkout bus $1 file all $2", true, new
            // String[]{sObjectId, sCheckOutPath});
            testOut = MqlUtil.mqlCommand(context, "print bus $1 select $2", true,
                    new String[] { sObjectId, checkRouteAtt });
            String[] cutResultAction1 = testOut.split(checkRouteJava);
            if (cutResultAction1.length > 1) {
                if (cutResultAction1[1].trim().equals("TRUE")) {
                    // Action
                    // System.out.println(cutResultAction1[1].trim());
                    String getAllData = MqlUtil.mqlCommand(context, "print bus $1 select $2 $3 $4 $5 ", true,
                            new String[] { sObjectId, mqlCodeUser, mqlCodeSeq, mqlCodeDate, mqlCodeObjectid });
                    // System.out.println(getAllData);
                    String[] b = getAllData
                            .split(javaCodeUser + "|" + javaCodeSeq + "|" + javaCodeDate + "|" + javaCodeObjectid);
                    for (String str : b) {
                        System.out.println(str.trim());
                    }
                    // checkout file in route
                    MqlUtil.mqlCommand(context, "checkout bus $1 server file all $2", true,
                            new String[] { b[13].trim(), sCheckOutPath });
                    // StampFile
                    String tempPath = "C:\\temp\\in";
                    String sInputFile = "C:\\temp\\in\\";
                    String sOutputFile = "C:\\temp\\out\\";
                    File directory = new File(tempPath);
                    File[] fList = directory.listFiles();

                    // System.out.println(sInputFile+fList[0].getName());
                    addImageStamp(sInputFile + fList[0].getName(), sOutputFile + fList[0].getName());

                    // MqlUtil.mqlCommand(context, "checkin bus $1 $2" , true, new
                    // String[]{b[13].trim(),sOutputFile+fList[0].getName()});
                }
            }
            // ***** Log
            /*
             * fh = new FileHandler("C:/temp/test/MyLogFile.log"); logger.addHandler(fh);
             * SimpleFormatter formatter = new SimpleFormatter();
             * fh.setFormatter(formatter); logger.info("Print OBject ID >> " + sObjectId );
             * fh.close();
             */
            String tempPath = "C:\\temp\\in";
            String sInputFile = "C:\\temp\\in\\";
            String sOutputFile = "C:\\temp\\out\\";

        } catch (Exception e) {
            System.out.println("Exception stamp" + e.toString());
            throw e;
        }

    }

    public void addTextStamp(String aInputFile, String aOutputFile) throws Exception {
        try {
            Document pdfDocument = new Document(aInputFile);
            TextStamp textStamp = new TextStamp("Confidential");
            textStamp.setBackground(true);
            textStamp.setXIndent(50);
            textStamp.setYIndent(50);

            textStamp.getTextState().setFont(new FontRepository().findFont("Arial"));
            textStamp.getTextState().setFontSize(14.0f);
            textStamp.getTextState().setFontStyle(FontStyles.Bold);
            textStamp.getTextState().setForegroundColor(Color.getGreen());

            pdfDocument.getPages().get_Item(1).addStamp(textStamp);

            pdfDocument.save(aOutputFile);
        } catch (Exception e) {
            System.out.println("Exc Textstamp " + e.toString());
        }
    }

    public void addImageStamp(String aInputFile, String sOutputFile) throws Exception {
        try {
            Document pdfDocument = new Document(aInputFile);
            var brandingImage = new ImageStamp("C:/temp/stamp/niceSig1.png");
            brandingImage.XIndent = 10;
            brandingImage.YIndent = 10;
            brandingImage.Opacity = 1;
            // ImageStamp imageStamp = new ImageStamp("C:/temp/stamp/niceSig1.png");
            // imageStamp.setBackground(false);
            // imageStamp.setXIndent(500);
            // imageStamp.setYIndent(700);
            // ImageStamp imageStamp2 = new ImageStamp("C:/temp/stamp/niceSig2.png");
            // imageStamp2.setBackground(false);
            // imageStamp2.setXIndent(450);
            // imageStamp2.setYIndent(700);
            // ImageStamp imageStamp3 = new ImageStamp("C:/temp/stamp/niceSig3.png");
            // imageStamp3.setBackground(false);
            // imageStamp3.setXIndent(400);
            // imageStamp3.setYIndent(700);
            // ImageStamp imageStamp4 = new ImageStamp("C:/temp/stamp/niceSig4.png");
            // imageStamp4.setBackground(false);
            // imageStamp4.setXIndent(350);
            // imageStamp4.setYIndent(700);

            // Gen Date Time
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            // System.out.println(dateFormat.format(date));

            Page pdfPage = pdfDocument.getPages().get_Item(1);
            // create text fragment
            TextFragment textFragment = new TextFragment(dateFormat.format(date));
            textFragment.setPosition(new Position(510, 690));
            // set text properties
            textFragment.getTextState().setFont(FontRepository.findFont("Verdana"));
            textFragment.getTextState().setFontSize(10);
            textFragment.getTextState().setForegroundColor(Color.getBlue());
            // textFragment.getTextState().setBackgroundColor(Color.getGray());
            // create TextBuilder object
            TextBuilder textBuilder = new TextBuilder(pdfPage);
            // append the text fragment to the PDF page
            textBuilder.appendText(textFragment);

            // create text fragment
            TextFragment textFragment2 = new TextFragment(dateFormat.format(date));
            textFragment2.setPosition(new Position(455, 690));
            // set text properties
            textFragment2.getTextState().setFont(FontRepository.findFont("Verdana"));
            textFragment2.getTextState().setFontSize(10);
            textFragment2.getTextState().setForegroundColor(Color.getBlue());
            // textFragment.getTextState().setBackgroundColor(Color.getGray());
            // create TextBuilder object
            TextBuilder textBuilder2 = new TextBuilder(pdfPage);
            // append the text fragment to the PDF page
            textBuilder2.appendText(textFragment2);

            // create text fragment
            TextFragment textFragment3 = new TextFragment(dateFormat.format(date));
            textFragment3.setPosition(new Position(400, 690));
            // set text properties
            textFragment3.getTextState().setFont(FontRepository.findFont("Verdana"));
            textFragment3.getTextState().setFontSize(10);
            textFragment3.getTextState().setForegroundColor(Color.getBlue());
            // textFragment.getTextState().setBackgroundColor(Color.getGray());
            // create TextBuilder object
            TextBuilder textBuilder3 = new TextBuilder(pdfPage);
            // append the text fragment to the PDF page
            textBuilder3.appendText(textFragment3);

            // create text fragment
            TextFragment textFragment4 = new TextFragment(dateFormat.format(date));
            textFragment4.setPosition(new Position(350, 690));
            // set text properties
            textFragment4.getTextState().setFont(FontRepository.findFont("Verdana"));
            textFragment4.getTextState().setFontSize(10);
            textFragment4.getTextState().setForegroundColor(Color.getBlue());
            // textFragment.getTextState().setBackgroundColor(Color.getGray());
            // create TextBuilder object
            TextBuilder textBuilder4 = new TextBuilder(pdfPage);
            // append the text fragment to the PDF page
            textBuilder4.appendText(textFragment4);
            // imageStamp.setHeight();
            // imageStamp.setWidth(150);
            // int xT = pdfDocument.getPages().indexOf();
            // System.out.println(xT);
            for (int pageCount = 1; pageCount <= pdfDocument.getPages().size(); pageCount++) {
                int pageNumber = pdfDocument.getPages().get_Item(pageCount).getNumber();
                pdfDocument.getPages().get_Item(pageNumber).addStamp(brandingImage);
                pdfDocument.getPages().get_Item(pageNumber).addStamp(imageStamp2);
                pdfDocument.getPages().get_Item(pageNumber).addStamp(imageStamp3);
                pdfDocument.getPages().get_Item(pageNumber).addStamp(imageStamp4);

            }
            // pdfDocument.getPages().get_Item(1).addStamp(imageStamp);
            // pdfDocument.getPages().get_Item(2).addStamp(imageStamp);
            // pdfDocument.getPages().get_Item(3).addStamp(imageStamp);
            pdfDocument.save(sOutputFile);
            // pdfDocument.close();
        } catch (Exception e) {
            System.out.println("Exception addimage" + e.toString());
        }
    }

}