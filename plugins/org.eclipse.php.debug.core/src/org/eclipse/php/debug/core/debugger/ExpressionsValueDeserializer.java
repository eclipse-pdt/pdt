/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.core.debugger;

import java.io.ByteArrayInputStream;

public class ExpressionsValueDeserializer {

    private ExpressionsValueDeserializer() {
    }

    public static ExpressionValue deserializer(Expression expression, String value) {
        if (value == null) {
            // the expression is Illeagal.
            value = "N";
        }

        return build(expression, new VariableReader(value.getBytes()));
    }

    private static ExpressionValue build(Expression expression, VariableReader reader) {
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

    private static ExpressionValue buildIntType(VariableReader reader) {
        String value = reader.readToken();
        String valueAsString = "(int) " + value;
        return new ExpressionValue(ExpressionValue.INT_TYPE, value, valueAsString, null);
    }

    private static ExpressionValue buildDoubleType(VariableReader reader) {
        String value = reader.readToken();
        String valueAsString = "(double) " + value;
        return new ExpressionValue(ExpressionValue.DOUBLE_TYPE, value, valueAsString, null);
    }

    private static ExpressionValue buildSringType(VariableReader reader) {
        String value = reader.readString();
        String valueAsString = "(string:" + value.length() + ") " + value;
        return new ExpressionValue(ExpressionValue.STRING_TYPE, value, valueAsString, null);
    }

    private static ExpressionValue buildBooleanType(VariableReader reader) {
        String value = reader.readToken();
        String valueAsString = "(boolean) " + ((value.equals("0")) ? "false" : "true");
        return new ExpressionValue(ExpressionValue.BOOLEAN_TYPE, value, valueAsString, null);
    }

    private static ExpressionValue buildResourceType(VariableReader reader) {
        int resourceNumber = reader.readInt();
        reader.readInt();

        String value = reader.readToken();
        String valueAsString = "resource (" + resourceNumber + ") of type (" + value + ')';
        return new ExpressionValue(ExpressionValue.RESOURCE_TYPE, value, valueAsString, null);
    }

    private static ExpressionValue buildArrayType(Expression expression, VariableReader reader) {
        int objectLength = reader.readInt();
        int originalLength = objectLength;

        //System.out.println("objectLength " + objectLength);
        if (reader.isLastEnd()) {
            objectLength = 0;
        }
        Expression[] variableNodes = new Expression[objectLength];

        for (int i = 0; i < objectLength; i++) {
            char type = reader.readType();
            //System.out.println("type " + type);
            String name;
            if (type == 'i') {
                name = Integer.toString(reader.readInt());
            } else {
                name = reader.readString();
            }
            if (expression == null) {
                variableNodes[i] = createDefaultVariable(name);
            } else {
                variableNodes[i] = expression.createChildExpression(name, '[' + name + ']');
            }
            variableNodes[i].setValue(build(expression, reader));
        }

        return new ExpressionValue(ExpressionValue.ARRAY_TYPE, "Array", "Array [" + originalLength + ']', variableNodes);
    }

    private static ExpressionValue buildObjectType(Expression expression, VariableReader reader) {
        String objectName = reader.readString();
        int objectLength = reader.readInt();

        if (reader.isLastEnd()) {
            objectLength = 0;
        }

        Expression[] expressionNodes = new Expression[objectLength];

        for (int i = 0; i < objectLength; i++) {
            char type = reader.readType();
            //System.out.println("type " + type);
            String name;
            if (type == 'i') {
                name = Integer.toString(reader.readInt());
            } else {
                name = reader.readString();
            }

            if (expression == null) {
                expressionNodes[i] = createDefaultVariable(name);
            } else {
                expressionNodes[i] = expression.createChildExpression(name, "->" + name);
            }
            expressionNodes[i].setValue(build(expression, reader));
        }
        String valueAsString = "Object of: " + objectName;

        return new ExpressionValue(ExpressionValue.OBJECT_TYPE, objectName, valueAsString, expressionNodes);
    }

    private static Expression createDefaultVariable(String name) {
        return new DefaultExpression('$' + name);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    // variable reader.
    //////////////////////////////////////////////////////////////////////////////////////////////////
    private static class VariableReader extends ByteArrayInputStream {

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

            return new String(bytes);
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