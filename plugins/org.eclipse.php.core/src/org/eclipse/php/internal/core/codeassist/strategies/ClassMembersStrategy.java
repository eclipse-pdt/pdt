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
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.*;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext.Trigger;
import org.eclipse.php.internal.core.codeassist.contexts.ClassObjMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassStaticMemberContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.TraitUtils;
import org.eclipse.php.internal.core.typeinference.UseTrait;

/**
 * This strategy completes class members: $a->|, A::|, etc...
 * 
 * @author michael
 */
public abstract class ClassMembersStrategy extends AbstractCompletionStrategy {

	ClassStaticMemberContext staticMemberContext;
	boolean isFunctionParameterContext = false;

	public ClassMembersStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassMembersStrategy(ICompletionContext context) {
		super(context);
		if (context instanceof ClassStaticMemberContext) {
			staticMemberContext = (ClassStaticMemberContext) context;
			isFunctionParameterContext = staticMemberContext
					.isFunctionParameterContext();
		}

	}

	protected boolean showStaticMembers(ClassMemberContext context) {
		return context.getTriggerType() == Trigger.CLASS
				|| context.getPhpVersion().isGreaterThan(PHPVersion.PHP5);
	}

	protected boolean showNonStaticMembers(ClassMemberContext context) {
		return context.getTriggerType() == Trigger.OBJECT
				|| isParentCall(context);
	}

	protected boolean isThisCall(ClassMemberContext context) {
		return ((context instanceof ClassObjMemberContext) && ((ClassObjMemberContext) context)
				.isThis());
	}

	protected boolean isDirectThis(ClassMemberContext context) {
		return ((context instanceof ClassObjMemberContext) && ((ClassObjMemberContext) context)
				.isDirectThis());
	}

	protected boolean isSelfCall(ClassMemberContext context) {
		return ((context instanceof ClassStaticMemberContext) && ((ClassStaticMemberContext) context)
				.isSelf());
	}

	protected boolean isDirectSelfCall(ClassMemberContext context) {
		return ((context instanceof ClassStaticMemberContext) && ((ClassStaticMemberContext) context)
				.isDirectSelf());
	}

	protected boolean isParentCall(ClassMemberContext context) {
		return ((context instanceof ClassStaticMemberContext) && ((ClassStaticMemberContext) context)
				.isParent());
	}

	protected boolean isDirectParentCall(ClassMemberContext context) {
		return ((context instanceof ClassStaticMemberContext) && ((ClassStaticMemberContext) context)
				.isDirectParent());
	}

	protected boolean showStrictOptions() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_SHOW_STRICT_OPTIONS, false, null);
	}

	/**
	 * Returns whether the specified member is visible in current context
	 * 
	 * @param member
	 * @param context
	 * @return
	 * @throws ModelException
	 */
	protected boolean isVisible(IMember member, ClassMemberContext context)
			throws ModelException {
		if (isThisCall(context) || isSelfCall(context)) {
			return true;
		}
		int flags = member.getFlags();
		if (isParentCall(context)) {
			return !PHPFlags.isPrivate(flags);
		}
		return !PHPFlags.isPrivate(flags) && !PHPFlags.isProtected(flags);
	}

	/**
	 * Returns whether the specified member should be filtered from the code
	 * assist
	 * 
	 * @param member
	 * @param type
	 * @param context
	 * @return
	 * @throws ModelException
	 */
	protected boolean isFiltered(IMember member, IType type,
			ClassMemberContext context) throws ModelException {

		if (context.getPhpVersion() == PHPVersion.PHP4) {
			if (!isVisible(member, context)) {
				return true;
			}
			int flags = member.getFlags();
			if (!showNonStaticMembers(context) && !PHPFlags.isStatic(flags)
					&& !PHPFlags.isConstant(flags)) {
				return true;
			}
			if (!showStaticMembers(context)
					&& (PHPFlags.isStatic(flags) || PHPFlags.isConstant(flags))) {
				return true;
			}
			return false;

		} else if (context.getPhpVersion().isGreaterThan(PHPVersion.PHP4)) {
			/* check 0 */
			int flags = member.getFlags();
			if (PHPFlags.isConstant(member.getFlags())) {
				if (context.getTriggerType() == Trigger.CLASS) {
					return false; // 17:1-4
				} else if (context.getTriggerType() == Trigger.OBJECT) {
					return true;// 17:5-7
				}
				/* check 1 */
			} else if (PHPFlags.isStatic(flags)) {
				/* check 2 */
				if (member instanceof IField) {
					/* check 3 */
					if (context.getTriggerType() == Trigger.CLASS
							&& !isFunctionParameterContext) {
						/* check 5 */
						if (PHPFlags.isPrivate(flags)
								&& (member.getDeclaringType().equals(type) || isTraitMember(
										context.getPhpVersion(), type, member))) {
							if (isParent(context)) { // is Parent
								return true; // 1:1
							} else if (isSelfKeyword(context)) {
								return false;// 1:2
							} else if (isStaticAccessFromInsideClass(context)) {
								return false;// 1:3
							} else if (isStaticAccessFromOutsideClass(context)) {
								return true;// 1:4
							}
						} else if (PHPFlags.isProtected(flags)) {
							if (isParent(context)) { // is Parent
								return false; // 2:1
							} else if (isSelfKeyword(context)) {
								return false;// 2:2
							} else if (isStaticAccessFromInsideClass(context)) {
								return false;// 2:3
							} else if (isStaticAccessFromOutsideClass(context)) {
								return true;// 2:4
							}
						} else if (PHPFlags.isPublic(flags)) {
							return false;
						}

					} else if (context.getTriggerType() == Trigger.OBJECT) {
						if (PHPFlags.isPrivate(flags)
								&& (member.getDeclaringType().equals(type) || isTraitMember(
										context.getPhpVersion(), type, member))) {
							if (isThisKeyWord(context) && showStrictOptions()) {
								return false; // 1:5
							} else if (isIndirectThis(context)) {
								return true;// 1:6
							} else if (isSimpleArrow(context)) {
								return true;// 1:7
							}
						} else if (PHPFlags.isProtected(flags)) {
							if (isThisKeyWord(context) && showStrictOptions()) {
								return false; // 2:5
							} else if (isIndirectThis(context)
									&& isDirectThis(context)) {
								return true;// 2:6
							} else if (isSimpleArrow(context)) {
								return true;// 2:7
							}
						} else if (PHPFlags.isPublic(flags)) {
							if (isThisKeyWord(context) && showStrictOptions()) {
								return false; // 3:5
							} else if (isIndirectThis(context)
									&& showStrictOptions()) {
								return false;// 3:6
							} else if (isSimpleArrow(context)
									&& showStrictOptions()) {
								return false;// 3:7
							}
						}
					}

				} else if (member instanceof IMethod) {
					if (context.getTriggerType() == Trigger.CLASS
							&& !isFunctionParameterContext) {
						if (PHPFlags.isPrivate(flags)
								&& (member.getDeclaringType().equals(type) || isTraitMember(
										context.getPhpVersion(), type, member))) {
							if (isParent(context)) {
								return true; // 5:1
							} else if (isSelfCall(context)) {
								return false;// 5:2
							} else if (!isThisCall(context)
									&& !isParentCall(context)
									&& isSelfCall(context)) {
								return false;// 5:3
							} else if (!isThisCall(context)
									&& !isParentCall(context)
									&& !isSelfCall(context)) {
								return true;// 5:4
							}
						} else if (PHPFlags.isProtected(flags)) {
							if (isParent(context)) {
								return false; // 6:1
							} else if (isSelfCall(context)) {
								return false;// 6:2
							} else if (!isThisCall(context)
									&& !isParentCall(context)
									&& isSelfCall(context)) {
								return false;// 6:3
							} else if (!isThisCall(context)
									&& !isParentCall(context)
									&& !isSelfCall(context)) {
								return true;// 6:4
							}
						} else if (PHPFlags.isPublic(flags)) {
							if (isParent(context)) {
								return false; // 7:1
							} else if (isSelfCall(context)) {
								return false;// 7:2
							} else if (!isThisCall(context)
									&& !isParentCall(context)
									&& isSelfCall(context)) {
								return false;// 7:3
							} else if (!isThisCall(context)
									&& !isParentCall(context)) {
								return false;
							}
						} else if (PHPFlags.isDefault(flags)) {
							if (isParent(context)) {
								return false; // 8:1
							} else if (isSelfCall(context)) {
								return false;// 8:2
							} else if (!isThisCall(context)
									&& !isParentCall(context)
									&& isSelfCall(context)) {
								return false;// 8:3
							} else if (!isThisCall(context)
									&& !isParentCall(context)) {
								return false;
							}
						}
					} else if (context.getTriggerType() == Trigger.OBJECT) {
						if (PHPFlags.isPrivate(flags)
								&& (member.getDeclaringType().equals(type) || isTraitMember(
										context.getPhpVersion(), type, member))) {
							if (isThisKeyWord(context)) {
								return false; // 5:5
							} else if (isIndirectThis(context)) {
								return false;// 5:6
							} else if (isSimpleArrow(context)) {
								return true;// 5:7
							}
						} else if (PHPFlags.isProtected(flags)) {
							if (isThisKeyWord(context)) {
								return false; // 6:5
							} else if (isIndirectThis(context)) {
								return false;// 6:6
							} else if (isSimpleArrow(context)) {
								return true;// 6:7
							}
						} else if (PHPFlags.isPublic(flags)) {
							if (isThisKeyWord(context)) {
								return false; // 7:5
							} else if (isIndirectThis(context)) {
								return false;// 7:6
							} else if (isSimpleArrow(context)) {
								return false;// 7:7
							}
						} else if (PHPFlags.isDefault(flags)) {
							if (isThisKeyWord(context)) {
								return false; // 8:5
							} else if (isIndirectThis(context)) {
								return false;// 8:6
							} else if (isSimpleArrow(context)) {
								return false;// 8:7
							}
						}
					}
				}
			} else {
				// not static
				if (member instanceof IField) {
					if (context.getTriggerType() == Trigger.CLASS
							&& !isFunctionParameterContext) {
						return true; // 9:1 - 12:4
					} else if (context.getTriggerType() == Trigger.OBJECT) {
						if (PHPFlags.isPrivate(flags)
								&& (member.getDeclaringType().equals(type) || isTraitMember(
										context.getPhpVersion(), type, member))) {
							if (isThisKeyWord(context)) {
								return false; // 9:5
							} else if (isIndirectThis(context)) {
								return false;// 9:6
							} else if (isSimpleArrow(context)) {
								return true;// 9:7
							}
						} else if (PHPFlags.isProtected(flags)) {
							if (isThisKeyWord(context)) {
								return false; // 10:5
							} else if (isIndirectThis(context)) {
								return false;// 10:6
							} else if (isSimpleArrow(context)) {
								return true;// 10:7
							}
						} else if (PHPFlags.isPublic(flags)) {
							if (isThisKeyWord(context)) {
								return false; // 11:5
							} else if (isIndirectThis(context)) {
								return false;// 11:6
							} else if (isSimpleArrow(context)) {
								return false;// 11:7
							}
						}
					}

				} else if (member instanceof IMethod) {
					if (context.getTriggerType() == Trigger.CLASS
							&& !isFunctionParameterContext) {
						if (PHPFlags.isPrivate(flags)
								&& (member.getDeclaringType().equals(type) || isTraitMember(
										context.getPhpVersion(), type, member))) {
							if (isParent(context)) {
								return true; // 13:1
							} else if (isSelfKeyword(context)) {
								return false;// 13:2
							} else if (isStaticAccessFromInsideClass(context)) {
								return false;// 13:3
							} else if (isStaticAccessFromOutsideClass(context)) {
								return true;// 13:4
							}
						} else if (PHPFlags.isProtected(flags)) {
							if (isParent(context)) {
								return false; // 14:1
							} else if (isSelfKeyword(context)) {
								return false;// 14:2
							} else if (isStaticAccessFromInsideClass(context)) {
								return false;// 14:3
							} else if (isStaticAccessFromOutsideClass(context)) {
								return true;// 14:4
							}
						} else if (PHPFlags.isPublic(flags)) {
							if (isParent(context)) {
								return false; // 15:1
							} else if (isSelfKeyword(context)) {
								return false;// 15:2
							} else if (isStaticAccessFromInsideClass(context)) {
								return false;// 15:3
							} else if (isStaticAccessFromOutsideClass(context)
									&& showStrictOptions()) {
								return false;// 15:4
							}
						} else if (PHPFlags.isDefault(flags)) {
							if (isParent(context)) {
								return false; // 16:1
							} else if (isSelfKeyword(context)) {
								return false;// 16:2
							} else if (isStaticAccessFromInsideClass(context)) {
								return false;// 16:3
							} else if (isStaticAccessFromOutsideClass(context)
									&& showStrictOptions()) {
								return false;// 16:4
							}
						}
					} else if (context.getTriggerType() == Trigger.OBJECT) {
						if (PHPFlags.isPrivate(flags)
								&& (member.getDeclaringType().equals(type) || isTraitMember(
										context.getPhpVersion(), type, member))) {
							if (isThisKeyWord(context)) {
								return false; // 13:5
							} else if (isIndirectThis(context)) {
								return false;// 13:6
							} else if (isSimpleArrow(context)) {
								return true;// 13:7
							}
						} else if (PHPFlags.isProtected(flags)) {
							if (isThisKeyWord(context)) {
								return false; // 14:5
							} else if (isIndirectThis(context)) {
								return false;// 14:6
							} else if (isSimpleArrow(context)) {
								return true;// 14:7
							}
						} else if (PHPFlags.isPublic(flags)) {
							if (isThisKeyWord(context)) {
								return false; // 15:5
							} else if (isIndirectThis(context)) {
								return false;// 15:6
							} else if (isSimpleArrow(context)) {
								return false;// 15:7
							}
						} else if (PHPFlags.isDefault(flags)) {
							if (isThisKeyWord(context)) {
								return false; // 16:5
							} else if (isIndirectThis(context)) {
								return false;// 16:6
							} else if (isSimpleArrow(context)) {
								return false;// 16:7
							}
						}
					}

				}

			}
		}
		return true;
	}

	private boolean isTraitMember(PHPVersion phpVersion, IType type,
			IMember member) {
		return isTraitMember(phpVersion, type, member, new HashSet<IType>());
	}

	private boolean isTraitMember(PHPVersion phpVersion, IType type,
			IMember member, Set<IType> typeSet) {
		if (phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
			UseTrait useTrait = TraitUtils.parse(type);
			String typeName = PHPModelUtils.getFullName(member
					.getDeclaringType());
			for (String trait : useTrait.getTraits()) {
				if (trait.equals(typeName)) {
					return true;
				}
			}

			for (String trait : useTrait.getTraits()) {
				IType[] traits = PhpModelAccess.getDefault().findTraits(trait,
						MatchRule.EXACT, 0, 0,
						TraitUtils.createSearchScope(type), null);

				for (IType traitType : traits) {
					return isTraitMember(phpVersion, traitType, member, typeSet);
				}
			}
		}
		return false;
	}

	/**
	 * is 'parent' keyword
	 * 
	 * @param context
	 * @return
	 */
	private boolean isParent(ClassMemberContext context) {
		return !isThisCall(context) && isParentCall(context)
				&& isDirectParentCall(context);
	}

	/**
	 * is 'self' keyword
	 * 
	 * @param context
	 * @return
	 */
	private boolean isSelfKeyword(ClassMemberContext context) {
		return !isThisCall(context) && !isParentCall(context)
				&& isSelfCall(context) && isDirectSelfCall(context);
	}

	/**
	 * class A { static private $var = 0; function foo() { A::$var; //has an
	 * access to private $var field } }
	 * 
	 * @param context
	 * @return
	 */
	private boolean isStaticAccessFromInsideClass(ClassMemberContext context) {
		return !isThisCall(context) && !isParentCall(context)
				&& isSelfCall(context) && !isDirectSelfCall(context);
	}

	/**
	 * class A { static private $var = 0; public function foo() {} } A::$var;
	 * //has no access to private $var field A::foo(); //has access to public
	 * function foo
	 * 
	 * 
	 * @param context
	 * @return
	 */
	private boolean isStaticAccessFromOutsideClass(ClassMemberContext context) {
		return !isThisCall(context) && !isParentCall(context)
				&& !isSelfCall(context) && !isDirectSelfCall(context);
	}

	/**
	 * simple 'this' keyword case
	 * 
	 * @param context
	 * @return
	 */
	private boolean isThisKeyWord(ClassMemberContext context) {
		return isThisCall(context) && isDirectThis(context);
	}

	/**
	 * class A { static private $var = 0; function foo() { $a = new A(); $a->| }
	 * } access level as for 'this' keyword in this case.
	 * 
	 * @param context
	 * @return
	 */
	private boolean isIndirectThis(ClassMemberContext context) {
		return isThisCall(context) && !isDirectThis(context);
	}

	/**
	 * case 6 simple -> case
	 * 
	 * @param context
	 * @return
	 */
	private boolean isSimpleArrow(ClassMemberContext context) {
		return !isThisCall(context) && !isDirectThis(context);
	}

	/**
	 * Removes overridden members from the completion list
	 * 
	 * @param members
	 *            Class/Interface memebers in type hierarchy order (from bottom
	 *            to up)
	 */
	protected <T extends IMember> Collection<T> removeOverriddenElements(
			Collection<T> members) {
		List<T> result = new LinkedList<T>();
		List<T> newMembers = new ArrayList<T>();
		newMembers.addAll(members);
		Collections.sort(newMembers, new Comparator<T>() {

			public int compare(T o1, T o2) {
				try {
					int flag1 = getFlag(o1.getFlags());
					int flag2 = getFlag(o2.getFlags());
					return flag2 - flag1;
				} catch (ModelException e) {
				}
				return 0;
			}

			private int getFlag(int flags) {
				if (Flags.isPublic(flags)) {
					return Modifiers.AccPublic;
				}
				if (Flags.isProtected(flags)) {
					return Modifiers.AccProtected;
				}
				if (Flags.isPrivate(flags)) {
					return Modifiers.AccPrivate;
				}
				return Modifiers.AccPrivate;
			}
		});
		Set<String> processed = new HashSet<String>();
		for (IMember member : newMembers) {
			if (processed.add(member.getElementName())) {
				result.add((T) member);
			}
		}
		return result;
	}
}
