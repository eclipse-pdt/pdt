/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.HashSet;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.corext.util.TypeFilter;
import org.eclipse.dltk.ui.DLTKUILanguageManager;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class SimilarElementsRequestor extends CompletionRequestor {

	public static final int CLASSES = 1 << 1;
	public static final int INTERFACES = 1 << 2;
	public static final int REF_TYPES = CLASSES | INTERFACES;
	public static final int ALL_TYPES = REF_TYPES;

	public static final String ENCLOSING_TYPE_SEPARATOR = new String(
			new char[] { NamespaceReference.NAMESPACE_SEPARATOR });

	private int fKind;
	private String fName;
	private static final TypeFilter filter = new TypeFilter(DLTKUILanguageManager.getLanguageToolkit(PHPNature.ID));

	private HashSet<SimilarElement> fResult;

	public static SimilarElement[] findSimilarElement(ISourceModule cu, Identifier name, int kind)
			throws ModelException {
		String identifier = PHPModelUtils.extractElementName(name.getName());

		int pos = name.getStart() + 1;

		SimilarElementsRequestor requestor = new SimilarElementsRequestor(identifier, kind);
		requestor.setIgnored(CompletionProposal.KEYWORD, true);
		requestor.setIgnored(CompletionProposal.LABEL_REF, true);
		requestor.setIgnored(CompletionProposal.PACKAGE_REF, true);
		requestor.setIgnored(CompletionProposal.VARIABLE_DECLARATION, true);
		requestor.setIgnored(CompletionProposal.FIELD_REF, true);
		requestor.setIgnored(CompletionProposal.LOCAL_VARIABLE_REF, true);
		requestor.setIgnored(CompletionProposal.VARIABLE_DECLARATION, true);
		requestor.setIgnored(CompletionProposal.VARIABLE_DECLARATION, true);
		requestor.setIgnored(CompletionProposal.POTENTIAL_METHOD_DECLARATION, true);
		requestor.setIgnored(CompletionProposal.METHOD_NAME_REFERENCE, true);
		return requestor.process(cu, pos);
	}

	/**
	 * Constructor for SimilarElementsRequestor.
	 * 
	 * @param name
	 *            the name
	 * @param kind
	 *            the type kind
	 */
	private SimilarElementsRequestor(String name, int kind) {
		super();
		fName = name;
		fKind = kind;

		fResult = new HashSet<SimilarElement>();
	}

	private void addResult(SimilarElement elem) {
		fResult.add(elem);
	}

	private SimilarElement[] process(ISourceModule cu, int pos) throws ModelException {
		try {
			cu.codeComplete(pos, this, 5000);
			return fResult.toArray(new SimilarElement[fResult.size()]);
		} finally {
			fResult.clear();
		}
	}

	private boolean isKind(int kind) {
		return (fKind & kind) != 0;
	}

	private static final int getKind(int flags, char[] typeNameSig) {
		if (Flags.isInterface(flags)) {
			return INTERFACES;
		}
		return CLASSES;
	}

	private void addType(char[] typeNameSig, int flags, int relevance) {
		int kind = getKind(flags, typeNameSig);
		if (!isKind(kind)) {
			return;
		}
		String fullName = new String(typeNameSig);
		if (filter.isFiltered(fullName)) {
			return;
		}
		if (NameMatcher.isSimilarName(fName, PHPModelUtils.extractElementName(fullName))) {
			addResult(new SimilarElement(kind, fullName, relevance));
		}
	}

	@Override
	public void accept(CompletionProposal proposal) {
		if (proposal.getKind() == CompletionProposal.TYPE_REF || proposal.getKind() == CompletionProposal.METHOD_REF) {
			String name = null;
			IModelElement element = proposal.getModelElement();
			if (element instanceof IType || element instanceof FakeConstructor) {
				IMember type = (IMember) element;
				try {
					if (!PHPFlags.isNamespace(type.getFlags())) {
						if (element instanceof FakeConstructor) {
							element = ((FakeConstructor) element).getParent();
						}
						name = ((IType) element).getTypeQualifiedName(ENCLOSING_TYPE_SEPARATOR);
					}
				} catch (ModelException e) {
				}
			} else if (element instanceof FakeMethod) {
				try {
					name = ((IMethod) element).getTypeQualifiedName(ENCLOSING_TYPE_SEPARATOR, false);
				} catch (ModelException e) {
					PHPUiPlugin.log(e);
				}
			}
			if (name != null) {
				addType(name.toCharArray(), proposal.getFlags(), proposal.getRelevance());
			}
		}
	}
}
