package org.eclipse.php.internal.server.core.deploy;

import java.util.HashMap;
import java.util.Map;

/**
 * A deploy filter manage the resources names that will be filtered (ignored) in the deploy process. 
 * The initial state of this DeployFilter will filter .settings, .project, .projectOptions and .cache files. 
 */
public class DeployFilter {

	private static final String[] BASIC_FILTERS = new String[] { ".settings", ".project", ".cache", ".svn", "CVS" };
	private static DeployFilter instance;
	private Map filters;

	private static DeployFilter getInstance() {
		if (instance == null) {
			instance = new DeployFilter();
		}
		return instance;
	}

	private DeployFilter() {
		filters = new HashMap();
		for (int i = 0; i < BASIC_FILTERS.length; i++) {
			filters.put(BASIC_FILTERS[i], BASIC_FILTERS[i]);
		}
	}

	/**
	 * Returns a deploy filter map.
	 * Note that the returned map is a copy of the actualy map that the DeployFilter instance is holding.
	 * 
	 * @return A Map that contains string keys and values of the resources that should be ignored in the deploy process.
	 */
	public static Map getFilterMap() {
		DeployFilter filter = getInstance();
		Map map = new HashMap(filter.filters);
		return map;
	}

	/**
	 * Adds a file name to the deploy filter.
	 * 
	 * @param fileName
	 */
	public static void addFilter(String fileName) {
		getInstance().filters.put(fileName, fileName);
	}

	/**
	 * Removes a file name from the deploy filter.
	 * 
	 * @param fileName
	 */
	public static void removeFilter(String fileName) {
		getInstance().filters.remove(fileName);
	}

	/**
	 * Reset the filter to its default initial state.
	 * The initial state of this DeployFilter will filter .settings, .project, .projectOptions and .cache files. 
	 */
	public static void resetToDefault() {
		DeployFilter filter = getInstance();
		filter.filters.clear();
		for (int i = 0; i < BASIC_FILTERS.length; i++) {
			filter.filters.put(BASIC_FILTERS[i], BASIC_FILTERS[i]);
		}
	}
}
