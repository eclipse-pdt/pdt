package org.eclipse.php.internal.core.ast.parser;


public interface IDocumentorLexer {
	
	public Object parse ();
	
	public void reset(java.io.Reader  reader, char[] buffer, int[] parameters);
	
	public int[] getParamenters();
	
    public char[] getBuffer();

}
