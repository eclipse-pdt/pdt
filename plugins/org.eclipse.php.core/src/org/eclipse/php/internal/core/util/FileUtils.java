/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

import java.io.*;
import java.util.ArrayList;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * File Utilities class.
 */
public class FileUtils {

	/**
	 * Checks if a file exists under the workspace root.
	 * 
	 * @param filePath
	 * @return True, if the file exists; False, otherwise.
	 */
	public static boolean fileExists(String filePath) {
		if (filePath == null || "".equals(filePath)) {
			return false;
		}
		boolean fileExists = false;
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath path = new Path(filePath);

			fileExists = root.exists(path);
		} catch (Exception e) {
		}
		return fileExists;
	}
	
	  /**
	  * Fetch the entire contents of a text file, and return it in a String.
	  * This style of implementation does not throw Exceptions to the caller.
	  *
	  * @param file is a file which already exists and can be read.
	  */
	  static public String getContents(File file) throws IOException {
	    StringBuffer contents = new StringBuffer();

	    BufferedReader input = null;
	    try {
	      //FileReader always assumes default encoding is OK!
	      input = new BufferedReader( new FileReader(file) );
	      String line = null; 

	      while (( line = input.readLine()) != null){
	        contents.append(line);
	        contents.append(System.getProperty("line.separator"));
	      }
	    }
	    catch (FileNotFoundException ex) {
	      ex.printStackTrace();
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    finally {
	      try {
	        if (input!= null) {
	          input.close();
	        }
	      }
	      catch (IOException ex) {
	        throw ex;	    	 
	      }
	    }
	    return contents.toString();
	  }

	  /**
	  * Change the contents of text file in its entirety, overwriting any
	  * existing text.
	  *
	  * This style of implementation throws all exceptions to the caller.
	  *
	  * @param file is an existing file which can be written to.
	  * @throws IllegalArgumentException if param does not comply.
	  * @throws FileNotFoundException if the file does not exist.
	  * @throws IOException if problem encountered during write.
	  */
	  static public void setContents(File file, String contents)
	                                 throws FileNotFoundException, IOException {
	    if (file == null) {
	      throw new IllegalArgumentException("File should not be null.");
	    }
	    if (!file.exists()) {
	      throw new FileNotFoundException ("File does not exist: " + file);
	    }
	    if (!file.isFile()) {
	      throw new IllegalArgumentException("Should not be a directory: " + file);
	    }
	    if (!file.canWrite()) {
	      throw new IllegalArgumentException("File cannot be written: " + file);
	    }

	    Writer output = null;
	    try {
	      //FileWriter always assumes default encoding is OK!
	      output = new BufferedWriter( new FileWriter(file) );
	      output.write( contents );
	    }
	    finally {
	      if (output != null) output.close();
	    }
	  }
}
