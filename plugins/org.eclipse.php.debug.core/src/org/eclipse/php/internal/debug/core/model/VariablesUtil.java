/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.*;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;

/**
 * Utility class for PHP debugger's variables.
 * 
 * @author Bartlomiej Laczkowski
 */
public class VariablesUtil {

	private static final class ContextMembersComparator implements Comparator<Object> {

		@Override
		public int compare(Object o1, Object o2) {
			if (!(o1 instanceof IVariableFacet) && !(o2 instanceof IVariableFacet))
				return 0;
			return getPriority((IVariableFacet) o1) - getPriority((IVariableFacet) o2);
		}

		private int getPriority(IVariableFacet facet) {
			if (facet.hasFacet(KIND_THIS) || facet.hasFacet(VIRTUAL_CLASS))
				return 0;
			if (facet.hasFacet(KIND_LOCAL))
				return 1;
			if (facet.hasFacet(KIND_SUPER_GLOBAL))
				return 2;
			return 3;
		}

	}

	private static final class ObjectMembersComparator implements Comparator<Object> {

		@Override
		public int compare(Object o1, Object o2) {
			if (!(o1 instanceof IVariableFacet) && !(o2 instanceof IVariableFacet))
				return 0;
			// Check by type first
			int facetOrderDiff = getPriority((IVariableFacet) o1) - getPriority((IVariableFacet) o2);
			// Same type, check alphabetically
			if (facetOrderDiff == 0) {
				if ((o1 instanceof IVariable) && (o2 instanceof IVariable))
					try {
						return ((IVariable) o1).getName().compareToIgnoreCase(((IVariable) o2).getName());
					} catch (DebugException e) {
						return 0;
					}
				// TODO - should be done better in future at variable level
				else if ((o1 instanceof Expression) && (o2 instanceof Expression)) {
					String o1name = ((Expression) o1).getLastName();
					int o1idx = o1name.lastIndexOf(':');
					if (o1idx != -1)
						o1name = o1name.substring(o1idx + 1);
					String o2name = ((Expression) o2).getLastName();
					int o2idx = o2name.lastIndexOf(':');
					if (o2idx != -1)
						o2name = o2name.substring(o2idx + 1);
					return (o1name.compareToIgnoreCase(o2name));
				} else
					return facetOrderDiff;
			} else
				return facetOrderDiff;
		}

		private int getPriority(IVariableFacet facet) {
			if (facet.hasFacet(MOD_STATIC))
				return 0;
			return 1;
		}

	}

	@SuppressWarnings("nls")
	public static final String[] SUPER_GLOBAL_NAMES = new String[] { "$GLOBALS", "$_SERVER", "$_GET", "$_POST",
			"$_FILES", "$_COOKIE", "$_SESSION", "$_REQUEST", "$_ENV" };

	public static final String THIS = "$this"; //$NON-NLS-1$
	public static final String CLASS_INDICATOR = "<class>"; //$NON-NLS-1$

	private VariablesUtil() {
		// Utility class - no public constructor
	}

	/**
	 * Checks if given variable name is a name of super global variable.
	 * 
	 * @param name
	 * @return <code>true</code> if given variable name is a name of super
	 *         global variable, <code>false</code> otherwise
	 */
	public static boolean isSuperGlobal(String name) {
		for (int i = 0; i < SUPER_GLOBAL_NAMES.length; i++)
			if (SUPER_GLOBAL_NAMES[i].equalsIgnoreCase(name))
				return true;
		return false;
	}

	/**
	 * Checks if given variable name is a name of "this" pseudo-variable.
	 * 
	 * @param name
	 * @return <code>true</code> if given variable name is a name of "this"
	 *         pseudo-variable, <code>false</code> otherwise
	 */
	public static boolean isThis(String name) {
		return THIS.equalsIgnoreCase(name);
	}

	/**
	 * Checks if given variable name is a name of virtual class indicator.
	 * 
	 * @param name
	 * @return <code>true</code> if given variable name is a name of virtual
	 *         class indicator, <code>false</code> otherwise
	 */
	public static boolean isClassIndicator(String name) {
		return CLASS_INDICATOR.equalsIgnoreCase(name);
	}

	/**
	 * Sorts provided top-level variables.
	 * 
	 * @param members
	 */
	public static void sortContextMembers(Object[] members) {
		if (members != null)
			Arrays.sort(members, new ContextMembersComparator());
	}

	/**
	 * Sorts provided top-level variables.
	 * 
	 * @param members
	 */
	public static void sortObjectMembers(Object[] members) {
		if (members != null)
			Arrays.sort(members, new ObjectMembersComparator());
	}

}
