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
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java_cup.runtime.lr_parser;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.php.internal.core.ast.scanner.AstLexer;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;

abstract public class AbstractASTParser extends lr_parser {

	private PHPModuleDeclaration program = new PHPModuleDeclaration(0, 0,
			new LinkedList<Statement>(), new LinkedList<ASTError>(),
			new LinkedList<VarComment>());
	private IProblemReporter problemReporter;
	private String fileName;

	/**
	 * This is a place holder for statements that were found after unclosed
	 * classes
	 */
	public Statement pendingStatement = null;

	/** This is a latest non-bracketed namespace declaration */
	public NamespaceDeclaration currentNamespace = null;

	/** Whether we've met the unbracketed namespace declaration in this file */
	public boolean metUnbracketedNSDecl;

	/** Whether we've met the bracketed namespace declaration in this file */
	public boolean metBracketedNSDecl;

	/** Top declarations stack */
	public Stack<Statement> declarations = new Stack<Statement>();

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

	protected void reportError(IProblemReporter problemReporter,
			String fileName, int start, int end, int lineNumber, String message) {
		DefaultProblem problem = new DefaultProblem(fileName, message,
				IProblem.Syntax, new String[0], ProblemSeverities.Error, start,
				end, lineNumber);
		problemReporter.reportProblem(problem);
	}

	/**
	 * Report on errors that will be added to the AST as statements
	 */
	public void reportError() {
		program.setHasErrors(true);
	}

	public void reportError(ASTError error) {
		reportError(error, null);
	}

	/**
	 * Reporting an error that cannot be added as a statement and has to be in a
	 * separated list.
	 * 
	 * @param error
	 */
	public void reportError(ASTError error, String message) {
		getErrors().add(error);
		reportError();

		if (message != null && problemReporter != null && fileName != null) {
			int lineNumber = ((AstLexer) getScanner()).getCurrentLine();
			reportError(problemReporter, fileName, error.sourceStart(), error
					.sourceEnd(), lineNumber, message);
		}
	}

	public void addStatement(Statement s) {
		int kind = s.getKind();
		if (kind != ASTNodeKinds.EMPTY_STATEMENT
				&& kind != ASTNodeKinds.DECLARE_STATEMENT
				&& kind != ASTNodeKinds.NAMESPACE_DECLARATION
				&& metBracketedNSDecl) {
			reportError(new ASTError(s.sourceStart(), s.sourceEnd()),
					Messages.AbstractASTParser_0);
		}

		if (currentNamespace != null && currentNamespace != s) {
			currentNamespace.addStatement(s);
		} else {
			program.addStatement(s);
		}
	}

	public PHPModuleDeclaration getModuleDeclaration() {
		return program;
	}

	public void report_error(String message, Object info) {
		if (info instanceof Symbol) {
			if (((Symbol) info).left != -1) {
				ASTError error = new ASTError(((Symbol) info).left,
						((Symbol) info).right);
				reportError(error);
			} else {
				reportError(new ASTError(0, 1));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void unrecovered_syntax_error(Symbol cur_token)
			throws java.lang.Exception {
		// in case
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

		// Set end offset of recovered class/interface node to the end of file
		if (statements.size() > 0) {
			Statement lastStatement = statements.get(statements.size() - 1);
			if (lastStatement instanceof IRecoverable) {
				IRecoverable recoverable = (IRecoverable) lastStatement;
				if (recoverable.isRecovered()) {
					lastStatement.setEnd(end);
				}
			}
		}

		super.unrecovered_syntax_error(cur_token);
	}

	public void syntax_error(Symbol cur_token) {
		super.syntax_error(cur_token);

		if (fileName == null || problemReporter == null) {
			return;
		}

		int state = ((Symbol) stack.peek()).parse_state;

		short[] rowOfProbe = action_tab[state];
		int startPosition = cur_token.left;
		int endPosition = cur_token.right;
		int lineNumber = ((AstLexer) getScanner()).getCurrentLine();

		StringBuilder errorMessage = new StringBuilder(Messages.AbstractASTParser_1);

		// current token can be either null, string or phpdoc - according to
		// this resolve:
		String currentText = cur_token.value instanceof String ? (String) cur_token.value
				: null;
		if (currentText == null || currentText.length() == 0) {
			currentText = getTokenName(cur_token.sym);
		}
		if (currentText != null && currentText.length() > 0) {
			if (currentText.equals(";")) { // This means EOF, since it's //$NON-NLS-1$
				// substituted by the lexer
				// explicitly.
				currentText = "EOF"; //$NON-NLS-1$
			}
			endPosition = startPosition + currentText.length();
			errorMessage.append(Messages.AbstractASTParser_4).append(currentText).append(
					'\'');
		}

		if (rowOfProbe.length <= 6) {
			errorMessage.append(Messages.AbstractASTParser_5);
			boolean first = true;
			for (int probe = 0; probe < rowOfProbe.length; probe += 2) {
				String tokenName = getTokenName(rowOfProbe[probe]);
				if (tokenName != null) {
					if (!first) {
						errorMessage.append(Messages.AbstractASTParser_6);
					}
					errorMessage.append('\'').append(tokenName).append('\'');
					first = false;
				}
			}
		}

		reportError(problemReporter, fileName, startPosition, endPosition,
				lineNumber, errorMessage.toString());
	}

	protected abstract String getTokenName(int token);

	public void report_fatal_error(String message, Object info)
			throws java.lang.Exception {
		/* stop parsing (not really necessary since we throw an exception, but) */
		done_parsing();

		/* use the normal error message reporting to put out the message */
		// report_error(message, info);

		// throw new Exception("Can't recover from previous error(s)");
	}

	public void addDeclarationStatement(Statement s) {
		if (declarations.isEmpty()) {
			if (s.getKind() == ASTNodeKinds.NAMESPACE_DECLARATION) {
				if (program.getStatements().size() > 0 && !metBracketedNSDecl
						&& !metUnbracketedNSDecl) {
					boolean justDeclarationNodes = true;
					for (Object statement : program.getStatements()) {
						if (((Statement) statement).getKind() != ASTNodeKinds.DECLARE_STATEMENT) {
							justDeclarationNodes = false;
							break;
						}
					}
					if (!justDeclarationNodes) {
						reportError(
								new ASTError(s.sourceStart(), s.sourceEnd()),
								Messages.AbstractASTParser_7);
					}
				}
			}

			// we don't add top level statements to the program node this way
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
		}
		block.addStatement(s);
		block.setEnd(s.sourceEnd());
	}
}
