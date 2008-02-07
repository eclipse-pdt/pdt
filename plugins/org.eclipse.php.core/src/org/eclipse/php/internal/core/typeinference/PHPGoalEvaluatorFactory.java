package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.Logger;

public class PHPGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	private static final String GOAL_EVALUATOR_FACTORIES_EXT = "org.eclipse.php.core.goalEvaluatorFactories";
	private final static FactoryInfo[] factoryInfos;

	private static class FactoryInfo {
		int priority;
		IGoalEvaluatorFactory factory;

		public FactoryInfo(int priority, IGoalEvaluatorFactory factory) {
			super();
			this.priority = priority;
			this.factory = factory;
		}
	}

	private static int getPriority(IConfigurationElement element) {
		String priority = element.getAttribute("priority");
		if (priority == null) {
			return 0;
		}
		try {
			int parseInt = Integer.parseInt(priority);
			return parseInt;
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	static {
		List<FactoryInfo> factories = new ArrayList<FactoryInfo>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(GOAL_EVALUATOR_FACTORIES_EXT);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			try {
				int priority = getPriority(element);
				IGoalEvaluatorFactory factory = (IGoalEvaluatorFactory) element.createExecutableExtension("class");
				if (factory != null) {
					factories.add(new FactoryInfo(priority, factory));
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		factoryInfos = factories.toArray(new FactoryInfo[factories.size()]);
		Arrays.sort(factoryInfos, new Comparator<FactoryInfo>() {

			public int compare(FactoryInfo info1, FactoryInfo info2) {
				return info2.priority - info1.priority;
			}

		});
	}

	public GoalEvaluator createEvaluator(IGoal goal) {
		if (factoryInfos == null) {
			return null;
		}
		for (int i = 0; i < factoryInfos.length; i++) {
			GoalEvaluator evaluator = factoryInfos[i].factory.createEvaluator(goal);
			if (evaluator != null) {
				return evaluator;
			}
		}
		return null;
	}

}
