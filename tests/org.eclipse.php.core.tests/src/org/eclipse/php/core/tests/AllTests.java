package org.eclipse.php.core.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.php.core.tests.ast_rewrite.ASTRewriteTests;
import org.eclipse.php.core.tests.binding.BindingTests;
import org.eclipse.php.core.tests.codeassist.CodeAssistTests;
import org.eclipse.php.core.tests.compiler_parser.CompilerParserTests;
import org.eclipse.php.core.tests.dom_parser.DomParserTests;
import org.eclipse.php.core.tests.errors.ErrorReportingTests;
import org.eclipse.php.core.tests.filenetwork.FileNetworkTests;
import org.eclipse.php.core.tests.formatter.FormatterTests;
import org.eclipse.php.core.tests.includepath.IncludePathManagerTests;
import org.eclipse.php.core.tests.mixin.MixinTests;
import org.eclipse.php.core.tests.model_structure.ModelStructureTests;
import org.eclipse.php.core.tests.phpdoc_parser.PHPDocAwareDeclarationTests;
import org.eclipse.php.core.tests.phpdoc_parser.PHPDocParserTests;
import org.eclipse.php.core.tests.search.SearchTests;
import org.eclipse.php.core.tests.selection.SelectionEngineTests;
import org.eclipse.php.core.tests.typeinference.TypeInferenceTests;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.php.core");

		// $JUnit-BEGIN$
		
		// Model tests:
		suite.addTest(DomParserTests.suite());
		suite.addTest(CompilerParserTests.suite());
		suite.addTest(ModelStructureTests.suite());
		suite.addTest(CodeAssistTests.suite());
		suite.addTest(SelectionEngineTests.suite());
		suite.addTest(MixinTests.suite());
		suite.addTest(SearchTests.suite());
		suite.addTest(FileNetworkTests.suite());
		suite.addTest(TypeInferenceTests.suite());
		suite.addTest(PHPDocParserTests.suite());
		suite.addTest(PHPDocAwareDeclarationTests.suite());
		suite.addTest(IncludePathManagerTests.suite());
		suite.addTest(ErrorReportingTests.suite());
		suite.addTest(BindingTests.suite());
		suite.addTest(ASTRewriteTests.suite());
		
		// Document tests:
		suite.addTest(FormatterTests.suite());
		
		// $JUnit-END$
		
		return suite;
	}
}
