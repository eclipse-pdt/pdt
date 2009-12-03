grammar CompilerAst;

//php4

options {
	output = AST;
	ASTLabelType = SLAST;
	language = Java;
}

tokens {
	ModuleDeclaration;
	ClassDeclaration;
	
	PROG;		// Main program: statements
	CLASS_BODY;
	FIELD_DECL;
	METHOD_DECL;	// function definition
	TYPE;
	PARAMETER;		// parameter
	BLOCK;
	VAR_DECL;
	STATEMENT;
	CONDITION;
	LIST; // list literal
	INDEX; // a[i]
	MEMBERACCESS;
	CALL;
	ELIST; // expression list
	EXPR;
	ASSIGN;
	LIST_DECL;
	
	SCALAR_ELEMENT;
	SCALAR_VAR;
	CAST;
	LABEL;
	
	ITERATE;
	
	USE_DECL;

	ARGU;
	CALLED_OBJ;
	PREFIX;
	POSTFIX;
	
	STATEMENT;
	
	NAMESPACE;
	EMPTYSTATEMENT;
	SCALAR;
	
	ARRAY_DECL;
	
	PREFIX_EXPR;
	POSTFIX_EXPR;
	CAST_EXPR;
	UNARY_EXPR;
	
	VAR;
	HASH_INDEX;
	USE_PARETHESIS_T;
}

@header {
  package org.eclipse.php.internal.core.compiler.ast.parser.php4;
  
  import org.antlr.runtime.*;
  import org.antlr.runtime.tree.*;
  import org.antlr.runtime.BitSet;
  
  import java.util.*;
	import org.eclipse.dltk.ast.*;
	import org.eclipse.dltk.ast.declarations.*;
	import org.eclipse.dltk.ast.expressions.*;
	import org.eclipse.dltk.ast.references.*;
	import org.eclipse.dltk.ast.statements.*;
	import org.eclipse.php.internal.core.compiler.ast.nodes.*;
	import org.eclipse.php.internal.core.compiler.ast.parser.*;
	import org.eclipse.php.internal.core.ast.scanner.php4.*;
}

@lexer::header {
  package org.eclipse.php.internal.core.compiler.ast.parser.php4;
  
  import org.antlr.runtime.*;
  import org.antlr.runtime.tree.*;
}

@members {
    private IErrorReporter errorReporter = null;
    public void setErrorReporter(IErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }
//    public void emitErrorMessage(String msg) {
//        errorReporter.reportError(msg);
//    }
    
    private List errors = new LinkedList();
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        errors.add(hdr + " " + msg);
    }
    public List getErrors() {
        return errors;
    }
}

php_source
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST phpSourceToken = retval.tree;
  phpSourceToken.setIndex(startIndex, endIndex);
}
  : (SOC_T 
  {
    token = (CommonToken)$SOC_T;
    startIndex = token.getStartIndex();
  } 
  | SOC_PHP_T
  {
    token = (CommonToken)$SOC_PHP_T;
    startIndex = token.getStartIndex();
  })
        top_statement_list? 
     EOC_T 
  {
    token = (CommonToken)$EOC_T;
    endIndex = token.getStopIndex();
  }  
    ->  ^(ModuleDeclaration top_statement_list?)
  ;


top_statement_list
  : top_statement+
  ;

top_statement
  : statement
  | function_declaration_statement
  | class_declaration_statement
  | halt_compiler_statement
  ;
  
inner_statement_list
  : (inner_statement)+
  ;
  
inner_statement
  : statement
  | function_declaration_statement
  | class_declaration_statement
  | halt_compiler_statement
  ;
  
halt_compiler_statement
  : '__halt_compiler' LEFT_PARETHESIS RIGHT_PARETHESIS SEMI_COLON   ->  '__halt_compiler'
  ;
  
class_declaration_statement 
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : ( class_entr_type? CLASS_T IDENTIFIER (EXTENDS_T fully_qualified_class_name)? (IMPLEMENTS_T fully_qualified_class_name_list)?
      LEFT_BRACKET class_statement* RIGHT_BRACKET
    {
      startIndex = ((CommonToken)$CLASS_T).getStartIndex();
      if ($class_entr_type.text != null) {
          token = (CommonToken)$class_entr_type.start;
          startIndex = token.getStartIndex();
      } 
      token = (CommonToken)$RIGHT_BRACKET;
      endIndex = token.getStopIndex(); 
    }
    ->  ^(CLASS_T class_entr_type? IDENTIFIER ^(EXTENDS_T fully_qualified_class_name)? ^(IMPLEMENTS_T fully_qualified_class_name_list)?
      ^(CLASS_BODY class_statement*)?)
  )
   | (INTERFACE_T IDENTIFIER (EXTENDS_T fully_qualified_class_name_list)? (IMPLEMENTS_T fully_qualified_class_name_list)?
      LEFT_BRACKET class_statement* RIGHT_BRACKET
    {
      startIndex = ((CommonToken)$INTERFACE_T).getStartIndex();
      endIndex = ((CommonToken)$RIGHT_BRACKET).getStopIndex();
    } 
      
    ->  ^(INTERFACE_T IDENTIFIER ^(EXTENDS_T fully_qualified_class_name_list)? 
      ^(CLASS_BODY class_statement*)?)
  )
  ;
  
class_entr_type
  : 'abstract'
  | 'final'
  ;
  
class_statement
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("classstat:" + startIndex + " " + endIndex);
}
  : variable_modifiers static_var_list SEMI_COLON
  {
     token = (CommonToken)$variable_modifiers.start;
	   startIndex = token.getStartIndex();
	   token = (CommonToken)$SEMI_COLON;
     endIndex = token.getStopIndex();
  }
      ->  ^(FIELD_DECL variable_modifiers static_var_list)
  | modifier? FUNCTION_T REF_T? IDENTIFIER LEFT_PARETHESIS parameter_list? RIGHT_PARETHESIS SEMI_COLON
  {
     if ($modifier.text != null) {
       token = (CommonToken)$modifier.start;
     }
     else {
       token = (CommonToken)$FUNCTION_T;
     }
     startIndex = token.getStartIndex();
     token = (CommonToken)$SEMI_COLON;
     endIndex = token.getStopIndex();
  }
     ->  ^(METHOD_DECL modifier? REF_T? IDENTIFIER parameter_list? EMPTYSTATEMENT)
  | modifier? FUNCTION_T REF_T? IDENTIFIER LEFT_PARETHESIS parameter_list? RIGHT_PARETHESIS block
  {
     if ($modifier.text != null) {
       token = (CommonToken)$modifier.start;
     }
     else {
       token = (CommonToken)$FUNCTION_T;
     }
     startIndex = token.getStartIndex();
     token = (CommonToken)$block.stop;
     endIndex = token.getStopIndex();
  }
     ->  ^(METHOD_DECL modifier? REF_T? IDENTIFIER parameter_list? block)
  | CONST_T directive SEMI_COLON
  {
     token = (CommonToken)$CONST_T;
     startIndex = token.getStartIndex();
     token = (CommonToken)$SEMI_COLON;
     endIndex = token.getStopIndex();
  }  
     ->  ^(FIELD_DECL CONST_T directive)
  ;
  
function_declaration_statement
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : FUNCTION_T REF_T? IDENTIFIER LEFT_PARETHESIS parameter_list? RIGHT_PARETHESIS 
    block
  {
    token = (CommonToken)$FUNCTION_T;
    startIndex = token.getStartIndex();
    token = (CommonToken)$block.stop;
    endIndex = token.getStopIndex();
  }
  -> ^(METHOD_DECL REF_T? IDENTIFIER parameter_list? block)
  ; 

block
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : LEFT_BRACKET inner_statement_list? RIGHT_BRACKET
  {
    token = (CommonToken)$LEFT_BRACKET;
    startIndex = token.getStartIndex();
    token = (CommonToken)$RIGHT_BRACKET;
    endIndex = token.getStopIndex();
  }
  
     ->  ^(BLOCK inner_statement_list?)
  ;
  
statement
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST exprToken = retval.tree;
  exprToken.setIndex(startIndex, endIndex);
}
  : topStatement
  {
      startIndex = $topStatement.tree.startIndex;
      endIndex = $topStatement.tree.endIndex;
  }
    -> ^(STATEMENT topStatement)
  ; 
  
topStatement
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  if (ast != null) {
    ast.setIndex(startIndex, endIndex);
    System.out.println("statement " + startIndex + " " + endIndex);
  }
}
  : block
  {
    startIndex = ((CommonToken)$block.start).getStartIndex();
    endIndex = ((CommonToken)$block.stop).getStopIndex();
  }
  | if_stat
  {
    startIndex = $if_stat.tree.startIndex;
    endIndex = $if_stat.tree.endIndex;
  }
  | WHILE_T LEFT_PARETHESIS expression RIGHT_PARETHESIS while_statement    
  {
    startIndex = ((CommonToken)$WHILE_T).getStartIndex();
    endIndex = ((CommonToken)$while_statement.stop).getStopIndex();
  }
    ->  ^(WHILE_T ^(CONDITION expression) while_statement)
  | DO_T statement WHILE_T LEFT_PARETHESIS expression RIGHT_PARETHESIS SEMI_COLON 
  {
    startIndex = ((CommonToken)$DO_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(DO_T ^(CONDITION expression) statement)
  | FOR_T LEFT_PARETHESIS e1=expr_list? SEMI_COLON e2=expr_list? SEMI_COLON e3=expr_list? RIGHT_PARETHESIS for_statement
  {
    startIndex = ((CommonToken)$FOR_T).getStartIndex();
    endIndex = ((CommonToken)$RIGHT_PARETHESIS).getStopIndex();
    if ($for_statement.text != null) {
      endIndex = ((CommonToken)$for_statement.stop).getStopIndex();
    }
  }
    ->  ^(FOR_T $e1? ^(CONDITION $e2?) ^(ITERATE $e3?) for_statement?)
  | SWITCH_T LEFT_PARETHESIS expression RIGHT_PARETHESIS switch_case_list
  {
    startIndex = ((CommonToken)$SWITCH_T).getStartIndex();
    endIndex = ((CommonToken)$switch_case_list.stop).getStopIndex();
  }
    ->  ^(SWITCH_T ^(CONDITION expression) switch_case_list)
  | BREAK_T expression? SEMI_COLON
  {
    startIndex = ((CommonToken)$BREAK_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(BREAK_T expression?)
  | CONTINUE_T expression? SEMI_COLON            
  {
    startIndex = ((CommonToken)$CONTINUE_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(CONTINUE_T expression?)
  | RETURN_T expression? SEMI_COLON              
  {
    startIndex = ((CommonToken)$RETURN_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  } 
    ->  ^(RETURN_T expression?)
  | GLOBAL_T variable_list SEMI_COLON            
  {
    startIndex = ((CommonToken)$GLOBAL_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }   
    ->  ^(GLOBAL_T variable_list)
  | STATIC_T static_var_list SEMI_COLON          
  {
    startIndex = ((CommonToken)$STATIC_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }  
    ->  ^(STATIC_T static_var_list)
  | ECHO_T expr_list SEMI_COLON
  {
    startIndex = ((CommonToken)$ECHO_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(ECHO_T expr_list)
  | expression SEMI_COLON!
  {
    startIndex = ((CommonToken)$expression.start).getStartIndex();
    endIndex = ((CommonToken)$expression.stop).getStopIndex();
    if (((CommonToken)$SEMI_COLON).getStopIndex() != 0) {
      endIndex = ((CommonToken)$SEMI_COLON).getStopIndex();
    }    
    System.out.println("weird:" + endIndex);
  }
  | FOREACH_T LEFT_PARETHESIS expression AS_T foreach_variable (ARROW_T foreach_variable)? RIGHT_PARETHESIS
        foreach_statement
  {
    startIndex = ((CommonToken)$FOREACH_T).getStartIndex();
    endIndex = ((CommonToken)$foreach_statement.stop).getStopIndex();
  } 
     -> ^(FOREACH_T ^(AS_T expression foreach_variable foreach_variable?) foreach_statement)
  | DECLARE_T LEFT_PARETHESIS directive RIGHT_PARETHESIS declare_statement
  {
    startIndex = ((CommonToken)$DECLARE_T).getStartIndex();
    endIndex = ((CommonToken)$declare_statement.stop).getStopIndex();
  }
    ->  ^(DECLARE_T directive declare_statement?)
  | SEMI_COLON
  {
    startIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStopIndex();
  }
    ->  ^(EMPTYSTATEMENT SEMI_COLON)
  | TRY_T block catch_branch+
  {
    startIndex = ((CommonToken)$TRY_T).getStartIndex();
    endIndex = ((CommonToken)$catch_branch.stop).getStopIndex();
  }
    ->  ^(TRY_T block catch_branch+)
  | THROW_T expression SEMI_COLON              
  {
    startIndex = ((CommonToken)$THROW_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(THROW_T expression)
  | USE_T scalar SEMI_COLON              
  {
    startIndex = ((CommonToken)$USE_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(USE_T scalar)
  | USE_T LEFT_PARETHESIS scalar RIGHT_PARETHESIS SEMI_COLON
  {
    startIndex = ((CommonToken)$USE_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(USE_PARETHESIS_T scalar)
  | INCLUDE_T LEFT_PARETHESIS expression RIGHT_PARETHESIS SEMI_COLON
  {
    startIndex = ((CommonToken)$INCLUDE_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(INCLUDE_T expression)
  | INCLUDE_ONCE_T LEFT_PARETHESIS expression RIGHT_PARETHESIS SEMI_COLON
  {
    startIndex = ((CommonToken)$INCLUDE_ONCE_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(INCLUDE_ONCE_T expression)
  | REQUIRE_T LEFT_PARETHESIS expression RIGHT_PARETHESIS SEMI_COLON
  {
    startIndex = ((CommonToken)$REQUIRE_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(REQUIRE_T expression)
  | REQUIRE_ONCE_T LEFT_PARETHESIS expression RIGHT_PARETHESIS SEMI_COLON
  {
    startIndex = ((CommonToken)$REQUIRE_ONCE_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStartIndex();
  }
    ->  ^(REQUIRE_ONCE_T expression)
//| error
//| var comment
//| T_INLINE_HTML
  ;
  
  
foreach_variable
//  : (v1=variable -> variable)
//    (ARROW_T v2=variable -> ^(ARROW_T $foreach_variable $v2))?
  : REF_T? variable
  ;
  
use_filename
  : STRINGLITERAL
  | QUOTE_STRING
  | LEFT_PARETHESIS! STRINGLITERAL RIGHT_PARETHESIS!
  | LEFT_PARETHESIS! QUOTE_STRING RIGHT_PARETHESIS!
  ;
  
//method_declaration
//  : FUNCTION_T REF_T? IDENTIFIER LEFT_PARETHESIS parameter_list? RIGHT_PARETHESIS 
//    (
//    SEMI_COLON   ->  ^(FUNCTION_T REF_T? IDENTIFIER parameter_list? EMPTYSTATEMENT)
//    |
//    block ->  ^(FUNCTION_T REF_T? IDENTIFIER parameter_list? block)   
//    )
//  ;

//class_constant_declaration
//  : CONST_T directive    ->  ^(VAR_DECL CONST_T directive)
//  ;

fully_qualified_class_name_list
  : fully_qualified_class_name (',' fully_qualified_class_name)*    ->  fully_qualified_class_name+
  ;

fully_qualified_class_name
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  System.out.println("domain:" + startIndex + " " + endIndex);
  ast.setIndex(startIndex, endIndex);
}
  : id1=IDENTIFIER (DOMAIN_T^ id2=IDENTIFIER)* d2=DOMAIN_T?
  {
    token = (CommonToken)$id1;
    startIndex = token.getStartIndex();
    endIndex = token.getStopIndex();
    if ($d2.text != null) {
      endIndex = ((CommonToken)$d2).getStopIndex();
    }
    else if ($id2.text != null) {
      endIndex = ((CommonToken)$id2).getStopIndex();
    }
  }
  ; 
  
//static_array_pair_list
//  : static_scalar_element (COMMA_T static_scalar_element)*  ->  ^(SCALAR_ELEMENT static_scalar_element)+
//  ;
// 
//static_scalar_element
//  : scalar (ARROW_T^ scalar)?
//  ;
  
static_var_list
  : static_var_element (COMMA_T static_var_element)*  ->  static_var_element+
  ;

static_var_element
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : pure_variable (EQUAL_T^ scalar)?
  {
    token = (CommonToken)$pure_variable.start;
    startIndex = token.getStartIndex();
    endIndex = token.getStopIndex();
    if ($scalar.text != null) {
      endIndex = ((CommonToken)$scalar.stop).getStopIndex();
    }
  }
  ;
  
if_stat
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  System.out.println("setif " + startIndex + " " + endIndex);
  ast.setIndex(startIndex, endIndex);
}
  : IF_T LEFT_PARETHESIS eIfCond=expression RIGHT_PARETHESIS
    (
      s1=statement (options {k=1; backtrack=true;}: ELSEIF_T LEFT_PARETHESIS eElseCond=expression RIGHT_PARETHESIS s2=statement)*
        (options {k=1;backtrack = true;}:ELSE_T s3=statement)?
     {
        startIndex = ((CommonToken)$IF_T).getStartIndex();
        endIndex = ((CommonToken)$s1.stop).getStopIndex();
        if ($s2.text != null) {
          endIndex = ((CommonToken)$s2.stop).getStopIndex();
        }
        if ($s3.text != null) {
          endIndex = ((CommonToken)$s3.stop).getStopIndex();
        }
     }
      ->  ^(IF_T ^(CONDITION $eIfCond)  $s1 ^(ELSEIF_T ^(CONDITION $eElseCond) $s2)* ^(ELSE_T $s3)?)
    |   COLON_T inner_statement_list? new_elseif_branch* (options {k=1;backtrack = true;}:ELSE_T COLON_T s4=statement)? ENDIF_T SEMI_COLON
     {
        startIndex = ((CommonToken)$IF_T).getStartIndex();
        endIndex = ((CommonToken)$ENDIF_T).getStartIndex() -2;
     }
      ->  ^(IF_T ^(CONDITION $eIfCond) inner_statement_list? new_elseif_branch* ^(ELSE_T $s4)?)
    )
  ;
  
new_elseif_branch
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
 : ELSEIF_T LEFT_PARETHESIS expression RIGHT_PARETHESIS COLON_T inner_statement_list?
  {
    token = (CommonToken)$ELSEIF_T;
    startIndex = token.getStartIndex();
    token = (CommonToken)$COLON_T;
    endIndex = token.getStopIndex();
    if ($inner_statement_list.text != null) {
      endIndex = ((CommonToken)$inner_statement_list.stop).getStopIndex();
    }
  } 
   ->  ^(ELSEIF_T ^(CONDITION expression) inner_statement_list?)
 ;
 
switch_case_list
  : LEFT_BRACKET SEMI_COLON? case_list+ RIGHT_BRACKET           ->  case_list+
  | COLON_T SEMI_COLON? case_list+ ENDSWITCH_T SEMI_COLON     ->  case_list+
  ;

case_list
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : CASE_T expression (COLON_T | SEMI_COLON) inner_statement_list?   
  {
    token = (CommonToken)$CASE_T;
    startIndex = token.getStartIndex();
    token = (CommonToken)$COLON_T;
    if ($SEMI_COLON.text != null) {
      token = (CommonToken)$SEMI_COLON;
    }
    endIndex = token.getStopIndex();
    if ($inner_statement_list.text != null) {
      endIndex = ((CommonToken)$inner_statement_list.stop).getStopIndex();
    }
  }  
    ->  ^(CASE_T expression inner_statement_list?)
  | DEFAULT_T (COLON_T | SEMI_COLON) inner_statement_list?      
  {
    token = (CommonToken)$DEFAULT_T;
    startIndex = token.getStartIndex();
    token = (CommonToken)$COLON_T;
    if ($SEMI_COLON.text != null) {
      token = (CommonToken)$SEMI_COLON;
    }
    endIndex = token.getStopIndex();
    if ($inner_statement_list.text != null) {
      endIndex = ((CommonToken)$inner_statement_list.stop).getStopIndex();
    }
  }
    ->  ^(DEFAULT_T inner_statement_list?)
  ;
 
catch_branch
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : CATCH_T LEFT_PARETHESIS fully_qualified_class_name variable RIGHT_PARETHESIS
      block
    {
	    token = (CommonToken)$CATCH_T;
	    startIndex = token.getStartIndex();
	    token = (CommonToken)$block.stop;
	    endIndex = token.getStopIndex();  
    }
    ->  ^(CATCH_T fully_qualified_class_name variable block)
  ;

for_statement
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
 : statement
 {
    startIndex = ((CommonToken)$statement.start).getStartIndex();
    endIndex = ((CommonToken)$statement.stop).getStopIndex();
 }
   ->  statement
 | COLON_T inner_statement_list? ENDFOR_T SEMI_COLON
 {
    startIndex = ((CommonToken)$COLON_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStopIndex();
 }
   ->  inner_statement_list?
 ;
 
while_statement
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
 : statement
  {
    startIndex = ((CommonToken)$statement.start).getStartIndex();
    endIndex = ((CommonToken)$statement.stop).getStopIndex();
  }
   ->  statement
 | COLON_T inner_statement_list? ENDWHILE_T SEMI_COLON
  {
    startIndex = ((CommonToken)$COLON_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStopIndex();
  }
   ->  inner_statement_list?
 ;
 
foreach_statement
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("foreach stat :" + startIndex + " " + endIndex);
}
  : statement
  {
    startIndex = ((CommonToken)$statement.start).getStartIndex();
    endIndex = ((CommonToken)$statement.stop).getStopIndex();
  }
   ->  statement
  | COLON_T inner_statement_list? ENDFOREACH_T SEMI_COLON
  {
    startIndex = ((CommonToken)$COLON_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStopIndex();
  }
   ->  inner_statement_list?
  ;
  
declare_statement
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  if (ast != null) {
	  System.out.println("decl stat:" + startIndex + " " + endIndex);
	  ast.setIndex(startIndex, endIndex);
  }
}
  : statement
  {
    startIndex = ((CommonToken)$statement.start).getStartIndex();
    endIndex = ((CommonToken)$statement.stop).getStopIndex();
  }
    ->  ^(BLOCK statement)
  | COLON_T inner_statement_list? ENDDECLARE_T SEMI_COLON 
  {
    startIndex = ((CommonToken)$COLON_T).getStartIndex();
    endIndex = ((CommonToken)$SEMI_COLON).getStopIndex();
  }
    ->  ^(BLOCK inner_statement_list?)
  ;
  
parameter_list
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : p1=parameter (COMMA_T p2=parameter)*        
  {
    token = (CommonToken)$p1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$p1.stop;
    if ($p2.text != null) {
      token = (CommonToken)$p2.stop;
    }
    endIndex = token.getStopIndex();
  }  
    -> parameter+
  ;
  
parameter
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : parameter_type? CONST_T? pure_variable (options{k=1; backtrack=true;}: EQUAL_T scalar)?
  {
    token = (CommonToken)$pure_variable.start;
    if ($parameter_type.text != null) {
      token = (CommonToken)$parameter_type.start;
    }
    else if ($CONST_T.text != null) {
      token = (CommonToken)$CONST_T;
    }
    startIndex = token.getStartIndex();
    token = (CommonToken)$pure_variable.stop;
    if ($scalar.text != null) {
      token = (CommonToken)$scalar.stop;
    }
    endIndex = token.getStopIndex();
  }
    
  -> ^(PARAMETER ^(TYPE parameter_type)? CONST_T? pure_variable scalar?)
  ;
  
parameter_type
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : fully_qualified_class_name
  {
    startIndex = ((CommonToken)$fully_qualified_class_name.start).getStartIndex();
    endIndex = ((CommonToken)$fully_qualified_class_name.stop).getStopIndex();
  }
  | cast_option
  {
    startIndex = ((CommonToken)$cast_option.start).getStartIndex();
    endIndex = ((CommonToken)$cast_option.stop).getStopIndex();
  }
  ;
  
variable_list
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : v1=variable (COMMA_T v2=variable)*
  {
    token = (CommonToken)$v1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$v1.stop;
    if ($v2.text != null) {
      token = (CommonToken)$v2.stop;
    }
    endIndex = token.getStopIndex();
  }
    ->  variable+
  ;
  
variable_modifiers
  : 'var'
  | modifier
  ;
 
modifier
  : ('public' 
  | 'protected'
  | 'private'
  | 'static' 
  | 'abstract'
  | 'final')+
  ;
   
directive
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : d1=directive_element (COMMA_T d2=directive_element)* 
  {
    token = (CommonToken)$d1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$d1.stop;
    if ($d2.text != null) {
      token = (CommonToken)$d2.stop;
    }
    endIndex = token.getStopIndex();
  }  
   ->  directive_element+
  ;
  
directive_element
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : IDENTIFIER EQUAL_T expression
  {
    token = (CommonToken)$IDENTIFIER;
    startIndex = token.getStartIndex();
    token = (CommonToken)$expression.stop;
    endIndex = token.getStopIndex();  
  }
    ->  ^(EQUAL_T IDENTIFIER expression)
  ;
  
expr_list
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
 : e1=expression (COMMA_T e2=expression)*
 {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  } 
    ->  expression+
 ;
   
expression
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("top expr:" + startIndex + " " + endIndex);
}
  : logical_text_or_expr
  {
    token = (CommonToken)$logical_text_or_expr.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$logical_text_or_expr.stop;
    endIndex = token.getStopIndex();
  }
    ->  ^(EXPR logical_text_or_expr)
  ;
 
logical_text_or_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=logical_text_xor_expr (OR_T^ e2=logical_text_xor_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

logical_text_xor_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=logical_text_and_expr (XOR_T^ e2=logical_text_and_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

logical_text_and_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=assignment_expr (AND_T^ e2=assignment_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

assignment_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=conditional_expr (assignment_operator^ e2=conditional_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }  
  ;

assignment_operator
  : EQUAL_T
  | PLUS_EQ
  | MINUS_EQ 
  | MUL_EQ 
  | DIV_EQ 
  | DOT_EQ
  | PERCENT_EQ 
  | BIT_AND_EQ 
  | BIT_OR_EQ 
  | POWER_EQ 
  | LMOVE_EQ 
  | RMOVE_EQ
  ;
               
conditional_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("condition " + startIndex + " " + endIndex);
}
  : (ll=logical_or_expr
  {
    token = (CommonToken)$ll.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$ll.stop;
    endIndex = token.getStopIndex();
  }
   -> $ll)
   (QUESTION_T expression? COLON_T lr=logical_or_expr
  {
    token = (CommonToken)$lr.stop;
    endIndex = token.getStopIndex();
  } -> ^(QUESTION_T $ll ^(COLON_T expression? $lr)))?
  ;

logical_or_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=logical_and_expr (LOGICAL_OR_T^ e2=logical_and_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

logical_and_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=bitwise_or_expr (LOGICAL_AND_T^ e2=bitwise_or_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

bitwise_or_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=bitwise_xor_expr (BIT_OR_T^ e2=bitwise_xor_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

bitwise_xor_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=bitwise_and_expr (POWER_T^ e2=bitwise_and_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

bitwise_and_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=concat_expr (DOT_T^ e2=concat_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

concat_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=equality_expr (REF_T^ e2=equality_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

equality_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("euqa " + startIndex + " " + endIndex);
}
  : e1=relational_expr ((EQUAL_EQUAL_T | NOT_EQUAL_T | EQUAL_EQUAL_EQUAL_T | NOT_EQUAL_EQUAL_T)^ e2=relational_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

relational_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("rela " + startIndex + " " + endIndex);
}
  : e1=shift_expr ((LT_T | MT_T | LE_T | ME_T)^ e2=shift_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

shift_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("shift " + startIndex + " " + endIndex);
}
  : e1=additive_expr ((LSHIFT_T | RSHIFT_T)^ e2=additive_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

additive_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("add " + startIndex + " " + endIndex);
}
  : e1=multiplicative_expr ((PLUS_T | MINUS_T)^ e2=multiplicative_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

multiplicative_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("mult " + startIndex + " " + endIndex);
}
  : e1=cast_expr ((MUL_T | DIV_T | PERCENT_T)^ e2=cast_expr)*
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;

cast_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("cast " + startIndex + " " + endIndex);
}
  : unary_expr
  {
    token = (CommonToken)$unary_expr.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$unary_expr.stop;
    endIndex = token.getStopIndex();
  }
  | (LEFT_PARETHESIS cast_option RIGHT_PARETHESIS)+ unary_expr
  {
    token = (CommonToken)$LEFT_PARETHESIS;
    startIndex = token.getStartIndex();
    token = (CommonToken)$unary_expr.stop;
    endIndex = token.getStopIndex();
  }  
   -> ^(CAST_EXPR cast_option+ unary_expr)
  ;
  
cast_option
  : 'bool'
  | 'boolean'
  | 'int'
  | 'float'
  | 'double'
  | 'real'
  | 'string'
  | UNSET_T
  | CLONE_T
  | 'object'
  | 'array'
  ;  

unary_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("unary " + startIndex + " " + endIndex);
}
  : unary_symbol_list prefix_inc_dec_expr
  {
    token = (CommonToken)$unary_symbol_list.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$prefix_inc_dec_expr.stop;
    endIndex = token.getStopIndex();
  }
    ->  ^(UNARY_EXPR unary_symbol_list prefix_inc_dec_expr)
  | prefix_inc_dec_expr
  {
    token = (CommonToken)$prefix_inc_dec_expr.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$prefix_inc_dec_expr.stop;
    endIndex = token.getStopIndex();
  }
  ;

unary_symbol_list
  : unary_symbol+
  ;  
  
unary_symbol
  : REF_T | PLUS_T | MINUS_T | TILDA_T | EXC_NOT_T
  ;

prefix_inc_dec_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("prefix " + startIndex + " " + endIndex);
}
  : post_inc_dec_expr
  {
    token = (CommonToken)$post_inc_dec_expr.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$post_inc_dec_expr.stop;
    endIndex = token.getStopIndex();
  }
  | (plus_minus)+ post_inc_dec_expr
  {
    token = (CommonToken)$plus_minus.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$post_inc_dec_expr.stop;
    endIndex = token.getStopIndex();
  }
   ->  ^(PREFIX_EXPR plus_minus+ post_inc_dec_expr)
  ;
  
post_inc_dec_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("post " + startIndex + " " + endIndex);
}
  : (instance_expr
  {
    token = (CommonToken)$instance_expr.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$instance_expr.stop;
    endIndex = token.getStopIndex();
  }
   -> instance_expr)
  (plus_minus
  {
    token = (CommonToken)$plus_minus.stop;
    endIndex = token.getStopIndex();
  }  
   -> ^(POSTFIX_EXPR $post_inc_dec_expr plus_minus))*
  ;
  
plus_minus
  : PLUS_PLUS_T
  | MINUS_MINUS_T
  ;
  
instance_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : atom_expr (INSTANCEOF_T^ class_name_reference)?
  {
    token = (CommonToken)$atom_expr.start;
    startIndex = token.getStartIndex();    
    token = (CommonToken)$atom_expr.stop;
    if ($class_name_reference.text != null) {
      token = (CommonToken)$class_name_reference.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;  

atom_expr
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  :  AT_T? variable
    {
      token = (CommonToken)$variable.start;
      if ($AT_T.text != null) {
        token = (CommonToken)$AT_T;
      }
      startIndex = token.getStartIndex();
      token = (CommonToken)$variable.stop;
      endIndex = token.getStopIndex();
      System.out.println("i write here: " + startIndex + " " + endIndex);
    }
  |  AT_T? scalar
    {
      token = (CommonToken)$scalar.start;
      if ($AT_T.text != null) {
        token = (CommonToken)$AT_T;
      }
      startIndex = token.getStartIndex();
      token = (CommonToken)$scalar.stop;
      endIndex = token.getStopIndex();
    }
  |  LEFT_PARETHESIS expression RIGHT_PARETHESIS
    {
      token = (CommonToken)$LEFT_PARETHESIS;
      startIndex = token.getStartIndex();
      token = (CommonToken)$RIGHT_PARETHESIS;
      endIndex = token.getStopIndex();
    }
  |  LIST_T LEFT_PARETHESIS assignment_list RIGHT_PARETHESIS
    {
      token = (CommonToken)$LIST_T;
      startIndex = token.getStartIndex();
      token = (CommonToken)$RIGHT_PARETHESIS;
      endIndex = token.getStopIndex();
    }
     ->  ^(LIST_T assignment_list)
  |  'array' LEFT_PARETHESIS array_pair_list? RIGHT_PARETHESIS
    {
      token = (CommonToken)$LEFT_PARETHESIS;
	    startIndex = token.getStartIndex() - 5;
	    token = (CommonToken)$RIGHT_PARETHESIS;
	    endIndex = token.getStopIndex();
    }
      ->  ^(ARRAY_DECL array_pair_list?)
  |  NEW_T class_name_reference
    {
      token = (CommonToken)$NEW_T;
      startIndex = token.getStartIndex();
      token = (CommonToken)$class_name_reference.stop;
      endIndex = token.getStopIndex();
    }  
   ->  ^(NEW_T class_name_reference)
  |  CLONE_T variable
    {
      token = (CommonToken)$CLONE_T;
      startIndex = token.getStartIndex();
      token = (CommonToken)$variable.stop;
      endIndex = token.getStopIndex();
    }
   ->  ^(CLONE_T variable)
//  |  EXIT_T (LEFT_PARETHESIS expression? RIGHT_PARETHESIS)?
//    {
//      token = (CommonToken)$EXIT_T;
//      startIndex = token.getStartIndex();
//      if ($RIGHT_PARETHESIS.text != null) {
//        token = (CommonToken)$RIGHT_PARETHESIS;
//      }
//      endIndex = token.getStopIndex();
//    }
//   ->  ^(EXIT_T expression?)
//  |  lambda_function_declaration
  |  UNSET_T LEFT_PARETHESIS variable_list RIGHT_PARETHESIS
  {
      token = (CommonToken)$UNSET_T;
      startIndex = token.getStartIndex();
      token = (CommonToken)$RIGHT_PARETHESIS;
      endIndex = token.getStopIndex();
  }
    ->  ^(UNSET_T variable_list)
  |  BACKTRICKLITERAL
    {
      token = (CommonToken)$BACKTRICKLITERAL;
      startIndex = token.getStartIndex();
      endIndex = token.getStopIndex();
    }
  |  PRINT_T expression
    {
      token = (CommonToken)$PRINT_T;
      startIndex = token.getStartIndex();
      if ($expression.text != null) {
        token = (CommonToken)$expression.stop;
      }
      endIndex = token.getStopIndex();
    }
   -> ^(PRINT_T expression)
  ;
 
//lambda_function_declaration
//  : FUNCTION_T REF_T? LEFT_PARETHESIS parameter_list? RIGHT_PARETHESIS (USE_T LEFT_PARETHESIS expr_list? RIGHT_PARETHESIS)?
//    block
//  ->  ^(METHOD_DECL REF_T? parameter_list? ^(USE_T expr_list?)?
//    block)
//  ;
     
class_name_reference
  : dynamic_name_reference
  | fully_qualified_class_name
  ;
    
dynamic_name_reference
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
  ArrayList endIndexList = new ArrayList();
  ArrayList hasArgument = new ArrayList();
  ArrayList argumentStartIndex = new ArrayList();
  ArrayList argumentEndIndex = new ArrayList();
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  
  Iterator iter = endIndexList.iterator(),
          iterHasArgument = hasArgument.iterator(),
          iterArguStart = argumentStartIndex.iterator(),
          iterArguEnd = argumentEndIndex.iterator();
  
  int currentEndIndex = endIndex;
  boolean hasArgu = false;
  SLAST arguAst = null;
  while (iter.hasNext()) {
    currentEndIndex = (Integer)iter.next();
    ast.setIndex(startIndex, currentEndIndex);
    hasArgu = (Boolean)iterHasArgument.next();
    if (hasArgu) {
      arguAst = (SLAST)ast.getChild(2);
      arguAst.setIndex((Integer)iterArguStart.next(), (Integer)iterArguEnd.next());
    }   
    
    ast = (SLAST)ast.getChild(0);
  }
  
}
  : (base_variable_with_function_calls 
    {
      token = (CommonToken)$base_variable_with_function_calls.start;
      startIndex = token.getStartIndex();
      endIndex = token.getStopIndex();
    }
      -> base_variable_with_function_calls)
    (SINGLE_ARROW_T object_property ctor_arguments?  
    {
      token = (CommonToken)$base_variable_with_function_calls.start;
      startIndex = token.getStartIndex();
      token = (CommonToken)$object_property.stop;
      endIndex = token.getStopIndex();
      endIndexList.add(0, endIndex);
      if ($ctor_arguments.text != null) {
        endIndex = ((CommonToken)$ctor_arguments.stop).getStopIndex();
        int sid = $ctor_arguments.tree.startIndex;
        int eid = $ctor_arguments.tree.endIndex;
        hasArgument.add(0, true);
        argumentStartIndex.add(sid);
        argumentEndIndex.add(eid);
        endIndex = ((CommonToken)$ctor_arguments.stop).getStopIndex();
      }
      else {
        hasArgument.add(0, false);
      }
      
    }    
    ->  ^(CALL $dynamic_name_reference object_property ctor_arguments?))*
  ;
    
assignment_list
  : assignment_element? (COMMA_T assignment_element?)*  ->  assignment_element*
  ;
 
assignment_element
  : variable
  | LIST_T LEFT_PARETHESIS assignment_list RIGHT_PARETHESIS  ->  ^(LIST_T assignment_list)
  ;
  
array_pair_list
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=array_pair_element (COMMA_T e2=array_pair_element)* COMMA_T?
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }  
     ->  array_pair_element+
  ;
  
array_pair_element
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : e1=expression (ARROW_T^ e2=expression)?
  {
    token = (CommonToken)$e1.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$e1.stop;
    if ($e2.text != null) {
      token = (CommonToken)$e2.stop;
    }
    endIndex = token.getStopIndex();
  }
  ;
    
variable
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
  ArrayList endIndexList = new ArrayList();
  ArrayList hasArgument = new ArrayList();
  ArrayList argumentStartIndex = new ArrayList();
  ArrayList argumentEndIndex = new ArrayList();
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("variable:" + startIndex + " " + endIndex);
  
  Iterator iter = endIndexList.iterator(),
          iterHasArgument = hasArgument.iterator(),
          iterArguStart = argumentStartIndex.iterator(),
          iterArguEnd = argumentEndIndex.iterator();
  
  int currentEndIndex = endIndex;
  boolean hasArgu = false;
  SLAST arguAst = null;
  while (iter.hasNext()) {
    currentEndIndex = (Integer)iter.next();
    ast.setIndex(startIndex, currentEndIndex);
    hasArgu = (Boolean)iterHasArgument.next();
    if (hasArgu) {
      arguAst = (SLAST)ast.getChild(2);
      arguAst.setIndex((Integer)iterArguStart.next(), (Integer)iterArguEnd.next());
    }   
    
    ast = (SLAST)ast.getChild(0);
  }
}
  : (base_variable_with_function_calls
    {
      token = (CommonToken)$base_variable_with_function_calls.start;
      startIndex = token.getStartIndex();
      endIndex = token.getStopIndex();
    }
     -> base_variable_with_function_calls)
    (SINGLE_ARROW_T object_property ctor_arguments?  
    {
      token = (CommonToken)$base_variable_with_function_calls.start;
      startIndex = token.getStartIndex();
      token = (CommonToken)$object_property.stop;
      endIndex = token.getStopIndex();
      endIndexList.add(0, endIndex);
      
      if ($ctor_arguments.text != null) {
        int sid = $ctor_arguments.tree.startIndex;
        int eid = $ctor_arguments.tree.endIndex;
        hasArgument.add(0, true);
        argumentStartIndex.add(sid);
        argumentEndIndex.add(eid);
        endIndex = ((CommonToken)$ctor_arguments.stop).getStopIndex();
      }
      else {
        hasArgument.add(0, false);
      }
    }    
    ->  ^(CALL $variable object_property ctor_arguments?))*
  ;

base_variable_with_function_calls
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("base var: " + startIndex + " " + endIndex);
}
  : fully_qualified_class_name? reference_variable ctor_arguments?
    {
      token = (CommonToken)$reference_variable.start;
      if ($fully_qualified_class_name.text != null) {
        token = (CommonToken)$fully_qualified_class_name.start;
      }
      startIndex = token.getStartIndex();
      token = (CommonToken)$reference_variable.stop;
      if ($ctor_arguments.text != null) {
        token = (CommonToken)$ctor_arguments.stop;
      }
      endIndex = token.getStopIndex();
    }
      ->  ^(VAR_DECL fully_qualified_class_name? reference_variable ctor_arguments?)
  | fully_qualified_class_name ctor_arguments   
    {
      token = (CommonToken)$fully_qualified_class_name.start;
      startIndex = token.getStartIndex();
      token = (CommonToken)$ctor_arguments.stop;
      endIndex = token.getStopIndex();
      
      System.out.println("ctor_ag::");
    }
      ->  ^(CALL fully_qualified_class_name ctor_arguments)
//  | STATIC '::' reference_variable        ->  ^(VAR_DECL ^('::' STATIC reference_variable))
//  | fully_qualified_class_name
  ;
  
object_property
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("obj: " + startIndex + " " + endIndex);
}
  : reference_variable
  {
     token = (CommonToken)$reference_variable.start;
     startIndex = token.getStartIndex();
     token = (CommonToken)$reference_variable.stop;
     endIndex = token.getStopIndex();
  }
  | reference_variable_without_dollar
  {
     token = (CommonToken)$reference_variable_without_dollar.start;
     startIndex = token.getStartIndex();
     token = (CommonToken)$reference_variable_without_dollar.stop;
     endIndex = token.getStopIndex();
  }
  ;
  
reference_variable
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;  
  ArrayList endIndexList = new ArrayList();
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("ref:" + startIndex + " " + endIndex);
  
  Iterator iter = endIndexList.iterator();
  int currentEndIndex = endIndex;
  while (iter.hasNext()) {
    currentEndIndex = (Integer)iter.next();
    ast.setIndex(startIndex, currentEndIndex);
    ast = (SLAST)ast.getChild(0);
  }  
}
  : (compound_variable
  {
    token = (CommonToken)$compound_variable.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$compound_variable.stop;
    endIndex = token.getStopIndex();
  }
    -> ^(VAR compound_variable))
  (
  LEFT_OPEN_RECT e=expression? RIGHT_OPEN_RECT
  {
    endIndex = ((CommonToken)$RIGHT_OPEN_RECT).getStopIndex();
    endIndexList.add(0, endIndex);
  }
    ->  ^(INDEX $reference_variable $e?)
  |
  LEFT_BRACKET e=expression RIGHT_BRACKET
  {
    endIndex = ((CommonToken)$RIGHT_BRACKET).getStopIndex();
    endIndexList.add(0, endIndex);
  }
    ->  ^(HASH_INDEX $reference_variable $e)
  )*
  ;
  
compound_variable
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;  
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("com:" + startIndex + " " + endIndex);
}
  : dollars IDENTIFIER 
   {
     token = (CommonToken)$dollars.start;
     startIndex = token.getStartIndex();
     token = (CommonToken)$IDENTIFIER;
     endIndex = token.getStopIndex();
   }
  | dollars LEFT_BRACKET! expression RIGHT_BRACKET!
   {
     token = (CommonToken)$dollars.start;
     startIndex = token.getStartIndex();
     token = (CommonToken)$RIGHT_BRACKET;
     endIndex = token.getStopIndex();
   }
  ;
  
dollars
  : DOLLAR_T+
  ;
   
reference_variable_without_dollar
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
  ArrayList endIndexList = new ArrayList();
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  
  int currentEndIndex = endIndex;
  Iterator iter = endIndexList.iterator();
  while (iter.hasNext()) {
    currentEndIndex = (Integer)iter.next();
    ast.setIndex(startIndex, currentEndIndex);
    ast = (SLAST)ast.getChild(0);
  }
}
  : (compound_variable_without_dollar
  {
    token = (CommonToken)$compound_variable_without_dollar.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$compound_variable_without_dollar.stop;
    endIndex = token.getStopIndex();
  }  
     ->  ^(VAR compound_variable_without_dollar))
  (
  LEFT_OPEN_RECT expression? RIGHT_OPEN_RECT 
  {
    endIndex = ((CommonToken)$RIGHT_OPEN_RECT).getStopIndex();
    endIndexList.add(0, endIndex);
  }
     ->  ^(INDEX $reference_variable_without_dollar expression?)
  |
  LEFT_BRACKET expression RIGHT_BRACKET 
  {
    endIndex = ((CommonToken)$RIGHT_BRACKET).getStopIndex();
    endIndexList.add(0, endIndex);
  }
     ->  ^(HASH_INDEX $reference_variable_without_dollar expression)
  )*
  ;

compound_variable_without_dollar
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;  
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("com:" + startIndex + " " + endIndex);
}
  : IDENTIFIER
  {
     token = (CommonToken)$IDENTIFIER;
     startIndex = token.getStartIndex();
     endIndex = token.getStopIndex();
  }
  | LEFT_BRACKET! expression RIGHT_BRACKET!
  {
     token = (CommonToken)$LEFT_BRACKET;
     startIndex = token.getStartIndex();
     token = (CommonToken)$RIGHT_BRACKET;
     endIndex = token.getStopIndex();
  }
  ;
  
ctor_arguments
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
  System.out.println("argu:" + startIndex + " " + endIndex);
}
  :  LEFT_PARETHESIS expr_list? RIGHT_PARETHESIS
    {
      token = (CommonToken)$LEFT_PARETHESIS;
      startIndex = token.getStartIndex();
      token = (CommonToken)$RIGHT_PARETHESIS;
      endIndex = token.getStopIndex();
    }
     ->  ^(ARGU expr_list?)
  ;
  
pure_variable
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST ast = retval.tree;
  ast.setIndex(startIndex, endIndex);
}
  : REF_T? DOLLAR_T+ IDENTIFIER    
    {
      token = (CommonToken)$DOLLAR_T;
      if ($REF_T.text != null) {
        token = (CommonToken)$REF_T;
      }
      startIndex = token.getStartIndex();
      token = (CommonToken)$IDENTIFIER;
      endIndex = token.getStopIndex();
    }
    ->  ^(VAR_DECL REF_T? DOLLAR_T+ IDENTIFIER)
  ;

scalar
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST exprToken = retval.tree;
  exprToken.setIndex(startIndex, endIndex);
}
  : constant
  {
    token = (CommonToken)$constant.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$constant.stop;
    endIndex = token.getStopIndex();
  }
    ->  ^(SCALAR constant)
  ;   
  
constant
@init {
  int startIndex = -1;
  int endIndex = -1;
  CommonToken token;
}
@after {
  SLAST exprToken = retval.tree;
  exprToken.setIndex(startIndex, endIndex);
}
  : INTLITERAL   
  | FLOATLITERAL  
  | STRINGLITERAL
//  | QUOTE_STRING
  | DOUBLELITERRAL
  | REALLITERAL
  | common_scalar  
  | fully_qualified_class_name
  {
    token = (CommonToken)$fully_qualified_class_name.start;
    startIndex = token.getStartIndex();
    token = (CommonToken)$fully_qualified_class_name.stop;
    endIndex = token.getStopIndex();
  }
  | HERE_DOC
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
  
HEREDOC : '<<<Heredoc';
END_HEREDOC : 'Heredoc;';
SOC_T : '<?';
SOC_PHP_T : '<?php';
EOC_T : '?>';
DOLLAR_T : '$';
LEFT_BRACKET : '{';
RIGHT_BRACKET : '}';
LEFT_PARETHESIS : '(';
RIGHT_PARETHESIS : ')';
LEFT_OPEN_RECT : '[';
RIGHT_OPEN_RECT : ']';
CLASS_T : 'class';
FOR_T: 'for';
BREAK_T: 'break';
EXTENDS_T : 'extends';
IMPLEMENTS_T: 'implements';
INTERFACE_T: 'interface';
FUNCTION_T: 'function';
DO_T: 'do';
WHILE_T : 'while';
SWITCH_T: 'switch';
CONTINUE_T: 'continue';
RETURN_T: 'return';
GLOBAL_T: 'global';
STATIC_T: 'static';
ECHO_T: 'echo';
FOREACH_T: 'foreach';
AS_T: 'as';
DECLARE_T: 'declare';
TRY_T: 'try';
THROW_T: 'throw';
USE_T: 'use';
ELSEIF_T: 'elseif';
ELSE_T: 'else';
ENDIF_T: 'endif';
IF_T: 'if';
ENDSWITCH_T: 'endswitch';
CASE_T: 'case';
DEFAULT_T: 'default';
CATCH_T: 'catch';
ENDFOR_T: 'endfor';
ENDWHILE_T: 'endwhile';
ENDFOREACH_T: 'endforeach';
ENDDECLARE_T: 'enddeclare';
XOR_T: 'XOR';
OR_T: 'OR';
AND_T: 'AND';
INSTANCEOF_T: 'instanceof';
CONST_T: 'const';
LIST_T: 'list';
NEW_T: 'new';
CLONE_T: 'clone';
//EXIT_T: 'exit';
UNSET_T: 'unset';
INCLUDE_T: 'include';
INCLUDE_ONCE_T: 'include_once';
REQUIRE_T: 'require';
REQUIRE_ONCE_T: 'require_once';
PRINT_T: 'print';

AT_T: '@';
PLUS_EQ : '+='; 
MINUS_EQ : '-='; 
MUL_EQ : '*='; 
DIV_EQ : '/='; 
DOT_EQ : '.='; 
PERCENT_EQ : '%='; 
BIT_AND_EQ : '&='; 
BIT_OR_EQ : '|='; 
POWER_EQ : '^='; 
LMOVE_EQ : '<<='; 
RMOVE_EQ : '>>=';
ARROW_T: '=>';
SINGLE_ARROW_T: '->';
EQUAL_EQUAL_EQUAL_T: '===';
NOT_EQUAL_EQUAL_T: '!==';
EQUAL_EQUAL_T: '==';
NOT_EQUAL_T: '!=';
EXC_NOT_T: '!';
DOMAIN_T: '::';
COMMA_T: ',';
EQUAL_T: '=';
COLON_T: ':';
QUESTION_T: '?';
LOGICAL_OR_T: '||';
LOGICAL_AND_T: '&&';
POWER_T: '^';
BIT_OR_T: '|';
REF_T: '&';
DOT_T: '.';
LSHIFT_T: '<<';
RSHIFT_T: '>>';
PERCENT_T: '%';
SEMI_COLON: ';';
PLUS_PLUS_T: '++';
MINUS_MINUS_T: '--';
PLUS_T: '+';
MINUS_T: '-';
MUL_T: '*';
DIV_T: '/';
TILDA_T: '~';
LE_T: '<=';
ME_T: '>=';
LT_T: '<';
MT_T: '>';



INTLITERAL
  : IntegerNumber LongSuffix?
  ;
  
REALLITERAL
  : HexPrefix HexDigit+  LongSuffix?
  ;

fragment
IntegerNumber
  :   '0' 
  |   '1'..'9' ('0'..'9')*    
  |   '0' ('0'..'7')+
  ;

fragment
LongSuffix
  :   'l' | 'L'
  ;

fragment
HexPrefix
  :   '0x' | '0X'
  ;
        
fragment
HexDigit
  :   ('0'..'9'|'a'..'f'|'A'..'F')
  ;

STATIC
  : '__PHP_Incomplete_Class'
  ;

FLOATLITERAL
  :   NonIntegerNumber FloatSuffix
  ;
  
DOUBLELITERRAL
  :   NonIntegerNumber DoubleSuffix
  ;
  


fragment
NonIntegerNumber
  :   ('0' .. '9')+ '.' ('0' .. '9')* Exponent?  
  |   '.' ( '0' .. '9' )+ Exponent?  
  |   ('0' .. '9')+ Exponent  
  |   ('0' .. '9')+ 
  |   
     HexPrefix (HexDigit )* 
     (    () 
      |    ('.' (HexDigit )* ) 
     ) 
     ( 'p' | 'P' ) 
     ( '0' .. '9' )+
  ;
        
fragment 
Exponent    
  :   ( 'e' | 'E' ) ( PLUS_T | MINUS_T )? ( '0' .. '9' )+ 
  ;
    
fragment 
FloatSuffix
  :   'f' | 'F' 
  ;     

fragment 
DoubleSuffix
  :   'd' | 'D' 
  ;

BACKTRICKLITERAL
  : '`'
    .* 
      '`'
    ;
    
STRINGLITERAL
  :   '\'' 
      ( .      
      )* 
      '\''
      |
      '"' 
      ( .      
      )* 
      '"'
  ;

//QUOTE_STRING
//  :  '"' 
//      ( .      
//      )* 
//     '"'
//  ;
  
HERE_DOC
  : HEREDOC (.)* END_HEREDOC
  ;
  
fragment
EscapeSequence 
  :   '\\' (   'b' 
           |   'n' 
           |   'f' 
           |   't' 
           |   'r' 
           |   '\"' 
           |   '\'' 
           |   '\\' 
           |   ('0'..'3') ('0'..'7') ('0'..'7')
           |   ('0'..'7') ('0'..'7') 
           |   ('0'..'7')
           )
  ;



IDENTIFIER
//    :   IdentifierStart IdentifierPart*
  : ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
    ;
   



fragment
IdentifierStart
    :   '\u005c'
    |   '\u005f'
    | '\u0041'..'\u005a'
    | '\u0061'..'\u007a'
  |   '\u00a2'..'\u00a5'
    |   '\u00aa'
    |   '\u00b5'
    |   '\u00ba'
    |   '\u00c0'..'\u00d6'
    |   '\u00d8'..'\u00f6'
    |   '\u00f8'..'\u0236'
    |   '\u0250'..'\u02c1'
    |   '\u02c6'..'\u02d1'
    |   '\u02e0'..'\u02e4'
    |   '\u02ee'
    |   '\u037a'
    |   '\u0386'
    |   '\u0388'..'\u038a'
    |   '\u038c'
    |   '\u038e'..'\u03a1'
    |   '\u03a3'..'\u03ce'
    |   '\u03d0'..'\u03f5'
    |   '\u03f7'..'\u03fb'
    |   '\u0400'..'\u0481'
    |   '\u048a'..'\u04ce'
    |   '\u04d0'..'\u04f5'
    |   '\u04f8'..'\u04f9'
    |   '\u0500'..'\u050f'
    |   '\u0531'..'\u0556'
    |   '\u0559'
    |   '\u0561'..'\u0587'
    |   '\u05d0'..'\u05ea'
    |   '\u05f0'..'\u05f2'
    |   '\u0621'..'\u063a'
    |   '\u0640'..'\u064a'
    |   '\u066e'..'\u066f'
    |   '\u0671'..'\u06d3'
    |   '\u06d5'
    |   '\u06e5'..'\u06e6'
    |   '\u06ee'..'\u06ef'
    |   '\u06fa'..'\u06fc'
    |   '\u06ff'
    |   '\u0710'
    |   '\u0712'..'\u072f'
    |   '\u074d'..'\u074f'
    |   '\u0780'..'\u07a5'
    |   '\u07b1'
    |   '\u0904'..'\u0939'
    |   '\u093d'
    |   '\u0950'
    |   '\u0958'..'\u0961'
    |   '\u0985'..'\u098c'
    |   '\u098f'..'\u0990'
    |   '\u0993'..'\u09a8'
    |   '\u09aa'..'\u09b0'
    |   '\u09b2'
    |   '\u09b6'..'\u09b9'
    |   '\u09bd'
    |   '\u09dc'..'\u09dd'
    |   '\u09df'..'\u09e1'
    |   '\u09f0'..'\u09f3'
    |   '\u0a05'..'\u0a0a'
    |   '\u0a0f'..'\u0a10'
    |   '\u0a13'..'\u0a28'
    |   '\u0a2a'..'\u0a30'
    |   '\u0a32'..'\u0a33'
    |   '\u0a35'..'\u0a36'
    |   '\u0a38'..'\u0a39'
    |   '\u0a59'..'\u0a5c'
    |   '\u0a5e'
    |   '\u0a72'..'\u0a74'
    |   '\u0a85'..'\u0a8d'
    |   '\u0a8f'..'\u0a91'
    |   '\u0a93'..'\u0aa8'
    |   '\u0aaa'..'\u0ab0'
    |   '\u0ab2'..'\u0ab3'
    |   '\u0ab5'..'\u0ab9'
    |   '\u0abd'
    |   '\u0ad0'
    |   '\u0ae0'..'\u0ae1'
    |   '\u0af1'
    |   '\u0b05'..'\u0b0c'
    |   '\u0b0f'..'\u0b10'
    |   '\u0b13'..'\u0b28'
    |   '\u0b2a'..'\u0b30'
    |   '\u0b32'..'\u0b33'
    |   '\u0b35'..'\u0b39'
    |   '\u0b3d'
    |   '\u0b5c'..'\u0b5d'
    |   '\u0b5f'..'\u0b61'
    |   '\u0b71'
    |   '\u0b83'
    |   '\u0b85'..'\u0b8a'
    |   '\u0b8e'..'\u0b90'
    |   '\u0b92'..'\u0b95'
    |   '\u0b99'..'\u0b9a'
    |   '\u0b9c'
    |   '\u0b9e'..'\u0b9f'
    |   '\u0ba3'..'\u0ba4'
    |   '\u0ba8'..'\u0baa'
    |   '\u0bae'..'\u0bb5'
    |   '\u0bb7'..'\u0bb9'
    |   '\u0bf9'
    |   '\u0c05'..'\u0c0c'
    |   '\u0c0e'..'\u0c10'
    |   '\u0c12'..'\u0c28'
    |   '\u0c2a'..'\u0c33'
    |   '\u0c35'..'\u0c39'
    |   '\u0c60'..'\u0c61'
    |   '\u0c85'..'\u0c8c'
    |   '\u0c8e'..'\u0c90'
    |   '\u0c92'..'\u0ca8'
    |   '\u0caa'..'\u0cb3'
    |   '\u0cb5'..'\u0cb9'
    |   '\u0cbd'
    |   '\u0cde'
    |   '\u0ce0'..'\u0ce1'
    |   '\u0d05'..'\u0d0c'
    |   '\u0d0e'..'\u0d10'
    |   '\u0d12'..'\u0d28'
    |   '\u0d2a'..'\u0d39'
    |   '\u0d60'..'\u0d61'
    |   '\u0d85'..'\u0d96'
    |   '\u0d9a'..'\u0db1'
    |   '\u0db3'..'\u0dbb'
    |   '\u0dbd'
    |   '\u0dc0'..'\u0dc6'
    |   '\u0e01'..'\u0e30'
    |   '\u0e32'..'\u0e33'
    |   '\u0e3f'..'\u0e46'
    |   '\u0e81'..'\u0e82'
    |   '\u0e84'
    |   '\u0e87'..'\u0e88'
    |   '\u0e8a'
    |   '\u0e8d'
    |   '\u0e94'..'\u0e97'
    |   '\u0e99'..'\u0e9f'
    |   '\u0ea1'..'\u0ea3'
    |   '\u0ea5'
    |   '\u0ea7'
    |   '\u0eaa'..'\u0eab'
    |   '\u0ead'..'\u0eb0'
    |   '\u0eb2'..'\u0eb3'
    |   '\u0ebd'
    |   '\u0ec0'..'\u0ec4'
    |   '\u0ec6'
    |   '\u0edc'..'\u0edd'
    |   '\u0f00'
    |   '\u0f40'..'\u0f47'
    |   '\u0f49'..'\u0f6a'
    |   '\u0f88'..'\u0f8b'
    |   '\u1000'..'\u1021'
    |   '\u1023'..'\u1027'
    |   '\u1029'..'\u102a'
    |   '\u1050'..'\u1055'
    |   '\u10a0'..'\u10c5'
    |   '\u10d0'..'\u10f8'
    |   '\u1100'..'\u1159'
    |   '\u115f'..'\u11a2'
    |   '\u11a8'..'\u11f9'
    |   '\u1200'..'\u1206'
    |   '\u1208'..'\u1246'
    |   '\u1248'
    |   '\u124a'..'\u124d'
    |   '\u1250'..'\u1256'
    |   '\u1258'
    |   '\u125a'..'\u125d'
    |   '\u1260'..'\u1286'
    |   '\u1288'
    |   '\u128a'..'\u128d'
    |   '\u1290'..'\u12ae'
    |   '\u12b0'
    |   '\u12b2'..'\u12b5'
    |   '\u12b8'..'\u12be'
    |   '\u12c0'
    |   '\u12c2'..'\u12c5'
    |   '\u12c8'..'\u12ce'
    |   '\u12d0'..'\u12d6'
    |   '\u12d8'..'\u12ee'
    |   '\u12f0'..'\u130e'
    |   '\u1310'
    |   '\u1312'..'\u1315'
    |   '\u1318'..'\u131e'
    |   '\u1320'..'\u1346'
    |   '\u1348'..'\u135a'
    |   '\u13a0'..'\u13f4'
    |   '\u1401'..'\u166c'
    |   '\u166f'..'\u1676'
    |   '\u1681'..'\u169a'
    |   '\u16a0'..'\u16ea'
    |   '\u16ee'..'\u16f0'
    |   '\u1700'..'\u170c'
    |   '\u170e'..'\u1711'
    |   '\u1720'..'\u1731'
    |   '\u1740'..'\u1751'
    |   '\u1760'..'\u176c'
    |   '\u176e'..'\u1770'
    |   '\u1780'..'\u17b3'
    |   '\u17d7' 
    |   '\u17db'..'\u17dc'
    |   '\u1820'..'\u1877'
    |   '\u1880'..'\u18a8'
    |   '\u1900'..'\u191c'
    |   '\u1950'..'\u196d'
    |   '\u1970'..'\u1974'
    |   '\u1d00'..'\u1d6b'
    |   '\u1e00'..'\u1e9b'
    |   '\u1ea0'..'\u1ef9'
    |   '\u1f00'..'\u1f15'
    |   '\u1f18'..'\u1f1d'
    |   '\u1f20'..'\u1f45'
    |   '\u1f48'..'\u1f4d'
    |   '\u1f50'..'\u1f57'
    |   '\u1f59'
    |   '\u1f5b'
    |   '\u1f5d'
    |   '\u1f5f'..'\u1f7d'
    |   '\u1f80'..'\u1fb4'
    |   '\u1fb6'..'\u1fbc'
    |   '\u1fbe'
    |   '\u1fc2'..'\u1fc4'
    |   '\u1fc6'..'\u1fcc'
    |   '\u1fd0'..'\u1fd3'
    |   '\u1fd6'..'\u1fdb'
    |   '\u1fe0'..'\u1fec'
    |   '\u1ff2'..'\u1ff4'
    |   '\u1ff6'..'\u1ffc'
    |   '\u203f'..'\u2040'
    |   '\u2054'
    |   '\u2071'
    |   '\u207f'
    |   '\u20a0'..'\u20b1'
    |   '\u2102'
    |   '\u2107'
    |   '\u210a'..'\u2113'
    |   '\u2115'
    |   '\u2119'..'\u211d'
    |   '\u2124'
    |   '\u2126'
    |   '\u2128'
    |   '\u212a'..'\u212d'
    |   '\u212f'..'\u2131'
    |   '\u2133'..'\u2139'
    |   '\u213d'..'\u213f'
    |   '\u2145'..'\u2149'
    |   '\u2160'..'\u2183'
    |   '\u3005'..'\u3007'
    |   '\u3021'..'\u3029'
    |   '\u3031'..'\u3035'
    |   '\u3038'..'\u303c'
    |   '\u3041'..'\u3096'
    |   '\u309d'..'\u309f'
    |   '\u30a1'..'\u30ff'
    |   '\u3105'..'\u312c'
    |   '\u3131'..'\u318e'
    |   '\u31a0'..'\u31b7'
    |   '\u31f0'..'\u31ff'
    |   '\u3400'..'\u4db5'
    |   '\u4e00'..'\u9fa5'
    |   '\ua000'..'\ua48c'
    |   '\uac00'..'\ud7a3'
    |   '\uf900'..'\ufa2d'
    |   '\ufa30'..'\ufa6a'
    |   '\ufb00'..'\ufb06'
    |   '\ufb13'..'\ufb17'
    |   '\ufb1d'
    |   '\ufb1f'..'\ufb28'
    |   '\ufb2a'..'\ufb36'
    |   '\ufb38'..'\ufb3c'
    |   '\ufb3e'
    |   '\ufb40'..'\ufb41'
    |   '\ufb43'..'\ufb44'
    |   '\ufb46'..'\ufbb1'
    |   '\ufbd3'..'\ufd3d'
    |   '\ufd50'..'\ufd8f'
    |   '\ufd92'..'\ufdc7'
    |   '\ufdf0'..'\ufdfc'
    |   '\ufe33'..'\ufe34'
    |   '\ufe4d'..'\ufe4f'
    |   '\ufe69'
    |   '\ufe70'..'\ufe74'
    |   '\ufe76'..'\ufefc'
    |   '\uff04'
    |   '\uff21'..'\uff3a'
    |   '\uff3f'
    |   '\uff41'..'\uff5a'
    |   '\uff65'..'\uffbe'
    |   '\uffc2'..'\uffc7'
    |   '\uffca'..'\uffcf'
    |   '\uffd2'..'\uffd7'
    |   '\uffda'..'\uffdc'
    |   '\uffe0'..'\uffe1'
    |   '\uffe5'..'\uffe6'
//    |   ('\ud800'..'\udbff') ('\udc00'..'\udfff')
    ;                
                       
fragment 
IdentifierPart
    : '\u0000'..'\u0008'
    |   '\u000e'..'\u001b'
    |   '\u0024'
    |   '\u0030'..'\u0039'
    |   '\u0041'..'\u005a'
    | '\u005c'
    |   '\u005f'
    |   '\u0061'..'\u007a'
    |   '\u007f'..'\u009f'
    |   '\u00a2'..'\u00a5'
    |   '\u00aa'
    |   '\u00ad'
    |   '\u00b5'
    |   '\u00ba'
    |   '\u00c0'..'\u00d6'
    |   '\u00d8'..'\u00f6'
    |   '\u00f8'..'\u0236'
    |   '\u0250'..'\u02c1'
    |   '\u02c6'..'\u02d1'
    |   '\u02e0'..'\u02e4'
    |   '\u02ee'
    |   '\u0300'..'\u0357'
    |   '\u035d'..'\u036f'
    |   '\u037a'
    |   '\u0386'
    |   '\u0388'..'\u038a'
    |   '\u038c'
    |   '\u038e'..'\u03a1'
    |   '\u03a3'..'\u03ce'
    |   '\u03d0'..'\u03f5'
    |   '\u03f7'..'\u03fb'
    |   '\u0400'..'\u0481'
    |   '\u0483'..'\u0486'
    |   '\u048a'..'\u04ce'
    |   '\u04d0'..'\u04f5'
    |   '\u04f8'..'\u04f9'
    |   '\u0500'..'\u050f'
    |   '\u0531'..'\u0556'
    |   '\u0559'
    |   '\u0561'..'\u0587'
    |   '\u0591'..'\u05a1'
    |   '\u05a3'..'\u05b9'
    |   '\u05bb'..'\u05bd'
    |   '\u05bf'
    |   '\u05c1'..'\u05c2'
    |   '\u05c4'
    |   '\u05d0'..'\u05ea'
    |   '\u05f0'..'\u05f2'
    |   '\u0600'..'\u0603'
    |   '\u0610'..'\u0615'
    |   '\u0621'..'\u063a'
    |   '\u0640'..'\u0658'
    |   '\u0660'..'\u0669'
    |   '\u066e'..'\u06d3'
    |   '\u06d5'..'\u06dd'
    |   '\u06df'..'\u06e8'
    |   '\u06ea'..'\u06fc'
    |   '\u06ff'
    |   '\u070f'..'\u074a'
    |   '\u074d'..'\u074f'
    |   '\u0780'..'\u07b1'
    |   '\u0901'..'\u0939'
    |   '\u093c'..'\u094d'
    |   '\u0950'..'\u0954'
    |   '\u0958'..'\u0963'
    |   '\u0966'..'\u096f'
    |   '\u0981'..'\u0983'
    |   '\u0985'..'\u098c'
    |   '\u098f'..'\u0990'
    |   '\u0993'..'\u09a8'
    |   '\u09aa'..'\u09b0'
    |   '\u09b2'
    |   '\u09b6'..'\u09b9'
    |   '\u09bc'..'\u09c4'
    |   '\u09c7'..'\u09c8'
    |   '\u09cb'..'\u09cd'
    |   '\u09d7'
    |   '\u09dc'..'\u09dd'
    |   '\u09df'..'\u09e3'
    |   '\u09e6'..'\u09f3'
    |   '\u0a01'..'\u0a03'
    |   '\u0a05'..'\u0a0a'
    |   '\u0a0f'..'\u0a10'
    |   '\u0a13'..'\u0a28'
    |   '\u0a2a'..'\u0a30'
    |   '\u0a32'..'\u0a33'
    |   '\u0a35'..'\u0a36'
    |   '\u0a38'..'\u0a39'
    |   '\u0a3c'
    |   '\u0a3e'..'\u0a42'
    |   '\u0a47'..'\u0a48'
    |   '\u0a4b'..'\u0a4d'
    |   '\u0a59'..'\u0a5c'
    |   '\u0a5e'
    |   '\u0a66'..'\u0a74'
    |   '\u0a81'..'\u0a83'
    |   '\u0a85'..'\u0a8d'
    |   '\u0a8f'..'\u0a91'
    |   '\u0a93'..'\u0aa8'
    |   '\u0aaa'..'\u0ab0'
    |   '\u0ab2'..'\u0ab3'
    |   '\u0ab5'..'\u0ab9'
    |   '\u0abc'..'\u0ac5'
    |   '\u0ac7'..'\u0ac9'
    |   '\u0acb'..'\u0acd'
    |   '\u0ad0'
    |   '\u0ae0'..'\u0ae3'
    |   '\u0ae6'..'\u0aef'
    |   '\u0af1'
    |   '\u0b01'..'\u0b03'
    |   '\u0b05'..'\u0b0c'        
    |   '\u0b0f'..'\u0b10'
    |   '\u0b13'..'\u0b28'
    |   '\u0b2a'..'\u0b30'
    |   '\u0b32'..'\u0b33'
    |   '\u0b35'..'\u0b39'
    |   '\u0b3c'..'\u0b43'
    |   '\u0b47'..'\u0b48'
    |   '\u0b4b'..'\u0b4d'
    |   '\u0b56'..'\u0b57'
    |   '\u0b5c'..'\u0b5d'
    |   '\u0b5f'..'\u0b61'
    |   '\u0b66'..'\u0b6f'
    |   '\u0b71'
    |   '\u0b82'..'\u0b83'
    |   '\u0b85'..'\u0b8a'
    |   '\u0b8e'..'\u0b90'
    |   '\u0b92'..'\u0b95'
    |   '\u0b99'..'\u0b9a'
    |   '\u0b9c'
    |   '\u0b9e'..'\u0b9f'
    |   '\u0ba3'..'\u0ba4'
    |   '\u0ba8'..'\u0baa'
    |   '\u0bae'..'\u0bb5'
    |   '\u0bb7'..'\u0bb9'
    |   '\u0bbe'..'\u0bc2'
    |   '\u0bc6'..'\u0bc8'
    |   '\u0bca'..'\u0bcd'
    |   '\u0bd7'
    |   '\u0be7'..'\u0bef'
    |   '\u0bf9'
    |   '\u0c01'..'\u0c03'
    |   '\u0c05'..'\u0c0c'
    |   '\u0c0e'..'\u0c10'
    |   '\u0c12'..'\u0c28'
    |   '\u0c2a'..'\u0c33'
    |   '\u0c35'..'\u0c39'
    |   '\u0c3e'..'\u0c44'
    |   '\u0c46'..'\u0c48'
    |   '\u0c4a'..'\u0c4d'
    |   '\u0c55'..'\u0c56'
    |   '\u0c60'..'\u0c61'
    |   '\u0c66'..'\u0c6f'        
    |   '\u0c82'..'\u0c83'
    |   '\u0c85'..'\u0c8c'
    |   '\u0c8e'..'\u0c90'
    |   '\u0c92'..'\u0ca8'
    |   '\u0caa'..'\u0cb3'
    |   '\u0cb5'..'\u0cb9'
    |   '\u0cbc'..'\u0cc4'
    |   '\u0cc6'..'\u0cc8'
    |   '\u0cca'..'\u0ccd'
    |   '\u0cd5'..'\u0cd6'
    |   '\u0cde'
    |   '\u0ce0'..'\u0ce1'
    |   '\u0ce6'..'\u0cef'
    |   '\u0d02'..'\u0d03'
    |   '\u0d05'..'\u0d0c'
    |   '\u0d0e'..'\u0d10'
    |   '\u0d12'..'\u0d28'
    |   '\u0d2a'..'\u0d39'
    |   '\u0d3e'..'\u0d43'
    |   '\u0d46'..'\u0d48'
    |   '\u0d4a'..'\u0d4d'
    |   '\u0d57'
    |   '\u0d60'..'\u0d61'
    |   '\u0d66'..'\u0d6f'
    |   '\u0d82'..'\u0d83'
    |   '\u0d85'..'\u0d96'
    |   '\u0d9a'..'\u0db1'
    |   '\u0db3'..'\u0dbb'
    |   '\u0dbd'
    |   '\u0dc0'..'\u0dc6'
    |   '\u0dca'
    |   '\u0dcf'..'\u0dd4'
    |   '\u0dd6'
    |   '\u0dd8'..'\u0ddf'
    |   '\u0df2'..'\u0df3'
    |   '\u0e01'..'\u0e3a'
    |   '\u0e3f'..'\u0e4e'
    |   '\u0e50'..'\u0e59'
    |   '\u0e81'..'\u0e82'
    |   '\u0e84'
    |   '\u0e87'..'\u0e88'        
    |   '\u0e8a'
    |   '\u0e8d'
    |   '\u0e94'..'\u0e97'
    |   '\u0e99'..'\u0e9f'
    |   '\u0ea1'..'\u0ea3'
    |   '\u0ea5'
    |   '\u0ea7'
    |   '\u0eaa'..'\u0eab'
    |   '\u0ead'..'\u0eb9'
    |   '\u0ebb'..'\u0ebd'
    |   '\u0ec0'..'\u0ec4'
    |   '\u0ec6'
    |   '\u0ec8'..'\u0ecd'
    |   '\u0ed0'..'\u0ed9'
    |   '\u0edc'..'\u0edd'
    |   '\u0f00'
    |   '\u0f18'..'\u0f19'
    |   '\u0f20'..'\u0f29'
    |   '\u0f35'
    |   '\u0f37'
    |   '\u0f39'
    |   '\u0f3e'..'\u0f47'
    |   '\u0f49'..'\u0f6a'
    |   '\u0f71'..'\u0f84'
    |   '\u0f86'..'\u0f8b'
    |   '\u0f90'..'\u0f97'
    |   '\u0f99'..'\u0fbc'
    |   '\u0fc6'
    |   '\u1000'..'\u1021'
    |   '\u1023'..'\u1027'
    |   '\u1029'..'\u102a'
    |   '\u102c'..'\u1032'
    |   '\u1036'..'\u1039'
    |   '\u1040'..'\u1049'
    |   '\u1050'..'\u1059'
    |   '\u10a0'..'\u10c5'
    |   '\u10d0'..'\u10f8'
    |   '\u1100'..'\u1159'
    |   '\u115f'..'\u11a2'
    |   '\u11a8'..'\u11f9'
    |   '\u1200'..'\u1206'        
    |   '\u1208'..'\u1246'
    |   '\u1248'
    |   '\u124a'..'\u124d'
    |   '\u1250'..'\u1256'
    |   '\u1258'
    |   '\u125a'..'\u125d'
    |   '\u1260'..'\u1286'
    |   '\u1288'        
    |   '\u128a'..'\u128d'
    |   '\u1290'..'\u12ae'
    |   '\u12b0'
    |   '\u12b2'..'\u12b5'
    |   '\u12b8'..'\u12be'
    |   '\u12c0'
    |   '\u12c2'..'\u12c5'
    |   '\u12c8'..'\u12ce'
    |   '\u12d0'..'\u12d6'
    |   '\u12d8'..'\u12ee'
    |   '\u12f0'..'\u130e'
    |   '\u1310'
    |   '\u1312'..'\u1315'
    |   '\u1318'..'\u131e'
    |   '\u1320'..'\u1346'
    |   '\u1348'..'\u135a'
    |   '\u1369'..'\u1371'
    |   '\u13a0'..'\u13f4'
    |   '\u1401'..'\u166c'
    |   '\u166f'..'\u1676'
    |   '\u1681'..'\u169a'
    |   '\u16a0'..'\u16ea'
    |   '\u16ee'..'\u16f0'
    |   '\u1700'..'\u170c'
    |   '\u170e'..'\u1714'
    |   '\u1720'..'\u1734'
    |   '\u1740'..'\u1753'
    |   '\u1760'..'\u176c'
    |   '\u176e'..'\u1770'
    |   '\u1772'..'\u1773'
    |   '\u1780'..'\u17d3'
    |   '\u17d7'
    |   '\u17db'..'\u17dd'
    |   '\u17e0'..'\u17e9'
    |   '\u180b'..'\u180d'
    |   '\u1810'..'\u1819'
    |   '\u1820'..'\u1877'
    |   '\u1880'..'\u18a9'
    |   '\u1900'..'\u191c'
    |   '\u1920'..'\u192b'
    |   '\u1930'..'\u193b'
    |   '\u1946'..'\u196d'
    |   '\u1970'..'\u1974'
    |   '\u1d00'..'\u1d6b'
    |   '\u1e00'..'\u1e9b'
    |   '\u1ea0'..'\u1ef9'
    |   '\u1f00'..'\u1f15'
    |   '\u1f18'..'\u1f1d'
    |   '\u1f20'..'\u1f45'
    |   '\u1f48'..'\u1f4d'
    |   '\u1f50'..'\u1f57'
    |   '\u1f59'
    |   '\u1f5b'
    |   '\u1f5d'
    |   '\u1f5f'..'\u1f7d'
    |   '\u1f80'..'\u1fb4'
    |   '\u1fb6'..'\u1fbc'        
    |   '\u1fbe'
    |   '\u1fc2'..'\u1fc4'
    |   '\u1fc6'..'\u1fcc'
    |   '\u1fd0'..'\u1fd3'
    |   '\u1fd6'..'\u1fdb'
    |   '\u1fe0'..'\u1fec'
    |   '\u1ff2'..'\u1ff4'
    |   '\u1ff6'..'\u1ffc'
    |   '\u200c'..'\u200f'
    |   '\u202a'..'\u202e'
    |   '\u203f'..'\u2040'
    |   '\u2054'
    |   '\u2060'..'\u2063'
    |   '\u206a'..'\u206f'
    |   '\u2071'
    |   '\u207f'
    |   '\u20a0'..'\u20b1'
    |   '\u20d0'..'\u20dc'
    |   '\u20e1'
    |   '\u20e5'..'\u20ea'
    |   '\u2102'
    |   '\u2107'
    |   '\u210a'..'\u2113'
    |   '\u2115'
    |   '\u2119'..'\u211d'
    |   '\u2124'
    |   '\u2126'
    |   '\u2128'
    |   '\u212a'..'\u212d'
    |   '\u212f'..'\u2131'
    |   '\u2133'..'\u2139'
    |   '\u213d'..'\u213f'
    |   '\u2145'..'\u2149'
    |   '\u2160'..'\u2183'
    |   '\u3005'..'\u3007'
    |   '\u3021'..'\u302f'        
    |   '\u3031'..'\u3035'
    |   '\u3038'..'\u303c'
    |   '\u3041'..'\u3096'
    |   '\u3099'..'\u309a'
    |   '\u309d'..'\u309f'
    |   '\u30a1'..'\u30ff'
    |   '\u3105'..'\u312c'
    |   '\u3131'..'\u318e'
    |   '\u31a0'..'\u31b7'
    |   '\u31f0'..'\u31ff'
    |   '\u3400'..'\u4db5'
    |   '\u4e00'..'\u9fa5'
    |   '\ua000'..'\ua48c'
    |   '\uac00'..'\ud7a3'
    |   '\uf900'..'\ufa2d'
    |   '\ufa30'..'\ufa6a'
    |   '\ufb00'..'\ufb06'
    |   '\ufb13'..'\ufb17'
    |   '\ufb1d'..'\ufb28'
    |   '\ufb2a'..'\ufb36'
    |   '\ufb38'..'\ufb3c'
    |   '\ufb3e'
    |   '\ufb40'..'\ufb41'
    |   '\ufb43'..'\ufb44'
    |   '\ufb46'..'\ufbb1'
    |   '\ufbd3'..'\ufd3d'
    |   '\ufd50'..'\ufd8f'
    |   '\ufd92'..'\ufdc7'
    |   '\ufdf0'..'\ufdfc'
    |   '\ufe00'..'\ufe0f'
    |   '\ufe20'..'\ufe23'
    |   '\ufe33'..'\ufe34'
    |   '\ufe4d'..'\ufe4f'
    |   '\ufe69'
    |   '\ufe70'..'\ufe74'
    |   '\ufe76'..'\ufefc'
    |   '\ufeff'
    |   '\uff04'
    |   '\uff10'..'\uff19'
    |   '\uff21'..'\uff3a'
    |   '\uff3f'
    |   '\uff41'..'\uff5a'
    |   '\uff65'..'\uffbe'
    |   '\uffc2'..'\uffc7'
    |   '\uffca'..'\uffcf'
    |   '\uffd2'..'\uffd7'
    |   '\uffda'..'\uffdc'
    |   '\uffe0'..'\uffe1'
    |   '\uffe5'..'\uffe6'
    |   '\ufff9'..'\ufffb'
//    |   ('\ud800'..'\udbff') ('\udc00'..'\udfff')
  ;

WS  
  :   (
           ' '
      |    '\r'
      |    '\t'
      |    '\u000C'
      |    '\n'
      ) 
          {
              $channel = HIDDEN;
          }          
    ;

COMMENT
         @init{
            boolean isPHPDoc = false;
        }
    :   '/*'
            {
                if((char)input.LA(1) == '*'){
                    isPHPDoc = true;
                }
            }
        (options {greedy=false;} : . )* 
        '*/'
            {
                if(isPHPDoc==true){
                    $channel = COMMENT;
                }else{
                    $channel = COMMENT;
                }
            }
    ;

LINE_COMMENT
    :   '#' ~('\n'|'\r')* ('\r\n' | '\r' | '\n') {skip();}
    |   '#' ~('\n'|'\r')* 
            {
                $channel = COMMENT;
            }
    |   '//' ~('\n'|'\r')*  ('\r\n' | '\r' | '\n') {skip();}
    |   '//' ~('\n'|'\r')*     // a line comment could appear at the end of the file without CR/LF
            {
                $channel = COMMENT;
            }
    ;