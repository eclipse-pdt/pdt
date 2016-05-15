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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseNameContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

/**
 * This strategy completes elements: classes, functions, variables, etc...
 * 
 * @author michael
 */
public abstract class GlobalElementStrategy extends AbstractCompletionStrategy {

	public GlobalElementStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalElementStrategy(ICompletionContext context) {
		super(context);
	}

	class AliasResolver extends PHPASTVisitor {
		boolean stop = false;
		String found = null;
		ISourceRange validRange;
		String search;

		public AliasResolver(ISourceRange validRange, String search) {
			super();
			this.validRange = validRange;
			this.search = search;
		}

		@Override
		public boolean visitGeneral(ASTNode node) throws Exception {
			if (stop || node.sourceEnd() < validRange.getOffset()
					|| node.sourceStart() > validRange.getOffset() + validRange.getLength()) {
				return false;
			}
			return super.visitGeneral(node);
		}

		@Override
		public boolean visit(UseStatement s) throws Exception {

			if (s.getStatementType() != UseStatement.T_NONE) {
				return false;
			}
			for (UsePart part : s.getParts()) {
				if (part.getNamespace() == null || part.getStatementType() != UseStatement.T_NONE) {
					continue;
				}
				String name = part.getAlias() != null ? part.getAlias().getName() : part.getNamespace().getName();
				if (name.equalsIgnoreCase(search)) {
					stop = true;
					found = part.getNamespace().getFullyQualifiedName();
					break;
				}
			}

			return false;
		}

	}

	public String extractNamespace(String prefix) {
		prefix = realPrefix(prefix);

		int pos = prefix.lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER);
		if (pos == -1) {
			return null;
		}

		String name = prefix.substring(0, pos);
		if (name.length() == 0) {
			return null;
		}

		return name;
	}

	public String resolveNamespace(String name) {
		if (!(getContext() instanceof AbstractCompletionContext)) {
			return null;
		}
		AbstractCompletionContext context = (AbstractCompletionContext) getContext();
		if (name == null) {
			return context.getCurrentNamespace();
		}
		ISourceModule sourceModule = context.getSourceModule();
		final ISourceRange validRange = context.getCurrentNamespaceRange();

		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		try {
			int searchEnd = name.indexOf(NamespaceReference.NAMESPACE_SEPARATOR);
			String search;
			if (searchEnd < 1) {
				search = name;
			} else {
				search = name.substring(0, searchEnd);
			}
			if ("namespace".equalsIgnoreCase(search)) {
				search = context.getCurrentNamespace();
				if (searchEnd < 1) {
					return search;
				} else {
					return searchEnd + NamespaceReference.NAMESPACE_SEPARATOR + name.substring(searchEnd + 1);
				}
			}
			AliasResolver aliasResolver = new AliasResolver(validRange, search);
			moduleDeclaration.traverse(aliasResolver);
			if (aliasResolver.stop) {
				if (searchEnd < 1) {
					name = aliasResolver.found;
				} else {
					name = aliasResolver.found + NamespaceReference.NAMESPACE_SEPARATOR + name.substring(searchEnd + 1);
				}

				return name;
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		if (context instanceof UseNameContext) {
			return name;
		}
		String current = context.getCurrentNamespace();

		if (current != null) {
			return current + NamespaceReference.NAMESPACE_SEPARATOR + name;
		}

		return name;
	}

	public String extractMemberName(String prefix) {
		prefix = realPrefix(prefix);

		int pos = prefix.lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER);
		if (pos == -1) {
			return prefix;
		}

		return prefix.substring(pos + 1);
	}

	public String realPrefix(String prefix) {
		prefix = prefix.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
		if (prefix.length() > 0 && prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			return prefix.substring(1);
		}

		return prefix;
	}

	public int getRelevance(String currentNamespace, IMember model) {
		IModelElement parent = model.getParent();
		try {
			if (model.getElementType() == IMember.TYPE && PHPFlags.isNamespace(model.getFlags())) {
				return -10;
			}
			if (currentNamespace == null) {
				if (parent.getElementType() == IMember.SOURCE_MODULE) {
					return 10;
				}
			} else {
				if (parent.getElementName().equalsIgnoreCase(currentNamespace)) {
					return 10;
				}
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}

		return 0;
	}
}