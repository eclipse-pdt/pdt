package org.eclipse.php.internal.core.project.options.includepath;

import java.util.HashMap;

import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;

public class IncludePathEntryDescriptor implements IXMLPreferencesStorable {

	private String entryKind = ""; //$NON-NLS-1$
	private String contentKind = ""; //$NON-NLS-1$
	private String path = ""; //$NON-NLS-1$
	private String resourceName = ""; //$NON-NLS-1$
	private boolean isExported = false;
	private boolean createdReference = false;

	public IncludePathEntryDescriptor(){
	}

	public String getContentKind() {
		return contentKind;
	}

	public void setContentKind(String contentKind) {
		this.contentKind = contentKind;
	}

	public boolean isCreatedReference() {
		return createdReference;
	}

	public void setCreatedReference(boolean createdReference) {
		this.createdReference = createdReference;
	}

	public String getEntryKind() {
		return entryKind;
	}

	public void setEntryKind(String entryKind) {
		this.entryKind = entryKind;
	}

	public boolean isExported() {
		return isExported;
	}

	public void setExported(boolean isExported) {
		this.isExported = isExported;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public IncludePathEntryDescriptor(IncludePathEntry entry, IPath projectPath){
		this.entryKind = IncludePathEntry.entryKindToString(entry.entryKind);
		this.contentKind = IncludePathEntry.contentKindToString(entry.contentKind);
		//path = entry.path.toOSString();
		if (entry.resource != null) {
			this.resourceName = entry.resource.getFullPath().toString();
		}
		this.isExported = entry.isExported;
		this.createdReference = false;

		IPath entryPath = entry.path;
		if (entry.entryKind != IIncludePathEntry.IPE_VARIABLE && entry.entryKind != IIncludePathEntry.IPE_CONTAINER) {
			// translate to project relative from absolute (unless a device path)
			if (projectPath != null && projectPath.isPrefixOf(entryPath)) {
				if (entryPath.segment(0).equals(projectPath.segment(0))) {
					entryPath = entryPath.removeFirstSegments(1);
					entryPath = entryPath.makeRelative();
				} else {
					entryPath = entryPath.makeAbsolute();
				}
			}
		}
		this.path = String.valueOf(entryPath);
	}

	public void restoreFromMap(HashMap map) {
		HashMap entry = (HashMap) map.get("javabridge_entry"); //$NON-NLS-1$
		if (entry != null) {
			entryKind = (String) entry.get("entryKind"); //$NON-NLS-1$
			contentKind = (String) entry.get("contentKind"); //$NON-NLS-1$
			path = (String) entry.get("path"); //$NON-NLS-1$
			resourceName = (String) entry.get("resourceName"); //$NON-NLS-1$
			isExported = Boolean.valueOf( (String) entry.get("isExported")).booleanValue(); //$NON-NLS-1$
			createdReference = Boolean.valueOf( (String) entry.get("referenceWasCreated")).booleanValue(); //$NON-NLS-1$
		}
	}

	public HashMap storeToMap() {
		HashMap map = new HashMap(6);
		map.put("entryKind", entryKind); //$NON-NLS-1$
		map.put("contentKind", contentKind); //$NON-NLS-1$
		map.put("path", path); //$NON-NLS-1$
		map.put("resourceName", resourceName); //$NON-NLS-1$
		map.put("isExported", new Boolean(isExported)); //$NON-NLS-1$
		map.put("referenceWasCreated", new Boolean(createdReference)); //$NON-NLS-1$

		HashMap entry = new HashMap(1);
		entry.put("javabridge_entry", map); //$NON-NLS-1$

		return entry;
	}




}
