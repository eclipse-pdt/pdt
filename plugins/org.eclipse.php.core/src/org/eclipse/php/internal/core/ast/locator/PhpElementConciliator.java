/*******************************************************************************
 * Copyright (c) 2009, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.locator;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;

/**
 * Conciles the php element from a given path NOTE: use the
 * {@link #concile(LinkedList)} method, to identifiy the path element
 * 
 * @author Roy, 2007
 */
public class PhpElementConciliator {

	private static final String THIS = "this"; ////$NON-NLS-1$

	public static final int CONCILIATOR_UNKNOWN = 0;
	public static final int CONCILIATOR_GLOBAL_VARIABLE = 1;
	public static final int CONCILIATOR_FUNCTION = 2;
	public static final int CONCILIATOR_LOCAL_VARIABLE = 3;
	public static final int CONCILIATOR_CLASSNAME = 4;
	public static final int CONCILIATOR_CONSTANT = 5;
	public static final int CONCILIATOR_CLASS_MEMBER = 6;
	public static final int CONCILIATOR_PROGRAM = 7;
	public static final int CONCILIATOR_TRAITNAME = 8;

	// public static final int CONCILIATOR_TRAIT_MEMBER = 9;

	public static int concile(ASTNode locateNode) {
		if (locateNode == null || isProgram(locateNode)) {
			return CONCILIATOR_PROGRAM;
		} else if (isGlobalVariable(locateNode)) {
			return CONCILIATOR_GLOBAL_VARIABLE;
		} else if (isFunction(locateNode)) {
			return CONCILIATOR_FUNCTION;
		} else if (isClassName(locateNode)) {
			return CONCILIATOR_CLASSNAME;
		} else if (isTraitName(locateNode)) {
			return CONCILIATOR_TRAITNAME;
		} else if (isConstant(locateNode)) {
			return CONCILIATOR_CONSTANT;
		} else if (isLocalVariable(locateNode)) {
			return CONCILIATOR_LOCAL_VARIABLE;
		} else if (isDispatch(locateNode)) {
			return CONCILIATOR_CLASS_MEMBER;
		} else if (isGlobalConstant(locateNode)) {
			return CONCILIATOR_CONSTANT;
		}
		return CONCILIATOR_UNKNOWN;
	}

	public static boolean isGlobalConstant(ASTNode locateNode) {
		assert locateNode != null;

		// check if it is an identifier
		if (locateNode.getType() != ASTNode.IDENTIFIER) {
			return false;
		}

		ASTNode parent = locateNode.getParent();
		if (parent.getType() != ASTNode.NAMESPACE_NAME) {
			return false;
		}

		final NamespaceName namespaceName = (NamespaceName) parent;
		parent = namespaceName.getParent();
		if (parent.getType() == ASTNode.FUNCTION_NAME
				|| parent.getType() == ASTNode.CLASS_NAME
				|| parent.getType() == ASTNode.NAMESPACE
				|| parent.getType() == ASTNode.USE_STATEMENT_PART
				|| parent.getType() == ASTNode.TRAIT_USE_STATEMENT) {
			return false;
		}

		return true;

	}

	/**
	 * Identifies a dispatch usage
	 * 
	 * @param locateNode
	 */
	private static boolean isDispatch(ASTNode locateNode) {
		assert locateNode != null;

		ASTNode parent = null;
		// check if it is an identifier
		final int type = locateNode.getType();
		if (locateNode instanceof Identifier
				&& ((Identifier) locateNode).getParent() instanceof Variable) {
			parent = (Variable) ((Identifier) locateNode).getParent();
			parent = parent.getParent();
		} else if (type == ASTNode.SINGLE_FIELD_DECLARATION
				|| type == ASTNode.FIELD_DECLARATION
				|| type == ASTNode.METHOD_DECLARATION) {
			parent = locateNode;
		} else {
			parent = locateNode.getParent();
		}

		if (parent instanceof TraitAlias) {
			// TraitAlias ta = (TraitAlias) parent;
			// return locateNode == ta.getTraitMethod();
			return true;
		}
		// check if it is a method declaration
		if (parent.getType() == ASTNode.FUNCTION_DECLARATION) {
			// check if it is a method declaration
			if (parent.getParent() != null) {
				parent = parent.getParent();
				if (parent.getType() == ASTNode.METHOD_DECLARATION) {
					return true;
				}
			}
			return false;
		}

		if (parent.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
			FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) parent;
			if (reference.getFunctionName() == locateNode) {
				return true;
			}
			return false;
		}

		if (parent.getType() == ASTNode.SINGLE_FIELD_DECLARATION) {
			// check for $this variable
			if (parent.getParent().getType() == ASTNode.FIELD_DECLARATION
					|| parent.getParent().getType() == ASTNode.SINGLE_FIELD_DECLARATION) {
				return true;
			}
		}

		if (parent.getType() == ASTNode.FIELD_DECLARATION
				|| parent.getType() == ASTNode.METHOD_DECLARATION) {
			return true;
		}

		if (parent.getType() == ASTNode.VARIABLE) {
			if (parent.getParent().getType() == ASTNode.FIELD_DECLARATION
					|| parent.getParent().getType() == ASTNode.SINGLE_FIELD_DECLARATION) {
				return true;
			}
		}

		if (parent.getType() == ASTNode.CONSTANT_DECLARATION)
			return true;

		// check if it is a dispatch
		while (parent != null) {
			if (parent instanceof Dispatch || parent instanceof StaticDispatch) {
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}

	/**
	 * Identifies a constant use
	 * 
	 * @param locateNode
	 * @return
	 */
	private static boolean isConstant(ASTNode locateNode) {
		assert locateNode != null;
		Scalar scalar = null;
		// check if it is an identifier
		if (locateNode.getType() != ASTNode.SCALAR) {
			ASTNode parent = locateNode.getParent();
			ASTNode node = null;
			// php 5.3, the parent is NamespaceName
			if (parent instanceof NamespaceName) {
				node = parent.getParent().getParent();
			} else { // non-php 5.3
				node = parent.getParent();
			}

			// check if the node is 'define'
			if ((locateNode instanceof Identifier)
					&& "define".equals(((Identifier) locateNode).getName()) //$NON-NLS-1$
					&& node instanceof FunctionInvocation) {
				FunctionInvocation inv = (FunctionInvocation) node;
				List<Expression> parameters = inv.parameters();
				if (parameters != null && parameters.size() > 0) {
					Expression param = parameters.get(0);
					if (param instanceof Scalar) {
						scalar = (Scalar) param;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			scalar = (Scalar) locateNode;
		}

		// if it is not a dollared variable - it is not a global one
		if (scalar == null || scalar.getScalarType() != Scalar.TYPE_STRING
				|| scalar.getStringValue() == null) {
			return false;
		}

		final int length = scalar.getStringValue().length() - 1;
		final char charAtBegining = scalar.getStringValue().charAt(0);
		final char charAtEnd = scalar.getStringValue().charAt(length);
		if (!detectString(charAtEnd) && !detectString(charAtBegining)) {
			return true;
		}

		// check if it is part of define
		ASTNode previous = locateNode.getParent();
		if (previous instanceof NamespaceName) {
			previous = previous.getParent();
		}
		if (previous.getType() == ASTNode.FUNCTION_NAME) {
			previous = previous.getParent();
		}

		if (previous.getType() != ASTNode.FUNCTION_INVOCATION) {
			return false;
		}

		final FunctionInvocation functionInvocation = (FunctionInvocation) previous;
		if (!(functionInvocation.getFunctionName().getName() instanceof Identifier)) {
			return false;
		}

		final Identifier identifier = (Identifier) functionInvocation
				.getFunctionName().getName();
		return "define".equalsIgnoreCase(identifier.getName()) || "constant".equalsIgnoreCase(identifier.getName()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @param locateNode
	 * @return true if the given path indicates a function
	 */
	private static boolean isClassName(ASTNode locateNode) {
		assert locateNode != null;

		ASTNode parent = null;
		if (locateNode.getType() == ASTNode.CLASS_DECLARATION
				|| locateNode.getType() == ASTNode.INTERFACE_DECLARATION) {
			parent = locateNode;
		} else if (locateNode.getType() == ASTNode.IDENTIFIER) {
			parent = locateNode.getParent();
		} else {
			return false;
		}

		if (parent.getType() == ASTNode.NAMESPACE_NAME) {
			locateNode = parent;
			parent = parent.getParent();
		}

		if (parent instanceof TraitDeclaration) {
			return false;
		}
		final int parentType = parent.getType();
		if (parentType == ASTNode.CLASS_NAME
				|| parentType == ASTNode.CLASS_DECLARATION
				|| parentType == ASTNode.INTERFACE_DECLARATION
				|| parentType == ASTNode.CATCH_CLAUSE
				|| parentType == ASTNode.FORMAL_PARAMETER
				|| parentType == ASTNode.USE_STATEMENT_PART) {
			return true;
		}

		if (parentType == ASTNode.STATIC_METHOD_INVOCATION
				|| parentType == ASTNode.STATIC_FIELD_ACCESS
				|| parentType == ASTNode.STATIC_CONSTANT_ACCESS) {
			StaticDispatch staticDispatch = (StaticDispatch) parent;
			if (staticDispatch.getClassName() == locateNode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param locateNode
	 * @return true if the given path indicates a trait
	 */
	private static boolean isTraitName(ASTNode locateNode) {
		assert locateNode != null;

		ASTNode parent = null;
		if (locateNode.getType() == ASTNode.CLASS_DECLARATION) {
			parent = locateNode;
		} else if (locateNode.getType() == ASTNode.IDENTIFIER) {
			parent = locateNode.getParent();
		} else {
			return false;
		}

		if (parent.getType() == ASTNode.NAMESPACE_NAME) {
			locateNode = parent;
			parent = parent.getParent();
		}

		final int parentType = parent.getType();
		if (parentType == ASTNode.CLASS_DECLARATION
				|| parentType == ASTNode.TRAIT_USE_STATEMENT
				|| parentType == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
			if (parentType == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
				FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) parent;
				if (reference.getFunctionName() == locateNode) {
					return false;
				}

			}
			return true;
		}

		return false;
	}

	private static boolean isLocalVariable(ASTNode locateNode) {
		assert locateNode != null;
		Variable parent = null;
		// check if it is an identifier
		if (locateNode instanceof Identifier
				&& ((Identifier) locateNode).getParent() instanceof Variable) {
			parent = (Variable) ((Identifier) locateNode).getParent();
		} else if (locateNode.getType() == ASTNode.VARIABLE) {
			parent = (Variable) locateNode;
		} else {
			return false;
		}

		// check for not variables / or $this / or field declaration
		if (!parent.isDollared() || isThisVariable(parent)
				|| parent.getType() == ASTNode.FIELD_DECLARATION) {
			return false;
		}

		// check for static variables
		if (parent.isDollared() && parent.getParent() != null
				&& parent.getParent().getType() == ASTNode.STATIC_FIELD_ACCESS) {
			return false;
		}

		// check for static array variables
		if (parent.isDollared()
				&& parent.getParent() != null
				&& parent.getParent().getType() == ASTNode.ARRAY_ACCESS
				&& parent != ((ArrayAccess) parent.getParent()).getIndex()
				&& parent.getParent().getParent().getType() == ASTNode.STATIC_FIELD_ACCESS) {
			return false;
		}
		ASTNode node = parent;
		while (node != null) {
			final int type = node.getType();
			if (type == ASTNode.FUNCTION_DECLARATION) {
				return true;
			}
			node = node.getParent();
		}
		return false;
	}

	private final static boolean isThisVariable(Variable variable) {
		return (variable.isDollared()
				&& variable.getName() instanceof Identifier && THIS
					.equalsIgnoreCase(((Identifier) variable.getName())
							.getName()));
	}

	/**
	 * @param locateNode
	 * @return true if the given path indicates a global variable
	 */
	private static boolean isGlobalVariable(ASTNode locateNode) {
		assert locateNode != null;

		// if user full selected the $a variable
		if (locateNode.getType() == ASTNode.VARIABLE) {
			return checkGlobalVariable((Variable) locateNode);
		}

		// if the cursor is in the scope of a variable identifier
		return checkGlobalIdentifier(locateNode);

	}

	private static boolean checkGlobalIdentifier(ASTNode locateNode) {
		// check if it is a GLOBALS['a'] direction
		if (locateNode.getType() == ASTNode.SCALAR) {
			Scalar scalar = (Scalar) locateNode;
			return checkGLOBALS(scalar);
		}

		// check if it is an identifier
		if (locateNode.getType() != ASTNode.IDENTIFIER) {
			return false;
		}

		final Identifier targetIdentifier = ((Identifier) locateNode);

		ASTNode parent = locateNode.getParent();
		if (parent == null || parent.getType() != ASTNode.VARIABLE) {
			return false;
		}

		final Variable variable = (Variable) parent;
		// if it is not a dollared variable - it is not a global one
		if (!variable.isDollared()
				|| variable.getParent().getType() == ASTNode.FIELD_DECLARATION) {
			return false;
		}

		// ignore static memeber call
		if (parent.getParent().getType() == ASTNode.STATIC_FIELD_ACCESS) {
			final StaticFieldAccess staticFieldAccess = (StaticFieldAccess) parent
					.getParent();
			if (staticFieldAccess.getMember() == variable) {
				return false;
			}
		}

		if (parent.getParent().getLocationInParent() == FieldsDeclaration.FIELDS_PROPERTY) {
			return false;
		}

		// check if declared global in function
		while (parent != null) {
			// if the variable was used inside a function
			if (parent.getType() == ASTNode.FUNCTION_DECLARATION) {
				// global declaration detection
				final int end = parent.getEnd();
				class GlobalSeacher extends ApplyAll {
					public int offset = end;

					public boolean apply(ASTNode node) {
						if (offset != end) {
							return false;
						}

						if (node.getType() == ASTNode.GLOBAL_STATEMENT) {
							GlobalStatement globalStatement = (GlobalStatement) node;
							if (checkGlobal(targetIdentifier, globalStatement)) {
								offset = globalStatement.getStart();
							}
						}

						return true;
					}
				}
				GlobalSeacher searchGlobal = new GlobalSeacher();
				parent.accept(searchGlobal);
				return searchGlobal.offset <= targetIdentifier.getStart();
			}
			parent = parent.getParent();
		}
		return parent == null;
	}

	private static boolean checkGlobalVariable(Variable locateNode) {
		assert locateNode.getType() == ASTNode.VARIABLE;

		if (locateNode.getParent().getType() == ASTNode.STATIC_FIELD_ACCESS) {
			return false;
		}
		return locateNode.getParent().getType() == ASTNode.GLOBAL_STATEMENT // Global
				// $a
				// case
				|| (locateNode.getEnclosingBodyNode() != null && locateNode
						.getEnclosingBodyNode().getType() == ASTNode.PROGRAM); // $a
		// declared
		// in
		// global
		// scope
		// case
	}

	/**
	 * @param scalar
	 * @return true if the scalar is defines as $GLOBALS call
	 */
	private static boolean checkGLOBALS(Scalar scalar) {
		final String stringValue = scalar.getStringValue();
		if (scalar.getScalarType() != Scalar.TYPE_STRING
				|| stringValue.length() < 3) {
			return false;
		}
		final char charAtZero = stringValue.charAt(0);
		final char charAtEnd = stringValue.charAt(stringValue.length() - 1);

		if (!detectString(charAtZero) || !detectString(charAtEnd)) {
			return false;
		}

		if (scalar.getParent().getType() == ASTNode.ARRAY_ACCESS) {
			ArrayAccess arrayAccess = (ArrayAccess) scalar.getParent();
			final Expression variableName = arrayAccess.getName();
			if (variableName.getType() == ASTNode.VARIABLE) {
				Variable var = (Variable) variableName;
				if (var.isDollared() && var.getName() instanceof Identifier) {
					final Identifier id = (Identifier) var.getName();
					return id.getName().equals("_GLOBALS") //$NON-NLS-1$
							|| id.getName().equals("GLOBALS"); //$NON-NLS-1$
				}
			}
		}
		return false;
	}

	/**
	 * @param targetIdentifier
	 * @param variable
	 * @param isGlobal
	 * @param globalStatement
	 * @return true is the
	 */
	private static boolean checkGlobal(Identifier targetIdentifier,
			final GlobalStatement globalStatement) {
		final List<Variable> variables = globalStatement.variables();
		for (final Variable current : variables) {
			// if the variable is reflection (eg. global $$var) skip
			if (current.getName().getType() == ASTNode.IDENTIFIER) {
				Identifier id = (Identifier) current.getName();

				// variables are case sensative
				if (id.getName().equals(targetIdentifier.getName())) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * @param ch
	 * @return
	 */
	private static final boolean detectString(final char ch) {
		return ch == '\'' || ch == '\"';
	}

	/**
	 * @param locateNode
	 * @return true if the given path indicates a function
	 */
	private static boolean isFunction(ASTNode locateNode) {
		assert locateNode != null;
		ASTNode parent = null;
		Identifier targetIdentifier = null;

		if (locateNode.getType() == ASTNode.FUNCTION_DECLARATION) {
			parent = ((FunctionDeclaration) locateNode);
			targetIdentifier = ((FunctionDeclaration) locateNode)
					.getFunctionName();
		} else if (locateNode instanceof Identifier
				&& !"define".equals(((Identifier) locateNode).getName())) { //$NON-NLS-1$
			targetIdentifier = (Identifier) locateNode;
			parent = targetIdentifier.getParent();
			if (parent != null && parent.getType() == ASTNode.NAMESPACE_NAME) {
				parent = targetIdentifier.getParent().getParent();
			}
		} else {
			return false;
		}

		// check if it is a function
		if (parent == null
				|| parent.getType() != ASTNode.FUNCTION_DECLARATION
				&& parent.getType() != ASTNode.FUNCTION_NAME
				|| parent.getParent().getType() == ASTNode.METHOD_DECLARATION
				|| parent.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
			return false;
		}

		if (parent instanceof TraitAlias) {
			return false;
		}
		// check if it is a method
		final int type = parent.getParent().getType();
		if (type == ASTNode.FUNCTION_INVOCATION) {
			final int parentParentType = parent.getParent().getParent()
					.getType();
			if (parentParentType == ASTNode.METHOD_DECLARATION
					|| parentParentType == ASTNode.STATIC_METHOD_INVOCATION) {
				return false;
			}
		} else if (type == ASTNode.METHOD_DECLARATION) {
			return false;
		}

		return true;
	}

	/**
	 * @param locateNode
	 * @return true if the given path indicates a program
	 */
	private static boolean isProgram(ASTNode locateNode) {
		assert locateNode != null;

		// check if it is a program
		return locateNode.getType() == ASTNode.PROGRAM;
	}

	/**
	 * 
	 * @param program
	 * @param name
	 * @return true - if the program has this constant already
	 */
	public static boolean constantAlreadyExists(Program program,
			final String name) {
		assert program != null && name != null;

		final DefinedSearcher checkConstantVisitor = new DefinedSearcher(name);
		program.accept(checkConstantVisitor);

		return checkConstantVisitor.constantAlreadyExists();
	}

	/**
	 * 
	 * @param program
	 * @param name
	 * @return true - if the program has a class name already
	 */
	public static boolean classNameAlreadyExists(Program program,
			final String name) {
		assert program != null && name != null;

		final ClassSearcher checkClassVisitor = new ClassSearcher(name);
		program.accept(checkClassVisitor);

		return checkClassVisitor.classNameAlreadyExists();
	}

	/**
	 * 
	 * @param typeDeclaration
	 *            the search scope.
	 * @param name
	 *            name to be searched.
	 * @param type
	 *            the ASTNode type.
	 * @return true - if the TypeDeclaration has a class member already.
	 */
	public static boolean classMemeberAlreadyExists(
			TypeDeclaration typeDeclaration, final String name, int type) {
		assert typeDeclaration != null && name != null;

		final ClassMemberSearcher checkClassVisitor = new ClassMemberSearcher(
				name, type);
		typeDeclaration.accept(checkClassVisitor);

		return checkClassVisitor.classMemberAlreadyExists();
	}

	/**
	 * @param path
	 * @return true if the given path indicates a global variable
	 */
	public static boolean localVariableAlreadyExists(
			FunctionDeclaration functionDeclaration, final String name) {
		assert functionDeclaration != null && name != null;

		final LocalVariableSearcher checkLocalVariable = new LocalVariableSearcher(
				name);
		functionDeclaration.accept(checkLocalVariable);

		return checkLocalVariable.localVariableAlreadyExists();
	}

	/**
	 * @param path
	 * @return true if the given path indicates a global variable
	 */
	public static boolean functionAlreadyExists(Program program,
			final String name) {
		assert program != null && name != null;

		final FunctionSearcher checkFunctionVisitor = new FunctionSearcher(name);
		program.accept(checkFunctionVisitor);

		return checkFunctionVisitor.functionAlreadyExists();
	}

	/**
	 * @param path
	 * @return true if the given path indicates a global variable
	 */
	public static boolean globalVariableAlreadyExists(Program program,
			final String name) {
		assert program != null && name != null;

		final GlobalVariableSearcher checkGlobalVisitor = new GlobalVariableSearcher(
				name);
		program.accept(checkGlobalVisitor);

		return checkGlobalVisitor.globalVariableAlreadyExists();
	}

	/**
	 * Searches for defined constant name, stops when found
	 */
	private static class DefinedSearcher extends ApplyAll {

		private boolean exists = false;
		private final String name;

		public DefinedSearcher(String name) {
			this.name = name;
		}

		public boolean apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return false;

			if (node.getType() == ASTNode.SCALAR) {
				final Scalar scalar = (Scalar) node;

				final String stringValue = scalar.getStringValue();
				if (scalar.getScalarType() != Scalar.TYPE_STRING
						|| stringValue == null) {
					return false;
				}

				final int length = stringValue.length() - 1;
				if (stringValue.charAt(0) != '"'
						&& stringValue.charAt(length) != '"'
						&& stringValue.equals(name)) {
					exists = true;
				}
			} else if (node.getType() == ASTNode.FUNCTION_INVOCATION) {
				FunctionInvocation functionInvocation = (FunctionInvocation) node;
				final Expression functionName = functionInvocation
						.getFunctionName().getName();
				if (!(functionName instanceof Identifier)) {
					return false;
				}

				final Identifier identifier = (Identifier) functionName;
				final List<Expression> parameters = functionInvocation
						.parameters();
				if (!"define".equalsIgnoreCase(identifier.getName()) || parameters == null || parameters.size() == 0) { //$NON-NLS-1$
					return false;
				}

				final Expression expression = parameters.get(0);
				if (expression.getType() != ASTNode.SCALAR) {
					return false;
				}

				Scalar scalar = (Scalar) expression;
				final String stringValue = scalar.getStringValue();
				if (stringValue.length() < 2 || stringValue.charAt(0) != '"') {
					return false;
				}
				exists = name.equals(stringValue.substring(1,
						stringValue.length() - 1));
			}
			return true;
		}

		public boolean constantAlreadyExists() {
			return exists;
		}
	}

	/**
	 * Searches for global variable with a given name, stops when found
	 */
	private static class LocalVariableSearcher extends ApplyAll {

		private boolean exists = false;
		private final String name;

		public LocalVariableSearcher(String name) {
			this.name = name;
		}

		public boolean apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return false;

			if (node.getType() == ASTNode.VARIABLE) {
				Variable variable = (Variable) node;
				if (variable.isDollared()) {
					assert variable.getName().getType() == ASTNode.IDENTIFIER;
					Identifier identifier = (Identifier) variable.getName();
					if (identifier.getName().equals(name)) {
						exists = true;
					}
				}
			}
			return true;
		}

		public boolean localVariableAlreadyExists() {
			return exists;
		}
	}

	/**
	 * Searches for class with a given name, stops when found
	 */
	private static class ClassSearcher extends ApplyAll {

		private boolean exists = false;
		private final String name;

		public ClassSearcher(String name) {
			this.name = name;
		}

		public boolean apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return false;

			if (node.getType() == ASTNode.CLASS_DECLARATION
					|| node.getType() == ASTNode.INTERFACE_DECLARATION) {
				TypeDeclaration typeDeclaration = (TypeDeclaration) node;
				if (typeDeclaration.getName().getName().equals(name)) {
					exists = true;
				}
			}

			return true;
		}

		public boolean classNameAlreadyExists() {
			return exists;
		}
	}

	/**
	 * Searches for class member with a given name and type, stops when found
	 */
	private static class ClassMemberSearcher extends ApplyAll {

		private boolean exists = false;
		private final String name;
		private int type;

		public ClassMemberSearcher(String name, int type) {
			if (name == null) {
				throw new IllegalArgumentException(
						"member name should not be null"); ////$NON-NLS-1$
			}
			this.name = name;
			this.type = type;
		}

		public boolean apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return false;

			if (node instanceof Identifier) {
				final Identifier id = (Identifier) node;
				if (name.equals(id.getName())
						&& id.getParent().getType() == type) {
					exists = true;
				}
			}

			return true;
		}

		public boolean classMemberAlreadyExists() {
			return exists;
		}
	}

	/**
	 * Searches for global variable with a given name, stops when found
	 */
	private static class FunctionSearcher extends ApplyAll {

		private boolean exists = false;
		private final String name;

		public FunctionSearcher(String name) {
			this.name = name;
		}

		public boolean apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return false;

			if (node.getType() == ASTNode.CLASS_DECLARATION) {
				// do nothing never catch method names
			} else if (node.getType() == ASTNode.FUNCTION_DECLARATION) {
				FunctionDeclaration functionDeclaration = (FunctionDeclaration) node;
				Identifier identifier = functionDeclaration.getFunctionName();
				if (identifier.getName().equalsIgnoreCase(name)) {
					exists = true;
				}
			}

			return true;
		}

		public boolean functionAlreadyExists() {
			return exists;
		}
	}

	/**
	 * Searches for global variable with a given name, stops when found
	 */
	private static class GlobalVariableSearcher extends ApplyAll {

		private boolean exists = false;
		private boolean isGlobalScope = true;
		private final String name;

		public GlobalVariableSearcher(String name) {
			this.name = name;
		}

		public boolean apply(ASTNode node) {

			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return false;

			if (node.getType() == ASTNode.CLASS_DECLARATION
					|| node.getType() == ASTNode.FUNCTION_DECLARATION) {
				isGlobalScope = false;
				node.childrenAccept(this);
				isGlobalScope = true;
				return false;
			} else if (node instanceof Identifier) {
				Identifier identifier = (Identifier) node;
				if (identifier.getParent().getType() == ASTNode.VARIABLE) {
					Variable variable = (Variable) identifier.getParent();
					if (variable.isDollared() && isGlobalScope
							&& name.equals(identifier.getName())) {
						exists = true;
					}
				}
			} else if (node.getType() == ASTNode.GLOBAL_STATEMENT) {
				GlobalStatement globalStatement = (GlobalStatement) node;
				final List<Variable> variables = globalStatement.variables();
				for (final Variable variable : variables) {
					final Expression variableName = variable.getName();
					if (variable.isDollared()
							&& variableName instanceof Identifier) {
						Identifier identifier = (Identifier) variableName;
						if (name.equals(identifier.getName())) {
							exists = true;
						}
					}
				}
			}

			return true;
		}

		public boolean globalVariableAlreadyExists() {
			return exists;
		}
	}
}
