package org.eclipse.php.composer.ui.job;

import java.io.IOException;

import org.apache.commons.exec.ExecuteException;
import org.eclipse.core.resources.IProject;
import org.eclipse.php.composer.core.launch.ScriptLauncher;
import org.eclipse.php.composer.core.launch.execution.ExecutionResponseListener;

abstract public class AbstractInstallJob extends ComposerJob {

	public AbstractInstallJob(IProject project, String name) {
		super(project, name);
	}

	public AbstractInstallJob(String name) {
		super(name);
	}

	@Override
	protected void launch(ScriptLauncher launcher) throws ExecuteException, IOException, InterruptedException {
		launcher.addResponseListener(new ExecutionResponseListener() {

			@Override
			public void executionStarted() {
			}

			@Override
			public void executionMessage(String message) {
			}

			@Override
			public void executionFinished(String response, int exitValue) {
				new AfterInstallJob(getProject()).schedule();
			}

			@Override
			public void executionFailed(String response, Exception exception) {

			}

			@Override
			public void executionError(String message) {

			}

			@Override
			public void executionAboutToStart() {
			}
		});
	}

}
