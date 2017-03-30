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

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.php.core.compiler.IPHPModifiers;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class PHPElementLabels extends ScriptElementLabels {
	private final static String MIXED_RETURN_TYPE = "mixed"; //$NON-NLS-1$
	private final static String VOID_RETURN_TYPE = "void"; //$NON-NLS-1$
	private final static String QUESTION_MARK = "?"; //$NON-NLS-1$

	/**
	 * User-readable string for reference ("&").
	 */
	public static final String REFERENCE_STRING = "&"; //$NON-NLS-1$

	@Override
	protected void getTypeLabel(IType type, long flags, StringBuffer buf) {
		if (getFlag(flags, T_FULLY_QUALIFIED | T_CONTAINER_QUALIFIED)) {
			IModelElement elem = type.getParent();
			IType declaringType = (elem instanceof IType) ? (IType) elem : null;
			if (declaringType != null) {
				getTypeLabel(declaringType, T_CONTAINER_QUALIFIED | (flags & QUALIFIER_FLAGS), buf);
				buf.append(getTypeDelimiter(elem));
			}
			int parentType = type.getParent().getElementType();
			if (parentType == IModelElement.METHOD || parentType == IModelElement.FIELD) { // anonymous
				// or
				// local
				getElementLabel(type.getParent(),
						(parentType == IModelElement.METHOD ? M_FULLY_QUALIFIED : F_FULLY_QUALIFIED)
								| (flags & QUALIFIER_FLAGS),
						buf);
				buf.append(getTypeDelimiter(elem));
			}
		}

		String typeName = type.getElementName();
		if (typeName.length() == 0) { // anonymous
			try {
				if (type.getParent() instanceof IField) {
					typeName = '{' + ELLIPSIS_STRING + '}';
				} else {
					String[] superNames = type.getSuperClasses();
					if (superNames != null) {
						int count = 0;
						typeName += DECL_STRING;
						for (int i = 0; i < superNames.length; ++i) {

							if (count > 0) {
								typeName += COMMA_STRING + " "; //$NON-NLS-1$
							}
							typeName += superNames[i];
							count++;
						}
					}
				}
			} catch (ModelException e) {
				// ignore
				typeName = ""; //$NON-NLS-1$
			}
		}

		buf.append(typeName);

		// post qualification
		if (getFlag(flags, T_POST_QUALIFIED)) {
			IModelElement elem = type.getParent();
			IType declaringType = (elem instanceof IType) ? (IType) elem : null;
			if (declaringType != null) {
				buf.append(CONCAT_STRING);
				getTypeLabel(declaringType, T_FULLY_QUALIFIED | (flags & QUALIFIER_FLAGS), buf);
				int parentType = type.getParent().getElementType();
				if (parentType == IModelElement.METHOD || parentType == IModelElement.FIELD) { // anonymous
					// or
					// local
					buf.append(getTypeDelimiter(elem));
					getElementLabel(type.getParent(), 0, buf);
				}
			}
			int parentType = type.getParent().getElementType();
			if (parentType == IModelElement.METHOD || parentType == IModelElement.FIELD) { // anonymous
				// or
				// local
				buf.append(CONCAT_STRING);
				getElementLabel(type.getParent(),
						(parentType == IModelElement.METHOD ? M_FULLY_QUALIFIED : F_FULLY_QUALIFIED)
								| (flags & QUALIFIER_FLAGS),
						buf);
			}
		}
	}

	@Override
	protected void getMethodLabel(IMethod method, long flags, StringBuffer buf) {

		try {
			// qualification
			if (getFlag(flags, M_FULLY_QUALIFIED)) {
				IType type = method.getDeclaringType();
				if (type != null) {
					getTypeLabel(type, T_FULLY_QUALIFIED | (flags & QUALIFIER_FLAGS), buf);
					buf.append("::"); //$NON-NLS-1$
				}
			}

			buf.append(method.getElementName());

			// parameters
			buf.append('(');
			getMethodParameters(method, flags, buf);
			buf.append(')');

			if (getFlag(flags, ScriptElementLabels.M_APP_RETURNTYPE) && method.exists() && !method.isConstructor()) {
				String type = method.getType();
				if (type == null) {
					if ((method.getFlags() & IPHPModifiers.AccReturn) != 0) {
						type = MIXED_RETURN_TYPE;
					} else {
						type = VOID_RETURN_TYPE;
					}
				}
				buf.append(ScriptElementLabels.DECL_STRING);
				if (PHPFlags.isNullable(method.getFlags())) {
					buf.append(QUESTION_MARK);
				}
				buf.append(type);
			}

			// post qualification
			if (getFlag(flags, M_POST_QUALIFIED)) {
				IType declaringType = method.getDeclaringType();
				if (declaringType != null) {
					buf.append(CONCAT_STRING);
					getTypeLabel(declaringType, T_FULLY_QUALIFIED | (flags & QUALIFIER_FLAGS), buf);
				}
			}
		} catch (ModelException e) {
			PHPUiPlugin.log(e);
		}
	}

	@Override
	protected void getMethodParameters(IMethod method, long flags, StringBuffer buf) throws ModelException {
		if (getFlag(flags, M_PARAMETER_TYPES | M_PARAMETER_NAMES)) {
			if (method.exists()) {
				final boolean bNames = getFlag(flags, M_PARAMETER_NAMES);
				final boolean bTypes = getFlag(flags, M_PARAMETER_TYPES);
				final boolean bInitializers = getFlag(flags, M_PARAMETER_INITIALIZERS);
				final IParameter[] params = method.getParameters();
				final boolean isVariadic = PHPFlags.isVariadic(method.getFlags());
				for (int i = 0, nParams = params.length; i < nParams; i++) {
					if (i > 0) {
						buf.append(COMMA_STRING);
					}
					boolean isLast = i + 1 == nParams;
					if (bTypes) {
						if (params[i].getType() != null) {
							if (PHPFlags.isNullable(params[i].getFlags())) {
								buf.append(QUESTION_MARK);
							}
							buf.append(params[i].getType());
							if (bNames) {
								buf.append(' ');
							} else {
								if (PHPFlags.isReference(params[i].getFlags())) {
									buf.append(REFERENCE_STRING);
								}
								if (isLast && isVariadic) {
									buf.append(ELLIPSIS_STRING);
								}
							}
						} else if (!bNames) {
							if (PHPFlags.isReference(params[i].getFlags())) {
								buf.append(REFERENCE_STRING);
							}
							if (isLast && isVariadic) {
								buf.append(ELLIPSIS_STRING);
							}
							buf.append(params[i].getName());
						}
					}
					if (bNames) {
						if (PHPFlags.isReference(params[i].getFlags())) {
							buf.append(REFERENCE_STRING);
						}
						if (isLast && isVariadic) {
							buf.append(ELLIPSIS_STRING);
						}
						buf.append(params[i].getName());
					}
					if (bInitializers && params[i].getDefaultValue() != null) {
						buf.append("="); //$NON-NLS-1$
						buf.append(params[i].getDefaultValue());
					}
				}
			}
		} else if (method.getParameters().length > 0) {
			buf.append(ELLIPSIS_STRING);
		}
	}

	@Override
	protected void getImportDeclarationLabel(IModelElement element, long flags, StringBuffer buf) {
		super.getImportDeclarationLabel(element, flags, buf);
		IImportDeclaration declaration = (IImportDeclaration) element;
		if (declaration.getAlias() != null) {
			buf.append(" as "); //$NON-NLS-1$
			buf.append(declaration.getAlias());
		}
	}
}
