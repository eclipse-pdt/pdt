/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java_cup.runtime.lr_parser;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.php.internal.core.ast.scanner.AstLexer;
import org.eclipse.php.internal.core.compiler.ast.nodes.ASTError;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.wst.xml.core.internal.Logger;

abstract public class AbstractASTParser extends lr_parser {

	private PHPModuleDeclaration program = new PHPModuleDeclaration(0, 0, new LinkedList<Statement>(), new LinkedList<ASTError>());
	private IProblemReporter problemReporter;
	private String fileName;

	public AbstractASTParser() {
		super();
	}

	public AbstractASTParser(Scanner s) {
		super(s);
	}

	public void setProblemReporter(IProblemReporter problemReporter) {
		this.problemReporter = problemReporter;
	}

	public IProblemReporter getProblemReporter() {
		return problemReporter;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	protected List<ASTError> getErrors() {
		return program.getErrors();
	}

	/**
	 * Report on errors that will be added to the AST as statements
	 */
	public void reportError() {
		program.setHasErros(true);
	}

	/**
	 * Reporting an error that cannot be added as a statement and has to be in a separated list.
	 * @param error
	 */
	public void reportError(ASTError error) {
		getErrors().add(error);
		reportError();
	}

	@SuppressWarnings("unchecked")
	public List<Statement> getStatements() {
		return program.getStatements();
	}

	public void addStatement(Statement s) {
		getStatements().add(s);
	}

	public PHPModuleDeclaration getModuleDeclaration() {
		return program;
	}

	public Stack<Statement> declarations = new Stack<Statement>();

	public void report_error(String message, Object info) {
		if (info instanceof Symbol) {
			if (((Symbol) info).left != -1) {
				ASTError error = new ASTError(((Symbol) info).left, ((Symbol) info).right);
				reportError(error);
			} else {
				reportError(new ASTError(0, 1));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void unrecovered_syntax_error(Symbol cur_token) throws java.lang.Exception {
		//in case 
		int start = 0;
		int end = 0;
		Object value = cur_token.value;
		PHPModuleDeclaration program = getModuleDeclaration();
		List<Statement> statements = program.getStatements();
		if (value instanceof List) {
			statements.addAll((List) value);
		}

		while (!declarations.isEmpty()) {
			statements.add(declarations.remove(0));
		}
		if (!statements.isEmpty()) {
			Statement s1 = (Statement) statements.get(0);
			Statement s2 = (Statement) statements.get(statements.size() - 1);
			start = s1.sourceStart();
			end = s2.sourceEnd();
		}
		List<ASTError> errors = getErrors();
		if (!errors.isEmpty()) {
			ASTError lastError = errors.get(errors.size() - 1);
			end = (end > lastError.sourceEnd()) ? end : lastError.sourceEnd();
		}
		program.setStart(start);
		program.setEnd(end);

		super.unrecovered_syntax_error(cur_token);
	}

	public void syntax_error(Symbol cur_token) {
		super.syntax_error(cur_token);
		
		String fileName = getFileName();
		IProblemReporter problemReporter = getProblemReporter();
		if (fileName == null || problemReporter == null) {
			return;
		}

		int state = ((Symbol) stack.peek()).parse_state;

		short[] rowOfProbe = action_tab[state];
		String currentText = (String) cur_token.value;
		int startPosition = cur_token.left;
		int endPosition = cur_token.right;
		int lineNumber = ((AstLexer) getScanner()).getCurrentLine();

		StringBuilder errorMessage = new StringBuilder("syntax error");
		
		if (currentText == null || currentText.length() == 0) {
			currentText = getTokenName(cur_token.sym);
		}
		if (currentText != null && currentText.length() > 0) {
			if (currentText.equals(";")) { // This means EOF, since it's substituted by the lexer explicitly.
				currentText = "EOF"; //$NON-NLS-1$
			}
			endPosition = startPosition + currentText.length();
			errorMessage.append(", unexpected '").append(currentText).append('\'');
		}
		
		if (rowOfProbe.length <= 6) {
			errorMessage.append(", expecting ");
			for (int probe = 0; probe < rowOfProbe.length; probe += 2) {
				String tokenName = getTokenName(rowOfProbe[probe]);
				if (tokenName != null) {
					if (probe > 0) {
						errorMessage.append(" or ");
					}
					errorMessage.append('\'').append(tokenName).append('\'');
				}
			}
		}

		DefaultProblem problem = new DefaultProblem(fileName, errorMessage.toString(), IProblem.Syntax, new String[0], ProblemSeverities.Error, startPosition, endPosition, lineNumber);
		try {
			problemReporter.reportProblem(problem);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	protected abstract String getTokenName(int token);

	public void report_fatal_error(String message, Object info) throws java.lang.Exception {
		/* stop parsing (not really necessary since we throw an exception, but) */
		done_parsing();

		/* use the normal error message reporting to put out the message */
		// report_error(message, info);
		
		// throw new Exception("Can't recover from previous error(s)");
	}

	/* This is a place holder for statements that were found after unclosed classes */
	public Statement pendingStatement = null;

	public void addDeclarationStatement(Statement s) {
		if (declarations.isEmpty()) { // no need to add the declaration to the ModuleDeclaration at this point.
			return;
		}
		Statement node = declarations.peek();
		Block block = null;
		if (node instanceof TypeDeclaration) {
			block = ((TypeDeclaration) node).getBody();
		} else if (node instanceof MethodDeclaration) {
			block = ((MethodDeclaration) node).getBody();
		} else if (node instanceof Block) {
			block = (Block) node;
		} else {
			return;
		}
		block.addStatement(s);
		block.setEnd(s.sourceEnd());
	}
}
