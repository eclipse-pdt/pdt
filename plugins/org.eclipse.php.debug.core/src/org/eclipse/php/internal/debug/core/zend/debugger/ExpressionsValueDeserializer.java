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

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

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

	// private byte[] getBytes(String text) {
	// try {
	// return text.getBytes(fEncoding);
	// } catch (UnsupportedEncodingException e) {
	// }
	// return text.getBytes();
	// }

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
			return buildDoubleType(reader);
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
		String valueAsString = "(int) " + value; //$NON-NLS-1$
		return new ExpressionValue(ExpressionValue.INT_TYPE, value,
				valueAsString, null);
	}

	private ExpressionValue buildDoubleType(VariableReader reader) {
		String value = reader.readToken();
		String valueAsString = "(double) " + value; //$NON-NLS-1$
		return new ExpressionValue(ExpressionValue.DOUBLE_TYPE, value,
				valueAsString, null);
	}

	private ExpressionValue buildSringType(VariableReader reader) {
		String value = reader.readString();
		String valueAsString = "(string:" + value.length() + ") " + value; //$NON-NLS-1$ //$NON-NLS-2$
		return new ExpressionValue(ExpressionValue.STRING_TYPE, value,
				valueAsString, null);
	}

	private ExpressionValue buildBooleanType(VariableReader reader) {
		String value = reader.readToken();
		String valueAsString = "(boolean) " //$NON-NLS-1$
				+ ((value.equals("0")) ? "false" : "true"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return new ExpressionValue(ExpressionValue.BOOLEAN_TYPE, value,
				valueAsString, null);
	}

	private ExpressionValue buildResourceType(VariableReader reader) {
		int resourceNumber = reader.readInt();
		reader.readInt();

		String value = reader.readToken();
		String valueAsString = "resource (" + resourceNumber + ") of type (" //$NON-NLS-1$ //$NON-NLS-2$
				+ value + ')';
		return new ExpressionValue(ExpressionValue.RESOURCE_TYPE, value,
				valueAsString, null);
	}

	private ExpressionValue buildArrayType(Expression expression,
			VariableReader reader) {
		int objectLength = reader.readInt();
		int originalLength = objectLength;

		// System.out.println("objectLength " + objectLength);
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
				variableNodes[i] = expression.createChildExpression(name,
						'[' + name + ']');
			}
			variableNodes[i].setValue(build(expression, reader));
		}

		return new ExpressionValue(ExpressionValue.ARRAY_TYPE, "Array", //$NON-NLS-1$
				"Array [" + originalLength + ']', variableNodes); //$NON-NLS-1$
	}

	private ExpressionValue buildObjectType(Expression expression,
			VariableReader reader) {
		String objectName = reader.readString();
		int objectLength = reader.readInt();

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
				expressionNodes[i] = expression.createChildExpression(name,
						"->" + name); //$NON-NLS-1$
			}
			expressionNodes[i].setValue(build(expression, reader));
		}
		String valueAsString = "Object of: " + objectName; //$NON-NLS-1$

		return new ExpressionValue(ExpressionValue.OBJECT_TYPE, objectName,
				valueAsString, expressionNodes);
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