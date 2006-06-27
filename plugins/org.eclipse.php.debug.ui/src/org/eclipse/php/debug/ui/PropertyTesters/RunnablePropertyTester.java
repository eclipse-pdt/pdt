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
package org.eclipse.php.debug.ui.PropertyTesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.php.debug.ui.Logger;

public class RunnablePropertyTester extends PropertyTester{

    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
        Class newClass = receiver.getClass();
        String name = newClass.getName();
        if (name.equals("org.eclipse.php.core.phpModel.parser.PHPCodeDataFactory$PHPFileDataImp")){
           return false; 
        }
        
        IProject project = null;
        if (receiver instanceof IFolder){
           IFolder folder = (IFolder)receiver;
           project = folder.getProject(); 
        }
        if (receiver instanceof IProject){
           project = (IProject)receiver;
        }
        
        if (project == null || !project.isOpen()) {
        	return true;
        }
        try {
            if (project.isNatureEnabled(PHPNature.ID)){
                return false;
            }
        } catch (CoreException e) {
           Logger.logException(e);
        }
                
        return true;
    }

}
