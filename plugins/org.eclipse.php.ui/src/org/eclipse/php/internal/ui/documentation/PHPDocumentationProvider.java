/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.documentation;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.ui.documentation.IScriptDocumentationProvider;
import org.eclipse.php.internal.core.codeassist.FakeGroupMethod;
import org.eclipse.php.internal.core.codeassist.FakeGroupType;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.wst.sse.core.internal.Logger;

public class PHPDocumentationProvider implements IScriptDocumentationProvider {

	protected static final String DL_END = "</dl>"; //$NON-NLS-1$
	protected static final String DL_START = "<dl>"; //$NON-NLS-1$
	protected static final String DD_END = "</dd>"; //$NON-NLS-1$
	protected static final String DD_START = "<dd>"; //$NON-NLS-1$
	protected static final String DT_START = "<dt>"; //$NON-NLS-1$
	protected static final String DT_END = "</dt>"; //$NON-NLS-1$
	protected static final String FIELD_LOCATION = "Location";
	protected static final String FIELD_AUTHOR = "Author";
	protected static final String FIELD_CLASS = "Class";
	protected static final String FIELD_DESC = "Description";
	protected static final String FIELD_PARAMETERS = "Parameters";
	protected static final String FIELD_RETURNS = "Returns";
	protected static final String FIELD_THROWS = "Throws";
	protected static final String FIELD_DEPRECATED = "Deprecated";
	protected static final String FIELD_SEEALSO = "See Also";
	protected static final String FIELD_EXTENDS = "Extends";
	protected static final String FIELD_IMPLEMENTS = "Implements";
	protected static final String FIELD_NAMESPACE = "Namespace";
	protected static final String FIELD_INTERFACE = "Interface";
	protected static final String FIELD_TYPE = "Type";

	public Reader getInfo(IMember element, boolean lookIntoParents, boolean lookIntoExternal) {
		StringBuilder buf = new StringBuilder(DL_START);
		try {
			if (!appendBuiltinDoc(element, buf)) {
				if (element instanceof IMethod) {
					appendMethodInfo((IMethod) element, buf);
				} else if (element instanceof IType) {
					appendTypeInfo((IType) element, buf);
				} else if (element instanceof IField) {
					appendFieldInfo((IField) element, buf);
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		buf.append(DL_END);
		return new StringReader(buf.toString());
	}

	public Reader getInfo(String keyword) {
		String builtinDoc = BuiltinDoc.getString(keyword);
		if (builtinDoc.length() > 0) {
			StringBuilder buf = new StringBuilder(DL_START);
			buf.append(builtinDoc);
			buf.append(DL_END);
			return new StringReader(buf.toString());
		}
		return null;
	}

	private boolean appendBuiltinDoc(IMember element, StringBuilder buf) {
		String builtinDoc = BuiltinDoc.getString(element.getElementName());
		if (builtinDoc.length() > 0) {
			String fileName = getFileName(element);

			// append the file name
			appendDefinitionRow(FIELD_LOCATION, fileName, buf);

			// append the class name if it exists
			IType declaringType = element.getDeclaringType();
			appendTypeInfoRow(declaringType, buf);

			buf.append(builtinDoc);
			return true;
		}
		return false;
	}

	private void appendMethodInfo(IMethod method, StringBuilder buf) throws ModelException {
		
		if (method instanceof FakeGroupMethod) {
			appendDefinitionRow(FIELD_DESC, "This is a group containing multiple functions.", buf);
			return;
		}

		ISourceModule sourceModule = method.getSourceModule();
		String fileName = getFileName(method);

		// append the file name
		appendDefinitionRow(FIELD_LOCATION, fileName, buf);

		// append the class name if it exists
		IType declaringType = method.getDeclaringType();
		appendTypeInfoRow(declaringType, buf);

		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule);
		MethodDeclaration methodDeclaration = PHPModelUtils.getNodeByMethod(module, method);
		if (!(methodDeclaration instanceof IPHPDocAwareDeclaration)) {
			return;
		}

		PHPDocBlock doc = ((IPHPDocAwareDeclaration) methodDeclaration).getPHPDoc();
		if (doc == null) {
			return;
		}

		// append description if it exists
		appendShortDescription(doc, buf);

		// append parameters info
		appendTagInfo(doc, PHPDocTag.PARAM, FIELD_PARAMETERS, buf);

		// append return type
		appendTagInfo(doc, PHPDocTag.RETURN, FIELD_RETURNS, buf);

		// append throw info
		appendTagInfo(doc, PHPDocTag.THROWS, FIELD_THROWS, buf);

		// append see also info
		appendTagInfo(doc, PHPDocTag.SEE, FIELD_SEEALSO, buf);

		// append deprecated info
		appendTagInfo(doc, PHPDocTag.DEPRECATED, FIELD_DEPRECATED, buf);

		// append author info
		appendTagInfo(doc, PHPDocTag.AUTHOR, FIELD_AUTHOR, buf);
	}

	private void appendTypeInfo(IType type, StringBuilder buf) throws ModelException {
		
		if (type instanceof FakeGroupType) {
			appendDefinitionRow(FIELD_DESC, "This is a group containing multiple classes or interfaces.", buf);
			return;
		}

		ISourceModule sourceModule = type.getSourceModule();
		String fileName = getFileName(type);

		// append the file name
		appendDefinitionRow(FIELD_LOCATION, fileName, buf);
		
		// append the namespace name if it exists
		IType declaringType = type.getDeclaringType();
		appendTypeInfoRow(declaringType, buf);

		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule);
		TypeDeclaration typeDeclaration = PHPModelUtils.getNodeByClass(module, type);

		if (typeDeclaration instanceof ClassDeclaration) {
			ClassDeclaration classDeclaration = (ClassDeclaration) typeDeclaration;
			String superClassName = classDeclaration.getSuperClassName();
			if (superClassName != null) {
				appendDefinitionRow(FIELD_EXTENDS, superClassName, buf);
			}
			String[] interfaceNames = classDeclaration.getInterfaceNames();
			if (interfaceNames != null) {
				appendDefinitionRows(FIELD_IMPLEMENTS, interfaceNames, buf);
			}
		} else {
			String[] superClassNames = type.getSuperClasses();
			if (superClassNames != null) {
				appendDefinitionRows(FIELD_EXTENDS, superClassNames, buf);
			}
		}

		if (!(typeDeclaration instanceof IPHPDocAwareDeclaration)) {
			return;
		}
		PHPDocBlock doc = ((IPHPDocAwareDeclaration) typeDeclaration).getPHPDoc();
		if (doc == null) {
			return;
		}

		// append description if it exists
		appendShortDescription(doc, buf);

		// append see also info
		appendTagInfo(doc, PHPDocTag.SEE, FIELD_SEEALSO, buf);

		// append deprecated info
		appendTagInfo(doc, PHPDocTag.DEPRECATED, FIELD_DEPRECATED, buf);

		// append author info
		appendTagInfo(doc, PHPDocTag.AUTHOR, FIELD_AUTHOR, buf);
	}

	private void appendFieldInfo(IField field, StringBuilder buf) throws ModelException {

		ISourceModule sourceModule = field.getSourceModule();
		String fileName = getFileName(field);

		// append the file name
		appendDefinitionRow(FIELD_LOCATION, fileName, buf);

		// append the class name if it exists
		IType declaringType = field.getDeclaringType();
		appendTypeInfoRow(declaringType, buf);

		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule);
		ASTNode node = PHPModelUtils.getNodeByField(module, field);

		if (!(node instanceof IPHPDocAwareDeclaration)) {
			return;
		}
		PHPDocBlock doc = ((IPHPDocAwareDeclaration) node).getPHPDoc();
		if (doc == null) {
			return;
		}

		// append description if it exists
		appendShortDescription(doc, buf);
		
		// append type information
		for (PHPDocTag tag : doc.getTags()) {
			if (tag.getTagKind() == PHPDocTag.VAR) {
				SimpleReference[] references = tag.getReferences();
				StringBuilder typeBuf = new StringBuilder();
				for (SimpleReference ref : references) {
					if (typeBuf.length() > 0) {
						typeBuf.append(" | ");
					}
					typeBuf.append(ref.getName());
				}
				appendDefinitionRow(FIELD_TYPE, typeBuf.toString(), buf);
			}
		}
	}
	
	private void appendTypeInfoRow(IType type, StringBuilder buf) {
		if (type == null) {
			return;
		}
		
		int flags = 0;
		try {
			flags = type.getFlags();
		} catch (ModelException e) {
		}
		if (PHPFlags.isNamespace(flags)) {
			appendDefinitionRow(FIELD_NAMESPACE, type.getElementName(), buf);
		}
		else if (PHPFlags.isInterface(flags)) {
			appendDefinitionRow(FIELD_INTERFACE, type.getElementName(), buf);
		}
		else {
			appendDefinitionRow(FIELD_CLASS, type.getElementName(), buf);
		}
	}

	protected String nl2br(String str) {
		return str.replaceAll("\\n", "<br>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void appendDefinitionRow(String field, String data, StringBuilder buf) {
		buf.append(DT_START).append(field).append(DT_END);
		buf.append(DD_START).append(data).append(DD_END);
	}

	protected void appendDefinitionRows(String field, String[] data, StringBuilder buf) {
		buf.append(DT_START).append(field).append(DT_END);
		for (String row : data) {
			buf.append(DD_START).append(row).append(DD_END);
		}
	}

	protected void appendShortDescription(PHPDocBlock doc, StringBuilder buf) {
		String desc = doc.getShortDescription();
		if (desc != null && desc.length() > 0) {
			appendDefinitionRow(FIELD_DESC, nl2br(desc), buf);
		}
	}

	protected void appendTagInfo(PHPDocBlock doc, int tagKind, String field, StringBuilder buf) {
		PHPDocTag[] tags = getTags(doc, tagKind);
		if (tags.length > 0) {
			buf.append(DT_START).append(field).append(DT_END);
			for (PHPDocTag tag : tags) {
				buf.append(DD_START).append(tag.getValue()).append(DD_END);
			}
		}
	}

	protected PHPDocTag[] getTags(PHPDocBlock doc, int kind) {
		List<PHPDocTag> tags = new LinkedList<PHPDocTag>();
		for (PHPDocTag tag : doc.getTags()) {
			if (tag.getTagKind() == kind) {
				tags.add(tag);
			}
		}
		return tags.toArray(new PHPDocTag[tags.size()]);
	}
	
	protected String getFileName(IMember modelElement) {
		IPath path = EnvironmentPathUtils.getLocalPath(modelElement.getSourceModule().getPath());
		String fileName = path.toOSString();
		if (fileName.startsWith("\\") || fileName.startsWith("/")) {
			fileName = fileName.substring(1);
		}
		return fileName;
	}
}
