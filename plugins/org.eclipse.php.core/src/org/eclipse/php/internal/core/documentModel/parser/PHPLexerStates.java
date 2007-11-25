package org.eclipse.php.internal.core.documentModel.parser;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;


public class PHPLexerStates {

	final private static int BASE = 100;

	final public static int ST_PHP_BACKQUOTE = 101;
	final public static int ST_PHP_COMMENT = 102;
	final public static int ST_PHP_DOC_COMMENT = 103;
	final public static int ST_PHP_DOUBLE_QUOTES = 104;
	final public static int ST_PHP_HEREDOC = 105;
	final public static int ST_PHP_IN_SCRIPTING = 106;
	final public static int ST_PHP_LINE_COMMENT = 107;
	final public static int ST_PHP_SINGLE_QUOTE = 108;

	private static final int SIZE = 100;
	private static int[] unifiedToVersioned4 = new int[SIZE];
	private static int[] versionedToUnified4 = new int[SIZE];
	private static int[] unifiedToVersioned5 = new int[SIZE];
	private static int[] versionedToUnified5 = new int[SIZE];

	static{
		buildHash();
	}
	public static int toUniversalState(IProject project, int state) {
		assert (state < BASE) : "State provided is not version dependent state";

		if (state == -1) {
			return -1;
		}
		final String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		if (phpVersion.equals(PHPCoreConstants.PHP5)) {
			return versionedToUnified5[state];
		}

		return versionedToUnified4[state];
	}

	public static int toSpecificVersionState(IProject project, int state) {
		assert (state >= BASE) : "State provided is not universal state";
		final String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		if (phpVersion.equals(PHPCoreConstants.PHP5)) {
			return unifiedToVersioned5[state - BASE];
		}

		return unifiedToVersioned4[state - BASE];
	}

	private static void buildHash(){
		//PHP 5 
		unifiedToVersioned5[ST_PHP_BACKQUOTE - BASE] = PhpLexer5.ST_PHP_BACKQUOTE;
		unifiedToVersioned5[ST_PHP_COMMENT - BASE] = PhpLexer5.ST_PHP_COMMENT;
		unifiedToVersioned5[ST_PHP_DOC_COMMENT - BASE] = PhpLexer5.ST_PHP_DOC_COMMENT;
		unifiedToVersioned5[ST_PHP_DOUBLE_QUOTES - BASE] = PhpLexer5.ST_PHP_DOUBLE_QUOTES;
		unifiedToVersioned5[ST_PHP_HEREDOC - BASE] = PhpLexer5.ST_PHP_HEREDOC;
		unifiedToVersioned5[ST_PHP_IN_SCRIPTING - BASE] = PhpLexer5.ST_PHP_IN_SCRIPTING;
		unifiedToVersioned5[ST_PHP_LINE_COMMENT - BASE] = PhpLexer5.ST_PHP_LINE_COMMENT;
		unifiedToVersioned5[ST_PHP_SINGLE_QUOTE - BASE] = PhpLexer5.ST_PHP_SINGLE_QUOTE;

		versionedToUnified5[PhpLexer5.ST_PHP_BACKQUOTE] = ST_PHP_BACKQUOTE;
		versionedToUnified5[PhpLexer5.ST_PHP_COMMENT] = ST_PHP_COMMENT;
		versionedToUnified5[PhpLexer5.ST_PHP_DOC_COMMENT] = ST_PHP_DOC_COMMENT;
		versionedToUnified5[PhpLexer5.ST_PHP_DOUBLE_QUOTES] = ST_PHP_DOUBLE_QUOTES;
		versionedToUnified5[PhpLexer5.ST_PHP_HEREDOC] = ST_PHP_HEREDOC;
		versionedToUnified5[PhpLexer5.ST_PHP_IN_SCRIPTING] = ST_PHP_IN_SCRIPTING;
		versionedToUnified5[PhpLexer5.ST_PHP_LINE_COMMENT] = ST_PHP_LINE_COMMENT;
		versionedToUnified5[PhpLexer5.ST_PHP_SINGLE_QUOTE] = ST_PHP_SINGLE_QUOTE;

		//PHP 4
		unifiedToVersioned4[ST_PHP_BACKQUOTE - BASE] = PhpLexer4.ST_PHP_BACKQUOTE;
		unifiedToVersioned4[ST_PHP_COMMENT - BASE] = PhpLexer4.ST_PHP_COMMENT;
		unifiedToVersioned4[ST_PHP_DOC_COMMENT - BASE] = PhpLexer4.ST_PHP_DOC_COMMENT;
		unifiedToVersioned4[ST_PHP_DOUBLE_QUOTES - BASE] = PhpLexer4.ST_PHP_DOUBLE_QUOTES;
		unifiedToVersioned4[ST_PHP_HEREDOC - BASE] = PhpLexer4.ST_PHP_HEREDOC;
		unifiedToVersioned4[ST_PHP_IN_SCRIPTING - BASE] = PhpLexer4.ST_PHP_IN_SCRIPTING;
		unifiedToVersioned4[ST_PHP_LINE_COMMENT - BASE] = PhpLexer4.ST_PHP_LINE_COMMENT;
		unifiedToVersioned4[ST_PHP_SINGLE_QUOTE - BASE] = PhpLexer4.ST_PHP_SINGLE_QUOTE;		
		
		versionedToUnified4[PhpLexer4.ST_PHP_BACKQUOTE] = ST_PHP_BACKQUOTE;
		versionedToUnified4[PhpLexer4.ST_PHP_COMMENT] = ST_PHP_COMMENT;
		versionedToUnified4[PhpLexer4.ST_PHP_DOC_COMMENT] = ST_PHP_DOC_COMMENT;
		versionedToUnified4[PhpLexer4.ST_PHP_DOUBLE_QUOTES] = ST_PHP_DOUBLE_QUOTES;
		versionedToUnified4[PhpLexer4.ST_PHP_HEREDOC] = ST_PHP_HEREDOC;
		versionedToUnified4[PhpLexer4.ST_PHP_IN_SCRIPTING] = ST_PHP_IN_SCRIPTING;
		versionedToUnified4[PhpLexer4.ST_PHP_LINE_COMMENT] = ST_PHP_LINE_COMMENT;
		versionedToUnified4[PhpLexer4.ST_PHP_SINGLE_QUOTE] = ST_PHP_SINGLE_QUOTE;
	}
}
