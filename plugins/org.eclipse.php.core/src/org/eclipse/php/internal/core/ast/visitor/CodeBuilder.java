package org.eclipse.php.internal.core.ast.visitor;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.parser.ASTParser;

/**
 * Sample visitor which get the AST and convert it to PHP code
 *  
 * @author Jackie
 *
 */
public class CodeBuilder implements Visitor {

	final static StringBuffer buffer = new StringBuffer();

	public static void main(String[] args) {
		try {
			CodeBuilder codeBuilder = new CodeBuilder();
			String inputDir = "C:/swtTests/inputFiles";
			String outputDir = "C:/swtTests/outputFiles";
			File dir = new File(inputDir);
			String[] files = dir.list();
			for (int i = 0; i < files.length; i++) {
				String filename = files[i];
				codeBuilder.str = readFile(inputDir + "/" + filename);

				StringReader reader = new StringReader(codeBuilder.str);
				Program program = ASTParser.parse(reader);
				System.out.println(program);
				program.accept(codeBuilder);

				System.out.println(buffer.toString());

				printOutputfile(outputDir + "/" + filename);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printOutputfile(String filename) throws FileNotFoundException {
		File outputFile = new File(filename);
		PrintStream printStream = new PrintStream(new FileOutputStream(outputFile));
		printStream.print(buffer.toString());
		printStream.close();
	}

	private static String readFile(String filename) throws FileNotFoundException, IOException {
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

	public void visit(ArrayAccess arrayAccess) {
		arrayAccess.getVariableName().accept(this);
		if (arrayAccess.getIndex() != null) {
			if (arrayAccess.getArrayType() == ArrayAccess.VARIABLE_ARRAY) { //array type
				buffer.append("[");
				arrayAccess.getIndex().accept(this);
				buffer.append("]");
			} else if (arrayAccess.getArrayType() == ArrayAccess.VARIABLE_HASHTABLE) { //hashtable type
				buffer.append("{");
				arrayAccess.getIndex().accept(this);
				buffer.append("}");
			} else {
				throw new IllegalArgumentException();
			}
		}
	}

	public void visit(ArrayCreation arrayCreation) {
		buffer.append("array(");
		ArrayElement[] elements = arrayCreation.getElements();
		for (int i = 0; i < elements.length; i++) {
			elements[i].accept(this);
			buffer.append(",");
		}
		buffer.append(")");
	}

	public void visit(ArrayElement arrayElement) {
		if (arrayElement.getKey() != null) {
			arrayElement.getKey().accept(this);
			buffer.append("=>");
		}
		arrayElement.getValue().accept(this);
	}

	public void visit(Assignment assignment) {
		assignment.getVariable().accept(this);
		buffer.append(Assignment.getOperator(assignment.getOperator()));
		assignment.getValue().accept(this);
	}

	public void visit(ASTError astError) {
		buffer.append(this.str.substring(astError.getStart(), astError.getEnd()));

	}

	public void visit(BackTickExpression backTickExpression) {
		buffer.append("`");
		Expression[] expressions = backTickExpression.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(this);
		}
		buffer.append("`");
	}

	public void visit(Block block) {
		if (block.isCurly()) {
			buffer.append("{\n");
		} else {
			buffer.append(":\n");
		}

		Statement[] statements = block.getStatements();
		for (int i = 0; i < statements.length; i++) {
			statements[i].accept(this);
		}

		if (block.isCurly()) {
			buffer.append("\n}\n");
		} else {
			buffer.append("end;\n");
		}
	}

	public void visit(BreakStatement breakStatement) {
		buffer.append("break ");
		if (breakStatement.getExpr() != null) {
			breakStatement.getExpr().accept(this);
		}
		buffer.append(";\n");
	}

	public void visit(CastExpression castExpression) {
		buffer.append("(");
		buffer.append(CastExpression.getCastType(castExpression.getCastType()));
		buffer.append(")");
		castExpression.getExpr().accept(this);
	}

	public void visit(CatchClause catchClause) {
		buffer.append("catch (");
		catchClause.getClassName().accept(this);
		buffer.append(" ");
		catchClause.getVariable().accept(this);
		buffer.append(") ");
		catchClause.getStatement().accept(this);
	}

	public void visit(ClassConstantDeclaration classConstantDeclaration) {
		buffer.append("const ");
		boolean isFirst = true;
		Identifier[] variableNames = classConstantDeclaration.getVariableNames();
		Expression[] constantValues = classConstantDeclaration.getConstantValues();
		for (int i = 0; i < variableNames.length; i++) {
			if (!isFirst) {
				buffer.append(",");
			}
			variableNames[i].accept(this);
			buffer.append(" = ");
			constantValues[i].accept(this);
			isFirst = false;
		}
		buffer.append(";\n");
	}

	public void visit(ClassDeclaration classDeclaration) {
		buffer.append("class ");
		classDeclaration.getName().accept(this);
		if (classDeclaration.getSuperClass() != null) {
			buffer.append(" extends ");
			classDeclaration.getSuperClass().accept(this);
		}
		Identifier[] interfaces = classDeclaration.getInterfaces();
		if (interfaces != null && interfaces.length != 0) {
			buffer.append(" implements ");
			interfaces[0].accept(this);
			for (int i = 1; i < interfaces.length; i++) {
				buffer.append(" , ");
				interfaces[i].accept(this);
			}
		}
		classDeclaration.getBody().accept(this);
	}

	public void visit(ClassInstanceCreation classInstanceCreation) {
		buffer.append("new ");
		classInstanceCreation.getClassName().accept(this);
		Expression[] ctorParams = classInstanceCreation.getCtorParams();
		if (ctorParams.length != 0) {
			buffer.append("(");
			ctorParams[0].accept(this);
			for (int i = 1; i < ctorParams.length; i++) {
				buffer.append(",");
				ctorParams[i].accept(this);
			}
			buffer.append(")");
		}
	}

	public void visit(ClassName className) {
		className.getClassName().accept(this);
	}

	public void visit(CloneExpression cloneExpression) {
		buffer.append("clone ");
		cloneExpression.getExpr().accept(this);
	}

	public void visit(Comment comment) {
		buffer.append(this.str.substring(comment.getStart(), comment.getEnd()));
	}

	public void visit(ConditionalExpression conditionalExpression) {
		conditionalExpression.getCondition().accept(this);
		buffer.append(" ? ");
		conditionalExpression.getIfTrue().accept(this);
		buffer.append(" : ");
		conditionalExpression.getIfFalse().accept(this);
	}

	public void visit(ContinueStatement continueStatement) {
		buffer.append("continue ");
		if (continueStatement.getExpr() != null) {
			continueStatement.getExpr().accept(this);
		}
		buffer.append(";\n");
	}

	public void visit(DeclareStatement declareStatement) {
		buffer.append("declare (");
		boolean isFirst = true;
		Identifier[] directiveNames = declareStatement.getDirectiveNames();
		Expression[] directiveValues = declareStatement.getDirectiveValues();
		for (int i = 0; i < directiveNames.length; i++) {
			if (!isFirst) {
				buffer.append(",");
			}
			directiveNames[i].accept(this);
			buffer.append(" = ");
			directiveValues[i].accept(this);
			isFirst = false;
		}
		buffer.append(")");
		declareStatement.getAction().accept(this);
	}

	public void visit(DoStatement doStatement) {
		buffer.append("do ");
		doStatement.getAction().accept(this);
		buffer.append("while (");
		doStatement.getCondition().accept(this);
		buffer.append(");\n");
	}

	public void visit(EchoStatement echoStatement) {
		buffer.append("echo ");
		Expression[] expressions = echoStatement.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(this);
		}
		buffer.append(";\n ");
	}

	public void visit(EmptyStatement emptyStatement) {
		buffer.append(";\n");
	}

	public void visit(ExpressionStatement expressionStatement) {
		expressionStatement.getExpr().accept(this);
		buffer.append(";\n");
	}

	public void visit(FieldAccess fieldAccess) {
		fieldAccess.getDispatcher().accept(this);
		buffer.append("->");
		fieldAccess.getField().accept(this);
	}

	public void visit(FieldsDeclaration fieldsDeclaration) {
		Variable[] variableNames = fieldsDeclaration.getVariableNames();
		Expression[] initialValues = fieldsDeclaration.getInitialValues();
		for (int i = 0; i < variableNames.length; i++) {
			buffer.append(fieldsDeclaration.getModifierString() + " ");
			variableNames[i].accept(this);
			if (initialValues[i] != null) {
				buffer.append(" = ");
				initialValues[i].accept(this);
			}
			buffer.append(";\n");
		}
	}

	public void visit(ForEachStatement forEachStatement) {
		buffer.append("foreach (");
		forEachStatement.getExpression().accept(this);
		buffer.append(" as ");
		if (forEachStatement.getKey() != null) {
			forEachStatement.getKey().accept(this);
			buffer.append(" => ");
		}
		forEachStatement.getValue().accept(this);
		buffer.append(")");
		forEachStatement.getStatement().accept(this);
	}

	public void visit(FormalParameter formalParameter) {
		if (formalParameter.getParameterType() != null) {
			formalParameter.getParameterType().accept(this);
		}
		formalParameter.getParameterName().accept(this);
		if (formalParameter.getDefaultValue() != null) {
			formalParameter.getDefaultValue().accept(this);
		}
	}

	public void visit(ForStatement forStatement) {
		boolean isFirst = true;
		buffer.append("for (");
		Expression[] initializations = forStatement.getInitializations();
		Expression[] conditions = forStatement.getConditions();
		Expression[] increasements = forStatement.getIncreasements();
		for (int i = 0; i < initializations.length; i++) {
			if (!isFirst) {
				buffer.append(",");
			}
			initializations[i].accept(this);
			isFirst = false;
		}
		isFirst = true;
		buffer.append(" ; ");
		for (int i = 0; i < conditions.length; i++) {
			if (!isFirst) {
				buffer.append(",");
			}
			conditions[i].accept(this);
			isFirst = false;
		}
		isFirst = true;
		buffer.append(" ; ");
		for (int i = 0; i < increasements.length; i++) {
			if (!isFirst) {
				buffer.append(",");
			}
			increasements[i].accept(this);
			isFirst = false;
		}
		buffer.append(" ) ");
		forStatement.getAction().accept(this);
	}

	public void visit(FunctionDeclaration functionDeclaration) {
		functionDeclaration.getFunctionName().accept(this);
		buffer.append("(");
		FormalParameter[] formalParameters = functionDeclaration.getFormalParameters();
		if (formalParameters.length != 0) {
			formalParameters[0].accept(this);
			for (int i = 1; i < formalParameters.length; i++) {
				buffer.append(",");
				formalParameters[i].accept(this);
			}
			buffer.append(")");
		}
		if (functionDeclaration.getBody() != null) {
			functionDeclaration.getBody().accept(this);
		}
	}

	public void visit(FunctionInvocation functionInvocation) {
		functionInvocation.getFunctionName().accept(this);
		buffer.append("(");
		Expression[] parameters = functionInvocation.getParameters();
		if (parameters.length != 0) {
			parameters[0].accept(this);
			for (int i = 1; i < parameters.length; i++) {
				buffer.append(",");
				parameters[i].accept(this);
			}
		}
		buffer.append(")");
	}

	public void visit(FunctionName functionName) {
		functionName.getFunctionName().accept(this);
	}

	public void visit(GlobalStatement globalStatement) {
		buffer.append("global ");
		boolean isFirst = true;
		Variable[] variables = globalStatement.getVariables();
		for (int i = 0; i < variables.length; i++) {
			if (!isFirst) {
				buffer.append(", ");
			}
			variables[i].accept(this);
			isFirst = false;
		}
		buffer.append(";\n ");
	}

	public void visit(Identifier identifier) {
		buffer.append(identifier.getName());
	}

	public void visit(IfStatement ifStatement) {
		buffer.append("if(");
		ifStatement.getCondition().accept(this);
		buffer.append(")");
		ifStatement.getTrueStatement().accept(this);
		if (ifStatement.getFalseStatement() != null) {
			buffer.append("else");
			ifStatement.getFalseStatement().accept(this);
		}
	}

	public void visit(IgnoreError ignoreError) {
		buffer.append("@");
		ignoreError.getExpr().accept(this);
	}

	public void visit(Include include) {
		buffer.append(Include.getType(include.getIncludeType()));
		buffer.append(" (");
		include.getExpr().accept(this);
		buffer.append(")");
	}

	public void visit(InfixExpression infixExpression) {
		infixExpression.getLeft().accept(this);
		buffer.append(InfixExpression.getOperator(infixExpression.getOperator()));
		infixExpression.getRight().accept(this);
	}

	public void visit(InLineHtml inLineHtml) {
		buffer.append(this.str.substring(inLineHtml.getStart(), inLineHtml.getEnd()));
	}

	public void visit(InstanceOfExpression instanceOfExpression) {
		instanceOfExpression.getExpr().accept(this);
		buffer.append(" instanceof ");
		instanceOfExpression.getClassName().accept(this);
	}

	public void visit(InterfaceDeclaration interfaceDeclaration) {
		buffer.append("interface ");
		interfaceDeclaration.getName().accept(this);
		buffer.append(" extends ");
		boolean isFirst = true;
		Identifier[] interfaces = interfaceDeclaration.getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			if (!isFirst) {
				buffer.append(", ");
			}
			interfaces[i].accept(this);
			isFirst = false;
		}
		interfaceDeclaration.getBody().accept(this);
	}

	public void visit(ListVariable listVariable) {
		buffer.append("list(");
		boolean isFirst = true;
		VariableBase[] variables = listVariable.getVariables();
		for (int i = 0; i < variables.length; i++) {
			if (!isFirst) {
				buffer.append(", ");
			}
			variables[i].accept(this);
			isFirst = false;
		}
		buffer.append(")");
	}

	public void visit(MethodDeclaration methodDeclaration) {
		methodDeclaration.getFunction().accept(this);
	}

	public void visit(MethodInvocation methodInvocation) {
		methodInvocation.getDispatcher().accept(this);
		buffer.append("->");
		methodInvocation.getMethod().accept(this);
	}

	public void visit(ParenthesisExpression parenthesisExpression) {
		buffer.append("(");
		if (parenthesisExpression.getExpr() != null) {
			parenthesisExpression.getExpr().accept(this);
		}
		buffer.append(")");
		
	}

	public void visit(PostfixExpression postfixExpressions) {
		postfixExpressions.getVariable().accept(this);
		buffer.append(PostfixExpression.getOperator(postfixExpressions.getOperator()));
	}

	public void visit(PrefixExpression prefixExpression) {
		prefixExpression.getVariable().accept(this);
		buffer.append(PrefixExpression.getOperator(prefixExpression.getOperator()));
	}

	public void visit(Program program) {
		boolean isPhpState = false;
		Statement[] statements = program.getStatements();
		for (int i = 0; i < statements.length; i++) {
			boolean isHtml = statements[i] instanceof InLineHtml;

			if (!isHtml && !isPhpState) {
				// html -> php
				buffer.append("<?php\n");
				statements[i].accept(this);
				isPhpState = true;
			} else if (!isHtml && isPhpState) {
				// php -> php
				statements[i].accept(this);
				buffer.append("\n");
			} else if (isHtml && isPhpState) {
				// php -> html
				buffer.append("?>\n");
				statements[i].accept(this);
				buffer.append("\n");
				isPhpState = false;
			} else {
				// html first
				statements[i].accept(this);
				buffer.append("\n");
			}
		}

		if (isPhpState) {
			buffer.append("?>\n");
		}

		Collection comments = program.getComments();
		for (Iterator iter = comments.iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.accept(this);
		}
	}

	public void visit(Quote quote) {
		switch (quote.getQuoteType()) {
			case 0:
				buffer.append("\"");
				acceptQuoteExpression(quote.getExpressions());
				buffer.append("\"");
				break;
			case 1:
				buffer.append("\'");
				acceptQuoteExpression(quote.getExpressions());
				buffer.append("\'");
				break;
			case 2:
				buffer.append("<<<Heredoc\n");
				acceptQuoteExpression(quote.getExpressions());
				buffer.append("\nHeredoc");
		}
	}

	public void visit(Reference reference) {
		buffer.append("&");
		reference.getExpression().accept(this);
	}

	public void visit(ReflectionVariable reflectionVariable) {
		buffer.append("$");
		reflectionVariable.getVariableName().accept(this);
	}

	public void visit(ReturnStatement returnStatement) {
		buffer.append("return ");
		if (returnStatement.getExpr() != null) {
			returnStatement.getExpr().accept(this);
		}
		buffer.append(";\n");
	}

	public void visit(Scalar scalar) {
		if (scalar.getScalarType() == Scalar.TYPE_UNKNOWN) {
			buffer.append(this.str.substring(scalar.getStart(), scalar.getEnd()));
		} else {
			buffer.append(scalar.getStringValue());
		}
	}

	public void visit(StaticConstantAccess staticFieldAccess) {
		staticFieldAccess.getClassName().accept(this);
		buffer.append("::");
		staticFieldAccess.getConstant().accept(this);
	}

	public void visit(StaticFieldAccess staticFieldAccess) {
		staticFieldAccess.getClassName().accept(this);
		buffer.append("::");
		staticFieldAccess.getField().accept(this);
	}

	public void visit(StaticMethodInvocation staticMethodInvocation) {
		staticMethodInvocation.getClassName().accept(this);
		buffer.append("::");
		staticMethodInvocation.getMethod().accept(this);
	}

	public void visit(StaticStatement staticStatement) {
		buffer.append("static ");
		boolean isFirst = true;
		Expression[] expressions = staticStatement.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			if (!isFirst) {
				buffer.append(", ");
			}
			expressions[i].accept(this);
			isFirst = false;
		}
		buffer.append(";\n");
	}

	public void visit(SwitchCase switchCase) {

		if (switchCase.getValue() != null) {
			switchCase.getValue().accept(this);
			buffer.append(":\n");
		}
		Statement[] actions = switchCase.getActions();
		for (int i = 0; i < actions.length; i++) {
			actions[i].accept(this);
		}
	}

	public void visit(SwitchStatement switchStatement) {
		buffer.append("switch (");
		switchStatement.getExpr().accept(this);
		buffer.append(")");
		switchStatement.getStatement().accept(this);
	}

	public void visit(ThrowStatement throwStatement) {
		throwStatement.getExpr().accept(this);
	}

	public void visit(TryStatement tryStatement) {
		buffer.append("try ");
		tryStatement.getTryStatement().accept(this);
		CatchClause[] catchClauses = tryStatement.getCatchClauses();
		for (int i = 0; i < catchClauses.length; i++) {
			catchClauses[i].accept(this);
		}
	}

	public void visit(UnaryOperation unaryOperation) {
		buffer.append(UnaryOperation.getOperator(unaryOperation.getOperator()));
		unaryOperation.getExpr().accept(this);
	}

	public void visit(Variable variable) {
		variable.getVariableName().accept(this);
	}

	public void visit(WhileStatement whileStatement) {
		buffer.append("while (");
		whileStatement.getCondition().accept(this);
		buffer.append(")\n");
		whileStatement.getAction().accept(this);
	}
}
