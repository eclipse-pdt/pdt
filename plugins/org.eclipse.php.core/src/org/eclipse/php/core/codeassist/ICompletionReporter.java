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
package org.eclipse.php.core.codeassist;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;

/**
 * Completion reporter accepts elements to be added into completion proposals
 * list.
 * 
 * @author michael
 */
public interface ICompletionReporter {

	int RELEVANCE_KEYWORD = 10000000;
	int RELEVANCE_VAR = 1000000;
	int RELEVANCE_METHOD = 100000;
	int RELEVANCE_CLASS = 10000;
	int RELEVANCE_CONST = 1000;
	int RELEVANCE_ADJUST = 100;

	/**
	 * Reports a PHP resource (file or folder)
	 * 
	 * @param model
	 *            PHP file or folder
	 * @param relative
	 *            a relative PHP where this resource is added
	 * @param suffix
	 *            Suffix to append after completion will be inserted
	 * @param replaceRange
	 *            The range in the document to be replaced with the completion
	 *            proposal text
	 */
	void reportResource(IModelElement model, IPath relative, String suffix, ISourceRange replaceRange);

	/**
	 * Reports type: interface, namespace or class
	 * 
	 * @param type
	 *            PHP class, interface or function
	 * @param suffix
	 *            Suffix to uppend after completion will be inserted
	 * @param replaceRange
	 *            The range in the document to be replaced with the completion
	 *            proposal text
	 */
	void reportType(IType type, String suffix, ISourceRange replaceRange);

	/**
	 * Reports type: interface, namespace or class
	 * 
	 * @param type
	 *            PHP class, interface or function
	 * @param suffix
	 *            Suffix to uppend after completion will be inserted
	 * @param replaceRange
	 *            The range in the document to be replaced with the completion
	 *            proposal text
	 * @param extraInfo
	 *            extraInfo for CompletionProposal
	 */
	void reportType(IType type, String suffix, ISourceRange replaceRange, Object extraInfo);

	void reportType(IType type, String suffix, ISourceRange replaceRange, Object extraInfo, int subRelevance);

	void reportType(IType type, String prefix, String suffix, ISourceRange replaceRange, Object extraInfo,
			int subRelevance);

	/**
	 * Reports method or function
	 * 
	 * @param method
	 *            PHP method or function
	 * @param suffix
	 *            Suffix to uppend after completion will be inserted
	 * @param replaceRange
	 *            The range in the document to be replaced with the completion
	 *            proposal text
	 * @deprecated
	 */
	@Deprecated
	void reportMethod(IMethod method, String suffix, ISourceRange replaceRange);

	/**
	 * Reports method or function
	 * 
	 * @param method
	 *            PHP method or function
	 * @param suffix
	 *            Suffix to uppend after completion will be inserted
	 * @param replaceRange
	 *            The range in the document to be replaced with the completion
	 *            proposal text
	 * @param extraInfo
	 *            extraInfo for CompletionProposal
	 */
	void reportMethod(IMethod method, String suffix, ISourceRange replaceRange, Object extraInfo);

	void reportMethod(IMethod method, String suffix, ISourceRange replaceRange, Object extraInfo, int subRelevance);

	void reportMethod(IMethod method, String prefix, String suffix, ISourceRange replaceRange, Object extraInfo,
			int subRelevance);

	/**
	 * Reports field: variable, constant
	 * 
	 * @param field
	 *            PHP variable or constant
	 * @param suffix
	 *            Suffix to uppend after completion will be inserted
	 * @param replaceRange
	 *            The range in the document to be replaced with the completion
	 *            proposal text
	 * @param removeDollar
	 *            Remove dollar from the variable in completion
	 * @param subRelevance
	 * @param extraInfo
	 *            extraInfo for CompletionProposal
	 */
	void reportField(IField field, String suffix, ISourceRange replaceRange, boolean removeDollar, int subRelevance,
			Object extraInfo);

	void reportField(IField field, String suffix, ISourceRange replaceRange, boolean removeDollar);

	void reportField(IField field, String prefix, String suffix, ISourceRange replaceRange, int subRelevance,
			Object extraInfo);

	/**
	 * Reports PHP keyword
	 * 
	 * @param keyword
	 *            PHP keyword string
	 * @param suffix
	 *            Suffix to append after completion will be inserted
	 * @param replaceRange
	 *            The range in the document to be replaced with the completion
	 *            proposal text
	 */
	void reportKeyword(String keyword, String suffix, ISourceRange replaceRange);

	void reportKeyword(String keyword, String suffix, ISourceRange replaceRange, int subRelevance);

	/**
	 * @since 2.3
	 * @return
	 */
	IModuleSource getModule();
}
