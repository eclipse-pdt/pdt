package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.IModelElement;

/**
 * This is a model element filter that filters out model elements from adding them to code assist list  
 * @author michael
 */
interface IElementFilter {
	
	/**
	 * @param element Model element
	 * @return <code>true</code> if given element must be filtered out from code assist, otherwise <code>false</code>
	 */
	public boolean filter(IModelElement element);
}