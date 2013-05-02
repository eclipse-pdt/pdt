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
package org.eclipse.php.internal.core.typeinference;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.types.IEvaluatedType;

/**
 * This is a factory for PHP simple types
 * 
 * @author michael
 */
public class PHPSimpleTypes {

	private static final Map<String, IEvaluatedType> SIMPLE_TYPES;
	public static final IEvaluatedType NUMBER = new SimpleType(
			SimpleType.TYPE_NUMBER);
	public static final IEvaluatedType BOOLEAN = new SimpleType(
			SimpleType.TYPE_BOOLEAN);
	public static final IEvaluatedType STRING = new SimpleType(
			SimpleType.TYPE_STRING);
	public static final IEvaluatedType OBJECT = new PHPClassType("object"); //$NON-NLS-1$
	public static final IEvaluatedType RESOURCE = new PHPClassType("resource"); //$NON-NLS-1$
	public static final IEvaluatedType ARRAY = new MultiTypeType();
	public static final IEvaluatedType VOID = new PHPClassType("void"); //$NON-NLS-1$
	public static final IEvaluatedType NULL = new SimpleType(
			SimpleType.TYPE_NULL);

	static {
		SIMPLE_TYPES = new HashMap<String, IEvaluatedType>();
		SIMPLE_TYPES.put("array", new MultiTypeType()); //$NON-NLS-1$
		SIMPLE_TYPES.put("bool", BOOLEAN); //$NON-NLS-1$
		SIMPLE_TYPES.put("boolean", BOOLEAN); //$NON-NLS-1$
		SIMPLE_TYPES.put("int", NUMBER); //$NON-NLS-1$
		SIMPLE_TYPES.put("integer", NUMBER); //$NON-NLS-1$
		SIMPLE_TYPES.put("float", NUMBER); //$NON-NLS-1$
		SIMPLE_TYPES.put("double", NUMBER); //$NON-NLS-1$
		SIMPLE_TYPES.put("number", NUMBER); //$NON-NLS-1$
		SIMPLE_TYPES.put("string", STRING); //$NON-NLS-1$
		SIMPLE_TYPES.put("resource", RESOURCE); //$NON-NLS-1$
		SIMPLE_TYPES.put("string", STRING); //$NON-NLS-1$
		SIMPLE_TYPES.put("object", OBJECT); //$NON-NLS-1$
		SIMPLE_TYPES.put("void", VOID); //$NON-NLS-1$
		SIMPLE_TYPES.put("null", NULL); //$NON-NLS-1$
	}

	/**
	 * Returns {@link IEvaluatedType} for the PHP simple type name
	 * 
	 * @param type
	 * @return
	 */
	public static IEvaluatedType fromString(String type) {
		return SIMPLE_TYPES.get(type.toLowerCase());
	}
}
