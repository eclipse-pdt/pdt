package org.eclipse.php.internal.ui.explorer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;

public class NamespaceNode extends SourceType {
	private IType[] namespaces;

	public NamespaceNode(IScriptProject project, String name, IType[] namespaces) {
		super((ModelElement) project, name);
		this.namespaces = namespaces;
	}

	public IModelElement[] getChildren(IProgressMonitor monitor) throws ModelException {
		List<IModelElement> children = new LinkedList<IModelElement>();
		for (IType namespace : namespaces) {
			children.addAll(Arrays.asList(namespace.getChildren()));
		}
		return children.toArray(new IModelElement[children.size()]);
	}

	public int getFlags() throws ModelException {
		return Modifiers.AccNameSpace;
	}

	public boolean exists() {
		return true;
	}
}