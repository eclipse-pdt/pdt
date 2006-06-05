/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.core.model;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.debugger.RemoteDebugger;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

public class BreakpointSet {
    
    private IProject fProject;
    private ArrayList fZips;
    private ArrayList fDirectories;
    private ArrayList fProjects;
    private boolean fIsPHPCGI;
    
    public BreakpointSet (IProject project, boolean isPHPCGI) {
        
        fProject = project;
        fIsPHPCGI = isPHPCGI;
        fZips = new ArrayList();
        fDirectories = new ArrayList();
        fProjects = new ArrayList();
        
        if (project == null);
        PHPProjectOptions options = PHPProjectOptions.forProject(project);
        if (options != null) {
            IIncludePathEntry[] entries = options.readRawIncludePath();

            if (entries != null) {
                for (int i = 0; i < entries.length; i++) {
                    if (entries[i].getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
                        IPath path = entries[i].getPath();
                        File file = new File(path.toString());
                        if (entries[i].getContentKind() == IIncludePathEntry.K_BINARY) {
                            fZips.add(RemoteDebugger.convertToSystemIndependentFileName(file.getAbsolutePath()));
                        } else {
                            fDirectories.add(RemoteDebugger.convertToSystemIndependentFileName(file.getAbsolutePath()));
                        }
                    } else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
                        IResource includeResource = entries[i].getResource();
                        if (includeResource instanceof IProject) {
                            fProjects.add(includeResource);
                        }
                    } else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
                        IPath path = entries[i].getPath();
                        String variableName = path.toString();
                        File file = getVriableFile(variableName);
                        if (file.isDirectory()) {
                            fDirectories.add(RemoteDebugger.convertToSystemIndependentFileName(file.getAbsolutePath()));
                        } else {
                            String fileName = file.getName();
                            if (fileName.toLowerCase().endsWith(".zip")) { //$NON-NLS-1$
                                fZips.add(RemoteDebugger.convertToSystemIndependentFileName(file.getAbsolutePath()));
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public boolean supportsBreakpoint(IBreakpoint breakpoint){
        
        // If no project, assume everything in the workspace
        if (fProject == null) return true;
        
        PHPLineBreakpoint bp = (PHPLineBreakpoint) breakpoint;
        IMarker marker = bp.getMarker();
        IResource resource = null;
        if (breakpoint instanceof PHPRunToLineBreakpoint) {
            return true;
        } else {
            resource = marker.getResource();
        }
        
        if (!fIsPHPCGI) {
            if (resource instanceof IWorkspaceRoot) {
                String includeType = marker.getAttribute(IPHPConstants.Include_Storage_type, "");
                String id = marker.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY, "");
                String filename = marker.getAttribute(IPHPConstants.Include_Storage, "");
                String base = id.substring(0,(id.length()-(filename.length()+1)));
                if (includeType.equals(IPHPConstants.Include_Storage_zip)) {
                    Object [] zips = fZips.toArray();
                    for (int i=0; i < zips.length; i++){
                        if (base.equals((String)zips[i]))return true;
                    }
                   return false;
                } else if (includeType.equals(IPHPConstants.Include_Storage_LFile)) {                    
                    Object [] dirs = fDirectories.toArray();
                    for (int i=0; i < dirs.length; i++){
                        if (base.equals((String)dirs[i]))return true;
                    }
                    return false;
                }
                return true;
            } else {
                IProject project = resource.getProject();
                if (fProject.equals(project))return true;
                return fProjects.contains(project);
            }
        } else {
            if (resource instanceof IWorkspaceRoot){
                return false;
            } else { 
                IProject project = resource.getProject();
                if (fProject.equals(project)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        
    }

    private File getVriableFile(String variableName) {
        int index = variableName.indexOf('/');
        String extention = ""; //$NON-NLS-1$
        if (index != -1) {
            if (index + 1 < variableName.length()) {
                extention = variableName.substring(index + 1);
            }
            variableName = variableName.substring(0, index);
        }
        IPath path = PHPProjectOptions.getIncludePathVariable(variableName);
        path = path.append(extention);
        return path.toFile();
    }
    
}
