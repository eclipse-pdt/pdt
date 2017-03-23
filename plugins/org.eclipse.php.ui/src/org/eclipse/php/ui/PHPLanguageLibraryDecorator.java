/*******************************************************************************
 * Copyright (c) 2016 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui;

import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.swt.graphics.Image;

/**
 * LabelDecorator that decorates PHP Language Library element with project PHP
 * Version.
 * 
 * @since 4.0
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class PHPLanguageLibraryDecorator implements ILabelDecorator, ILightweightLabelDecorator {

	/**
	 * Creates a decorator. The decorator creates an own image registry to cache
	 * images.
	 */
	public PHPLanguageLibraryDecorator() {
	}

	@Override
	public String decorateText(String text, Object element) {
		return text;
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		return image;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof BuildPathContainer && ((BuildPathContainer) element).getBuildpathEntry().getPath()
				.equals(LanguageModelInitializer.LANGUAGE_CONTAINER_PATH)) {
			PHPVersion version = ProjectOptions.getPHPVersion(((BuildPathContainer) element).getScriptProject());
			if (version != null) {
				decoration.addSuffix(
						new StringBuilder(" [PHP ").append(version.getAlias().substring(3)).append("]").toString()); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}

	}

}
