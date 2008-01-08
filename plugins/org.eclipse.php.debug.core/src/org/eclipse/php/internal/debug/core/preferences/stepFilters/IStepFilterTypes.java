package org.eclipse.php.internal.debug.core.preferences.stepFilters;

public interface IStepFilterTypes {
	/**
	 * A Filter Type representation of a filter by pattern
	 */
	public final static String PATH_PATTERN = "*php_pattern";
	
	/**
	 * A Filter Type representation of a PHP Project filter
	 */
	public final static String PHP_PROJECT = "*php_project";
	
	/**
	 * A Filter Type representation of a PHP Project File filter
	 */
	public final static String PHP_PROJECT_FILE = "*php_project_file";
	
	/**
	 * A Filter Type representation of a PHP Project Folder filter
	 */
	public final static String PHP_PROJECT_FOLDER = "*php_project_folder";
	
	/**
	 * A Filter Type representation of a PHP Include Path Variable Entry
	 */
	public final static String PHP_INCLUDE_PATH_VAR = "*php_include_path_var";
	
	/**
	 * A Filter Type representation of a PHP Include Path Library Entry
	 */
	public final static String PHP_INCLUDE_PATH_LIBRARY = "*php_include_path_library";
	
	/**
	 * A Filter Type representation of a PHP Include Path File Entry
	 */
	public final static String PHP_INCLUDE_PATH_FILE = "*php_include_path_file";
	
	/**
	 * A Filter Type representation of a PHP Include Path Folder Entry
	 */
	public final static String PHP_INCLUDE_PATH_FOLDER = "*php_include_path_folder";
}
