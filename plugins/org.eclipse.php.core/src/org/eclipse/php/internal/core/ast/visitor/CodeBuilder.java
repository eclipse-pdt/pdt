package org.eclipse.php.internal.core.ast.visitor;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Sample visitor which get the AST and convert it to PHP code
 *  
 * @author Jackie
 *
 */
public class CodeBuilder extends AbstractVisitor {

	public StringBuffer buffer = new StringBuffer();

	public void printOutputfile(String filename) throws IOException {
		Writer outFile = new FileWriter(filename, false);
		outFile.write(buffer.toString());
		outFile.close();
	}

	public static String readFile(String filename) throws FileNotFoundException, IOException {
		StringBuffer inputBuffer = new StringBuffer();
		FileInputStream fileInputStream = new FileInputStream(new File(filename));
		int read = fileInputStream.read();
		while (read != -1) {
			inputBuffer.append((char) read);
			read = fileInputStream.read();
		}
		fileInputStream.close();
		return inputBuffer.toString();
	}

	private String str;

	private void acceptQuoteExpression(Expression[] expressions) {
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(this);
		}
	}

	public boolean visit(ArrayAccess arrayAccess) {
		arrayAccess.getVariableName().accept(this);
		if (arrayAccess.getIndex() != null) {
			if (arrayAccess.getArrayType() == ArrayAccess.VARIABLE_ARRAY) { //array type
				buffer.append("["); //$NON-NLS-1$
				arrayAccess.getIndex().accept(this);
				buffer.append("]"); //$NON-NLS-1$
			} else if (arrayAccess.getArrayType() == ArrayAccess.VARIABLE_HASHTABLE) { //hashtable type
				buffer.append("{"); //$NON-NLS-1$
				arrayAccess.getIndex().accept(this);
				buffer.append("}"); //$NON-NLS-1$
			} else {
				throw new IllegalArgumentException();
			}
		}
		return false;
	}

	public boolean visit(ArrayCreation arrayCreation) {
		buffer.append("array("); //$NON-NLS-1$
		ArrayElement[] elements = arrayCreation.getElements();
		for (int i = 0; i < elements.length; i++) {
			elements[i].accept(this);
			buffer.append(","); //$NON-NLS-1$
		}
		buffer.append(")"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(ArrayElement arrayElement) {
		if (arrayElement.getKey() != null) {
			arrayElement.getKey().accept(this);
			buffer.append("=>"); //$NON-NLS-1$
		}
		arrayElement.getValue().accept(this);
		return false;
	}

	public boolean visit(Assignment assignment) {
		assignment.getVariable().accept(this);
		buffer.append(Assignment.getOperator(assignment.getOperator()));
		assignment.getValue().accept(this);
		return false;
	}

	public boolean visit(ASTError astError) {
		buffer.append(this.str.substring(astError.getStart(), astError.getEnd()));

		return false;
	}

	public boolean visit(BackTickExpression backTickExpression) {
		buffer.append("`"); //$NON-NLS-1$
		Expression[] expressions = backTickExpression.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(this);
		}
		buffer.append("`"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(Block block) {
		if (block.isCurly()) {
			buffer.append("{\n"); //$NON-NLS-1$
		} else {
			buffer.append(":\n"); //$NON-NLS-1$
		}

		Statement[] statements = block.getStatements();
		for (int i = 0; i < statements.length; i++) {
			statements[i].accept(this);
		}

		if (block.isCurly()) {
			buffer.append("\n}\n"); //$NON-NLS-1$
		} else {
			buffer.append("end;\n"); //$NON-NLS-1$
		}
		return false;
	}

	public boolean visit(BreakStatement breakStatement) {
		buffer.append("break "); //$NON-NLS-1$
		if (breakStatement.getExpr() != null) {
			breakStatement.getExpr().accept(this);
		}
		buffer.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(CastExpression castExpression) {
		buffer.append("("); //$NON-NLS-1$
		buffer.append(CastExpression.getCastType(castExpression.getCastType()));
		buffer.append(")"); //$NON-NLS-1$
		castExpression.getExpr().accept(this);
		return false;
	}

	public boolean visit(CatchClause catchClause) {
		buffer.append("catch ("); //$NON-NLS-1$
		catchClause.getClassName().accept(this);
		buffer.append(" "); //$NON-NLS-1$
		catchClause.getVariable().accept(this);
		buffer.append(") "); //$NON-NLS-1$
		catchClause.getStatement().accept(this);
		return false;
	}

	public boolean visit(ClassConstantDeclaration classConstantDeclaration) {
		buffer.append("const "); //$NON-NLS-1$
		boolean isFirst = true;
		Identifier[] variableNames = classConstantDeclaration.getVariableNames();
		Expression[] constantValues = classConstantDeclaration.getConstantValues();
		for (int i = 0; i < variableNames.length; i++) {
			if (!isFirst) {
				buffer.append(","); //$NON-NLS-1$
			}
			variableNames[i].accept(this);
			buffer.append(" = "); //$NON-NLS-1$
			constantValues[i].accept(this);
			isFirst = false;
		}
		buffer.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		buffer.append("class "); //$NON-NLS-1$
		classDeclaration.getName().accept(this);
		if (classDeclaration.getSuperClass() != null) {
			buffer.append(" extends "); //$NON-NLS-1$
			classDeclaration.getSuperClass().accept(this);
		}
		Identifier[] interfaces = classDeclaration.getInterfaces();
		if (interfaces != null && interfaces.length != 0) {
			buffer.append(" implements "); //$NON-NLS-1$
			interfaces[0].accept(this);
			for (int i = 1; i < interfaces.length; i++) {
				buffer.append(" , "); //$NON-NLS-1$
				interfaces[i].accept(this);
			}
		}
		classDeclaration.getBody().accept(this);
		return false;
	}

	public boolean visit(ClassInstanceCreation classInstanceCreation) {
		buffer.append("new "); //$NON-NLS-1$
		classInstanceCreation.getClassName().accept(this);
		Expression[] ctorParams = classInstanceCreation.getCtorParams();
		if (ctorParams.length != 0) {
			buffer.append("("); //$NON-NLS-1$
			ctorParams[0].accept(this);
			for (int i = 1; i < ctorParams.length; i++) {
				buffer.append(","); //$NON-NLS-1$
				ctorParams[i].accept(this);
			}
			buffer.append(")"); //$NON-NLS-1$
		}
		return false;
	}

	public boolean visit(ClassName className) {
		className.getClassName().accept(this);
		return false;
	}

	public boolean visit(CloneExpression cloneExpression) {
		buffer.append("clone "); //$NON-NLS-1$
		cloneExpression.getExpr().accept(this);
		return false;
	}

	public boolean visit(Comment comment) {
		buffer.append(this.str.substring(comment.getStart(), comment.getEnd()));
		return false;
	}

	public boolean visit(ConditionalExpression conditionalExpression) {
		conditionalExpression.getCondition().accept(this);
		buffer.append(" ? "); //$NON-NLS-1$
		conditionalExpression.getIfTrue().accept(this);
		buffer.append(" : "); //$NON-NLS-1$
		conditionalExpression.getIfFalse().accept(this);
		return false;
	}

	public boolean visit(ContinueStatement continueStatement) {
		buffer.append("continue "); //$NON-NLS-1$
		if (continueStatement.getExpr() != null) {
			continueStatement.getExpr().accept(this);
		}
		buffer.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(DeclareStatement declareStatement) {
		buffer.append("declare ("); //$NON-NLS-1$
		boolean isFirst = true;
		Identifier[] directiveNames = declareStatement.getDirectiveNames();
		Expression[] directiveValues = declareStatement.getDirectiveValues();
		for (int i = 0; i < directiveNames.length; i++) {
			if (!isFirst) {
				buffer.append(","); //$NON-NLS-1$
			}
			directiveNames[i].accept(this);
			buffer.append(" = "); //$NON-NLS-1$
			directiveValues[i].accept(this);
			isFirst = false;
		}
		buffer.append(")"); //$NON-NLS-1$
		declareStatement.getAction().accept(this);
		return false;
	}

	public boolean visit(DoStatement doStatement) {
		buffer.append("do "); //$NON-NLS-1$
		doStatement.getAction().accept(this);
		buffer.append("while ("); //$NON-NLS-1$
		doStatement.getCondition().accept(this);
		buffer.append(");\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(EchoStatement echoStatement) {
		buffer.append("echo "); //$NON-NLS-1$
		Expression[] expressions = echoStatement.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(this);
		}
		buffer.append(";\n "); //$NON-NLS-1$
		return false;
	}

	public boolean visit(EmptyStatement emptyStatement) {
		buffer.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(ExpressionStatement expressionStatement) {
		expressionStatement.getExpr().accept(this);
		buffer.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(FieldAccess fieldAccess) {
		fieldAccess.getDispatcher().accept(this);
		buffer.append("->"); //$NON-NLS-1$
		fieldAccess.getField().accept(this);
		return false;
	}

	public boolean visit(FieldsDeclaration fieldsDeclaration) {
		Variable[] variableNames = fieldsDeclaration.getVariableNames();
		Expression[] initialValues = fieldsDeclaration.getInitialValues();
		for (int i = 0; i < variableNames.length; i++) {
			buffer.append(fieldsDeclaration.getModifierString() + " "); //$NON-NLS-1$
			variableNames[i].accept(this);
			if (initialValues[i] != null) {
				buffer.append(" = "); //$NON-NLS-1$
				initialValues[i].accept(this);
			}
			buffer.append(";\n"); //$NON-NLS-1$
		}
		return false;
	}

	public boolean visit(ForEachStatement forEachStatement) {
		buffer.append("foreach ("); //$NON-NLS-1$
		forEachStatement.getExpression().accept(this);
		buffer.append(" as "); //$NON-NLS-1$
		if (forEachStatement.getKey() != null) {
			forEachStatement.getKey().accept(this);
			buffer.append(" => "); //$NON-NLS-1$
		}
		forEachStatement.getValue().accept(this);
		buffer.append(")"); //$NON-NLS-1$
		forEachStatement.getStatement().accept(this);
		return false;
	}

	public boolean visit(FormalParameter formalParameter) {
		if (formalParameter.getParameterType() != null) {
			formalParameter.getParameterType().accept(this);
		}
		formalParameter.getParameterName().accept(this);
		if (formalParameter.getDefaultValue() != null) {
			formalParameter.getDefaultValue().accept(this);
		}
		return false;
	}

	public boolean visit(ForStatement forStatement) {
		boolean isFirst = true;
		buffer.append("for ("); //$NON-NLS-1$
		Expression[] initializations = forStatement.getInitializations();
		Expression[] conditions = forStatement.getConditions();
		Expression[] increasements = forStatement.getIncreasements();
		for (int i = 0; i < initializations.length; i++) {
			if (!isFirst) {
				buffer.append(","); //$NON-NLS-1$
			}
			initializations[i].accept(this);
			isFirst = false;
		}
		isFirst = true;
		buffer.append(" ; "); //$NON-NLS-1$
		for (int i = 0; i < conditions.length; i++) {
			if (!isFirst) {
				buffer.append(","); //$NON-NLS-1$
			}
			conditions[i].accept(this);
			isFirst = false;
		}
		isFirst = true;
		buffer.append(" ; "); //$NON-NLS-1$
		for (int i = 0; i < increasements.length; i++) {
			if (!isFirst) {
				buffer.append(","); //$NON-NLS-1$
			}
			increasements[i].accept(this);
			isFirst = false;
		}
		buffer.append(" ) "); //$NON-NLS-1$
		forStatement.getAction().accept(this);
		return false;
	}

	public boolean visit(FunctionDeclaration functionDeclaration) {
		buffer.append(" function ");
		functionDeclaration.getFunctionName().accept(this);
		buffer.append("("); //$NON-NLS-1$
		FormalParameter[] formalParameters = functionDeclaration.getFormalParameters();
		if (formalParameters.length != 0) {
			formalParameters[0].accept(this);
			for (int i = 1; i < formalParameters.length; i++) {
				buffer.append(","); //$NON-NLS-1$
				formalParameters[i].accept(this);
			}

		}
		buffer.append(")"); //$NON-NLS-1$
		if (functionDeclaration.getBody() != null) {
			functionDeclaration.getBody().accept(this);
		}
		return false;
	}

	public boolean visit(FunctionInvocation functionInvocation) {
		functionInvocation.getFunctionName().accept(this);
		buffer.append("("); //$NON-NLS-1$
		Expression[] parameters = functionInvocation.getParameters();
		if (parameters.length != 0) {
			parameters[0].accept(this);
			for (int i = 1; i < parameters.length; i++) {
				buffer.append(","); //$NON-NLS-1$
				parameters[i].accept(this);
			}
		}
		buffer.append(")"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(FunctionName functionName) {
		functionName.getFunctionName().accept(this);
		return false;
	}

	public boolean visit(GlobalStatement globalStatement) {
		buffer.append("global "); //$NON-NLS-1$
		boolean isFirst = true;
		Variable[] variables = globalStatement.getVariables();
		for (int i = 0; i < variables.length; i++) {
			if (!isFirst) {
				buffer.append(", "); //$NON-NLS-1$
			}
			variables[i].accept(this);
			isFirst = false;
		}
		buffer.append(";\n "); //$NON-NLS-1$
		return false;
	}

	public boolean visit(Identifier identifier) {
		buffer.append(identifier.getName());
		return false;
	}

	public boolean visit(IfStatement ifStatement) {
		buffer.append("if("); //$NON-NLS-1$
		ifStatement.getCondition().accept(this);
		buffer.append(")"); //$NON-NLS-1$
		ifStatement.getTrueStatement().accept(this);
		if (ifStatement.getFalseStatement() != null) {
			buffer.append("else"); //$NON-NLS-1$
			ifStatement.getFalseStatement().accept(this);
		}
		return false;
	}

	public boolean visit(IgnoreError ignoreError) {
		buffer.append("@"); //$NON-NLS-1$
		ignoreError.getExpr().accept(this);
		return false;
	}

	public boolean visit(Include include) {
		buffer.append(Include.getType(include.getIncludeType()));
		buffer.append(" ("); //$NON-NLS-1$
		include.getExpr().accept(this);
		buffer.append(")"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(InfixExpression infixExpression) {
		infixExpression.getLeft().accept(this);
		buffer.append(InfixExpression.getOperator(infixExpression.getOperator()));
		infixExpression.getRight().accept(this);
		return false;
	}

	public boolean visit(InLineHtml inLineHtml) {
		buffer.append(this.str.substring(inLineHtml.getStart(), inLineHtml.getEnd()));
		return false;
	}

	public boolean visit(InstanceOfExpression instanceOfExpression) {
		instanceOfExpression.getExpr().accept(this);
		buffer.append(" instanceof "); //$NON-NLS-1$
		instanceOfExpression.getClassName().accept(this);
		return false;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		buffer.append("interface "); //$NON-NLS-1$
		interfaceDeclaration.getName().accept(this);
		buffer.append(" extends "); //$NON-NLS-1$
		boolean isFirst = true;
		Identifier[] interfaces = interfaceDeclaration.getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			if (!isFirst) {
				buffer.append(", "); //$NON-NLS-1$
			}
			interfaces[i].accept(this);
			isFirst = false;
		}
		interfaceDeclaration.getBody().accept(this);
		return false;
	}

	public boolean visit(ListVariable listVariable) {
		buffer.append("list("); //$NON-NLS-1$
		boolean isFirst = true;
		VariableBase[] variables = listVariable.getVariables();
		for (int i = 0; i < variables.length; i++) {
			if (!isFirst) {
				buffer.append(", "); //$NON-NLS-1$
			}
			variables[i].accept(this);
			isFirst = false;
		}
		buffer.append(")"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(MethodDeclaration methodDeclaration) {
		buffer.append(methodDeclaration.getModifierString());
		methodDeclaration.getFunction().accept(this);
		return false;
	}

	public boolean visit(MethodInvocation methodInvocation) {
		methodInvocation.getDispatcher().accept(this);
		buffer.append("->"); //$NON-NLS-1$
		methodInvocation.getMethod().accept(this);
		return false;
	}

	public boolean visit(ParenthesisExpression parenthesisExpression) {
		buffer.append("("); //$NON-NLS-1$
		if (parenthesisExpression.getExpr() != null) {
			parenthesisExpression.getExpr().accept(this);
		}
		buffer.append(")"); //$NON-NLS-1$

		return false;
	}

	public boolean visit(PostfixExpression postfixExpressions) {
		postfixExpressions.getVariable().accept(this);
		buffer.append(PostfixExpression.getOperator(postfixExpressions.getOperator()));
		return false;
	}

	public boolean visit(PrefixExpression prefixExpression) {
		prefixExpression.getVariable().accept(this);
		buffer.append(PrefixExpression.getOperator(prefixExpression.getOperator()));
		return false;
	}

	public boolean visit(Program program) {
		boolean isPhpState = false;
		Statement[] statements = program.getStatements();
		for (int i = 0; i < statements.length; i++) {
			boolean isHtml = statements[i] instanceof InLineHtml;

			if (!isHtml && !isPhpState) {
				// html -> php
				buffer.append("<?php\n"); //$NON-NLS-1$
				statements[i].accept(this);
				isPhpState = true;
			} else if (!isHtml && isPhpState) {
				// php -> php
				statements[i].accept(this);
				buffer.append("\n"); //$NON-NLS-1$
			} else if (isHtml && isPhpState) {
				// php -> html
				buffer.append("?>\n"); //$NON-NLS-1$
				statements[i].accept(this);
				buffer.append("\n"); //$NON-NLS-1$
				isPhpState = false;
			} else {
				// html first
				statements[i].accept(this);
				buffer.append("\n"); //$NON-NLS-1$
			}
		}

		if (isPhpState) {
			buffer.append("?>\n"); //$NON-NLS-1$
		}

		Collection comments = program.getComments();
		for (Iterator iter = comments.iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.accept(this);
		}
		return false;
	}

	public boolean visit(Quote quote) {
		switch (quote.getQuoteType()) {
			case 0:
				buffer.append("\""); //$NON-NLS-1$
				acceptQuoteExpression(quote.getExpressions());
				buffer.append("\""); //$NON-NLS-1$
				break;
			case 1:
				buffer.append("\'"); //$NON-NLS-1$
				acceptQuoteExpression(quote.getExpressions());
				buffer.append("\'"); //$NON-NLS-1$
				break;
			case 2:
				buffer.append("<<<Heredoc\n"); //$NON-NLS-1$
				acceptQuoteExpression(quote.getExpressions());
				buffer.append("\nHeredoc"); //$NON-NLS-1$
		}
		return false;
	}

	public boolean visit(Reference reference) {
		buffer.append("&"); //$NON-NLS-1$
		reference.getExpression().accept(this);
		return false;
	}

	public boolean visit(ReflectionVariable reflectionVariable) {
		buffer.append("$"); //$NON-NLS-1$
		reflectionVariable.getVariableName().accept(this);
		return false;
	}

	public boolean visit(ReturnStatement returnStatement) {
		buffer.append("return "); //$NON-NLS-1$
		if (returnStatement.getExpr() != null) {
			returnStatement.getExpr().accept(this);
		}
		buffer.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(Scalar scalar) {
		if (scalar.getScalarType() == Scalar.TYPE_UNKNOWN) {
			buffer.append(this.str.substring(scalar.getStart(), scalar.getEnd()));
		} else {
			buffer.append(scalar.getStringValue());
		}
		return false;
	}

	public boolean visit(StaticConstantAccess staticFieldAccess) {
		staticFieldAccess.getClassName().accept(this);
		buffer.append("::"); //$NON-NLS-1$
		staticFieldAccess.getConstant().accept(this);
		return false;
	}

	public boolean visit(StaticFieldAccess staticFieldAccess) {
		staticFieldAccess.getClassName().accept(this);
		buffer.append("::"); //$NON-NLS-1$
		staticFieldAccess.getField().accept(this);
		return false;
	}

	public boolean visit(StaticMethodInvocation staticMethodInvocation) {
		staticMethodInvocation.getClassName().accept(this);
		buffer.append("::"); //$NON-NLS-1$
		staticMethodInvocation.getMethod().accept(this);
		return false;
	}

	public boolean visit(StaticStatement staticStatement) {
		buffer.append("static "); //$NON-NLS-1$
		boolean isFirst = true;
		Expression[] expressions = staticStatement.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			if (!isFirst) {
				buffer.append(", "); //$NON-NLS-1$
			}
			expressions[i].accept(this);
			isFirst = false;
		}
		buffer.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(SwitchCase switchCase) {

		if (switchCase.getValue() != null) {
			switchCase.getValue().accept(this);
			buffer.append(":\n"); //$NON-NLS-1$
		}
		Statement[] actions = switchCase.getActions();
		for (int i = 0; i < actions.length; i++) {
			actions[i].accept(this);
		}
		return false;
	}

	public boolean visit(SwitchStatement switchStatement) {
		buffer.append("switch ("); //$NON-NLS-1$
		switchStatement.getExpr().accept(this);
		buffer.append(")"); //$NON-NLS-1$
		switchStatement.getStatement().accept(this);
		return false;
	}

	public boolean visit(ThrowStatement throwStatement) {
		throwStatement.getExpr().accept(this);
		return false;
	}

	public boolean visit(TryStatement tryStatement) {
		buffer.append("try "); //$NON-NLS-1$
		tryStatement.getTryStatement().accept(this);
		CatchClause[] catchClauses = tryStatement.getCatchClauses();
		for (int i = 0; i < catchClauses.length; i++) {
			catchClauses[i].accept(this);
		}
		return false;
	}

	public boolean visit(UnaryOperation unaryOperation) {
		buffer.append(UnaryOperation.getOperator(unaryOperation.getOperator()));
		unaryOperation.getExpr().accept(this);
		return false;
	}

	public boolean visit(Variable variable) {
		buffer.append("$");
		variable.getVariableName().accept(this);
		return false;
	}

	public boolean visit(WhileStatement whileStatement) {
		buffer.append("while ("); //$NON-NLS-1$
		whileStatement.getCondition().accept(this);
		buffer.append(")\n"); //$NON-NLS-1$
		whileStatement.getAction().accept(this);
		return false;
	}
}
