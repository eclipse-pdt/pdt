package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PHPModuleDeclaration extends ModuleDeclaration {

	private List<ASTError> errors;
	private boolean hasErros;

	public PHPModuleDeclaration(int start, int end, List<Statement> statements, List<ASTError> errors) {
		super(end - start);
		setStatements(statements);
		setStart(start);
		setEnd(end);
		this.errors = errors;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}

	/**
	 * due to the nature of the parser and the error recovery method not all errors can be added to the 
	 * AST as statements, the error list is made for those errors.
	 */
	public List<ASTError> getErrors() {
		return errors;
	}

	public List<ASTError> getAllErrors() {
		ErrorSearcher searcher = new ErrorSearcher();
		try {
			traverse(searcher);
		} catch (Exception e) {
		}
		List<ASTError> errorsList = searcher.getErrors();
		errorsList.addAll(getErrors());
		return errorsList;
	}

	public boolean hasErros() {
		return hasErros || !errors.isEmpty();
	}

	public void setHasErros(boolean hasErros) {
		this.hasErros = hasErros;
	}
	
	private class ErrorSearcher extends ASTVisitor{
		private List<ASTError> errors = new LinkedList<ASTError>();

		public boolean visit(ASTError error) throws Exception {
			errors.add(error);
			return false;
		}

		public boolean visit(Statement s) throws Exception {
			if(s.getKind() == ASTNodeKinds.AST_ERROR){
				return visit((ASTError)s);
			}
			return super.visit(s);
		}

		public List<ASTError> getErrors() {
			return errors;
		}
	}
}
