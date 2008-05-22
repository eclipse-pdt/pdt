package org.eclipse.php.internal.ui.documentation;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.ui.documentation.IScriptDocumentationProvider;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.wst.sse.core.internal.Logger;

public class PHPDocumentationProvider implements IScriptDocumentationProvider {
	
	private static final String DL_END = "</dl>"; //$NON-NLS-1$
	private static final String DL_START = "<dl>"; //$NON-NLS-1$
	private static final String DD_END = "</dd>"; //$NON-NLS-1$
	private static final String DD_START = "<dd>"; //$NON-NLS-1$
	private static final String DT_START = "<dt>"; //$NON-NLS-1$
	private static final String DT_END = "</dt>"; //$NON-NLS-1$
	private static final String FIELD_LOCATION = "Location";
	private static final String FIELD_AUTHOR = "Author";
	private static final String FIELD_CLASS = "Class";
	private static final String FIELD_DESC = "Description";
	private static final String FIELD_PARAMETERS = "Parameters";
	private static final String FIELD_RETURNS = "Returns";
	private static final String FIELD_THROWS = "Throws";
	private static final String FIELD_DEPRECATED = "Deprecated";
	private static final String FIELD_SEEALSO = "See Also";
	private static final String FIELD_EXTENDS = "Extends";
	private static final String FIELD_IMPLEMENTS = "Implements";

	public Reader getInfo(IMember element, boolean lookIntoParents, boolean lookIntoExternal) {
		StringBuilder buf = new StringBuilder(DL_START);
		try {
			if (!appendBuiltinDoc(element, buf)) {
				if (element instanceof IMethod) {
					appendMethodInfo((IMethod)element, buf);
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

	public Reader getInfo(String content) {
		return null;
	}
	
	private boolean appendBuiltinDoc(IMember element, StringBuilder buf) {
		String builtinDoc = BuiltinDoc.getString(element.getElementName());
		if (builtinDoc.length() > 0) {
			ISourceModule sourceModule = element.getSourceModule();
			String fileName = sourceModule.getElementName();

			// append the file name
			appendDefinitionRow(FIELD_LOCATION, fileName, buf);
			
			// append the class name if it exists
			IType declaringType = element.getDeclaringType();
			if (declaringType != null) {
				appendDefinitionRow(FIELD_CLASS, declaringType.getElementName(), buf);
			}
			
			buf.append(builtinDoc);
			return true;
		}
		return false;
	}

	private void appendMethodInfo(IMethod method, StringBuilder buf) throws ModelException {
		
		ISourceModule sourceModule = method.getSourceModule();
		String fileName = sourceModule.getElementName();

		// append the file name
		appendDefinitionRow(FIELD_LOCATION, fileName, buf);
		
		// append the class name if it exists
		IType declaringType = method.getDeclaringType();
		if (declaringType != null) {
			appendDefinitionRow(FIELD_CLASS, declaringType.getElementName(), buf);
		}
		
		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		MethodDeclaration methodDeclaration = PHPModelUtils.getNodeByMethod(module, method);
		if (!(methodDeclaration instanceof IPHPDocAwareDeclaration)) {
			return;
		}
		
		PHPDocBlock doc = ((IPHPDocAwareDeclaration)methodDeclaration).getPHPDoc();
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
		
		ISourceModule sourceModule = type.getSourceModule();
		String fileName = sourceModule.getElementName();
		
		// append the file name
		appendDefinitionRow(FIELD_LOCATION, fileName, buf);

		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule, null);
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
		PHPDocBlock doc = ((IPHPDocAwareDeclaration)typeDeclaration).getPHPDoc();
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
		String fileName = sourceModule.getElementName();

		// append the file name
		appendDefinitionRow(FIELD_LOCATION, fileName, buf);
		
		// append the class name if it exists
		IType declaringType = field.getDeclaringType();
		if (declaringType != null) {
			appendDefinitionRow(FIELD_CLASS, declaringType.getElementName(), buf);
		}
		
		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		ASTNode node = PHPModelUtils.getNodeByField(module, field);
		
		if (!(node instanceof IPHPDocAwareDeclaration)) {
			return;
		}
		PHPDocBlock doc = ((IPHPDocAwareDeclaration)node).getPHPDoc();
		if (doc == null) {
			return;
		}
		
		// append description if it exists
		appendShortDescription(doc, buf);
	}
	
	private static String nl2br(String str) {
		return str.replaceAll("\\n", "<br>"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	private static void appendDefinitionRow(String field, String data, StringBuilder buf) {
		buf.append(DT_START).append(field).append(DT_END);
		buf.append(DD_START).append(data).append(DD_END);
	}
	
	private static void appendDefinitionRows(String field, String[] data, StringBuilder buf) {
		buf.append(DT_START).append(field).append(DT_END);
		for (String row : data) {
			buf.append(DD_START).append(row).append(DD_END);
		}
	}
	
	private static void appendShortDescription(PHPDocBlock doc, StringBuilder buf) {
		String desc = doc.getShortDescription();
		if (desc != null && desc.length() > 0) {
			appendDefinitionRow(FIELD_DESC, nl2br(desc), buf);
		}
	}
	
	private static void appendTagInfo(PHPDocBlock doc, int tagKind, String field, StringBuilder buf) {
		PHPDocTag[] tags = getTags(doc, tagKind);
		if (tags.length > 0) {
			buf.append(DT_START).append(field).append(DT_END);
			for (PHPDocTag tag : tags) {
				buf.append(DD_START).append(tag.getValue()).append(DD_END);
			}
		}
	}
	
	private static PHPDocTag[] getTags(PHPDocBlock doc, int kind) {
		List<PHPDocTag> tags = new LinkedList<PHPDocTag>();
		for (PHPDocTag tag : doc.getTags()) {
			if (tag.getTagKind() == kind) {
				tags.add(tag);
			}
		}
		return tags.toArray(new PHPDocTag[tags.size()]);
	}
}
