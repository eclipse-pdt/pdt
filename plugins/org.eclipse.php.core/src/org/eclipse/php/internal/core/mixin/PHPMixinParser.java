package org.eclipse.php.internal.core.mixin;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.mixin.IMixinParser;
import org.eclipse.dltk.core.mixin.IMixinRequestor;

public class PHPMixinParser implements IMixinParser {

	public static final String CLASS_SUFFIX = "%"; //$NON-NLS-1$

	private IMixinRequestor requestor;

	public void parserSourceModule(boolean signature, ISourceModule module) {

		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(module, null);
		PHPMixinBuildVisitor visitor = new PHPMixinBuildVisitor(moduleDeclaration, module, signature, requestor);
		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRequirestor(IMixinRequestor requestor) {
		this.requestor = requestor;
	}

}
