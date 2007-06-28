package org.eclipse.php.internal.core.ast.binding.proposal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import org.eclipse.php.internal.core.ast.binding.proposal.ResolvedVariable.ScopeKind;
import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Variable;

/**
   This class represents objects which resolve variable names for
   local scopes (ie, methods and functions.)
   
   @author samweber
   @version $Revision: 1.1 $
 */
public class LocalVariableResolver implements VariableResolver 
{
  /** The set containing the superglobals. */
  private static final HashSet<String> _supers =
    new HashSet<String>();
  {
    final String[] names = {"_GET", "_POST", "_FILES", "_COOKIE",
                             "_REQUEST", "_SESSION", "_SERVER", "_ENV",
                             "GLOBALS"};
    for (final String n: names)
      {
        _supers.add(n);
      }
  }

  /** The set of names known to be global, other than the superglobals. */
  private final HashSet<String> _globals;

  /** The set of names known to be undetermined. */
  private final HashSet<String> _undetermined;

  /** Whether or not this resolver is in an indeterminate state: ie,
      if the variable variable was made global, or some such. */
  private final boolean _indeterminate;

  /** Constructs a resolver which has no variables known to be global
      other than the superglobals. */
  public LocalVariableResolver()
  {
    _globals = new HashSet<String>();
    _undetermined = new HashSet<String>();
    _indeterminate = false;
  }

  /** Constructs a resolver which has the given variables known to be
      global, and no others. */
  public LocalVariableResolver(Collection<String> globals)
  {
    _globals = new HashSet<String>(globals);
    _undetermined = new HashSet<String>();
    _indeterminate = false;
  }

  /** Constructs a new resolver, given two existing ones.  Any variables
      known to be global in one but not in the other will be undetermined. */
  public LocalVariableResolver(LocalVariableResolver x, LocalVariableResolver y)
  {
    _indeterminate = x._indeterminate || y._indeterminate;
    if (_indeterminate)
      {
        _globals = new HashSet<String>(x._globals);
        _globals.retainAll(y._globals);
        _undetermined = new HashSet<String>();
      }
    else
      {
        _globals = new HashSet<String>(x._globals);
        _globals.retainAll(y._globals);
        _undetermined = new HashSet<String>(x._globals);
        _undetermined.addAll(y._globals);
        _undetermined.removeAll(_globals);
        _undetermined.addAll(x._undetermined);
        _undetermined.addAll(y._undetermined);
      }
  }

  /** Constructs a new resolver, given an existing one and an
      array of names to be made global.  If any of the names are
      null, then it is assumed that the name is not able to be
      determined statically, and therefore this resolver becomes
      indetermined. */
  public LocalVariableResolver(LocalVariableResolver v, String[] names)
  {
    _globals = new HashSet<String>(v._globals);
    _undetermined = new HashSet<String>(v._undetermined);
    boolean i = v._indeterminate;
    for (final String s:names)
      {
        if (null == s)
          {
            i=true;
          }
        else
          {
            _globals.add(s);
            _undetermined.remove(s);
          }
      }
    _indeterminate = i;
    if (_indeterminate)
    {
      _undetermined.clear();
    }
  }

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

  // same javadoc as interface
  public VariableResolver addGlobalNames(String[] names)
  {
    final Vector<String> newnames = new Vector<String>();
    for (final String n:names)
      {
        if (!(_globals.contains(n)))
          {
            newnames.add(n);
          }
      }
    if (newnames.isEmpty())
      {
        return this;
      }
    else
      {
        return new LocalVariableResolver(this,
                                         newnames.toArray(new String[newnames.size()]));
      }
  }

  // same javadoc as interface
  public VariableResolver addGlobalNames(Variable[] names)
  {
    final String[] snames = new String[names.length];
    for (int i=0; i<names.length; i++)
      {
        final Variable var = names[i];
        final Expression varname = var.getVariableName();
        if (var.isDollared() && (varname instanceof Identifier))
          {
            snames[i] = ((Identifier)varname).getName();
          }
        else
          {
            snames[i] = null;
          }
      }
    return addGlobalNames(snames);
  }

  /** Given a variable name, returns its resolved value. */
  public ResolvedVariable resolveName(String name)
  {
    if (null == name)
      {
        return ResolvedVariable.UNKNOWN;
      }
    if (_supers.contains(name) || _globals.contains(name))
      {
        return ResolvedVariable.getBinding(ScopeKind.GLOBAL,
                                           name);
      }
    if (_indeterminate || _undetermined.contains(name))
      {
        return ResolvedVariable.getBinding(ScopeKind.UNDETERMINED, name);
      }
    return ResolvedVariable.getBinding(ScopeKind.LOCAL, name);
  }

  public String toString()
  {
    String r = "LocalVariableResolver: "+super.toString()+" "+(_indeterminate?"INDETERMINATE":"determinate")+" ";
    r+= "Globals(";
    for (final String g:_globals)
      {
        r+=g+",";
      }
    r+=") Undetermined (";
    for (final String u:_undetermined)
      {
        r += u+",";
      }
    return r+")";
  }


}
