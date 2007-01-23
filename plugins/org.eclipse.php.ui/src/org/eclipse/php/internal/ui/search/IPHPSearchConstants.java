/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search;

/**
 * <p>
 * This interface defines the constants used by the search engine.
 * </p>
 * <p>
 * This interface declares constants only; it is not intended to be implemented.
 * </p>
 * @see org.eclipse.jdt.core.search.SearchEngine
 */
public interface IPHPSearchConstants {

	/**
	 * The searched element is a Class.
	 */
	int CLASS = 0;

	/**
	 * The searched element is a function.
	 */
	int FUNCTION = 1;

	/**
	 * The searched element is a constant.
	 */
	int CONSTANT = 2;

	/* Nature of match */

	/**
	 * The search result is a declaration.
	 * Can be used in conjunction with any of the nature of searched elements
	 * so as to better narrow down the search.
	 */
	int DECLARATIONS= 0;

	/**
	 * The search result is a type that implements an interface. 
	 * Used in conjunction with either TYPE or CLASS or INTERFACE, it will
	 * respectively search for any type implementing/extending an interface, or
	 * rather exclusively search for classes implementing an interface, or interfaces 
	 * extending an interface.
	 */
	int IMPLEMENTORS= 1;

	/**
	 * The search result is a reference.
	 * Can be used in conjunction with any of the nature of searched elements
	 * so as to better narrow down the search.
	 * References can contain implementers since they are more generic kind
	 * of matches.
	 */
	int REFERENCES= 2;

	/**
	 * The search result is a declaration, a reference, or an implementer 
	 * of an interface.
	 * Can be used in conjunction with any of the nature of searched elements
	 * so as to better narrow down the search.
	 */
	int ALL_OCCURRENCES= 3;

	/**
	 * When searching for field matches, it will exclusively find read accesses, as
	 * opposed to write accesses. Note that some expressions are considered both
	 * as field read/write accesses: for example, x++; x+= 1;
	 * 
	 * @since 2.0
	 */
	int READ_ACCESSES = 4;
	
	/**
	 * When searching for field matches, it will exclusively find write accesses, as
	 * opposed to read accesses. Note that some expressions are considered both
	 * as field read/write accesses: for example,  x++; x+= 1;
	 * 
	 * @since 2.0
	 */
	int WRITE_ACCESSES = 5;

	/**
	 * Ignore declaring type while searching result.
	 * Can be used in conjunction with any of the nature of match.
	 * @since 3.1
	 */
	int IGNORE_DECLARING_TYPE = 0x10;

	/**
	 * Ignore return type while searching result.
	 * Can be used in conjunction with any of the nature of match.
	 * Note that:
	 * <ul>
	 * 	<li>for fields search, pattern will ignore field type</li>
	 * 	<li>this flag will have no effect for types search</li>
	 *	</ul>
	 * @since 3.1
	 */
	int IGNORE_RETURN_TYPE = 0x20;

}
