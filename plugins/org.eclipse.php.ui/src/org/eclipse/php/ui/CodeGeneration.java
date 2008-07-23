/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     John Kaplan, johnkaplantech@gmail.com - 108071 [code templates] template for body of newly created class
 *******************************************************************************/
package org.eclipse.php.ui;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;
import org.eclipse.php.internal.ui.corext.template.php.CodeTemplateContextType;
import org.eclipse.php.ui.editor.SharedASTProvider;

/**
 * Class that offers access to the templates contained in the 'code templates' preference page.
 * 
 * <p>
 * This class is not intended to be subclassed or instantiated by clients.
 * </p>
 * 
 * @since 2.1
 * 
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
public class CodeGeneration {

	/**
	 * Constant ID for the type kind to be used in {@link #getTypeBody(String, ICompilationUnit, String, String)} to get the code template used
	 * for a new class type body.
	 * @since 3.2
	 */
	public static final String CLASS_BODY_TEMPLATE_ID = CodeTemplateContextType.CLASSBODY_ID;

	/**
	 * Constant ID for the type kind to be used in {@link #getTypeBody(String, ICompilationUnit, String, String)} to get the code template used
	 * for a new interface type body.
	 * @since 3.2
	 */
	public static final String INTERFACE_BODY_TEMPLATE_ID = CodeTemplateContextType.INTERFACEBODY_ID;

	/**
	 * Constant ID for the type kind to be used in {@link #getTypeBody(String, ICompilationUnit, String, String)} to get the code template used
	 * for a new enum type body.
	 * @since 3.2
	 */
	public static final String ENUM_BODY_TEMPLATE_ID = CodeTemplateContextType.ENUMBODY_ID;

	/**
	 * Constant ID for the type kind to be used in {@link #getTypeBody(String, ICompilationUnit, String, String)} to get the code template used
	 * for a new annotation type body.
	 * @since 3.2
	 */
	public static final String ANNOTATION_BODY_TEMPLATE_ID = CodeTemplateContextType.ANNOTATIONBODY_ID;

	private static final String[] EMPTY = new String[0];

	private CodeGeneration() {
	}

	/**
	 * Returns the content for a new compilation unit using the 'new Java file' code template.
	 * @param sp The compilation unit to create the source for. The compilation unit does not need to exist.
	 * @param typeComment The comment for the type to be created. Used when the code template contains a <i>${typecomment}</i> variable. Can be <code>null</code> if
	 * no comment should be added.
	 * @param typeContent The code of the type, including type declaration and body.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the template is undefined or empty.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 */
	public static String getCompilationUnitContent(IScriptProject sp, String typeComment, String typeContent, String lineDelimiter) throws CoreException {
		return getCompilationUnitContent(sp, getFileComment(sp, lineDelimiter), typeComment, typeContent, lineDelimiter);
	}

	/**
	 * Returns the content for a new compilation unit using the 'new Java file' code template.
	 * @param sp The compilation unit to create the source for. The compilation unit does not need to exist.
	 * 	@param fileComment The file comment to be used when the code template contains a <i>${filecomment}</i> variable. Can be <code>null</code> if
	 * no comment should be added.
	 * @param typeComment The comment for the type to be created. Used when the code template contains a <i>${typecomment}</i> variable. Can be <code>null</code> if
	 * no comment should be added.
	 * @param typeContent The code of the type, including type declaration and body.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the template is undefined or empty.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.1
	 */
	public static String getCompilationUnitContent(IScriptProject sp, String fileComment, String typeComment, String typeContent, String lineDelimiter) throws CoreException {
		return StubUtility.getCompilationUnitContent(sp, fileComment, typeComment, typeContent, lineDelimiter);
	}

	/**
	 * Returns the content for a new file comment using the 'file comment' code template. The returned content is unformatted and is not indented.
	 * @param sp The compilation unit to add the comment to. The compilation unit does not need to exist.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template is undefined or empty. The returned content is unformatted and is not indented.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.1
	 */
	public static String getFileComment(IScriptProject sp, String lineDelimiter) throws CoreException {
		return StubUtility.getFileComment(sp, lineDelimiter);
	}

	/**
	 * Returns the content for a new type comment using the 'type comment' code template. The returned content is unformatted and is not indented.
	 * @param sp The compilation unit where the type is contained. The compilation unit does not need to exist.
	 * @param typeQualifiedName The name of the type to which the comment is added. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template is undefined or empty. The returned content is unformatted and is not indented.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 */
	public static String getTypeComment(IScriptProject sp, String typeQualifiedName, String lineDelimiter) throws CoreException {
		return StubUtility.getTypeComment(sp, typeQualifiedName, EMPTY, lineDelimiter);
	}

	/**
	 * Returns the content for a new type comment using the 'type comment' code template. The returned content is unformatted and is not indented.
	 * @param sp The compilation unit where the type is contained. The compilation unit does not need to exist.
	 * @param typeQualifiedName The name of the type to which the comment is added. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param typeParameterNames The type parameter names
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template is undefined or empty. The returned content is unformatted and is not indented.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.1
	 */
	public static String getTypeComment(IScriptProject sp, String typeQualifiedName, String[] typeParameterNames, String lineDelimiter) throws CoreException {
		return StubUtility.getTypeComment(sp, typeQualifiedName, typeParameterNames, lineDelimiter);
	}

	/**
	 * Returns the content of a new new type body using the 'type body' code templates. The returned content is unformatted and is not indented.
	 * @param typeKind The type kind ID of the body template. Valid values are {@link #CLASS_BODY_TEMPLATE_ID}, {@link #INTERFACE_BODY_TEMPLATE_ID},
	 * {@link #ENUM_BODY_TEMPLATE_ID} and {@link #ANNOTATION_BODY_TEMPLATE_ID}.
	 * @param sp The compilation unit where the type is contained. The compilation unit does not need to exist.
	 * @param typeName The name of the type(for embedding in the template as a user variable).
	 * @param lineDelim The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template is undefined or empty. The returned content is unformatted and is not indented.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.2
	 */
	public static String getTypeBody(String typeKind, IScriptProject sp, String typeName, String lineDelim) throws CoreException {
		return StubUtility.getTypeBody(typeKind, sp, typeName, lineDelim);
	}

	/**
	 * Returns the content for a new field comment using the 'field comment' code template. The returned content is unformatted and is not indented.
	 * @param sp The compilation unit where the field is contained. The compilation unit does not need to exist.
	 * @param fieldType The name of the field declared type.
	 * @param fieldName The name of the field to which the comment is added.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template is undefined or empty. The returned content is unformatted and is not indented.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getFieldComment(IScriptProject sp, IField field, String lineDelimiter) throws CoreException {
		String fieldName = field.getElementName();
		String fieldType = "unknown_type";
		Boolean isVar = false;

		try {
			Program program = SharedASTProvider.getAST(field.getSourceModule(), SharedASTProvider.WAIT_YES, new NullProgressMonitor());
			ASTNode elementAt = program.getElementAt(field.getSourceRange().getOffset());
			ITypeBinding varType = null;
			IVariableBinding resolvedBinding = null;

			if (elementAt instanceof FieldsDeclaration) {
				FieldsDeclaration fieldDeclaration = (FieldsDeclaration) elementAt;
				resolvedBinding = fieldDeclaration.resolveTypeBinding();

				if (null != resolvedBinding) {
					varType = resolvedBinding.getType();
				}
			} else if (elementAt instanceof Variable) {
				isVar = true;

				Variable varDeclaration = (Variable) elementAt;
				if (varDeclaration.getParent() instanceof Assignment) {
					Expression expression = ((Assignment) varDeclaration.getParent()).getRightHandSide();
					varType = expression.resolveTypeBinding();
					//FIXME - what happens when it is a simple type... (see bug #241807)

				} else {
					varType = varDeclaration.resolveTypeBinding();
				}
			}

			if (null != varType) {
				if (varType.isUnknown()) {
					fieldType = "unknown_type";
				} else if (varType.isAmbiguous()) {
					fieldType = "Ambiguous";
				} else {
					fieldType = varType.getName();
				}
			}

		} catch (IOException e) {
			return null;
		}
		if (isVar) {
			return StubUtility.getVarComment(sp, fieldType, fieldName, lineDelimiter);
		}
		return StubUtility.getFieldComment(sp, fieldType, fieldName, lineDelimiter);
	}

	/**
	 * Returns the comment for a method or constructor using the comment code templates (constructor / method / overriding method).
	 * <code>null</code> is returned if the template is empty.
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param decl The MethodDeclaration AST node that will be added as new
	 * method. The node does not need to exist in an AST (no parent needed) and does not need to resolve.
	 * See {@link org.eclipse.jdt.core.dom.AST#newMethodDeclaration()} for how to create such a node.
	 * @param overridden The binding of the method to which to add an "@see" link or 
	 * <code>null</code> if no link should be created.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the generated method comment or <code>null</code> if the
	 * code template is empty. The returned content is unformatted and not indented (formatting required).
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 */
	/*	public static String getMethodComment(IScriptProject sp, String declaringTypeName, MethodDeclaration decl, IMethodBinding overridden, String lineDelimiter) throws CoreException {
			if (overridden != null) {
				overridden= overridden.getMethodDeclaration();
				String declaringClassQualifiedName= overridden.getDeclaringClass().getQualifiedName();
				String linkToMethodName= overridden.getName();
				String[] parameterTypesQualifiedNames= StubUtility.getParameterTypeNamesForSeeTag(overridden);
				return StubUtility.getMethodComment(sp, declaringTypeName, decl, overridden.isDeprecated(), linkToMethodName, declaringClassQualifiedName, parameterTypesQualifiedNames, false, lineDelimiter);
			} else {
				return StubUtility.getMethodComment(sp, declaringTypeName, decl, false, null, null, null, false, lineDelimiter);
			}
		}*/

	/**
	 * Returns the comment for a method or constructor using the comment code templates (constructor / method / overriding method).
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * <p>Exception types and return type are in signature notation. e.g. a source method declared as <code>public void foo(String text, int length)</code>
	 * would return the array <code>{"QString;","I"}</code> as parameter types. See {@link org.eclipse.jdt.core.Signature}.
	 * 
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName Name of the method.
	 * @param paramNames Names of the parameters for the method.
	 * @param excTypeSig Thrown exceptions (Signature notation).
	 * @param retTypeSig Return type (Signature notation) or <code>null</code>
	 * for constructors.
	 * @param overridden The method that will be overridden by the created method or
	 * <code>null</code> for non-overriding methods. If not <code>null</code>, the method must exist.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the constructed comment or <code>null</code> if
	 * the comment code template is empty. The returned content is unformatted and not indented (formatting required).
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 */
	public static String getMethodComment(IScriptProject sp, String declaringTypeName, String methodName, String[] paramNames, String[] excTypeSig, String retTypeSig, IMethod overridden, String lineDelimiter) throws CoreException {
		return StubUtility.getMethodComment(sp, declaringTypeName, methodName, paramNames, retTypeSig, EMPTY, overridden, false, lineDelimiter);
	}

	/**
	 * Returns the comment for a method or constructor using the comment code templates (constructor / method / overriding method).
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * <p>Exception types and return type are in signature notation. e.g. a source method declared as <code>public void foo(String text, int length)</code>
	 * would return the array <code>{"QString;","I"}</code> as parameter types. See {@link org.eclipse.jdt.core.Signature}.
	 * 
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName Name of the method.
	 * @param paramNames Names of the parameters for the method.
	 * @param excTypeSig Thrown exceptions (Signature notation).
	 * @param retTypeSig Return type (Signature notation) or <code>null</code>
	 * for constructors.
	 * @param typeParameterNames Names of the type parameters for the method.
	 * @param overridden The method that will be overridden by the created method or
	 * <code>null</code> for non-overriding methods. If not <code>null</code>, the method must exist.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the constructed comment or <code>null</code> if
	 * the comment code template is empty. The returned content is unformatted and not indented (formatting required).
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.1
	 */
	public static String getMethodComment(IScriptProject sp, String declaringTypeName, String methodName, String[] paramNames, String[] excTypeSig, String retTypeSig, String[] typeParameterNames, IMethod overridden, String lineDelimiter) throws CoreException {
		return StubUtility.getMethodComment(sp, declaringTypeName, methodName, paramNames, retTypeSig, typeParameterNames, overridden, false, lineDelimiter);
	}

	/**
	 * Returns the comment for a method or constructor using the comment code templates (constructor / method / overriding method).
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * 
	 * @param method The method to be documented. The method must exist.
	 * @param overridden The method that will be overridden by the created method or
	 * <code>null</code> for non-overriding methods. If not <code>null</code>, the method must exist.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the constructed comment or <code>null</code> if
	 * the comment code template is empty. The returned string is unformatted and and has no indent (formatting required).
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 */
	public static String getMethodComment(IMethod method, IMethod overridden, String lineDelimiter) throws CoreException {
		//FIXME - 'retType' should be initialized to null after the 'getReturnType will be functional, so void/c'tor will not have 'return' tag
		String retType = "unknown_type";
		String[] typeParameterNames = null;

		try {
			Program program = SharedASTProvider.getAST(method.getSourceModule(), SharedASTProvider.WAIT_YES, new NullProgressMonitor());
			ASTNode elementAt = program.getElementAt(method.getSourceRange().getOffset());
			ITypeBinding returnType = null;
			ITypeBinding[] typeParametersTypes = null;
			IFunctionBinding resolvedBinding = null;

			if (elementAt instanceof MethodDeclaration) {
				MethodDeclaration methodDeclaration = (MethodDeclaration) elementAt;
				resolvedBinding = methodDeclaration.resolveMethodBinding();
			} else if (elementAt instanceof FunctionDeclaration) {
				FunctionDeclaration functionDeclaration = (FunctionDeclaration) elementAt;
				resolvedBinding = functionDeclaration.resolveFunctionBinding();
			}
			if (null != resolvedBinding) {
				returnType = resolvedBinding.getReturnType();
				if (null != returnType) {
					if (returnType.isUnknown()) {
						retType = "unknown_type";
					} else if (returnType.isAmbiguous()) {
						retType = "Ambiguous";
					} else {
						retType = returnType.getName();
					}
				}

				typeParametersTypes = resolvedBinding.getParameterTypes();

				if (null != returnType) {
					int i = 0;
					for (ITypeBinding type : typeParametersTypes) {
						typeParameterNames[i++] = type.getName();
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		String[] paramNames = method.getParameters();// ParameterNames();

		return StubUtility.getMethodComment(method.getScriptProject(), method.getDeclaringType().getElementName(), method.getElementName(), paramNames, retType, typeParameterNames, overridden, false, lineDelimiter);
	}

	/**
	 * Returns the comment for a method or constructor using the comment code templates (constructor / method / overriding method).
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * 
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.

	 * @param decl The MethodDeclaration AST node that will be added as new
	 * method. The node does not need to exist in an AST (no parent needed) and does not need to resolve.
	 * See {@link org.eclipse.jdt.core.dom.AST#newMethodDeclaration()} for how to create such a node.
	 * @param isDeprecated If set, the method is deprecated
	 * @param overriddenMethodName If a method is overridden, the simple name of the overridden method, or <code>null</code> if no method is overridden.
	 * @param overriddenMethodDeclaringTypeName If a method is overridden, the fully qualified type name of the overridden method's declaring type,
	 * or <code>null</code> if no method is overridden.
	 * @param overriddenMethodParameterTypeNames If a method is overridden, the fully qualified parameter type names of the overridden method,
	 * or <code>null</code> if no method is overridden.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the constructed comment or <code>null</code> if
	 * the comment code template is empty. The returned string is unformatted and and has no indent (formatting required).
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.2
	 */

	//	public static String getMethodComment(IScriptProject sp, String declaringTypeName, MethodDeclaration decl, boolean isDeprecated, String overriddenMethodName, String overriddenMethodDeclaringTypeName, String[] overriddenMethodParameterTypeNames, String lineDelimiter) throws CoreException {
	//		return StubUtility.getMethodComment(sp, declaringTypeName, decl, isDeprecated, overriddenMethodName, overriddenMethodDeclaringTypeName, overriddenMethodParameterTypeNames, false, lineDelimiter);
	//	}
	/**
	 * Returns the content of the body for a method or constructor using the method body templates.
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * 
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName Name of the method.
	 * @param isConstructor Defines if the created body is for a constructor.
	 * @param bodyStatement The code to be entered at the place of the variable ${body_statement}. 
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the constructed body content or <code>null</code> if
	 * the comment code template is empty. The returned string is unformatted and and has no indent (formatting required).
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 */
	public static String getMethodBodyContent(IScriptProject sp, String declaringTypeName, String methodName, boolean isConstructor, String bodyStatement, String lineDelimiter) throws CoreException {
		return StubUtility.getMethodBodyContent(isConstructor, sp, declaringTypeName, methodName, bodyStatement, lineDelimiter);
	}

	/**
	 * Returns the content of body for a getter method using the getter method body template.
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * 
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName The name of the getter method.
	 * @param fieldName The name of the field to get in the getter method, corresponding to the template variable for ${field}. 
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the constructed body content or <code>null</code> if
	 * the comment code template is empty. The returned string is unformatted and and has no indent (formatting required).
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getGetterMethodBodyContent(IScriptProject sp, String declaringTypeName, String methodName, String fieldName, String lineDelimiter) throws CoreException {
		return StubUtility.getGetterMethodBodyContent(sp, declaringTypeName, methodName, fieldName, lineDelimiter);
	}

	/**
	 * Returns the content of body for a setter method using the setter method body template.
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * 
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName The name of the setter method.
	 * @param fieldName The name of the field to be set in the setter method, corresponding to the template variable for ${field}.
	 * @param paramName The name of the parameter passed to the setter method, corresponding to the template variable for $(param).
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the constructed body content or <code>null</code> if
	 * the comment code template is empty. The returned string is unformatted and and has no indent (formatting required).
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getSetterMethodBodyContent(IScriptProject sp, String declaringTypeName, String methodName, String fieldName, String paramName, String lineDelimiter) throws CoreException {
		return StubUtility.getSetterMethodBodyContent(sp, declaringTypeName, methodName, fieldName, paramName, lineDelimiter);
	}

	/**
	 * Returns the comment for a getter method using the getter comment template.
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * 
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName Name of the method.
	 * @param fieldName Name of the field to get.
	 * @param fieldType The type of the field to get.
	 * @param bareFieldName The field name without prefix or suffix.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the generated getter comment or <code>null</code> if the
	 * code template is empty. The returned content is not indented.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getGetterComment(IScriptProject sp, String declaringTypeName, String methodName, String fieldName, String fieldType, String bareFieldName, String lineDelimiter) throws CoreException {
		return StubUtility.getGetterComment(sp, declaringTypeName, methodName, fieldName, fieldType, bareFieldName, lineDelimiter);
	}

	/**
	 * Returns the comment for a setter method using the setter method body template.
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and not indented.
	 * 
	 * @param sp The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName Name of the method.
	 * @param fieldName Name of the field that is set.
	 * @param fieldType The type of the field that is to set.
	 * @param paramName The name of the parameter that used to set.
	 * @param bareFieldName The field name without prefix or suffix.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return Returns the generated setter comment or <code>null</code> if the
	 * code template is empty. The returned comment is not indented.
	 * @throws CoreException Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getSetterComment(IScriptProject sp, String declaringTypeName, String methodName, String fieldName, String fieldType, String paramName, String bareFieldName, String lineDelimiter) throws CoreException {
		return StubUtility.getSetterComment(sp, declaringTypeName, methodName, fieldName, fieldType, paramName, bareFieldName, lineDelimiter);
	}
}
