/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.index;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.dltk.core.index2.IElementResolver;
import org.eclipse.dltk.internal.core.*;
import org.eclipse.php.core.compiler.IPHPModifiers;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.model.IncludeField;

public class PhpElementResolver implements IElementResolver {

	private static final char SPLIT_CHAR = ';';
	private static final char SEPARATOR_CHAR = ',';
	private static final char RETURN_TYPE_CHAR = ':';
	private static final String[] EMPTY = new String[0];

	public IModelElement resolve(int elementType, int flags, int offset, int length, int nameOffset, int nameLength,
			String elementName, String metadata, String doc, String qualifier, String parent,
			ISourceModule sourceModule) {

		int occurrenceCount = 1;
		String metadataToDecode = null;

		if (metadata != null) {
			String[] split = StringUtils.split(metadata, SPLIT_CHAR);
			if (split.length >= 1) {
				try {
					occurrenceCount = Integer.parseInt(split[0]);
				} catch (NumberFormatException e) {
					// should never happen
					Logger.logException(e);
				}
			}
			if (split.length >= 2) {
				metadataToDecode = split[1];
			}
		}

		ModelElement parentElement = (ModelElement) sourceModule;
		if (PHPCoreConstants.FILE_PARENT.equals(parent)) {
			parent = null;
		}
		if (PHPCoreConstants.GLOBAL_NAMESPACE.equals(qualifier)) {
			qualifier = null;
		}
		if (qualifier != null) {
			// namespace:
			parentElement = new IndexType(parentElement, qualifier, Modifiers.AccNameSpace, 0, 0, 0, 0, null, doc,
					occurrenceCount);
		}
		if (parent != null) {
			parentElement = new SourceType(parentElement, parent);
		}

		String[] superClassNames = null;

		switch (elementType) {
		case IModelElement.PACKAGE_DECLARATION:
			if (metadataToDecode != null) {
				superClassNames = StringUtils.split(metadataToDecode, SEPARATOR_CHAR);
			}
			// a namespace cannot have a nested namespace otherwise
			// occurrenceCount will be applied to both!
			assert qualifier == null;
			return new IndexType(parentElement, elementName, flags, offset, length, nameOffset, nameLength,
					superClassNames, doc, occurrenceCount);

		case IModelElement.TYPE:
			if (metadataToDecode != null) {
				superClassNames = StringUtils.split(metadataToDecode, SEPARATOR_CHAR);
			}
			return new IndexType(parentElement, elementName, flags, offset, length, nameOffset, nameLength,
					superClassNames, doc, 1);

		case IModelElement.METHOD:
			String[] parameters = EMPTY;
			String returnType = null;
			if (metadataToDecode != null && metadataToDecode.length() > 1) {
				if (metadataToDecode.charAt(0) == RETURN_TYPE_CHAR) {
					returnType = null;
					metadataToDecode = metadataToDecode.substring(1);
				} else if (metadataToDecode.charAt(metadataToDecode.length() - 1) == RETURN_TYPE_CHAR) {
					returnType = metadataToDecode.substring(0, metadataToDecode.length() - 1);
					metadataToDecode = null;
				} else {
					String[] decoded = StringUtils.split(metadataToDecode, RETURN_TYPE_CHAR);
					if (decoded.length == 2) {
						metadataToDecode = decoded[1];
						returnType = decoded[0];
					}
				}
				if (metadataToDecode != null) {
					parameters = StringUtils.split(metadataToDecode, SEPARATOR_CHAR);
				}
			}
			return new IndexMethod(parentElement, elementName, returnType, flags, offset, length, nameOffset,
					nameLength, parameters, doc, 1);

		case IModelElement.FIELD:
			return new IndexField(parentElement, elementName, flags, offset, length, nameOffset, nameLength, doc, 1);

		case IModelElement.IMPORT_DECLARATION:
			// XXX: replace with import declaration element
			return new IncludeField(parentElement, metadataToDecode);

		default:
			Logger.log(Logger.WARNING,
					PhpElementResolver.class.getName() + ": Unsupported element type (" + elementType + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return null;
	}

	/**
	 * See
	 * {@link PhpIndexingVisitor#encodeDocInfo(org.eclipse.dltk.ast.declarations.Declaration)}
	 * for the encoding routine
	 * 
	 * @param doc
	 *            String representation of encoded PHPDoc info
	 * @return map of encoded information, or <code>null</code> in case PHPDoc
	 *         info is <code>null</code>
	 */
	protected static Map<String, String> decodeDocInfo(String doc) {
		if (doc == null) {
			return null;
		}
		Map<String, String> info = new HashMap<String, String>();
		StringTokenizer tok = new StringTokenizer(doc, ";"); //$NON-NLS-1$
		while (tok.hasMoreTokens()) {
			String key = tok.nextToken();
			String value = null;
			int i = key.indexOf(':');
			if (i != -1) {
				value = key.substring(i + 1);
				key = key.substring(0, i);
			}
			info.put(key, value);
		}
		return info;
	}

	private static class IndexField extends SourceField implements IPHPDocAwareElement {

		private int flags;
		private ISourceRange sourceRange;
		private ISourceRange nameRange;
		private String doc;

		public IndexField(ModelElement parent, String name, int flags, int offset, int length, int nameOffset,
				int nameLength, String doc, int occurrenceCount) {
			super(parent, name);
			this.flags = flags;
			this.sourceRange = new SourceRange(offset, length);
			this.nameRange = new SourceRange(nameOffset, nameLength);
			this.doc = doc;
			this.occurrenceCount = occurrenceCount;
		}

		public int getFlags() throws ModelException {
			return flags;
		}

		public ISourceRange getNameRange() throws ModelException {
			return nameRange;
		}

		public ISourceRange getSourceRange() throws ModelException {
			return sourceRange;
		}

		public boolean isDeprecated() {
			return PHPFlags.isDeprecated(flags);
		}

		public String[] getReturnTypes() {
			return null;
		}

		@Override
		public INamespace getNamespace() throws ModelException {
			return null;
		}

		@Override
		public String getType() throws ModelException {
			Map<String, String> info = decodeDocInfo(doc);
			if (info != null) {
				String types = info.get("v"); //$NON-NLS-1$
				if (types != null) {
					types = types.replace(Constants.DOT, Constants.TYPE_SEPERATOR_CHAR);
					return types;
				}
			}
			return null;
		}
	}

	private static class IndexMethod extends SourceMethod implements IPHPDocAwareElement {

		private int flags;
		private ISourceRange sourceRange;
		private ISourceRange nameRange;
		private String returnType;
		private IParameter[] parameters;
		private String doc;

		public IndexMethod(ModelElement parent, String name, String returnType, int flags, int offset, int length,
				int nameOffset, int nameLength, String[] parameterNames, String doc, int occurrenceCount) {

			super(parent, name);
			this.returnType = returnType;
			this.flags = flags;
			this.sourceRange = new SourceRange(offset, length);
			this.nameRange = new SourceRange(nameOffset, nameLength);
			// MethodParameterInfo
			this.parameters = new IParameter[0];
			if (parameterNames != null) {
				this.parameters = new IParameter[parameterNames.length];
				for (int i = 0; i < parameterNames.length; i++) {
					String[] values = parameterNames[i].split("\\" //$NON-NLS-1$
							+ PhpIndexingVisitor.PARAMETER_SEPERATOR);
					if (values.length == 1) {
						this.parameters[i] = new MethodParameterInfo(values[0]);
					} else {
						String type = values[0];
						if (PhpIndexingVisitor.NULL_VALUE.equals(type)) {
							type = null;
						}
						if (type != null) {
							type = type.replace(Constants.DOT, Constants.TYPE_SEPERATOR_CHAR);
						}
						String param = values[1];

						String defaultValue = values[2];
						if (PhpIndexingVisitor.NULL_VALUE.equals(defaultValue)) {
							defaultValue = null;
						}
						if (defaultValue != null) {
							defaultValue = defaultValue.replace("&p", PhpIndexingVisitor.PARAMETER_SEPERATOR) //$NON-NLS-1$
									.replace("&a", "&"); //$NON-NLS-1$ //$NON-NLS-2$
						}
						int modifiers = 0;
						if (values.length == 4) {
							try {
								modifiers = Integer.parseInt(values[3]);
							} catch (NumberFormatException e) {
								// should never happen
								Logger.logException(e);
							}
						}
						this.parameters[i] = new MethodParameterInfo(param, type, defaultValue, modifiers);
					}
				}
			}
			this.doc = doc;
			this.occurrenceCount = occurrenceCount;
		}

		public int getFlags() throws ModelException {
			return flags;
		}

		public ISourceRange getNameRange() throws ModelException {
			return super.getNameRange();
		}

		public ISourceRange getSourceRange() throws ModelException {
			return sourceRange;
		}

		public IParameter[] getParameters() throws ModelException {
			return parameters;
		}

		@Override
		public String[] getParameterNames() throws ModelException {
			return SourceMethodUtils.getParameterNames(parameters);
		}

		public boolean isConstructor() throws ModelException {
			return (flags & IPHPModifiers.Constructor) != 0;
		}

		public boolean isDeprecated() {
			return PHPFlags.isDeprecated(flags);
		}

		public String[] getReturnTypes() {
			if (returnType != null) {
				return new String[] { returnType };
			}
			Map<String, String> info = decodeDocInfo(doc);
			if (info != null) {
				String types = info.get("r"); //$NON-NLS-1$
				if (types != null) {
					String[] returnTypes = types.split(","); //$NON-NLS-1$
					for (int i = 0; i < returnTypes.length; i++) {
						returnTypes[i] = returnTypes[i].replaceAll("~", ","); //$NON-NLS-1$ //$NON-NLS-2$
					}
					return returnTypes;
				}
			}
			return null;
		}

		@Override
		public INamespace getNamespace() throws ModelException {
			return null;
		}

	}

	private static class IndexType extends SourceType implements IPHPDocAwareElement {

		private int flags;
		private ISourceRange sourceRange;
		private ISourceRange nameRange;
		private String[] superClassNames;
		private String doc;

		public IndexType(ModelElement parent, String name, int flags, int offset, int length, int nameOffset,
				int nameLength, String[] superClassNames, String doc, int occurrenceCount) {
			super(parent, name);
			this.flags = flags;
			this.sourceRange = new SourceRange(offset, length);
			this.nameRange = new SourceRange(nameOffset, nameLength);
			this.superClassNames = superClassNames;
			this.doc = doc;
			this.occurrenceCount = occurrenceCount;
		}

		public int getFlags() throws ModelException {
			return flags;
		}

		public ISourceRange getNameRange() throws ModelException {
			return super.getNameRange();
		}

		public ISourceRange getSourceRange() throws ModelException {
			return sourceRange;
		}

		public String[] getSuperClasses() throws ModelException {
			return superClassNames;
		}

		public boolean isDeprecated() {
			return PHPFlags.isDeprecated(flags);
		}

		public String[] getReturnTypes() {
			return null;
		}

		@Override
		public INamespace getNamespace() throws ModelException {
			return null;
		}

	}
}
