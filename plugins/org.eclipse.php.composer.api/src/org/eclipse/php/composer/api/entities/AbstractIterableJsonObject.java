/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.entities;

import java.util.Iterator;
import java.util.Map.Entry;

public abstract class AbstractIterableJsonObject<V> extends AbstractJsonObject<V>
		implements Iterable<Entry<String, V>> {

	public Iterator<Entry<String, V>> iterator() {
		return properties.entrySet().iterator();
	}
}
