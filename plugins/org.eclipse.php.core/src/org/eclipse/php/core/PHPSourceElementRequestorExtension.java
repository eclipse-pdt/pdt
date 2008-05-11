package org.eclipse.php.core;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.compiler.ISourceElementRequestor;

public class PHPSourceElementRequestorExtension extends ASTVisitor {

	private String filename;
	private char[] contents;
	protected ISourceElementRequestor fRequestor;

	public PHPSourceElementRequestorExtension() {
	}

	public void setRequestor(ISourceElementRequestor requesor) {
		fRequestor = requesor;
	}
	
	public void setContents(char[] contents) {
		this.contents = contents;
	}

	public char[] getContents() {
		return contents;
	}

	public void setFilename(String file) {
		this.filename = file;
	}

	public String getFilename() {
		return filename;
	}
}
