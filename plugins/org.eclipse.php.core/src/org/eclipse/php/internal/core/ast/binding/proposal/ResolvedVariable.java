package org.eclipse.php.internal.core.ast.binding.proposal;

import java.util.Hashtable;

/**
   This class reflects scope-resolved variable names.  That is, it
   represents a variable name along with an identification of
   what scope the variable name refers to.
   <p/>
   Normally, a variable name will be either local or global.  (We consider
   the superglobals as global variables which will always be bound to
   the global scope.)  However, in cases such as:
   <pre>
     if (...)
      {
        global $x;
        ...
      };
   </pre>
   then the identifier $x will, afterwards, have either local or global
   scope, depending on control-flow information.  Also, the statement
   <code>global $$x;</code> will result in some variable being global, but
   we cannot, in general, determine which.
   <p/>
   There is one distinguished value which represents a variable which
   is of unknown scope and whose name is unknown.  This allows instances
   of this class to be used to represent all variable references, including
   uses of variable variables, whose values cannot be computed statically.
   <p/>
   Instances of this class can be compared using ==, because each variable
   name/scope will be represented by at most one instance.

   @author samweber
   @version $Revision: 1.2 $
*/

public class ResolvedVariable
{
  /** The kinds of scope.  UNDETERMINED represents when a variable name
      can be either global or local, depending upon prior control-flow. */
  public enum ScopeKind {GLOBAL, LOCAL, UNDETERMINED};

  /** The special, distinguished value of which has undetermined scope
      and unknown name. */
  public static final ResolvedVariable UNKNOWN = new ResolvedVariable();

  /** The map holding all global instances. */
  private static final Hashtable<String, ResolvedVariable> _globals =
    new Hashtable<String, ResolvedVariable>();

  /** The map holding all local instances. */
  private static final Hashtable<String, ResolvedVariable> _locals =
    new Hashtable<String, ResolvedVariable>();
  
  /** The map holding all undetermined instances.  This will not be
      initially constructed, because it will be frequently unused. */
  private static Hashtable<String, ResolvedVariable> _undetermined = null;

  /** The name of the variable */
  private final String _name;

  /** The scope kind */
  private final ScopeKind _scope;

  /** The private constructor. */
  private ResolvedVariable(ScopeKind kind, String name)
  {
    assert (null != name):"ResolvedVariable created with null name."; //$NON-NLS-1$
    _scope = kind;
    _name = name;
    switch (kind)
      {
      case GLOBAL:
        _globals.put(_name, this);
        break;
      case LOCAL:
        _locals.put(_name, this);
        break;
      case UNDETERMINED:
        if (_undetermined == null)
          {
            _undetermined = new Hashtable<String, ResolvedVariable>();
          }
        _undetermined.put(_name, this);
      }
  }

  /** A private constructor, used to create the unknown value. */
  private ResolvedVariable()
  {
    _name = null;
    _scope = null;
    // not put into any hash table!
  }

  /** Returns the name of this variable.  This will be non-null, unless
   this object is the UNKNOWN value. */
  public String getName()
  {
    return _name;
  }

  /** Returns the scope kind. */
  public ScopeKind getScope()
  {
    return _scope;
  }

  public String toString()
  {
    return "("+_scope+","+_name+")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }

  /** Given a scope and a variable name, returns the corresponging
      instance.*/
  public static ResolvedVariable getBinding(ScopeKind scope, String name)
  {
    if (null == name)
      {
        return UNKNOWN;
      }
    ResolvedVariable r;
    switch (scope)
      {
      case GLOBAL:
        r = _globals.get(name);
        break;
      case LOCAL:
        r = _locals.get(name);
        break;
      case UNDETERMINED:
        if (null == _undetermined)
          {
            r = null;
          }
        else
          {
            r = _undetermined.get(name);
          }
        break;
       default:
          throw new AssertionError("Nonexistent scope value"); //$NON-NLS-1$
      }
    if (null == r)
      {
        // Make a new instance!
        return new ResolvedVariable(scope, name);
      }
    else
      {
        return r;
      }
  }

  /** A test method. */
  public static void printContents()
  {
    System.out.println("Globals:"); //$NON-NLS-1$
    for (final ResolvedVariable r:_globals.values())
      {
        System.out.println("  "+r); //$NON-NLS-1$
      }
    System.out.println("Locals:"); //$NON-NLS-1$
    for (final ResolvedVariable r:_locals.values())
      {
        System.out.println("  "+r); //$NON-NLS-1$
      }
    System.out.println("Undetermined:"); //$NON-NLS-1$
    if (null == _undetermined)
      {
        System.out.println("  None."); //$NON-NLS-1$
      }
    else
      {
        for (final ResolvedVariable r:_undetermined.values())
          {
            System.out.println("  "+r); //$NON-NLS-1$
          }
      }
    
  }

}
    
