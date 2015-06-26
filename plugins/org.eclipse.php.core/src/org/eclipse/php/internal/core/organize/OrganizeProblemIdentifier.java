/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *     Yannick de Lange <yannick.l.88@gmail.com>
 *******************************************************************************/
package org.eclipse.php.internal.core.organize;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemIdentifierExtension;
import org.eclipse.php.internal.core.PHPCorePlugin;

/**
 * Class provides "Organize Syntax Error" marker type for DLTK problem
 * mechanism.
 *
 * @author Yannick de Lange
 */
public enum OrganizeProblemIdentifier
	implements IProblemIdentifier,IProblemIdentifierExtension {

	USE_STATEMENTS;

	public static final String MARKER_TYPE_ID = "org.eclipse.php.core.organizeproblemmarker"; //$NON-NLS-1$

	@Override
	public String contributor() {
		return PHPCorePlugin.ID;
	}

	@Override
	public String getMarkerType() {
		return MARKER_TYPE_ID;
	}

}
