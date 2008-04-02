package org.eclipse.php.internal.core.mixin;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.mixin.IMixinParser;
import org.eclipse.dltk.core.mixin.IMixinRequestor;
import org.eclipse.php.core.PHPMixinBuildVisitorExtension;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class PHPMixinParser implements IMixinParser {

	public static final String CLASS_SUFFIX = "%"; //$NON-NLS-1$
	public static final String INCLUDE_SUFFIX = "#"; //$NON-NLS-1$

	private IMixinRequestor requestor;
	private IConfigurationElement[] extensions;

	public PHPMixinParser() {
		// Load parser extensions
		extensions = Platform.getExtensionRegistry().getConfigurationElementsFor(PHPCorePlugin.ID, "phpMixinBuildVisitors");
	}

	public void parserSourceModule(boolean signature, ISourceModule module) {

		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(module, null);
		PHPMixinBuildVisitor visitor = new PHPMixinBuildVisitor(moduleDeclaration, module, signature, requestor);
		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		for (IConfigurationElement extension : extensions) {
			try {
				PHPMixinBuildVisitorExtension visitorExtension = (PHPMixinBuildVisitorExtension) extension.createExecutableExtension("class");
				visitorExtension.setSourceModule(module);
				visitorExtension.setModuleAvailable(signature);
				visitorExtension.setRequestor(requestor);

				moduleDeclaration.traverse(visitorExtension);
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	public void setRequirestor(IMixinRequestor requestor) {
		this.requestor = requestor;
	}

}
