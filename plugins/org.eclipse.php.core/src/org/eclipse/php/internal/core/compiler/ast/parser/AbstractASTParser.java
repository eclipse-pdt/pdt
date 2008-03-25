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
import org.eclipse.php.internal.core.compiler.ast.nodes.ASTError;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPModuleDeclaration;

abstract public class AbstractASTParser extends lr_parser {

	private PHPModuleDeclaration program = new PHPModuleDeclaration(0, 0, new LinkedList<Statement>(), new LinkedList<ASTError>());

	public AbstractASTParser() {
		super();
	}

	public AbstractASTParser(Scanner s) {
		super(s);
	}

	List<ASTError> getErrors() {
		return program.getErrors();
	}

	/**
	 * tReport on errors that will be added to the AST as statements
	 */
	void reportError() {
		program.setHasErros(true);
	}

	/**
	 * Reporting an error that cannot be added as a statement and has to be in a separated list.
	 * @param error
	 */
	void reportError(ASTError error) {
		getErrors().add(error);
		reportError();
	}

	List<Statement> getStatements() {
		return program.getStatements();
	}

	void addStatement(Statement s) {
		getStatements().add(s);
	}

	PHPModuleDeclaration getModuleDeclaration() {
		return program;
	}

	Stack<Statement> declarations = new Stack<Statement>();

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

	public void report_fatal_error(String message, Object info) throws java.lang.Exception {
		/* stop parsing (not really necessary since we throw an exception, but) */
		done_parsing();

		/* use the normal error message reporting to put out the message */
		// report_error(message, info);
		/* throw an exception */
		// throw new Exception("Can't recover from previous error(s)");
	}

	/* This is a place holder for statements that were found after unclosed classes */
	Statement pendingStatement = null;

	void addDeclarationStatement(Statement s) {
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
