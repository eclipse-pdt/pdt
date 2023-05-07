/*******************************************************************************
 * Copyright (c) 2023 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.List;

/**
 * This interfeaces is applied to all declarations that can consume PHP8
 * attributes
 *
 */
public interface IAttributed {
	public List<Attribute> getAttributes();

	public void setAttributes(List<Attribute> attributes);
}
