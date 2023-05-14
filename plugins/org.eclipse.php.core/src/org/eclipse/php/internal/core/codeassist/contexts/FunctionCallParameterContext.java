/*******************************************************************************
 * Copyright (c) Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestor;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.format.PHPHeuristicScanner;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This context represents state when staying in a function/method call <br/>
 * Examples:
 * 
 * <pre>
 *  call(|)
 *  $obj->call(|)
 *  $obj::call(|)
 *  $obj?->call(|)
 * </pre>
 * 
 * @author Dawid Pakuła
 */
public class FunctionCallParameterContext extends StatementContext {

	private String callName;
	private int callNameOffset = -1;
	private boolean isConstructor = false;
	private boolean isMethod = false;
	private IMethod[] method;

	@SuppressWarnings("null")
	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (!(requestor instanceof IPHPCompletionRequestor)) {
			return false;
		}
		IPHPCompletionRequestor phpReq = (IPHPCompletionRequestor) requestor;

		PHPHeuristicScanner scanner;
		try {
			if (phpReq.getDocument().getChar(offset) == ')') { // we are before
																// end
				offset--;
			}

			scanner = PHPHeuristicScanner.createHeuristicScanner(phpReq.getDocument(), offset, true);
			int open = scanner.findOpeningPeer(offset, PHPHeuristicScanner.UNBOUND, '(', ')');

			if (open == -1) {
				return false;
			}
			open--;
			open = scanner.findNonWhitespaceBackward(open, PHPHeuristicScanner.UNBOUND);
			if (open == PHPHeuristicScanner.NOT_FOUND) {
				return false;
			}
			ITextRegion textRegion = scanner.getTextRegion(open);
			if (textRegion == null) {
				return false;
			}
			if (!textRegion.getType().equals(PHPRegionTypes.PHP_LABEL)) {
				return false;
			}
			StringBuilder sb = new StringBuilder();
			do {
				callNameOffset = getCompanion().getPHPScriptRegion().getStart() + textRegion.getStart();
				sb.insert(0, phpReq.getDocument().get(callNameOffset, textRegion.getLength()));
				open = scanner.findNonWhitespaceBackward(scanner.getPosition() - textRegion.getLength(),
						PHPHeuristicScanner.UNBOUND);
				if (open == PHPHeuristicScanner.NOT_FOUND) {
					return false;
				}
				textRegion = scanner.getTextRegion(open);
			} while (textRegion.getType() == PHPRegionTypes.PHP_LABEL
					|| textRegion.getType() == PHPRegionTypes.PHP_NS_SEPARATOR
					|| textRegion.getType() == PHPRegionTypes.PHP_NAMESPACE);
			callName = sb.toString();
			if (open == PHPHeuristicScanner.NOT_FOUND) {
				return false;
			}
			if (textRegion.getType() == PHPRegionTypes.PHP_FUNCTION) {
				return false;
			}
			if (textRegion.getType().equals(PHPRegionTypes.PHP_OBJECT_OPERATOR)
					|| textRegion.getType().equals(PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM)) {
				isMethod = true;

				return true;
			} else if (textRegion.getType() == PHPRegionTypes.PHP_ATTRIBUTE
					|| textRegion.getType() == PHPRegionTypes.PHP_NEW) {
				isConstructor = true;
				return true;
			} else if (getCompanion().getScope().getType() == Type.HEAD
					&& getCompanion().getScope().getParent().getType() == Type.ATTRIBUTE) {
				isConstructor = true;
				return true;
			}

			if (callName.charAt(0) != '\\') {
				String alias = callName;
				int nsPos = callName.indexOf('\\');
				if (nsPos != -1) {
					alias = callName.substring(0, nsPos);
				}
				if (alias.equals("namespace")) { //$NON-NLS-1$
					callName = getCompanion().getCurrentNamespace() + callName.substring(nsPos);
				} else {
					String ns = getCompanion().isGlobalNamespace() ? "" : getCompanion().getCurrentNamespace();
					alias = PHPModelUtils.getRealName(ns, alias, sourceModule, offset, alias);
					if (nsPos != -1) {
						callName = alias + callName.substring(nsPos);
					} else {
						callName = alias;
					}
				}
			}

			return true;
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return false;
	}

	public IMethod[] getMethod() {
		if (method == null) {
			try {
				method = resolveMethod();
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
				method = new IMethod[] {};
			}
		}
		return method;
	}

	private IMethod[] resolveMethod() throws ModelException {
		if (isMethod) {
			return resolveObjectCall();
		} else if (isConstructor) {
			return resolveConstructor();
		} else {
			return resolveFunction();
		}

	}

	@SuppressWarnings("null")
	private IMethod[] resolveObjectCall() throws ModelException {
		TextSequence statement = PHPTextSequenceUtilities.getStatement(callNameOffset,
				getCompanion().getStructuredDocumentRegion(), true);
		IType[] type = CodeAssistUtils.getTypesFor(getCompanion().getSourceModule(), statement, statement.length(),
				callNameOffset);

		return PHPModelUtils.getTypesMethod(type, callName, true);
	}

	private IMethod[] resolveConstructor() throws ModelException {
		IType[] types = PHPModelUtils.getTypes(callName, getCompanion().getSourceModule(), callNameOffset, null);

		IMethod[] list = PHPModelUtils.getTypesMethod(types, "__construct", true);
		if (list.length == 0) {
			list = PHPModelUtils.getTypesMethod(types, PHPModelUtils.extractElementName(callName), true);
		}
		return list;
	}

	private IMethod[] resolveFunction() throws ModelException {
		boolean isGlobal = callName.charAt(0) == '\\';
		String testName = isGlobal ? callName.substring(1) : callName;
		String namespaceName = PHPModelUtils.extractNameSpaceName(testName);
		String elementName = PHPModelUtils.extractElementName(testName);
		IMethod[] list = null;
		if (namespaceName != null) {
			list = PHPModelUtils.getNamespaceFunction(namespaceName, elementName, true,
					getCompanion().getSourceModule(), null);
			if (isGlobal || list.length > 0) {
				return list;
			}
		} else if (!isGlobal && getCompanion().getCurrentNamespace() != null) {
			list = PHPModelUtils.getNamespaceFunction(getCompanion().getCurrentNamespace(), elementName, true,
					getCompanion().getSourceModule(), null);
			if (list.length > 0) {
				return list;
			}
		}

		return PHPModelUtils.getFunctions(elementName, getCompanion().getSourceModule(), callNameOffset, null);
	}

	public boolean isFunction() {
		return !isConstructor && !isMethod;
	}

	public boolean isConstructor() {
		return isConstructor;
	}

	public boolean isMethod() {
		return isMethod;
	}

}
