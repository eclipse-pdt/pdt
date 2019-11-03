/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.hierarchy.FakeType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext.Trigger;
import org.eclipse.php.internal.core.codeassist.contexts.ClassStaticMemberContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This strategy completes class constants and variables.
 * 
 * @author michael
 */
public class ClassFieldsStrategy extends ClassMembersStrategy {

	private static final String CLASS_KEYWORD = "class"; //$NON-NLS-1$
	private static final String STD_CLASS = "stdClass"; //$NON-NLS-1$

	public ClassFieldsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassFieldsStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof ClassMemberContext)) {
			return;
		}

		ClassMemberContext concreteContext = (ClassMemberContext) context;
		CompletionRequestor requestor = concreteContext.getCompletionRequestor();

		String prefix = concreteContext.getPrefix();
		ISourceRange replaceRange = getReplacementRange(concreteContext);

		List<IField> result = new LinkedList<>();
		boolean inUseTrait = getCompanion().getScope().findParent(Type.TRAIT_USE) != null;
		for (IType type : concreteContext.getLhsTypes()) {
			try {
				ITypeHierarchy hierarchy = getCompanion().getSuperTypeHierarchy(type, null);
				IField[] fields = null;
				if (concreteContext instanceof ClassStaticMemberContext
						&& concreteContext.getTriggerType() == Trigger.CLASS
						&& ((ClassStaticMemberContext) concreteContext).isParent()) {
					List<IField> superTypes = new ArrayList<>();
					for (IType currType : hierarchy.getAllSupertypes(type)) {
						superTypes.addAll(Arrays.asList(
								PHPModelUtils.getTypeField(currType, prefix, requestor.isContextInformationMode())));
					}

					fields = superTypes.toArray(new IField[superTypes.size()]);
				} else {
					fields = PHPModelUtils.getTypeHierarchyField(type, hierarchy, prefix,
							requestor.isContextInformationMode(), null);
				}

				for (IField field : removeOverriddenElements(Arrays.asList(fields))) {
					if (inUseTrait) {
						result.add(field);
					} else if (!isFiltered(field, type, concreteContext)) {
						result.add(field);
					}
				}
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}
		}
		if (concreteContext instanceof ClassStaticMemberContext && concreteContext.getTriggerType() == Trigger.CLASS
				&& !inUseTrait && PHPVersion.PHP5_4.isLessThan(getCompanion().getPHPVersion())
				&& (CLASS_KEYWORD.startsWith(prefix.toLowerCase()) || CLASS_KEYWORD.equals(prefix.toLowerCase()))) {
			try {
				ITextRegion phpToken = getCompanion().getPHPScriptRegion()
						.getPHPToken(getCompanion().getPHPToken().getStart() - 1);
				if (PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM.equals(phpToken.getType())) {
					phpToken = getCompanion().getPHPToken(phpToken.getStart() - 1);
				}

				if (isStaticCall(phpToken.getType())) {
					result.add(new FakeField(new FakeType((ModelElement) getCompanion().getSourceModule(), STD_CLASS),
							CLASS_KEYWORD, Modifiers.AccConstant | Modifiers.AccPublic));
				}
			} catch (BadLocationException e) {
				Logger.logException(e);
			}
		}

		for (IField field : result) {
			reporter.reportField(field, getSuffix(), replaceRange, concreteContext.getTriggerType() == Trigger.OBJECT);
		}
	}

	private boolean isStaticCall(String type) {
		return PHPRegionTypes.PHP_LABEL.equals(type) || PHPRegionTypes.PHP_PARENT.equals(type)
				|| PHPRegionTypes.PHP_SELF.equals(type) || PHPRegionTypes.PHP_NS_SEPARATOR.equals(type)
				|| PHPRegionTypes.PHP_STATIC.equals(type);
	}

	@Override
	protected boolean showNonStaticMembers(ClassMemberContext context) {
		return super.showNonStaticMembers(context) && !isParentCall(context);
	}

	public String getSuffix() {
		return ""; //$NON-NLS-1$
	}
}