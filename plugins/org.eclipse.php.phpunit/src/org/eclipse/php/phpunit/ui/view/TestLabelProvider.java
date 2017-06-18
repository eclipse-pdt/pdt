/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPElementImageDescriptor;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;
import org.eclipse.php.phpunit.model.elements.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.markers.internal.Util;

public class TestLabelProvider extends LabelProvider {

	private final Image fExceptionIcon = Util.getImage(IMarker.SEVERITY_ERROR);
	private final Image fFunctionIcon = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_MISC_PUBLIC);
	private final Image fStaticFunctionIcon = PHPUiPlugin.getImageDescriptorRegistry()
			.get(new PHPElementImageDescriptor(PHPPluginImages.DESC_MISC_PUBLIC, PHPElementImageDescriptor.STATIC,
					PHPElementImageDescriptor.SMALL_SIZE));
	private final Image fSuiteErrorIcon = PHPUnitPlugin.createImage("obj16/tsuiteerror.png"); //$NON-NLS-1$
	private final Image fSuiteFailIcon = PHPUnitPlugin.createImage("obj16/tsuitefail.png"); //$NON-NLS-1$
	private final Image fSuiteOkIcon = PHPUnitPlugin.createImage("obj16/tsuiteok.png"); //$NON-NLS-1$
	private final Image fSuiteRunningIcon = PHPUnitPlugin.createImage("obj16/tsuiterun.png"); //$NON-NLS-1$
	private final Image fSuiteWarningIcon;

	private final Image fOkIcon;
	private final Image fErrorIcon;
	private final Image fWarningIcon;
	private final Image fFailureIcon;
	private final Image fTestRunningIcon;

	private PHPUnitView view;

	public TestLabelProvider(final PHPUnitView view) {
		this.view = view;
		Image image = PHPUnitPlugin.createImage("obj16/test.png"); //$NON-NLS-1$
		Image testSuiteImage = PHPUnitPlugin.createImage("obj16/tsuite.png"); //$NON-NLS-1$

		this.fOkIcon = PHPUnitPlugin.createImage("obj16/testok.png"); //$NON-NLS-1$
		this.fErrorIcon = PHPUnitPlugin.createImage("obj16/testerr.png"); //$NON-NLS-1$
		this.fFailureIcon = PHPUnitPlugin.createImage("obj16/testfail.png"); //$NON-NLS-1$
		this.fWarningIcon = createOverlay(image, ISharedImages.IMG_DEC_FIELD_WARNING);
		this.fTestRunningIcon = PHPUnitPlugin.createImage("obj16/tcaserun.png"); //$NON-NLS-1$

		this.fSuiteWarningIcon = createOverlay(testSuiteImage, ISharedImages.IMG_DEC_FIELD_WARNING);

		image.dispose();
		testSuiteImage.dispose();
	}

	private Image createOverlay(Image image, String symbolicName) {
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		return new DecorationOverlayIcon(image, sharedImages.getImageDescriptor(symbolicName), IDecoration.BOTTOM_LEFT)
				.createImage();
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof PHPUnitTestCase) {
			final PHPUnitTestCase test = (PHPUnitTestCase) element;
			switch (test.getStatus()) {
			case PHPUnitTest.STATUS_PASS:
				return fOkIcon;
			case PHPUnitTest.STATUS_INCOMPLETE:
			case PHPUnitTest.STATUS_SKIP:
				return fWarningIcon;
			case PHPUnitTest.STATUS_STARTED:
				return fTestRunningIcon;
			case PHPUnitTest.STATUS_FAIL:
				return fFailureIcon;
			case PHPUnitTest.STATUS_ERROR:
				return fErrorIcon;
			}
		} else if (element instanceof PHPUnitTestGroup) {
			final PHPUnitTestGroup test = (PHPUnitTestGroup) element;
			if (test.getTotalCount() > test.getRunCount())
				return fSuiteRunningIcon;
			switch (test.getStatus()) {
			case PHPUnitTest.STATUS_PASS:
				return fSuiteOkIcon;
			case PHPUnitTest.STATUS_INCOMPLETE:
			case PHPUnitTest.STATUS_SKIP:
				return fSuiteWarningIcon;
			case PHPUnitTest.STATUS_FAIL:
				return fSuiteFailIcon;
			case PHPUnitTest.STATUS_ERROR:
				return fSuiteErrorIcon;
			}
		} else if (element instanceof PHPUnitTraceFrame) {
			if (PHPUnitMessageParser.CALL_STATIC.equals(((PHPUnitTraceFrame) element).getTraceType()))
				return fStaticFunctionIcon;
			return fFunctionIcon;
		} else if (element instanceof PHPUnitTestException)
			return fExceptionIcon;
		else if (element instanceof PHPUnitTestWarning)
			return Util.getImage(IMarker.SEVERITY_WARNING);

		return null;
	}

	@Override
	public String getText(final Object element) {
		final PHPUnitElement test = (PHPUnitElement) element;
		String fileName = test.getLocalFile();
		if (StringUtils.isNotEmpty(fileName)) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(fileName));
			if (file == null) {
				file = view.getProject().getFile(fileName);
			}
			if (file != null) {
				fileName = file.getProjectRelativePath().toString();
			}
		}

		final int line = test.getLine();
		if (test instanceof PHPUnitTest) {
			String name = ((PHPUnitTest) test).getName();
			if (StringUtils.isEmpty(name)) {
				name = fileName + ":" + line; //$NON-NLS-1$
			}
			if (test instanceof PHPUnitTestCase) {
				final PHPUnitTestException exception = ((PHPUnitTestCase) test).getException();
				if (exception != null && StringUtils.isNotEmpty(exception.getMessage())) {
					name += ": " + exception.getMessage(); //$NON-NLS-1$
					if (StringUtils.isNotEmpty(exception.getDiff())) {
						name += " (see Object Diff tab ->)";//$NON-NLS-1$
					}
				}
			}
			return name;
		}
		if (test instanceof PHPUnitTestEvent) {
			final String message = ((PHPUnitTestEvent) test).getMessage();
			String prefix = StringUtils.EMPTY;
			if (test instanceof PHPUnitTestException) {
				prefix = ((PHPUnitTestException) test).getExceptionClass();
			} else if (test instanceof PHPUnitTestWarning) {
				prefix = ((PHPUnitTestWarning) test).getCode();
			}
			if (StringUtils.isNotEmpty(message)) {
				return prefix + ": " + message; //$NON-NLS-1$
			}
			return prefix;
		}
		if (test instanceof PHPUnitTraceFrame) {
			PHPUnitTraceFrame frame = (PHPUnitTraceFrame) test;
			IPath path = new Path(frame.getFile());
			return path.lastSegment() + " - " + test.toString() + "()"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return test.toString();
	}

	@Override
	public void dispose() {
		fOkIcon.dispose();
		fErrorIcon.dispose();
		fFailureIcon.dispose();
		fSuiteOkIcon.dispose();
		fSuiteErrorIcon.dispose();
		fSuiteFailIcon.dispose();
		fSuiteRunningIcon.dispose();
		fWarningIcon.dispose();
		fSuiteWarningIcon.dispose();
	}

}
