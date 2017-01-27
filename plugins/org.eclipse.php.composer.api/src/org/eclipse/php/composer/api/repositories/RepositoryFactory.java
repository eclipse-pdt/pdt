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
package org.eclipse.php.composer.api.repositories;

import java.lang.reflect.Type;

public class RepositoryFactory {

	public static Repository create(String type) {
		if (type.equalsIgnoreCase("composer")) { //$NON-NLS-1$
			return new ComposerRepository();
		} else if (type.equalsIgnoreCase("vcs")) { //$NON-NLS-1$
			return new VcsRepository();
		} else if (type.equalsIgnoreCase("git")) { //$NON-NLS-1$
			return new GitRepository();
		} else if (type.equalsIgnoreCase("svn")) { //$NON-NLS-1$
			return new SubversionRepository();
		} else if (type.equalsIgnoreCase("hg")) { //$NON-NLS-1$
			return new MercurialRepository();
		} else if (type.equalsIgnoreCase("package")) { //$NON-NLS-1$
			return new PackageRepository();
		} else if (type.equalsIgnoreCase("pear")) { //$NON-NLS-1$
			return new PearRepository();
		}

		return null;
	}

	public static Type getType(String type) {
		if (type.equalsIgnoreCase("composer")) { //$NON-NLS-1$
			return ComposerRepository.class;
		} else if (type.equalsIgnoreCase("vcs")) { //$NON-NLS-1$
			return VcsRepository.class;
		} else if (type.equalsIgnoreCase("git")) { //$NON-NLS-1$
			return GitRepository.class;
		} else if (type.equalsIgnoreCase("svn")) { //$NON-NLS-1$
			return SubversionRepository.class;
		} else if (type.equalsIgnoreCase("hg")) { //$NON-NLS-1$
			return MercurialRepository.class;
		} else if (type.equalsIgnoreCase("package")) { //$NON-NLS-1$
			return PackageRepository.class;
		} else if (type.equalsIgnoreCase("pear")) { //$NON-NLS-1$
			return PearRepository.class;
		}

		return null;
	}
}
