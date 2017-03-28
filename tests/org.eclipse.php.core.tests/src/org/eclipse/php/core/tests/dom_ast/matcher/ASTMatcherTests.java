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
package org.eclipse.php.core.tests.dom_ast.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.match.ASTMatcher;
import org.eclipse.php.core.ast.match.PHPASTMatcher;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.Statement;
import org.eclipse.php.core.project.ProjectOptions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * Tests {@link ASTMatcher}
 * 
 * @author Eden K., 2008
 */
public class ASTMatcherTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	public void performMatching(String matchingStr, String notMatchingStr) throws Exception {

		ASTNode node = getAstNode(matchingStr);
		ASTNode notMatchingNode = getAstNode(notMatchingStr);

		assertTrue(node.subtreeMatch(new PHPASTMatcher(), node));
		assertFalse(node.subtreeMatch(new PHPASTMatcher(), notMatchingNode));

		assertFalse(node.subtreeMatch(new PHPASTMatcher(), new Object()));
		assertFalse(node.subtreeMatch(new PHPASTMatcher(), null));
	}

	private ASTNode getAstNode(String str) throws Exception {
		StringReader reader = new StringReader(str);
		Program program = ASTParser.newParser(reader, PHPVersion.PHP5, ProjectOptions.useShortTags((IProject) null))
				.createAST(new NullProgressMonitor());
		List<Statement> statements = program.statements();

		assertNotNull(statements);
		assertTrue(statements.size() > 0);

		// the statement that we want to test
		return statements.get(0);
	}

	@Test
	public void matchScalar() throws Exception {
		String matchingStr = "<?php 5;?>";
		String notMatchingStr = "<?php 6;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchVariable() throws Exception {
		String matchingStr = "<?php $a;?>";
		String notMatchingStr = "<?php $b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchBackTickExpression() throws Exception {
		String matchingStr = "<?php `$cmd`?>";
		String notMatchingStr = "<?php `$cmd2`;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchFunctionInvocation() throws Exception {
		String matchingStr = "<?php foo(); ?>";
		String notMatchingStr = "<?php bar(); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchFunctionInvocationWithParams() throws Exception {
		String matchingStr = "<?php $foo($a, 's<>&', 12, true, __CLASS__); ?>";
		String notMatchingStr = "<?php $bar($a, 's<>&', 12, true, __CLASS__); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchStaticFunctionInvocation() throws Exception {
		String matchingStr = "<?php A::foo($a); ?>";
		String notMatchingStr = "<?php B::foo($a); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchArrayVariableWithoutIndex() throws Exception {
		String matchingStr = "<?php $a[]; ?>";
		String notMatchingStr = "<?php $b[]; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchArrayVariable() throws Exception {
		String matchingStr = "<?php $a[$b]; ?>";
		String notMatchingStr = "<?php $a[$c]; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchArrayVariableMultiIndex() throws Exception {
		String matchingStr = "<?php $a[$b][5][3]; ?>";
		String notMatchingStr = "<?php $a[$b][2][5]; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchHashTableVariable() throws Exception {
		String matchingStr = "<?php $a{'name'}; ?>";
		String notMatchingStr = "<?php $a{'test'}; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchListVariable() throws Exception {
		String matchingStr = "<?php list($a,$b)=1; ?>";
		String notMatchingStr = "<?php list($a,$c)=1; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchNestedListVariable() throws Exception {
		String matchingStr = "<?php list($a, list($b,$c))=1;?>";
		String notMatchingStr = "<?php list($b, list($b,$c))=1;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchAssignment() throws Exception {
		String matchingStr = "<?php $a = 1;?>";
		String notMatchingStr = "<?php $b = 1;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchPlusAssignment() throws Exception {
		String matchingStr = "<?php $a += 1;?>";
		String notMatchingStr = "<?php $a += 2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchSLAssignment() throws Exception {
		String matchingStr = "<?php $a <<= 1;?>";
		String notMatchingStr = "<?php $b <<= 1;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchReflectionSimple() throws Exception {
		String matchingStr = "<?php $$a;?>";
		String notMatchingStr = "<?php $$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchReflectionNested() throws Exception {
		String matchingStr = "<?php $$$$$foo;?>";
		String notMatchingStr = "<?php $$$$$bar;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchReflectionFunction() throws Exception {
		String matchingStr = "<?php $$$foo();?>";
		String notMatchingStr = "<?php $$$bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchReflectionComplex() throws Exception {
		String matchingStr = "<?php ${\"var\"};?>";
		String notMatchingStr = "<?php ${\"foo\"};?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchStaticMemberSimple() throws Exception {
		String matchingStr = "<?php MyClass::$a;?>";
		String notMatchingStr = "<?php MyClass::$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchStaticMemberWithArray() throws Exception {
		String matchingStr = "<?php MyClass::$$a[5];?>";
		String notMatchingStr = "<?php MyClass::$$a[3];?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchDispatchSimple() throws Exception {
		String matchingStr = "<?php $a->$b;?>";
		String notMatchingStr = "<?php $c->$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchDispatchNested() throws Exception {
		String matchingStr = "<?php $myClass->foo()->bar();?>";
		String notMatchingStr = "<?php $myClass->foo()->bar2();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchDispatchWithStaticCall() throws Exception {
		String matchingStr = "<?php MyClass::$a->foo();?>";
		String notMatchingStr = "<?php MyClass::$a->bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchDispatchNestedWithStaticCall() throws Exception {
		String matchingStr = "<?php MyClass::$a->$b->foo();?>";
		String notMatchingStr = "<?php MyClass::$a->$b->bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchClone() throws Exception {
		String matchingStr = "<?php clone $a; ?>";
		String notMatchingStr = "<?php clone $b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchCastOfVariable() throws Exception {
		String matchingStr = "<?php (int) $a; ?>";
		String notMatchingStr = "<?php (int) $b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchCastOfDispatch() throws Exception {
		String matchingStr = "<?php (string) $b->foo(); ?>";
		String notMatchingStr = "<?php (string) $b->bar(); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchClassConstant() throws Exception {
		String matchingStr = "<?php $a = MyClass::MY_CONST; ?>";
		String notMatchingStr = "<?php $a = MyClass::MY_CONST_2; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchPostfixSimple() throws Exception {
		String matchingStr = "<?php $a++;?>";
		String notMatchingStr = "<?php $b++;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchPostfixWithFunction() throws Exception {
		String matchingStr = "<?php foo()--;?>";
		String notMatchingStr = "<?php bar()--;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchPrefixSimple() throws Exception {
		String matchingStr = "<?php ++$a;?>";
		String notMatchingStr = "<?php ++$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchPrefixWithFunction() throws Exception {
		String matchingStr = "<?php --foo();?>";
		String notMatchingStr = "<?php --bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchUnaryOperationSimple() throws Exception {
		String matchingStr = "<?php +$a;?>";
		String notMatchingStr = "<?php +$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchUnaryOperationWithFunction() throws Exception {
		String matchingStr = "<?php -foo();?>";
		String notMatchingStr = "<?php -bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchUnaryOperationComplex() throws Exception {
		String matchingStr = "<?php +-+-$b;?>";
		String notMatchingStr = "<?php +-+-$c;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchClassInstanciationSimple() throws Exception {
		String matchingStr = "<?php new MyClass();?>";
		String notMatchingStr = "<?php new MyClass2();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchClassInstanciationVariable() throws Exception {
		String matchingStr = "<?php new $a('start');?>";
		String notMatchingStr = "<?php new $a('end');?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchClassInstanciationFunction() throws Exception {
		String matchingStr = "<?php new $a->$b(1, $a);?>";
		String notMatchingStr = "<?php new $c->$b(1, $c);?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchRefernceSimple() throws Exception {
		String matchingStr = "<?php $b = &$a;?>";
		String notMatchingStr = "<?php $b = &$c;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchRefernceWithFunction() throws Exception {
		String matchingStr = "<?php $g = &$foo();?>";
		String notMatchingStr = "<?php $g = &$bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchRefernceInstanciation() throws Exception {
		String matchingStr = "<?php $b = &new MyClass();?>";
		String notMatchingStr = "<?php $b = &new MyClass2();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchInstanceofSimple() throws Exception {
		String matchingStr = "<?php $a instanceof MyClass;?>";
		String notMatchingStr = "<?php $a instanceof MyClass2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchInstanceofWithFunction() throws Exception {
		String matchingStr = "<?php foo() instanceof $myClass;?>";
		String notMatchingStr = "<?php foo() instanceof $myClass2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchInstanceofDispatch() throws Exception {
		String matchingStr = "<?php $a instanceof $b->$myClass;?>";
		String notMatchingStr = "<?php $a instanceof $b->$myClass2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchIgnoreError() throws Exception {
		String matchingStr = "<?php @$a->foo();?>";
		String notMatchingStr = "<?php @$b->foo();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchInclude() throws Exception {
		String matchingStr = "<?php include('myFile.php');?>";
		String notMatchingStr = "<?php include('myFile2.php');?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchIncludeOnce() throws Exception {
		String matchingStr = "<?php include_once($myFile);?>";
		String notMatchingStr = "<?php include_once($myFile2);?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchRequire() throws Exception {
		String matchingStr = "<?php require($myClass->getFileName());?>";
		String notMatchingStr = "<?php require($myClass2->getFileName());?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchRequireOnce() throws Exception {
		String matchingStr = "<?php require_once(A::FILE_NAME);?>";
		String notMatchingStr = "<?php require_once(A::FILE_NAME2);?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchArray() throws Exception {
		String matchingStr = "<?php array(1,2,3,);?>";
		String notMatchingStr = "<?php array(4,5,6,);?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchArrayKeyValue() throws Exception {
		String matchingStr = "<?php array('Dodo'=>'Golo','Dafna'=>'Dodidu');?>";
		String notMatchingStr = "<?php array('Test'=>'Golo','Test2'=>'Dodidu');?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchArrayComplex() throws Exception {
		String matchingStr = "<?php array($a, $b=>foo(), 1=>$myClass->getFirst());?>";
		String notMatchingStr = "<?php array($a, $b=>foo2(), 1=>$myClass->getFirst2());?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchConditionalExpression() throws Exception {
		String matchingStr = "<?php (bool)$a ? 3 : 4;?>";
		String notMatchingStr = "<?php (bool)$a ? 5 : 6;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchPlusOperation() throws Exception {
		String matchingStr = "<?php $a + 1;?>";
		String notMatchingStr = "<?php $a + 2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchMinusOperation() throws Exception {
		String matchingStr = "<?php 3 - 2;?>";
		String notMatchingStr = "<?php 3 - 1;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchAndOperation() throws Exception {
		String matchingStr = "<?php foo() & $a->bar();?>";
		String notMatchingStr = "<?php foo() & $b->bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchConcatOperation() throws Exception {
		String matchingStr = "<?php 'string'.$c;?>";
		String notMatchingStr = "<?php 'string'.$d;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchQuote() throws Exception {
		String matchingStr = "<?php \"this\nis $a quote\";?>";
		String notMatchingStr = "<?php \"this\nis $b quote2\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchSingleQuote() throws Exception {
		String matchingStr = "<?php \"'single ${$complex->quote()}'\";?>";
		String notMatchingStr = "<?php \"'single ${$complex->quote2()}'\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchQuoteWithOffsetNumber() throws Exception {
		String matchingStr = "<?php \"this\nis $a[6] quote\";?>";
		String notMatchingStr = "<?php \"this\nis $a[7] quote\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchQuoteWithCurly() throws Exception {
		String matchingStr = "<?php $text = <<<EOF\ntest{test}test\nEOF;\n?>";
		String notMatchingStr = "<?php $text = <<<EOF\ntest{test2}test\nEOF;\n?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchQuoteWithOffsetString() throws Exception {
		String matchingStr = "<?php \"this\nis $a[hello] quote\";?>";
		String notMatchingStr = "<?php \"this\nis $a[hello2] quote\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchHeredoc() throws Exception {
		String matchingStr = "<?php <<<Heredoc\n  This is here documents \nHeredoc;\n?>";
		String notMatchingStr = "<?php <<<Heredoc\n  This is here documents2 \nHeredoc;\n?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchEmptyHeredoc() throws Exception {
		String matchingStr = "<?php <<<Heredoc\nHeredoc;\n?>";
		String notMatchingStr = "<?php <<<Heredoc\nHeredoc2;\n?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchNotQuote() throws Exception {
		String matchingStr = "<?php \"This is\".$not.\" a quote node\";?>";
		String notMatchingStr = "<?php \"This is\".$not2.\" a quote node\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchBreakStatement() throws Exception {
		String matchingStr = "<?php break $a;?>";
		String notMatchingStr = "<?php break $b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchContinueStatementExpression() throws Exception {
		String matchingStr = "<?php continue $a;?>";
		String notMatchingStr = "<?php continue $b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchReturnExprStatement() throws Exception {
		String matchingStr = "<?php return $a; ?>";
		String notMatchingStr = "<?php return $b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchEchoStatement() throws Exception {
		String matchingStr = "<?php echo \"hello \",$b;?>";
		String notMatchingStr = "<?php echo \"hello \",$c;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchSwitchStatement() throws Exception {
		String matchingStr = "<?php switch ($i) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		String notMatchingStr = "<?php switch ($j) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchIfStatement() throws Exception {
		String matchingStr = "<?php if ($a > $b) {   echo 'a is bigger than b';} elseif ($a == $b) {   echo 'a is equal to b';} else {   echo 'a is smaller than b';} ?>";
		String notMatchingStr = "<?php if ($a > $c) {   echo 'a is bigger than b';} elseif ($a == $c) {   echo 'a is equal to b';} else {   echo 'a is smaller than b';} ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchEndIfStatement() throws Exception {
		String matchingStr = "<?php if ($a):   echo 'a is bigger than b'; elseif ($a == $b): echo 'a is equal to b'; else: echo 'a is equal to b'; endif; ?>";
		String notMatchingStr = "<?php if ($b):   echo 'a is bigger than b'; elseif ($a == $b): echo 'a is equal to b'; else: echo 'a is equal to b'; endif; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchWhileStatement() throws Exception {
		String matchingStr = "<?php while ($i <= 10) echo $i++; ?>";
		String notMatchingStr = "<?php while ($j <= 10) echo $j++; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchWhileEndStatement() throws Exception {
		String matchingStr = "<?php while ($i <= 10):  echo $i;   $i++; endwhile; ?>";
		String notMatchingStr = "<?php while ($j <= 10):  echo $j;   $j++; endwhile; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchDoWhileStatement() throws Exception {
		String matchingStr = "<?php do { echo $i;} while ($i > 0); ?>";
		String notMatchingStr = "<?php do { echo $j;} while ($j > 0); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchForStatement() throws Exception {
		String matchingStr = "<?php for ($i = 1; $i <= 10; $i++) {  echo $i; } ?>";
		String notMatchingStr = "<?php for ($j = 1; $j <= 10; $j++) {  echo $j; } ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchEndForStatement() throws Exception {
		String matchingStr = "<?php for ($i = 1; $i <= 10; $i++):  echo $i; endfor; ?>";
		String notMatchingStr = "<?php for ($j = 1; $j <= 10; $j++):  echo $j; endfor; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchForStatementNoCondition() throws Exception {
		String matchingStr = "<?php for ($i = 1; ; $i++) { if ($i > 10) {  break;  }  echo $i;} ?>";
		String notMatchingStr = "<?php for ($j = 1; ; $j++) { if ($j > 10) {  break;  }  echo $j;} ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchForStatementNoArgs() throws Exception {
		String matchingStr = "<?php for (; ; ) { if ($i > 10) {   break;  } echo $i;  $i++;} ?>";
		String notMatchingStr = "<?php for (; ; ) { if ($j > 10) {   break;  } echo $j;  $j++;} ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchEmptyForStatement() throws Exception {
		String matchingStr = "<?php for ($i = 1, $j = 0; $i <= 10; $j += $i, print $i, $i++); ?>";
		String notMatchingStr = "<?php for ($j = 1, $k = 0; $j <= 10; $k += $j, print $j, $j++); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchForEachStatement() throws Exception {
		String matchingStr = "<?php foreach ($arr as &$value) { $value = $value * 2; } ?>";
		String notMatchingStr = "<?php foreach ($arr2 as &$value) { $value = $value * 2; } ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchForEachStatementWithValue() throws Exception {
		String matchingStr = "<?php foreach ($arr as $key => $value) { echo \"Key: $key; Value: $value<br />\n\"; } ?>";
		String notMatchingStr = "<?php foreach ($arr2 as $key => $value) { echo \"Key: $key; Value: $value<br />\n\"; } ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchForEachStatementBlocked() throws Exception {
		String matchingStr = "<?php foreach ($arr as &$value): $value = $value * 2;endforeach; ?>";
		String notMatchingStr = "<?php foreach ($arr2 as &$value): $value = $value * 2;endforeach; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchTryCatchStatement() throws Exception {
		String matchingStr = "<?php try { $error = 'Always throw this error'; } catch (Exception $e) { echo ''; }  ?>";
		String notMatchingStr = "<?php try { $error2 = 'Always throw this error'; } catch (Exception $e) { echo ''; }  ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchTryMultiCatchStatement() throws Exception {
		String matchingStr = "<?php try { $error = 'Always throw this error'; } catch (Exception $e) { echo ''; } catch (AnotherException $ea) { echo ''; }  ?>";
		String notMatchingStr = "<?php try { $error2 = 'Always throw this error'; } catch (Exception $e) { echo ''; } catch (AnotherException $ea) { echo ''; }  ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchGlobalStatementSimple() throws Exception {
		String matchingStr = "<?php global $a; ?>";
		String notMatchingStr = "<?php global $b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchGlobalStatementReflection() throws Exception {
		String matchingStr = "<?php global $$a; ?>";
		String notMatchingStr = "<?php global $$b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchGlobalStatementReflectionWithExpression() throws Exception {
		String matchingStr = "<?php global ${foo()->bar()}; ?>";
		String notMatchingStr = "<?php global ${foo2()->bar()}; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchGlobalStatementMultiple() throws Exception {
		String matchingStr = "<?php global $a, $b; ?>";
		String notMatchingStr = "<?php global $c, $d; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchStaticSimple() throws Exception {
		String matchingStr = "<?php static $a;?>";
		String notMatchingStr = "<?php static $b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchStaticMultiWithAssignment() throws Exception {
		String matchingStr = "<?php static $a, $b=6;?>";
		String notMatchingStr = "<?php static $b, $c=6;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	// @Test public void matchInLineHtml() throws Exception {
	// String matchingStr = "<html> </html>";
	// String notMatchingStr = "<body> </body>";
	// performMatching(matchingStr, notMatchingStr);
	// }
	//
	// @Test public void matchInLineHtmlWithPHP() throws Exception {
	// String matchingStr = "<html> <?php ?> </html> <?php ?>";
	// String notMatchingStr = "<body> <?php ?> </body> <?php ?>";
	// performMatching(matchingStr, notMatchingStr);
	// }

	@Test
	public void matchDeclareSimple() throws Exception {
		String matchingStr = "<?php declare(ticks=1) { };?> ";
		String notMatchingStr = "<?php declare(ticks2=1) { };?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchEmptyDeclare() throws Exception {
		String matchingStr = "<?php declare(ticks=1);?> ";
		String notMatchingStr = "<?php declare(ticks2=1);?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchDeclareWithStatement() throws Exception {
		String matchingStr = "<?php declare(ticks=2) { static $a; }; ?> ";
		String notMatchingStr = "<?php declare(ticks2=2) { static $a; }; ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchFunctionDeclaration() throws Exception {
		String matchingStr = "<?php function foo() {} ?> ";
		String notMatchingStr = "<?php function bar() {} ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchFunctionDeclarationReference() throws Exception {
		String matchingStr = "<?php function &foo() {} ?> ";
		String notMatchingStr = "<?php function &bar() {} ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchFunctionDeclarationWithParams() throws Exception {
		String matchingStr = "<?php function foo($a, int $b, $c = 5, int $d = 6) {} ?> ";
		String notMatchingStr = "<?php function bar($b, int $b, $c = 5, int $d = 6) {} ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchClassDeclarationSimple() throws Exception {
		String matchingStr = "<?php class MyClass { } ?> ";
		String notMatchingStr = "<?php class MyClass2 { } ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchClassDeclarationWithDeclarations() throws Exception {
		String matchingStr = "<?php final class MyClass extends SuperClass implements Interface1, Interface2 { const MY_CONSTANT = 3; public static final $myVar = 5, $yourVar; var $anotherOne; private function myFunction(MyClass $a, $b = 6) { }  } ?> ";
		String notMatchingStr = "<?php final class MyClass2 extends SuperClass2 implements Interface1, Interface2 { const MY_CONSTANT = 3; public static final $myVar = 5, $yourVar; var $anotherOne; private function myFunction(MyClass $a, $b = 6) { }  } ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchInterfaceDeclarationSimple() throws Exception {
		String matchingStr = "<?php interface MyInterface { } ?> ";
		String notMatchingStr = "<?php interface MyInterface2 { } ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchInterfaceDeclarationWithDeclarations() throws Exception {
		String matchingStr = "<?php interface MyInterface extends Interface1, Interface2 { const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		String notMatchingStr = "<?php interface MyInterface2 extends Interface3, Interface2 { const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchEmpty() throws Exception {
		String matchingStr = "<?php empty($a); ?> ";
		String notMatchingStr = "<?php empty($b); ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	@Test
	public void matchEval() throws Exception {
		String matchingStr = "<?php eval($a); ?> ";
		String notMatchingStr = "<?php eval($b); ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

}
