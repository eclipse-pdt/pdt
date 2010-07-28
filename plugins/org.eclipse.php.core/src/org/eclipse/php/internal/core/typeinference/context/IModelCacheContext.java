package org.eclipse.php.internal.core.typeinference.context;

import org.eclipse.php.internal.core.typeinference.IModelAccessCache;

/**
 * This context contains model access cache that can be reused between type
 * inferencer goals and evaluators.
 * 
 * @author Michael
 * 
 */
public interface IModelCacheContext {

	/**
	 * @return cache instance if available, otherwise may return
	 *         <code>null</code>.
	 */
	IModelAccessCache getCache();

	void setCache(IModelAccessCache cache);
}
