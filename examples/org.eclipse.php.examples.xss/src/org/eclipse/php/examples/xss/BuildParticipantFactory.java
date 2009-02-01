package org.eclipse.php.examples.xss;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.core.builder.IBuildParticipantFactory;

public class BuildParticipantFactory implements IBuildParticipantFactory {

	public BuildParticipantFactory() {
	}

	public IBuildParticipant createBuildParticipant(IScriptProject project) throws CoreException {
		return new XSSProtectionParticipant();
	}

}
