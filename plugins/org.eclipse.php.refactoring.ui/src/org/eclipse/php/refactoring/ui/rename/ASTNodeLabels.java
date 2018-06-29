/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.rename;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Description: Labels to few of the {@link ASTNode} - initial work
 * 
 * @author Roy, 2007
 */
public class ASTNodeLabels {

	/**
	 * Method names contain parameter types. e.g. <code>foo(int)</code>
	 */
	public static final int M_PARAMETER_TYPES = 1 << 0;

	/**
	 * Method names contain parameter names. e.g. <code>foo(index)</code>
	 */
	public static final int M_PARAMETER_NAMES = 1 << 1;

	/**
	 * Method names contain return type (appended) e.g. <code>foo : int</code>
	 */
	public static final int M_APP_RETURNTYPE = 1 << 3;

	/**
	 * Method names contain return type (appended) e.g. <code>int foo</code>
	 */
	public static final int M_PRE_RETURNTYPE = 1 << 4;

	/**
	 * Method names are fully qualified. e.g. <code>java.util.Vector.size</code>
	 */
	public static final int M_FULLY_QUALIFIED = 1 << 5;

	/**
	 * Method names are post qualified. e.g. <code>size - java.util.Vector</code>
	 */
	public static final int M_POST_QUALIFIED = 1 << 6;

	/**
	 * Type names are post qualified. e.g. <code>{ ... } - java.util.Map</code>
	 */
	public static final int I_POST_QUALIFIED = 1 << 8;

	/**
	 * Field names contain the declared type (appended) e.g.
	 * <code>fHello : int</code>
	 */
	public static final int F_APP_TYPE_SIGNATURE = 1 << 9;

	/**
	 * Field names contain the declared type (prepended) e.g.
	 * <code>int fHello</code>
	 */
	public static final int F_PRE_TYPE_SIGNATURE = 1 << 10;

	/**
	 * Fields names are fully qualified. e.g. <code>java.lang.System.out</code>
	 */
	public static final int F_FULLY_QUALIFIED = 1 << 11;

	/**
	 * Fields names are post qualified. e.g. <code>out - java.lang.System</code>
	 */
	public static final int F_POST_QUALIFIED = 1 << 12;

	/**
	 * Type names are fully qualified. e.g. <code>java.util.Map.MapEntry</code>
	 */
	public static final int T_FULLY_QUALIFIED = 1 << 13;

	/**
	 * Type names are type container qualified. e.g. <code>Map.MapEntry</code>
	 */
	public static final int T_CONTAINER_QUALIFIED = 1 << 14;

	/**
	 * Type names are post qualified. e.g. <code>MapEntry - java.util.Map</code>
	 */
	public static final int T_POST_QUALIFIED = 1 << 15;

	/**
	 * Compilation unit names are fully qualified. e.g.
	 * <code>java.util.Vector.java</code>
	 */
	public static final int CU_QUALIFIED = 1 << 16;

	/**
	 * Compilation unit names are post qualified. e.g.
	 * <code>Vector.java - java.util</code>
	 */
	public static final int CU_POST_QUALIFIED = 1 << 17;

	/**
	 * Folder names are qualified. e.g. <code>MyProject/src/java.util</code>
	 */
	public static final int P_QUALIFIED = 1 << 22;

	/**
	 * Folder names are post qualified. e.g. <code>java.util - MyProject/src</code>
	 */
	public static final int P_POST_QUALIFIED = 1 << 23;

	/**
	 * Folder Roots contain variable name if from a variable.
	 */
	public static final int ROOT_VARIABLE = 1 << 24;

	/**
	 * Folder Roots contain the project name if not an archive (prepended). e.g.
	 * <code>MyProject/src</code>
	 */
	public static final int ROOT_QUALIFIED = 1 << 25;

	/**
	 * Folder Roots contain the project name if not an archive (appended). e.g.
	 * <code>src - MyProject</code>
	 */
	public static final int ROOT_POST_QUALIFIED = 1 << 26;

	/**
	 * Add root path to all elements except Folder Roots and PHP projects.
	 */
	public static final int APPEND_ROOT_PATH = 1 << 27;

	/**
	 * Add root path to all elements except Folder Roots and PHP projects.
	 */
	public static final int PREPEND_ROOT_PATH = 1 << 28;

	/**
	 * Folder names are compressed. e.g. <code>o*.e*.search</code>
	 */
	public static final int P_COMPRESSED = 1 << 29;

	public static final int REFERENCED_ROOT_POST_QUALIFIED = 1 << 30;
	public static final long T_TYPE_PARAMETERS = 1L << 21;
	public static final long CF_POST_QUALIFIED = 1L << 28;
	public static final long CF_QUALIFIED = 1L << 27;
	public static final long I_FULLY_QUALIFIED = 1L << 10;
	public static final long D_QUALIFIED = 1L << 24;
	public static final long D_POST_QUALIFIED = 1L << 25;

	/**
	 * Qualify all elements
	 */
	public static final int ALL_FULLY_QUALIFIED = F_FULLY_QUALIFIED | M_FULLY_QUALIFIED | T_FULLY_QUALIFIED
			| CU_QUALIFIED | P_QUALIFIED | ROOT_QUALIFIED;

	/**
	 * Post qualify all elements
	 */
	public static final int ALL_POST_QUALIFIED = F_POST_QUALIFIED | M_POST_QUALIFIED | I_POST_QUALIFIED
			| T_POST_QUALIFIED | CU_POST_QUALIFIED | P_POST_QUALIFIED | ROOT_POST_QUALIFIED;

	/**
	 * Default options (M_PARAMETER_TYPES enabled)
	 */
	public static final int ALL_DEFAULT = M_PARAMETER_TYPES;

	/**
	 * Default qualify options (All except Root and Folder)
	 */
	public static final int DEFAULT_QUALIFIED = F_FULLY_QUALIFIED | M_FULLY_QUALIFIED | T_FULLY_QUALIFIED
			| CU_QUALIFIED;

	/**
	 * Default post qualify options (All except Root and Folder)
	 */
	public static final int DEFAULT_POST_QUALIFIED = F_POST_QUALIFIED | M_POST_QUALIFIED | I_POST_QUALIFIED
			| T_POST_QUALIFIED | CU_POST_QUALIFIED;

	/**
	 * User-readable string for separating post qualified names (e.g. " - ").
	 */
	public static final String CONCAT_STRING = " - "; //$NON-NLS-1$
	/**
	 * User-readable string for separating list items (e.g. ", ").
	 */
	public static final String COMMA_STRING = ", "; //$NON-NLS-1$
	/**
	 * User-readable string for separating the return type (e.g. " : ").
	 */
	public static final String DECL_STRING = " :"; //$NON-NLS-1$
	/**
	 * User-readable string for the default folder name (e.g. "(default folder)" ).
	 */
	public static final String DEFAULT_FOLDER = "(source)"; //$NON-NLS-1$

	/**
	 * Returns the label for a PHP element. Flags as defined above.
	 */
	public static String getElementLabel(Object element, long flags) {
		StringBuilder buf = new StringBuilder(60);
		getElementLabel(element, flags, buf);
		return buf.toString();
	}

	/**
	 * Returns the label for a PHP element. Flags as defined above.
	 */
	public static void getElementLabel(Object element, long flags, StringBuilder buf) {
		if (element instanceof ASTNode) {
			printNodeLabel((ASTNode) element, flags, buf);
		}
	}

	private static void printNodeLabel(ASTNode node, long flags, StringBuilder buf) {
		final int type = node.getType();

		switch (type) {
		case ASTNode.FUNCTION_DECLARATION:
			getFunctionLabel((FunctionDeclaration) node, flags, buf);
			break;
		case ASTNode.METHOD_DECLARATION:
			getMethodLabel((MethodDeclaration) node, flags, buf);
			break;
		case ASTNode.FIELD_DECLARATION:
			getFieldLabel((FieldsDeclaration) node, flags, buf);
			break;
		case ASTNode.CLASS_DECLARATION:
		case ASTNode.INTERFACE_DECLARATION:
			getTypeLabel((TypeDeclaration) node, flags, buf);
			break;
		case ASTNode.PROGRAM:
			getProgramLabel((Program) node, flags, buf);
			break;
		}
	}

	private static void getFunctionLabel(FunctionDeclaration declaration, long flags, StringBuilder buf) {
		buf.append(declaration.getFunctionName().getName());

		// parameters
		if (getFlag(flags, M_PARAMETER_TYPES | M_PARAMETER_NAMES)) {
			final List<FormalParameter> parameters = declaration.formalParameters();
			buf.append('(');

			int nParams = (parameters != null && getFlag(flags, M_PARAMETER_TYPES)) ? parameters.size() : 0;

			for (int i = 0; i < nParams; i++) {
				if (i > 0) {
					buf.append(COMMA_STRING);
					buf.append(" "); //$NON-NLS-1$
				}
				if (parameters != null) {
					if (getFlag(flags, M_PARAMETER_TYPES) && parameters.get(i).getParameterType() != null) {
						String t = parameters.get(i).getParameterType().toString();
						if (t == null) {
							t = ""; //$NON-NLS-1$
						} else {
							t += " "; //$NON-NLS-1$
						}
						buf.append(t);
					}
					if (getFlag(flags, M_PARAMETER_NAMES)) {
						buf.append("$" + parameters.get(i).getParameterNameIdentifier().getName()); //$NON-NLS-1$
					}
				}
			}
			buf.append(')');
		}

		// TODO: return type
	}

	private static boolean getFlag(long flags, long flag) {
		return (flags & flag) != 0;
	}

	public static void getMethodLabel(MethodDeclaration method, long flags, StringBuilder buf) {
		// qualification
		if (getFlag(flags, M_FULLY_QUALIFIED)) {
			final ASTNode parent = method.getParent().getParent();
			assert parent != null && (parent.getType() == ASTNode.CLASS_DECLARATION
					|| parent.getType() == ASTNode.INTERFACE_DECLARATION);
			TypeDeclaration typeDeclaration = (TypeDeclaration) parent;
			getTypeLabel(typeDeclaration, T_FULLY_QUALIFIED | (flags & P_COMPRESSED), buf);
			buf.append('.');
		}

		getFunctionLabel(method.getFunction(), flags, buf);

		// TODO : exceptions
		// post qualification
		if (getFlag(flags, M_POST_QUALIFIED)) {
			buf.append(CONCAT_STRING);
			ASTNode container = method.getParent().getParent();
			assert container == null || container.getType() == ASTNode.CLASS_DECLARATION
					|| container.getType() == ASTNode.INTERFACE_DECLARATION;
			TypeDeclaration declaration = (TypeDeclaration) container;
			if (declaration != null) {
				getTypeLabel(declaration, T_FULLY_QUALIFIED | (flags & P_COMPRESSED), buf);
			}
		}

	}

	public static void getTypeLabel(TypeDeclaration type, long flags, StringBuilder buf) {
		if (type == null) {
			return;
		}

		String typeName = type.getName().getName();
		buf.append(typeName);
	}

	public static void getFieldLabel(FieldsDeclaration field, long flags, StringBuilder buf) {

		final Variable[] variableNames = field.getVariableNames();
		for (int i = 0; i < variableNames.length; i++) {
			Variable variable = variableNames[i];
			assert variable.getName() instanceof Identifier;
			Identifier identifier = (Identifier) variable.getName();
			buf.append(identifier.getName());
			buf.append(" ,"); //$NON-NLS-1$
		}

		// post qualification
		if (getFlag(flags, F_POST_QUALIFIED)) {
			buf.append(CONCAT_STRING);
			ASTNode container = field.getParent().getParent();
			assert container == null || container.getType() == ASTNode.CLASS_DECLARATION
					|| container.getType() == ASTNode.INTERFACE_DECLARATION;
			TypeDeclaration declaration = (TypeDeclaration) container;
			if (declaration != null) {
				getTypeLabel(declaration, T_FULLY_QUALIFIED | (flags & P_COMPRESSED), buf);
			}
		}

	}

	public static void getProgramLabel(Program program, long flags, StringBuilder buf) {
		buf.append("Program"); //$NON-NLS-1$
	}

	/**
	 * Returns the tooltip text for a PHP element.
	 */
	public static String getElementTooltipText(Object element) {
		StringBuilder buf = new StringBuilder(60);

		if (element instanceof ASTNode) {
			ASTNode node = (ASTNode) element;
			switch (node.getType()) {
			case ASTNode.METHOD_DECLARATION:
				getMethodTooltipText(((MethodDeclaration) element).getFunction(), buf);
				break;
			case ASTNode.INTERFACE_DECLARATION:
			case ASTNode.CLASS_DECLARATION:
				// getClassTooltipText((PHPClassData) element, buf);
				break;
			}
		}
		return buf.toString();
	}

	// public static void getClassTooltipText(PHPClassData classData,
	// StringBuilder buf) {
	// if (classData == null) {
	// return;
	// }
	// buf.append(classData.getName());
	//
	// if (classData.getDocBlock() != null) {
	// buf.append("\n" + classData.getDocBlock().getShortDescription());
	// //$NON-NLS-1$
	// }
	// }

	public static void getMethodTooltipText(FunctionDeclaration function, StringBuilder buf) {
		buf.append(function.getFunctionName().getName());
		// parameters
		buf.append('(');
		List<FormalParameter> parameters = function.formalParameters();
		for (int i = 0; i < parameters.size(); i++) {
			if (i > 0) {
				buf.append(COMMA_STRING);
			}
			buf.append(parameters.get(i).getParameterType().toString());
			buf.append(" $" + parameters.get(i).getParameterNameIdentifier().getName()); //$NON-NLS-1$
		}
		buf.append(')');
	}

	public static String getTextLabel(Object obj, long flags) {
		if (obj instanceof ASTNode) {
			return getElementLabel(obj, flags);
		} else if (obj instanceof IAdaptable) {
			IWorkbenchAdapter wbadapter = ((IAdaptable) obj).getAdapter(IWorkbenchAdapter.class);
			if (wbadapter != null) {
				return wbadapter.getLabel(obj);
			}
		}
		return ""; //$NON-NLS-1$
	}

	public static String getTooltipTextLabel(Object obj) {
		if (obj instanceof ASTNode) {
			return getElementTooltipText(obj);
		} else if (obj instanceof IAdaptable) {
			IWorkbenchAdapter wbadapter = ((IAdaptable) obj).getAdapter(IWorkbenchAdapter.class);
			if (wbadapter != null) {
				return wbadapter.getLabel(obj);
			}
		}
		// else if (obj != null &&
		// obj.equals(PHPFunctionsContentProvider.CONSTANTS_NODE_NAME)) {
		// return obj.toString();
		// }
		return ""; //$NON-NLS-1$
	}

}
