package org.eclipse.php.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.tptp.platform.analysis.codereview.java.CodeReviewProvider;
import org.eclipse.tptp.platform.analysis.core.AnalysisConstants;
import org.eclipse.tptp.platform.analysis.core.AnalysisUtil;
import org.eclipse.tptp.platform.analysis.core.CoreMessages;
import org.eclipse.tptp.platform.analysis.core.element.AbstractAnalysisElement;
import org.eclipse.tptp.platform.analysis.core.element.AnalysisParameter;
import org.eclipse.tptp.platform.analysis.core.history.AnalysisHistory;
import org.eclipse.tptp.platform.analysis.core.history.AnalysisHistoryElement;
import org.eclipse.tptp.platform.analysis.core.history.AnalysisHistoryFactory;
import org.eclipse.tptp.platform.analysis.core.logging.Log;
import org.eclipse.tptp.platform.analysis.core.manager.AnalysisProviderManager;
import org.eclipse.tptp.platform.analysis.core.rule.AbstractAnalysisRule;
import org.eclipse.tptp.platform.analysis.core.ui.AnalysisUIPlugin;
import org.eclipse.tptp.platform.analysis.core.ui.UIMessages;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;

public class AnalysisLaunchConfigurationDelegate implements ILaunchConfigurationDelegate {
	private static ILaunchConfiguration lastLaunch = null;
	private final String rulesFileName;
	private final String outputFile;

	public AnalysisLaunchConfigurationDelegate(String rulesFileName, String outputFile) {
		super();
		this.rulesFileName = rulesFileName;
		this.outputFile = outputFile;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(final ILaunchConfiguration inputConf, String mode, ILaunch launch, IProgressMonitor parentProgress) throws CoreException {

		if (AnalysisHistoryFactory.instance().isBusy()) {
			return;
		} else {
			// We don't really care about this progress bar since it is just for the launch.
			parentProgress.beginTask(AnalysisConstants.BLANK, 1);
			parentProgress.worked(1);
			parentProgress.done();

//			Display.getDefault().asyncExec(new Runnable() {
//				public void run() {
//					try {
//						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AnalysisConstants.ID_RESULTS_FRAME_VIEW);
//					} catch (PartInitException e) {
//						Log.severe(UIMessages.RESULT_VIEW_CREATE_ERROR_, e);
//					}
//				}
//			});

			ILaunchConfiguration conf = inputConf;
			if (conf == null) {
				conf = lastLaunch;
			}

			try {
				AnalysisHistory history = AnalysisHistoryFactory.instance().createAnalysisHistory(conf.getName());
				buildSelectionList(conf, history);

				if (history.getSelectAnalysisElements().size() > 0) {
					// Get the manager and if it is enabled begin processing it
					AnalysisProviderManager manager = AnalysisUtil.getProviderManager();

					loadFromFile();
					
					// Run analysis for the right kind of project selection
					int scope = conf.getAttribute(AnalysisConstants.CONFIG_PROP_INPUT, AnalysisConstants.ANALYSIS_SCOPE_WORKSPACE);

					// Create a list of all projects in the workspace
					if (scope == AnalysisConstants.ANALYSIS_SCOPE_WORKSPACE) {
						analyzeWorkspace(manager, history);
					} else if (scope == AnalysisConstants.ANALYSIS_SCOPE_WORKINGSET) {
						analyzeWorkingset(conf, manager, history);
					} else if (scope == AnalysisConstants.ANALYSIS_SCOPE_PROJECTS) {
						analyzeProject(conf, manager, history);
					} else if (scope == AnalysisConstants.ANALYSIS_SCOPE_SINGLE) {
						analyzeSingle(conf, manager, history);
					}

					final String historyId = history.getHistoryId();

					final List selectAnalysisElements = history.getSelectAnalysisElements();
					final CodeReviewProvider provider = (CodeReviewProvider) selectAnalysisElements.get(0);

					ExportResultRuleData exporter = new ExportResultRuleData();

					try {
						PrintStream output = System.out;
						PrintStream outputXML = new PrintStream(new File(outputFile));
						exporter.export(output, new NullProgressMonitor(), history, provider);
						exporter.exportSuites(outputXML);
						output.close();
						outputXML.close();

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

				} else {
//					Display.getDefault().asyncExec(new Runnable() {
//						public void run() {
//							MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), UIMessages.messagebox_no_selection_title, UIMessages.messagebox_no_selection_message);
//						}
//					});
				}
			} catch (CoreException e) {
				Log.severe(CoreMessages.provider_build_error_, e);
			}
			lastLaunch = conf;
		}
	}

	private void loadFromFile() {
		if (rulesFileName == null) {
			Log.info("rules file name doesn't supplied - all rules are recommendations");
		}
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rulesFileName)));
			String id = br.readLine();
			while (id != null) {

				// Load external data values from the imported data
				int index = id.indexOf(AnalysisConstants.EXTERNAL_LABEL);
				if (index != -1) {
					int commaIndex = id.indexOf(AnalysisConstants.COMMA);
					String elementId = id.substring(0, index);

					// Skip the id prefix and the last "."
					String key = id.substring(index + AnalysisConstants.EXTERNAL_LABEL.length() + 1, commaIndex);

					// Get the data after the "," separator
					String value = id.substring(commaIndex + 1);
					AbstractAnalysisElement element = AnalysisUtil.getAnalysisElement(elementId);
					element.getExternalDataMap().put(key, value);
				}

				// Determine if this is a rule or a variable
				index = id.indexOf(AnalysisConstants.VARIABLE_LABEL);
				if (index != -1) {
					int commaIndex = id.indexOf(AnalysisConstants.COMMA);

					String ruleId = id.substring(0, index);
					String variable = id.substring(index + AnalysisConstants.VARIABLE_LABEL.length(), commaIndex);
					String value = id.substring(commaIndex + 1);

					AbstractAnalysisRule rule = (AbstractAnalysisRule) AnalysisUtil.getAnalysisElement(ruleId);
					if (rule != null) {
						rule.getParameter(variable).setValue(value);
					}
				}
				id = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			Log.severe(UIMessages.XML_EXPORT_ERROR_, e);
		}
	}

	private void analyzeWorkspace(AnalysisProviderManager manager, AnalysisHistory history) {
		List list = new ArrayList(10);
		IProject[] projectArray = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int iCtr = 0; iCtr < projectArray.length; iCtr++) {
			if (projectArray[iCtr].isOpen()) {
				list.add(projectArray[iCtr]);
			}
		}

		manager.analyze(history, list);
	}

	private void analyzeWorkingset(ILaunchConfiguration conf, AnalysisProviderManager manager, AnalysisHistory history) {
		List list = new ArrayList(10);
		try {
			List projects = conf.getAttribute(AnalysisConstants.CONFIG_PROP_WORKINGSET_LIST, new ArrayList(10));
			IWorkingSetManager wsMgr = AnalysisUIPlugin.getDefault().getWorkbench().getWorkingSetManager();
			for (Iterator it2 = projects.iterator(); it2.hasNext();) {
				IWorkingSet ws = wsMgr.getWorkingSet((String) it2.next());
				if (ws != null) {
					IAdaptable[] adapt = ws.getElements();
					for (int iCtr = 0; iCtr < adapt.length; iCtr++) {
						if (adapt[iCtr] instanceof IResource) {
							list.add(adapt[iCtr]);
						}
					}
				}
			}

			manager.analyze(history, list);
		} catch (CoreException e) {
			Log.severe(AnalysisConstants.BLANK, e);
		}
	}

	private void analyzeProject(ILaunchConfiguration conf, AnalysisProviderManager manager, AnalysisHistory history) {
		List list = new ArrayList(10);
		try {
			List projects = conf.getAttribute(AnalysisConstants.CONFIG_PROP_PROJECT_LIST, new ArrayList(10));
			for (Iterator it2 = projects.iterator(); it2.hasNext();) {
				list.add(ResourcesPlugin.getWorkspace().getRoot().getProject((String) it2.next()));
			}

			manager.synchronousAnalyze(history, list, new NullProgressMonitor());
		} catch (CoreException e) {
			Log.severe(AnalysisConstants.BLANK, e);
		}
	}

	private void analyzeSingle(ILaunchConfiguration conf, AnalysisProviderManager manager, AnalysisHistory history) {
		List list = new ArrayList(10);

		try {
			List resource = conf.getAttribute(AnalysisConstants.CONFIG_PROP_RESOURCE, new ArrayList(10));
			for (Iterator it2 = resource.iterator(); it2.hasNext();) {
				list.add(it2.next());
			}

			manager.analyze(history, list);
		} catch (CoreException e) {
			Log.severe(AnalysisConstants.BLANK, e);
		}
	}

	private void buildSelectionList(ILaunchConfiguration conf, AnalysisHistory history) {
		buildChildren(conf, history, AnalysisUtil.getProviderManager(), null);
	}

	private void buildChildren(ILaunchConfiguration conf, AnalysisHistory history, AbstractAnalysisElement parent, AnalysisHistoryElement parentNode) {
		List elements = parent.getOwnedElements();
		for (Iterator it = elements.iterator(); it.hasNext();) {
			AbstractAnalysisElement element = (AbstractAnalysisElement) it.next();

			try {
				// If the configuration says this element is enabled, add it to the selection list
				if (conf.getAttribute(element.getId(), false)) {
					AnalysisHistoryElement node = new AnalysisHistoryElement(history, parentNode, element);

					// For rule handle any rule variables
					if (element.getElementType() == AbstractAnalysisElement.RULE_ELEMENT_TYPE) {
						AbstractAnalysisRule rule = (AbstractAnalysisRule) element;
						if (rule.getParameterCount() > 0) {
							List list = rule.getParameterList();
							for (Iterator it2 = list.iterator(); it2.hasNext();) {
								AnalysisParameter rv = (AnalysisParameter) it2.next();
								StringBuffer sb = new StringBuffer();
								sb.append(element.getId()).append(AnalysisConstants.VARIABLE_LABEL).append(rv.getName());
								rv.setValue(conf.getAttribute(sb.toString(), rv.getValue()));
							}

							node.setAnalysisVariables(list);
						}
					}

					// For enabled elements, add their enabled children too
					if (element.getOwnedElements() != null) {
						buildChildren(conf, history, element, node);
					}
				}
			} catch (CoreException e) {
				Log.severe(AnalysisConstants.BLANK, e);
			}
		}
	}

	public static ILaunchConfiguration getLastLaunch() {
		return lastLaunch;
	}

}
