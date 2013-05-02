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
package org.eclipse.php.ui;

import java.io.IOException;
import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;
import org.eclipse.php.internal.ui.corext.template.php.CodeTemplateContextType;
import org.eclipse.php.ui.editor.SharedASTProvider;

/**
 * Class that offers access to the templates contained in the 'code templates'
 * preference page.
 * 
 * <p>
 * This class is not intended to be subclassed or instantiated by clients.
 * </p>
 * 
 * @since 2.2
 * 
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
public class CodeGeneration {

	private static final String UNKNOWN_TYPE = UnknownType.INSTANCE
			.getTypeName();

	/**
	 * Constant ID for the type kind to be used in
	 * {@link #getTypeBody(String, IScriptProject, String, String)} to get the
	 * code template used for a new class type body.
	 * 
	 * @since 3.2
	 */
	public static final String CLASS_BODY_TEMPLATE_ID = CodeTemplateContextType.CLASSBODY_ID;

	/**
	 * Constant ID for the type kind to be used in
	 * {@link #getTypeBody(String, IScriptProject, String, String)} to get the
	 * code template used for a new interface type body.
	 * 
	 * @since 3.2
	 */
	public static final String INTERFACE_BODY_TEMPLATE_ID = CodeTemplateContextType.INTERFACEBODY_ID;

	/**
	 * Constant ID for the type kind to be used in
	 * {@link #getTypeBody(String, IScriptProject, String, String)} to get the
	 * code template used for a new enum type body.
	 * 
	 * @since 3.2
	 */
	public static final String ENUM_BODY_TEMPLATE_ID = CodeTemplateContextType.ENUMBODY_ID;

	/**
	 * Constant ID for the type kind to be used in
	 * {@link #getTypeBody(String, IScriptProject, String, String)} to get the
	 * code template used for a new annotation type body.
	 * 
	 * @since 3.2
	 */
	public static final String ANNOTATION_BODY_TEMPLATE_ID = CodeTemplateContextType.ANNOTATIONBODY_ID;

	private static final String[] EMPTY = new String[0];

	private CodeGeneration() {
	}

	/**
	 * Returns the content for a new compilation unit using the 'new Java file'
	 * code template.
	 * 
	 * @param sp
	 *            The compilation unit to create the source for. The compilation
	 *            unit does not need to exist.
	 * @param typeComment
	 *            The comment for the type to be created. Used when the code
	 *            template contains a <i>${typecomment}</i> variable. Can be
	 *            <code>null</code> if no comment should be added.
	 * @param typeContent
	 *            The code of the type, including type declaration and body.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the template is
	 *         undefined or empty.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 */
	public static String getCompilationUnitContent(IScriptProject sp,
			String typeComment, String typeContent, String lineDelimiter)
			throws CoreException {
		return getCompilationUnitContent(sp, getFileComment(sp, lineDelimiter),
				typeComment, typeContent, lineDelimiter);
	}

	/**
	 * Returns the content for a new compilation unit using the 'new Java file'
	 * code template.
	 * 
	 * @param sp
	 *            The compilation unit to create the source for. The compilation
	 *            unit does not need to exist.
	 * @param fileComment
	 *            The file comment to be used when the code template contains a
	 *            <i>${filecomment}</i> variable. Can be <code>null</code> if no
	 *            comment should be added.
	 * @param typeComment
	 *            The comment for the type to be created. Used when the code
	 *            template contains a <i>${typecomment}</i> variable. Can be
	 *            <code>null</code> if no comment should be added.
	 * @param typeContent
	 *            The code of the type, including type declaration and body.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the template is
	 *         undefined or empty.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.1
	 */
	public static String getCompilationUnitContent(IScriptProject sp,
			String fileComment, String typeComment, String typeContent,
			String lineDelimiter) throws CoreException {
		return StubUtility.getCompilationUnitContent(sp, fileComment,
				typeComment, typeContent, lineDelimiter);
	}

	/**
	 * Returns the content for a new file comment using the 'file comment' code
	 * template. The returned content is unformatted and is not indented.
	 * 
	 * @param sp
	 *            The compilation unit to add the comment to. The compilation
	 *            unit does not need to exist.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template
	 *         is undefined or empty. The returned content is unformatted and is
	 *         not indented.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.1
	 */
	public static String getFileComment(ISourceModule sm, String lineDelimiter)
			throws CoreException {
		return StubUtility.getFileComment(sm, lineDelimiter);
	}

	public static String getFileComment(IScriptProject sp, String lineDelimiter)
			throws CoreException {
		return StubUtility.getFileComment(sp, lineDelimiter);
	}

	/**
	 * Returns the content for a new type comment using the 'type comment' code
	 * template. The returned content is unformatted and is not indented.
	 * 
	 * @param sp
	 *            The compilation unit where the type is contained. The
	 *            compilation unit does not need to exist.
	 * @param typeQualifiedName
	 *            The name of the type to which the comment is added. For inner
	 *            types the name must be qualified and include the outer types
	 *            names (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template
	 *         is undefined or empty. The returned content is unformatted and is
	 *         not indented.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 */
	public static String getTypeComment(IScriptProject sp,
			String typeQualifiedName, String lineDelimiter)
			throws CoreException {
		return StubUtility.getTypeComment(sp, typeQualifiedName, EMPTY,
				lineDelimiter);
	}

	/**
	 * Returns the content for a new type comment using the 'type comment' code
	 * template. The returned content is unformatted and is not indented.
	 * 
	 * @param sp
	 *            The compilation unit where the type is contained. The
	 *            compilation unit does not need to exist.
	 * @param typeQualifiedName
	 *            The name of the type to which the comment is added. For inner
	 *            types the name must be qualified and include the outer types
	 *            names (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param typeParameterNames
	 *            The type parameter names
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template
	 *         is undefined or empty. The returned content is unformatted and is
	 *         not indented.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.1
	 */
	public static String getTypeComment(IScriptProject sp,
			String typeQualifiedName, String[] typeParameterNames,
			String lineDelimiter) throws CoreException {
		return StubUtility.getTypeComment(sp, typeQualifiedName,
				typeParameterNames, lineDelimiter);
	}

	/**
	 * Returns the content of a new new type body using the 'type body' code
	 * templates. The returned content is unformatted and is not indented.
	 * 
	 * @param typeKind
	 *            The type kind ID of the body template. Valid values are
	 *            {@link #CLASS_BODY_TEMPLATE_ID},
	 *            {@link #INTERFACE_BODY_TEMPLATE_ID},
	 *            {@link #ENUM_BODY_TEMPLATE_ID} and
	 *            {@link #ANNOTATION_BODY_TEMPLATE_ID}.
	 * @param sp
	 *            The compilation unit where the type is contained. The
	 *            compilation unit does not need to exist.
	 * @param typeName
	 *            The name of the type(for embedding in the template as a user
	 *            variable).
	 * @param lineDelim
	 *            The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template
	 *         is undefined or empty. The returned content is unformatted and is
	 *         not indented.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.2
	 */
	public static String getTypeBody(String typeKind, IScriptProject sp,
			String typeName, String lineDelim) throws CoreException {
		return StubUtility.getTypeBody(typeKind, sp, typeName, lineDelim);
	}

	/**
	 * Returns the content for a new field comment using the 'field comment'
	 * code template. The returned content is unformatted and is not indented.
	 * 
	 * @param sp
	 *            The compilation unit where the field is contained. The
	 *            compilation unit does not need to exist.
	 * @param fieldType
	 *            The name of the field declared type.
	 * @param fieldName
	 *            The name of the field to which the comment is added.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the new content or <code>null</code> if the code template
	 *         is undefined or empty. The returned content is unformatted and is
	 *         not indented.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getFieldComment(IScriptProject sp, IField field,
			String lineDelimiter) throws CoreException {
		String fieldName = field.getElementName();
		String fieldType = null;
		Boolean isVar = false;

		try {
			Program program = SharedASTProvider.getAST(field.getSourceModule(),
					SharedASTProvider.WAIT_YES, new NullProgressMonitor());
			ASTNode elementAt = program.getElementAt(field.getSourceRange()
					.getOffset());
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
					Expression expression = ((Assignment) varDeclaration
							.getParent()).getRightHandSide();
					varType = expression.resolveTypeBinding();
					if (expression instanceof Scalar) {
						Scalar scalar = (Scalar) expression;
						switch (scalar.getScalarType()) {
						case Scalar.TYPE_INT:
							fieldType = "integer"; //$NON-NLS-1$
							break;
						case Scalar.TYPE_STRING:
							fieldType = "string"; //$NON-NLS-1$
							break;
						}
					}

				} else {
					varType = varDeclaration.resolveTypeBinding();
				}
			}

			if (null == fieldType && null != varType) {
				if (varType.isAmbiguous()) {
					fieldType = "Ambiguous"; //$NON-NLS-1$
				} else {
					fieldType = varType.getName();
				}
			}

			if (null == fieldType) {
				fieldType = UNKNOWN_TYPE;
			}

		} catch (IOException e) {
			return null;
		}
		if (isVar) {
			return StubUtility.getVarComment(sp, fieldType, fieldName,
					lineDelimiter);
		}
		return StubUtility.getFieldComment(sp, fieldType, fieldName,
				lineDelimiter);
	}

	/**
	 * Returns the comment for a method or constructor using the comment code
	 * templates (constructor / method / overriding method). <code>null</code>
	 * is returned if the template is empty.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param decl
	 *            The MethodDeclaration AST node that will be added as new
	 *            method. The node does not need to exist in an AST (no parent
	 *            needed) and does not need to resolve. See
	 *            {@link org.eclipse.jdt.core.dom.AST#newMethodDeclaration()}
	 *            for how to create such a node.
	 * @param overridden
	 *            The binding of the method to which to add an "@see" link or
	 *            <code>null</code> if no link should be created.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the generated method comment or <code>null</code> if the
	 *         code template is empty. The returned content is unformatted and
	 *         not indented (formatting required).
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 */
	/*
	 * public static String getMethodComment(IScriptProject sp, String
	 * declaringTypeName, MethodDeclaration decl, IMethodBinding overridden,
	 * String lineDelimiter) throws CoreException { if (overridden != null) {
	 * overridden= overridden.getMethodDeclaration(); String
	 * declaringClassQualifiedName=
	 * overridden.getDeclaringClass().getQualifiedName(); String
	 * linkToMethodName= overridden.getName(); String[]
	 * parameterTypesQualifiedNames=
	 * StubUtility.getParameterTypeNamesForSeeTag(overridden); return
	 * StubUtility.getMethodComment(sp, declaringTypeName, decl,
	 * overridden.isDeprecated(), linkToMethodName, declaringClassQualifiedName,
	 * parameterTypesQualifiedNames, false, lineDelimiter); } else { return
	 * StubUtility.getMethodComment(sp, declaringTypeName, decl, false, null,
	 * null, null, false, lineDelimiter); } }
	 */

	/**
	 * Returns the comment for a method or constructor using the comment code
	 * templates (constructor / method / overriding method). <code>null</code>
	 * is returned if the template is empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * <p>
	 * Exception types and return type are in signature notation. e.g. a source
	 * method declared as <code>public void foo(String text, int length)</code>
	 * would return the array <code>{"QString;","I"}</code> as parameter types.
	 * See {@link org.eclipse.jdt.core.Signature}.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName
	 *            Name of the method.
	 * @param paramNames
	 *            Names of the parameters for the method.
	 * @param excTypeSig
	 *            Thrown exceptions (Signature notation).
	 * @param retTypeSig
	 *            Return type (Signature notation) or <code>null</code> for
	 *            constructors.
	 * @param overridden
	 *            The method that will be overridden by the created method or
	 *            <code>null</code> for non-overriding methods. If not
	 *            <code>null</code>, the method must exist.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the constructed comment or <code>null</code> if the
	 *         comment code template is empty. The returned content is
	 *         unformatted and not indented (formatting required).
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 */
	public static String getMethodComment(IScriptProject sp,
			String declaringTypeName, String methodName, String[] paramNames,
			String[] excTypeSig, String retTypeSig, IMethod overridden,
			String lineDelimiter, List<String> exceptions) throws CoreException {
		return StubUtility.getMethodComment(sp, declaringTypeName, methodName,
				paramNames, retTypeSig, EMPTY, overridden, false,
				lineDelimiter, exceptions);
	}

	/**
	 * Returns the comment for a method or constructor using the comment code
	 * templates (constructor / method / overriding method). <code>null</code>
	 * is returned if the template is empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * <p>
	 * Exception types and return type are in signature notation. e.g. a source
	 * method declared as <code>public void foo(String text, int length)</code>
	 * would return the array <code>{"QString;","I"}</code> as parameter types.
	 * See {@link org.eclipse.jdt.core.Signature}.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName
	 *            Name of the method.
	 * @param paramNames
	 *            Names of the parameters for the method.
	 * @param excTypeSig
	 *            Thrown exceptions (Signature notation).
	 * @param retTypeSig
	 *            Return type (Signature notation) or <code>null</code> for
	 *            constructors.
	 * @param typeParameterNames
	 *            Names of the type parameters for the method.
	 * @param overridden
	 *            The method that will be overridden by the created method or
	 *            <code>null</code> for non-overriding methods. If not
	 *            <code>null</code>, the method must exist.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the constructed comment or <code>null</code> if the
	 *         comment code template is empty. The returned content is
	 *         unformatted and not indented (formatting required).
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.1
	 */
	public static String getMethodComment(IScriptProject sp,
			String declaringTypeName, String methodName, String[] paramNames,
			String[] excTypeSig, String retTypeSig,
			String[] typeParameterNames, IMethod overridden,
			String lineDelimiter, List<String> exceptions) throws CoreException {
		return StubUtility.getMethodComment(sp, declaringTypeName, methodName,
				paramNames, retTypeSig, typeParameterNames, overridden, false,
				lineDelimiter, exceptions);
	}

	/**
	 * Returns the comment for a method or constructor using the comment code
	 * templates (constructor / method / overriding method). <code>null</code>
	 * is returned if the template is empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * 
	 * @param method
	 *            The method to be documented. The method must exist.
	 * @param overridden
	 *            The method that will be overridden by the created method or
	 *            <code>null</code> for non-overriding methods. If not
	 *            <code>null</code>, the method must exist.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the constructed comment or <code>null</code> if the
	 *         comment code template is empty. The returned string is
	 *         unformatted and and has no indent (formatting required).
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 *             Contributed by zhaozw - bug #255204 [regression] Parameters
	 *             type is not displayed in Generated element comments doc block
	 */
	public static String getMethodComment(IMethod method, IMethod overridden,
			String lineDelimiter) throws CoreException {
		// FIXME - 'retType' should be initialized to null after the
		// 'getReturnType will be functional, so void/c'tor will not have
		// 'return' tag

		String retType = null;
		String[] typeParameterNames = null;
		String[] parameterTypes = null;
		Program program = null;

		try {
			program = SharedASTProvider.getAST(method.getSourceModule(),
					SharedASTProvider.WAIT_YES, new NullProgressMonitor());
		} catch (IOException e1) {
		}

		if (program == null) {
			program = generageProgram(method, program);
			if (program == null) {
				return null;
			}
		}

		ASTNode elementAt = program.getElementAt(method.getSourceRange()
				.getOffset());

		if (!(elementAt instanceof MethodDeclaration
				|| elementAt instanceof FunctionDeclaration || elementAt
					.getParent() instanceof MethodDeclaration)) {
			program = generageProgram(method, program);
			if (program == null) {
				return null;
			}
			elementAt = program.getElementAt(method.getSourceRange()
					.getOffset());
		}

		if (elementAt.getParent() instanceof MethodDeclaration) {
			elementAt = elementAt.getParent();
		}

		ITypeBinding[] returnTypes = null;
		ITypeBinding[] typeParametersTypes = null;
		IFunctionBinding resolvedBinding = null;
		List<FormalParameter> formalParameters = null;

		if (elementAt instanceof MethodDeclaration) {
			MethodDeclaration methodDeclaration = (MethodDeclaration) elementAt;
			resolvedBinding = methodDeclaration.resolveMethodBinding();
			formalParameters = methodDeclaration.getFunction()
					.formalParameters();
		} else if (elementAt instanceof FunctionDeclaration) {
			FunctionDeclaration functionDeclaration = (FunctionDeclaration) elementAt;
			resolvedBinding = functionDeclaration.resolveFunctionBinding();
			formalParameters = functionDeclaration.formalParameters();
		}
		final List<String> exceptions = new ArrayList<String>();
		elementAt.accept(new AbstractVisitor() {
			public boolean visit(ThrowStatement throwStatement) {
				Expression expression = throwStatement.getExpression();
				if (expression instanceof ClassInstanceCreation) {
					ClassInstanceCreation cic = (ClassInstanceCreation) throwStatement
							.getExpression();
					if (cic.getClassName().getName() instanceof Identifier) {
						Identifier name = (Identifier) cic.getClassName()
								.getName();
						exceptions.add(name.getName());
					}
				}
				if (expression instanceof Variable) {
					ITypeBinding type = ((Variable) expression)
							.resolveTypeBinding();
					if (type != null) {
						exceptions.add(type.getName());
					}
				}

				return true;
			}
		});
		final List<String> newExceptions = new ArrayList<String>();
		final Set<String> exceptionSet = new HashSet<String>();
		for (Iterator<String> iterator = exceptions.iterator(); iterator
				.hasNext();) {
			String exception = iterator.next();
			if (!exceptionSet.contains(exception)) {
				exceptionSet.add(exception);
				newExceptions.add(exception);
			}
		}
		if (formalParameters != null) {
			// get parameter type
			parameterTypes = new String[formalParameters.size()];
			int i = 0;
			for (ASTNode node : formalParameters) {
				FormalParameter formalParameter = (FormalParameter) node;
				Expression parameterType = formalParameter.getParameterType();
				if (parameterType != null) {
					String typeName = ((Identifier) parameterType).getName();
					parameterTypes[i++] = typeName;
				} else {
					if (formalParameter.getDefaultValue() != null
							&& formalParameter.getDefaultValue() instanceof Scalar) {
						Scalar scalar = (Scalar) formalParameter
								.getDefaultValue();
						IEvaluatedType simpleType = PHPSimpleTypes
								.fromString(Scalar.getType(scalar
										.getScalarType()));
						if (simpleType == null) {
							parameterTypes[i++] = Scalar.getType(scalar
									.getScalarType());
						} else {
							parameterTypes[i++] = simpleType.getTypeName();
						}

					} else {
						parameterTypes[i++] = UNKNOWN_TYPE;
					}

				}
			}
		}

		StringBuilder returnTypeBuffer = new StringBuilder();
		if (null != resolvedBinding) {
			returnTypes = resolvedBinding.getReturnType();
			if (null != returnTypes && returnTypes.length > 0) {
				List<ITypeBinding> returnTypesList = removeDuplicateTypes(returnTypes);
				for (ITypeBinding returnType : returnTypesList) {
					if (returnType.isUnknown()) {
						returnTypeBuffer.append("null").append("|"); //$NON-NLS-1$ //$NON-NLS-2$
					} else if (returnType.isAmbiguous()) {
						returnTypeBuffer.append("Ambiguous").append("|"); //$NON-NLS-1$ //$NON-NLS-2$
					} else {
						returnTypeBuffer.append(returnType.getName()).append(
								"|"); //$NON-NLS-1$
					}
				}
				if (returnTypeBuffer.length() > 0) {
					retType = returnTypeBuffer.substring(0,
							returnTypeBuffer.length() - 1);
				}
			}

			typeParametersTypes = resolvedBinding.getParameterTypes();

			if (null != typeParametersTypes) {
				int i = 0;
				typeParameterNames = new String[typeParametersTypes.length];
				for (ITypeBinding type : typeParametersTypes) {
					typeParameterNames[i++] = type.getName();
				}
			}
		}

		String[] paramNames = method.getParameterNames();
		if (formalParameters == null) {
			parameterTypes = new String[paramNames.length];
			for (int i = 0; i < paramNames.length; i++) {
				parameterTypes[i] = UNKNOWN_TYPE;
			}
		}
		// add parameter type before parameter name
		for (int i = 0; i < paramNames.length; i++) {
			if (null != parameterTypes && null != parameterTypes[i]) {
				paramNames[i] = parameterTypes[i] + " " + paramNames[i]; //$NON-NLS-1$
				// } else {
				// String parameterType =
				// detectFromHungarianNotation(paramNames[i]);
				// if (parameterType != null) {
				// paramNames[i] = parameterType + " " + paramNames[i];
				// }
			}
		}
		IType declaringType = method.getDeclaringType();
		if (null != declaringType) {
			return StubUtility.getMethodComment(method.getScriptProject(),
					declaringType.getElementName(), method.getElementName(),
					paramNames, retType, typeParameterNames, overridden, false,
					lineDelimiter, newExceptions);
		}
		return StubUtility.getMethodComment(method.getScriptProject(), null,
				method.getElementName(), paramNames, retType,
				typeParameterNames, overridden, false, lineDelimiter,
				newExceptions);
	}

	private static Program generageProgram(IMethod method, Program program) {
		ISourceModule source = method.getSourceModule();
		ASTParser parserForExpected = ASTParser.newParser(ProjectOptions
				.getPhpVersion(source.getScriptProject().getProject()), source);
		try {
			parserForExpected.setSource(source);
			program = parserForExpected.createAST(new NullProgressMonitor());
			program.recordModifications();
			program.setSourceModule(source);
		} catch (Exception e) {
		}
		return program;
	}

	private static List<ITypeBinding> removeDuplicateTypes(
			ITypeBinding[] returnTypes) {

		List<ITypeBinding> types = new ArrayList<ITypeBinding>();

		for (ITypeBinding type : returnTypes) {
			if (!types.contains(type)) {
				types.add(type);
			}
		}
		return types;
	}

	// /**
	// * Detect variable type from variable named using Hungarian notation
	// */
	// private static String detectFromHungarianNotation(String paramName) {
	// if (paramName.matches("\\$ch[A-Z].*")) {
	// return "char";
	// }
	// if (paramName.matches("\\$ar[A-Z].*")) {
	// return "array";
	// }
	// if (paramName.matches("\\$str[A-Z].*")) {
	// return "string";
	// }
	// if (paramName.matches("\\$fl[A-Z].*")) {
	// return "float";
	// }
	// if (paramName.matches("\\$n[A-Z].*")) {
	// return "integer";
	// }
	// if (paramName.matches("\\$b[A-Z].*")) {
	// return "boolean";
	// }
	// return null;
	// }

	/**
	 * Returns the comment for a method or constructor using the comment code
	 * templates (constructor / method / overriding method). <code>null</code>
	 * is returned if the template is empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * 
	 * @param decl
	 *            The MethodDeclaration AST node that will be added as new
	 *            method. The node does not need to exist in an AST (no parent
	 *            needed) and does not need to resolve. See
	 *            {@link org.eclipse.jdt.core.dom.AST#newMethodDeclaration()}
	 *            for how to create such a node.
	 * @param isDeprecated
	 *            If set, the method is deprecated
	 * @param overriddenMethodName
	 *            If a method is overridden, the simple name of the overridden
	 *            method, or <code>null</code> if no method is overridden.
	 * @param overriddenMethodDeclaringTypeName
	 *            If a method is overridden, the fully qualified type name of
	 *            the overridden method's declaring type, or <code>null</code>
	 *            if no method is overridden.
	 * @param overriddenMethodParameterTypeNames
	 *            If a method is overridden, the fully qualified parameter type
	 *            names of the overridden method, or <code>null</code> if no
	 *            method is overridden.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the constructed comment or <code>null</code> if the
	 *         comment code template is empty. The returned string is
	 *         unformatted and and has no indent (formatting required).
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.2
	 */

	// public static String getMethodComment(IScriptProject sp, String
	// declaringTypeName, MethodDeclaration decl, boolean isDeprecated, String
	// overriddenMethodName, String overriddenMethodDeclaringTypeName, String[]
	// overriddenMethodParameterTypeNames, String lineDelimiter) throws
	// CoreException {
	// return StubUtility.getMethodComment(sp, declaringTypeName, decl,
	// isDeprecated, overriddenMethodName, overriddenMethodDeclaringTypeName,
	// overriddenMethodParameterTypeNames, false, lineDelimiter);
	// }
	/**
	 * Returns the content of the body for a method or constructor using the
	 * method body templates. <code>null</code> is returned if the template is
	 * empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName
	 *            Name of the method.
	 * @param isConstructor
	 *            Defines if the created body is for a constructor.
	 * @param bodyStatement
	 *            The code to be entered at the place of the variable
	 *            ${body_statement}.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the constructed body content or <code>null</code> if the
	 *         comment code template is empty. The returned string is
	 *         unformatted and and has no indent (formatting required).
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 */
	public static String getMethodBodyContent(IScriptProject sp,
			String declaringTypeName, String methodName, boolean isConstructor,
			String bodyStatement, String lineDelimiter) throws CoreException {
		return StubUtility.getMethodBodyContent(isConstructor, sp,
				declaringTypeName, methodName, bodyStatement, lineDelimiter);
	}

	/**
	 * Returns the content of body for a getter method using the getter method
	 * body template. <code>null</code> is returned if the template is empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName
	 *            The name of the getter method.
	 * @param fieldName
	 *            The name of the field to get in the getter method,
	 *            corresponding to the template variable for ${field}.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the constructed body content or <code>null</code> if the
	 *         comment code template is empty. The returned string is
	 *         unformatted and and has no indent (formatting required).
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getGetterMethodBodyContent(IScriptProject sp,
			String declaringTypeName, String methodName, String fieldName,
			String lineDelimiter) throws CoreException {
		return StubUtility.getGetterMethodBodyContent(sp, declaringTypeName,
				methodName, fieldName, lineDelimiter);
	}

	/**
	 * Returns the content of body for a setter method using the setter method
	 * body template. <code>null</code> is returned if the template is empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName
	 *            The name of the setter method.
	 * @param fieldName
	 *            The name of the field to be set in the setter method,
	 *            corresponding to the template variable for ${field}.
	 * @param paramName
	 *            The name of the parameter passed to the setter method,
	 *            corresponding to the template variable for $(param).
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the constructed body content or <code>null</code> if the
	 *         comment code template is empty. The returned string is
	 *         unformatted and and has no indent (formatting required).
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getSetterMethodBodyContent(IScriptProject sp,
			String declaringTypeName, String methodName, String fieldName,
			String paramName, String lineDelimiter) throws CoreException {
		return StubUtility.getSetterMethodBodyContent(sp, declaringTypeName,
				methodName, fieldName, paramName, lineDelimiter);
	}

	/**
	 * Returns the comment for a getter method using the getter comment
	 * template. <code>null</code> is returned if the template is empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName
	 *            Name of the method.
	 * @param fieldName
	 *            Name of the field to get.
	 * @param fieldType
	 *            The type of the field to get.
	 * @param bareFieldName
	 *            The field name without prefix or suffix.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the generated getter comment or <code>null</code> if the
	 *         code template is empty. The returned content is not indented.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getGetterComment(IScriptProject sp,
			String declaringTypeName, String methodName, String fieldName,
			String fieldType, String bareFieldName, String lineDelimiter)
			throws CoreException {
		return StubUtility.getGetterComment(sp, declaringTypeName, methodName,
				fieldName, fieldType, bareFieldName, lineDelimiter);
	}

	/**
	 * Returns the comment for a setter method using the setter method body
	 * template. <code>null</code> is returned if the template is empty.
	 * <p>
	 * The returned string is unformatted and not indented.
	 * 
	 * @param sp
	 *            The compilation unit to which the method belongs. The
	 *            compilation unit does not need to exist.
	 * @param declaringTypeName
	 *            Name of the type to which the method belongs. For inner types
	 *            the name must be qualified and include the outer types names
	 *            (dot separated). See
	 *            {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}.
	 * @param methodName
	 *            Name of the method.
	 * @param fieldName
	 *            Name of the field that is set.
	 * @param fieldType
	 *            The type of the field that is to set.
	 * @param paramName
	 *            The name of the parameter that used to set.
	 * @param bareFieldName
	 *            The field name without prefix or suffix.
	 * @param lineDelimiter
	 *            The line delimiter to be used.
	 * @return Returns the generated setter comment or <code>null</code> if the
	 *         code template is empty. The returned comment is not indented.
	 * @throws CoreException
	 *             Thrown when the evaluation of the code template fails.
	 * @since 3.0
	 */
	public static String getSetterComment(IScriptProject sp,
			String declaringTypeName, String methodName, String fieldName,
			String fieldType, String paramName, String bareFieldName,
			String lineDelimiter) throws CoreException {
		return StubUtility.getSetterComment(sp, declaringTypeName, methodName,
				fieldName, fieldType, paramName, bareFieldName, lineDelimiter);
	}
}
