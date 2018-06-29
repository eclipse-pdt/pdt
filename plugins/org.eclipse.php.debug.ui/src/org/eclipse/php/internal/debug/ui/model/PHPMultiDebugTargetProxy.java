/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ModelDelta;
import org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler;
import org.eclipse.debug.internal.ui.viewers.update.DebugTargetProxy;

/**
 * PHP multi-debug target element proxy.
 * 
 * @author Bartlomiej Laczkowski, 2014
 */
public class PHPMultiDebugTargetProxy extends DebugTargetProxy {

	private IDebugTarget debugTarget;

	/**
	 * Creates new multi+debug target proxz.
	 * 
	 * @param debugTarget
	 */
	public PHPMultiDebugTargetProxy(IDebugTarget debugTarget) {
		super(debugTarget);
		this.debugTarget = debugTarget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugTargetProxy#
	 * createEventHandlers()
	 */
	@Override
	protected DebugEventHandler[] createEventHandlers() {
		return new DebugEventHandler[] { new PHPThreadEventHandler(this) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugTargetProxy#
	 * containsEvent (org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected boolean containsEvent(DebugEvent event) {
		Object source = event.getSource();
		if (source instanceof IDebugElement) {
			/*
			 * Get target from launch (launch has the wrapping multiple-target)
			 */
			ILaunch launch = ((IDebugElement) source).getLaunch();
			if (launch == null) {
				return false;
			}
			IDebugTarget debugTarget = launch.getDebugTarget();
			if (debugTarget != null) {
				return debugTarget.equals(this.debugTarget);
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugTargetProxy#
	 * getNextSuspendedThreadDelta(org.eclipse.debug.core.model.IThread, boolean)
	 */
	@Override
	protected ModelDelta getNextSuspendedThreadDelta(IThread currentThread, boolean reverse) {
		if (debugTarget != null) {
			try {
				IThread[] threads = debugTarget.getThreads();
				ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
				ILaunch launch = debugTarget.getLaunch();
				int launchIndex = indexOf(manager.getLaunches(), debugTarget.getLaunch());
				int targetIndex = indexOf(debugTarget.getLaunch().getChildren(), debugTarget);
				List<IThread> chosen = new ArrayList<>();
				// Select & unfold all suspended threads
				boolean takeNext = currentThread == null;
				int startIdx = reverse ? threads.length - 1 : 0;
				int endIdx = reverse ? -1 : threads.length;
				int increment = reverse ? -1 : 1;
				for (int i = startIdx; i != endIdx; i = i + increment) {
					IThread thread = threads[i];
					if (thread.isSuspended()) {
						IBreakpoint[] bps = thread.getBreakpoints();
						if (bps != null && bps.length > 0) {
							chosen.add(thread);
						} else if (thread.isSuspended()) {
							chosen.add(thread);
						}
					}
					takeNext = takeNext || thread.equals(currentThread);
				}
				if (!chosen.isEmpty()) {
					ModelDelta delta = new ModelDelta(manager, IModelDelta.NO_CHANGE);
					ModelDelta node = delta.addNode(launch, launchIndex, IModelDelta.NO_CHANGE,
							debugTarget.getLaunch().getChildren().length);
					node = node.addNode(debugTarget, targetIndex, IModelDelta.NO_CHANGE, threads.length);
					for (int i = 0; i < chosen.size(); i++) {
						IThread t = chosen.get(i);
						IStackFrame frame = t.getTopStackFrame();
						if (frame != null) {
							ModelDelta next = node.addNode(t, i, IModelDelta.NO_CHANGE | IModelDelta.EXPAND,
									t.getStackFrames().length);
							next = next.addNode(frame, 0, IModelDelta.NO_CHANGE | IModelDelta.SELECT, 0);
						}
					}
					return delta;
				}
			} catch (DebugException e) {
			}
		}
		return null;
	}

}
