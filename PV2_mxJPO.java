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
import com.aspose.pdf.License;
import com.aspose.pdf.Page;

import java.io.*;
import java.io.File;

import matrix.db.*;



public class PV2_mxJPO {
    public PV2_mxJPO(Context context,String[] args)
            throws Exception {

    }

	    public void stampPDF(Context context,String[] args) throws Exception {

		String titlename;
		String comm = "default.file";
        String sObjectId = args[0];
	//	String sUserName = args[1];
	//			sUserName = sUserName.trim();
		String getSignPic = "";
	/*	if (sUserName.equals("Cat01")){
			getSignPic = "sign1.jpg";
		}
		else if (sUserName.equals("admin_platform")){
			getSignPic = "sign2.jpg";
		}*/
 //       System.out.println("Eakkawat Hello worlddd" + getSignPic);
		getSignPic = "sign1.jpg";
        String sCheckOutPath = "C:/temp/in";
        String sCheckInPath = "C:/temp/out/";
	   
        //Addlog 
	Logger logger = Logger.getLogger("MyLog");  
    FileHandler fh;  
		
        try {
		
		
	
		
		// Initialize License Instance
		License license = new com.aspose.pdf.License();
		// Call setLicense method to set license		
		license.setLicense("C:/DassaultSystemes/R2019x/3DSpace/win_b64/code/tomee/webapps/3dspace/WebClient/Aspose.Pdf.lic");
		
		MqlUtil.mqlCommand(context, "checkout bus $1 file all $2", true, new String[]{sObjectId, sCheckOutPath});
//		MqlUtil.mqlCommand(context, "checkout bus HHH file all C:/temp/in");
		//***** Log
        fh = new FileHandler("C:/temp/test/MyLogFile.log");  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
        logger.info("Print OBject ID >> " + sObjectId ); 
		fh.close();
		//********  
//			titlename = MqlUtil.mqlCommand(context, "print bus $1 select $2 ", true,new String[]{sObjectId, comm});
		
//			String[] arrOfStr = titlename.split("default.file = :"); 
			//Debug Output
			//System.out.println("Eakkawat Debug Array of 1 "+ arrOfStr[1]);
	
	//	String[] sInputFile = new String[arrOfStr.length];
	//	String[] sOutputFile = new String[arrOfStr.length];
	//	String[] sCheckInPath = new String[arrOfStr.length];
		String tempPath = "C:\\temp\\in";
		String sInputFile = "C:\\temp\\in\\";
		String sOutputFile = "C:\\temp\\out\\";
		File directory = new File(tempPath); 
		File[] fList = directory.listFiles();
//		System.out.println(fList[0].getName());
//		System.out.println(fList[1].getName());
//		System.out.println(fList[2].getName());
		//String multipleFile = sOutputFile + fList[0].getName() + " " + sOutputFile + fList[1].getName() + " " + sOutputFile + fList[2].getName();
//		String multipleFile = sOutputFile + fList[0].getName();
//		String multipleFile1 = sOutputFile + fList[1].getName();
		for (File file : fList){
			//System.out.println(file.getName());
			addImageStamp(sInputFile + file.getName(), sOutputFile + file.getName(),getSignPic);
		//	multipleFile = multipleFile +" " + sOutputFile + file.getName();
	//		MqlUtil.mqlCommand(context, "checkin bus $1 $2", true, new String[]{sObjectId, sOutputFile + file.getName()});
		}
//			System.out.println(multipleFile);
//			MqlUtil.mqlCommand(context, "checkin bus $1" + multipleFile + " " + multipleFile1, true, new String[]{sObjectId});
		//	MqlUtil.mqlCommand(context, "checkin bus $1 $2", true, new String[]{sObjectId, multipleFile1});
		
		/*	// For collect path
			for (int arrCount = 1; arrCount < arrOfStr.length; arrCount++) {
				//System.out.println("Eakkawat Debug Array of "+ arrCount + "value is " + arrOfStr[arrCount]);
				 sInputFile[arrCount] 	= "C:/temp/in/" + arrOfStr[arrCount];
				 sOutputFile[arrCount] 	= "C:/temp/out/" + arrOfStr[arrCount];
				 sCheckInPath[arrCount] = "C:/temp/out/" + arrOfStr[arrCount];
			} 
		*/
		//Debug Output path and file name
//		System.out.print("Eakkawat Debug InputFile  1 is " + sInputFile[1] + "555");
//		System.out.print("Eakkawat Debug InputFile  2 is " + sInputFile[2] + "6666");
//		addImageStamp(sInputFile[1], sOutputFile[1]);
	/*		
			for (int stampCount = 1;stampCount < sInputFile.length; stampCount++){
//				addImageStamp(sInputFile[stampCount], sOutputFile[stampCount]);
				
			}
	*/
		
//_Y        addTextStamp(sInputFile, sOutputFile);
//_Y        addImageStamp(sInputFile, sOutputFile);
//       MqlUtil.mqlCommand(context, "checkin bus $1 $2", true, new String[]{sObjectId, sCheckInPath});

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
    public void addImageStamp(String aInputFile,String sOutputFile,String signPic) throws Exception{
        try{
            Document pdfDocument = new Document(aInputFile);
            ImageStamp imageStamp = new ImageStamp("C:/temp/stamp/" + signPic);
            imageStamp.setBackground(false);
            imageStamp.setXIndent(430);
            imageStamp.setYIndent(80);
//           imageStamp.setHeight();
//            imageStamp.setWidth(150);
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
	//		pdfDocument.close();
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