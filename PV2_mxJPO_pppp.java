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

//import javax.naming.Context;
//import java.awt.*;
//import java.awt.Color;
//import matrix.util.*;
import com.aspose.pdf.*;
import com.aspose.pdf.License;
import com.aspose.pdf.Page;
//import com.aspose.pdf.Color;
//import matrix.db.*;
//import matrix.util.StringList;
import java.io.*;
//import com.matrixone.apps.domain.DomainObject;
// com.matrixone.apps.domain.util.MapList;
//import com.matrixone.apps.domain.util.MqlUtil;
//import com.matrixone.util;
import matrix.db.*;
//import matrix.util.SelectList;
//import matrix.util.StringList;
//import com.matrixone.apps.domain.*;


public class PV2_mxJPO {
    public PV2_mxJPO(Context context,String[] args)
            throws Exception {

    }

	    public void stampPDF(Context context,String[] args) throws Exception {

//_T		String titlename;
//_T		String comm = "attribute[Title]";
        String sObjectId = args[0];
      //  System.out.println("Eakkawat Hello worlddd" + sObjectId);
		
        String sCheckOutPath = "C:/temp/in";
       // String sCheckInPath = "C:/temp/out/";
        

        try {

        MqlUtil.mqlCommand(context, "checkout bus $1 file all $2", true, new String[]{sObjectId, sCheckOutPath});
		
//_T		titlename = MqlUtil.mqlCommand(context, "print bus $1 select $2 ", true,new String[]{sObjectId, comm});
		
//_T		String[] arrOfStr = titlename.split("= ", 2); 
		//System.out.println("Eakkawat Debug Titlename is " + arrOfStr[1]);
		
		String sInputFile = "C:/temp/in/SampleDataTable_005.pdf";
		String sOutputFile = "C:/temp/out/SampleDataTable_005.pdf";
		String sCheckInPath = "C:/temp/out/SampleDataTable_005.pdf";
		
//_T	String sInputFile = "C:/temp/in/"+arrOfStr[1]+".pdf";
//_T	String sOutputFile = "C:/temp/out/"+arrOfStr[1]+".pdf";
//_T	String sCheckInPath = "C:/temp/out/"+arrOfStr[1]+".pdf";
		System.out.println("Eakkawat Debug sInputFile is " + sInputFile);
		System.out.println("Eakkawat Debug sOutputFile is " + sOutputFile);
		
		
         //   print bus 33319.63715.52776.59313 select attribute[Title]



		// Initialize License Instance
		License license = new com.aspose.pdf.License();
		// Call setLicense method to set license
		
		license.setLicense("C:/DassaultSystemes/R2019x/3DSpace/win_b64/code/tomee/webapps/3dspace/WebClient/Aspose.Pdf.lic");
	
		// TODO Auto-generated catch block
		
     //   addTextStamp(sInputFile, sOutputFile);
    //    addImageStamp(sInputFile, sOutputFile);
    //    MqlUtil.mqlCommand(context, "checkin bus $1 $2", true, new String[]{sObjectId, sCheckInPath});

        } catch (Exception e) {
            System.out.println("Exception stamp" + e.toString());
            throw e;
        }

    }
    public void addTextStamp (String aInputFile, String aOutputFile)throws Exception {
        try{
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
        }catch(Exception e){
            System.out.println("Exc Textstamp " + e.toString());
        }
    }
    public void addImageStamp(String aInputFile,String sOutputFile) throws Exception{
        try{
            Document pdfDocument = new Document(aInputFile);
            ImageStamp imageStamp = new ImageStamp("C:/temp/stamp/sample.jpg");
            imageStamp.setBackground(false);
            imageStamp.setXIndent(100);
            imageStamp.setYIndent(50);
            imageStamp.setHeight(150);
            imageStamp.setWidth(150);
			//int xT = pdfDocument.getPages().indexOf();
			//System.out.println(xT);
			for (int pageCount = 1; pageCount <= pdfDocument.getPages().size(); pageCount++) {
				int pageNumber = pdfDocument.getPages().get_Item(pageCount).getNumber();
				pdfDocument.getPages().get_Item(pageNumber).addStamp(imageStamp);
			} 
    //     pdfDocument.getPages().get_Item(1).addStamp(imageStamp);
	//		pdfDocument.getPages().get_Item(2).addStamp(imageStamp);
	//		pdfDocument.getPages().get_Item(3).addStamp(imageStamp);
            pdfDocument.save(sOutputFile);

        }catch(Exception e){
            System.out.println("Exception addimage" + e.toString());
        }
    }
	
	/*public String getTitlename(String idObj ) throws Exception {
		String result;
		String comm = "attribute[Title]";
        try {

			result = MqlUtil.mqlCommand(context, "print bus $1 select $2 ", true,new String[]{idObj, comm});
            //print bus 33319.63715.52776.59313 select attribute[Title]
			String[] arrOfStr = titlename.split("= ", 2); 
			//System.out.println("Eakkawat Debug Titlename is " + arrOfStr[1]);
			String sInputFile = "C:/temp/in/"+arrOfStr[1]+".pdf";
			String sOutputFile = "C:/temp/out/"+arrOfStr[1]+".pdf";
			System.out.println("Eakkawat Debug sInputFile is " + sInputFile);
			System.out.println("Eakkawat Debug sOutputFile is " + sOutputFile);
			return  arrOfStr;

        } catch (Exception e) {
            System.out.println("Exception stamp New Fucntion" + e.toString());
            throw e;
        }
	}*/
}