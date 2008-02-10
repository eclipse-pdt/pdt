package org.eclipse.php.internal.core.mixin;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.php.internal.core.PHPLanguageToolkit;

public class PHPMixinModel {

	private static PHPMixinModel instance = new PHPMixinModel();
	private final MixinModel model;

	private PHPMixinModel() {
		model = new MixinModel(PHPLanguageToolkit.getDefault());
	}

	public static PHPMixinModel getInstance () {
		return instance;
	}

	public static MixinModel getRawInstance () {
		return getInstance().getRawModel();
	}

	public MixinModel getRawModel() {
		return model;
	}

	private IModelElement[] filterElements(IMixinElement[] elements, int kind) {
		List<IModelElement> filtered = new LinkedList<IModelElement>();
		for (IMixinElement element : elements) {
			Object[] objects = element.getAllObjects();
			for (Object obj : objects) {
				if (obj instanceof PHPMixinElementInfo) {
					PHPMixinElementInfo info = (PHPMixinElementInfo) obj;
					if (info.getKind() == kind) {
						filtered.add((IModelElement) info.getObject());
					}
				}
			}
		}
		return filtered.toArray(new IModelElement[filtered.size()]);
	}

	public IModelElement[] getMethod(String className, String methodName) {
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX + MixinModel.SEPARATOR + methodName);
		return filterElements(elements, PHPMixinElementInfo.K_METHOD);
	}

	public IModelElement[] getFunction(String functionName) {
		IMixinElement[] elements = model.find(MixinModel.SEPARATOR + functionName);
		return filterElements(elements, PHPMixinElementInfo.K_METHOD);
	}
}
