/*******************************************************************************
 * Copyright (c) 2017 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła <zulus@w3des.net> - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.validator;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;

/**
 * @provisional
 */
public interface IValidatorVisitor {
	public interface ITypeReferenceInfo {
		public boolean isGlobal();

		public String getTypeName();

		public String getFullyQualifiedName();

		public TypeReference getTypeReference();

		public boolean isUseStatement();
	}

	public interface IUsePartInfo {
		public UsePart getUsePart();

		public boolean isAlias();

		public String getRealName();

		public String getFullyQualifiedName();

		public String getNamespaceName();

		public void increaseRefCount();

		public int getRefCount();

		public ITypeReferenceInfo getTypeReferenceInfo();
	}

	public void reportProblem(ASTNode s, String message, IProblemIdentifier id, ProblemSeverity severity);

	public void reportProblem(int start, int end, String message, IProblemIdentifier id, ProblemSeverity severity);
	
	public IUsePartInfo getUsePartInfo(String name);

	public boolean hasNamespace();

	public NamespaceDeclaration getCurrentNamespace();
}
