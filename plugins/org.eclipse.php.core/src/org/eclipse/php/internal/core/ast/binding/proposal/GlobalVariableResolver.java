package org.eclipse.php.internal.core.ast.binding.proposal;

import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Variable;

/**
   This represents a Variable Resolver used for global statements.
   All variables returned will therefore be global. 

   @author samweber
   @version $Revision: 1.1 $
 */
public class GlobalVariableResolver implements VariableResolver 
{
  /** The one instance of this class. */
  private static final GlobalVariableResolver _resolver
    = new GlobalVariableResolver();
  
  /** Private constructor -- there should only be one of these. */
  private GlobalVariableResolver()
    {}

  /** Returns the GlobalVariableResolver. */
  public static GlobalVariableResolver getResolver()
    {
      return _resolver;
    }

  /** Given a variable name, returns its resolved value. */
  public ResolvedVariable resolveName(String name)
  {
    return ResolvedVariable.getBinding(ResolvedVariable.ScopeKind.GLOBAL,
                                       name);
  }

  // Same javadoc as interface
  public ResolvedVariable resolveName(Variable var)
  {
    final Expression varname = var.getVariableName();
    if (var.isDollared() && (varname instanceof Identifier))
      {
        return resolveName(((Identifier)varname).getName());
      }
    else
      {
        return ResolvedVariable.UNKNOWN;
      }
  }

  /** Returns a VariableResolver which has the same variables marked
      global as this one and, in addition, has the given names also
      marked global.  This method might return the same object as
      this one if the given objects are already marked global. */
  public VariableResolver addGlobalNames(String[] names)
  {
    return this;
  }

  // same javadoc as interface
  public VariableResolver addGlobalNames(Variable[] names)
  {
    return this;
  }

}
