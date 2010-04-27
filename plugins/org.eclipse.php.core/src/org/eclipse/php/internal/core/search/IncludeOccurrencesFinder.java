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
package org.eclipse.php.internal.core.search;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.AbstractSourceModule;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.ast.nodes.*;

public class IncludeOccurrencesFinder extends AbstractOccurrencesFinder {

	private static final String INCLUDE_POINT_OF = CoreMessages
			.getString("IncludeOccurrencesFinder.0"); //$NON-NLS-1$
	public static final String ID = "RequireFinder"; //$NON-NLS-1$
	private IModelElement source;
	private IBinding binding;
	private Include includeNode;
	private IType[] types;
	private IMethod[] methods;

	/**
	 * @param root
	 *            the AST root
	 * @param node
	 *            the selected node
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;

		this.includeNode = getIncludeExpression(node);
		if (this.includeNode != null) {
			binding = includeNode.resolveBinding();
			if (binding == null) {
				return null;
			}
			source = binding.getPHPElement();
			if (source != null) {
				AbstractSourceModule module = (AbstractSourceModule) source;
				try {
					this.types = module.getTypes();
					this.methods = module.getMethods();
					return null;
				} catch (ModelException e) {
					fDescription = "MethodExitsFinder_occurrence_exit_description"; //$NON-NLS-1$
					return fDescription;
				}
			}

		}
		fDescription = "MethodExitsFinder_occurrence_exit_description"; //$NON-NLS-1$
		return fDescription;
	}

	private final Include getIncludeExpression(ASTNode node) {
		boolean isInclude = (node != null && node.getType() == ASTNode.INCLUDE);
		if (isInclude) {
			return (Include) node;
		}
		ASTNode parent = ASTNodes.getParent(node, Include.class);
		return (parent != null && parent.getType() == ASTNode.INCLUDE) ? (Include) parent
				: null;
	}

	protected void findOccurrences() {
		if (source == null) {
			return;
		}
		fDescription = Messages.format(INCLUDE_POINT_OF, this.source
				.getElementName());
		getASTRoot().accept(this);
		int offset = includeNode.getStart();
		int length = includeNode.getLength();
		fResult.add(new OccurrenceLocation(offset, length,
				getOccurrenceType(null), fDescription));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#
	 * getOccurrenceReadWriteType
	 * (org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		return IOccurrencesFinder.K_OCCURRENCE;
	}

	public String getElementName() {
		return binding.getName();
	}

	public String getID() {
		return ID;
	}

	public String getJobLabel() {
		return "RncludeFinder_job_label"; //$NON-NLS-1$
	}

	public int getSearchKind() {
		return IOccurrencesFinder.K_EXIT_POINT_OCCURRENCE;
	}

	public String getUnformattedPluralLabel() {
		return "IncludeFinder_label_plural"; //$NON-NLS-1$
	}

	public String getUnformattedSingularLabel() {
		return "IncludeFinder_label_singular"; //$NON-NLS-1$
	}

	@Override
	public boolean visit(ClassName className) {
		Expression className2 = className.getName();
		if (className2.getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) className2;
			String name = id.getName();
			for (IType type : types) {
				if (type.getElementName().equals(name))
					fResult.add(new OccurrenceLocation(className.getStart(),
							className.getLength(), getOccurrenceType(null),
							fDescription));
			}
		}
		return false;
	}

	@Override
	public boolean visit(Identifier className) {
		final StructuralPropertyDescriptor location = className
				.getLocationInParent();
		if (location == ClassDeclaration.SUPER_CLASS_PROPERTY
				|| location == ClassDeclaration.INTERFACES_PROPERTY
				|| location == StaticMethodInvocation.CLASS_NAME_PROPERTY
				|| location == FormalParameter.PARAMETER_TYPE_PROPERTY) {
			String name = className.getName();
			for (IType type : types) {
				if (type.getElementName().equals(name))
					fResult.add(new OccurrenceLocation(className.getStart(),
							className.getLength(), getOccurrenceType(null),
							fDescription));
			}
		}
		return false;
	}

	@Override
	public boolean visit(FunctionInvocation functionInvocation) {
		Expression functionName2 = functionInvocation.getFunctionName()
				.getName();
		if (functionName2.getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) functionName2;
			String name = id.getName();
			for (IMethod method : methods) {
				if (method.getElementName().equals(name))
					fResult.add(new OccurrenceLocation(functionInvocation
							.getStart(), functionInvocation.getLength(),
							getOccurrenceType(null), fDescription));
			}
		}
		return true;
	}
}
