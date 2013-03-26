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
package org.eclipse.php.internal.core.codeassist;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.SourceRange;

/**
 * Completion reporter accepts elements to be added into completion proposals
 * list.
 * 
 * @author michael
 */
public interface ICompletionReporter {

	public final static int RELEVANCE_KEYWORD = 10000000;
	public final static int RELEVANCE_VAR = 1000000;
	public final static int RELEVANCE_METHOD = 100000;
	public final static int RELEVANCE_CLASS = 10000;
	public final static int RELEVANCE_CONST = 1000;
	public final static int RELEVANCE_ADJUST = 100;

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
	public void reportResource(IModelElement model, IPath relative,
			String suffix, SourceRange replaceRange);

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
	public void reportType(IType type, String suffix, SourceRange replaceRange);

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
	public void reportType(IType type, String suffix, SourceRange replaceRange,
			Object extraInfo);

	public void reportType(IType type, String suffix, SourceRange replaceRange,
			Object extraInfo, int subRelevance);

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
	 */
	public void reportMethod(IMethod method, String suffix,
			SourceRange replaceRange);

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
	public void reportMethod(IMethod method, String suffix,
			SourceRange replaceRange, Object extraInfo);

	public void reportMethod(IMethod method, String suffix,
			SourceRange replaceRange, Object extraInfo, int subRelevance);

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
	 */
	public void reportField(IField field, String suffix,
			SourceRange replaceRange, boolean removeDollar);

	public void reportField(IField field, String suffix,
			SourceRange replaceRange, boolean removeDollar, int subRelevance);

	/**
	 * Reports PHP keyword
	 * 
	 * @param keyword
	 *            PHP keyword string
	 * @param suffix
	 *            Suffix to uppend after completion will be inserted
	 * @param replaceRange
	 *            The range in the document to be replaced with the completion
	 *            proposal text
	 */
	public void reportKeyword(String keyword, String suffix,
			SourceRange replaceRange);

	public void reportKeyword(String keyword, String suffix,
			SourceRange replaceRange, int subRelevance);

	/**
	 * @since 2.3
	 * @return
	 */
	public IModuleSource getModule();

	public void reportField(IField field, String suffix,
			SourceRange replaceRange, boolean removeDollar, int subRelevance,
			Object extraInfo);
}
