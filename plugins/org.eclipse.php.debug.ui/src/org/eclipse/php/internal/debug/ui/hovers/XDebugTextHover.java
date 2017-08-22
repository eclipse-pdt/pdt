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
package org.eclipse.php.internal.debug.ui.hovers;

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.*;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.model.VariablesUtil;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.*;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.w3c.dom.Node;

public class XDebugTextHover extends PHPDebugTextHover {

	@Override
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		DBGpStackFrame frame = (DBGpStackFrame) getFrame();
		if (frame == null)
			return null;

		DBGpVariable variable = null;
		try {
			ISourceModule sourceModule = getEditorInputModelElement();
			if (sourceModule == null) {
				return null;
			}
			ASTNode root = SharedASTProvider.getAST(sourceModule, SharedASTProvider.WAIT_NO, null);
			if (root == null) {
				ASTParser parser = ASTParser.newParser(sourceModule);
				root = parser.createAST(null);
			}
			ASTNode node = NodeFinder.perform(root, hoverRegion.getOffset(), hoverRegion.getLength());
			if (node == null) {
				return null;
			}

			if (node instanceof Scalar) {
				Scalar scalar = (Scalar) node;
				if (node.getParent() instanceof ArrayAccess) {
					ArrayAccess access = (ArrayAccess) node.getParent();
					DBGpVariable var = getVariable(computeExpression(access.getName()));
					String arrayAccessKey = scalar.getStringValue();
					if (scalar.getScalarType() == Scalar.TYPE_STRING) {
						arrayAccessKey = arrayAccessKey.replace("\"", ""); //$NON-NLS-1$ //$NON-NLS-2$
					}
					arrayAccessKey = "[" + arrayAccessKey + "]"; //$NON-NLS-1$ //$NON-NLS-2$
					if (var != null) {
						variable = fetchMember(var, arrayAccessKey);
					}
				} else if (!(scalar.getParent() instanceof Include) && scalar.getScalarType() == Scalar.TYPE_STRING) {
					if (!(scalar.getStringValue().startsWith("\"") && scalar.getStringValue().endsWith("\""))) { //$NON-NLS-1$ //$NON-NLS-2$
						variable = getVariable(scalar.getStringValue());
						if (variable != null) {
							variable.addFacets(KIND_CONSTANT);
							variable.addFacets(MOD_PUBLIC);
						}
					}
				}
			} else if (node.getParent() instanceof Variable && node.getParent().getParent() instanceof FieldAccess
					&& node instanceof Identifier /* avoid $obj->{$myVar} */) {
				String nodeName = ((Identifier) node).getName();
				String expression = computeExpression(((FieldAccess) node.getParent().getParent()).getDispatcher());
				DBGpVariable var = getVariable(expression);
				if (var != null) {
					variable = fetchMember(var, nodeName);
				}
			} else if (node.getParent() instanceof StaticConstantAccess) {
				String nodeName = ((Identifier) node).getName();
				StaticConstantAccess staticAccess = (StaticConstantAccess) node.getParent();
				String className = resolveTypeName((Identifier) staticAccess.getClassName());
				if (className != null) {
					String name = className + "::" + nodeName; //$NON-NLS-1$
					variable = getVariable(name);
					if (variable != null) {
						if (nodeName.equals("class")) { //$NON-NLS-1$
							variable.addFacets(VIRTUAL_CLASS);
						} else {
							variable.addFacets(KIND_CONSTANT);
							variable.addFacets(MOD_PUBLIC);
						}
					}
				}
			} else if (node.getParent() instanceof StaticFieldAccess && node instanceof Variable && ((Variable) node)
					.getName() instanceof Identifier /* avoid A::$$myVar */) {
				Variable var = (Variable) node;
				String nodeName = ((Identifier) var.getName()).getName();
				StaticFieldAccess staticAccess = (StaticFieldAccess) node.getParent();
				Identifier identifier = null;
				if (staticAccess.getClassName() instanceof Identifier) {
					identifier = (Identifier) staticAccess.getClassName();
				} else if (staticAccess.getClassName() instanceof VariableBase) {
					identifier = (Identifier) var.getName();
				}
				String className = resolveTypeName(identifier);
				String name = className + "::$" + nodeName; //$NON-NLS-1$
				variable = getVariable(name);
			} else if (node.getParent() instanceof ConstantDeclaration) {
				String nodeName = ((Identifier) node).getName();
				IField field = (IField) sourceModule.getElementAt(node.getStart());
				if (field.getParent() instanceof IType) {
					IType type = (IType) field.getParent();
					String typeName = type.getFullyQualifiedName(NamespaceReference.NAMESPACE_DELIMITER);
					if (!PHPFlags.isNamespace(type.getFlags())) {
						variable = getVariable(typeName + "::" + nodeName); //$NON-NLS-1$
					} else {
						variable = getVariable(typeName + NamespaceReference.NAMESPACE_DELIMITER + nodeName);
					}
					if (variable != null) {
						variable.addFacets(KIND_CONSTANT);
						variable.addFacets(MOD_PUBLIC);
					}
				}
			} else if (node.getParent() instanceof SingleFieldDeclaration) {
				IField field = (IField) sourceModule.getElementAt(node.getStart());
				String typeName = ""; //$NON-NLS-1$
				boolean isAnonymous = false;
				if (field.getParent() instanceof IType) {
					IType type = (IType) field.getParent();
					typeName = type.getFullyQualifiedName(NamespaceReference.NAMESPACE_DELIMITER);
					isAnonymous = PHPFlags.isAnonymous(type.getFlags());
				}
				Variable var = (Variable) node;
				String nodeName = ((Identifier) var.getName()).getName();
				if (!PHPFlags.isStatic(field.getFlags())) {
					DBGpVariable varThis = getVariable("$this"); //$NON-NLS-1$
					if (varThis != null) {
						if (isAnonymous || typeName.equals(varThis.getValue().getValueString())) {
							variable = fetchMember(varThis, nodeName);
						}
					}
				} else {
					variable = getVariable(typeName + "::$" + nodeName); //$NON-NLS-1$
				}
			} else {
				// local variables
				String variableName = null;
				// ${a}
				if (node instanceof Identifier && node.getParent() instanceof Variable
						&& !((Variable) node.getParent()).isDollared()) {
					variableName = "$" + ((Identifier) node).getName(); //$NON-NLS-1$
				} else {
					IDocument document = textViewer.getDocument();
					if (document != null) {
						// $$a
						if (node instanceof ReflectionVariable) {
							variableName = document.get(((ReflectionVariable) node).getName().getStart(),
									((ReflectionVariable) node).getName().getLength());
						} else {
							// $a
							variableName = document.get(hoverRegion.getOffset(), hoverRegion.getLength());
						}
					}
				}
				for (IVariable stackVariable : frame.getVariables()) {
					if (stackVariable.getName().equals(variableName)) {
						variable = (DBGpVariable) stackVariable;
						break;
					}
				}
			}
		} catch (Exception e) {
			PHPDebugPlugin.log(e);
		}
		return variable;

	}

	@Override
	protected IStackFrame getFrame() {
		IAdaptable adaptable = DebugUITools.getDebugContext();
		if (adaptable != null) {
			return adaptable.getAdapter(DBGpStackFrame.class);
		}
		return null;
	}

	@Nullable
	protected DBGpVariable getVariable(String expression) {
		DBGpVariable variable = null;
		DBGpStackFrame frame = (DBGpStackFrame) getFrame();
		if (isStack(expression)) {
			variable = getByProperty(frame, expression);
		} else {
			variable = getByEval(frame, expression);
		}
		return variable;
	}

	private void setContextFacets(IVariable variable) {
		if (variable instanceof DBGpVariable) {
			DBGpVariable dbgpVariable = (DBGpVariable) variable;
			try {
				String endName = dbgpVariable.getName();
				if (VariablesUtil.isThis(endName))
					dbgpVariable.addFacets(KIND_THIS);
				else if (VariablesUtil.isSuperGlobal(endName))
					dbgpVariable.addFacets(KIND_SUPER_GLOBAL);
				else if (VariablesUtil.isClassIndicator(endName))
					dbgpVariable.addFacets(VIRTUAL_CLASS);
				else
					dbgpVariable.addFacets(KIND_LOCAL);
			} catch (DebugException e) {
				// should not happen
			}
		}
	}

	/**
	 * Returns the variable value.
	 * 
	 * @param expression
	 *            The variable name
	 * @return
	 */
	protected DBGpVariable getByEval(DBGpStackFrame context, String expression) {
		DBGpTarget debugTarget = (DBGpTarget) context.getDebugTarget();
		Node resp = debugTarget.eval(expression);
		if (resp == null) {
			return null;
		}
		return new DBGpEvalVariable(debugTarget, expression, resp);
	}

	protected DBGpVariable getByProperty(DBGpStackFrame context, String variable) {
		DBGpTarget debugTarget = (DBGpTarget) context.getDebugTarget();
		String stackLevel = context.getStackLevel();
		Node resp = debugTarget.getProperty(variable, stackLevel, 0);
		if (resp == null || DBGpResponse.REASON_ERROR.equals(resp.getNodeName())) {
			// Check if it is not super global property
			stackLevel = "-1"; //$NON-NLS-1$
			resp = debugTarget.getProperty(variable, stackLevel, 0);
		}
		if (resp == null) {
			return null;
		}
		DBGpVariable var = new DBGpStackVariable(debugTarget, resp, Integer.valueOf(stackLevel));
		setContextFacets(var);
		return var;
	}

	private boolean isStack(String expression) {
		if (expression.startsWith("$")) {//$NON-NLS-1$
			ISourceRange enclosingIdentifier = PHPTextSequenceUtilities.getEnclosingIdentifier(expression, 0);
			if (enclosingIdentifier != null && enclosingIdentifier.getLength() == expression.length() + 1) {
				return true;
			}
		}
		return false;
	}

	private DBGpVariable fetchMember(DBGpVariable variable, String memberName) {
		try {
			if (variable.getValue() == null || variable.getValue().getVariables() == null)
				return null;
			for (IVariable child : variable.getValue().getVariables()) {
				if (child.getName().equals(memberName) && child instanceof DBGpVariable) {
					return (DBGpVariable) child;
				}
			}
		} catch (DebugException e) {
		}
		return null;
	}

}
