package org.eclipse.php.core;

import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.SourceElementRequestVisitor;

public class PHPSourceElementRequestorExtension extends SourceElementRequestVisitor {

	private String filename;
	private char[] contents;

	public PHPSourceElementRequestorExtension() {
		super(null);
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
