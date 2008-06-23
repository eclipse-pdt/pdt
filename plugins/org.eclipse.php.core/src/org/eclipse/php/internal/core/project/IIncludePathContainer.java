/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.project;

import org.eclipse.core.runtime.IPath;

/** 
 * Interface of a include path container.
 * A include path container provides a way to indirectly reference a set of include path entries through
 * a include path entry of kind <code>CPE_CONTAINER</code>. Typically, a include path container can
 * be used to describe a complex library composed of multiple JARs or projects, considering also 
 * that containers can map to different set of entries on each project, in other words, several 
 * projects can reference the same generic container path, but have each of them actually bound 
 * to a different container object.
 * <p>
 * The set of entries associated with a include path container may contain any of the following:
 * <ul>
 * <li> library entries (<code>CPE_LIBRARY</code>) </li>
 * <li> project entries (<code>CPE_PROJECT</code>) </li>
 * </ul>
 * In particular, a include path container can neither reference further include path containers or include path variables.
 * <p>
 * IncludePath container values are persisted locally to the workspace, but are not preserved from a 
 * session to another. It is thus highly recommended to register a <code>IncludePathContainerInitializer</code> 
 * for each referenced container (through the extension point "org.eclipse.jdt.core.IncludePathContainerInitializer").
 * <p>
 * @see IIncludePathEntry
 * @since 2.0
 */

public interface IIncludePathContainer {

	/**
	 * Kind for a container mapping to an application library
	 */
	int K_APPLICATION = 1;

	/**
	 * Kind for a container mapping to a system library
	 */
	int K_SYSTEM = 2;

	/**
	 * Kind for a container mapping to a default system library, implicitly contributed by the runtime
	 */
	int K_DEFAULT_SYSTEM = 3;

	IIncludePathEntry[] getIncludePathEntries();

	/**
	 * Answers a readable description of this container
	 * 
	 * @return String - a string description of the container
	 */
	String getDescription();

	/**
	 * Answers the kind of this container. Can be either:
	 * <ul>
	 * <li><code>K_APPLICATION</code> if this container maps to an application library</li>
	 * <li><code>K_SYSTEM</code> if this container maps to a system library</li>
	 * <li><code>K_DEFAULT_SYSTEM</code> if this container maps to a default system library (library
	 * 	implicitly contributed by the runtime).</li>
	 * </ul>
	 * Typically, system containers should be placed first on a build path.
	 * @return the kind of this container
	 */
	int getKind();

	/**
	 * Answers the container path identifying this container.
	 * A container path is formed by a first ID segment followed with extra segments, which 
	 * can be used as additional hints for resolving to this container.
	 * <p>
	 * The container ID is also used to identify a<code>IncludePathContainerInitializer</code>
	 * registered on the extension point "org.eclipse.jdt.core.include pathContainerInitializer", which can
	 * be invoked if needing to resolve the container before it is explicitly set.
	 * <p>
	 * @return IPath - the container path that is associated with this container
	 */
	IPath getPath();
}
