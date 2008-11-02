package org.eclipse.php.internal.ui.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;

/**
 * This Node is shown in the first level of the PHP Functions view. It aggregates all of the PHP constants   
 * @author Eden K., 2008
 *
 */
public class ConstantNode {
	
	private static final String CONSTANT_NODE_LABEL = "constants";

	public ConstantNode(){		
	}
	
	List<IModelElement> modules = new ArrayList<IModelElement>();
	
	public Object[] getChildren(){
		List<IField> children = new ArrayList<IField>();
		// only the constants should be shown under this node
		for (IModelElement element : modules) {
			if(element instanceof IField){
				children.add((IField) element);
			}
		}
		return children.toArray(new Object[children.size()]);
	}


	public void addSourceModuleChildren(IModelElement[] externalSourceModuleChildren) {
		modules.addAll(Arrays.asList(externalSourceModuleChildren));
		
	}
	
	public String getName(){
		return CONSTANT_NODE_LABEL;
	}
}
