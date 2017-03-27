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
package org.eclipse.php.internal.debug.core.zend.debugger;

import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.*;
import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.KIND_ARRAY_MEMBER;
import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.KIND_OBJECT_MEMBER;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

public class ExpressionsValueDeserializer {

	private String fEncoding;

	public ExpressionsValueDeserializer(String encoding) {
		fEncoding = encoding;
	}

	public ExpressionValue deserializer(Expression expression, byte[] value) {
		if (value == null) {
			// the expression is Illeagal.
			value = new byte[] { 'N' };
		}
		return build(expression, new VariableReader(value));
	}

	private String getText(byte[] buf) {
		try {
			return new String(buf, fEncoding);
		} catch (UnsupportedEncodingException e) {
		}
		return new String(buf);
	}

	private ExpressionValue build(Expression expression, VariableReader reader) {
		char type = reader.readType();

		switch (type) {
		case 'i':
			return buildIntType(reader);
		case 'd':
			return buildFloatType(reader);
		case 's':
			return buildSringType(reader);
		case 'b':
			return buildBooleanType(reader);
		case 'r':
			return buildResourceType(reader);
		case 'a':
			return buildArrayType(expression, reader);
		case 'O':
			return buildObjectType(expression, reader);
		}
		return ExpressionValue.NULL_VALUE;
	}

	private ExpressionValue buildIntType(VariableReader reader) {
		String value = reader.readToken();
		String valueAsString = value; // $NON-NLS-1$
		return new ExpressionValue(PHP_INT, value, valueAsString, null);
	}

	private ExpressionValue buildFloatType(VariableReader reader) {
		String value = reader.readToken();
		String valueAsString = value; // $NON-NLS-1$
		return new ExpressionValue(PHP_FLOAT, value, valueAsString, null);
	}

	private ExpressionValue buildSringType(VariableReader reader) {
		String value = reader.readString();
		String valueAsString = "\"" + value + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		return new ExpressionValue(PHP_STRING, value, valueAsString, null);
	}

	private ExpressionValue buildBooleanType(VariableReader reader) {
		String value = reader.readToken();
		String valueAsString = ((value.equals("0")) ? "false" : "true"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return new ExpressionValue(PHP_BOOL, value, valueAsString, null);
	}

	private ExpressionValue buildResourceType(VariableReader reader) {
		int resourceNumber = reader.readInt();
		reader.readInt();

		String value = reader.readToken();
		String valueAsString = "resource (" + resourceNumber + ") of type (" //$NON-NLS-1$ //$NON-NLS-2$
				+ value + ')';
		return new ExpressionValue(PHP_RESOURCE, value, valueAsString, null);
	}

	private ExpressionValue buildArrayType(Expression expression, VariableReader reader) {
		int objectLength = reader.readInt();
		int originalLength = objectLength;
		if (reader.isLastEnd()) {
			objectLength = 0;
		}
		Expression[] variableNodes = new Expression[objectLength];
		for (int i = 0; i < objectLength; i++) {
			char type = reader.readType();
			// System.out.println("type " + type);
			String name;
			if (type == 'i') {
				name = Integer.toString(reader.readInt());
			} else if (type == 's') {
				name = reader.readString();
			} else {
				// fall back when type is invalid
				return ExpressionValue.NULL_VALUE;
			}
			if (expression == null) {
				variableNodes[i] = createDefaultVariable(name);
			} else {
				variableNodes[i] = expression.createChildExpression(name, '[' + name + ']', KIND_ARRAY_MEMBER);
			}
			variableNodes[i].setValue(build(expression, reader));
		}
		return new ExpressionValue(PHP_ARRAY, "Array", //$NON-NLS-1$
				"Array [" + originalLength + ']', variableNodes, originalLength); //$NON-NLS-1$
	}

	private ExpressionValue buildObjectType(Expression expression, VariableReader reader) {
		String className = reader.readString();
		int objectLength = reader.readInt();
		int originalLength = objectLength;
		if (reader.isLastEnd()) {
			objectLength = 0;
		}
		Expression[] expressionNodes = new Expression[objectLength];
		for (int i = 0; i < objectLength; i++) {
			char type = reader.readType();
			// System.out.println("type " + type);
			String name;
			if (type == 'i') {
				name = Integer.toString(reader.readInt());
			} else if (type == 's') {
				name = reader.readString();
			} else {
				// fall back when type is invalid
				return ExpressionValue.NULL_VALUE;
			}
			if (expression == null) {
				expressionNodes[i] = createDefaultVariable(name);
			} else {
				expressionNodes[i] = expression.createChildExpression(name, "->" + name, KIND_OBJECT_MEMBER); //$NON-NLS-1$
			}
			expressionNodes[i].setValue(build(expression, reader));
		}
		String valueAsString = "Object of: " + PHPModelUtils.extractElementName(className); //$NON-NLS-1$
		return new ExpressionValue(PHP_OBJECT, className, valueAsString, expressionNodes, originalLength);
	}

	private Expression createDefaultVariable(String name) {
		return new DefaultExpression('$' + name);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	// variable reader.
	// ////////////////////////////////////////////////////////////////////////////////////////////////
	private class VariableReader extends ByteArrayInputStream {

		private VariableReader(byte[] result) {
			super(result);
		}

		private char readType() {
			char curr;
			do {
				int temp = super.read();
				if (temp == -1) {
					return ' ';
				}
				curr = (char) temp;
			} while (curr == ';' || curr == ':' || curr == '{' || curr == '}');
			return curr;
		}

		private String readToken() {
			StringBuffer buffer = new StringBuffer(6);
			char curr;
			do {
				curr = (char) super.read();
			} while (curr == ';' || curr == ':');

			while (curr != ';' && curr != ':') {
				buffer.append(curr);
				curr = (char) super.read();
			}
			return buffer.toString();
		}

		private String readString() {
			int length = readInt();
			while ((char) super.read() != '"')
				;

			byte[] bytes = new byte[length];

			read(bytes, 0, length);
			super.read(); // read '"'

			return getText(bytes);
		}

		public int readInt() {
			int result = 0;
			char curr;
			boolean isMinus = false;
			do {
				curr = (char) super.read();
				if (curr == '-') {
					isMinus = true;
				}
			} while (!Character.isDigit(curr));
			do {
				result *= 10;
				result += Character.getNumericValue(curr);
				this.mark(1);
			} while (Character.isDigit(curr = (char) super.read()));
			if (isMinus) {
				result *= -1;
			}
			return result;
		}

		private boolean isLastEnd() {
			this.reset();
			char curr = (char) super.read();
			return curr == ';';
		}
	}

}