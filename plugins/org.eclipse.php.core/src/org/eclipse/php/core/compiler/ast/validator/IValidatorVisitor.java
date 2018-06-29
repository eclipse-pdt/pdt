/*******************************************************************************
 * Copyright (c) 2017 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła <zulus@w3des.net> - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.validator;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;

/**
 * @provisional
 */
public interface IValidatorVisitor {
	public interface ITypeReferenceInfo {
		boolean isGlobal();

		String getTypeName();

		String getFullyQualifiedName();

		TypeReference getTypeReference();

		boolean isUseStatement();
	}

	public interface IUsePartInfo {
		UsePart getUsePart();

		boolean isAlias();

		String getRealName();

		String getFullyQualifiedName();

		String getNamespaceName();

		void increaseRefCount();

		int getRefCount();

		ITypeReferenceInfo getTypeReferenceInfo();
	}

	void reportProblem(ASTNode s, String message, IProblemIdentifier id, ProblemSeverity severity);

	void reportProblem(int start, int end, String message, IProblemIdentifier id, ProblemSeverity severity);

	IUsePartInfo getUsePartInfo(String name);

	boolean hasNamespace();

	NamespaceDeclaration getCurrentNamespace();

	PHPVersion getPHPVersion();
}
