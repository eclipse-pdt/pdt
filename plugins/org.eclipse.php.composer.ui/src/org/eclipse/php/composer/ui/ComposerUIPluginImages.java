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
package org.eclipse.php.composer.ui;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

public class ComposerUIPluginImages {
	public static final IPath ICONS_PATH = new Path("/icons/full");

	private static final String T_OBJ16 = "obj16";
	private static final String T_WIZBAN = "wizban";

	public static final ImageDescriptor ADD_DEPENDENCY = create(T_WIZBAN, "packagist.png");
	public static final ImageDescriptor CREATE_PROJECT = create(T_WIZBAN, "newpack_wiz.png");
	public static final ImageDescriptor CREATE_PROJECT_FROM_PACKAGE = create(T_WIZBAN, "newsrcfldr_wiz.gif");
	public static final ImageDescriptor IMPORT_PROJECT = create(T_WIZBAN, "packrefact_wiz.gif");

	public static final ImageDescriptor PERSON = create(T_OBJ16, "person.png");
	public static final ImageDescriptor PAGE = create(T_OBJ16, "page.gif");
	public static final ImageDescriptor PACKAGE = create(T_OBJ16, "package.gif");
	public static final ImageDescriptor NAMESPACE = create(T_OBJ16, "namespace_obj.png");
	public static final ImageDescriptor PACKAGE_FOLDER = create(T_OBJ16, "packagefolder_obj.gif");
	public static final ImageDescriptor COMPOSER = create(T_OBJ16, "composer.png");
	public static final ImageDescriptor PHP = create(T_OBJ16, "php.png");
	public static final ImageDescriptor BROWSER = create(T_OBJ16, "browser.png");
	public static final ImageDescriptor INSTALL = create(T_OBJ16, "install.png");
	public static final ImageDescriptor INSTALL_DEV = create(T_OBJ16, "install_dev.png");
	public static final ImageDescriptor UPDATE = create(T_OBJ16, "update.png");
	public static final ImageDescriptor UPDATE_DEV = create(T_OBJ16, "update_dev.png");
	public static final ImageDescriptor SELFUPDATE = create(T_OBJ16, "selfupdate.png");
	public static final ImageDescriptor EVENT = create(T_OBJ16, "event3.png");
	public static final ImageDescriptor SCRIPT = create(T_OBJ16, "script2.png");
	public static final ImageDescriptor STAR = create(T_OBJ16, "star.png");
	public static final ImageDescriptor DOWNLOAD = create(T_OBJ16, "download.png");

	public static final ImageDescriptor CLEAR = create(T_OBJ16, "clear.png");
	public static final ImageDescriptor CLEAR_DISABLED = create(T_OBJ16, "clear_disabled.png");

	public static final ImageDescriptor REPO_GENERIC = create(T_OBJ16, "repo_generic.gif");
	public static final ImageDescriptor REPO_GIT = create(T_OBJ16, "repo_git.png");
	public static final ImageDescriptor REPO_SVN = create(T_OBJ16, "repo_svn.png");
	public static final ImageDescriptor REPO_MERCURIAL = create(T_OBJ16, "repo_mercurial.png");
	public static final ImageDescriptor REPO_PEAR = create(T_OBJ16, "repo_pear.png");
	public static final ImageDescriptor REPO_PACKAGE = PACKAGE;
	public static final ImageDescriptor REPO_COMPOSER = COMPOSER;

	public static final ImageDescriptor BUILDPATH_INCLUDE = create(T_OBJ16, "buildpath_include.gif");
	public static final ImageDescriptor BUILDPATH_EXCLUDE = create(T_OBJ16, "buildpath_exclude.gif");

	private static ImageDescriptor create(String prefix, String name) {
		return create(prefix, name, true);
	}

	private static ImageDescriptor create(String prefix, String name, boolean useMissingImageDescriptor) {
		IPath path = ICONS_PATH.append(prefix).append(name);

		return createImageDescriptor(ComposerUIPlugin.getDefault().getBundle(), path, useMissingImageDescriptor);
	}

	public static ImageDescriptor createImageDescriptor(Bundle bundle, IPath path, boolean useMissingImageDescriptor) {
		URL url = FileLocator.find(bundle, path, null);
		if (url != null) {
			return ImageDescriptor.createFromURL(url);
		}
		if (useMissingImageDescriptor) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
		return null;
	}

}
