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
package org.eclipse.php.internal.ui.corext.codemanipulation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.corext.util.Strings;
import org.eclipse.dltk.internal.ui.DLTKUIStatus;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.persistence.TemplatePersistenceData;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.util.Signature;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.template.php.CodeTemplateContext;
import org.eclipse.php.internal.ui.corext.template.php.CodeTemplateContextType;
import org.eclipse.php.internal.ui.viewsupport.ProjectTemplateStore;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.wst.jsdt.internal.compiler.env.ICompilationUnit;

public class StubUtility {

	private static final String[] EMPTY = new String[0];

	private static final Set<String> VALID_TYPE_BODY_TEMPLATES;
	static {
		VALID_TYPE_BODY_TEMPLATES = new HashSet<String>();
		VALID_TYPE_BODY_TEMPLATES.add(CodeTemplateContextType.CLASSBODY_ID);
		VALID_TYPE_BODY_TEMPLATES.add(CodeTemplateContextType.INTERFACEBODY_ID);
		VALID_TYPE_BODY_TEMPLATES.add(CodeTemplateContextType.ENUMBODY_ID);
		VALID_TYPE_BODY_TEMPLATES
				.add(CodeTemplateContextType.ANNOTATIONBODY_ID);
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 */
	public static String getMethodBodyContent(boolean isConstructor,
			IScriptProject project, String destTypeName, String methodName,
			String bodyStatement, String lineDelimiter) throws CoreException {
		String templateName = isConstructor ? CodeTemplateContextType.CONSTRUCTORSTUB_ID
				: CodeTemplateContextType.METHODSTUB_ID;
		Template template = getCodeTemplate(templateName, project);
		if (template == null) {
			return bodyStatement;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), project, lineDelimiter);
		context.setVariable(CodeTemplateContextType.ENCLOSING_METHOD,
				methodName);
		context.setVariable(CodeTemplateContextType.ENCLOSING_TYPE,
				destTypeName);
		context.setVariable(CodeTemplateContextType.BODY_STATEMENT,
				bodyStatement);
		String str = evaluateTemplate(context, template,
				new String[] { CodeTemplateContextType.BODY_STATEMENT });
		if (str == null && !Strings.containsOnlyWhitespaces(bodyStatement)) {
			return bodyStatement;
		}
		return str;
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 */
	public static String getGetterMethodBodyContent(IScriptProject project,
			String destTypeName, String methodName, String fieldName,
			String lineDelimiter) throws CoreException {
		String templateName = CodeTemplateContextType.GETTERSTUB_ID;
		Template template = getCodeTemplate(templateName, project);
		if (template == null) {
			return null;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), project, lineDelimiter);
		context.setVariable(CodeTemplateContextType.ENCLOSING_METHOD,
				methodName);
		context.setVariable(CodeTemplateContextType.ENCLOSING_TYPE,
				destTypeName);
		context.setVariable(CodeTemplateContextType.FIELD, fieldName);

		return evaluateTemplate(context, template);
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 */
	public static String getSetterMethodBodyContent(IScriptProject project,
			String destTypeName, String methodName, String fieldName,
			String paramName, String lineDelimiter) throws CoreException {
		String templateName = CodeTemplateContextType.SETTERSTUB_ID;
		Template template = getCodeTemplate(templateName, project);
		if (template == null) {
			return null;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), project, lineDelimiter);
		context.setVariable(CodeTemplateContextType.ENCLOSING_METHOD,
				methodName);
		context.setVariable(CodeTemplateContextType.ENCLOSING_TYPE,
				destTypeName);
		context.setVariable(CodeTemplateContextType.FIELD, fieldName);
		context.setVariable(CodeTemplateContextType.FIELD_TYPE, fieldName);
		context.setVariable(CodeTemplateContextType.PARAM, paramName);

		return evaluateTemplate(context, template);
	}

	public static String getCatchBodyContent(IScriptProject sp,
			String exceptionType, String variableName, String enclosingType,
			String enclosingMethod, String lineDelimiter) throws CoreException {
		Template template = getCodeTemplate(
				CodeTemplateContextType.CATCHBLOCK_ID, sp);
		if (template == null) {
			return null;
		}

		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelimiter);
		context.setVariable(CodeTemplateContextType.ENCLOSING_TYPE,
				enclosingType);
		context.setVariable(CodeTemplateContextType.ENCLOSING_METHOD,
				enclosingMethod);
		context.setVariable(CodeTemplateContextType.EXCEPTION_TYPE,
				exceptionType);
		context.setVariable(CodeTemplateContextType.EXCEPTION_VAR, variableName);
		return evaluateTemplate(context, template);
	}

	public static String getCompilationUnitContent(IScriptProject sp,
			String fileComment, String typeComment, String typeContent,
			String lineDelimiter) throws CoreException {
		Template template = getCodeTemplate(CodeTemplateContextType.NEWTYPE_ID,
				sp);
		if (template == null) {
			return null;
		}

		IScriptProject project = sp;
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), project, lineDelimiter);
		context.setVariable(CodeTemplateContextType.TYPE_COMMENT,
				typeComment != null ? typeComment : ""); //$NON-NLS-1$
		context.setVariable(CodeTemplateContextType.FILE_COMMENT,
				fileComment != null ? fileComment : ""); //$NON-NLS-1$
		context.setVariable(CodeTemplateContextType.TYPE_DECLARATION,
				typeContent);
		context.setVariable(CodeTemplateContextType.TYPENAME,
				sp.getElementName());

		String[] fullLine = { CodeTemplateContextType.PACKAGE_DECLARATION,
				CodeTemplateContextType.FILE_COMMENT,
				CodeTemplateContextType.TYPE_COMMENT };
		return evaluateTemplate(context, template, fullLine);
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 * 
	 * @see org.eclipse.jdt.ui.CodeGeneration#getFileComment(ICompilationUnit,
	 * String)
	 */
	public static String getFileComment(ISourceModule sm, String lineDelimiter)
			throws CoreException {
		Template template = getCodeTemplate(
				CodeTemplateContextType.FILECOMMENT_ID, sm.getScriptProject());
		if (template == null) {
			return null;
		}

		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sm.getScriptProject(),
				lineDelimiter);
		context.setVariable(CodeTemplateContextType.FILENAME,
				sm.getElementName());
		context.setVariable(CodeTemplateContextType.PROJECTNAME, sm
				.getScriptProject().getElementName());
		return evaluateTemplate(context, template);
	}

	public static String getFileComment(IScriptProject sp, String lineDelimiter)
			throws CoreException {
		Template template = getCodeTemplate(
				CodeTemplateContextType.FILECOMMENT_ID, sp);
		if (template == null) {
			return null;
		}

		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelimiter);
		context.setVariable(CodeTemplateContextType.FILENAME,
				sp.getElementName());
		return evaluateTemplate(context, template);
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 * 
	 * @see org.eclipse.jdt.ui.CodeGeneration#getTypeComment(ICompilationUnit,
	 * String, String[], String)
	 */
	public static String getTypeComment(IScriptProject sp,
			String typeQualifiedName, String[] typeParameterNames,
			String lineDelim) throws CoreException {
		Template template = getCodeTemplate(
				CodeTemplateContextType.TYPECOMMENT_ID, sp);
		if (template == null) {
			return null;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelim);
		context.setVariable(CodeTemplateContextType.ENCLOSING_TYPE,
				Signature.getQualifier(typeQualifiedName));
		context.setVariable(CodeTemplateContextType.TYPENAME,
				Signature.getSimpleName(typeQualifiedName));

		TemplateBuffer buffer;
		try {
			buffer = context.evaluate(template);
		} catch (BadLocationException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		} catch (TemplateException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		String str = buffer.getString();
		if (Strings.containsOnlyWhitespaces(str)) {
			return null;
		}

		TemplateVariable position = findVariable(buffer,
				CodeTemplateContextType.TAGS); // look if PHPDoc tags have to
		// be added
		if (position == null) {
			return str;
		}

		IDocument document = new Document(str);
		int[] tagOffsets = position.getOffsets();
		for (int i = tagOffsets.length - 1; i >= 0; i--) { // from last to first
			try {
				insertTag(document, tagOffsets[i], position.getLength(), EMPTY,
						null, typeParameterNames, false, lineDelim, null);
			} catch (BadLocationException e) {
				throw new CoreException(DLTKUIStatus.createError(IStatus.ERROR,
						e));
			}
		}
		return document.get();
	}

	/*
	 * Returns the parameters type names used in see tags. Currently, these are
	 * always fully qualified.
	 */
	public static String[] getParameterTypeNamesForSeeTag(
			IFunctionBinding binding) {
		ITypeBinding[] typeParametersTypes = binding.getParameterTypes();
		String[] typeParameterNames = null;
		if (typeParametersTypes != null) {
			typeParameterNames = new String[typeParametersTypes.length];
			int i = 0;
			for (ITypeBinding type : typeParametersTypes) {
				typeParameterNames[i++] = type.getName();
			}
		}
		return typeParameterNames;
	}

	/*
	 * Returns the parameters type names used in see tags. Currently, these are
	 * always fully qualified.
	 */
	private static String[] getParameterTypeNamesForSeeTag(IMethod overridden)
			throws ModelException {
		try {
			Program program = SharedASTProvider.getAST(
					overridden.getSourceModule(), SharedASTProvider.WAIT_YES,
					new NullProgressMonitor());
			ASTNode elementAt = program.getElementAt(overridden
					.getSourceRange().getOffset());
			IFunctionBinding resolvedBinding = null;

			if (elementAt instanceof MethodDeclaration) {
				MethodDeclaration methodDeclaration = (MethodDeclaration) elementAt;
				resolvedBinding = methodDeclaration.resolveMethodBinding();
			} else if (elementAt instanceof FunctionDeclaration) {
				FunctionDeclaration functionDeclaration = (FunctionDeclaration) elementAt;
				resolvedBinding = functionDeclaration.resolveFunctionBinding();
			}

			if (resolvedBinding != null) {
				return getParameterTypeNamesForSeeTag(resolvedBinding);
			}
		} catch (IOException e) {
			PHPUiPlugin.log(e);
		}

		// fall back code. Not good for generic methods!
		String[] paramTypes = overridden.getParameterNames();
		String[] paramTypeNames = new String[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++) {
			paramTypeNames[i] = Signature.toString(Signature
					.getTypeErasure(paramTypes[i]));
		}
		return paramTypeNames;
	}

	private static String getSeeTag(String declaringClassQualifiedName,
			String methodName, String[] parameterTypesQualifiedNames) {
		StringBuffer buf = new StringBuffer();
		buf.append("@see "); //$NON-NLS-1$
		buf.append(declaringClassQualifiedName);
		buf.append("::"); //$NON-NLS-1$
		buf.append(methodName);
		buf.append('(');

		if (null != parameterTypesQualifiedNames) {
			for (int i = 0; i < parameterTypesQualifiedNames.length; i++) {
				if (i > 0) {
					buf.append(", "); //$NON-NLS-1$
				}
				buf.append(parameterTypesQualifiedNames[i]);
			}
		}
		buf.append(')');
		return buf.toString();
	}

	public static String[] getTypeParameterNames(String[] typeParameters) {
		String[] typeParametersNames = new String[typeParameters.length];
		for (int i = 0; i < typeParameters.length; i++) {
			typeParametersNames[i] = typeParameters[i];
		}
		return typeParametersNames;
	}

	/**
	 * Don't use this method directly, use CodeGeneration.
	 * 
	 * @param templateID
	 *            the template id of the type body to get. Valid id's are
	 *            {@link CodeTemplateContextType#CLASSBODY_ID},
	 *            {@link CodeTemplateContextType#INTERFACEBODY_ID},
	 *            {@link CodeTemplateContextType#ENUMBODY_ID},
	 *            {@link CodeTemplateContextType#ANNOTATIONBODY_ID},
	 * @param sp
	 *            the compilation unit to which the template is added
	 * @param typeName
	 *            the type name
	 * @param lineDelim
	 *            the line delimiter to use
	 * @return return the type body template or <code>null</code>
	 * @throws CoreException
	 *             thrown if the template could not be evaluated
	 * @see org.eclipse.jdt.ui.CodeGeneration#getTypeBody(String,
	 *      ICompilationUnit, String, String)
	 */
	public static String getTypeBody(String templateID, IScriptProject sp,
			String typeName, String lineDelim) throws CoreException {
		if (!VALID_TYPE_BODY_TEMPLATES.contains(templateID)) {
			throw new IllegalArgumentException(
					"Invalid code template ID: " + templateID); //$NON-NLS-1$
		}

		Template template = getCodeTemplate(templateID, sp);
		if (template == null) {
			return null;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelim);
		// context.setCompilationUnitVariables(sp);
		context.setVariable(CodeTemplateContextType.TYPENAME, typeName);

		return evaluateTemplate(context, template);
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 * 
	 * @see org.eclipse.jdt.ui.CodeGeneration#getMethodComment(ICompilationUnit,
	 * String, String, String[], String[], String, String[], IMethod, String)
	 */
	public static String getMethodComment(IScriptProject sp, String typeName,
			String methodName, String[] paramNames, String retTypeSig,
			String[] typeParameterNames, IMethod target, boolean delegate,
			String lineDelimiter, List<String> newExceptions)
			throws CoreException {
		String templateName = CodeTemplateContextType.METHODCOMMENT_ID;
		if (target != null) {
			if (delegate)
				templateName = CodeTemplateContextType.DELEGATECOMMENT_ID;
			else
				templateName = CodeTemplateContextType.OVERRIDECOMMENT_ID;
		} else if (retTypeSig == null
				&& typeName != null
				&& (typeName.equals(methodName) || "constructor" //$NON-NLS-1$
						.equals(methodName))) {
			templateName = CodeTemplateContextType.CONSTRUCTORCOMMENT_ID;
		}
		Template template = getCodeTemplate(templateName, sp);
		if (template == null) {
			return null;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelimiter);
		// context.setCompilationUnitVariables(sp);
		context.setVariable(CodeTemplateContextType.ENCLOSING_TYPE, typeName);
		context.setVariable(CodeTemplateContextType.ENCLOSING_METHOD,
				methodName);

		if (retTypeSig != null) {
			context.setVariable(CodeTemplateContextType.RETURN_TYPE, retTypeSig);
		}
		if (target != null) {
			String targetTypeName = target.getDeclaringType()
					.getTypeQualifiedName(
							PHPModelUtils.ENCLOSING_TYPE_SEPARATOR);
			if (targetTypeName.indexOf(PHPModelUtils.ENCLOSING_TYPE_SEPARATOR) > 0) {
				targetTypeName = PHPModelUtils.ENCLOSING_TYPE_SEPARATOR
						+ targetTypeName;
			}
			String[] targetParamTypeNames = getParameterTypeNamesForSeeTag(target);
			if (delegate)
				context.setVariable(
						CodeTemplateContextType.SEE_TO_TARGET_TAG,
						getSeeTag(targetTypeName, methodName,
								targetParamTypeNames));
			else
				context.setVariable(
						CodeTemplateContextType.SEE_TO_OVERRIDDEN_TAG,
						getSeeTag(targetTypeName, methodName,
								targetParamTypeNames));
		}
		TemplateBuffer buffer;
		try {
			buffer = context.evaluate(template);
		} catch (BadLocationException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		} catch (TemplateException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		if (buffer == null) {
			return null;
		}

		String str = buffer.getString();
		if (Strings.containsOnlyWhitespaces(str)) {
			return null;
		}
		TemplateVariable position = findVariable(buffer,
				CodeTemplateContextType.TAGS); // look if PHPDoc tags have to
		// be added
		if (position == null) {
			return str;
		}

		IDocument document = new Document(str);

		int[] tagOffsets = position.getOffsets();
		for (int i = tagOffsets.length - 1; i >= 0; i--) { // from last to first
			try {
				insertTag(document, tagOffsets[i], position.getLength(),
						paramNames, retTypeSig, typeParameterNames, false,
						lineDelimiter, newExceptions);
			} catch (BadLocationException e) {
				throw new CoreException(new Status(IStatus.ERROR,
						PHPUiPlugin.ID, e.getClass().getName(), e));
			}
		}
		return document.get();
	}

	// remove lines for empty variables
	private static String fixEmptyVariables(TemplateBuffer buffer,
			String[] variables) throws MalformedTreeException,
			BadLocationException {
		IDocument doc = new Document(buffer.getString());
		int nLines = doc.getNumberOfLines();
		MultiTextEdit edit = new MultiTextEdit();
		HashSet<Integer> removedLines = new HashSet<Integer>();
		for (int i = 0; i < variables.length; i++) {
			TemplateVariable position = findVariable(buffer, variables[i]); // look
			// if
			// Javadoc
			// tags
			// have
			// to
			// be
			// added
			if (position == null || position.getLength() > 0) {
				continue;
			}
			int[] offsets = position.getOffsets();
			for (int k = 0; k < offsets.length; k++) {
				int line = doc.getLineOfOffset(offsets[k]);
				IRegion lineInfo = doc.getLineInformation(line);
				int offset = lineInfo.getOffset();
				String str = doc.get(offset, lineInfo.getLength());
				if (Strings.containsOnlyWhitespaces(str) && nLines > line + 1
						&& removedLines.add(new Integer(line))) {
					int nextStart = doc.getLineOffset(line + 1);
					edit.addChild(new DeleteEdit(offset, nextStart - offset));
				}
			}
		}
		edit.apply(doc, 0);
		return doc.get();
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 */
	public static String getFieldComment(IScriptProject sp, String fieldType,
			String fieldName, String lineDelimiter) throws CoreException {
		Template template = getCodeTemplate(
				CodeTemplateContextType.FIELDCOMMENT_ID, sp);
		if (template == null) {
			return null;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelimiter);
		// context.setCompilationUnitVariables(sp);
		context.setVariable(CodeTemplateContextType.FIELD_TYPE, fieldType);
		context.setVariable(CodeTemplateContextType.FIELD, fieldName);

		return evaluateTemplate(context, template);
	}

	public static String getVarComment(IScriptProject sp, String fieldType,
			String fieldName, String lineDelimiter) throws CoreException {
		Template template = getCodeTemplate(
				CodeTemplateContextType.VARCOMMENT_ID, sp);
		if (template == null) {
			return null;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelimiter);
		context.setVariable(CodeTemplateContextType.FIELD_TYPE, fieldType);
		context.setVariable(CodeTemplateContextType.FIELD, fieldName);

		return evaluateTemplate(context, template);
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 * 
	 * @see org.eclipse.jdt.ui.CodeGeneration#getSetterComment(ICompilationUnit,
	 * String, String, String, String, String, String, String)
	 */
	public static String getSetterComment(IScriptProject sp, String typeName,
			String methodName, String fieldName, String fieldType,
			String paramName, String bareFieldName, String lineDelimiter)
			throws CoreException {
		String templateName = CodeTemplateContextType.SETTERCOMMENT_ID;
		Template template = getCodeTemplate(templateName, sp);
		if (template == null) {
			return null;
		}

		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelimiter);
		context.setVariable(CodeTemplateContextType.ENCLOSING_TYPE, typeName);
		context.setVariable(CodeTemplateContextType.ENCLOSING_METHOD,
				methodName);
		context.setVariable(CodeTemplateContextType.FIELD, fieldName);
		context.setVariable(CodeTemplateContextType.FIELD_TYPE, fieldType);
		context.setVariable(CodeTemplateContextType.BARE_FIELD_NAME,
				bareFieldName);
		context.setVariable(CodeTemplateContextType.PARAM, paramName);

		return evaluateTemplate(context, template);
	}

	/*
	 * Don't use this method directly, use CodeGeneration.
	 * 
	 * @see org.eclipse.jdt.ui.CodeGeneration#getGetterComment(ICompilationUnit,
	 * String, String, String, String, String, String)
	 */
	public static String getGetterComment(IScriptProject sp, String typeName,
			String methodName, String fieldName, String fieldType,
			String bareFieldName, String lineDelimiter) throws CoreException {
		String templateName = CodeTemplateContextType.GETTERCOMMENT_ID;
		Template template = getCodeTemplate(templateName, sp);
		if (template == null) {
			return null;
		}
		CodeTemplateContext context = new CodeTemplateContext(
				template.getContextTypeId(), sp, lineDelimiter);
		context.setVariable(CodeTemplateContextType.ENCLOSING_TYPE, typeName);
		context.setVariable(CodeTemplateContextType.ENCLOSING_METHOD,
				methodName);
		context.setVariable(CodeTemplateContextType.FIELD, fieldName);
		context.setVariable(CodeTemplateContextType.FIELD_TYPE, fieldType);
		context.setVariable(CodeTemplateContextType.BARE_FIELD_NAME,
				bareFieldName);

		return evaluateTemplate(context, template);
	}

	private static String evaluateTemplate(CodeTemplateContext context,
			Template template) throws CoreException {
		TemplateBuffer buffer;
		try {
			buffer = context.evaluate(template);
		} catch (BadLocationException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		} catch (TemplateException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		if (buffer == null)
			return null;
		String str = buffer.getString();
		if (Strings.containsOnlyWhitespaces(str)) {
			return null;
		}
		return str;
	}

	private static String evaluateTemplate(CodeTemplateContext context,
			Template template, String[] fullLineVariables) throws CoreException {
		TemplateBuffer buffer;
		try {
			buffer = context.evaluate(template);
			if (buffer == null)
				return null;
			String str = fixEmptyVariables(buffer, fullLineVariables);
			if (Strings.containsOnlyWhitespaces(str)) {
				return null;
			}
			return str;
		} catch (BadLocationException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		} catch (TemplateException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
	}

	/*
	 * private static ASTNode getReturnType(MethodDeclaration decl) { // used
	 * from API, can't eliminate return decl.getAST().apiLevel() == AST.JLS2 ?
	 * decl.getReturnType() : decl.getReturnType2(); }
	 */

	private static TemplateVariable findVariable(TemplateBuffer buffer,
			String variable) {
		TemplateVariable[] positions = buffer.getVariables();
		for (int i = 0; i < positions.length; i++) {
			TemplateVariable curr = positions[i];
			if (variable.equals(curr.getType())) {
				return curr;
			}
		}
		return null;
	}

	private static void insertTag(IDocument textBuffer, int offset, int length,
			String[] paramNames, String returnType,
			String[] typeParameterNames, boolean isDeprecated,
			String lineDelimiter, List<String> newExceptions)
			throws BadLocationException {
		IRegion region = textBuffer.getLineInformationOfOffset(offset);
		if (region == null) {
			return;
		}
		String lineStart = textBuffer.get(region.getOffset(),
				offset - region.getOffset());

		StringBuffer buf = new StringBuffer();
		if (null != typeParameterNames) {
			for (int i = 0; i < typeParameterNames.length; i++) {
				if (buf.length() > 0) {
					buf.append(lineDelimiter).append(lineStart);
				}
				buf.append("@param <").append(typeParameterNames[i]).append('>'); //$NON-NLS-1$
			}
		}
		for (int i = 0; i < paramNames.length; i++) {
			if (buf.length() > 0) {
				buf.append(lineDelimiter).append(lineStart);
			}
			buf.append("@param ").append(paramNames[i]); //$NON-NLS-1$
		}
		if (null != newExceptions) {
			for (Iterator<String> iterator = newExceptions.iterator(); iterator
					.hasNext();) {
				String exception = iterator.next();
				if (buf.length() > 0) {
					buf.append(lineDelimiter).append(lineStart);
				}
				buf.append("@throws ").append(exception); //$NON-NLS-1$
			}
		}
		if (returnType != null && !returnType.equals("void")) { //$NON-NLS-1$
			if (buf.length() > 0) {
				buf.append(lineDelimiter).append(lineStart);
			}
			buf.append("@return ").append(returnType); //$NON-NLS-1$
		}

		if (isDeprecated) {
			if (buf.length() > 0) {
				buf.append(lineDelimiter).append(lineStart);
			}
			buf.append("@deprecated"); //$NON-NLS-1$
		}
		if (buf.length() == 0 && isAllCommentWhitespace(lineStart)) {
			int prevLine = textBuffer.getLineOfOffset(offset) - 1;
			if (prevLine > 0) {
				IRegion prevRegion = textBuffer.getLineInformation(prevLine);
				int prevLineEnd = prevRegion.getOffset()
						+ prevRegion.getLength();
				// clear full line
				textBuffer.replace(prevLineEnd, offset + length - prevLineEnd,
						""); //$NON-NLS-1$
				return;
			}
		}
		textBuffer.replace(offset, length, buf.toString());
	}

	private static boolean isAllCommentWhitespace(String lineStart) {
		for (int i = 0; i < lineStart.length(); i++) {
			char ch = lineStart.charAt(i);
			if (!Character.isWhitespace(ch) && ch != '*') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the line delimiter which is used in the specified project.
	 * 
	 * @param project
	 *            the java project, or <code>null</code>
	 * @return the used line delimiter
	 */
	public static String getLineDelimiterUsed(IScriptProject project) {
		return getProjectLineDelimiter(project);
	}

	private static String getProjectLineDelimiter(IScriptProject project) {
		if (project == null) {
			assert false;
			return null;
		}

		String lineDelimiter = getLineDelimiterPreference(project);
		if (lineDelimiter != null)
			return lineDelimiter;

		return System.getProperty("line.separator", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static String getLineDelimiterPreference(IScriptProject project) {
		IScopeContext[] scopeContext;
		if (project != null) {
			// project preference
			scopeContext = new IScopeContext[] { new ProjectScope(
					project.getProject()) };
			String lineDelimiter = Platform.getPreferencesService().getString(
					Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null,
					scopeContext);
			if (lineDelimiter != null)
				return lineDelimiter;
		}
		// workspace preference
		scopeContext = new IScopeContext[] { new InstanceScope() };
		String platformDefault = System.getProperty("line.separator", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		return Platform.getPreferencesService().getString(Platform.PI_RUNTIME,
				Platform.PREF_LINE_SEPARATOR, platformDefault, scopeContext);
	}

	// --------------------------- name suggestions --------------------------

	public static final int STATIC_FIELD = 1;
	public static final int INSTANCE_FIELD = 2;
	public static final int CONSTANT_FIELD = 3;
	public static final int PARAMETER = 4;
	public static final int LOCAL = 5;

	/**
	 * Only to be used by tests
	 * 
	 * @param templateId
	 *            the template id
	 * @param pattern
	 *            the new pattern
	 * @param project
	 *            not used
	 */
	public static void setCodeTemplate(String templateId, String pattern,
			IScriptProject project) {
		TemplateStore codeTemplateStore = PHPUiPlugin.getDefault()
				.getCodeTemplateStore();
		TemplatePersistenceData data = codeTemplateStore
				.getTemplateData(templateId);
		Template orig = data.getTemplate();
		Template copy = new Template(orig.getName(), orig.getDescription(),
				orig.getContextTypeId(), pattern, true);
		data.setTemplate(copy);
	}

	private static Template getCodeTemplate(String id, IScriptProject sp) {
		if (sp == null)
			return PHPUiPlugin.getDefault().getCodeTemplateStore()
					.findTemplateById(id);
		ProjectTemplateStore projectStore = new ProjectTemplateStore(
				sp.getProject());
		try {
			projectStore.load();
		} catch (IOException e) {
			PHPUiPlugin.log(e);
		}
		return projectStore.findTemplateById(id);
	}
}
