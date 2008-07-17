/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.template.php;



/**
 * The context type for templates inside SWT code.
 * The same class is used for several context types:
 * <dl>
 * <li>templates for all Java code locations</li>
 * <li>templates for member locations</li>
 * <li>templates for statement locations</li>
 * </dl>
 * @since 3.4
 */
public class SWTContextType extends AbstractPhpContextType {
	
	/**
	 * The context type id for templates working on all Java code locations in SWT projects
	 */
	public static final String ID_ALL= "swt"; //$NON-NLS-1$

	/**
	 * The context type id for templates working on member locations in SWT projects
	 */
	public static final String ID_MEMBERS= "swt-members"; //$NON-NLS-1$
	
	/**
	 * The context type id for templates working on statement locations in SWT projects
	 */
	public static final String ID_STATEMENTS= "swt-statements"; //$NON-NLS-1$
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.corext.template.java.AbstractJavaContextType#initializeContext(org.eclipse.jdt.internal.corext.template.java.JavaContext)
	 */
	protected void initializeContext(PhpContext context) {
		if (!getId().equals(SWTContextType.ID_ALL)) { // a specific context must also allow the templates that work everywhere 
			context.addCompatibleContextType(SWTContextType.ID_ALL); 
		}
	}	
}
