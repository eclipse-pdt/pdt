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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ModelDelta;
import org.eclipse.debug.internal.ui.viewers.provisional.AbstractModelProxy;
import org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler;

/**
 * PHP thread event handler.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPThreadEventHandler extends DebugEventHandler {

	/**
	 * Queue of suspended threads to choose from when needing to select a thread
	 * when another is resumed. Threads are added in the order they suspend.
	 */
	private final Set<IThread> fThreadQueue = new LinkedHashSet<>();

	/**
	 * Map of previous TOS per thread
	 */
	private final Map<IThread, IStackFrame> fLastTopFrame = new HashMap<>();

	/**
	 * Creates an event handler for a threads in the given viewer.
	 * 
	 * @param proxy
	 */
	public PHPThreadEventHandler(AbstractModelProxy proxy) {
		super(proxy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#dispose()
	 */
	@Override
	public synchronized void dispose() {
		fLastTopFrame.clear();
		fThreadQueue.clear();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handlesEvent (org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected boolean handlesEvent(DebugEvent event) {
		return event.getSource() instanceof IThread;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handleSuspend (org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected void handleSuspend(DebugEvent event) {
		IThread thread = (IThread) event.getSource();
		if (!event.isEvaluation()) {
			int extras = IModelDelta.NO_CHANGE;
			switch (event.getDetail()) {
			case DebugEvent.BREAKPOINT:
				// on breakpoint also position thread to be top element
				extras = IModelDelta.EXPAND | IModelDelta.REVEAL;
				break;
			case DebugEvent.CLIENT_REQUEST:
				extras = IModelDelta.EXPAND | IModelDelta.REVEAL;
				break;
			default:
				break;
			}
			fireDeltaUpdatingSelectedFrame(thread, IModelDelta.NO_CHANGE | extras, event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handleResume (org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected void handleResume(DebugEvent event) {
		IThread thread = (IThread) event.getSource();
		fireDeltaAndClearTopFrame(thread, IModelDelta.STATE | IModelDelta.CONTENT | IModelDelta.SELECT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handleCreate (org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected void handleCreate(DebugEvent event) {
		fireDeltaAndClearTopFrame((IThread) event.getSource(), IModelDelta.ADDED | IModelDelta.STATE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handleTerminate(org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected void handleTerminate(DebugEvent event) {
		IThread thread = (IThread) event.getSource();
		IDebugTarget target = thread.getLaunch().getDebugTarget();
		// ignore thread termination if target is terminated/disconnected
		if (!(target.isTerminated() || target.isDisconnected())) {
			fireDeltaAndClearTopFrame(thread, IModelDelta.REMOVED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handleChange (org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected void handleChange(DebugEvent event) {
		if (event.getDetail() == DebugEvent.STATE) {
			fireDeltaUpdatingThread((IThread) event.getSource(), IModelDelta.STATE);
		} else {
			fireDeltaUpdatingThread((IThread) event.getSource(), IModelDelta.CONTENT);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handleOther (org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected void handleOther(DebugEvent event) {
		// nothing to do right now
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handleLateSuspend(org.eclipse.debug.core.DebugEvent,
	 * org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected void handleLateSuspend(DebugEvent suspend, DebugEvent resume) {
		IThread thread = queueSuspendedThread(suspend);
		if (suspend.isEvaluation() && suspend.getDetail() == DebugEvent.EVALUATION_IMPLICIT) {
			// late implicit evaluation - update thread and frame
			ModelDelta delta = buildRootDelta();
			ModelDelta node = addPathToThread(delta, thread);
			node = node.addNode(thread, IModelDelta.STATE);
			try {
				IStackFrame frame = thread.getTopStackFrame();
				if (frame != null) {
					node.addNode(frame, IModelDelta.STATE);
					fireDelta(delta);
				}
			} catch (DebugException e) {
			}
		} else {
			fireDeltaUpdatingSelectedFrame(thread, IModelDelta.STATE | IModelDelta.EXPAND, suspend);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.update.DebugEventHandler#
	 * handleSuspendTimeout(org.eclipse.debug.core.DebugEvent)
	 */
	@Override
	protected void handleSuspendTimeout(DebugEvent event) {
		IThread thread = removeSuspendedThread(event);
		// don't collapse thread when waiting for long step or evaluation to
		// complete
		fireDeltaUpdatingThread(thread, IModelDelta.STATE);
	}

	/**
	 * @return
	 */
	protected ModelDelta buildRootDelta() {
		return new ModelDelta(getLaunchManager(), IModelDelta.NO_CHANGE);
	}

	/**
	 * Returns the launch manager.
	 * 
	 * @return the launch manager
	 */
	protected ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}

	/**
	 * Adds nodes into the delta up to but not including the given thread.
	 * 
	 * @param delta
	 *            root delta for the view (includes viewer input)
	 * @param thread
	 *            thread for which path is requested
	 * @return
	 */
	protected ModelDelta addPathToThread(ModelDelta delta, IThread thread) {
		ILaunch launch = thread.getLaunch();
		Object[] children = launch.getChildren();
		delta = delta.addNode(launch, indexOf(getLaunchManager().getLaunches(), launch), IModelDelta.CONTENT,
				children.length);
		IDebugTarget debugTarget = launch.getDebugTarget();
		int numThreads = -1;
		try {
			numThreads = debugTarget.getThreads().length;
		} catch (DebugException e) {
		}
		return delta.addNode(debugTarget, indexOf(children, debugTarget), IModelDelta.NO_CHANGE, numThreads);
	}

	/**
	 * Returns the index of the given thread, relative to its parent in the view.
	 * 
	 * @param thread
	 *            thread
	 * @return index of the thread, relative to its parent
	 */
	protected int indexOf(IThread thread) {
		try {
			return indexOf(thread.getLaunch().getDebugTarget().getThreads(), thread);
		} catch (DebugException e) {
		}
		return -1;
	}

	/**
	 * Returns the index of the given frame, relative to its parent in the view.
	 * 
	 * @param frame
	 *            stack frame
	 * @return index of the frame, relative to its thread
	 */
	protected int indexOf(IStackFrame frame) {
		try {
			return indexOf(frame.getThread().getStackFrames(), frame);
		} catch (DebugException e) {
			return -1;
		}
	}

	/**
	 * Returns the number of children the given thread has in the view.
	 * 
	 * @param thread
	 *            thread
	 * @return number of children
	 */
	protected int childCount(IThread thread) {
		try {
			return thread.getStackFrames().length;
		} catch (DebugException e) {
		}
		return -1;
	}

	/**
	 * Returns the number of children the given frame has in the view.
	 * 
	 * @param frame
	 *            frame
	 * @return child count
	 */
	protected int childCount(IStackFrame frame) {
		return 0;
	}

	protected synchronized IThread queueSuspendedThread(DebugEvent event) {
		IThread thread = (IThread) event.getSource();
		if (!isDisposed()) {
			fThreadQueue.add(thread);
		}
		return thread;
	}

	protected synchronized IThread removeSuspendedThread(DebugEvent event) {
		IThread thread = (IThread) event.getSource();
		fThreadQueue.remove(thread);
		return thread;
	}

	private void fireDeltaAndClearTopFrame(IThread thread, int flags) {
		ModelDelta delta = buildRootDelta();
		ModelDelta node = addPathToThread(delta, thread);
		node.addNode(thread, flags);
		synchronized (this) {
			fLastTopFrame.remove(thread);
		}
		fireDelta(delta);
	}

	private void fireDeltaUpdatingSelectedFrame(IThread thread, int flags, DebugEvent event) {
		ModelDelta delta = buildRootDelta();
		ModelDelta node = addPathToThread(delta, thread);
		IStackFrame prev = null;
		synchronized (this) {
			prev = fLastTopFrame.get(thread);
		}
		IStackFrame frame = null;
		try {
			Object frameToSelect = event.getData();
			if (frameToSelect == null || !(frameToSelect instanceof IStackFrame)) {
				frame = thread.getTopStackFrame();
			} else {
				frame = (IStackFrame) frameToSelect;
			}
		} catch (DebugException e) {
		}
		int threadIndex = indexOf(thread);
		int childCount = childCount(thread);
		if (isEqual(frame, prev)) {
			if (frame == null) {
				if (thread.isSuspended()) {
					// no frames, but suspended - update & select
					node = node.addNode(thread, threadIndex, flags | IModelDelta.STATE | IModelDelta.SELECT,
							childCount);
				}
			} else {
				node = node.addNode(thread, threadIndex, flags, childCount);
			}
		} else {
			if (event.getDetail() == DebugEvent.STEP_END) {
				if (prev == null) {
					flags = flags | IModelDelta.EXPAND;
				} else if (frame == null) {
					return;
				}
			}
			node = node.addNode(thread, threadIndex, flags | IModelDelta.CONTENT, childCount);
		}
		if (frame != null) {
			node.addNode(frame, indexOf(frame), IModelDelta.STATE | IModelDelta.SELECT, childCount(frame));
		}
		synchronized (this) {
			if (!isDisposed()) {
				fLastTopFrame.put(thread, frame);
			}
		}
		fireDelta(delta);
	}

	private void fireDeltaUpdatingThread(IThread thread, int flags) {
		ModelDelta delta = buildRootDelta();
		ModelDelta node = addPathToThread(delta, thread);
		node = node.addNode(thread, flags);
		fireDelta(delta);
	}

	private boolean isEqual(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null) {
			return false;
		}
		return o1.equals(o2);
	}

}
