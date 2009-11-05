package org.eclipse.php.internal.debug.ui.breakpoint.provider;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ui.IEditorInput;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IBreakpointProvider;

public interface IPHPBreakpointProvider extends IBreakpointProvider,
		IExecutableExtension {

	IBreakpoint createBreakpoint(IEditorInput input, IResource resource,
			int lineNumber, Map<String, String> attributes)
			throws CoreException;
}
