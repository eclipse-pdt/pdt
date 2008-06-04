package org.eclipse.php.internal.core.callhierarchy;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ICallHierarchyFactory;
import org.eclipse.dltk.core.ICallProcessor;
import org.eclipse.dltk.core.ICalleeProcessor;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.search.IDLTKSearchScope;

public class PHPCallHierarchyFactory implements ICallHierarchyFactory {

	public ICallProcessor createCallProcessor() {
		return new PHPCallProcessor();
	}

	public ICalleeProcessor createCalleeProcessor(IMethod method, IProgressMonitor monitor, IDLTKSearchScope scope) {
		return new PHPCalleeProcessor(method, monitor, scope);
	}

}
