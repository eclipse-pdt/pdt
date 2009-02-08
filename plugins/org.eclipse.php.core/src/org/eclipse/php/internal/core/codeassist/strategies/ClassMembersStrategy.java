/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassStaticMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext.Trigger;


/**
 * This strategy completes class members: $a->|, A::|, etc...  
 * @author michael
 */
public abstract class ClassMembersStrategy extends AbstractCompletionStrategy {
	
	protected boolean showStaticMembers(ClassMemberContext context) {
		 return context.getTriggerType() == Trigger.CLASS; 
	}
	
	protected boolean showNonStaticMembers(ClassMemberContext context) {
		return context.getTriggerType() == Trigger.OBJECT || ((ClassStaticMemberContext)context).isParentCall();
	}
	
	protected boolean showNonStrictOptions() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID, PHPCoreConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS, false, null);
	}
}
