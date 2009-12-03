tree grammar TreePHP;

options {
  tokenVocab=CompilerAst;
  ASTLabelType=SLAST;
  output=AST;
}

@header {
  package org.eclipse.php.internal.core.ast.scanner.php5;
  import org.eclipse.php.internal.core.compiler.ast.parser.SLAST;
  import org.eclipse.php.internal.core.ast.scanner.AbstractTreePHP;
  
  import org.eclipse.php.internal.core.ast.nodes.*;
  import org.eclipse.dltk.ast.Modifiers;
  import java.util.*;
  import org.antlr.runtime.BitSet;
}

@members {
  Program program;
  
  Expression quoteExpr;
  
  boolean inExprList = false;
  
  boolean inClassStatementList = false;
  
  boolean inVarList = false;
  
  boolean inNameList = false;
  
  boolean inPrintExpr = false;
  
  boolean inArray = false;
   
  AST ast;
  
  public final void setAST (AST ast) {
    this.ast = ast;
  }  
  
  public Expression getQuoteExpression() {
      return quoteExpr;
  }
  
  public Program getProgram() {
    return program;
  }
  public Dispatch createDispatch(VariableBase dispatcher, VariableBase property) {
    Dispatch dispatch = null;
    if (property instanceof Variable) {
      dispatch = new FieldAccess(dispatcher.getStart(), property.getEnd(), ast, dispatcher, (Variable)property);
    } else if (property instanceof FunctionInvocation) {
      dispatch = new MethodInvocation(dispatcher.getStart(), property.getEnd(), ast ,dispatcher, (FunctionInvocation)property);
    } else {
      throw new IllegalArgumentException();
    }
    return dispatch;
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
    
    
//    public Expression parseQuote(String inputStr, int originalStartIndex, int originalEndIndex, int type) {
//      
//        if (inputStr.contains("'")) {
//          inputStr = inputStr.replace("'", "");
//        }
//      
//        String [] stats = inputStr.split("( |\n|\t)+",0);
//     
//        Expression expr = null;
//        
//        List list = new LinkedList();
//        
//        int startIndex = originalStartIndex;
//        
//        int endIndex = 0; 
//        Quote quote = null;
//
//        for (String str : stats) {
//          
//          String code = "<?php " + str + ";?>";
//          
//          System.out.println("code:" + code);
//          
//          try {
//          
//              CharStream input = new ANTLRStringStream(code);
//          
//              CompilerAstLexer lexer = new CompilerAstLexer(input);
//              
//              CommonTokenStream tokens = new CommonTokenStream(lexer);
//              
//              CompilerAstParser parser = new CompilerAstParser(tokens);
//              
//              parser.setTreeAdaptor(tokenAdaptor);
//              
//              if (inputStr.indexOf(str) >= endIndex) {
//                  startIndex = inputStr.indexOf(str) + originalStartIndex + 1;
//              }
//              
//              endIndex = startIndex + str.length();
//                
//              CompilerAstParser.php_source_return r = parser.php_source();
//          
//              SLAST t = (SLAST) r.getTree(); // get tree result
//              
//              CommonTreeNodeStream nodes = new CommonTreeNodeStream(tokenAdaptor, t);
//              
//              nodes.setTokenStream(tokens);
//        
//              TreePHP def = new TreePHP(nodes);
//              
//              def.setTreeAdaptor(tokenAdaptor);
//              
//              TreePHP.php_source_return result = def.php_source();
//              
//              expr = def.getQuoteExpression();
//              
////              expr.setStart(startIndex);
////              
////              expr.setEnd(endIndex);
//            
//            }  catch (Exception ex) {
//              System.out.println("exception in quote");
//              System.out.println(ex);
//              
//              expr = new Scalar(startIndex, endIndex, ast, str, Scalar.TYPE_UNKNOWN);
//            }
//                          
//            list.add(expr);
//              
//            startIndex = endIndex;
//          }
//          
//        quote = new Quote(originalStartIndex, originalEndIndex, ast, list, type);
//
//        return quote;
//    }
}

php_source
  :  ^(ModuleDeclaration top_statement_list?)
  {
    int startIndex = $ModuleDeclaration.startIndex;
    int endIndex = $ModuleDeclaration.endIndex + 1;
 
    program = new Program(startIndex, endIndex, ast, $top_statement_list.topStatList, new LinkedList());
  }
  ;

top_statement_list returns [List topStatList]
scope {
  List list;
}
@init {
  $top_statement_list::list = new LinkedList();
}
  : top_statement+
  {
    $topStatList = $top_statement_list::list;
  }
  ;

top_statement
  : statement
  {
    Statement stat = $statement.stat;
    $top_statement_list::list.add(stat);
  }
  | function_declaration_statement
  {
    $top_statement_list::list.add($function_declaration_statement.stat);
  }
  | class_declaration_statement
  {
    ClassDeclaration classDeclaration = $class_declaration_statement.classDeclaration;
    if (classDeclaration != null) {
        $top_statement_list::list.add(classDeclaration);
    }
    else {
      InterfaceDeclaration interfaceDeclaration = $class_declaration_statement.interfaceDeclaration;
      $top_statement_list::list.add(interfaceDeclaration);
    }
  }
  | halt_compiler_statement
  ;

inner_statement_list returns [List innerStatementList, Statement block]
scope {
  List list;
}
@init {
  $inner_statement_list::list = new LinkedList();
}
  : (inner_statement)+
  {
    $innerStatementList = $inner_statement_list::list;
    if ($innerStatementList.size() == 1) {
      if ($innerStatementList.get(0) instanceof Block) {
        $block = (Statement)$innerStatementList.get(0);
      }
      else if ($innerStatementList.get(0) instanceof EmptyStatement) {
        $block = (Statement)$innerStatementList.get(0);
      }
    }
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
    else {
      $inner_statement_list::list.add($class_declaration_statement.interfaceDeclaration);
    }
  }
  | halt_compiler_statement
  ;
  
halt_compiler_statement
  : '__halt_compiler'
  ;
  
class_declaration_statement returns [ClassDeclaration classDeclaration, InterfaceDeclaration interfaceDeclaration]
@init {
  CommonToken token;
}
  : ^(CLASS_T class_entr_type? IDENTIFIER (^(EXTENDS_T fully_qualified_class_name))? (^(IMPLEMENTS_T fully_qualified_class_name_list))?
      class_body)
    {
      int startIndex = $CLASS_T.startIndex;
      int endIndex = $CLASS_T.endIndex + 1;
      
      int modifier = 0;
      if ($class_entr_type.text != null) {
        modifier = $class_entr_type.modifierType;
      }
      token = (CommonToken)$IDENTIFIER.token;
      int classNameLeft = 0;
      int classNameRight = 0;
      if (token != null) {
         classNameLeft = token.getStartIndex();
         classNameRight = token.getStopIndex() + 1;
      }
      String className = $IDENTIFIER.text;
      Identifier classId = new Identifier(classNameLeft, classNameRight, ast, className);
        
      Identifier superClass = $fully_qualified_class_name.idClassName;
      
      List interfaces = new LinkedList();
      if ($fully_qualified_class_name_list.list != null) {
        interfaces = $fully_qualified_class_name_list.list;
      }

      $classDeclaration = new ClassDeclaration(startIndex ,endIndex, ast, modifier, classId, superClass, interfaces, $class_body.block);
    }
  | ^(INTERFACE_T IDENTIFIER (^(EXTENDS_T fully_qualified_class_name_list))? class_body)
  {
      int startIndex = $INTERFACE_T.startIndex;
      int endIndex = $INTERFACE_T.endIndex + 1;
      
      token = (CommonToken)$IDENTIFIER.token;
      int classNameLeft = 0;
      int classNameRight = 0;
      if (token != null) {
         classNameLeft = token.getStartIndex();
         classNameRight = token.getStopIndex() + 1;
      }
      String className = $IDENTIFIER.text;
      Identifier classId = new Identifier(classNameLeft, classNameRight, ast, className);
          
      List interfaces = new LinkedList();
      if ($fully_qualified_class_name_list.list != null) {
        interfaces = $fully_qualified_class_name_list.list;
      }

      $interfaceDeclaration = new InterfaceDeclaration(startIndex ,endIndex, ast, classId, interfaces, $class_body.block);  
  }
  ;
  
class_entr_type returns [int modifierType]
  : 'abstract'  {$modifierType = 1;}
  | 'final'     {$modifierType = 2;}
  ;
  
class_body returns [Block block]
  : ^(CLASS_BODY class_statement_list?)
  {
      int blockLeft = $CLASS_BODY.startIndex;
      int blockRight = $CLASS_BODY.endIndex + 1;
      Block block = new Block(blockLeft, blockRight, ast, new LinkedList());
      if ($class_statement_list.clist != null) {
        block = new Block(blockLeft, blockRight, ast, $class_statement_list.clist); 
      }
      $block = block;
      
      System.out.println("classbody:" + $block); 
  }
  ;
  
class_statement_list returns [List clist]
scope {
  List list;
}
@init {
  $class_statement_list::list = new LinkedList();
}
  : class_statement+
  {
    $clist = $class_statement_list::list;
  }
  ;
 
class_statement
scope {
  List constList;
  List varList;
}
@init {
  $class_statement::constList = new LinkedList();
  $class_statement::varList = new LinkedList();
  CommonToken token;
}
  : ^(FIELD_DECL {inClassStatementList = true;} variable_modifiers static_var_element+ {inClassStatementList = false;})
    {
        int startIndex = $FIELD_DECL.startIndex;
		    int endIndex = $FIELD_DECL.endIndex + 1;
		    List varList = $class_statement::varList;
		    int modifier = $variable_modifiers.modifierValue;
		    FieldsDeclaration fieldsDeclaration = new FieldsDeclaration(startIndex, endIndex, ast, modifier, varList);
        $class_statement_list::list.add(fieldsDeclaration);		    
    }
  | ^(METHOD_DECL modifier REF_T? IDENTIFIER parameter_list?
    ( block
    {
      int modifier = $modifier.modifierValue;
      int startIndex = $METHOD_DECL.startIndex;
	    int endIndex = $METHOD_DECL.endIndex + 1;
	    Boolean isReference = false;
	    if ($REF_T.text != null) {
	       isReference = true;
	    }
	    int functionNameLeft = ((CommonToken)$IDENTIFIER.token).getStartIndex();
	    int functionNameRight = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;
	    String functionName = $IDENTIFIER.text;
	      
	    List paramList = new LinkedList();
	    if ($parameter_list.parameterList != null) {
	       paramList = $parameter_list.parameterList;
	    }
	
	    Identifier functionId = new Identifier(functionNameLeft, functionNameRight, ast, functionName);
	    FunctionDeclaration function = new FunctionDeclaration(startIndex + $modifier.text.length() + 1, endIndex, ast, functionId, paramList, (Block)$block.stat, isReference);
	    MethodDeclaration methodDeclaration = new MethodDeclaration(startIndex, endIndex, ast, modifier, function, true);
	    $class_statement_list::list.add(methodDeclaration);
    }    
    | EMPTYSTATEMENT 
    { 
      int modifier = $modifier.modifierValue;
      int startIndex = $METHOD_DECL.startIndex;
      int endIndex = $METHOD_DECL.endIndex + 1;
      Boolean isReference = false;
      if ($REF_T.text != null) {
         isReference = true;
      }
    
      int functionNameLeft = ((CommonToken)$IDENTIFIER.token).getStartIndex();
      int functionNameRight = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;
      String functionName = $IDENTIFIER.text;
        
      List paramList = new LinkedList();
      if ($parameter_list.parameterList != null) {
         paramList = $parameter_list.parameterList;
      }
    
      int blockLeft = ((CommonToken)$EMPTYSTATEMENT.token).getStartIndex();
      int blockRight = ((CommonToken)$EMPTYSTATEMENT.token).getStopIndex() + 1;
      Identifier functionId = new Identifier(functionNameLeft, functionNameRight, ast, functionName);
      FunctionDeclaration function = new FunctionDeclaration(startIndex + $modifier.text.length() + 1, endIndex, ast, functionId, paramList, null, isReference);
      MethodDeclaration methodDeclaration = new MethodDeclaration(startIndex, endIndex, ast, modifier, function, true);
      
      $class_statement_list::list.add(methodDeclaration);
    }
    | ERROR_TOKEN
    ))
  | ^(FIELD_DECL {inClassStatementList = true;} CONST_T directive+ {inClassStatementList = false;})
  {
    int startIndex = $FIELD_DECL.startIndex;
    int endIndex = $FIELD_DECL.endIndex + 1;
    List constList = $class_statement::constList;
    ConstantDeclaration classConstantDeclaration = new ConstantDeclaration(startIndex, endIndex, ast, constList);
    $class_statement_list::list.add(classConstantDeclaration);
  }
  | ^(ERROR_TOKEN variable?) 
  {     
      int type = $ERROR_TOKEN.specificType;
      int startIndex = $ERROR_TOKEN.startIndex;
      int endIndex = $ERROR_TOKEN.endIndex + 1;
      Statement stat = new ASTError(startIndex, endIndex, ast);
      List errorList = new LinkedList();
      errorList.add(stat);
      Block block = new Block(startIndex, endIndex, ast, errorList);
  }
  ;

function_declaration_statement returns [Statement stat]
  : ^(METHOD_DECL REF_T? IDENTIFIER parameter_list? block)
  {
    int startIndex = $METHOD_DECL.startIndex;
    int endIndex = $METHOD_DECL.endIndex + 1;
    Boolean isReference = false;
    if ($REF_T.text != null) {
       isReference = true;
    }
    int functionNameLeft = ((CommonToken)$IDENTIFIER.token).getStartIndex();
    int functionNameRight = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;
    String functionName = $IDENTIFIER.text;
      
    List paramList = new LinkedList();
    if ($parameter_list.parameterList != null) {
       paramList = $parameter_list.parameterList;
    }

    Identifier functionId = new Identifier(functionNameLeft, functionNameRight, ast, functionName);
    $stat = new FunctionDeclaration(startIndex, endIndex, ast, functionId, paramList, (Block)$block.stat, isReference);
   } 
  ; 

block returns [Statement stat, List statList]//returns [Statement stat, List statList]
  : ^(BLOCK inner_statement_list?)
     {
      int startIndex = $BLOCK.startIndex;
      int endIndex = $BLOCK.endIndex + 1;
      
      Block block = new Block(startIndex, endIndex, ast, new LinkedList());
      if ($inner_statement_list.innerStatementList != null) {
        block = new Block(startIndex, endIndex, ast, $inner_statement_list.innerStatementList);
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
//      int startIndex = $STATEMENT.startIndex;
//      int endIndex = $STATEMENT.endIndex + 1;
      SLAST ast = $topStatement.start;
      int startIndex = ast.startIndex;
      int endIndex = ast.endIndex + 1;
      $stat = new ExpressionStatement(startIndex, endIndex, this.ast, $topStatement.expr);
      System.out.println("$stat:" + $stat);
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
    $stat = new WhileStatement(startIndex, endIndex, ast, $e.expr, $while_statement.block);   
  }  
  | ^(DO_T ^(CONDITION e=expression) statement)
  {
    int startIndex = $DO_T.startIndex;
    int endIndex = $DO_T.endIndex + 1;
    $stat = new DoStatement(startIndex, endIndex, ast, $e.expr, $statement.stat);      
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
    $stat = new ForStatement(startIndex, endIndex, ast, initList, condList, changeList, $s1.block);
  }
  | ^(SWITCH_T ^(CONDITION e=expression) switch_case_list)
  {
    int startIndex = $SWITCH_T.startIndex;
    int endIndex = $SWITCH_T.endIndex + 1;
    $stat = new SwitchStatement(startIndex, endIndex, ast, $e.expr, $switch_case_list.block);
  }
  | ^(BREAK_T expression?)
  {
    int startIndex = $BREAK_T.startIndex;
    int endIndex = $BREAK_T.endIndex + 1;
    if ($expression.expr != null) {
      $stat = new BreakStatement(startIndex, endIndex, ast, $expression.expr);
    }
    else {
      $stat = new BreakStatement(startIndex, endIndex, ast);
    }
  }
  | ^(CONTINUE_T e=expression?)
  {
    int startIndex = $CONTINUE_T.startIndex;
    int endIndex = $CONTINUE_T.endIndex + 1;
    $stat = new ContinueStatement(startIndex, endIndex, ast);
    if ($e.expr != null) {
      $stat = new ContinueStatement(startIndex, endIndex, ast, $e.expr);
    }
  }
  | ^(RETURN_T e=expression?)
  {
    int startIndex = $RETURN_T.startIndex;
    int endIndex = $RETURN_T.endIndex + 1;
    $stat = new ReturnStatement(startIndex, endIndex, ast);
    if ($e.expr != null) {
      $stat = new ReturnStatement(startIndex, endIndex, ast, $e.expr);
    }
  }
  | ^(GLOBAL_T variable_list)
  {
    int startIndex = $GLOBAL_T.startIndex;
    int endIndex = $GLOBAL_T.endIndex + 1;
    $stat = new GlobalStatement(startIndex, endIndex, ast, $variable_list.variableList);
  }
  | ^(STATIC_T static_var_list)
  {
    int startIndex = $STATIC_T.startIndex;
    int endIndex = $STATIC_T.endIndex + 1;
    $stat = new StaticStatement(startIndex, endIndex, ast, $static_var_list.staticVarList);
  }
  | ^(ECHO_T expr_list)
  {
    int startIndex = $ECHO_T.startIndex;
    int endIndex = $ECHO_T.endIndex + 1;
    $stat = new EchoStatement(startIndex, endIndex, ast, $expr_list.exprList); 
  }
  | ^(EMPTYSTATEMENT SEMI_COLON)
  {
    int startIndex = $EMPTYSTATEMENT.startIndex;
    int endIndex = $EMPTYSTATEMENT.endIndex + 1;
    $stat = new EmptyStatement(startIndex, endIndex, ast); 
  }
  | expression
  {
     if ($expression.stat != null) {
        $stat = $expression.stat;
     }
     else {
        $expr = $expression.expr;
     }
  }
  | ^(FOREACH_T ^(AS_T e=expression v1=foreach_variable v2=foreach_variable?) foreach_statement)
  {
    int startIndex = $FOREACH_T.startIndex;
    int endIndex = $FOREACH_T.endIndex + 1;
    
    if ($v2.expr == null) {
      $stat = new ForEachStatement(startIndex, endIndex, ast, $e.expr, $v1.expr, $foreach_statement.block);
    }
    else {
      $stat = new ForEachStatement(startIndex, endIndex, ast, $e.expr, $v1.expr, $v2.expr, $foreach_statement.block);
    }
  } 
  | ^(DECLARE_T directive declare_statement)
    {
      int startIndex = $DECLARE_T.startIndex;
      int endIndex = $DECLARE_T.endIndex + 1;
      DeclareStatement declare = new DeclareStatement(startIndex, endIndex, ast, $topStatement::declareKey, $topStatement::declareValue, $declare_statement.block);
      $stat = declare;
    }
  | ^(TRY_T block catch_branch_list)
  {
    int startIndex = $TRY_T.startIndex;
    int endIndex = $TRY_T.endIndex + 1;
    
    TryStatement tryStatement = new TryStatement(startIndex, endIndex, ast, (Block)$block.stat, $catch_branch_list.catchList);
    $stat = tryStatement;
  }
  | ^(THROW_T e=expression)
  {
    int startIndex = $THROW_T.startIndex;
    int endIndex = $THROW_T.endIndex + 1;
    $stat = new ThrowStatement(startIndex, endIndex, ast, $e.expr); 
  }
  | ^(USE_T scalar)
  {
    int startIndex = $USE_T.startIndex;
    int endIndex = $USE_T.endIndex;
    
    Identifier id = new Identifier(startIndex, startIndex + "use".length(), ast, "use");
    FunctionName functionName = new FunctionName(startIndex, startIndex + "use".length(), ast, id);
    
    List list = new LinkedList();
    list.add($scalar.expr);
    
    FunctionInvocation functionInvocation = new FunctionInvocation(startIndex, endIndex, ast, functionName, list);
  
    $stat = new ExpressionStatement(startIndex, endIndex, ast, functionInvocation);
  }
  | ^(USE_PARETHESIS_T scalar)
  {
    int startIndex = $USE_PARETHESIS_T.startIndex;
    int endIndex = $USE_PARETHESIS_T.endIndex;
        
    Identifier id = new Identifier(startIndex, startIndex + "use".length(), ast, "use");
    FunctionName functionName = new FunctionName(startIndex, startIndex + "use".length(), ast, id);
    
    List list = new LinkedList();
    list.add($scalar.expr);
    
    FunctionInvocation functionInvocation = new FunctionInvocation(startIndex, endIndex, ast, functionName, list);
  
    $stat = new ExpressionStatement(startIndex, endIndex, ast, functionInvocation);
  }
  | ^(INCLUDE_T e=expression) 
  {
    int startIndex = $INCLUDE_T.startIndex;
    int endIndex = $INCLUDE_T.endIndex;
    
    ParenthesisExpression parenthesisExpression = new ParenthesisExpression($e.start.startIndex - "(".length(), $e.start.endIndex + "()".length(), ast, $e.expr);
    
    $expr = new Include(startIndex, endIndex, ast, parenthesisExpression, Include.IT_INCLUDE);
  }
  | ^(INCLUDE_ONCE_T e=expression) 
  {
    int startIndex = $INCLUDE_ONCE_T.startIndex;
    int endIndex = $INCLUDE_ONCE_T.endIndex;
    
    ParenthesisExpression parenthesisExpression = new ParenthesisExpression($e.start.startIndex - "(".length(), $e.start.endIndex + "()".length(), ast, $e.expr);
    
    $expr = new Include(startIndex, endIndex, ast, parenthesisExpression, Include.IT_INCLUDE_ONCE);
  }
  | ^(REQUIRE_T e=expression) 
  {
    int startIndex = $REQUIRE_T.startIndex;
    int endIndex = $REQUIRE_T.endIndex;
    ParenthesisExpression parenthesisExpression = new ParenthesisExpression($e.start.startIndex - "(".length(), $e.start.endIndex + "()".length(), ast, $e.expr);
    $expr = new Include(startIndex, endIndex, ast, parenthesisExpression, Include.IT_REQUIRE);
  }
  | ^(REQUIRE_ONCE_T e=expression) 
  {
    int startIndex = $REQUIRE_ONCE_T.startIndex;
    int endIndex = $REQUIRE_ONCE_T.endIndex;
    ParenthesisExpression parenthesisExpression = new ParenthesisExpression($e.start.startIndex - "(".length(), $e.start.endIndex + "()".length(), ast, $e.expr);
    $expr = new Include(startIndex, endIndex, ast, parenthesisExpression, Include.IT_REQUIRE_ONCE);
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
    int startIndex = $variable.start.startIndex;
    int endIndex = $variable.start.endIndex + 1;
    $expr = $variable.var;
    if ($REF_T.tree != null) {
      startIndex = ((CommonToken)$REF_T.tree.token).getStartIndex();
      $expr = new Reference(startIndex, endIndex, ast, (VariableBase)$expr);
    }
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
    //need to correct this part later
    Iterator iter = $variableList.iterator();
    while (iter.hasNext()) {
      Object obj = iter.next();
      if (!(obj instanceof Variable)) {
        $variableList.remove(obj);
      }
    }
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
  
fully_qualified_class_name returns [String name, ClassName className, Identifier idClassName, Identifier idVarName, StaticConstantAccess constant, int option]
@init {
  CommonToken token;
}
  : ^(domain=DOMAIN_T fname=fully_qualified_class_name IDENTIFIER) DOMAIN_T?
  {
    $option = 2;
    int startIndex = $domain.startIndex;
    int endIndex = $domain.endIndex + 1;
    if ($fname.constant != null) {
    
    }
    else {
      int varLeft = startIndex;
      int varRight = endIndex;
      if ($fname.start != null) {
        token = (CommonToken)$fname.start.token;
        varLeft = token.getStartIndex();
        varRight = token.getStopIndex() + 1; 
      }
      Identifier vname = new Identifier(varLeft, varRight, ast, $fname.name);
      $idVarName = vname;
      int classLeft = ((CommonToken)$IDENTIFIER.tree.token).getStartIndex();
      int classRight = ((CommonToken)$IDENTIFIER.tree.token).getStopIndex() + 1;
      
      Identifier cname = new Identifier(classLeft, classRight, ast, $IDENTIFIER.text);
      $idClassName = cname;
      $constant = new StaticConstantAccess(startIndex, endIndex, ast, vname, cname); 
    }    
  }
  | IDENTIFIER DOMAIN_T?
  {
    $option = 1;
    $name = $IDENTIFIER.text;
    token = (CommonToken)$IDENTIFIER.tree.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    Identifier name = new Identifier(startIndex, endIndex, ast, $name);
    $idClassName = name;
    Identifier forClassName = new Identifier(startIndex, endIndex, ast, $name);
    $className = new ClassName(startIndex, endIndex, ast, forClassName);
    
    if (inNameList) {
        $fully_qualified_class_name_list::nameList.add(name);
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
    int varNameRight = $pure_variable.tree.endIndex + 1;
    String varName = $pure_variable.text;
    Variable varId = new Variable(varNameLeft, varNameRight, ast, varName);
    
    if (inClassStatementList) {
      Object obj = new ASTNode[] {varId, null};
      $class_statement::varList.add(obj);
    }
    else {
      $static_var_list::varList.add(varId);
    }
  }
  | ^(EQUAL_T pure_variable scalar)
  {
    int startIndex = $EQUAL_T.startIndex;
    int endIndex = $EQUAL_T.endIndex + 1;
    int varNameLeft = $pure_variable.tree.startIndex;
    int varNameRight = $pure_variable.tree.endIndex + 1;
    String varName = $pure_variable.text;
    Expression expr = $scalar.expr;
    
    Variable varId = new Variable(varNameLeft, varNameRight, ast, varName);

    Expression assignExpr = new Assignment(startIndex, endIndex, ast, varId, Assignment.OP_EQUAL, expr);
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
         falseStatement = new IfStatement(start, trueStatement.getEnd(), ast, innerCondition, trueStatement, falseStatement);
      }
      
      int sid = ((CommonToken)$inner_statement_list.tree.token).getStartIndex();
      int eid = ((CommonToken)$inner_statement_list.tree.token).getStopIndex() + 1;
      Block block = new Block(sid, eid, ast, new LinkedList(), false);
      if ($inner_statement_list.innerStatementList != null) {
        if ($inner_statement_list.block == null) {
           block = new Block(sid, eid, ast, $inner_statement_list.innerStatementList, false);
        }
        else {
           block = (Block)$inner_statement_list.block;
        }
      }
      $stat = new IfStatement(startIndex, endIndex, ast, $expression.expr, block, falseStatement);  
    }
  ;

else_if_stat
  : ^(ELSEIF_T ^(CONDITION expression) inner_statement_list?)
  {
    int startIndex = $ELSEIF_T.startIndex;
    int endIndex = $ELSEIF_T.endIndex;
    $if_stat::conditionList.add($expression.expr);
    
    Block block = new Block(startIndex, endIndex, ast, new LinkedList());
    if ($inner_statement_list.innerStatementList != null) {
      if ($inner_statement_list.block == null) {
         block = new Block(startIndex, endIndex, ast, $inner_statement_list.innerStatementList, false);
      }
      else {
         block = (Block)$inner_statement_list.block;
      }
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
  : ^(BLOCK case_list+)
  {
    int startIndex = $BLOCK.startIndex;
    int endIndex = $BLOCK.endIndex + 1;
    Block block = new Block(startIndex, endIndex, ast, $switch_case_list::list);
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
    SwitchCase switchCase = new SwitchCase(startIndex, endIndex, ast, $e.expr, list, false);
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
    SwitchCase switchCase = new SwitchCase(startIndex, endIndex, ast, null, list, true);
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
    Identifier className = $fully_qualified_class_name.idClassName;
    
    
    Variable var = (Variable)$variable.var;
    Block block = (Block)$block.stat;
    
    CatchClause catchClause = new CatchClause(startIndex, endIndex, ast, className, var, block);
    
    $catch_branch_list::list.add(catchClause);
  }
  ;

for_statement returns [Statement block]
 : ^(BLOCK inner_statement_list?)
   {
      int startIndex = $BLOCK.startIndex;
      int endIndex = $BLOCK.endIndex + 1;
      Statement block = new Block(startIndex, endIndex, ast, new LinkedList(), false);
      if ($inner_statement_list.innerStatementList != null) {
        if ($inner_statement_list.block == null) {
           block = new Block(startIndex, endIndex, ast, $inner_statement_list.innerStatementList, false);
        }
        else {
           block = $inner_statement_list.block;
        }     
     }
     $block = block;
     System.out.println("block:" + block);
   }
 ;
 
while_statement returns [Statement block]
  : ^(BLOCK inner_statement_list?)
    {
      int startIndex = $BLOCK.startIndex;
      int endIndex = $BLOCK.endIndex + 1;
      Statement block = new Block(startIndex, endIndex, ast, new LinkedList(), false);
      if ($inner_statement_list.innerStatementList != null) {
        if ($inner_statement_list.block == null) {
           block = new Block(startIndex, endIndex, ast, $inner_statement_list.innerStatementList, false);
        }
        else {
           block = $inner_statement_list.block;
        }     
     }
     $block = block;
    }
  ;
 
foreach_statement returns [Statement block]
  : ^(BLOCK inner_statement_list?)
    {
      int startIndex = $BLOCK.startIndex;
      int endIndex = $BLOCK.endIndex + 1;
      Statement block = new Block(startIndex, endIndex, ast, new LinkedList());
	    if ($inner_statement_list.innerStatementList != null) {
	      if ($inner_statement_list.block == null) {
	         block = new Block(startIndex, endIndex, ast, $inner_statement_list.innerStatementList, false);
	      }
	      else {
	         block = (Statement)$inner_statement_list.block;
	      }
	    }
      $block = block;
    }
  ;
  
declare_statement returns [Statement block]
  : ^(BLOCK inner_statement_list?)
   {
      int startIndex = $BLOCK.startIndex;
      int endIndex = $BLOCK.endIndex + 1;
      Statement block = new Block(startIndex, endIndex, ast, new LinkedList());
      if ($inner_statement_list.innerStatementList != null) {
        if ($inner_statement_list.block == null) {
  	       block = new Block(startIndex, endIndex, ast, $inner_statement_list.innerStatementList);
  	    }
	      else {
	         block = $inner_statement_list.block;
	      }     
     }
     $block = block;
     System.out.println("block:" + block);
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
    int startIndex = $PARAMETER.startIndex;
    int endIndex = $PARAMETER.endIndex + 1;
	  Variable var = $pure_variable.var;
	  Reference refVar = $pure_variable.refVar;
	  Identifier classType = null;
	  if ($parameter_type.type != null) {
	    classType = $parameter_type.type;
	  }
	  FormalParameter parameter = null;
	  Expression scalar = $scalar.expr;
	  if (refVar != null) {
	     parameter = new FormalParameter(startIndex, endIndex, ast, classType, refVar, scalar);
	  }
	  else {
	     parameter = new FormalParameter(startIndex, endIndex, ast, classType, var, scalar);
	  }  
    $parameter_list::paramList.add(parameter);
  }
  ;
  
parameter_type returns [Identifier type]
  : fully_qualified_class_name
  {
    $type = $fully_qualified_class_name.idClassName;
  }
  | cast_option
  {
    int option = $cast_option.castOption;
    int startIndex = 0;
    int endIndex = 0;
    if ($cast_option.start != null) {
      CommonToken token = (CommonToken)$cast_option.start.token;
      startIndex = token.getStartIndex();
      endIndex = token.getStopIndex() + 1;
    }
    Identifier type = new Identifier(startIndex, endIndex, ast, $cast_option.text);
    $type = type;  
  }
  ;
 
variable_modifiers  returns [int modifierValue]
  : 'var'   {$modifierValue = Modifiers.AccPublic;}
  | modifier  {$modifierValue |= $modifier.modifierValue;}
  ;
 
modifier  returns [int modifierValue]
@init {
//  $modifierValue = 1;
}
  : ('public' {$modifierValue |= Modifiers.AccPublic;}
  | 'protected' {$modifierValue |= Modifiers.AccProtected;}
  | 'private' {$modifierValue |= Modifiers.AccPrivate;}
  | 'static'  {$modifierValue |= Modifiers.AccStatic;}
  | 'abstract'  {$modifierValue |= Modifiers.AccAbstract;}
  | 'final'   {$modifierValue |= Modifiers.AccFinal;}
  )*
  ;
   
directive returns [Object astNode]
  : ^(EQUAL_T IDENTIFIER expression)
  {
    int constNameleft = ((CommonToken)$IDENTIFIER.token).getStartIndex();
    int constNameright = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;
    String constName = $IDENTIFIER.text;
    if (inClassStatementList) {      
      int exprLeft = ((CommonToken)($expression.tree.token)).getStartIndex();
      int exprRight = ((CommonToken)($expression.tree.token)).getStopIndex() + 1;
      Expression expr = $expression.expr;
      
      Identifier constId = new Identifier(constNameleft, constNameright, ast, constName);
      Object obj = new ASTNode[]{constId, expr};
      $class_statement::constList.add(obj);
    }
    else {
      Identifier constId = new Identifier(constNameleft, constNameright, ast, constName);
      $topStatement::declareKey.add(constId);
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
   
expression returns [Expression expr, Statement stat]
  : ^(EXPR etop=expression)
  {
    $expr = $etop.expr;
    System.out.println("etop:" + $expr);
    $stat = $etop.stat;
    if (inExprList && !inPrintExpr) {
      $expr_list::list.add($expr);
    }
  }
  | ^(OR_T e1=expression e2=expression)
  {
    int startIndex = $OR_T.startIndex;
    int endIndex = $OR_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1, InfixExpression.OP_STRING_OR, expr2); 
  }
  | ^(XOR_T e1=expression e2=expression)
  {
    int startIndex = $XOR_T.startIndex;
    int endIndex = $XOR_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_STRING_XOR, expr2);
  }
  | ^(AND_T e1=expression e2=expression)
  {
    int startIndex = $AND_T.startIndex;
    int endIndex = $AND_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_STRING_AND, expr2);
  }
  | ^(EQUAL_T e1=expression e2=expression) 
  {
    int startIndex = $EQUAL_T.startIndex;
    int endIndex = $EQUAL_T.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_EQUAL, expr);
  }
  | ^(PLUS_EQ e1=expression e2=expression)
  {
    int startIndex = $PLUS_EQ.startIndex;
    int endIndex = $PLUS_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_PLUS_EQUAL, expr);
  }
  | ^(MINUS_EQ e1=expression e2=expression)
  {
    int startIndex = $MINUS_EQ.startIndex;
    int endIndex = $MINUS_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_MINUS_EQUAL, expr);
  }
  | ^(MUL_EQ e1=expression e2=expression)
  {
    int startIndex = $MUL_EQ.startIndex;
    int endIndex = $MUL_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_MUL_EQUAL, expr);
  }
  | ^(DIV_EQ e1=expression e2=expression)
  {
    int startIndex = $DIV_EQ.startIndex;
    int endIndex = $DIV_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_DIV_EQUAL, expr);
  }
  | ^(DOT_EQ e1=expression e2=expression)
  {
    int startIndex = $DOT_EQ.startIndex;
    int endIndex = $DOT_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_CONCAT_EQUAL, expr);
  }
  | ^(PERCENT_EQ e1=expression e2=expression)
  {
    int startIndex = $PERCENT_EQ.startIndex;
    int endIndex = $PERCENT_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_MOD_EQUAL, expr);
  }
  | ^(BIT_AND_EQ e1=expression e2=expression)
  {
    int startIndex = $BIT_AND_EQ.startIndex;
    int endIndex = $BIT_AND_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_AND_EQUAL, expr);
  }
  | ^(BIT_OR_EQ e1=expression e2=expression)
  {
    int startIndex = $BIT_OR_EQ.startIndex;
    int endIndex = $BIT_OR_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_OR_EQUAL, expr);
  }
  | ^(POWER_EQ e1=expression e2=expression)
  {
    int startIndex = $POWER_EQ.startIndex;
    int endIndex = $POWER_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_XOR_EQUAL, expr);
  }
  | ^(LMOVE_EQ e1=expression e2=expression)
  {
    int startIndex = $LMOVE_EQ.startIndex;
    int endIndex = $LMOVE_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_SL_EQUAL, expr);
  }
  | ^(RMOVE_EQ e1=expression e2=expression)
  {
    int startIndex = $RMOVE_EQ.startIndex;
    int endIndex = $RMOVE_EQ.endIndex + 1;
    Expression var = $e1.expr;
    Expression expr = $e2.expr;
    $expr = new Assignment(startIndex, endIndex, ast, (VariableBase)var, Assignment.OP_SR_EQUAL, expr);
  }
  | ^(QUESTION_T e1=expression ^(COLON_T e2=expression e3=expression))
  {
    int startIndex = $QUESTION_T.startIndex;
    int endIndex = $QUESTION_T.endIndex + 1;
    $expr = new ConditionalExpression(startIndex, endIndex, ast, $e1.expr, $e2.expr, $e3.expr); 
  }  
  | ^(LOGICAL_OR_T e1=expression e2=expression)
  {
    int startIndex = $LOGICAL_OR_T.startIndex;
    int endIndex = $LOGICAL_OR_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_BOOL_OR, expr2); 
  }
  | ^(LOGICAL_AND_T e1=expression e2=expression)
  {
    int startIndex = $LOGICAL_AND_T.startIndex;
    int endIndex = $LOGICAL_AND_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_BOOL_AND, expr2);
  }
  | ^(BIT_OR_T e1=expression e2=expression)
  {
    int startIndex = $BIT_OR_T.startIndex;
    int endIndex = $BIT_OR_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_OR, expr2); 
  }
  | ^(POWER_T e1=expression e2=expression)
  {
    int startIndex = $POWER_T.startIndex;
    int endIndex = $POWER_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_XOR, expr2); 
  }
  | ^(REF_T e1=expression e2=expression)
  {
    int startIndex = $REF_T.startIndex;
    int endIndex = $REF_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
//    if (expr2 != null) {
      $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_AND, expr2);
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
          $expr = new UnaryOperation(startIndex--, endIndex, ast, $expr, UnaryOperation.OP_PLUS);
          break;
        case 2:
          $expr = new UnaryOperation(startIndex--, endIndex, ast, $expr, UnaryOperation.OP_MINUS);
          break;
        case 3:
          $expr = new Reference(startIndex--, endIndex, ast, $expr);
          break;
        case 4:
          $expr = new UnaryOperation(startIndex--, endIndex, ast, $expr, UnaryOperation.OP_TILDA);
          break;
        case 5:
          $expr = new UnaryOperation(startIndex--, endIndex, ast, $expr, UnaryOperation.OP_NOT);
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
    if (startIndex == 0) {
      startIndex = $e1.start.startIndex;
      endIndex = $e2.start.endIndex + 1;
    }
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_CONCAT, expr2); 
  }
  | ^(EQUAL_EQUAL_T e1=expression e2=expression)
  {
    int startIndex = $EQUAL_EQUAL_T.startIndex;
    int endIndex = $EQUAL_EQUAL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_IS_EQUAL, expr2);
  }
  | ^(NOT_EQUAL_T e1=expression e2=expression)
  {
    int startIndex = $NOT_EQUAL_T.startIndex;
    int endIndex = $NOT_EQUAL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_IS_NOT_EQUAL, expr2);
  }
  | ^(EQUAL_EQUAL_EQUAL_T e1=expression e2=expression)
  {
    int startIndex = $EQUAL_EQUAL_EQUAL_T.startIndex;
    int endIndex = $EQUAL_EQUAL_EQUAL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_IS_IDENTICAL, expr2);
  }
  | ^(NOT_EQUAL_EQUAL_T e1=expression e2=expression)
  {
    int startIndex = $NOT_EQUAL_EQUAL_T.startIndex;
    int endIndex = $NOT_EQUAL_EQUAL_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_IS_NOT_IDENTICAL, expr2);
  }
  | ^(LT_T e1=expression e2=expression)
  {
    int startIndex = $LT_T.startIndex;
    int endIndex = $LT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_RGREATER, expr2); 
  }
  | ^(MT_T e1=expression e2=expression)
  {
    int startIndex = $MT_T.startIndex;
    int endIndex = $MT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_LGREATER, expr2); 
  }
  | ^(LE_T e1=expression e2=expression)
  {
    int startIndex = $LE_T.startIndex;
    int endIndex = $LE_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_IS_SMALLER_OR_EQUAL, expr2); 
  }
  | ^(ME_T e1=expression e2=expression)
  {
    int startIndex = $ME_T.startIndex;
    int endIndex = $ME_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1 , InfixExpression.OP_IS_GREATER_OR_EQUAL, expr2); 
  }
  | ^(LSHIFT_T e1=expression e2=expression)
  {
    int startIndex = $LSHIFT_T.startIndex;
    int endIndex = $LSHIFT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1, InfixExpression.OP_SL, expr2); 
  }
  | ^(RSHIFT_T e1=expression e2=expression)
  {
    int startIndex = $RSHIFT_T.startIndex;
    int endIndex = $RSHIFT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1, InfixExpression.OP_SR, expr2); 
  }
  | ^(PLUS_T e1=expression e2=expression)
  {
    int startIndex = $PLUS_T.startIndex;
    int endIndex = $PLUS_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
//    if (expr2 != null) {
      $expr = new InfixExpression(startIndex, endIndex, ast, expr1, InfixExpression.OP_PLUS, expr2);
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
      $expr = new InfixExpression(startIndex, endIndex, ast, expr1, InfixExpression.OP_MINUS, expr2);
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
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1, InfixExpression.OP_MUL, expr2);
  }
  | ^(DIV_T e1=expression e2=expression)
  {
    int startIndex = $DIV_T.startIndex;
    int endIndex = $DIV_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1, InfixExpression.OP_DIV, expr2);
  }
  | ^(PERCENT_T e1=expression e2=expression)
  {
    int startIndex = $PERCENT_T.startIndex;
    int endIndex = $PERCENT_T.endIndex + 1;
    Expression expr1 = $e1.expr;
    Expression expr2 = $e2.expr;
    $expr = new InfixExpression(startIndex, endIndex, ast, expr1, InfixExpression.OP_MOD, expr2);
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
        $expr = new CastExpression(startIndex, endIndex, ast, expr, CastExpression.TYPE_BOOL);
        break;
      case 3:
        $expr = new CastExpression(startIndex, endIndex, ast, expr, CastExpression.TYPE_INT);
        break;
      case 6:
        $expr = new CastExpression(startIndex, endIndex, ast, expr, CastExpression.TYPE_ARRAY);
        break;
      case 7:
        $expr = new CastExpression(startIndex, endIndex, ast, expr, CastExpression.TYPE_OBJECT);
        break;
      case 8:
        $expr = new CastExpression(startIndex, endIndex, ast, expr, CastExpression.TYPE_REAL);
        break;
      case 9:
        $expr = new CastExpression(startIndex, endIndex, ast, expr, CastExpression.TYPE_STRING);
        break;
      case 10:
        $expr = new CastExpression(startIndex, endIndex, ast, expr, CastExpression.TYPE_UNSET);
        break;
      default:
        $expr = new CastExpression(startIndex, endIndex, ast, expr, CastExpression.TYPE_OBJECT);
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
        $expr = new PostfixExpression(startIndex, endIndex, ast, (VariableBase)$e.expr , PostfixExpression.OP_INC);
      }
      else {
        $expr = new PostfixExpression(startIndex, endIndex, ast, (VariableBase)$e.expr , PostfixExpression.OP_DEC);
      } 
  }
  | ^(PREFIX_EXPR plus_minus+ e=expression)
  {
      int startIndex = $PREFIX_EXPR.startIndex;
      int endIndex = $PREFIX_EXPR.endIndex + 1;
      if ($plus_minus.text.equals("++")) {
        $expr = new PrefixExpression(startIndex, endIndex, ast, (VariableBase)$e.expr, PrefixExpression.OP_INC);
      }
      else {
        $expr = new PrefixExpression(startIndex, endIndex, ast, (VariableBase)$e.expr, PrefixExpression.OP_DEC);
      }
  }
  | ^(INSTANCEOF_T e=expression class_name_reference)
  {
    int startIndex = $INSTANCEOF_T.startIndex;
    int endIndex = $INSTANCEOF_T.endIndex + 1;
    $expr = new InstanceOfExpression(startIndex, endIndex, ast, $e.expr, $class_name_reference.className); 
  }
  | AT_T? variable
  {
    $expr = $variable.var;
    $stat = $variable.errorStat;
  }
  | AT_T? scalar
  {
    $expr = $scalar.expr;
    $stat = $scalar.stat;
  }
  | list_decl
  {
    $expr = $list_decl.expr;
  }
  | ^(ARRAY_DECL array_pair_list?)
  {
    int startIndex = $ARRAY_DECL.startIndex;
    int endIndex = $ARRAY_DECL.endIndex + 1;
    if ($array_pair_list.arrayList != null) {
       $expr = new ArrayCreation(startIndex, endIndex, ast, $array_pair_list.arrayList);
    }
    else {
       $expr = new ArrayCreation(startIndex, endIndex, ast, new LinkedList());
    }
  }
  | ^(NEW_T class_name_reference)
  {
    int startIndex = $NEW_T.startIndex;
    int endIndex = $NEW_T.endIndex + 1;
    
    ClassName className = $class_name_reference.className;
    
    List ctor = $class_name_reference.ctorList;
    
    $expr = new ClassInstanceCreation(startIndex, endIndex, ast, className, ctor);
    
  }
  
  | ^(CLONE_T variable)
  {
    int startIndex = $CLONE_T.startIndex;
    int endIndex = $CLONE_T.endIndex + 1;
    $expr = new CloneExpression(startIndex, endIndex, ast, $variable.var);
  }
//  | ^(EXIT_T expression?)
  | ^(UNSET_T variable_list)
  {
     int startIndex = $UNSET_T.startIndex;
     int endIndex = $UNSET_T.endIndex + 1;
     int startOfVariableList = startIndex + "unset".length() + 1;
    
     List variableList = $variable_list.variableList;
     Identifier id = new Identifier(startIndex, startIndex + "unset".length(), ast, "unset");
		 FunctionName functionName = new FunctionName(startIndex, startIndex + "unset".length(), ast, id);
		 FunctionInvocation functionInvocation = new FunctionInvocation(startIndex, endIndex, ast, functionName, variableList);
		 $stat = new ExpressionStatement(startIndex, endIndex, ast, functionInvocation);
  }
//  | lambda_function_declaration
  | BACKTRICKLITERAL
  {
    int startIndex = $BACKTRICKLITERAL.startIndex;
    int endIndex = $BACKTRICKLITERAL.endIndex + 1;
    $expr = new BackTickExpression(startIndex, endIndex, ast, new LinkedList());
  }
  | ^(PRINT_T {inPrintExpr = true;} e=expression) {inPrintExpr = false;}
  {
    int startIndex = $PRINT_T.startIndex;
    int endIndex = $PRINT_T.endIndex + 1;
     
    List expList = new LinkedList();
    expList.add($e.expr);
	  Identifier id = new Identifier(startIndex, startIndex + "print".length(), ast, "print");
	  FunctionName name = new FunctionName(startIndex, startIndex + "print".length(), ast, id);
	  $expr = new FunctionInvocation(startIndex, endIndex, ast, name, expList);
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
      
class_name_reference returns [ClassName className, Expression var, List ctorList]
scope {
  List list;
  List argList;
}
@init {
  $class_name_reference::list = new LinkedList();
}
  : dynamic_name_reference
  {
    $var = $dynamic_name_reference.var;
    Expression id = $dynamic_name_reference.idVar;
    int startIndex = 0;
    int endIndex = 0;
    if (id != null) {
	    startIndex = id.getStart();
	    endIndex = id.getEnd();
	    $className = new ClassName(startIndex, endIndex, ast, id);
	  }
	  else {
	    VariableBase dispatcher = (VariableBase)$var;
	    startIndex = dispatcher.getStart();
	    Iterator iter = $class_name_reference::list.iterator();
	    while (iter.hasNext()) {
	      VariableBase property = (VariableBase)iter.next();
	      dispatcher = createDispatch(dispatcher, property);
	    }
	    $var = dispatcher;
	    endIndex = dispatcher.getEnd();
	    $className = new ClassName(startIndex, endIndex, ast, (Expression)$var);
	  }
	  $ctorList = $dynamic_name_reference.ctorList;
  }
  | fully_qualified_class_name
  {
    $className = $fully_qualified_class_name.className;
  }
  ;
  
dynamic_name_reference returns [Expression var, Expression idVar, List ctorList]
  : base_variable_with_function_calls 
  {
     $var = $base_variable_with_function_calls.var;
     $idVar = $base_variable_with_function_calls.idVar;
     $ctorList = $base_variable_with_function_calls.ctorList;
  }
  | ^(CALL v1=dynamic_name_reference obj=object_property ctor_arguments?)
  {
      $var = $v1.var;
      $idVar = null;   //set this as null, to distinguish alt1 and alt2(this)
      $class_name_reference::list.add($obj.expr);
      $ctorList = $ctor_arguments.argumentList;
  }
  ;

list_decl returns [Expression expr]
  : ^(LIST_T assignment_list?)
  {
    int startIndex = ($LIST_T).startIndex;
    int endIndex = $LIST_T.endIndex + 1;
    $expr = new ListVariable(startIndex, endIndex, ast, $assignment_list.assignList);
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
    $assignment_list::list.add((Variable)$variable.var);
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
    ArrayElement element = new ArrayElement(startIndex, endIndex, ast, key, value);
    $array_pair_list::list.add(element);
  }
  | e=expression
  {
    int startIndex = $e.expr.getStart();
    int endIndex = $e.expr.getEnd();
    Expression expr = $expression.expr;
    ArrayElement element = new ArrayElement(startIndex, endIndex, ast, expr);
    $array_pair_list::list.add(element);
  }
  ; 
    
variable returns [Expression var, List parameterList, Statement errorStat]
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
      dispatcher = createDispatch((VariableBase)dispatcher, (VariableBase)property);
    }
    $var = dispatcher;
    if (inVarList) {
      $variable_list::varList.add($var);
    }
    $variable.tree = $variable.start;
    $errorStat = $variable_temp.errorStat;
  }
  ;  

variable_temp returns [Expression var, List parameterList, Statement errorStat]
@init {
  CommonToken token;
}
  : base_variable_with_function_calls 
  {
     $var = $base_variable_with_function_calls.var;
     $errorStat = $base_variable_with_function_calls.errorStat;
  }
  | ^(CALL v1=variable_temp obj=object_property ctor_arguments?)
  {
      $var = $v1.var;
      $errorStat = $v1.errorStat;

      if ($errorStat == null) {
        int startIndex = $CALL.startIndex;
        int endIndex = $CALL.endIndex + 1;
  
        Expression firstVarProperty = null;
			  if ($ctor_arguments.argumentList == null) {
			    firstVarProperty = $obj.expr;
			  } else {
			    int objStartIndex = startIndex;
			    int objEndIndex = endIndex;
			    if ($obj.start != null) {
			       objStartIndex = $obj.start.startIndex;
			       objEndIndex = $obj.start.endIndex + 1;
			    }
			    		    
			    int ctorStartIndex = startIndex;
			    int ctorEndIndex = endIndex;
			    if ($ctor_arguments.tree != null) {
			       ctorStartIndex = $ctor_arguments.tree.startIndex;
			       ctorEndIndex = $ctor_arguments.tree.endIndex + 1;
			    }
			    
			    FunctionName functionName = new FunctionName(objStartIndex, objEndIndex, ast, $obj.expr);
			    firstVarProperty = new FunctionInvocation(objStartIndex, ctorEndIndex, ast, functionName, $ctor_arguments.argumentList);
			  }
        
        $variable::list.add(firstVarProperty);
     }
  }
  ;
  
base_variable_with_function_calls returns [Expression var, Expression idVar, List ctorList, Statement errorStat]
  : ^(VAR_DECL fully_qualified_class_name? obj=object_property ctor_arguments?
  {
    int startIndex = $VAR_DECL.startIndex;
    int endIndex = $VAR_DECL.endIndex + 1;
    $var = $object_property.expr;
    
    if ($fully_qualified_class_name.idClassName != null) {
       Identifier className = $fully_qualified_class_name.idClassName;
       Variable variableRef = new Variable(startIndex, endIndex, ast, $fully_qualified_class_name.name);
       $var = new StaticFieldAccess(startIndex, endIndex, ast, (Expression)className, (Variable)$var);
    }
    
    if ($ctor_arguments.argumentList != null) {
      int functionNameLeft= 0;
      int functionNameRight= 0;
      String functionName = $obj.str;
      if ($obj.start != null) {
        functionNameLeft = $obj.start.startIndex;
        functionNameRight = $obj.start.endIndex + 1;
      }
    
      //following for class creation
      $idVar = $obj.expr;
      $ctorList = $ctor_arguments.argumentList;
      
      //for functionInvocation
      Variable varObj = (Variable)$obj.expr;
      FunctionName fName = new FunctionName(functionNameLeft, functionNameRight, ast, varObj);
      $var = new FunctionInvocation(startIndex, endIndex, ast, fName, $ctor_arguments.argumentList);
      
      //for methodInvocation
    }
  }    
   )
  | ^(CALL fully_qualified_class_name ctor_arguments)
  {
    int startIndex = $CALL.startIndex;
    int endIndex = $CALL.endIndex + 1;
    
    int functionNameLeft= ((CommonToken)$fully_qualified_class_name.tree.token).getStartIndex();
    int functionNameRight= ((CommonToken)$fully_qualified_class_name.tree.token).getStopIndex() + 1;
    String functionName = $fully_qualified_class_name.text;
        
  //  Identifier name = new Identifier(functionNameLeft, functionNameRight, ast, functionName);
    Identifier name = $fully_qualified_class_name.idClassName;
    FunctionName fName = new FunctionName(functionNameLeft, functionNameRight, ast, name);
    $var = new FunctionInvocation(startIndex, endIndex, ast, fName, $ctor_arguments.argumentList);
    
    
    if ($fully_qualified_class_name.option == 2) {
      Identifier varName = $fully_qualified_class_name.idVarName;
      fName = new FunctionName(name.getStart(), name.getEnd(), ast, name);
      FunctionInvocation funcInvocation = new FunctionInvocation(fName.getStart(), endIndex, ast, fName, $ctor_arguments.argumentList);
      $var = new StaticMethodInvocation(startIndex, endIndex, ast, varName, funcInvocation);
    }
  
    //following for class creation
    $idVar = new Identifier(functionNameLeft, functionNameRight, ast, functionName);
    $ctorList = $ctor_arguments.argumentList;    
  }
  | error_token
  {
    $errorStat = $error_token.errorStat;
    $var = $error_token.errorExpr;
  }
//  | ^(VAR_DECL ^('::' STATIC reference_variable))
//  | fully_qualified_class_name
  ;
  
error_token returns [Expression errorExpr, Statement errorStat]
  :  ^(ERROR_TOKEN variable?)
  {
    int startIndex = $ERROR_TOKEN.startIndex;
    int endIndex = $ERROR_TOKEN.endIndex;
    int type = $ERROR_TOKEN.specificType;
    switch(type) {
      case FOR_T:
        $errorStat = new ForStatement(startIndex, endIndex, ast, new LinkedList(), new LinkedList(), new LinkedList(), new Block(ast));
        break;
      case WHILE_T:
        $errorStat = new WhileStatement(startIndex, endIndex, ast, null, new Block(ast));
        break;
      case DO_T:
        $errorStat = new DoStatement(startIndex, endIndex, ast, null, null);
        break;
      case RETURN_T:
        $errorStat = new ReturnStatement(startIndex, endIndex, ast);
        break;
      case GLOBAL_T:
        $errorStat = new GlobalStatement(startIndex, endIndex, ast, new LinkedList());
        break;
      case STATIC_T:
        $errorStat = new StaticStatement(startIndex, endIndex, ast, new LinkedList());
        break;
      case ECHO_T:
        $errorStat = new EchoStatement(startIndex, endIndex, ast, new LinkedList());
        break;
      case FOREACH_T:
        $errorStat = new ForEachStatement(startIndex, endIndex, ast, null, null, new Block(ast));
        break;
      case DECLARE_T:
        $errorStat = new DeclareStatement(startIndex, endIndex, ast, null, null, new Block(ast));
        break;
      case TRY_T:
        $errorStat = new TryStatement(startIndex, endIndex, ast, new Block(ast), new LinkedList());
        break;
      case THROW_T:
        $errorStat = new ThrowStatement(startIndex, endIndex, ast, null);
        break;
      case USE_T:
        Identifier id = new Identifier(startIndex, startIndex + "use".length(), ast, "use");
        FunctionName name = new FunctionName(startIndex, startIndex + "use".length(), ast, id);
        $errorExpr = new FunctionInvocation(startIndex, endIndex, ast, name, new LinkedList());
        break;
      case INCLUDE_T:
        $errorExpr = new Include(startIndex, endIndex, ast, null, Include.IT_INCLUDE);
        break;
      case INCLUDE_ONCE_T:
        $errorExpr = new Include(startIndex, endIndex, ast, null, Include.IT_INCLUDE_ONCE);
        break;
      case REQUIRE_T:
        $errorExpr = new Include(startIndex, endIndex, ast, null, Include.IT_REQUIRE);
        break;
      case REQUIRE_ONCE_T:
        $errorExpr = new Include(startIndex, endIndex, ast, null, Include.IT_REQUIRE_ONCE);
        break; 
      default:
        $errorStat = new ASTError(startIndex, endIndex, ast);
    }
  }
  ;
  
dollars returns [int size, String str]
@init {
  $size = 0;
  $str = "";
}
  : (DOLLAR_T{$size++; $str += $DOLLAR_T.text;})* 
  ;
    
object_property returns [String str, Expression expr, int numOfDollar]
@init {
  int startIndex = -1;
  int endIndex = -1;
  $numOfDollar = -1;
}
@after {
  $object_property.tree = $object_property.start;
}
  : ^(VAR var_atom)
  {
    $str = $var_atom.str;
    $expr = $var_atom.expr;
    $numOfDollar = $var_atom.numOfDollar;
  }
  
  | ^(INDEX {inArray = true;} obj=object_property expression?)
  {
    inArray = false;
    startIndex = $INDEX.startIndex;
    endIndex = $INDEX.endIndex + 1;
    Expression varName = $obj.expr;
    Expression index = $expression.expr;
    
    if ($obj.numOfDollar != -1) {
      startIndex += $obj.numOfDollar - 1;
    }
    
    if ($expression.text != null) {
      $expr = new ArrayAccess(startIndex, endIndex, ast, (Variable)varName, index, ArrayAccess.VARIABLE_ARRAY);
    }
    else {
      $expr = new ArrayAccess(startIndex, endIndex, ast, (Variable)varName, null, ArrayAccess.VARIABLE_ARRAY);
    }
    
    if ($obj.numOfDollar != -1) {
      for (int i = 0; i < $obj.numOfDollar - 1; i++) {
        $expr = new ReflectionVariable(startIndex - i - 1, endIndex, ast, $expr);       
      }
    }
    
  }
  | ^(HASH_INDEX {inArray = true;} obj=object_property expression?)
  {
    inArray = false;
    startIndex = $HASH_INDEX.startIndex;
    endIndex = $HASH_INDEX.endIndex + 1;
   
    if (startIndex == 0 && $expression.text != null) {
       startIndex = $obj.tree.startIndex;
       endIndex = $obj.tree.endIndex + $expression.text.length();
    }
    Expression varName = $obj.expr;
    Expression index = $expression.expr;
    if ($expression.text != null) {
      $expr = new ArrayAccess(startIndex, endIndex, ast, (Variable)varName, index, ArrayAccess.VARIABLE_HASHTABLE);
    }
    else {
      $expr = new ArrayAccess(startIndex, endIndex, ast, (Variable)varName, null, ArrayAccess.VARIABLE_HASHTABLE);
    }
  }
  | error_token
  {
    $expr = $error_token.errorExpr;
  }
  ;
  
var_atom returns [String str, Expression expr, int numOfDollar]
@init {
  int startIndex = -1;
  int endIndex = -1;
  $numOfDollar = -1;
}
@after {
  $var_atom.tree = $var_atom.start;
}
  : ^(VAR_ATOM dollars IDENTIFIER)
  {
    int size = $dollars.size;
        
    $str = $IDENTIFIER.text;
    startIndex = ((CommonToken)$IDENTIFIER.token).getStartIndex();
    endIndex = ((CommonToken)$IDENTIFIER.token).getStopIndex() + 1;

    if (!$dollars.str.equals("")) {
       $str = "$" + $IDENTIFIER.text;
       startIndex--;
    }
    
    Variable variableRef = new Variable(startIndex, endIndex, ast, $str);
    $expr = variableRef;
    
    //postpone for array
    if (!inArray && $dollars.size > 1) {
      for (int i = 0; i < $dollars.size - 1; i++) {
        $expr = new ReflectionVariable(startIndex - i - 1, endIndex, ast, $expr);       
      }
    }
    else {
      $numOfDollar = $dollars.size;
    }
  }
  | ^(VAR_ATOM dollars expression)
  {
    startIndex = $VAR_ATOM.startIndex;
    endIndex = $VAR_ATOM.endIndex + 1;
    $expr = new ReflectionVariable(startIndex, endIndex, ast, $expression.expr);  
  }
  | ^(ERROR_TOKEN variable?) 
  {
    startIndex = $ERROR_TOKEN.startIndex;
    endIndex = $ERROR_TOKEN.endIndex;
    $expr = new Variable(startIndex, endIndex, ast, "");
  }
  ;
  
  
ctor_arguments returns [List argumentList]
@after {
  $ctor_arguments.tree = $ctor_arguments.start;
}
  :  ^(ARGU expr_list?)
  {
    int startIndex = $ARGU.startIndex + 1;
    int endIndex = $ARGU.endIndex;
    
    if ($expr_list.exprList != null) {
      $argumentList = $expr_list.exprList;
      
    }
    else {
      $argumentList = new LinkedList();
    }
  }
  ;
  
pure_variable returns [String str, Boolean isRef, Variable var, Reference refVar]
@init {
  $isRef = false;
}
@after {
  $pure_variable.tree = $pure_variable.start;
}
  : ^(VAR_DECL REF_T? DOLLAR_T+ IDENTIFIER)
  {
    int startIndex = $VAR_DECL.startIndex;
    int endIndex = $VAR_DECL.endIndex + 1;
    if ($REF_T.text != null) {
      $isRef = true;
    }
    $str = "$" + $IDENTIFIER.text;
    
    $var = new Variable(startIndex, endIndex, ast, $str);
    if ($REF_T.text != null) {
      $refVar = new Reference(startIndex, endIndex, ast, $var);
    }
  }
  ;
  
scalar returns [Expression expr, Statement stat]
  : ^(SCALAR constant)
  {
    if ($constant.expr != null ) {
      $expr = $constant.expr;
    }
    else {
      $expr = $constant.scalar;
    }
    $stat = $constant.stat;
    System.out.println("$expr:" + $expr);
  }
  ;
  
constant returns [Scalar scalar, Expression expr, Statement stat]
  :   INTLITERAL
  {
    CommonToken token = (CommonToken)$INTLITERAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    $scalar = new Scalar(startIndex, endIndex, ast, $INTLITERAL.text, Scalar.TYPE_INT);
  }
  |   FLOATLITERAL
  {
    CommonToken token = (CommonToken)$FLOATLITERAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    $scalar = new Scalar(startIndex, endIndex, ast, $FLOATLITERAL.text, Scalar.TYPE_REAL);
  }
  |   STRINGLITERAL
  {
    CommonToken token = (CommonToken)$STRINGLITERAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    $scalar = new Scalar(startIndex, endIndex, ast, $STRINGLITERAL.text, Scalar.TYPE_STRING);
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
    $scalar = new Scalar(startIndex, endIndex, ast, $REALLITERAL.text, Scalar.TYPE_REAL);
  }
  
  |   DOUBLELITERRAL
  {
    CommonToken token = (CommonToken)$DOUBLELITERRAL.token;
    int startIndex = token.getStartIndex();
    int endIndex = token.getStopIndex() + 1;
    $scalar = new Scalar(startIndex, endIndex, ast, $DOUBLELITERRAL.text, Scalar.TYPE_REAL);
  }
  |   common_scalar
  {
      CommonToken token = (CommonToken)$common_scalar.tree.token;
      int startIndex = token.getStartIndex();
      int endIndex = token.getStopIndex() + 1;
      $scalar = new Scalar(startIndex, endIndex, ast, $common_scalar.text, Scalar.TYPE_SYSTEM);
  }
  |   IDENTIFIER
  {
      CommonToken token = (CommonToken)$IDENTIFIER.token;
      int startIndex = token.getStartIndex();
      int endIndex = token.getStopIndex() + 1;
      $scalar = new Scalar(startIndex, endIndex, ast, $IDENTIFIER.text, Scalar.TYPE_STRING);
  }
  |   fully_qualified_class_name
  {
      if ($fully_qualified_class_name.constant != null) {
        $expr = $fully_qualified_class_name.constant;
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
       Scalar scalar = new Scalar(index1, endIndex - 1, ast, $HERE_DOC.text.substring(index1, index2), Scalar.TYPE_STRING);
       List list = new LinkedList();
       list.add(scalar);
       $expr = new Quote(startIndex, endIndex, ast, list, Quote.QT_HEREDOC);
    }
    else {
       $expr = new Quote(startIndex, endIndex, ast, new LinkedList(), Quote.QT_HEREDOC);
    }
  }
  | ^(ERROR_TOKEN variable?)
  {
    int startIndex = $ERROR_TOKEN.startIndex;
    int endIndex = $ERROR_TOKEN.endIndex;
    int type = $ERROR_TOKEN.specificType;
    $stat = new ASTError(startIndex, endIndex, ast);
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