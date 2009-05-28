/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassObjMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassStaticMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext.Trigger;
import org.eclipse.php.internal.core.compiler.PHPFlags;


/**
 * This strategy completes class members: $a->|, A::|, etc...  
 * @author michael
 */
public abstract class ClassMembersStrategy extends AbstractCompletionStrategy {
	
	public ClassMembersStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassMembersStrategy(ICompletionContext context) {
		super(context);
	}

	protected boolean showStaticMembers(ClassMemberContext context) {
		 return context.getTriggerType() == Trigger.CLASS || context.getPhpVersion().isGreaterThan(PHPVersion.PHP5);
	}
	
	protected boolean showNonStaticMembers(ClassMemberContext context) {
		return context.getTriggerType() == Trigger.OBJECT || isParentCall(context);
	}
	
	protected boolean isThisCall(ClassMemberContext context) {
		return ((context instanceof ClassObjMemberContext) && ((ClassObjMemberContext)context).isThisCall());
	}
	
	protected boolean isParentCall(ClassMemberContext context) {
		return ((context instanceof ClassStaticMemberContext) && ((ClassStaticMemberContext)context).isParentCall());
	}
	
	protected boolean showNonStrictOptions() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID, PHPCoreConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS, false, null);
	}
	
	/**
	 * Returns whether the specified member is visible in current context
	 * @param member
	 * @param context
	 * @return
	 * @throws ModelException
	 */
	protected boolean isVisible(IMember member, ClassMemberContext context) throws ModelException {
		if (isThisCall(context)) {
			return true;
		}
		int flags = member.getFlags();
		if (isParentCall(context)) {
			return !PHPFlags.isInternal(flags) && !PHPFlags.isPrivate(flags);
		}
		return !PHPFlags.isInternal(flags) && !PHPFlags.isPrivate(flags) && !PHPFlags.isProtected(flags);
	}
	
	/**
	 * Returns whether the specified member should be filtered from the code assist
	 * @param member
	 * @param context
	 * @return
	 * @throws ModelException 
	 */
	protected boolean isFiltered(IMember member, ClassMemberContext context) throws ModelException {
		if (!isVisible(member, context)) {
			return true;
		}
		int flags = member.getFlags();
		if (!showNonStaticMembers(context) && !PHPFlags.isStatic(flags) && !PHPFlags.isConstant(flags)) {
			return true;
		}
		if (!showStaticMembers(context) && (PHPFlags.isStatic(flags) || PHPFlags.isConstant(flags))) {
			return true;
		}
		return false;
	}
}
