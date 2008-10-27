package org.eclipse.php.internal.core.includepath;

import org.eclipse.dltk.core.IBuildpathEntry;

/**
 * Include path entry
 */
public class IncludePath {

	private boolean isBuildpath;
	private Object entry;

	public IncludePath(Object entry) {
		isBuildpath = (entry instanceof IBuildpathEntry);
		this.entry = entry;
	}

	/**
	 * @return Include path entry. It's either {@link IBuildpathEntry} or {@link IResource} depending on kind 
	 */
	public Object getEntry() {
		return entry;
	}

	/**
	 * Returns whether this include path entry represents {@link IBuildpathEntry}
	 */
	public boolean isBuildpath() {
		return isBuildpath;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
		result = prime * result + (isBuildpath ? 1231 : 1237);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IncludePath other = (IncludePath) obj;
		if (entry == null) {
			if (other.entry != null) {
				return false;
			}
		} else if (!entry.equals(other.entry)) {
			return false;
		}
		if (isBuildpath != other.isBuildpath) {
			return false;
		}
		return true;
	}

	public String toString() {
		return new StringBuilder("Include Path [").append(entry.toString()).append(']').toString();
	}
}
