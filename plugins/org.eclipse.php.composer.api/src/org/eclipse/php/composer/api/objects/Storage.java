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
package org.eclipse.php.composer.api.objects;


/**
 * Represents a <code>dist</code> rsp <code>source</code> entity 
 * in a package
 * 
 * @see http://getcomposer.org/doc/05-repositories.md#package-2
 * @author Thomas Gossmann <gos.si>
 *
 */
public abstract class Storage extends JsonObject {

	/**
	 * Returns the <code>url</code> property.
	 * 
	 * @return the <code>url</code> value
	 */
	public String getUrl() {
		return getAsString("url");
	}
	
	/**
	 * Sets the <code>url</code> property.
	 * 
	 * @param url the new <code>url</code> value
	 */
	public void setUrl(String url) {
		set("url", url);
	}
	
	/**
	 * Returns the <code>type</code> property.
	 * 
	 * @return the <code>type</code> value
	 */
	public String getType() {
		return getAsString("type");
	}
	
	/**
	 * Sets the <code>type</code> property.
	 * 
	 * @param type the new <code>type</code> value
	 */
	public void setType(String type) {
		set("type", type);
	}
	
	/**
	 * Returns the <code>reference</code> property.
	 * 
	 * @return the <code>reference</code> value
	 */
	public String getReference() {
		return getAsString("reference");
	}
	
	/**
	 * Sets the <code>reference</code> property.
	 * 
	 * @param type the new <code>reference</code> value
	 */
	public void setReference(String reference) {
		set("reference", reference);
	}
}
