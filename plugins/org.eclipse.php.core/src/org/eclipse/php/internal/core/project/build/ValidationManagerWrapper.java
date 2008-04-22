package org.eclipse.php.internal.core.project.build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jem.util.logger.LogEntry;
import org.eclipse.jem.util.logger.proxy.Logger;
import org.eclipse.wst.validation.internal.*;
import org.eclipse.wst.validation.internal.operations.EnabledIncrementalValidatorsOperation;
import org.eclipse.wst.validation.internal.operations.EnabledValidatorsOperation;
import org.eclipse.wst.validation.internal.operations.ValidationBuilder;
import org.eclipse.wst.validation.internal.operations.ValidatorManager;
import org.eclipse.wst.validation.internal.plugin.ValidationPlugin;

public class ValidationManagerWrapper extends ValidationBuilder {
	public IProject[] build(final int kind, final Map args, IProgressMonitor monitor) {
		if (kind == IncrementalProjectBuilder.AUTO_BUILD) {
			final IResourceDelta delta = getDelta(getProject());
			RSEFolderReporter visitor = new RSEFolderReporter();
			try {
				delta.accept(visitor);
			} catch (CoreException e) {
				org.eclipse.php.internal.core.Logger.logException(e);
			}
			if (visitor.isNewJobNeeded()) {
				WorkspaceJob j = new WorkspaceJob("Validating") {

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						innerBuild(kind, args, monitor, delta);
						return Status.OK_STATUS;
					}

				};
				j.setPriority(Job.LONG);
				j.setUser(false);
				j.schedule();
				return null;
			}

		}

		return super.build(kind, args, monitor);
	}

	/**
	 * This function is the exact build function taken from the super with one difference - it gets the delta as parameter 
	 * and not using getDelta(project) function.
	 * The reason for it is when running on separated process you can't be sure the delta is still valid.
	 */
	private IProject[] innerBuild(int kind, Map parameters, IProgressMonitor monitor, IResourceDelta delta) {
		long start = System.currentTimeMillis();
		int executionMap = 0x0;
		Logger logger = ValidationPlugin.getPlugin().getMsgLogger();
		IProject project = getProject();
		IProject[] referenced = getAllReferencedProjects(project, null);
		try {
			if (ValidatorManager.getManager().isSuspended(project)) {
				// Do not perform validation on this project
				executionMap |= 0x1;
				return referenced;
			}
			ProjectConfiguration prjp = ConfigurationManager.getManager().getProjectConfiguration(project);
			boolean doFullBuild = (kind == FULL_BUILD);
			//			boolean doAutoBuild = ((delta != null) && (kind == AUTO_BUILD));
			//			boolean doIncrementalBuild = ((delta != null) && (kind == INCREMENTAL_BUILD));
			//			if ((doFullBuild || doIncrementalBuild) && !prjp.isBuildValidate()) {
			//				// Is a build validation about to be invoked? If so, does the
			//				// user want build validation to run?
			//				executionMap |= 0x2;
			//				return referenced;
			//			}
			// It is possible for kind to == AUTO_BUILD while delta is null
			// (saw this
			// when creating a project by copying another project.)
			// However, a "Rebuild Project" will invoke this builder with
			// kind==FULL_BUILD
			// and a null delta, and validation should run in that case.
			if (!doFullBuild && delta == null) {
				if (isReferencedProjectInDelta(referenced)) {
					performFullBuildForReferencedProjectChanged(monitor, prjp);
				} else {
					String[] msgParms = new String[] { project.getName() };
					monitor.subTask(ResourceHandler.getExternalizedMessage(ResourceConstants.VBF_STATUS_NULL_DELTA, msgParms));
					// A null delta means that a full build must be performed,
					// but this builder was invoked with an incremental or
					// automatic
					// build kind. Return without doing anything so that the
					// user
					// doesn't have to wait forever.
					executionMap |= 0x4;
				}
				return referenced;
			}
			if (doFullBuild) {
				performFullBuild(monitor, prjp);
			} else {
				//				if (doAutoBuild && !prjp.isAutoValidate()) {
				//					executionMap |= 0x8;
				//					return referenced;
				//				}
				if (delta.getAffectedChildren().length == 0) {
					if (isReferencedProjectInDelta(referenced))
						performFullBuildForReferencedProjectChanged(monitor, prjp);
					else
						executionMap |= 0x10;
					return referenced;
				}
				EnabledIncrementalValidatorsOperation operation = new EnabledIncrementalValidatorsOperation(project, delta, true);
				operation.run(monitor);
			}
			return referenced;
		} catch (InvocationTargetException exc) {
			logInvocationTargetException(logger, exc);
			executionMap |= 0x20;
			return referenced;
		} catch (Exception exc) {
			logBuildError(logger, exc);
			executionMap |= 0x40;
			return referenced;
		} finally {
			referencedProjects = null;
			// The builder's time needs to be FINE because the builder is
			// called often.
			if (logger.isLoggingLevel(Level.FINE)) {
				logBuilderTimeEntry(start, executionMap, logger, delta);
			}
		}
	}

	
	//rest of the code is copied from the super in order to support the innerBuild function
	
	
	private void logInvocationTargetException(Logger logger, InvocationTargetException exc) {
		if (logger.isLoggingLevel(Level.SEVERE)) {
			LogEntry entry = ValidationPlugin.getLogEntry();
			entry.setSourceID("ValidationBuilder::build"); //$NON-NLS-1$
			entry.setTargetException(exc);
			logger.write(Level.SEVERE, entry);
			if (exc.getTargetException() != null) {
				entry.setTargetException(exc);
				logger.write(Level.SEVERE, entry);
			}
		}
	}

	private void logBuildError(Logger logger, Throwable exc) {
		if (logger.isLoggingLevel(Level.SEVERE)) {
			if (!(exc instanceof OperationCanceledException)) {
				LogEntry entry = ValidationPlugin.getLogEntry();
				entry.setSourceID("ValidationBuilder.build(int, Map, IProgressMonitor)"); //$NON-NLS-1$
				entry.setTargetException(exc);
				logger.write(Level.SEVERE, entry);
			}
		}
	}

	private void logBuilderTimeEntry(long start, int executionMap, Logger logger, IResourceDelta delta) {
		TimeEntry entry = ValidationPlugin.getTimeEntry();
		entry.setSourceID("ValidationBuilder.build(int, Map, IProgressMonitor)"); //$NON-NLS-1$
		entry.setProjectName(getProject().getName()); //$NON-NLS-1$  //$NON-NLS-2$
		entry.setExecutionMap(executionMap);
		entry.setElapsedTime(System.currentTimeMillis() - start);
		if (delta == null) {
			entry.setDetails("delta == null"); //$NON-NLS-1$
		}
		entry.setToolName("ValidationBuilder"); //$NON-NLS-1$
		logger.write(Level.FINE, entry);
	}

	private IProject[] getAllReferencedProjects(IProject project, Set visitedProjects) {
		if (visitedProjects == null)
			visitedProjects = new HashSet();
		else if (visitedProjects.contains(project))
			return getReferencedProjects();
		else
			visitedProjects.add(project);
		if (referencedProjects == null)
			referencedProjects = new ArrayList();
		try {
			if (project.isAccessible()) {
				IProject[] refProjArray = project.getReferencedProjects();
				collectReferecedProject(refProjArray);
				for (int i = 0; i < refProjArray.length; i++) {
					IProject refProject = refProjArray[i];
					getAllReferencedProjects(refProject, visitedProjects);
				}
			}
			return getReferencedProjects();
		} catch (CoreException core) {
			return null;
		}
	}

	private void collectReferecedProject(IProject[] refProjArray) {
		for (int i = 0; i < refProjArray.length; i++) {
			IProject project = refProjArray[i];
			if (!referencedProjects.contains(project))
				referencedProjects.add(project);
		}
	}

	private IProject[] getReferencedProjects() {
		IProject[] refProjArray = new IProject[referencedProjects.size()];
		for (int i = 0; i < referencedProjects.size(); i++) {
			refProjArray[i] = (IProject) referencedProjects.get(i);
		}
		return refProjArray;
	}

	private boolean isReferencedProjectInDelta(IProject[] referenced) {
		IProject p = null;
		for (int i = 0; i < referenced.length; i++) {
			p = referenced[i];
			IResourceDelta delta = getDelta(p);
			if (delta != null && delta.getAffectedChildren().length > 0)
				return true;
		}
		return false;
	}

	private void performFullBuildForReferencedProjectChanged(IProgressMonitor monitor, ProjectConfiguration prjp) throws InvocationTargetException {
		performFullBuild(monitor, prjp, true);
	}

	private void performFullBuild(IProgressMonitor monitor, ProjectConfiguration prjp) throws InvocationTargetException {
		performFullBuild(monitor, prjp, false);
	}
	
	private void performFullBuild(IProgressMonitor monitor, ProjectConfiguration prjp, boolean onlyDependentValidators) throws InvocationTargetException {
		ValidatorMetaData[] enabledValidators = prjp.getEnabledFullBuildValidators(true, onlyDependentValidators);
		if ((enabledValidators != null) && (enabledValidators.length > 0)) {
			Set enabledValidatorsSet = InternalValidatorManager.wrapInSet(enabledValidators);
			EnabledValidatorsOperation op = new EnabledValidatorsOperation(getProject(), enabledValidatorsSet, true){}; // we added the curly brackets to bypass the protected C'tor
			op.run(monitor);
		}
	}
}
