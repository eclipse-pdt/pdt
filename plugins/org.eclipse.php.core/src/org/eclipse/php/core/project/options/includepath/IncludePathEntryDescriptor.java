package org.eclipse.php.core.project.options.includepath;

import java.util.HashMap;

import org.eclipse.core.runtime.IPath;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.util.preferences.IXMLPreferencesStorable;

public class IncludePathEntryDescriptor implements IXMLPreferencesStorable {
	
	private String entryKind = "";
	private String contentKind = "";
	private String path = "";
	private String resourceName = "";
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
			this.resourceName = entry.resource.getName();
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
			entryKind = (String) entry.get("entryKind");
			contentKind = (String) entry.get("contentKind");
			path = (String) entry.get("path");
			resourceName = (String) entry.get("resourceName");
			isExported = (Boolean.valueOf( (String) entry.get("isExported")) ).booleanValue();
			createdReference = (Boolean.valueOf( (String) entry.get("referenceWasCreated")) ).booleanValue();
		}
	}

	public HashMap storeToMap() {
		HashMap map = new HashMap(6);
		map.put("entryKind", entryKind);
		map.put("contentKind", contentKind);
		map.put("path", path);
		map.put("resourceName", resourceName);
		map.put("isExported", new Boolean(isExported));
		map.put("referenceWasCreated", new Boolean(createdReference));
		
		HashMap entry = new HashMap(1);
		entry.put("javabridge_entry", map); //$NON-NLS-1$
	
		return entry;
	}
	
	
	

}
