package org.eclipse.php.internal.core.phpModel;

public interface IPHPModelExtension {

	/**
	 * Returns path to the directory that contains language model PHP files
	 * @return file path
	 */
	public String getDirectory();

	/**
	 * Returns whether this extension should be enabled
	 * @return whether this extension is enabled
	 */
	public boolean isEnabled();

	/**
	 * Returns PHP version that this language model extension is applicable
	 * @return PHP version
	 */
	public String getPHPVersion();
}
