/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.entities;

import java.util.Iterator;
import java.util.Map.Entry;

public abstract class AbstractIterableJsonObject<V> extends AbstractJsonObject<V>
		implements Iterable<Entry<String, V>> {

	@Override
	public Iterator<Entry<String, V>> iterator() {
		return properties.entrySet().iterator();
	}
}
