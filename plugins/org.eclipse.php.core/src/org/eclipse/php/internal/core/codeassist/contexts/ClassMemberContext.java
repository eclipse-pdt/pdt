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
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPHPScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This context represents state when staying in a class member completion.
 * <br/>
 * Examples:
 * 
 * <pre>
 *  1. A::|
 *  2. $this-&gt;
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public abstract class ClassMemberContext extends StatementContext implements IClassMemberContext {

	private Trigger triggerType;
	private IType[] types;
	private int elementStart;

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (this.getCompanion().getScope().findParent(Type.TRAIT_CONFLICT) != null) {
			return false;
		}

		TextSequence statementText = getStatementText();
		// boolean b = isInUseTraitStatement();
		// if (b) {
		// return false;
		// }
		// TextSequence statementText1 = getStatementText(statementText
		// .getOriginalOffset(0) - 2);
		// statementText1.toString();
		int totalLength = statementText.length();
		elementStart = PHPTextSequenceUtilities.readBackwardSpaces(statementText, totalLength);
		elementStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, elementStart, true);
		elementStart = PHPTextSequenceUtilities.readBackwardSpaces(statementText, elementStart);
		if (elementStart <= 2) { // there's no trigger of length less than 2
			// characters
			return false;
		}
		String triggerText = statementText.subSequence(elementStart - 2, elementStart).toString();
		if (triggerText.equals("->")) { //$NON-NLS-1$
			triggerType = Trigger.OBJECT;
		} else if (triggerText.equals("?->") && getCompanion().getPHPVersion().isGreaterThan(PHPVersion.PHP7_4)) { //$NON-NLS-1$
			triggerType = Trigger.OBJECT;
		} else if (triggerText.equals("::")) { //$NON-NLS-1$
			triggerType = Trigger.CLASS;
		} else {
			return false;
		}

		// to support anonymous classes fields/methods CA
		List<IType> tmpTypes = new ArrayList<>();
		try {
			IModelElement enclosingElement = getEnclosingElement();
			while (enclosingElement instanceof IMethod) {
				enclosingElement = enclosingElement.getParent();
				if (enclosingElement instanceof IField) { // anonymous function
					enclosingElement = enclosingElement.getParent();
				}
			}
			if (enclosingElement instanceof IType && PHPFlags.isAnonymous(((IType) enclosingElement).getFlags())) {
				tmpTypes.add((IType) enclosingElement);
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		tmpTypes.addAll(Arrays.asList(getCompanion().getLeftHandType(this, true)));
		types = tmpTypes.toArray(new IType[0]);
		return true;
	}

	/**
	 * Returns the start position of class/object element relative to the text
	 * sequence.
	 * 
	 * @see #getStatementText()
	 */
	public int getElementStart() {
		return elementStart;
	}

	/**
	 * Returns the left hand side possible types. For example:
	 * 
	 * <pre>
	 * 1. $a-&gt;foo() : returns possible types of the $a
	 * 2. A::foo() : returns the 'A' type
	 * 3. $a-&gt;foo()-&gt;bar() : returns possible return types of method foo() in $a
	 * etc...
	 * </pre>
	 */
	public IType[] getLhsTypes() {
		return types;
	}

	/**
	 * Returns trigger type of the class member completion
	 * 
	 * @return
	 */
	public Trigger getTriggerType() {
		return triggerType;
	}

	@Override
	public int getReplacementEnd() throws BadLocationException {
		ITextRegion phpToken = getCompanion().getPHPToken();
		if (phpToken.getType() == PHPRegionTypes.PHP_OBJECT_OPERATOR
				|| phpToken.getType() == PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM) {
			IPHPScriptRegion phpScriptRegion = getCompanion().getPHPScriptRegion();
			ITextRegion nextRegion = phpScriptRegion.getPHPToken(phpToken.getEnd());

			if (phpToken.getTextLength() == phpToken.getLength()) {
				int addOffset = 0;
				if (nextRegion.getType() == PHPRegionTypes.PHP_TOKEN
						|| nextRegion.getType() == PHPRegionTypes.PHP_SEMICOLON) {
					addOffset = phpToken.getEnd();
				} else {
					addOffset = nextRegion.getTextEnd();
				}
				return getCompanion().getRegionCollection().getStartOffset() + phpScriptRegion.getStart() + addOffset;
			}
		}
		return super.getReplacementEnd();
	}
}
