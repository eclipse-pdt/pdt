tree grammar TreePHP;

options {
  tokenVocab=CompilerAst;
  ASTLabelType=SLAST;
  output=AST;
}

@header {
  package org.eclipse.php.internal.core.compiler.ast.parser.php4;
  
  import org.eclipse.dltk.ast.*;
import org.eclipse.dltk.ast.declarations.*;
import org.eclipse.dltk.ast.expressions.*;
import org.eclipse.dltk.ast.references.*;
import org.eclipse.dltk.ast.statements.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.*;
import java.util.*;
import org.antlr.runtime.BitSet;
}

@members {
  ModuleDeclaration program;
  
  AbstractASTParser parser;
  
  Expression quoteExpr;
  
  boolean inExprList = false;
  
  boolean inClassStatementList = false;
  
  boolean inVarList = false;
  
  boolean inNameList = false;
    
  public TreePHP(TreeNodeStream input, AbstractASTParser parser) {
     this(input, new RecognizerSharedState());
     this.parser = parser;
  }
   
  public ModuleDeclaration getModuleDeclaration() {
    return program;
  }
  
  public Expression getQuoteExpression() {
      return quoteExpr;
  }
  
  class ModifierDocPair {
    public int modifier;
    public PHPDocBlock doc;
    
    public ModifierDocPair(int modifier, PHPDocBlock doc) {
      this.modifier = modifier;
      this.doc = doc;
    }
  }
  
  public Expression createDispatch(Expression dispatcher, Expression property) {

    if (property.getKind() == ASTNodeKinds.REFLECTION_CALL_EXPRESSION) {
      ((ReflectionCallExpression) property).setReceiver (dispatcher);
      dispatcher = property;
    } else if (property.getKind() == ASTNodeKinds.METHOD_INVOCATION) {
      PHPCallExpression callExpression = (PHPCallExpression) property;
      dispatcher = new PHPCallExpression(dispatcher.sourceStart(), callExpression.sourceEnd(), dispatcher, callExpression.getCallName(), callExpression.getArgs());
    } else {
      dispatcher =  new FieldAccess(dispatcher.sourceStart(), property.sourceEnd(), dispatcher, property);
    }

    return dispatcher;
  }
  
    private List errors = new LinkedList();
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        errors.add(hdr + " " + msg);
    }
    public List<String> getErrors() {
        return errors;
    }
    
    public static TreeAdaptor tokenAdaptor = new CommonTreeAdaptor() {
		    public Object create(Token token) {
		      return new SLAST(token);
		    }
		
		    public Object dupNode(Object t) {
		      if (t == null) {
		        return null;
		      }
		      return create(((SLAST) t).token);
		    }
    };
    
    
    public Expression parseQuote(String inputStr, int originalStartIndex, int originalEndIndex, int type) {
      
        if (inputStr.contains("'")) {
          inputStr = inputStr.replace("'", "");
        }
      
        String [] stats = inputStr.split("( |\n|\t)+",0);
     
        Expression expr = null;
        
        List list = new LinkedList();
        
        int startIndex = originalStartIndex;
        
        int endIndex = 0; 
        Quote quote = null;
   
       
        
        for (String str : stats) {
          
          String code = "<?php " + str + ";?>";
          
          System.out.println("code:" + code);
          
          try {
			    
					    CharStream input = new ANTLRStringStream(code);
					
					    CompilerAstLexer lexer = new CompilerAstLexer(input);
					    
					    CommonTokenStream tokens = new CommonTokenStream(lexer);
					    
					    CompilerAstParser parser = new CompilerAstParser(tokens);
					    
					    parser.setTreeAdaptor(tokenAdaptor);
					    
					    if (inputStr.indexOf(str) >= endIndex) {
                  startIndex = inputStr.indexOf(str) + originalStartIndex + 1;
              }
              
              endIndex = startIndex + str.length();
					      
					    CompilerAstParser.php_source_return r = parser.php_source();
					
					    SLAST t = (SLAST) r.getTree(); // get tree result
					    
					    CommonTreeNodeStream nodes = new CommonTreeNodeStream(tokenAdaptor, t);
					    
					    nodes.setTokenStream(tokens);
				
				      TreePHP def = new TreePHP(nodes, parser);
				      
				      def.setTreeAdaptor(tokenAdaptor);
				      
				      TreePHP.php_source_return result = def.php_source();
				      
				      expr = def.getQuoteExpression();
				      
				      expr.setStart(startIndex);
              
              expr.setEnd(endIndex);
			      
			      }  catch (Exception ex) {
              System.out.println("exception in quote");
              System.out.println(ex);
              
              expr = new Scalar(startIndex, endIndex, str, Scalar.TYPE_UNKNOWN);
            }
                          
            list.add(expr);
              
            startIndex = endIndex;
				  }
				  
			  quote = new Quote(originalStartIndex, originalEndIndex, list, type);

	      return quote;
	  }
}

php_source
  :  ^(ModuleDeclaration top_statement_list?)
  {
    ModuleDeclaration RESULT = null;
    int startIndex = $ModuleDeclaration.startIndex;
    int endIndex = $ModuleDeclaration.endIndex + 2;
 
    PHPModuleDeclaration program = parser.getModuleDeclaration();
	  program.setStart(startIndex);
	  program.setEnd(endIndex);
	  RESULT = program;
	  
	  System.out.println("module: \n" + RESULT);
  }
  ;

top_statement_list
  : top_statement+
  ;

top_statement
  : statement
  {
    Statement stat = $statement.stat;
    parser.addStatement(stat);
//    System.out.println("state:" + stat);
  }
  | function_declaration_statement
  {
    parser.addStatement($function_declaration_statement.stat);
  }
  | class_declaration_statement
  {
    ClassDeclaration classDeclaration = $class_declaration_statement.classDeclaration;
    if (classDeclaration != null) {
      parser.addDeclarationStatement(classDeclaration);
      parser.declarations.push(classDeclaration);
      parser.addStatement(classDeclaration);
    }
    else {
      InterfaceDeclaration interfaceDeclaration = $class_declaration_statement.interfaceDeclaration;
      parser.addDeclarationStatement(interfaceDeclaration);
      parser.declarations.push(interfaceDeclaration);
      parser.addStatement(interfaceDeclaration);
    }
  }
  | halt_compiler_statement
  ;

inner_statement_list returns [List innerStatementList]
scope {
  List list;
}
@init {
  $inner_statement_list::list = new LinkedList();
}
  : (inner_statement)+
  {
    $innerStatementList = $inner_statement_list::list;
  }
  ;
  
inner_statement //returns [Statement stat]
  : statement
  {
    if ($statement.stat != null) {
      $inner_statement_list::list.add($statement.stat);
      System.out.println("inner state: " + $statement.stat);
    }
  }
  | function_declaration_statement
  {
    if ($function_declaration_statement.stat != null) {
      $inner_statement_list::list.add($function_declaration_statement.stat);
    }  
  }
  | class_declaration_statement
  {
    if ($class_declaration_statement.classDeclaration != null) {
      $inner_statement_list::list.add($class_declaration_statement.classDeclaration);
    } 
  }
  | halt_compiler_statement
  ;
  
halt_compiler_statement
  : '__halt_compiler'
  ;
  
class_declaration_statement returns [ClassDeclaration classDeclaration, InterfaceDeclaration interfaceDeclaration]
  : ^(CLASS_T class_entr_type? IDENTIFIER
  {
      TreePHP.ModifierDocPair modifier = new ModifierDocPair(Modifiers.AccDefault, null);
      int startIndex = $CLASS_T.startIndex;
      int endIndex = $CLASS_T.endIndex + 1;
      if ($class_entr_type.text != null) {
          if ($class_entr_type.text.equals("abstract")) {
            modifier = new ModifierDocPair(Modifiers.AccAbstract, null);
          }
      }
      
      CommonToken token = (CommonToken)$IDENTIFIER.token;
      int classNameLeft = token.getStartIndex();
      int classNameRight = token.getStopIndex() + 1;
      String className = $IDENTIFIER.text;
      
      $classDeclaration = new ClassDeclaration(startIndex ,endIndex, classNameLeft, classNameRight, modifier.modifier, className, null, null, new Block(classNameRight,classNameRight,null), null);
      parser.addDeclarationStatement($classDeclaration);
      parser.declarations.push($classDeclaration);
  }
  
   (^(EXTENDS_T fully_qualified_class_name))? (^(IMPLEMENTS_T fully_qualified_class_name_list))?
      (^(CLASS_BODY class_statement_list))?)
    {
      
      TreePHP.ModifierDocPair modifier = new ModifierDocPair(Modifiers.AccDefault, null);
      int startIndex = $CLASS_T.startIndex;
      int endIndex = $CLASS_T.endIndex + 1;
      if ($class_entr_type.text != null) {
          if ($class_entr_type.text.equals("abstract")) {
            modifier = new ModifierDocPair(Modifiers.AccAbstract, null);
          }
      }
      
      CommonToken token = (CommonToken)$IDENTIFIER.token;
      int classNameLeft = token.getStartIndex();
      int classNameRight = token.getStopIndex() + 1;
      String className = $IDENTIFIER.text;
        
      TypeReference superClass = null;
      int superClassLeft = 0;
      int superClassRight = 0;
      if ($fully_qualified_class_name.text != null) {
          superClassLeft = ((CommonToken)$fully_qualified_class_name.tree.token).getStartIndex();
          superClassRight = ((CommonToken)$fully_qualified_class_name.tree.token).getStopIndex() + 1;
          superClass = $fully_qualified_class_name.type;
      }
      
      List interfaces = null;
      int interfacesLeft = 0;
      int interfacesRight = 0;
      if ($fully_qualified_class_name_list.text != null) {
          interfacesLeft = ((CommonToken)$fully_qualified_class_name_list.tree.token).getStartIndex();
          interfacesRight = ((CommonToken)$fully_qualified_class_name_list.tree.token).getStopIndex() + 1;
      }
      
      $classDeclaration = (ClassDeclaration)parser.declarations.pop();
		  if (superClass != null) {
		    $classDeclaration.setSuperClass(superClass);
		  }
		  if (interfaces != null) {
		    $classDeclaration.setInterfaceList(interfaces);
		  }
    }
  | ^(INTERFACE_T IDENTIFIER (^(EXTENDS_T fully_qualified_class_name_list))?
    { 
       PHPDocBlock start = null;
       int startIndex = $INTERFACE_T.startIndex;
       int endIndex = $INTERFACE_T.endIndex + 1;
          
       CommonToken token = (CommonToken)$IDENTIFIER.token;
       int classNameLeft = token.getStartIndex();
       int classNameRight = token.getStopIndex() + 1;
       String className = $IDENTIFIER.text;
       
       List interfaces = new LinkedList();
       int listStartIndex = 0, listEndIndex = 0;
       if ($fully_qualified_class_name_list.list != null) {
          interfaces = $fully_qualified_class_name_list.list;
          CommonToken listToken = (CommonToken)$fully_qualified_class_name_list.tree.token;
          listStartIndex = listToken.getStartIndex();
          listEndIndex = listToken.getStopIndex();
       }
          
       $interfaceDeclaration = new InterfaceDeclaration(startIndex ,endIndex, classNameLeft, classNameRight, className, interfaces, new Block(listStartIndex, listEndIndex,null), start);
       parser.declarations.push($interfaceDeclaration);
    }
      (^(CLASS_BODY class_statement_list))?)
    {
       $interfaceDeclaration = (InterfaceDeclaration)parser.declarations.pop();
    }
  ;
  
class_entr_type
  : 'abstract'
  | 'final'
  ;
  
class_statement_list
  : class_statement+
  ;
 
class_statement
scope {
  List constList;
  List varList;
}
@init {
  $class_statement::constList = new LinkedList();
  $class_statement::varList = new LinkedList();
}
  : ^(FIELD_DECL {inClassStatementList = true;} variable_modifiers static_var_element+ {inClassStatementList = false;})
  {
    ModifierDocPair modifier = new ModifierDocPair(Modifiers.AccDefault, null);
    int modifierLeft = ((CommonToken)$variable_modifiers.tree.token).getStartIndex();
    if ($variable_modifiers.text.equals("var")) {
      modifier = new ModifierDocPair(Modifiers.AccPublic, null);
    }
    
    Iterator iter = $class_statement::varList.iterator();
    while (iter.hasNext()) {
	    ASTNode[] decl = (ASTNode[])iter.next();
	    if (decl != null) {
		    VariableReference variable = (VariableReference)decl[0];
		    Expression initializer = (Expression)decl[1];
		    int start = variable.sourceStart();
		    int end = (initializer == null ? variable.sourceEnd() : initializer.sourceEnd());
		    parser.addDeclarationStatement(new PHPFieldDeclaration(variable, initializer, start, end, modifier.modifier, modifierLeft, modifier.doc));
		  }
		}
  }
  | ^(METHOD_DECL modifier REF_T? IDENTIFIER
    {
      ModifierDocPair modifier = $modifier.modifierVar;
      PHPDocBlock start = null;
      Boolean isReference = false;
      if ($REF_T.text != null) {
         isReference = true;
      }
      int functionNameLeft = ((CommonToken)$IDENTIFIER.token).getStartIndex();
      int functionNameRight = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;
      String functionName = $IDENTIFIER.text;
  
      int startIndex = $METHOD_DECL.startIndex;
      int endIndex = $METHOD_DECL.endIndex + 1;
      int modifierValue = (modifier == null || modifier.modifier == Modifiers.AccDefault) ? Modifiers.AccPublic : modifier.modifier;
      
      PHPDocBlock docBlock = start;
      if (modifier != null && modifier.doc != null) {
        docBlock = modifier.doc;
      }
      PHPMethodDeclaration methodDeclaration = new PHPMethodDeclaration(startIndex, endIndex, startIndex, endIndex, functionName, modifierValue, null, new Block(startIndex, endIndex, null), isReference.booleanValue(), docBlock);
      parser.addDeclarationStatement(methodDeclaration);
      parser.declarations.push(methodDeclaration);
    }
      parameter_list?
      {
        if ($parameter_list.text != null) {
          PHPMethodDeclaration functionDeclaration = (PHPMethodDeclaration)parser.declarations.peek();
				  functionDeclaration.acceptArguments($parameter_list.parameterList);
        }
      }
    (block
     {
        startIndex = ((CommonToken)$block.tree.token).getStartIndex();
		    endIndex = ((CommonToken)$block.tree.token).getStopIndex() + 1;
		    
		    methodDeclaration = (PHPMethodDeclaration)parser.declarations.peek();
			  methodDeclaration.getBody().setStart(startIndex);
			  methodDeclaration.getBody().setEnd(endIndex);
			  methodDeclaration.getBody().getStatements().clear();
			  methodDeclaration.getBody().acceptStatements($block.statList);
			  
			  methodDeclaration = (PHPMethodDeclaration)parser.declarations.pop();
//			  if(body instanceof ASTError) {
//			    parser.reportError(new ASTError(methodDeclaration.sourceEnd() - 1, methodDeclaration.sourceEnd()), "syntax error, unfinished method declaration");
//			  }
			  TypeDeclaration type = (TypeDeclaration)parser.declarations.peek();
			  methodDeclaration.setDeclaringTypeName(type.getName()); 
     }
    |
    EMPTYSTATEMENT 
    {   
        startIndex = ((CommonToken)$EMPTYSTATEMENT.token).getStartIndex();
        endIndex = ((CommonToken)$EMPTYSTATEMENT.token).getStopIndex() + 1;
        
        methodDeclaration = (PHPMethodDeclaration)parser.declarations.peek();
        methodDeclaration.getBody().setStart(startIndex);
        methodDeclaration.getBody().setEnd(endIndex);
        methodDeclaration.getBody().getStatements().clear();
        methodDeclaration.getBody().acceptStatements(new LinkedList());
        
        methodDeclaration = (PHPMethodDeclaration)parser.declarations.pop();
//        if(body instanceof ASTError) {
//          parser.reportError(new ASTError(methodDeclaration.sourceEnd() - 1, methodDeclaration.sourceEnd()), "syntax error, unfinished method declaration");
//        }
        TypeDeclaration type = (TypeDeclaration)parser.declarations.peek();
        methodDeclaration.setDeclaringTypeName(type.getName()); 
     }
    ))  
  | ^(FIELD_DECL {inClassStatementList = true;} CONST_T directive+ {inClassStatementList = false;})
  {
    int startIndex = $FIELD_DECL.startIndex;
    int endIndex = $FIELD_DECL.endIndex;
    Iterator iter = $class_statement::constList.iterator();
    while (iter.hasNext()) {
      ASTNode[] decl = (ASTNode[])iter.next();
	    if (decl != null) {
		    ConstantReference constant = (ConstantReference)decl[0];
		    Expression initializer = (Expression)decl[1];
		    int decListLeft = ((CommonToken)$directive.tree.token).getStartIndex();
		      
		    PHPDocBlock docBlock = null;
		    if (decl.length == 3) {
		      docBlock = (PHPDocBlock)decl[2];
		    }
		    int start = constant.sourceStart();
		    int end = (initializer == null ? constant.sourceEnd() : initializer.sourceEnd());
		    parser.addDeclarationStatement(new ConstantDeclaration(constant, initializer, startIndex, end, docBlock));
		  }
		}
  }
  ;

function_declaration_statement returns [Statement stat]
  : ^(METHOD_DECL REF_T? IDENTIFIER
  {
    Boolean isReference = false;
    if ($REF_T.text != null) {
       isReference = true;
    }
    int functionNameLeft = ((CommonToken)$IDENTIFIER.token).getStartIndex();
    int functionNameRight = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;
    String functionName = $IDENTIFIER.text;
  
    int startIndex = $METHOD_DECL.startIndex;
    int endIndex = $METHOD_DECL.endIndex + 1;
    int modifierValue = Modifiers.AccDefault;
      
    PHPDocBlock docBlock = null;
    
    PHPMethodDeclaration methodDeclaration = new PHPMethodDeclaration(startIndex, endIndex, startIndex, endIndex, functionName, modifierValue, null, new Block(startIndex, endIndex, null), isReference.booleanValue(), docBlock);
    parser.addDeclarationStatement(methodDeclaration);
    parser.declarations.push(methodDeclaration);
  }
   parameter_list? 
   {
      if ($parameter_list.text != null) {
        PHPMethodDeclaration functionDeclaration = (PHPMethodDeclaration)parser.declarations.peek();
        functionDeclaration.acceptArguments($parameter_list.parameterList);
      }
   } 
   block
   {
    startIndex = ((CommonToken)$block.tree.token).getStartIndex();
    endIndex = ((CommonToken)$block.tree.token).getStopIndex() + 1;
        
    methodDeclaration = (PHPMethodDeclaration)parser.declarations.peek();
    methodDeclaration.getBody().setStart(startIndex);
    methodDeclaration.getBody().setEnd(endIndex);
    methodDeclaration.getBody().getStatements().clear();
    methodDeclaration.getBody().acceptStatements($block.statList);
        
    methodDeclaration = (PHPMethodDeclaration)parser.declarations.pop();
//        if(body instanceof ASTError) {
//          parser.reportError(new ASTError(methodDeclaration.sourceEnd() - 1, methodDeclaration.sourceEnd()), "syntax error, unfinished method declaration");
//        }
//    TypeDeclaration type = (TypeDeclaration)parser.declarations.peek();
//    methodDeclaration.setDeclaringTypeName(type.getName());
    $stat = methodDeclaration;
    System.out.println("here" + $stat);
  }
   )
  
  
  ; 

block returns [Statement stat, List statList]//returns [Statement stat, List statList]
  : ^(BLOCK inner_statement_list?)
     {
      int startIndex = $BLOCK.startIndex;
      int endIndex = $BLOCK.endIndex + 1;
      System.out.println("emptystat:" + startIndex + " " + endIndex);
      Block block = new Block(startIndex, endIndex, new LinkedList());
      if ($inner_statement_list.innerStatementList != null) {
        block.getStatements().clear();
        block.acceptStatements($inner_statement_list.innerStatementList);
        $statList = $inner_statement_list.innerStatementList;
      }
      else {
        $statList = new LinkedList();
      }
      $stat = block;
    }
  ;
  
statement returns [Statement stat]
  : ^(STATEMENT topStatement)
  {
    if ($topStatement.expr != null) {
//	    int startIndex = $STATEMENT.startIndex;
//	    int endIndex = $STATEMENT.endIndex + 1;
      SLAST ast = $topStatement.start;
      int startIndex = ast.startIndex;
      int endIndex = ast.endIndex + 1;
	    $stat = new ExpressionStatement(startIndex, endIndex, $topStatement.expr);
	    System.out.println("$stat:" + $stat);
	    quoteExpr = $topStatement.expr;
    }
    else {
      $stat = $topStatement.stat;
    }
  }
  ;

topStatement returns [Expression expr, Statement stat]
scope {
  List declareKey;
  List declareValue;
}
@init {
  $topStatement::declareKey = new LinkedList();
  $topStatement::declareValue = new LinkedList();
}
  : block 
  {
    $stat = $block.stat;
  }
  | if_stat
  {
    $stat = $if_stat.stat;
  }
  | ^(WHILE_T ^(CONDITION e=expression) while_statement)
  {
    int startIndex = $WHILE_T.startIndex;
    int endIndex = $WHILE_T.endIndex + 1;
    $stat = new WhileStatement(startIndex, endIndex, $e.expr, $while_statement.block);   
  }
  
  | ^(DO_T ^(CONDITION e=expression) statement)
  {
    int startIndex = $DO_T.startIndex;
    int endIndex = $DO_T.endIndex + 1;
    $stat = new DoStatement(startIndex, endIndex, $e.expr, $statement.stat);      
  }
  | ^(FOR_T e1=expr_list? ^(CONDITION e2=expr_list?) ^(ITERATE e3=expr_list?) s1=for_statement)
  {
    List initList = new LinkedList(),
        condList = new LinkedList(),
        changeList = new LinkedList();
    if ($e1.exprList != null) initList = $e1.exprList;
    if ($e2.exprList != null) condList = $e2.exprList;
    if ($e3.exprList != null) changeList = $e3.exprList;
    int startIndex = $FOR_T.startIndex;
    int endIndex = $FOR_T.endIndex + 1;
    $stat = new ForStatement(startIndex, endIndex, initList, condList, changeList, $s1.block);
  }
  | ^(SWITCH_T ^(CONDITION e=expression) switch_case_list)
  {
    int startIndex = $SWITCH_T.startIndex;
    int endIndex = $SWITCH_T.endIndex + 1;
    $stat = new SwitchStatement(startIndex, endIndex, $e.expr, $switch_case_list.block);
  }
  | ^(BREAK_T expression?)
  {
    int startIndex = $BREAK_T.startIndex;
    int endIndex = $BREAK_T.endIndex + 1;
    $stat = new BreakStatement(startIndex, endIndex);
  }
  | ^(CONTINUE_T e=expression?)
  {
    int startIndex = $CONTINUE_T.startIndex;
    int endIndex = $CONTINUE_T.endIndex + 1;
    $stat = new ContinueStatement(startIndex, endIndex);
    if ($e.expr != null) {
      $stat = new ContinueStatement(startIndex, endIndex, $e.expr);
    }
  }
  | ^(RETURN_T e=expression?)
  {
    int startIndex = $RETURN_T.startIndex;
    int endIndex = $RETURN_T.endIndex + 1;
    $stat = new ReturnStatement(startIndex, endIndex);
    if ($e.expr != null) {
      $stat = new ReturnStatement(startIndex, endIndex, $e.expr);
    }
  }
  | ^(GLOBAL_T variable_list)
  {
    int startIndex = $GLOBAL_T.startIndex;
    int endIndex = $GLOBAL_T.endIndex + 1;
    $stat = new GlobalStatement(startIndex, endIndex, $variable_list.variableList);
  }
  | ^(STATIC_T static_var_list)
  {
    int startIndex = $STATIC_T.startIndex;
    int endIndex = $STATIC_T.endIndex + 1;
    $stat = new StaticStatement(startIndex, endIndex, $static_var_list.staticVarList);
  }
  | ^(ECHO_T expr_list)
  {
    int startIndex = $ECHO_T.startIndex;
    int endIndex = $ECHO_T.endIndex + 1;
    $stat = new EchoStatement(startIndex, endIndex, $expr_list.exprList); 
  }
  | ^(EMPTYSTATEMENT SEMI_COLON)
  {
    int startIndex = $EMPTYSTATEMENT.startIndex;
    int endIndex = $EMPTYSTATEMENT.endIndex + 1;
    $stat = new EmptyStatement(startIndex, endIndex); 
  }
  | expression
  {
     $expr = $expression.expr;
  }
  | ^(FOREACH_T ^(AS_T e=expression v1=foreach_variable v2=foreach_variable?) foreach_statement)
  {
    int startIndex = $FOREACH_T.startIndex;
    int endIndex = $FOREACH_T.endIndex + 1;
    
    if ($v2.expr == null) {
      $stat = new ForEachStatement(startIndex, endIndex, $e.expr, $v1.expr, $foreach_statement.block);
    }
    else {
      $stat = new ForEachStatement(startIndex, endIndex, $e.expr, $v1.expr, $v2.expr, $foreach_statement.block);
    }
  } 
  | ^(DECLARE_T directive declare_statement)
	  {
	    int startIndex = $DECLARE_T.startIndex;
	    int endIndex = $DECLARE_T.endIndex + 1;
	    DeclareStatement declare = new DeclareStatement(startIndex, endIndex, $topStatement::declareKey, $topStatement::declareValue, $declare_statement.block);
	    $stat = declare;
	    System.out.println("declare block");
	  }
  | ^(TRY_T block catch_branch_list)
  {
    int startIndex = $TRY_T.startIndex;
    int endIndex = $TRY_T.endIndex + 1;
    
    TryStatement tryStatement = new TryStatement(startIndex, endIndex, (Block)$block.stat, $catch_branch_list.catchList);
    $stat = tryStatement;
  }
  | ^(THROW_T e=expression)
  {
    int startIndex = $THROW_T.startIndex;
    int endIndex = $THROW_T.endIndex + 1;
    $stat = new ThrowStatement(startIndex, endIndex, $e.expr); 
  }
  | ^(USE_T scalar)
  {
    int startIndex = $USE_T.startIndex;
    int endIndex = $USE_T.endIndex;
    SimpleReference functionName = new SimpleReference(startIndex, startIndex + "use".length(), "use");
    
    PHPCallArgumentsList list = new PHPCallArgumentsList($scalar.expr.sourceStart(), $scalar.expr.sourceEnd());
    list.addNode($scalar.expr);
    
    $expr = new PHPCallExpression(startIndex, endIndex, null, functionName, list);
  }
  | ^(USE_PARETHESIS_T scalar)
  {
    int startIndex = $USE_PARETHESIS_T.startIndex;
    int endIndex = $USE_PARETHESIS_T.endIndex;
    SimpleReference functionName = new SimpleReference(startIndex, startIndex + "use".length(), "use");
    
    PHPCallArgumentsList list = new PHPCallArgumentsList($scalar.expr.sourceStart() - 1, $scalar.expr.sourceEnd() + 1);
    list.addNode($scalar.expr);
    
    $expr = new PHPCallExpression(startIndex, endIndex, null, functionName, list);
  }
  | ^(INCLUDE_T e=expression) 
  {
    int startIndex = $INCLUDE_T.startIndex;
    int endIndex = $INCLUDE_T.endIndex;
    $expr = new Include(startIndex, endIndex, $e.expr, Include.IT_INCLUDE);
  }
  | ^(INCLUDE_ONCE_T e=expression) 
  {
    int startIndex = $INCLUDE_ONCE_T.startIndex;
    int endIndex = $INCLUDE_ONCE_T.endIndex;
    $expr = new Include(startIndex, endIndex, $e.expr, Include.IT_INCLUDE_ONCE);
  }
  | ^(REQUIRE_T e=expression) 
  {
    int startIndex = $REQUIRE_T.startIndex;
    int endIndex = $REQUIRE_T.endIndex;
    $expr = new Include(startIndex, endIndex, $e.expr, Include.IT_REQUIRE);
  }
  | ^(REQUIRE_ONCE_T e=expression) 
  {
    int startIndex = $REQUIRE_ONCE_T.startIndex;
    int endIndex = $REQUIRE_ONCE_T.endIndex;
    $expr = new Include(startIndex, endIndex, $e.expr, Include.IT_REQUIRE_ONCE);
  }
//| error
//| var comment
//| T_INLINE_HTML
  ;

foreach_variable returns [Expression expr]
//  : ^('=>' variable variable)
//  | variable
//  {
//    $expr = $variable.var;
//  }
  : REF_T? variable
  {
    $expr = $variable.var;
  }
  ;
  
 
use_filename
  : STRINGLITERAL
  | QUOTE_STRING
  ;
  
variable_list returns [List variableList]
scope {
  List varList;
}
@init {
  $variable_list::varList = new LinkedList();
  inVarList = true;
}
@after {
  $variable_list.tree = $variable_list.start;
}
  : variable+
  {
    $variableList = $variable_list::varList;
    inVarList = false;
  }
  ;
  
fully_qualified_class_name_list returns [List list]
scope {
  List nameList;
}
@init {
  $fully_qualified_class_name_list::nameList = new LinkedList();
  inNameList = true;
}
@after {
  $fully_qualified_class_name_list.tree = $fully_qualified_class_name_list.start;
}
  : fully_qualified_class_name+
  {
    $list = $fully_qualified_class_name_list::nameList; 
    inNameList = false;
  }
  ;
  
fully_qualified_class_name returns [String name, TypeReference type, SimpleReference simpleRef, StaticConstantAccess constant]
  : ^(d=DOMAIN_T f=fully_qualified_class_name IDENTIFIER) DOMAIN_T?
  {
    int startIndex = $d.startIndex;
    int endIndex = $d.endIndex + 1;
    String className = null;
    TypeReference type = null;
        
    if ($f.name != null) {
      className = $f.name;
      
      System.out.println("classname: " + className);
      int typeLeft = ((CommonToken)$f.tree.token).getStartIndex();
      int typeRight = ((CommonToken)$f.tree.token).getStopIndex() + 1;
      type = new TypeReference(typeLeft, typeRight, className);
      CommonToken token = (CommonToken)$IDENTIFIER.token;
      int varLeft = token.getStartIndex();
      int varRight = token.getStopIndex() + 1;
      $type = type;
      $simpleRef = new SimpleReference(varLeft, varRight, $IDENTIFIER.text);
    }
    else {
      type = $type;
    }
      
    CommonToken token = (CommonToken)$IDENTIFIER.token;
    int varLeft = token.getStartIndex();
    int varRight = token.getStopIndex() + 1;
    ConstantReference constRef = new ConstantReference(varLeft, varRight, $IDENTIFIER.text);
    $constant = new StaticConstantAccess(startIndex, endIndex, type, constRef);
    
    if (inNameList) {
      $fully_qualified_class_name_list::nameList.add(type);
    }
  }
  | IDENTIFIER DOMAIN_T?
  {
    $name = $IDENTIFIER.text;
    int typeLeft = ((CommonToken)$IDENTIFIER.token).getStartIndex();
    int typeRight = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;

    $type = new TypeReference(typeLeft, typeRight, $IDENTIFIER.text);
    
    if (inNameList) {
      $fully_qualified_class_name_list::nameList.add($type);
    }
  }
  ;
  

static_var_list returns [List staticVarList]
scope {
  List varList;
}
@init {
  $static_var_list::varList = new LinkedList();
}
  : static_var_element+
  {
    $staticVarList = $static_var_list::varList;
  }
  ;

static_var_element
  : pure_variable
  {
    int varNameLeft = $pure_variable.tree.startIndex;
    int varNameRight = $pure_variable.tree.endIndex + 2;
    String varName = $pure_variable.text;
    VariableReference varId = new VariableReference(varNameLeft, varNameRight, varName);
    
    if (inClassStatementList) {
      Object obj = new ASTNode[] {varId, null};
      $class_statement::varList.add(obj);
    }
    else {
      $static_var_list::varList.add(varId);
      System.out.println("var id:" + varId);
    }
  }
  | ^(EQUAL_T pure_variable scalar)
  {
    int startIndex = $EQUAL_T.startIndex;
    int endIndex = $EQUAL_T.endIndex + 1;
    int varNameLeft = $pure_variable.tree.startIndex;
    int varNameRight = $pure_variable.tree.endIndex + 2;
    String varName = $pure_variable.text;
    Expression expr = $scalar.expr;
    
    VariableReference varId = new VariableReference(varNameLeft, varNameRight, varName);

    Expression assignExpr = new Assignment(startIndex, endIndex, varId, Assignment.OP_EQUAL, expr);
    if (inClassStatementList) {
      Object obj = new ASTNode[] {varId, expr};
      $class_statement::varList.add(obj);
    }
    else {
      $static_var_list::varList.add(assignExpr);
    }
  }
  ;
  
if_stat returns [Statement stat]
scope {
  List conditionList;
  List statementList;
  List tokenList;
}
@init {
  $if_stat::conditionList = new LinkedList();
  $if_stat::statementList = new LinkedList();
  $if_stat::tokenList = new LinkedList();
}
  : ^(IF_T  ^(CONDITION expression)
    ( 
      inner_statement_list?
      else_if_stat* (^(ELSE_T statement))?
    ))
    {
      int startIndex = $IF_T.startIndex;
      int endIndex = $IF_T.endIndex + 1;
      System.out.println("if:" + startIndex + " " + endIndex);
      Expression innerCondition = null; 
      Statement trueStatement = null;
      Statement falseStatement = $statement.stat;
      Iterator iterCond = $if_stat::conditionList.iterator(),
              iterIfTrueStat = $if_stat::statementList.iterator(),
              iterTokenList = $if_stat::tokenList.iterator();
      while (iterCond.hasNext()) {
         innerCondition = (Expression)iterCond.next();
         trueStatement = (Statement)iterIfTrueStat.next();
         int start = (Integer)iterTokenList.next();
         falseStatement = new IfStatement(start, trueStatement.sourceEnd(), innerCondition, trueStatement, falseStatement);
      }
      
      int sid = ((CommonToken)$inner_statement_list.tree.token).getStartIndex();
      int eid = ((CommonToken)$inner_statement_list.tree.token).getStopIndex() + 1;
      Block block = new Block(sid, eid, new LinkedList());
      if ($inner_statement_list.innerStatementList != null) {
        block.getStatements().clear();
        block.acceptStatements($inner_statement_list.innerStatementList);
      }
      $stat = new IfStatement(startIndex, endIndex, $expression.expr, block, falseStatement);  
    }
  ;

else_if_stat
  : ^(ELSEIF_T ^(CONDITION expression) inner_statement_list?)
  {
    int startIndex = $ELSEIF_T.startIndex;
    int endIndex = $ELSEIF_T.endIndex;
    $if_stat::conditionList.add($expression.expr);
    
    Block block = new Block(startIndex, endIndex, new LinkedList());
    if ($inner_statement_list.innerStatementList != null) {
      block.getStatements().clear();
      block.acceptStatements($inner_statement_list.innerStatementList);
    }
    $if_stat::statementList.add(block);
    $if_stat::tokenList.add(startIndex);
  }
  ;

 
switch_case_list returns [Block block]
scope {
  List list;
}
@init {
  $switch_case_list::list = new LinkedList();
}
  : case_list+
  {
    int startIndex = -1;
    int endIndex = -1;
    Block block = new Block(startIndex, endIndex, new LinkedList());
    block.getStatements().clear();
    block.acceptStatements($switch_case_list::list);
    $block = block;
  }
  ;

case_list
  : ^(CASE_T e=expression inner_statement_list?)
  {
    int startIndex = $CASE_T.startIndex;
    int endIndex = $CASE_T.endIndex + 1;
    List list = new LinkedList();
    if ($inner_statement_list.innerStatementList != null) {
      list = $inner_statement_list.innerStatementList;
    }
    SwitchCase switchCase = new SwitchCase(startIndex, endIndex, $e.expr, list, false);
    $switch_case_list::list.add(switchCase);
  }  
  | ^(DEFAULT_T inner_statement_list?)
  {
    int startIndex = $DEFAULT_T.startIndex;
    int endIndex = $DEFAULT_T.endIndex + 1;
    List list = new LinkedList();
    if ($inner_statement_list.innerStatementList != null) {
      list = $inner_statement_list.innerStatementList;
    }
    SwitchCase switchCase = new SwitchCase(startIndex, endIndex, null, list, true);
    $switch_case_list::list.add(switchCase);
  }
  ;
  
catch_branch_list returns [List catchList]
scope {
  List list;
}
@init {
  $catch_branch_list::list = new LinkedList();
}
  : catch_branch+
  {
    $catchList = $catch_branch_list::list;
  }
  ;  
 
catch_branch
  : ^(CATCH_T fully_qualified_class_name variable block)
  {
    int startIndex = $CATCH_T.startIndex;
    int endIndex = $CATCH_T.endIndex + 1;
    CatchClause catchClause = new CatchClause(startIndex, endIndex, $fully_qualified_class_name.type, (VariableReference)$variable.var, (Block)$block.stat);
    $catch_branch_list::list.add(catchClause);
  }
  ;

for_statement returns [Statement block]
 : inner_statement_list?
   {
      int startIndex = -1;
      int endIndex = -1;
      Block block = new Block(startIndex, endIndex, new LinkedList());
      if ($inner_statement_list.innerStatementList != null) {
        block.getStatements().clear();
        block.acceptStatements($inner_statement_list.innerStatementList);
      }

      $block = block;
      System.out.println("what block" + block);
    }
 ;
 
while_statement returns [Statement block]
  : inner_statement_list?
    {
      int startIndex = -1;
      int endIndex = -1;
      Block block = new Block(startIndex, endIndex, new LinkedList());
      if ($inner_statement_list.innerStatementList != null) {
        block.getStatements().clear();
        block.acceptStatements($inner_statement_list.innerStatementList);
      }
      $block = block;
    }
  ;
 
foreach_statement returns [Statement block]
  : inner_statement_list?
    {
      int startIndex = -1;
      int endIndex = -1;
      Block block = new Block(startIndex, endIndex, new LinkedList());
      if ($inner_statement_list.innerStatementList != null) {
        block.getStatements().clear();
        block.acceptStatements($inner_statement_list.innerStatementList);
      }
      $block = block;
    }
  ;
  
declare_statement returns [Statement block]
  : ^(BLOCK inner_statement_list?)
    {
      int startIndex = -1;
      int endIndex = -1;
      Block block = new Block(startIndex, endIndex, new LinkedList());
      if ($inner_statement_list.innerStatementList != null) {
			  block.getStatements().clear();
			  block.acceptStatements($inner_statement_list.innerStatementList);
			}
			$block = block;
    }
  ;
  
parameter_list returns [List parameterList]
scope {
  List paramList;
}
@init {
  $parameter_list::paramList = new LinkedList();
}
  : parameter+
  {
    $parameterList = $parameter_list::paramList;
  }
  ;
  
parameter
  : ^(PARAMETER (^(TYPE parameter_type))? CONST_T? pure_variable scalar?)
  {
    FormalParameter RESULT = null;
    TypeReference classType = $parameter_type.type;
    System.out.println("type:" + classType);
    int varLeft = $pure_variable.tree.startIndex;
    int varRight = $pure_variable.tree.endIndex + 1;
    String varName = $pure_variable.str;
    
    int startIndex = $PARAMETER.startIndex;
    int endIndex = $PARAMETER.endIndex + 1;
    if ($pure_variable.isRef) {
      varLeft++;
    }
    VariableReference var = new VariableReference(varLeft, varRight, varName, PHPVariableKind.LOCAL);
    
    System.out.println("variable par:" + var);
    
    if ($scalar.expr == null) {
      if ($pure_variable.isRef) {
        $parameter_list::paramList.add(new FormalParameterByReference(startIndex, endIndex, classType, var));
      }
      else {
        $parameter_list::paramList.add(new FormalParameter(startIndex, endIndex, classType, var));
      }
    }
    else {
      if ($pure_variable.isRef) {
        $parameter_list::paramList.add(new FormalParameterByReference(startIndex, endIndex, classType, var, $scalar.expr));
      }
      else {
        $parameter_list::paramList.add(new FormalParameter(startIndex, endIndex, classType, var, $scalar.expr));
      }
    }
  }
  ;
  
parameter_type returns [TypeReference type]
  : fully_qualified_class_name
  {
    $type = $fully_qualified_class_name.type;
  }
  | cast_option
  {
    int startIndex = ((CommonToken)$cast_option.tree.token).getStartIndex();
    int endIndex = ((CommonToken)$cast_option.tree.token).getStopIndex() + 1;
    $type = new TypeReference(startIndex, endIndex, $cast_option.text);
  }
  ;
 
variable_modifiers
  : 'var'
  | modifier
  ;
 
modifier returns [ModifierDocPair modifierVar]
scope {
  ModifierDocPair m;
}
@init {
  $modifier::m = new ModifierDocPair(Modifiers.AccDefault, null);
}
  : ('public' {$modifier::m = new ModifierDocPair(Modifiers.AccPublic, null);}
  | 'protected' {$modifier::m = new ModifierDocPair(Modifiers.AccProtected, null);}
  | 'private' {$modifier::m = new ModifierDocPair(Modifiers.AccPrivate, null);}
  | 'static'  {$modifier::m = new ModifierDocPair(Modifiers.AccStatic, null);}
  | 'abstract'  {$modifier::m = new ModifierDocPair(Modifiers.AccAbstract, null);}
  | 'final' {$modifier::m = new ModifierDocPair(Modifiers.AccFinal, null);}
  )*
  {
    $modifierVar = $modifier::m;
  }
  ;
   
directive returns [Object astNode]
  : ^(EQUAL_T IDENTIFIER expression)
  {
    if (inClassStatementList) {
	    int constNameleft = ((CommonToken)$IDENTIFIER.token).getStartIndex();
	    int constNameright = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;
	    String constName = $IDENTIFIER.text;
	    int exprLeft = ((CommonToken)($expression.tree.token)).getStartIndex();
	    int exprRight = ((CommonToken)($expression.tree.token)).getStopIndex() + 1;
	    Expression expr = $expression.expr;
	    
	    ConstantReference constId = new ConstantReference(constNameleft, constNameright, constName);
	    Object obj = new ASTNode[]{constId, expr};
	    $class_statement::constList.add(obj);
	  }
	  else {
      $topStatement::declareKey.add($IDENTIFIER.text);
      $topStatement::declareValue.add($expression.expr);
    }
  }
  ;
  
expr_list returns [List exprList]
scope {
  List list;
}
@init {
  $expr_list::list = new LinkedList();
  inExprList = true;
}
@after {
  inExprList = false;
}
 : expression+
 {
  $exprList = $expr_list::list;
 }
 ;
   
expression returns [Expression expr]
@init {
  SLAST ast = null;
}
//@after {
//  if (ast != null) {
//    $expression.tree = ast;
//  }
//}
  : ^(EXPR etop=expression)
  {
    $expr = $etop.expr;
    ast = $etop.tree;
    if (inExprList) {
      $expr_list::list.add($expr);
    }
  }
  | ^(OR_T e1=expression e2=expression)
  {
    int startIndex = $OR_T.startIndex;
    int endIndex = $OR_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_STRING_OR, expr2); 
  }
  | ^(XOR_T e1=expression e2=expression)
  {
    int startIndex = $XOR_T.startIndex;
    int endIndex = $XOR_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_STRING_XOR, expr2);
  }
  | ^(AND_T e1=expression e2=expression)
  {
    int startIndex = $AND_T.startIndex;
    int endIndex = $AND_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_STRING_AND, expr2);
  }
  | ^(EQUAL_T e1=expression e2=expression) 
  {
    int startIndex = $EQUAL_T.startIndex;
    int endIndex = $EQUAL_T.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    if ($e2.text.startsWith("&")) {
      $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_REF_EQUAL, expr);
    }
    else {
      $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_EQUAL, expr);
    }
  }
  | ^(PLUS_EQ e1=expression e2=expression)
  {
    int startIndex = $PLUS_EQ.startIndex;
    int endIndex = $PLUS_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_PLUS_EQUAL, expr);
  }
  | ^(MINUS_EQ e1=expression e2=expression)
  {
    int startIndex = $MINUS_EQ.startIndex;
    int endIndex = $MINUS_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_MINUS_EQUAL, expr);
  }
  | ^(MUL_EQ e1=expression e2=expression)
  {
    int startIndex = $MUL_EQ.startIndex;
    int endIndex = $MUL_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_MUL_EQUAL, expr);
  }
  | ^(DIV_EQ e1=expression e2=expression)
  {
    int startIndex = $DIV_EQ.startIndex;
    int endIndex = $DIV_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_DIV_EQUAL, expr);
  }
  | ^(DOT_EQ e1=expression e2=expression)
  {
    int startIndex = $DOT_EQ.startIndex;
    int endIndex = $DOT_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_CONCAT_EQUAL, expr);
  }
  | ^(PERCENT_EQ e1=expression e2=expression)
  {
    int startIndex = $PERCENT_EQ.startIndex;
    int endIndex = $PERCENT_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_MOD_EQUAL, expr);
  }
  | ^(BIT_AND_EQ e1=expression e2=expression)
  {
    int startIndex = $BIT_AND_EQ.startIndex;
    int endIndex = $BIT_AND_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_AND_EQUAL, expr);
  }
  | ^(BIT_OR_EQ e1=expression e2=expression)
  {
    int startIndex = $BIT_OR_EQ.startIndex;
    int endIndex = $BIT_OR_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_OR_EQUAL, expr);
  }
  | ^(POWER_EQ e1=expression e2=expression)
  {
    int startIndex = $POWER_EQ.startIndex;
    int endIndex = $POWER_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_XOR_EQUAL, expr);
  }
  | ^(LMOVE_EQ e1=expression e2=expression)
  {
    int startIndex = $LMOVE_EQ.startIndex;
    int endIndex = $LMOVE_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_SL_EQUAL, expr);
  }
  | ^(RMOVE_EQ e1=expression e2=expression)
  {
    int startIndex = $RMOVE_EQ.startIndex;
    int endIndex = $RMOVE_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, var, Assignment.OP_SR_EQUAL, expr);
  }
  | ^(QUESTION_T e1=expression ^(COLON_T e2=expression e3=expression))
  {
    int startIndex = $QUESTION_T.startIndex;
    int endIndex = $QUESTION_T.endIndex + 1;
    $expr = new ConditionalExpression(startIndex, endIndex, $e1.expr, $e2.expr, $e3.expr); 
  }  
  | ^(LOGICAL_OR_T e1=expression e2=expression)
  {
    int startIndex = $LOGICAL_OR_T.startIndex;
    int endIndex = $LOGICAL_OR_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_BOOL_OR, expr2); 
  }
  | ^(LOGICAL_AND_T e1=expression e2=expression)
  {
    int startIndex = $LOGICAL_AND_T.startIndex;
    int endIndex = $LOGICAL_AND_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_BOOL_AND, expr2);
  }
  | ^(BIT_OR_T e1=expression e2=expression)
  {
    int startIndex = $BIT_OR_T.startIndex;
    int endIndex = $BIT_OR_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_OR, expr2); 
  }
  | ^(POWER_T e1=expression e2=expression)
  {
    int startIndex = $POWER_T.startIndex;
    int endIndex = $POWER_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_XOR, expr2); 
  }
  | ^(REF_T e1=expression e2=expression)
  {
    int startIndex = $REF_T.startIndex;
    int endIndex = $REF_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
//    if (expr2 != null) {
      $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_AND, expr2);
//    }
//    else {
//      $expr = new ReferenceExpression(startIndex, endIndex, expr1);
//    }
  }
  | ^(UNARY_EXPR unary_symbol_list e=expression)
  {
    Iterator iter = $unary_symbol_list.symbolList.iterator();
    int symbolIndex;
    int startIndex = $UNARY_EXPR.startIndex + $unary_symbol_list.symbolList.size() - 1;
    int endIndex = $UNARY_EXPR.endIndex + 1;
    $expr = $e.expr;
    while (iter.hasNext()) {
      symbolIndex = (Integer)iter.next();
      System.out.println("symbolIndex: " + symbolIndex);
      switch(symbolIndex) {
        case 1:
          $expr = new UnaryOperation(startIndex--, endIndex, $expr, UnaryOperation.OP_PLUS);
          break;
        case 2:
          $expr = new UnaryOperation(startIndex--, endIndex, $expr, UnaryOperation.OP_MINUS);
          break;
        case 3:
          if ($expr instanceof VariableReference) {
            $expr = new ReferenceExpression(startIndex--, endIndex, $expr);
          }
          break;
        case 4:
          $expr = new UnaryOperation(startIndex--, endIndex, $expr, UnaryOperation.OP_TILDA);
          break;
        case 5:
          $expr = new UnaryOperation(startIndex--, endIndex, $expr, UnaryOperation.OP_NOT);
          break;
        default:
          break;
      }
    }
  }
  | ^(DOT_T e1=expression e2=expression)
  {
    int startIndex = $DOT_T.startIndex;
    int endIndex = $DOT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_CONCAT, expr2); 
  }
  | ^(EQUAL_EQUAL_T e1=expression e2=expression)
  {
    int startIndex = $EQUAL_EQUAL_T.startIndex;
    int endIndex = $EQUAL_EQUAL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_IS_EQUAL, expr2);
  }
  | ^(NOT_EQUAL_T e1=expression e2=expression)
  {
    int startIndex = $NOT_EQUAL_T.startIndex;
    int endIndex = $NOT_EQUAL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_IS_NOT_EQUAL, expr2);
  }
  | ^(EQUAL_EQUAL_EQUAL_T e1=expression e2=expression)
  {
    int startIndex = $EQUAL_EQUAL_EQUAL_T.startIndex;
    int endIndex = $EQUAL_EQUAL_EQUAL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_IS_IDENTICAL, expr2);
  }
  | ^(NOT_EQUAL_EQUAL_T e1=expression e2=expression)
  {
    int startIndex = $NOT_EQUAL_EQUAL_T.startIndex;
    int endIndex = $NOT_EQUAL_EQUAL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_IS_NOT_IDENTICAL, expr2);
  }
  | ^(LT_T e1=expression e2=expression)
  {
    int startIndex = $LT_T.startIndex;
    int endIndex = $LT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_RGREATER, expr2); 
  }
  | ^(MT_T e1=expression e2=expression)
  {
    int startIndex = $MT_T.startIndex;
    int endIndex = $MT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_LGREATER, expr2); 
  }
  | ^(LE_T e1=expression e2=expression)
  {
    int startIndex = $LE_T.startIndex;
    int endIndex = $LE_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_IS_SMALLER_OR_EQUAL, expr2); 
  }
  | ^(ME_T e1=expression e2=expression)
  {
    int startIndex = $ME_T.startIndex;
    int endIndex = $ME_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1 , InfixExpression.OP_IS_GREATER_OR_EQUAL, expr2); 
  }
  | ^(LSHIFT_T e1=expression e2=expression)
  {
    int startIndex = $LSHIFT_T.startIndex;
    int endIndex = $LSHIFT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1, InfixExpression.OP_SL, expr2); 
  }
  | ^(RSHIFT_T e1=expression e2=expression)
  {
    int startIndex = $RSHIFT_T.startIndex;
    int endIndex = $RSHIFT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1, InfixExpression.OP_SR, expr2); 
  }
  | ^(PLUS_T e1=expression e2=expression)
  {
    int startIndex = $PLUS_T.startIndex;
    int endIndex = $PLUS_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
//    if (expr2 != null) {
      $expr = new InfixExpression(startIndex, endIndex, expr1, InfixExpression.OP_PLUS, expr2);
//    }
//    else {
//      $expr = new UnaryOperation(startIndex, endIndex, $e1.expr , UnaryOperation.OP_PLUS);
//    }
  }
  | ^(MINUS_T e1=expression e2=expression)
  {
    int startIndex = $MINUS_T.startIndex;
    int endIndex = $MINUS_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
//    if (expr2 != null) {
      $expr = new InfixExpression(startIndex, endIndex, expr1, InfixExpression.OP_MINUS, expr2);
//    }
//    else {
//      $expr = new UnaryOperation(startIndex, endIndex, $e1.expr , UnaryOperation.OP_MINUS);
//    }
  }
  | ^(MUL_T e1=expression e2=expression)
  {
    int startIndex = $MUL_T.startIndex;
    int endIndex = $MUL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1, InfixExpression.OP_MUL, expr2);
  }
  | ^(DIV_T e1=expression e2=expression)
  {
    int startIndex = $DIV_T.startIndex;
    int endIndex = $DIV_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1, InfixExpression.OP_DIV, expr2);
  }
  | ^(PERCENT_T e1=expression e2=expression)
  {
    int startIndex = $PERCENT_T.startIndex;
    int endIndex = $PERCENT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, expr1, InfixExpression.OP_MOD, expr2);
  }
  | ^(CAST_EXPR cast_option e=expression)
  {
    int result = $cast_option.castOption;
    int startIndex = $CAST_EXPR.startIndex;
    int endIndex = $CAST_EXPR.endIndex + 1;
    Expression expr = $e.expr;
    switch(result) {
      case 1:
      case 2:
        $expr = new CastExpression(startIndex, endIndex, expr, CastExpression.TYPE_BOOL);
        break;
      case 3:
        $expr = new CastExpression(startIndex, endIndex, expr, CastExpression.TYPE_INT);
        break;
      case 6:
        $expr = new CastExpression(startIndex, endIndex, expr, CastExpression.TYPE_ARRAY);
        break;
      case 7:
        $expr = new CastExpression(startIndex, endIndex, expr, CastExpression.TYPE_OBJECT);
        break;
      case 8:
        $expr = new CastExpression(startIndex, endIndex, expr, CastExpression.TYPE_REAL);
        break;
      case 9:
        $expr = new CastExpression(startIndex, endIndex, expr, CastExpression.TYPE_STRING);
        break;
      case 10:
        $expr = new CastExpression(startIndex, endIndex, expr, CastExpression.TYPE_UNSET);
        break;
      default:
        $expr = new CastExpression(startIndex, endIndex, expr, CastExpression.TYPE_OBJECT);
        break;
    }
  }
//  | ^(TILDA_T e=expression)
//  {
//      int startIndex = $TILDA_T.startIndex;
//      int endIndex = $TILDA_T.endIndex + 1;
//      $expr = new UnaryOperation(startIndex, endIndex, $e.expr , UnaryOperation.OP_TILDA);
//  }
//  | ^(EXC_NOT_T e=expression)
//  {
//      int startIndex = $EXC_NOT_T.startIndex;
//      int endIndex = $EXC_NOT_T.endIndex + 1;
//      $expr = new UnaryOperation(startIndex, endIndex, $e.expr , UnaryOperation.OP_NOT);
//  }
  | ^(POSTFIX_EXPR e=expression plus_minus)
  {
      int startIndex = $POSTFIX_EXPR.startIndex;
      int endIndex = $POSTFIX_EXPR.endIndex + 1;
      if ($plus_minus.text.equals("++")) {
        $expr = new PostfixExpression(startIndex, endIndex, $e.expr , PostfixExpression.OP_INC);
      }
      else {
        $expr = new PostfixExpression(startIndex, endIndex, $e.expr , PostfixExpression.OP_DEC);
      } 
  }
  | ^(PREFIX_EXPR plus_minus+ e=expression)
  {
      int startIndex = $PREFIX_EXPR.startIndex;
      int endIndex = $PREFIX_EXPR.endIndex + 1;
      if ($plus_minus.text.equals("++")) {
        $expr = new PrefixExpression(startIndex, endIndex, $e.expr, PrefixExpression.OP_INC);
      }
      else {
        $expr = new PrefixExpression(startIndex, endIndex, $e.expr, PrefixExpression.OP_DEC);
      }
  }
  | ^(INSTANCEOF_T e=expression class_name_reference)
  {
    int startIndex = $INSTANCEOF_T.startIndex;
    int endIndex = $INSTANCEOF_T.endIndex + 1;
    $expr = new InstanceOfExpression(startIndex, endIndex, $e.expr, $class_name_reference.var); 
  }
  | AT_T? variable
  {
    $expr = $variable.var;
  }
  | AT_T? scalar
  {
    $expr = $scalar.expr;
    ast = $scalar.tree;
  }
  | list_decl
  {
    $expr = $list_decl.expr;
  }
  | ^(ARRAY_DECL array_pair_list?)
  {
    int startIndex = $ARRAY_DECL.startIndex;
    int endIndex = $ARRAY_DECL.endIndex + 1;
    if ($array_pair_list.text != null) {
       $expr = new ArrayCreation(startIndex, endIndex, $array_pair_list.arrayList);
    }
    else {
       $expr = new ArrayCreation(startIndex, endIndex, new LinkedList());
    }
  }
  | ^(NEW_T class_name_reference)
  {
    int startIndex = $NEW_T.startIndex;
    int endIndex = $NEW_T.endIndex + 1;
    Expression className = $class_name_reference.var;
    PHPCallArgumentsList ctor = $class_name_reference.parameterList;
    if (ctor == null) {
      System.out.println("ctor is null");
      ctor = new PHPCallArgumentsList();
      ctor.setStart(endIndex);
      ctor.setEnd(endIndex);
    }

    ClassInstanceCreation classInstanceCreation = new ClassInstanceCreation(startIndex, endIndex, className, ctor);
    $expr = classInstanceCreation;
  }
  
  | ^(CLONE_T variable)
  {
    int startIndex = $CLONE_T.startIndex;
    int endIndex = $CLONE_T.endIndex + 1;
    $expr = new CloneExpression(startIndex, endIndex, $variable.var);
  }
//  | ^(EXIT_T expression?)
  | ^(UNSET_T variable_list)
  {
     int startIndex = $UNSET_T.startIndex;
     int endIndex = $UNSET_T.endIndex + 1;
     int startOfVariableList = startIndex + "unset".length() + 1;
     SimpleReference simpleRef = new SimpleReference(startIndex, startIndex + "unset".length(), "unset");
     
     PHPCallArgumentsList list = new PHPCallArgumentsList();
     List variableList = $variable_list.variableList;
     Iterator iter = variableList.iterator();
     while (iter.hasNext()) {
	      Expression expr = (Expression)iter.next();
	      list.addNode(expr);
     }
     list.setStart(startOfVariableList);
     list.setEnd(endIndex - 1);     
     
     $expr = new PHPCallExpression(startIndex, endIndex, null, simpleRef, list);
  }
//  | lambda_function_declaration
  | BACKTRICKLITERAL
  {
    int startIndex = $BACKTRICKLITERAL.startIndex;
    int endIndex = $BACKTRICKLITERAL.endIndex + 1;
    $expr = new BackTickExpression(startIndex, endIndex, new LinkedList());
  }
  | ^(PRINT_T e=expression)
  {
    int startIndex = $PRINT_T.startIndex;
    int endIndex = $PRINT_T.endIndex + 1;
    
    PHPCallArgumentsList list = new PHPCallArgumentsList();
	  if ($e.expr != null) {
	    list.addNode($e.expr);
	    list.setStart($e.expr.sourceStart());
	    list.setEnd($e.expr.sourceEnd());
	  } else {
	    list.setStart(startIndex);
	    list.setEnd(startIndex);
	  }
	  
	  if (inExprList) {
	     $expr_list::list.remove($expr_list::list.size() - 1);
	  }
    
    SimpleReference name = new SimpleReference(startIndex, startIndex + "print".length(), "print");
	  $expr = new PHPCallExpression(startIndex, endIndex, null, name, list);
  }
//  | fully_qualified_class_name
  ;
  
unary_symbol_list returns [List symbolList]
scope {
  List list;
}
@init {
  $unary_symbol_list::list = new ArrayList();
}
  : unary_symbol+
  {
    $symbolList = $unary_symbol_list::list;
  }
  ;
  
unary_symbol
  : PLUS_T  {$unary_symbol_list::list.add(0,1);}
  | MINUS_T {$unary_symbol_list::list.add(0,2);}
  | REF_T   {$unary_symbol_list::list.add(0,3);}
  | TILDA_T {$unary_symbol_list::list.add(0,4);}
  | EXC_NOT_T {$unary_symbol_list::list.add(0,5);}
  ;

plus_minus
  : PLUS_PLUS_T
  | MINUS_MINUS_T
  ;

cast_option returns [int castOption]
  : 'bool'    {$castOption = 1;}
  | 'boolean' {$castOption = 2;}
  | 'int'     {$castOption = 3;}
  | 'float'   {$castOption = 4;}
  | 'double'  {$castOption = 5;}
  | 'array'   {$castOption = 6;}
  | 'object'  {$castOption = 7;}
  | 'real'    {$castOption = 8;}
  | 'string'  {$castOption = 9;}
  | UNSET_T   {$castOption = 10;}
  | CLONE_T   {$castOption = 11;}
  ;
 
//lambda_function_declaration
//  : ^(METHOD_DECL REF_T? parameter_list? (^(USE_T expr_list?))?
//      block)
//  ;
      
class_name_reference returns [Expression var, PHPCallArgumentsList parameterList]
scope {
  List list;
  List argList;
}
@init {
  $class_name_reference::list = new LinkedList();
  $class_name_reference::argList = new LinkedList();
}
  : dynamic_name_reference
  {
    $var = $dynamic_name_reference.var;
    $parameterList = $dynamic_name_reference.parameterList;
       
    Expression dispatcher = $var;
    Iterator iter = $class_name_reference::list.iterator();
    System.out.println("i ma vara;;;;" + $var);
    while (iter.hasNext()) {
      Expression property = (Expression)iter.next();
      dispatcher = createDispatch(dispatcher, property);
    }
    $var = dispatcher;
  }
  | fully_qualified_class_name
  {
    $var = $fully_qualified_class_name.type;
  }
  ;
  
dynamic_name_reference returns [Expression var, PHPCallArgumentsList parameterList]
  : base_variable_with_function_calls 
  {
     $var = $base_variable_with_function_calls.var;
     if ($base_variable_with_function_calls.type != null) {
        $var = $base_variable_with_function_calls.type;
     }
     $parameterList = $base_variable_with_function_calls.parameterList;
  }
  | ^(CALL v1=dynamic_name_reference obj=object_property ctor_arguments?)
  {
      $var = $v1.var;
       
      $class_name_reference::list.add($obj.expr);
      
      if ($ctor_arguments.argumentList != null) {
        $parameterList = $ctor_arguments.argumentList;
      }
  }
  ;

list_decl returns [Expression expr]
  : ^(LIST_T assignment_list?)
  {
    int startIndex = ($LIST_T).startIndex;
    int endIndex = $LIST_T.endIndex + 1;
    $expr = new ListVariable(startIndex, endIndex, $assignment_list.assignList);
  }
  ;

assignment_list returns [List assignList]
scope {
  List list;
}
@init {
  $assignment_list::list = new LinkedList();
}
  : assignment_element+
  {
    $assignList = $assignment_list::list;
  }
  ;

assignment_element
  : variable
  {
    $assignment_list::list.add($variable.var);
  }
  | list_decl
  {
    $assignment_list::list.add($list_decl.expr);
  }
  ;
  
array_pair_list returns [List arrayList]
scope {
  List list;
}
@init {
  $array_pair_list::list = new LinkedList();
}
  : array_pair_element+
  {
    $arrayList = $array_pair_list::list;
  }
  ;
  
array_pair_element
  : ^(ARROW_T e1=expression e2=expression)
  {
    int startIndex = $ARROW_T.startIndex;
    int endIndex = $ARROW_T.endIndex + 1;
    Expression key = $e1.expr;
    Expression value = $e2.expr; 
    ArrayElement element = new ArrayElement(startIndex, endIndex, key, value);
    $array_pair_list::list.add(element);
  }
  | e=expression
  {
    int startIndex = $e.expr.sourceStart();
    int endIndex = $e.expr.sourceEnd();
    Expression expr = $expression.expr;
    ArrayElement element = new ArrayElement(startIndex, endIndex, expr);
    $array_pair_list::list.add(element);
  }
  ; 
    
variable returns [Expression var, PHPCallArgumentsList parameterList]
scope {
  List list;
}
@init {
  $variable::list = new LinkedList();
}
  : variable_temp
  {
    Iterator iter = $variable::list.iterator();
    Expression dispatcher = $variable_temp.var;
    iter = $variable::list.iterator();
    while (iter.hasNext()) {
      Expression property = (Expression)iter.next();
      if (inVarList) {
        if ($variable_list::varList.contains(property)) {
           $variable_list::varList.remove(property);
        }
      }
      System.out.println("property:" + property);
      dispatcher = createDispatch(dispatcher, property);
    }
    $var = dispatcher;
    if (inVarList) {
      $variable_list::varList.add($var);
    }
    
    System.out.println("whatXXX:" + $var);
  }
  ;  

variable_temp returns [Expression var, PHPCallArgumentsList parameterList]
  : base_variable_with_function_calls 
  {
     $var = $base_variable_with_function_calls.var;
     $parameterList = $base_variable_with_function_calls.parameterList;
  }
  | ^(CALL v1=variable_temp obj=object_property ctor_arguments?)
  {
      $var = $v1.var;

      int startIndex = $CALL.startIndex;
      int endIndex = $CALL.endIndex + 1;

      Expression firstVarProperty = null;
      if ($ctor_arguments.argumentList == null) {
        firstVarProperty = $obj.expr;
        if ($obj.simpleRef != null) {
          firstVarProperty = $obj.simpleRef;
        }
      }
      else {
        if ($obj.simpleRef.getClass().equals(SimpleReference.class)) {
		      firstVarProperty = new PHPCallExpression(startIndex, endIndex, null, $obj.simpleRef, $ctor_arguments.argumentList);
		    } else {
		      firstVarProperty = new ReflectionCallExpression(startIndex, endIndex, null, (SimpleReference)$obj.simpleRef, $ctor_arguments.argumentList);
		    }
      }
      
      System.out.println("first var:" + firstVarProperty);
      $variable::list.add(firstVarProperty);
  }
  ;
  
base_variable_with_function_calls returns [Expression var, PHPCallArgumentsList parameterList, TypeReference type, Expression functionInvocation]
  : ^(VAR_DECL fully_qualified_class_name? obj=object_property ctor_arguments?
  {
    int startIndex = $VAR_DECL.startIndex;
    int endIndex = $VAR_DECL.endIndex + 1;
    System.out.println("varid: " + startIndex + " " + endIndex);
    $var = $object_property.expr;
    TypeReference type = $fully_qualified_class_name.type;
    
    System.out.println("vardddd:" + $var);
    
    if (type != null) {
        $var = new StaticFieldAccess(startIndex, endIndex, $fully_qualified_class_name.type, $object_property.expr);
    }
    
    if ($ctor_arguments.argumentList != null) {
      PHPCallArgumentsList parameters = $ctor_arguments.argumentList;
  //    parameters.addNode($var);
      $parameterList = parameters;
      $var = new ReflectionCallExpression(startIndex, endIndex, null, $obj.expr, parameters);
      
//      $var = new ReflectionStaticMethodInvocation(startIndex, endIndex, type, $obj.expr, parameters);
    }
    else {
      $parameterList = new PHPCallArgumentsList();
      $parameterList.setStart(startIndex);
      $parameterList.setEnd(startIndex);
      
    }
  }    
   )
  | ^(CALL fully_qualified_class_name ctor_arguments)
  {
    int startIndex = $CALL.startIndex;
    int endIndex = $CALL.endIndex + 1;
    
    TypeReference type = $fully_qualified_class_name.type;    
    PHPCallArgumentsList parameters = $ctor_arguments.argumentList;
    
    $type = type;
    $parameterList = parameters;
    
    if ($fully_qualified_class_name.simpleRef != null) {
      $var = new StaticMethodInvocation(startIndex, endIndex, (TypeReference)$fully_qualified_class_name.type, $fully_qualified_class_name.simpleRef, parameters);
    }
    else {
    
	    int functionNameLeft= ((CommonToken)$fully_qualified_class_name.tree.token).getStartIndex();
	    int functionNameRight= ((CommonToken)$fully_qualified_class_name.tree.token).getStopIndex() + 1;
	    String functionName = $fully_qualified_class_name.text;
	    SimpleReference name = new SimpleReference(functionNameLeft, functionNameRight, functionName);
	    $var = new PHPCallExpression(startIndex, endIndex, null, name, parameters);
	  }    
  }
//  | ^(VAR_DECL ^('::' STATIC reference_variable))
//  | fully_qualified_class_name
  ;
  
dollars returns [int size, String str]
@init {
  $size = 0;
  $str = "";
}
  : (DOLLAR_T{$size++; $str += $DOLLAR_T.text;})* 
  ;
    
object_property returns [String str, Expression expr, SimpleReference simpleRef]
@init {
  int startIndex = -1;
  int endIndex = -1;
  SLAST ast = null;
}
@after {
  $object_property.tree = $object_property.start;
}
  : ^(VAR dollars IDENTIFIER)
    {
        int size = $dollars.size;
        
        $str = $IDENTIFIER.text;
        startIndex = ((CommonToken)$IDENTIFIER.token).getStartIndex();
        endIndex = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;

        if (!$dollars.str.equals("")) {
          $str = "$" + $IDENTIFIER.text;
          startIndex--;
        }
        
        VariableReference variableRef = new VariableReference(startIndex, endIndex, $str ,PHPVariableKind.LOCAL);
        $expr = variableRef;
        
        $simpleRef = new SimpleReference(startIndex, endIndex, $str);
        ast = $VAR.tree;
        
        if ($dollars.size > 1) {
          for (int i = 0; i < $dollars.size - 1; i++) {
				    $expr = new ReflectionVariableReference(startIndex - i - 1, endIndex, $expr);       
				  }
        }
    }
  | ^(VAR dollars expression)
  {
    startIndex = $VAR.startIndex;
    endIndex = $VAR.endIndex + 1;
    $expr = new ReflectionVariableReference(startIndex, endIndex, $expression.expr);
    ast = $VAR.tree;
  }
  | ^(INDEX obj=object_property expression?)
  {
    startIndex = $INDEX.startIndex;
    endIndex = $INDEX.endIndex + 1;
   
   
    Expression varName = $obj.expr;
    Expression index = $expression.expr;
    if ($expression.text != null) {
      if(varName.getKind() == ExpressionConstants.E_IDENTIFIER) {
         $expr = new ArrayVariableReference(startIndex, endIndex, ((SimpleReference)varName).getName(), index, ArrayVariableReference.VARIABLE_ARRAY);
      } else {
         $expr = new ReflectionArrayVariableReference(startIndex, endIndex, varName, index, ReflectionArrayVariableReference.VARIABLE_ARRAY);
      }
    }
    else {
       $expr = new ArrayVariableReference(startIndex, endIndex, ((SimpleReference)varName).getName(), index, ArrayVariableReference.VARIABLE_ARRAY);
    }
    ast = $INDEX.tree;
  }
  | ^(HASH_INDEX obj=object_property expression?)
  {
    startIndex = $HASH_INDEX.startIndex;
    endIndex = $HASH_INDEX.endIndex + 1;
   
    if (startIndex == 0 && $expression.text != null) {
       startIndex = $obj.tree.startIndex;
       endIndex = $obj.tree.endIndex + $expression.text.length();
    }
    Expression varName = $obj.expr;
    Expression index = $expression.expr;
    if ($expression.text != null) {
      if(varName.getKind() == ExpressionConstants.E_IDENTIFIER) {
         $expr = new ArrayVariableReference(startIndex, endIndex, ((SimpleReference)varName).getName(), index, ArrayVariableReference.VARIABLE_HASHTABLE);
      } else {
         $expr = new ReflectionArrayVariableReference(startIndex, endIndex, varName, index, ReflectionArrayVariableReference.VARIABLE_HASHTABLE);
      }
    }
    else {
       $expr = new ArrayVariableReference(startIndex, endIndex, ((SimpleReference)varName).getName(), index, ArrayVariableReference.VARIABLE_HASHTABLE);
    }
    ast = $HASH_INDEX.tree;
  }
  ;
  
  
ctor_arguments returns [PHPCallArgumentsList argumentList]
@init {
  SLAST ast = null;
}
@after {
  $ctor_arguments.tree = ast;
}
  :  ^(ARGU expr_list?)
  {
    int startIndex = $ARGU.startIndex + 1;
    int endIndex = $ARGU.endIndex;

	  $argumentList = new PHPCallArgumentsList();
	  $argumentList.setStart(startIndex);
	  $argumentList.setEnd(endIndex);
	  
	  if ($expr_list.exprList != null) {
	    Iterator iter = $expr_list.exprList.iterator();
	    while (iter.hasNext()) {
	      $argumentList.addNode((Expression)iter.next());
	    }
	  }
	  ast = $ARGU;
  }
  ;
  
pure_variable returns [String str, Boolean isRef]
@init {
  SLAST ast;
  $isRef = false;
}
@after {
  $pure_variable.tree = ast;
}
  : ^(VAR_DECL REF_T? DOLLAR_T+ IDENTIFIER)
  {
    if ($REF_T.text != null) {
      $isRef = true;
    }
    $str = "$" + $IDENTIFIER.text;
    ast = $VAR_DECL;
  }
  ;
  
scalar returns [Expression expr]
@init {
  SLAST ast = null;
}
//@after {
//  $scalar.tree = ast;
//}
  : ^(SCALAR constant)
  {
    if ($constant.expr != null ) {
      $expr = $constant.expr;
    }
    else {
      $expr = $constant.scalar;
    }
    ast = $constant.tree;
  }
  ;
  
constant returns [Scalar scalar, Expression expr]
  :   INTLITERAL
  {
    CommonToken token = (CommonToken)$INTLITERAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    $scalar = new Scalar(startIndex, endIndex, $INTLITERAL.text, Scalar.TYPE_INT);
  }
  |   FLOATLITERAL
  {
    CommonToken token = (CommonToken)$FLOATLITERAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    $scalar = new Scalar(startIndex, endIndex, $FLOATLITERAL.text, Scalar.TYPE_REAL);
  }
  |   STRINGLITERAL
  {
    CommonToken token = (CommonToken)$STRINGLITERAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    $scalar = new Scalar(startIndex, endIndex, $STRINGLITERAL.text, Scalar.TYPE_STRING);
  }
//  |   QUOTE_STRING
//  {
//    CommonToken token = (CommonToken)$QUOTE_STRING.token;
//    int startIndex = token.getStartIndex();
//    int endIndex = token.getStopIndex() + 1;
//    $expr = parseQuote($QUOTE_STRING.text.substring(1, $QUOTE_STRING.text.length() - 1), startIndex, endIndex, Quote.QT_QUOTE);
//    if ($expr == null) {
//      $scalar = new Scalar(startIndex, endIndex, $QUOTE_STRING.text, Scalar.TYPE_STRING);
//    }
//  }
  |   REALLITERAL
  {
    CommonToken token = (CommonToken)$REALLITERAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    System.out.println("real id:" + startIndex + " " + endIndex);
    $scalar = new Scalar(startIndex, endIndex, $REALLITERAL.text, Scalar.TYPE_REAL);
  }
  
  |   DOUBLELITERRAL
  {
    CommonToken token = (CommonToken)$DOUBLELITERRAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    $scalar = new Scalar(startIndex, endIndex, $DOUBLELITERRAL.text, Scalar.TYPE_REAL);
  }
  |   common_scalar
  {
      CommonToken token = (CommonToken)$common_scalar.tree.token;
      int startIndex = token.getStartIndex();
      int endIndex = token.getStopIndex() + 1;
      $scalar = new Scalar(startIndex, endIndex, $common_scalar.text, Scalar.TYPE_SYSTEM);
  }
  |   IDENTIFIER
  {
      CommonToken token = (CommonToken)$IDENTIFIER.token;
	    int startIndex = token.getStartIndex();
	    int endIndex = token.getStopIndex() + 1;
	    $scalar = new Scalar(startIndex, endIndex, $IDENTIFIER.text, Scalar.TYPE_STRING);
  }
  |   fully_qualified_class_name
  {
      if ($fully_qualified_class_name.constant != null) {
        $expr = $fully_qualified_class_name.constant;
      }
      else {
        $expr = $fully_qualified_class_name.type;
      }
  }
  |  HERE_DOC
  {
    CommonToken token = (CommonToken)$HERE_DOC.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex();
    int index1 = "<<<Heredoc".length() + startIndex + 1;
    int index2 = $HERE_DOC.text.lastIndexOf("Heredoc") - "Heredoc;".length();

    if (index1 < index2) {
       Scalar scalar = new Scalar(index1, endIndex - 1, $HERE_DOC.text.substring(index1, index2), Scalar.TYPE_STRING);
       List list = new LinkedList();
       list.add(scalar);
       $expr = new Quote(startIndex, endIndex, list, Quote.QT_HEREDOC);
	  }
	  else {
	     $expr = new Quote(startIndex, endIndex, new LinkedList(), Quote.QT_HEREDOC);
	  }
  }
  ;  
  
common_scalar
  : '__CLASS__'
  | '__DIR__'
  | '__FILE__'
  | '__FUNCTION__'
  | '__METHOD__'
  | '__LINE__'
  | '__NAMESPACE__'
  ;