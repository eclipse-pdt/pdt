/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.locator;

import java.util.LinkedList;

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;

/**
 * Conciles the php element from a given path
 * NOTE: use the {@link #concile(LinkedList)} method, to identifiy the path element   
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
	public static final int CONCILIATOR_CLASS_PROPERTY = 6;
	public static final int CONCILIATOR_PROGRAM = 7;

	public static int concile(ASTNode locateNode) {
		if (locateNode == null || isProgram(locateNode)) {
			return CONCILIATOR_PROGRAM;
		}else if (isGlobalVariable(locateNode)) {
			return CONCILIATOR_GLOBAL_VARIABLE;
		} else if (isFunction(locateNode)) {
			return CONCILIATOR_FUNCTION;
		} else if (isClassName(locateNode)) {
			return CONCILIATOR_CLASSNAME;
		} else if (isConstant(locateNode)) {
			return CONCILIATOR_CONSTANT;
		} else if (isLocalVariable(locateNode)) {
			return CONCILIATOR_LOCAL_VARIABLE;
		} else if (isDispatch(locateNode)) {
			return CONCILIATOR_CLASS_PROPERTY;
		}
		return CONCILIATOR_UNKNOWN;
	}

	/**
	 * Identifies a dispatch usage 
	 * @param locateNode
	 */
	private static boolean isDispatch(ASTNode node) {
		assert node != null;

		// check if it is an identifier
		if (node.getType() != ASTNode.IDENTIFIER) {
			return false;
		}

		ASTNode parent = node.getParent();

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

		if (parent.getType() == ASTNode.VARIABLE) {

			// check for $this variable
			final Identifier id = (Identifier) node;
			final Variable variable = (Variable) parent;
			
			if (id.getName().equals(THIS) && variable.isDollared()) {
				return false;
			}
			
			if (parent.getParent().getType() == ASTNode.FIELD_DECLARATION) {
				return true;
			}
		}
		
		if(parent.getType() == ASTNode.CLASS_CONSTANT_DECLARATION)
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
	 * @param locateNode
	 * @return
	 */
	private static boolean isConstant(ASTNode locateNode) {
		assert locateNode != null;

		// check if it is an identifier
		if (locateNode.getType() != ASTNode.SCALAR) {
			return false;
		}
		final Scalar scalar = (Scalar) locateNode;

		// if it is not a dollared variable - it is not a global one
		if (scalar.getScalarType() != Scalar.TYPE_STRING || scalar.getStringValue() == null) {
			return false;
		}

		final int length = scalar.getStringValue().length() - 1;
		final char charAtBegining = scalar.getStringValue().charAt(0);
		final char charAtEnd = scalar.getStringValue().charAt(length);
		if (!detectString(charAtEnd) && !detectString(charAtBegining)) {
			return true;
		}

		// check if it is part of define
		final ASTNode previous = locateNode.getParent();
		if (previous.getType() != ASTNode.FUNCTION_INVOCATION) {
			return false;
		}

		final FunctionInvocation functionInvocation = (FunctionInvocation) previous;
		if (functionInvocation.getFunctionName().getFunctionName().getType() != ASTNode.IDENTIFIER) {
			return false;
		}

		final Identifier identifier = (Identifier) functionInvocation.getFunctionName().getFunctionName();
		return "define".equalsIgnoreCase(identifier.getName());
	}

	/**
	 * @param locateNode
	 * @return true if the given path indicates a function
	 */
	private static boolean isClassName(ASTNode locateNode) {
		assert locateNode != null;

		// check if it is an identifier
		if (locateNode.getType() != ASTNode.IDENTIFIER) {
			return false;
		}

		ASTNode parent = locateNode.getParent();
		final int parentType = parent.getType();
		if (parentType == ASTNode.CLASS_NAME || parentType == ASTNode.CLASS_DECLARATION || parentType == ASTNode.INTERFACE_DECLARATION || parentType == ASTNode.CATCH_CLAUSE || parentType == ASTNode.FORMAL_PARAMETER) {
			return true;
		}

		if (parentType == ASTNode.STATIC_METHOD_INVOCATION || parentType == ASTNode.STATIC_FIELD_ACCESS || parentType == ASTNode.STATIC_CONSTANT_ACCESS) {
			StaticDispatch staticDispatch = (StaticDispatch) parent;
			if (staticDispatch.getClassName() == locateNode) {
				return true;
			}
		}
		return false;
	}

	private static boolean isLocalVariable(ASTNode locateNode) {
		assert locateNode != null;
		// check if it is an identifier
		if (locateNode.getType() != ASTNode.IDENTIFIER) {
			return false;
		}
		final Identifier targetIdentifier = (Identifier) locateNode;

		if (targetIdentifier.getParent().getType() != ASTNode.VARIABLE) {
			return false;
		}

		Variable parent = (Variable) targetIdentifier.getParent();

		// check for not variables / or $this / or field declaration 
		if (!parent.isDollared() || targetIdentifier.getName().equals(THIS) || parent.getType() == ASTNode.FIELD_DECLARATION) {
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

	/**
	 * @param locateNode
	 * @return true if the given path indicates a global variable
	 */
	private static boolean isGlobalVariable(ASTNode locateNode) {
		assert locateNode != null;

		// check if it is a GLOBALS['a'] direction
		if (locateNode.getType() == ASTNode.SCALAR) {
			Scalar scalar = (Scalar) locateNode;
			return checkGLOBALS(scalar);
		}

		// check if it is an identifier
		if (locateNode.getType() != ASTNode.IDENTIFIER) {
			return false;
		}
		final Identifier targetIdentifier = (Identifier) locateNode;

		ASTNode parent = locateNode.getParent();
		if (parent.getType() != ASTNode.VARIABLE) {
			return false;
		}

		final Variable variable = (Variable) parent;
		// if it is not a dollared variable - it is not a global one
		if (!variable.isDollared() || variable.getParent().getType() == ASTNode.FIELD_DECLARATION) {
			return false;
		}

		// ignore static memeber call
		if (parent.getParent().getType() == ASTNode.STATIC_FIELD_ACCESS) {
			final StaticFieldAccess staticFieldAccess = (StaticFieldAccess) parent.getParent();
			if (staticFieldAccess.getProperty() == variable) {
				return false;
			}
		}

		// check if declared global in function
		while (parent != null) {
			// if the variable was used inside a function
			if (parent.getType() == ASTNode.FUNCTION_DECLARATION) {
				// global declaration detection
				final int end = parent.getEnd();
				class GlobalSeacher extends ApplyAll {
					public int offset = end;

					public void apply(ASTNode node) {
						if (offset != end) {
							return;
						}

						if (node.getType() == ASTNode.GLOBAL_STATEMENT) {
							GlobalStatement globalStatement = (GlobalStatement) node;
							if (checkGlobal(targetIdentifier, globalStatement)) {
								offset = globalStatement.getStart();
							}
						}

						node.childrenAccept(this);
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

	/**
	 * @param scalar
	 * @return true if the scalar is defines as $GLOBALS call
	 */
	private static boolean checkGLOBALS(Scalar scalar) {
		final String stringValue = scalar.getStringValue();
		if (scalar.getScalarType() != Scalar.TYPE_STRING || stringValue.length() < 3) {
			return false;
		}
		final char charAtZero = stringValue.charAt(0);
		final char charAtEnd = stringValue.charAt(stringValue.length() - 1);

		if (!detectString(charAtZero) || !detectString(charAtEnd)) {
			return false;
		}

		if (scalar.getParent().getType() == ASTNode.ARRAY_ACCESS) {
			ArrayAccess arrayAccess = (ArrayAccess) scalar.getParent();
			final Expression variableName = arrayAccess.getVariableName();
			if (variableName.getType() == ASTNode.VARIABLE) {
				Variable var = (Variable) variableName;
				if (var.isDollared() && var.getVariableName().getType() == ASTNode.IDENTIFIER) {
					final Identifier id = (Identifier) var.getVariableName();
					if (id.getName().equals("GLOBALS")) {
						return true;
					}
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
	private static boolean checkGlobal(Identifier targetIdentifier, final GlobalStatement globalStatement) {
		final Variable[] variables = globalStatement.getVariables();
		for (int j = 0; j < variables.length; j++) {
			final Variable current = variables[j];

			assert current.getVariableName().getType() == ASTNode.IDENTIFIER;
			Identifier id = (Identifier) current.getVariableName();

			// variables are case sensative
			if (id.getName().equals(targetIdentifier.getName())) {
				return true;
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

		// check if it is an identifier
		if (locateNode.getType() != ASTNode.IDENTIFIER) {
			return false;
		}
		final Identifier targetIdentifier = (Identifier) locateNode;

		// check if it is a function
		final ASTNode parent = targetIdentifier.getParent();
		if (parent.getType() != ASTNode.FUNCTION_DECLARATION && parent.getType() != ASTNode.FUNCTION_NAME) {
			return false;
		}

		// check if it is a method
		final int type = parent.getParent().getType();
		if (type == ASTNode.FUNCTION_INVOCATION) {
			final int parentParentType = parent.getParent().getParent().getType();
			if (parentParentType == ASTNode.METHOD_DECLARATION || parentParentType == ASTNode.STATIC_METHOD_INVOCATION) {
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
	public static boolean constantAlreadyExists(Program program, final String name) {
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
	public static boolean classNameAlreadyExists(Program program, final String name) {
		assert program != null && name != null;

		final ClassSearcher checkClassVisitor = new ClassSearcher(name);
		program.accept(checkClassVisitor);

		return checkClassVisitor.classNameAlreadyExists();
	}

	/**
	 * @param path
	 * @return true if the given path indicates a global variable
	 */
	public static boolean localVariableAlreadyExists(FunctionDeclaration functionDeclaration, final String name) {
		assert functionDeclaration != null && name != null;

		final LocalVariableSearcher checkLocalVariable = new LocalVariableSearcher(name);
		functionDeclaration.accept(checkLocalVariable);

		return checkLocalVariable.localVariableAlreadyExists();
	}

	/**
	 * @param path
	 * @return true if the given path indicates a global variable
	 */
	public static boolean functionAlreadyExists(Program program, final String name) {
		assert program != null && name != null;

		final FunctionSearcher checkFunctionVisitor = new FunctionSearcher(name);
		program.accept(checkFunctionVisitor);

		return checkFunctionVisitor.functionAlreadyExists();
	}

	/**
	 * @param path
	 * @return true if the given path indicates a global variable
	 */
	public static boolean globalVariableAlreadyExists(Program program, final String name) {
		assert program != null && name != null;

		final GlobalVariableSearcher checkGlobalVisitor = new GlobalVariableSearcher(name);
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

		public void apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return;

			if (node.getType() == ASTNode.SCALAR) {
				final Scalar scalar = (Scalar) node;

				final String stringValue = scalar.getStringValue();
				if (scalar.getScalarType() != Scalar.TYPE_STRING || stringValue == null) {
					return;
				}

				final int length = stringValue.length() - 1;
				if (stringValue.charAt(0) != '"' && stringValue.charAt(length) != '"' && stringValue.equals(name)) {
					exists = true;
				}
			} else if (node.getType() == ASTNode.FUNCTION_INVOCATION) {
				FunctionInvocation functionInvocation = (FunctionInvocation) node;
				final Expression functionName = functionInvocation.getFunctionName().getFunctionName();
				if (functionName.getType() != ASTNode.IDENTIFIER) {
					return;
				}

				final Identifier identifier = (Identifier) functionName;
				final Expression[] parameters = functionInvocation.getParameters();
				if (!"define".equalsIgnoreCase(identifier.getName()) || parameters == null || parameters.length == 0) {
					return;
				}

				final Expression expression = parameters[0];
				if (expression.getType() != ASTNode.SCALAR) {
					return;
				}

				Scalar scalar = (Scalar) expression;
				final String stringValue = scalar.getStringValue();
				if (stringValue.length() < 2 || stringValue.charAt(0) != '"') {
					return;
				}
				exists = name.equals(stringValue.substring(1, stringValue.length() - 1));
			} else {
				node.childrenAccept(this);
			}
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

		public void apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return;

			if (node.getType() == ASTNode.VARIABLE) {
				Variable variable = (Variable) node;
				if (variable.isDollared()) {
					assert variable.getVariableName().getType() == ASTNode.IDENTIFIER;
					Identifier identifier = (Identifier) variable.getVariableName();
					if (identifier.getName().equals(name)) {
						exists = true;
					}
				}
			} else {
				node.childrenAccept(this);
			}
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

		public void apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return;

			if (node.getType() == ASTNode.CLASS_DECLARATION || node.getType() == ASTNode.INTERFACE_DECLARATION) {
				TypeDeclaration typeDeclaration = (TypeDeclaration) node;
				if (typeDeclaration.getName().getName().equals(name)) {
					exists = true;
				}
			} else {
				node.childrenAccept(this);
			}
		}

		public boolean classNameAlreadyExists() {
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

		public void apply(ASTNode node) {
			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return;

			if (node.getType() == ASTNode.CLASS_DECLARATION) {
				// do nothing never catch method names
			} else if (node.getType() == ASTNode.FUNCTION_DECLARATION) {
				FunctionDeclaration functionDeclaration = (FunctionDeclaration) node;
				Identifier identifier = functionDeclaration.getFunctionName();
				if (identifier.getName().equalsIgnoreCase(name)) {
					exists = true;
				}
			} else {
				node.childrenAccept(this);
			}
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

		public void apply(ASTNode node) {

			// stops when found - that's the reason to use ApplyAll
			if (exists)
				return;

			if (node.getType() == ASTNode.CLASS_DECLARATION || node.getType() == ASTNode.FUNCTION_DECLARATION) {
				isGlobalScope = false;
				node.childrenAccept(this);
				isGlobalScope = true;
			} else if (node.getType() == ASTNode.IDENTIFIER) {
				Identifier identifier = (Identifier) node;
				if (identifier.getParent().getType() == ASTNode.VARIABLE) {
					Variable variable = (Variable) identifier.getParent();
					if (variable.isDollared() && isGlobalScope && name.equals(identifier.getName())) {
						exists = true;
					}
				}
			} else if (node.getType() == ASTNode.GLOBAL_STATEMENT) {
				GlobalStatement globalStatement = (GlobalStatement) node;
				final Variable[] variables = globalStatement.getVariables();
				for (int i = 0; i < variables.length; i++) {
					final Variable variable = variables[i];
					final Expression variableName = variable.getVariableName();
					if (variable.isDollared() && variableName.getType() == ASTNode.IDENTIFIER) {
						Identifier identifier = (Identifier) variableName;
						if (name.equals(identifier.getName())) {
							exists = true;
						}
					}
				}
			} else {
				node.childrenAccept(this);
			}
		}

		public boolean globalVariableAlreadyExists() {
			return exists;
		}
	}

}
