
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



public class PV22_mxJPO {
		public PV22_mxJPO(Context context,String[] args)
            throws Exception {

		}

	    public void stampPDF(Context context,String[] args) throws Exception {

       	String sObjectId = args[0];	
			String sCheckOutPath = "C:/temp/in";
		Logger logger = Logger.getLogger("MyLog");  
		FileHandler fh;  
			try {
				MqlUtil.mqlCommand(context, "checkout bus $1 file all $2", true, new String[]{sObjectId, sCheckOutPath});
				
						//***** Log
        fh = new FileHandler("C:/temp/test/MyLogFile.log");  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
        logger.info("Print OBject ID >> " + sObjectId ); 
		fh.close();
		//********  
				
			} catch (Exception e) {
				System.out.println("Exception stamp" + e.toString());
				throw e;
			}

		}

   

}