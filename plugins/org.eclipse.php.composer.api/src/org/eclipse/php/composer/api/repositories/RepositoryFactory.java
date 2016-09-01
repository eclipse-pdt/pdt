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
package org.eclipse.php.composer.api.repositories;

import java.lang.reflect.Type;

public class RepositoryFactory {

	public static Repository create(String type) {
		if (type.equalsIgnoreCase("composer")) {
			return new ComposerRepository();
		} else if (type.equalsIgnoreCase("vcs")) {
			return new VcsRepository();
		} else if (type.equalsIgnoreCase("git")) {
			return new GitRepository();
		} else if (type.equalsIgnoreCase("svn")) {
			return new SubversionRepository();
		} else if (type.equalsIgnoreCase("hg")) {
			return new MercurialRepository();
		} else if (type.equalsIgnoreCase("package")) {
			return new PackageRepository();
		} else if (type.equalsIgnoreCase("pear")) {
			return new PearRepository();
		}
		
		return null;
	}
	
	public static Type getType(String type) {
		if (type.equalsIgnoreCase("composer")) {
			return ComposerRepository.class;
		} else if (type.equalsIgnoreCase("vcs")) {
			return VcsRepository.class;
		} else if (type.equalsIgnoreCase("git")) {
			return GitRepository.class;
		} else if (type.equalsIgnoreCase("svn")) {
			return SubversionRepository.class;
		} else if (type.equalsIgnoreCase("hg")) {
			return MercurialRepository.class;
		} else if (type.equalsIgnoreCase("package")) {
			return PackageRepository.class;
		} else if (type.equalsIgnoreCase("pear")) {
			return PearRepository.class;
		}
		
		return null;
	}
}
