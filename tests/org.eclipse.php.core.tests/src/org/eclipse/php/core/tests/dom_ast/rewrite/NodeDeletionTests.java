/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.dom_ast.rewrite;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.ArrayCreation;
import org.eclipse.php.core.ast.nodes.Assignment;
import org.eclipse.php.core.ast.nodes.Block;
import org.eclipse.php.core.ast.nodes.BreakStatement;
import org.eclipse.php.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.core.ast.nodes.ContinueStatement;
import org.eclipse.php.core.ast.nodes.EchoStatement;
import org.eclipse.php.core.ast.nodes.ExpressionStatement;
import org.eclipse.php.core.ast.nodes.ForStatement;
import org.eclipse.php.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.core.ast.nodes.IfStatement;
import org.eclipse.php.core.ast.nodes.ListVariable;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.ReturnStatement;
import org.eclipse.php.core.ast.nodes.Statement;
import org.eclipse.php.core.ast.nodes.SwitchStatement;
import org.eclipse.php.core.ast.visitor.ApplyAll;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;

public class NodeDeletionTests {

	@Test
	public void variable() throws Exception {
		String str = "<?php $a; $A; ?>";
		String expected = "<?php ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				List<? extends ASTNode> allOfType = getAllOfType(program, ExpressionStatement.class);
				for (ASTNode node : allOfType) {
					node.delete();
				}
			}
		});
	}

	@Test
	public void functionInvocationWithParamsFirst() throws Exception {
		String str = "<?php $foo($a, 's<>&', 12, true, __CLASS__); ?>";
		String expected = "<?php $foo('s<>&', 12, true, __CLASS__); ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				FunctionInvocation functionInvocation = (FunctionInvocation) statement.getExpression();
				functionInvocation.parameters().remove(0);
			}
		});
	}

	@Test
	public void functionInvocationWithParamsLast() throws Exception {
		String str = "<?php $foo($a, 's<>&', 12, true, __CLASS__); ?>";
		String expected = "<?php $foo($a, 's<>&', 12, true); ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				FunctionInvocation functionInvocation = (FunctionInvocation) statement.getExpression();
				functionInvocation.parameters().remove(4);
			}
		});
	}

	@Test
	public void functionInvocationWithParamsMiddle() throws Exception {
		String str = "<?php $foo($a, 's<>&', 12, true, __CLASS__); ?>";
		String expected = "<?php $foo($a, 's<>&', true, __CLASS__); ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				FunctionInvocation functionInvocation = (FunctionInvocation) statement.getExpression();
				functionInvocation.parameters().remove(2);
			}
		});
	}

	@Test
	public void classRemove() throws Exception {
		String str = "<?php $a = 5; class A { } ?>";
		String expected = "<?php $a = 5; ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				Statement statement = program.statements().get(1);
				statement.delete();
			}
		});
	}

	@Test
	public void statementBeforeClass() throws Exception {
		String str = "<?php $a = 5; class A { } ?>";
		String expected = "<?php class A { } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				Statement statement = program.statements().get(0);
				statement.delete();
			}
		});
	}

	@Test
	public void arrayFirst() throws Exception {
		String str = "<?php array (0, 1, 2, 3) ?>";
		String expected = "<?php array (1, 2, 3) ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				ArrayCreation expression = (ArrayCreation) statement.getExpression();
				/* ArrayElement arrayElement = */expression.elements().remove(0);
			}
		});
	}

	@Test
	public void arrayLast() throws Exception {
		String str = "<?php array (0, 1, 2, 3) ?>";
		String expected = "<?php array (0, 1, 2) ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				ArrayCreation expression = (ArrayCreation) statement.getExpression();
				/* ArrayElement arrayElement = */expression.elements().remove(3);
			}
		});
	}

	@Test
	public void arrayMiddle() throws Exception {
		String str = "<?php array (0, 1, 2, 3) ?>";
		String expected = "<?php array (0, 1, 3) ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				ArrayCreation expression = (ArrayCreation) statement.getExpression();
				/* ArrayElement arrayElement = */expression.elements().remove(2);
			}
		});
	}

	@Test
	public void deleteArrayKeyValue() throws Exception {
		String str = "<?php array('Dodo'=>'Golo','Dafna'=>'Dodidu');?>";
		String expected = "<?php array('Dafna'=>'Dodidu');?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				ArrayCreation expression = (ArrayCreation) statement.getExpression();
				/* ArrayElement arrayElement = */expression.elements().remove(0);
			}
		});
	}

	@Test
	public void listFirst() throws Exception {
		String str = "<?php list($a, $b, $c, $d) = array () ?>";
		String expected = "<?php list($b, $c, $d) = array () ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				Assignment expression = (Assignment) statement.getExpression();
				ListVariable list = (ListVariable) expression.getLeftHandSide();
				list.variables().remove(0);
			}
		});
	}

	@Test
	public void listMiddle() throws Exception {
		String str = "<?php list($a, $b, $c, $d)  = array ()  ?>";
		String expected = "<?php list($a, $b, $d)  = array ()  ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				Assignment expression = (Assignment) statement.getExpression();
				ListVariable list = (ListVariable) expression.getLeftHandSide();
				list.variables().remove(2);
			}
		});
	}

	@Test
	public void listLast() throws Exception {
		String str = "<?php list ($a, $b, $c, $d)  = array ()  ?>";
		String expected = "<?php list ($a, $b, $c)  = array ()  ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ExpressionStatement statement = (ExpressionStatement) program.statements().get(0);
				Assignment expression = (Assignment) statement.getExpression();
				ListVariable list = (ListVariable) expression.getLeftHandSide();
				list.variables().remove(3);
			}
		});
	}

	@Test
	public void deleteBreak() throws Exception {
		String str = "<?php break $a;?>";
		String expected = "<?php break;?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				BreakStatement statement = (BreakStatement) program.statements().get(0);
				statement.getExpression().delete();
			}
		});
	}

	@Test
	public void deleteContinue() throws Exception {
		String str = "<?php continue $a;?>";
		String expected = "<?php continue;?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ContinueStatement statement = (ContinueStatement) program.statements().get(0);
				statement.getExpression().delete();
			}
		});
	}

	@Test
	public void deleteReturn() throws Exception {
		String str = "<?php return $a;?>";
		String expected = "<?php return;?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ReturnStatement statement = (ReturnStatement) program.statements().get(0);
				statement.getExpression().delete();
			}
		});
	}

	@Test
	public void deleteEchoFirst() throws Exception {
		String str = "<?php echo $a, $b , $c; ?>";
		String expected = "<?php echo $b , $c; ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				EchoStatement statement = (EchoStatement) program.statements().get(0);
				statement.expressions().remove(0);
			}
		});
	}

	@Test
	public void deleteEchoLast() throws Exception {
		String str = "<?php echo $a, $b , $c; ?>";
		String expected = "<?php echo $a, $b; ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				EchoStatement statement = (EchoStatement) program.statements().get(0);
				statement.expressions().remove(2);
			}
		});
	}

	@Test
	public void deleteEchoMiddle() throws Exception {
		String str = "<?php echo $a, $b , $c; ?>";
		String expected = "<?php echo $a, $c; ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				EchoStatement statement = (EchoStatement) program.statements().get(0);
				statement.expressions().remove(1);
			}
		});
	}

	@Test
	public void deleteSwitch() throws Exception {
		String str = "<?php switch ($i) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		String expected = "<?php switch ($i) { case 0:    echo 'i equals 0';    break; default:    echo 'i not equals 0,1';  }  ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				SwitchStatement statement = (SwitchStatement) program.statements().get(0);
				statement.getBody().statements().remove(1);
			}
		});
	}

	@Test
	public void deleteBlockFirst() throws Exception {
		String str = "<?php if ($a) { $a = 5; $b = 4; $c = 4; }  ?>";
		String expected = "<?php if ($a) { $b = 4; $c = 4; }  ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				IfStatement statement = (IfStatement) program.statements().get(0);
				Block block = (Block) statement.getTrueStatement();
				block.statements().remove(0);
			}
		});
	}

	@Test
	public void deleteBlockMiddle() throws Exception {
		String str = "<?php if ($a) { $a = 5; $b = 4; }  ?>";
		String expected = "<?php if ($a) { $a = 5; }  ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				IfStatement statement = (IfStatement) program.statements().get(0);
				Block block = (Block) statement.getTrueStatement();
				block.statements().remove(1);
			}
		});
	}

	@Test
	public void deleteBlockLast() throws Exception {
		String str = "<?php if ($a) { $a = 5; $b = 4; $c = 4;}  ?>";
		String expected = "<?php if ($a) { $a = 5; $b = 4;}  ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				IfStatement statement = (IfStatement) program.statements().get(0);
				Block block = (Block) statement.getTrueStatement();
				block.statements().remove(2);
			}
		});
	}

	@Test
	public void deleteForComponent1() throws Exception {
		String str = "<?php for ($i = 1; $i <= 10; $i++) {  echo $i; } ?>";
		String expected = "<?php for (;;) {  echo $i; } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ForStatement statement = (ForStatement) program.statements().get(0);
				statement.initializers().remove(0);
				statement.conditions().remove(0);
				statement.updaters().remove(0);
			}
		});
	}

	// @Test public void deleteHtml() throws Exception {
	// String str = "<html> <?php ?></html> <?php ?> </html> ";
	// String expected = "<?php ?><?php ?>";
	// parseAndCompare(str, expected, new ICodeManiplator() {
	// public void manipulate(Program program) {
	// InLineHtml statement = (InLineHtml) program.statements().get(4);
	// statement.delete();
	// statement = (InLineHtml) program.statements().get(2);
	// statement.delete();
	// statement = (InLineHtml) program.statements().get(0);
	// statement.delete();
	// }
	// });
	// }

	@Test
	public void deleteFunctionFormalFirst() throws Exception {
		String str = "<?php function foo($a, $b, $c = 5) { $a= 5; $b = 6; $c = 7; } ?>";
		String expected = "<?php function foo($b, $c = 5) { $a= 5; $b = 6; $c = 7; } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				FunctionDeclaration statement = (FunctionDeclaration) program.statements().get(0);
				statement.formalParameters().remove(0);
			}
		});
	}

	@Test
	public void deleteFunctionFormalLast() throws Exception {
		String str = "<?php function foo($a, $b, $c = 5) { $a= 5; $b = 6; $c = 7; } ?>";
		String expected = "<?php function foo($a, $b) { $a= 5; $b = 6; $c = 7; } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				FunctionDeclaration statement = (FunctionDeclaration) program.statements().get(0);
				statement.formalParameters().remove(2);
			}
		});
	}

	@Test
	public void deleteFunctionFormalMiddle() throws Exception {
		String str = "<?php function foo($a, $b, $c = 5) { $a= 5; $b = 6; $c = 7; } ?>";
		String expected = "<?php function foo($a, $c = 5) { $a= 5; $b = 6; $c = 7; } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				FunctionDeclaration statement = (FunctionDeclaration) program.statements().get(0);
				statement.formalParameters().remove(1);
			}
		});
	}

	@Test
	public void deleteFunctionBodyFirst() throws Exception {
		String str = "<?php function foo() { $a= 5; $b = 6; $c = 7; } ?>";
		String expected = "<?php function foo() { $b = 6; $c = 7; } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				FunctionDeclaration statement = (FunctionDeclaration) program.statements().get(0);
				statement.getBody().statements().remove(0);
			}
		});
	}

	@Test
	public void deleteFunctionBodyLast() throws Exception {
		String str = "<?php function foo() { $a= 5; $b = 6; $c = 7; } ?>";
		String expected = "<?php function foo() { $a= 5; $b = 6; } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				FunctionDeclaration statement = (FunctionDeclaration) program.statements().get(0);
				statement.getBody().statements().remove(2);
			}
		});
	}

	@Test
	public void deleteFunctionBodyMiddle() throws Exception {
		String str = "<?php function foo() { $a= 5; $b = 6; $c = 7; } ?>";
		String expected = "<?php function foo() { $a= 5; $c = 7; } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				FunctionDeclaration statement = (FunctionDeclaration) program.statements().get(0);
				statement.getBody().statements().remove(1);
			}
		});
	}

	@Test
	public void deleteClassElements() throws Exception {
		String str = "<?php final class MyClass extends SuperClass implements Interface1, Interface2 { const MY_CONSTANT = 3; public static final $myVar = 5, $yourVar; var $anotherOne; private function myFunction(MyClass $a, $b = 6) { }  } ?>";
		String expected = "<?php final class MyClass extends SuperClass {  } ?>";
		parseAndCompare(str, expected, new ICodeManiplator() {
			public void manipulate(Program program) {
				ClassDeclaration statement = (ClassDeclaration) program.statements().get(0);
				statement.interfaces().remove(1);
				statement.interfaces().remove(0);
				statement.getBody().statements().remove(3);
				statement.getBody().statements().remove(2);
				statement.getBody().statements().remove(1);
				statement.getBody().statements().remove(0);
			}
		});
	}

	/**
	 * @param reader
	 *            stringReader of inputstream
	 * @param goldenName
	 * @param str
	 * @throws Exception
	 */
	public void parseAndCompare(String string, String expected, ICodeManiplator manipulator) throws Exception {
		IDocument document = new Document(string);
		Program program = initialize(document);

		manipulator.manipulate(program);
		rewrite(program, document);

		String actual = document.get();
		String diff = TestUtils.compareContentsIgnoreWhitespace(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}

	/**
	 * Set the content into the document and initialize the parser, the program
	 * and the ast.
	 */
	private Program initialize(IDocument document) throws Exception {
		ASTParser parser = ASTParser.newParser(getPHPVersion(), ProjectOptions.useShortTags((IProject) null));
		parser.setSource(document.get().toCharArray());
		Program program = parser.createAST(new NullProgressMonitor());

		program.recordModifications();
		return program;
	}

	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5;
	}

	private void rewrite(Program program, IDocument document) throws Exception {
		TextEdit edits = program.rewrite(document, null);
		edits.apply(document);
	}

	public <T extends ASTNode> List<T> getAllOfType(Program program, final Class<T> nodeClass) {
		final List<T> list = new ArrayList<T>();
		program.accept(new ApplyAll() {
			@SuppressWarnings("unchecked")
			protected boolean apply(ASTNode node) {
				if (node.getClass() == nodeClass) {
					list.add((T) node);
				}
				return true;
			}
		});
		return list;
	}

}
