package org.eclipse.php.internal.core.phpModel;

public interface IPHPModelExtension {

	/**
	 * Returns path to the PHP file that contains language model
	 * @return file path
	 */
	public String getFile();

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
