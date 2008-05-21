package org.eclipse.php.internal.ui.documentation;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.ui.documentation.IScriptDocumentationProvider;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.wst.sse.core.internal.Logger;

public class PHPDocumentationProvider implements IScriptDocumentationProvider {
	
	private static final String FIELD_LOCATION = "Location";
	private static final String FIELD_AUTHOR = "Author";
	private static final String FIELD_CLASS = "Class";
	private static final String FIELD_DESC = "Description";
	private static final String FIELD_PARAMETERS = "Parameters";
	private static final String FIELD_RETURNS = "Returns";
	private static final String FIELD_THROWS = "Throws";
	private static final String FIELD_DEPRECATED = "Deprecated";
	private static final String FIELD_SEEALSO = "See Also";
	private static final String EMPTY = "";
	private static Pattern DOLLAR_PATTERN = Pattern.compile("\\$"); //$NON-NLS-1$
	private static Pattern UNKNOWN_TYPE_PATTERN = Pattern.compile("unknown_type\\ "); //$NON-NLS-1$

	public Reader getInfo(IMember element, boolean lookIntoParents, boolean lookIntoExternal) {
		StringBuilder buf = new StringBuilder("<dl>");
		try {
			if (element instanceof IMethod) {
				IMethod method = (IMethod) element;
				if (method.exists()) {
					appendMethodInfo(method, buf);
				}
			}
			
		} catch (Exception e) {
			Logger.logException(e);
		}
		buf.append("</dl>");
		return new StringReader(buf.toString());
	}

	public Reader getInfo(String content) {
		return null;
	}

	protected void appendMethodInfo(IMethod method, StringBuilder buf) throws ModelException {
		
		ISourceModule sourceModule = method.getSourceModule();
		String fileName = sourceModule.getElementName();

		// append the file name
		appendTableRow(FIELD_LOCATION, fileName, buf);
		
		// append the class name if it exists
		IType declaringType = method.getDeclaringType();
		if (declaringType != null) {
			appendTableRow(FIELD_CLASS, declaringType.getElementName(), buf);
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
		String desc = doc.getShortDescription();
		if (desc != null && desc.length() > 0) {
			appendTableRow(FIELD_DESC, nl2br(desc), buf);
		}
		
		// append tags info
		appendParamInfo(doc, buf);
		
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
	
	private static String nl2br(String str) {
		return str.replaceAll("\\n", "<br>"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	private static void appendTableRow(String field, String data, StringBuilder buf) {
		buf.append("<dt>").append(field).append("</dt>");
		buf.append("<dd>").append(data).append("</dd>");
	}
	
	private static void appendParamInfo(PHPDocBlock doc, StringBuilder buf) {
		PHPDocTag[] paramTags = getTags(doc, PHPDocTag.PARAM);
		if (paramTags.length > 0) {
			buf.append("<dt>").append(FIELD_PARAMETERS).append("</dt>");
			for (PHPDocTag tag : paramTags) {
				String arg = tag.getValue();
				arg = DOLLAR_PATTERN.matcher(arg).replaceAll(EMPTY);
				arg = UNKNOWN_TYPE_PATTERN.matcher(arg).replaceAll(EMPTY);
				if (arg.split(" ").length > 1) {
					buf.append("<dd>").append(arg).append("</dd>");
				}
			}
		}
	}
	
	private static void appendTagInfo(PHPDocBlock doc, int tagKind, String field, StringBuilder buf) {
		PHPDocTag[] returnTags = getTags(doc, tagKind);
		if (returnTags.length > 0) {
			buf.append("<dt>").append(field).append("</dt>");
			for (PHPDocTag tag : returnTags) {
				buf.append("<dd>").append(tag.getValue()).append("</dd>");
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
