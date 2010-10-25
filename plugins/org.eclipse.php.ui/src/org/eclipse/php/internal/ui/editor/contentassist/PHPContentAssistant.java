/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.eclipse.dltk.ui.text.completion.IScriptContentAssistExtension;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wst.sse.ui.internal.contentassist.StructuredContentAssistant;

public class PHPContentAssistant extends StructuredContentAssistant implements
		IScriptContentAssistExtension {

	private static final int DEFAULT_AUTO_ACTIVATION_DELAY = 500;
	private int fAutoActivationDelay = DEFAULT_AUTO_ACTIVATION_DELAY;

	private ITextViewer fViewer;

	public PHPContentAssistant() {
	}

	protected AutoAssistListener createAutoAssistListener() {
		return new AutoAssistListener2();
	}

	public void install(ITextViewer textViewer) {
		super.install(textViewer);
		fViewer = textViewer;
	}

	public void uninstall() {
		super.uninstall();
		fViewer = null;
	}

	public void setAutoActivationDelay(int delay) {
		fAutoActivationDelay = delay;
		super.setAutoActivationDelay(delay);
	}

	class AutoAssistListener2 extends AutoAssistListener implements
			KeyListener, Runnable, VerifyKeyListener {

		private Thread fThread;
		private boolean fIsReset = false;
		private Object fMutex = new Object();
		private int fShowStyle;

		private final static int SHOW_PROPOSALS = 1;
		private final static int SHOW_CONTEXT_INFO = 2;

		protected void start(int showStyle) {
			fShowStyle = showStyle;
			fThread = new Thread(this, "AutoAssist Delay"); //$NON-NLS-1$
			fThread.start();
		}

		public void run() {
			try {
				while (true) {
					synchronized (fMutex) {
						if (fAutoActivationDelay != 0) {
							fMutex.wait(fAutoActivationDelay);
						}
						if (fIsReset) {
							fIsReset = false;
							continue;
						}
					}
					showAssist(fShowStyle);
					break;
				}
			} catch (InterruptedException e) {
			}
			fThread = null;
		}

		protected void reset(int showStyle) {
			synchronized (fMutex) {
				fShowStyle = showStyle;
				fIsReset = true;
				fMutex.notifyAll();
			}
		}

		protected void stop() {
			Thread threadToStop = fThread;
			if (threadToStop != null && threadToStop.isAlive()) {
				threadToStop.interrupt();
			}
		}

		public void keyPressed(KeyEvent e) {
			// Only act on typed characters and ignore modifier-only events
			if (e.character == 0 && (e.keyCode & SWT.KEYCODE_BIT) == 0) {
				return;
			}
			if (!(e.character >= 0x20 && e.character != 0x7F && e.character != 0xFF)) {
				return;
			}
			try {
				int pos = ((Point) evaluatePrivateMemberMethod(
						"fContentAssistSubjectControlAdapter", "getSelectedRange", new Class[0], new Object[0])).x; //$NON-NLS-1$ //$NON-NLS-2$
				IDocument document = fViewer.getDocument();
				String type = TextUtilities.getContentType(document,
						getDocumentPartitioning(), pos, true);
				boolean activated = true;
				if (type != PHPPartitionTypes.PHP_DEFAULT) {
					if (fViewer instanceof PHPStructuredTextViewer) {
						PHPStructuredTextViewer phpViewer = (PHPStructuredTextViewer) fViewer;
						if (phpViewer.getViewerConfiguration() instanceof PHPStructuredTextViewerConfiguration) {
							PHPStructuredTextViewerConfiguration viewerConfiguration = (PHPStructuredTextViewerConfiguration) phpViewer
									.getViewerConfiguration();
							IContentAssistProcessor[] processors = viewerConfiguration
									.getProcessorMap().get(type);
							if (processors != null) {
								StringBuffer sb = new StringBuffer();
								for (int i = 0; i < processors.length; i++) {
									sb.append(computeAllAutoActivationTriggers(processors[i]));
								}
								if (sb.toString().indexOf(e.character) < 0) {
									stop();
									return;
								}
							} else {
								IContentAssistProcessor processor = getContentAssistProcessor(type);
								if (computeAllAutoActivationTriggers(processor)
										.indexOf(e.character) < 0) {
									stop();
									return;
								}
							}
						}
					}
					char[] activation;
					activation = (char[]) evaluatePrivateMemberMethod(
							"fContentAssistSubjectControlAdapter", "getCompletionProposalAutoActivationCharacters", new Class[] { ContentAssistant.class, int.class }, new Object[] { PHPContentAssistant.super, pos }); //$NON-NLS-1$ //$NON-NLS-2$
					activated = contains(activation, e.character);
				} else {
					if (e.character == 0x3E && document.getChar(pos - 1) != '-') {// '>'
																					// will
																					// not
																					// trigger
																					// proposal
																					// popuped
						stop();
						return;
					}
				}
				int showStyle;
				if (activated && !isProposalPopupActive()) {
					showStyle = SHOW_PROPOSALS;
				} else {
					if (isContextInfoPopupActive()) {
						showStyle = SHOW_CONTEXT_INFO;
					} else {
						stop();
						return;
					}
				}

				if (fThread != null && fThread.isAlive()) {
					reset(showStyle);
				} else {
					start(showStyle);
				}

			} catch (Exception e1) {
				PHPUiPlugin.log(e1);
			}
		}

		private String computeAllAutoActivationTriggers(
				IContentAssistProcessor processor) {
			if (processor == null) {
				return ""; //$NON-NLS-1$
			}

			StringBuffer buf = new StringBuffer(5);
			char[] triggers = processor
					.getCompletionProposalAutoActivationCharacters();
			if (triggers != null) {
				buf.append(triggers);
			}
			triggers = processor
					.getContextInformationAutoActivationCharacters();
			if (triggers != null) {
				buf.append(triggers);
			}
			return buf.toString();
		}

		private boolean contains(char[] characters, char character) {
			if (characters != null) {
				for (char character2 : characters) {
					if (character == character2) {
						return true;
					}
				}
			}
			return false;
		}

		private Object getPrivateMember(String member) throws Exception {
			Field declaredField = PHPContentAssistant.this.getClass()
					.getSuperclass().getSuperclass().getDeclaredField(member);
			declaredField.setAccessible(true);
			Object fProposalPopup = declaredField.get(PHPContentAssistant.this);
			return fProposalPopup;
		}

		private Object evaluatePrivateMemberMethod(String privateMember,
				String method, Class<?>[] params, Object[] args)
				throws Exception {
			Method declaredMethod = getPrivateMemberMethod(privateMember,
					method, params);
			if (declaredMethod == null) {
				return null;
			}
			Object member = getPrivateMember(privateMember);
			return declaredMethod.invoke(member, args);
		}

		private Method getPrivateMemberMethod(String privateMember,
				String method, Class<?>[] params) throws Exception {
			Object member = getPrivateMember(privateMember);
			if (member == null) {
				return null;
			}
			return getPrivateMetod(member, method, params);
		}

		private Method getPrivateMetod(Object obj, String method,
				Class<?>[] params) throws Exception {
			Method declaredMethod = obj.getClass().getDeclaredMethod(method,
					params);
			declaredMethod.setAccessible(true);
			return declaredMethod;
		}
	}

	public boolean provide(IContentAssistProcessor processor) {
		return true;
	}
}
