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
package org.eclipse.php.composer.core;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

public class ComposerPluginImages
{
    public static final IPath ICONS_PATH = new Path("/ico/full"); //$NON-NLS-1$

    private static final String T_WIZBAN = "wizban"; //$NON-NLS-1$

    public static final ImageDescriptor DESC_WIZBAN_ADD_DEPENDENCY = create(
            T_WIZBAN, "packagist.png");//$NON-NLS-1$

    private static ImageDescriptor create(String prefix, String name)
    {
        return create(prefix, name, true);
    }

    private static ImageDescriptor create(String prefix, String name,
            boolean useMissingImageDescriptor)
    {
        IPath path = ICONS_PATH.append(prefix).append(name);

        return createImageDescriptor(ComposerPlugin.getDefault().getBundle(), path,
                useMissingImageDescriptor);
    }

    public static ImageDescriptor createImageDescriptor(Bundle bundle,
            IPath path, boolean useMissingImageDescriptor)
    {
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
