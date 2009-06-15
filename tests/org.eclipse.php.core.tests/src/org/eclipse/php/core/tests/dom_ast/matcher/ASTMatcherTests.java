/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.dom_ast.matcher;

import java.io.StringReader;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.match.PHPASTMatcher;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.Statement;

/**
 * Tests {@link ASTMatcher}
 * @author Eden K., 2008 
 */
public class ASTMatcherTests extends TestCase {

	public ASTMatcherTests(String name) {
		super(name);
	}

	public static TestSuite suite() {
		return new TestSuite(ASTMatcherTests.class);
	}

	public void performMatching(String matchingStr, String notMatchingStr) throws Exception {

		ASTNode node = getAstNode(matchingStr);
		ASTNode notMatchingNode = getAstNode(notMatchingStr);

		Assert.assertTrue(node.subtreeMatch(new PHPASTMatcher(), node));
		Assert.assertFalse(node.subtreeMatch(new PHPASTMatcher(), notMatchingNode));

		Assert.assertFalse(node.subtreeMatch(new PHPASTMatcher(), new Object()));
		Assert.assertFalse(node.subtreeMatch(new PHPASTMatcher(), null));
	}

	private ASTNode getAstNode(String str) throws Exception {
		StringReader reader = new StringReader(str);
		Program program = ASTParser.newParser(reader, PHPVersion.PHP5).createAST(new NullProgressMonitor());
		List<Statement> statements = program.statements();

		Assert.assertNotNull(statements);
		Assert.assertTrue(statements.size() > 0);

		// the statement that we want to test
		return statements.get(0);
	}

	public void testMatchScalar() throws Exception {
		String matchingStr = "<?php 5;?>";
		String notMatchingStr = "<?php 6;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchVariable() throws Exception {
		String matchingStr = "<?php $a;?>";
		String notMatchingStr = "<?php $b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchBackTickExpression() throws Exception {
		String matchingStr = "<?php `$cmd`?>";
		String notMatchingStr = "<?php `$cmd2`;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchFunctionInvocation() throws Exception {
		String matchingStr = "<?php foo(); ?>";
		String notMatchingStr = "<?php bar(); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchFunctionInvocationWithParams() throws Exception {
		String matchingStr = "<?php $foo($a, 's<>&', 12, true, __CLASS__); ?>";
		String notMatchingStr = "<?php $bar($a, 's<>&', 12, true, __CLASS__); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchStaticFunctionInvocation() throws Exception {
		String matchingStr = "<?php A::foo($a); ?>";
		String notMatchingStr = "<?php B::foo($a); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchArrayVariableWithoutIndex() throws Exception {
		String matchingStr = "<?php $a[]; ?>";
		String notMatchingStr = "<?php $b[]; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchArrayVariable() throws Exception {
		String matchingStr = "<?php $a[$b]; ?>";
		String notMatchingStr = "<?php $a[$c]; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchArrayVariableMultiIndex() throws Exception {
		String matchingStr = "<?php $a[$b][5][3]; ?>";
		String notMatchingStr = "<?php $a[$b][2][5]; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchHashTableVariable() throws Exception {
		String matchingStr = "<?php $a{'name'}; ?>";
		String notMatchingStr = "<?php $a{'test'}; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchListVariable() throws Exception {
		String matchingStr = "<?php list($a,$b)=1; ?>";
		String notMatchingStr = "<?php list($a,$c)=1; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchNestedListVariable() throws Exception {
		String matchingStr = "<?php list($a, list($b,$c))=1;?>";
		String notMatchingStr = "<?php list($b, list($b,$c))=1;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchAssignment() throws Exception {
		String matchingStr = "<?php $a = 1;?>";
		String notMatchingStr = "<?php $b = 1;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchPlusAssignment() throws Exception {
		String matchingStr = "<?php $a += 1;?>";
		String notMatchingStr = "<?php $a += 2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchSLAssignment() throws Exception {
		String matchingStr = "<?php $a <<= 1;?>";
		String notMatchingStr = "<?php $b <<= 1;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchReflectionSimple() throws Exception {
		String matchingStr = "<?php $$a;?>";
		String notMatchingStr = "<?php $$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchReflectionNested() throws Exception {
		String matchingStr = "<?php $$$$$foo;?>";
		String notMatchingStr = "<?php $$$$$bar;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchReflectionFunction() throws Exception {
		String matchingStr = "<?php $$$foo();?>";
		String notMatchingStr = "<?php $$$bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchReflectionComplex() throws Exception {
		String matchingStr = "<?php ${\"var\"};?>";
		String notMatchingStr = "<?php ${\"foo\"};?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchStaticMemberSimple() throws Exception {
		String matchingStr = "<?php MyClass::$a;?>";
		String notMatchingStr = "<?php MyClass::$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchStaticMemberWithArray() throws Exception {
		String matchingStr = "<?php MyClass::$$a[5];?>";
		String notMatchingStr = "<?php MyClass::$$a[3];?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchDispatchSimple() throws Exception {
		String matchingStr = "<?php $a->$b;?>";
		String notMatchingStr = "<?php $c->$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchDispatchNested() throws Exception {
		String matchingStr = "<?php $myClass->foo()->bar();?>";
		String notMatchingStr = "<?php $myClass->foo()->bar2();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchDispatchWithStaticCall() throws Exception {
		String matchingStr = "<?php MyClass::$a->foo();?>";
		String notMatchingStr = "<?php MyClass::$a->bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchDispatchNestedWithStaticCall() throws Exception {
		String matchingStr = "<?php MyClass::$a->$b->foo();?>";
		String notMatchingStr = "<?php MyClass::$a->$b->bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchClone() throws Exception {
		String matchingStr = "<?php clone $a; ?>";
		String notMatchingStr = "<?php clone $b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchCastOfVariable() throws Exception {
		String matchingStr = "<?php (int) $a; ?>";
		String notMatchingStr = "<?php (int) $b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchCastOfDispatch() throws Exception {
		String matchingStr = "<?php (string) $b->foo(); ?>";
		String notMatchingStr = "<?php (string) $b->bar(); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchClassConstant() throws Exception {
		String matchingStr = "<?php $a = MyClass::MY_CONST; ?>";
		String notMatchingStr = "<?php $a = MyClass::MY_CONST_2; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchPostfixSimple() throws Exception {
		String matchingStr = "<?php $a++;?>";
		String notMatchingStr = "<?php $b++;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchPostfixWithFunction() throws Exception {
		String matchingStr = "<?php foo()--;?>";
		String notMatchingStr = "<?php bar()--;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchPrefixSimple() throws Exception {
		String matchingStr = "<?php ++$a;?>";
		String notMatchingStr = "<?php ++$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchPrefixWithFunction() throws Exception {
		String matchingStr = "<?php --foo();?>";
		String notMatchingStr = "<?php --bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchUnaryOperationSimple() throws Exception {
		String matchingStr = "<?php +$a;?>";
		String notMatchingStr = "<?php +$b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchUnaryOperationWithFunction() throws Exception {
		String matchingStr = "<?php -foo();?>";
		String notMatchingStr = "<?php -bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchUnaryOperationComplex() throws Exception {
		String matchingStr = "<?php +-+-$b;?>";
		String notMatchingStr = "<?php +-+-$c;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchClassInstanciationSimple() throws Exception {
		String matchingStr = "<?php new MyClass();?>";
		String notMatchingStr = "<?php new MyClass2();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchClassInstanciationVariable() throws Exception {
		String matchingStr = "<?php new $a('start');?>";
		String notMatchingStr = "<?php new $a('end');?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchClassInstanciationFunction() throws Exception {
		String matchingStr = "<?php new $a->$b(1, $a);?>";
		String notMatchingStr = "<?php new $c->$b(1, $c);?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchRefernceSimple() throws Exception {
		String matchingStr = "<?php $b = &$a;?>";
		String notMatchingStr = "<?php $b = &$c;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchRefernceWithFunction() throws Exception {
		String matchingStr = "<?php $g = &$foo();?>";
		String notMatchingStr = "<?php $g = &$bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchRefernceInstanciation() throws Exception {
		String matchingStr = "<?php $b = &new MyClass();?>";
		String notMatchingStr = "<?php $b = &new MyClass2();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchInstanceofSimple() throws Exception {
		String matchingStr = "<?php $a instanceof MyClass;?>";
		String notMatchingStr = "<?php $a instanceof MyClass2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchInstanceofWithFunction() throws Exception {
		String matchingStr = "<?php foo() instanceof $myClass;?>";
		String notMatchingStr = "<?php foo() instanceof $myClass2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchInstanceofDispatch() throws Exception {
		String matchingStr = "<?php $a instanceof $b->$myClass;?>";
		String notMatchingStr = "<?php $a instanceof $b->$myClass2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchIgnoreError() throws Exception {
		String matchingStr = "<?php @$a->foo();?>";
		String notMatchingStr = "<?php @$b->foo();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchInclude() throws Exception {
		String matchingStr = "<?php include('myFile.php');?>";
		String notMatchingStr = "<?php include('myFile2.php');?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchIncludeOnce() throws Exception {
		String matchingStr = "<?php include_once($myFile);?>";
		String notMatchingStr = "<?php include_once($myFile2);?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchRequire() throws Exception {
		String matchingStr = "<?php require($myClass->getFileName());?>";
		String notMatchingStr = "<?php require($myClass2->getFileName());?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchRequireOnce() throws Exception {
		String matchingStr = "<?php require_once(A::FILE_NAME);?>";
		String notMatchingStr = "<?php require_once(A::FILE_NAME2);?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchArray() throws Exception {
		String matchingStr = "<?php array(1,2,3,);?>";
		String notMatchingStr = "<?php array(4,5,6,);?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchArrayKeyValue() throws Exception {
		String matchingStr = "<?php array('Dodo'=>'Golo','Dafna'=>'Dodidu');?>";
		String notMatchingStr = "<?php array('Test'=>'Golo','Test2'=>'Dodidu');?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchArrayComplex() throws Exception {
		String matchingStr = "<?php array($a, $b=>foo(), 1=>$myClass->getFirst());?>";
		String notMatchingStr = "<?php array($a, $b=>foo2(), 1=>$myClass->getFirst2());?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchConditionalExpression() throws Exception {
		String matchingStr = "<?php (bool)$a ? 3 : 4;?>";
		String notMatchingStr = "<?php (bool)$a ? 5 : 6;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchPlusOperation() throws Exception {
		String matchingStr = "<?php $a + 1;?>";
		String notMatchingStr = "<?php $a + 2;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchMinusOperation() throws Exception {
		String matchingStr = "<?php 3 - 2;?>";
		String notMatchingStr = "<?php 3 - 1;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchAndOperation() throws Exception {
		String matchingStr = "<?php foo() & $a->bar();?>";
		String notMatchingStr = "<?php foo() & $b->bar();?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchConcatOperation() throws Exception {
		String matchingStr = "<?php 'string'.$c;?>";
		String notMatchingStr = "<?php 'string'.$d;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchQuote() throws Exception {
		String matchingStr = "<?php \"this\nis $a quote\";?>";
		String notMatchingStr = "<?php \"this\nis $b quote2\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchSingleQuote() throws Exception {
		String matchingStr = "<?php \"'single ${$complex->quote()}'\";?>";
		String notMatchingStr = "<?php \"'single ${$complex->quote2()}'\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchQuoteWithOffsetNumber() throws Exception {
		String matchingStr = "<?php \"this\nis $a[6] quote\";?>";
		String notMatchingStr = "<?php \"this\nis $a[7] quote\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchQuoteWithCurly() throws Exception {
		String matchingStr = "<?php $text = <<<EOF\ntest{test}test\nEOF;\n?>";
		String notMatchingStr = "<?php $text = <<<EOF\ntest{test2}test\nEOF;\n?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchQuoteWithOffsetString() throws Exception {
		String matchingStr = "<?php \"this\nis $a[hello] quote\";?>";
		String notMatchingStr = "<?php \"this\nis $a[hello2] quote\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchHeredoc() throws Exception {
		String matchingStr = "<?php <<<Heredoc\n  This is here documents \nHeredoc;\n?>";
		String notMatchingStr = "<?php <<<Heredoc\n  This is here documents2 \nHeredoc;\n?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchEmptyHeredoc() throws Exception {
		String matchingStr = "<?php <<<Heredoc\nHeredoc;\n?>";
		String notMatchingStr = "<?php <<<Heredoc\nHeredoc2;\n?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchNotQuote() throws Exception {
		String matchingStr = "<?php \"This is\".$not.\" a quote node\";?>";
		String notMatchingStr = "<?php \"This is\".$not2.\" a quote node\";?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchBreakStatement() throws Exception {
		String matchingStr = "<?php break $a;?>";
		String notMatchingStr = "<?php break $b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchContinueStatementExpression() throws Exception {
		String matchingStr = "<?php continue $a;?>";
		String notMatchingStr = "<?php continue $b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchReturnExprStatement() throws Exception {
		String matchingStr = "<?php return $a; ?>";
		String notMatchingStr = "<?php return $b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchEchoStatement() throws Exception {
		String matchingStr = "<?php echo \"hello \",$b;?>";
		String notMatchingStr = "<?php echo \"hello \",$c;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchSwitchStatement() throws Exception {
		String matchingStr = "<?php switch ($i) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		String notMatchingStr = "<?php switch ($j) { case 0:    echo 'i equals 0';    break; case 1:     echo 'i equals 1';     break; default:    echo 'i not equals 0,1';  }  ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchIfStatement() throws Exception {
		String matchingStr = "<?php if ($a > $b) {   echo 'a is bigger than b';} elseif ($a == $b) {   echo 'a is equal to b';} else {   echo 'a is smaller than b';} ?>";
		String notMatchingStr = "<?php if ($a > $c) {   echo 'a is bigger than b';} elseif ($a == $c) {   echo 'a is equal to b';} else {   echo 'a is smaller than b';} ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchEndIfStatement() throws Exception {
		String matchingStr = "<?php if ($a):   echo 'a is bigger than b'; elseif ($a == $b): echo 'a is equal to b'; else: echo 'a is equal to b'; endif; ?>";
		String notMatchingStr = "<?php if ($b):   echo 'a is bigger than b'; elseif ($a == $b): echo 'a is equal to b'; else: echo 'a is equal to b'; endif; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchWhileStatement() throws Exception {
		String matchingStr = "<?php while ($i <= 10) echo $i++; ?>";
		String notMatchingStr = "<?php while ($j <= 10) echo $j++; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchWhileEndStatement() throws Exception {
		String matchingStr = "<?php while ($i <= 10):  echo $i;   $i++; endwhile; ?>";
		String notMatchingStr = "<?php while ($j <= 10):  echo $j;   $j++; endwhile; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchDoWhileStatement() throws Exception {
		String matchingStr = "<?php do { echo $i;} while ($i > 0); ?>";
		String notMatchingStr = "<?php do { echo $j;} while ($j > 0); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchForStatement() throws Exception {
		String matchingStr = "<?php for ($i = 1; $i <= 10; $i++) {  echo $i; } ?>";
		String notMatchingStr = "<?php for ($j = 1; $j <= 10; $j++) {  echo $j; } ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchEndForStatement() throws Exception {
		String matchingStr = "<?php for ($i = 1; $i <= 10; $i++):  echo $i; endfor; ?>";
		String notMatchingStr = "<?php for ($j = 1; $j <= 10; $j++):  echo $j; endfor; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchForStatementNoCondition() throws Exception {
		String matchingStr = "<?php for ($i = 1; ; $i++) { if ($i > 10) {  break;  }  echo $i;} ?>";
		String notMatchingStr = "<?php for ($j = 1; ; $j++) { if ($j > 10) {  break;  }  echo $j;} ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchForStatementNoArgs() throws Exception {
		String matchingStr = "<?php for (; ; ) { if ($i > 10) {   break;  } echo $i;  $i++;} ?>";
		String notMatchingStr = "<?php for (; ; ) { if ($j > 10) {   break;  } echo $j;  $j++;} ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchEmptyForStatement() throws Exception {
		String matchingStr = "<?php for ($i = 1, $j = 0; $i <= 10; $j += $i, print $i, $i++); ?>";
		String notMatchingStr = "<?php for ($j = 1, $k = 0; $j <= 10; $k += $j, print $j, $j++); ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchForEachStatement() throws Exception {
		String matchingStr = "<?php foreach ($arr as &$value) { $value = $value * 2; } ?>";
		String notMatchingStr = "<?php foreach ($arr2 as &$value) { $value = $value * 2; } ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchForEachStatementWithValue() throws Exception {
		String matchingStr = "<?php foreach ($arr as $key => $value) { echo \"Key: $key; Value: $value<br />\n\"; } ?>";
		String notMatchingStr = "<?php foreach ($arr2 as $key => $value) { echo \"Key: $key; Value: $value<br />\n\"; } ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchForEachStatementBlocked() throws Exception {
		String matchingStr = "<?php foreach ($arr as &$value): $value = $value * 2;endforeach; ?>";
		String notMatchingStr = "<?php foreach ($arr2 as &$value): $value = $value * 2;endforeach; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchTryCatchStatement() throws Exception {
		String matchingStr = "<?php try { $error = 'Always throw this error'; } catch (Exception $e) { echo ''; }  ?>";
		String notMatchingStr = "<?php try { $error2 = 'Always throw this error'; } catch (Exception $e) { echo ''; }  ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchTryMultiCatchStatement() throws Exception {
		String matchingStr = "<?php try { $error = 'Always throw this error'; } catch (Exception $e) { echo ''; } catch (AnotherException $ea) { echo ''; }  ?>";
		String notMatchingStr = "<?php try { $error2 = 'Always throw this error'; } catch (Exception $e) { echo ''; } catch (AnotherException $ea) { echo ''; }  ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchGlobalStatementSimple() throws Exception {
		String matchingStr = "<?php global $a; ?>";
		String notMatchingStr = "<?php global $b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchGlobalStatementReflection() throws Exception {
		String matchingStr = "<?php global $$a; ?>";
		String notMatchingStr = "<?php global $$b; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchGlobalStatementReflectionWithExpression() throws Exception {
		String matchingStr = "<?php global ${foo()->bar()}; ?>";
		String notMatchingStr = "<?php global ${foo2()->bar()}; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchGlobalStatementMultiple() throws Exception {
		String matchingStr = "<?php global $a, $b; ?>";
		String notMatchingStr = "<?php global $c, $d; ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchStaticSimple() throws Exception {
		String matchingStr = "<?php static $a;?>";
		String notMatchingStr = "<?php static $b;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchStaticMultiWithAssignment() throws Exception {
		String matchingStr = "<?php static $a, $b=6;?>";
		String notMatchingStr = "<?php static $b, $c=6;?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchInLineHtml() throws Exception {
		String matchingStr = "<html> </html>";
		String notMatchingStr = "<body> </body>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchInLineHtmlWithPHP() throws Exception {
		String matchingStr = "<html> <?php ?> </html> <?php ?>";
		String notMatchingStr = "<body> <?php ?> </body> <?php ?>";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchDeclareSimple() throws Exception {
		String matchingStr = "<?php declare(ticks=1) { };?> ";
		String notMatchingStr = "<?php declare(ticks2=1) { };?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchEmptyDeclare() throws Exception {
		String matchingStr = "<?php declare(ticks=1);?> ";
		String notMatchingStr = "<?php declare(ticks2=1);?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchDeclareWithStatement() throws Exception {
		String matchingStr = "<?php declare(ticks=2) { static $a; }; ?> ";
		String notMatchingStr = "<?php declare(ticks2=2) { static $a; }; ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchFunctionDeclaration() throws Exception {
		String matchingStr = "<?php function foo() {} ?> ";
		String notMatchingStr = "<?php function bar() {} ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchFunctionDeclarationReference() throws Exception {
		String matchingStr = "<?php function &foo() {} ?> ";
		String notMatchingStr = "<?php function &bar() {} ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchFunctionDeclarationWithParams() throws Exception {
		String matchingStr = "<?php function foo($a, int $b, $c = 5, int $d = 6) {} ?> ";
		String notMatchingStr = "<?php function bar($b, int $b, $c = 5, int $d = 6) {} ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchClassDeclarationSimple() throws Exception {
		String matchingStr = "<?php class MyClass { } ?> ";
		String notMatchingStr = "<?php class MyClass2 { } ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchClassDeclarationWithDeclarations() throws Exception {
		String matchingStr = "<?php final class MyClass extends SuperClass implements Interface1, Interface2 { const MY_CONSTANT = 3; public static final $myVar = 5, $yourVar; var $anotherOne; private function myFunction(MyClass $a, $b = 6) { }  } ?> ";
		String notMatchingStr = "<?php final class MyClass2 extends SuperClass2 implements Interface1, Interface2 { const MY_CONSTANT = 3; public static final $myVar = 5, $yourVar; var $anotherOne; private function myFunction(MyClass $a, $b = 6) { }  } ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchInterfaceDeclarationSimple() throws Exception {
		String matchingStr = "<?php interface MyInterface { } ?> ";
		String notMatchingStr = "<?php interface MyInterface2 { } ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchInterfaceDeclarationWithDeclarations() throws Exception {
		String matchingStr = "<?php interface MyInterface extends Interface1, Interface2 { const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		String notMatchingStr = "<?php interface MyInterface2 extends Interface3, Interface2 { const MY_CONSTANT = 3; public function myFunction($a); } ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchEmpty() throws Exception {
		String matchingStr = "<?php empty($a); ?> ";
		String notMatchingStr = "<?php empty($b); ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

	public void testMatchEval() throws Exception {
		String matchingStr = "<?php eval($a); ?> ";
		String notMatchingStr = "<?php eval($b); ?> ";
		performMatching(matchingStr, notMatchingStr);
	}

}
