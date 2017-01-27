/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.api.objects;

/**
 * Represents a support section of a composer package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#support
 * @author Thomas Gossmann <gos.si>
 */
public class Support extends JsonObject implements Cloneable {

	/**
	 * Returns the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return getAsString("email"); //$NON-NLS-1$
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		set("email", email); //$NON-NLS-1$
	}

	/**
	 * Returns the issues.
	 * 
	 * @return the issues
	 */
	public String getIssues() {
		return getAsString("issues"); //$NON-NLS-1$
	}

	/**
	 * Sets the issues.
	 * 
	 * @param issues
	 *            the issues to set
	 */
	public void setIssues(String issues) {
		set("issues", issues); //$NON-NLS-1$
	}

	/**
	 * Returns the forum.
	 * 
	 * @return the forum
	 */
	public String getForum() {
		return getAsString("forum"); //$NON-NLS-1$
	}

	/**
	 * Sets the forum.
	 * 
	 * @param forum
	 *            the forum to set
	 */
	public void setForum(String forum) {
		set("forum", forum); //$NON-NLS-1$
	}

	/**
	 * Returns the wiki.
	 * 
	 * @return the wiki
	 */
	public String getWiki() {
		return getAsString("wiki"); //$NON-NLS-1$
	}

	/**
	 * Sets the wiki.
	 * 
	 * @param wiki
	 *            the wiki to set
	 */
	public void setWiki(String wiki) {
		set("wiki", wiki); //$NON-NLS-1$
	}

	/**
	 * Returns the irc.
	 * 
	 * @return the irc
	 */
	public String getIrc() {
		return getAsString("irc"); //$NON-NLS-1$
	}

	/**
	 * Sets the irc.
	 * 
	 * @param irc
	 *            the irc to set
	 */
	public void setIrc(String irc) {
		set("irc", irc); //$NON-NLS-1$
	}

	/**
	 * Returns the source.
	 * 
	 * @return the source
	 */
	public String getSource() {
		return getAsString("source"); //$NON-NLS-1$
	}

	/**
	 * Sets the source.
	 * 
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		set("source", source); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Support clone() {
		Support clone = new Support();
		cloneProperties(clone);
		return clone;
	}
}
