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
package org.eclipse.php.internal.core.index;

import java.util.regex.Pattern;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.IElementResolver;
import org.eclipse.dltk.internal.core.*;
import org.eclipse.php.core.compiler.IPHPModifiers;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.model.IncludeField;

public class PhpElementResolver implements IElementResolver {

	private static final Pattern SEPARATOR_PATTERN = Pattern.compile(","); //$NON-NLS-1$
	private static final String[] EMPTY = new String[0];

	public IModelElement resolve(int elementType, int flags, int offset,
			int length, int nameOffset, int nameLength, String elementName,
			String metadata, String qualifier, String parent,
			ISourceModule sourceModule) {

		ModelElement parentElement = (ModelElement) sourceModule;
		if (qualifier != null) {
			// namespace:
			parentElement = new IndexType(parentElement, qualifier,
					Modifiers.AccNameSpace, 0, 0, 0, 0, null);
		}
		if (parent != null) {
			parentElement = new SourceType(parentElement, parent);
		}

		switch (elementType) {
		case IModelElement.TYPE:
			String[] superClassNames = null;
			if (metadata != null) {
				superClassNames = SEPARATOR_PATTERN.split(metadata);
			}
			return new IndexType(parentElement, elementName, flags, offset,
					length, nameOffset, nameLength, superClassNames);

		case IModelElement.METHOD:
			String[] parameters;
			if (metadata != null) {
				parameters = SEPARATOR_PATTERN.split(metadata);
			} else {
				parameters = EMPTY;
			}
			return new IndexMethod(parentElement, elementName, flags, offset,
					length, nameOffset, nameLength, parameters);

		case IModelElement.FIELD:
			return new IndexField(parentElement, elementName, flags, offset,
					length, nameOffset, nameLength);

		case IModelElement.IMPORT_DECLARATION:
			// XXX: replace with import declaration element
			return new IncludeField(parentElement, metadata);

		default:
			Logger.log(Logger.WARNING, PhpElementResolver.class.getName()
					+ ": Unsupported element type (" + elementType + ")");
		}
		return null;
	}

	private static class IndexField extends SourceField {

		private int flags;
		private ISourceRange sourceRange;
		private ISourceRange nameRange;

		public IndexField(ModelElement parent, String name, int flags,
				int offset, int length, int nameOffset, int nameLength) {
			super(parent, name);
			this.flags = flags;
			this.sourceRange = new SourceRange(offset, length);
			this.nameRange = new SourceRange(nameOffset, nameLength);
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
	}

	private static class IndexMethod extends SourceMethod {

		private int flags;
		private ISourceRange sourceRange;
		private ISourceRange nameRange;
		private IParameter[] parameters;

		public IndexMethod(ModelElement parent, String name, int flags,
				int offset, int length, int nameOffset, int nameLength,
				String[] parameterNames) {

			super(parent, name);
			this.flags = flags;
			this.sourceRange = new SourceRange(offset, length);
			this.nameRange = new SourceRange(nameOffset, nameLength);
			// MethodParameterInfo
			this.parameters = new IParameter[0];
			if (parameterNames != null) {
				this.parameters = new IParameter[parameterNames.length];
				for (int i = 0; i < parameterNames.length; i++) {
					String[] values = parameterNames[i].split("\\"
							+ PhpIndexingVisitor.PARAMETER_SEPERATOR);
					if (values.length == 1) {
						this.parameters[i] = new MethodParameterInfo(values[0]);
					} else {
						String type = values[0];
						if (PhpIndexingVisitor.NULL_VALUE.equals(type)) {
							type = null;
						}
						String param = values[1];

						String defaultValue = values[2];
						if (PhpIndexingVisitor.NULL_VALUE.equals(defaultValue)) {
							defaultValue = null;
						}
						this.parameters[i] = new MethodParameterInfo(param,
								type, defaultValue);
					}
				}
			}
		}

		public int getFlags() throws ModelException {
			return flags;
		}

		public ISourceRange getNameRange() throws ModelException {
			return nameRange;
		}

		public ISourceRange getSourceRange() throws ModelException {
			return super.getNameRange();
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
	}

	private static class IndexType extends SourceType {

		private int flags;
		private ISourceRange sourceRange;
		private ISourceRange nameRange;
		private String[] superClassNames;

		public IndexType(ModelElement parent, String name, int flags,
				int offset, int length, int nameOffset, int nameLength,
				String[] superClassNames) {

			super(parent, name);
			this.flags = flags;
			this.sourceRange = new SourceRange(offset, length);
			this.nameRange = new SourceRange(nameOffset, nameLength);
			this.superClassNames = superClassNames;
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
	}
}
