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
package org.eclipse.php.internal.debug.core.model;

public class DebugOutput {
	
    private StringBuffer fOutput = new StringBuffer(); 
    private int fUpdateCount = 0;
    
    public void append (String data){
    	fOutput.append(data); 
    }

    public int getUpdateCount () {
    	return fUpdateCount;
    }
    
    public void incrementUpdateCount () {
    	fUpdateCount++;
    }
    
    public String toString (){
    	return fOutput.toString();
    }
}
