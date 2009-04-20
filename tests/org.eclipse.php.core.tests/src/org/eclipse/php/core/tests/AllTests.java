package org.eclipse.php.core.tests;

import org.eclipse.php.core.tests.codeassist.CodeAssistTests;
import org.eclipse.php.core.tests.compiler_parser.CompilerParserTests;
import org.eclipse.php.core.tests.dom_parser.DomParserTests;
import org.eclipse.php.core.tests.filenetwork.FileNetworkTests;
import org.eclipse.php.core.tests.includepath.IncludePathManagerTests;
import org.eclipse.php.core.tests.mixin.MixinTests;
import org.eclipse.php.core.tests.model_structure.ModelStructureTests;
import org.eclipse.php.core.tests.phpdoc_parser.PHPDocAwareDeclarationTests;
import org.eclipse.php.core.tests.phpdoc_parser.PHPDocParserTests;
import org.eclipse.php.core.tests.search.SearchTests;
import org.eclipse.php.core.tests.selection.SelectionEngineTests;
import org.eclipse.php.core.tests.typeinference.TypeInferenceTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.php.core");

		// $JUnit-BEGIN$
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

		// $JUnit-END$
		
		return suite;
	}
}
