/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.commands;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.ui.jobs.AddComposerJob;

/**
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class AddSupportHandler extends AbstractComposerHandler {

	@Override
	protected boolean shouldPerform(IContainer root) {
		return true;
	}

	@Override
	protected Job getJob(ComposerService composer) {
		return new AddComposerJob(composer);
	}

}