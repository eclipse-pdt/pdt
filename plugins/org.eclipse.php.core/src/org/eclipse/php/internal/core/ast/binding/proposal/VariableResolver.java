package org.eclipse.php.internal.core.ast.binding.proposal;

import org.eclipse.php.internal.core.ast.nodes.Variable;

/**
   This interface represents objects which, given a PHP variable name
   returns the resolved variable names.

   @author samweber
   @version $Revision: 1.1 $
 */
public interface VariableResolver 
{
  /** Given a variable name, returns its resolved value. */
  ResolvedVariable resolveName(String name);

  /** Given a PHP Variable AST node, returns the resolved value of
      that variable.  If said variable is not a simple name (like $a), the
      the undetermined resolved object will be returned. */
  ResolvedVariable resolveName(Variable var);

  /** Returns a variable resolver with the given names made global.  If
      null is provided, then that will denote a variable whose name is
      not known statically. If the object already has the given names
      global, then this method may return itself. */
  VariableResolver addGlobalNames(String[] newglobals);

  /** Returns a variable resolver with the given Variables made global. If
      any Variable is not a simple variable (ie, like "$a"), then this
      method will consider it to have a statically unknown name.  If the
      statid varaibles are already global, then this method may return 
      itself. */
  VariableResolver addGlobalNames(Variable[] newglobals);
     
}
