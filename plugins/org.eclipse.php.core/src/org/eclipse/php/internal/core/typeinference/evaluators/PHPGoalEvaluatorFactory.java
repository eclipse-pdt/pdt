/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Michele Locati <michele@locati.it>
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.goals.FactoryMethodReturnTypeGoal;

public class PHPGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	private static final String GOAL_EVALUATOR_FACTORIES_EXT = "org.eclipse.php.core.goalEvaluatorFactories"; //$NON-NLS-1$
	private static final String FACTORY_METHOD_GOAL_EVALUATOR_FACTORIES_EXT = "org.eclipse.php.core.factoryMethodGoalEvaluatorFactories"; //$NON-NLS-1$
	private final static FactoryInfo[] factoryInfos;
	private final static IGoalEvaluatorFactory[] factoryMethodFactories;

	private static class FactoryInfo {
		int priority;
		IGoalEvaluatorFactory factory;

		public FactoryInfo(int priority, IGoalEvaluatorFactory factory) {
			super();
			this.priority = priority;
			this.factory = factory;
		}
	}

	public PHPGoalEvaluatorFactory() {
	}

	private static int getPriority(IConfigurationElement element) {
		String priority = element.getAttribute("priority"); //$NON-NLS-1$
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

	private static FactoryInfo[] readFactoryInfos(final String extensionPoint) {
		List<FactoryInfo> factories = new ArrayList<>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPoint);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			try {
				int priority = getPriority(element);
				IGoalEvaluatorFactory factory = (IGoalEvaluatorFactory) element.createExecutableExtension("class"); //$NON-NLS-1$
				if (factory != null) {
					factories.add(new FactoryInfo(priority, factory));
				}
			} catch (Exception e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
		FactoryInfo[] result = factories.toArray(new FactoryInfo[factories.size()]);
		Arrays.sort(result, new Comparator<FactoryInfo>() {
			@Override
			public int compare(FactoryInfo info1, FactoryInfo info2) {
				return Integer.valueOf(info2.priority).compareTo(info1.priority);
			}
		});
		return result;
	}

	static {
		factoryInfos = readFactoryInfos(GOAL_EVALUATOR_FACTORIES_EXT);
		FactoryInfo[] factoryMethodFactoryInfos = readFactoryInfos(FACTORY_METHOD_GOAL_EVALUATOR_FACTORIES_EXT);
		factoryMethodFactories = new IGoalEvaluatorFactory[factoryMethodFactoryInfos.length];
		for (int i = 0; i < factoryMethodFactoryInfos.length; i++) {
			factoryMethodFactories[i] = factoryMethodFactoryInfos[i].factory;
		}
	}

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		if (factoryInfos == null) {
			return null;
		}
		if (goal instanceof FactoryMethodReturnTypeGoal) {
			if (factoryMethodFactories == null || factoryMethodFactories.length == 0) {
				return null;
			}
			return new FactoryMethodGoalEvaluator(goal, factoryMethodFactories);
		} else {
			for (int i = 0; i < factoryInfos.length; i++) {
				GoalEvaluator evaluator = factoryInfos[i].factory.createEvaluator(goal);
				if (evaluator != null) {
					return evaluator;
				}
			}
		}
		return null;
	}

}
