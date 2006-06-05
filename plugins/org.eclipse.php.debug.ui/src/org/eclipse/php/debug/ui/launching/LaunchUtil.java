package org.eclipse.php.debug.ui.launching;

import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.php.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;

public class LaunchUtil {
	
	public static final String ORG_ECLIPSE_WST_HTML_CORE_HTMLSOURCE = "org.eclipse.wst.html.core.htmlsource";
	
	public static String[] getRequiredNatures()
    {
    	return new String[] {org.eclipse.php.core.project.PHPNature.ID};
    }
    
    public static String[] getFileExtensions()
    {
    	ArrayList extensions = new ArrayList();
    	IContentTypeManager typeManager = Platform.getContentTypeManager();
    	
    	IContentType type = typeManager.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
        String[] phpExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
        
        IContentType htmlContentType = typeManager.getContentType(ORG_ECLIPSE_WST_HTML_CORE_HTMLSOURCE);
        String[] htmlExtensions = htmlContentType.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);

        if(phpExtensions != null)
        	for(int i=0; i<phpExtensions.length; i++)
        		extensions.add(phpExtensions[i]);
        
        if(htmlExtensions != null)
        	for(int i=0; i<htmlExtensions.length; i++)
        		extensions.add(htmlExtensions[i]);
        
    	if(extensions.isEmpty())
    		return null;
    	
    	return (String[])extensions.toArray(new String[extensions.size()]);
    }
}
