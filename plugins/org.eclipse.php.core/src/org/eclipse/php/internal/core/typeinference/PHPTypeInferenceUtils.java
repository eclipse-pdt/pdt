package org.eclipse.php.internal.core.typeinference;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.ModelClassType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;

public class PHPTypeInferenceUtils {

	public static IEvaluatedType combineMultiType(Collection<IEvaluatedType> evaluatedTypes) {
		MultiTypeType multiTypeType = new MultiTypeType();
		for (IEvaluatedType type : evaluatedTypes) {
			if (type == null) {
				type = PHPSimpleTypes.NULL;
			}
			multiTypeType.addType(type);
		}
		return multiTypeType;
	}

	private static Collection<IEvaluatedType> resolveAmbiguousTypes(Collection<IEvaluatedType> evaluatedTypes) {
		List<IEvaluatedType> resolved = new LinkedList<IEvaluatedType>();
		for (IEvaluatedType type : evaluatedTypes) {
			if (type instanceof AmbiguousType) {
				AmbiguousType ambType = (AmbiguousType) type;
				resolved.addAll(resolveAmbiguousTypes(Arrays.asList(ambType.getPossibleTypes())));
			} else {
				resolved.add(type);
			}
		}
		return resolved;
	}

	public static IEvaluatedType combineTypes(Collection<IEvaluatedType> evaluatedTypes) {
		Set<IEvaluatedType> types = new HashSet<IEvaluatedType>(resolveAmbiguousTypes(evaluatedTypes));
		if (types.contains(null)) {
			types.remove(null);
			types.add(PHPSimpleTypes.NULL);
		}
		if (types.size() == 0) {
			return UnknownType.INSTANCE;
		}
		if (types.size() == 1) {
			return types.iterator().next();
		}
		return new AmbiguousType(types.toArray(new IEvaluatedType[types.size()]));
	}
	
	/**
	 * Converts IEvaluatedType to IModelElement, if found. This method filters elements using file network dependencies.
	 * @param evaluatedType Evaluated type
	 * @return model elements
	 */
	public static IModelElement[] getModelElements(IEvaluatedType type, ISourceModuleContext context) {
		return getModelElements(type, context, true);
	}

	/**
	 * Converts IEvaluatedType to IModelElement, if found
	 * @param evaluatedType Evaluated type
	 * @param filter Whether to filter result using file network dependencies.
	 * @return model elements
	 */
	public static IModelElement[] getModelElements(IEvaluatedType type, ISourceModuleContext context, boolean filter) {
		IModelElement[] elements = null;

		if (type instanceof ModelClassType) {
			return new IModelElement[] { ((ModelClassType)type).getTypeDeclaration() };
		}
		if (type instanceof PHPClassType) {
			elements = PHPMixinModel.getInstance().getClass(((PHPClassType)type).getTypeName());
		}
		else if (type instanceof AmbiguousType) {
			List<IModelElement> tmpList = new LinkedList<IModelElement>();
			IEvaluatedType[] possibleTypes = ((AmbiguousType)type).getPossibleTypes();
			for (IEvaluatedType possibleType : possibleTypes) {
				IModelElement[] tmpArray = getModelElements(possibleType, context, false);
				if (tmpArray != null) {
					tmpList.addAll(Arrays.asList(tmpArray));
				}
			}
			elements = tmpList.toArray(new IModelElement[tmpList.size()]);
		}

		// Filter model elements using file network:
		if (filter && elements != null && elements.length > 0) {
			ISourceModule sourceModule = context.getSourceModule();
			ReferenceTree referenceTree = FileNetworkUtility.buildReferencedFilesTree(sourceModule, null);
			List<IModelElement> filteredElements = new LinkedList<IModelElement>();
			for (IModelElement element : elements) {
				if (referenceTree.find(((ModelElement)element).getSourceModule())) {
					filteredElements.add(element);
				}
			}
			elements = filteredElements.toArray(new IModelElement[filteredElements.size()]);
		}

		return elements;
	}
}
