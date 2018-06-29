/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/

package org.eclipse.php.composer.ui.editor;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class FormLayoutFactory {

	// Used in place of 0. If 0 is used, widget borders will appear clipped
	// on some platforms (e.g. Windows XP Classic Theme).
	// Form tool kit requires parent composites containing the widget to have
	// at least 1 pixel border margins in order to paint the flat borders.
	// The form toolkit paints flat borders on a given widget when native
	// borders are not painted by SWT. See FormToolkit#paintBordersFor()
	public static final int DEFAULT_CLEAR_MARGIN = 2;

	// Required to allow space for field decorations
	public static final int CONTROL_HORIZONTAL_INDENT = 3;

	// UI Forms Standards

	// FORM BODY
	public static final int FORM_BODY_MARGIN_TOP = 12;
	public static final int FORM_BODY_MARGIN_BOTTOM = 12;
	public static final int FORM_BODY_MARGIN_LEFT = 6;
	public static final int FORM_BODY_MARGIN_RIGHT = 6;
	public static final int FORM_BODY_HORIZONTAL_SPACING = 20;
	// Should be 20; but, we minus 3 because the section automatically pads the
	// bottom margin by that amount
	public static final int FORM_BODY_VERTICAL_SPACING = 17;
	public static final int FORM_BODY_MARGIN_HEIGHT = 0;
	public static final int FORM_BODY_MARGIN_WIDTH = 0;

	// SECTION CLIENT
	public static final int SECTION_CLIENT_MARGIN_TOP = 5;
	public static final int SECTION_CLIENT_MARGIN_BOTTOM = 5;
	// Should be 6; but, we minus 4 because the section automatically pads the
	// left margin by that amount
	public static final int SECTION_CLIENT_MARGIN_LEFT = 2;
	// Should be 6; but, we minus 4 because the section automatically pads the
	// right margin by that amount
	public static final int SECTION_CLIENT_MARGIN_RIGHT = 2;
	public static final int SECTION_CLIENT_HORIZONTAL_SPACING = 5;
	public static final int SECTION_CLIENT_VERTICAL_SPACING = 5;
	public static final int SECTION_CLIENT_MARGIN_HEIGHT = 0;
	public static final int SECTION_CLIENT_MARGIN_WIDTH = 0;

	public static final int SECTION_HEADER_VERTICAL_SPACING = 6;

	// CLEAR
	public static final int CLEAR_MARGIN_TOP = DEFAULT_CLEAR_MARGIN;
	public static final int CLEAR_MARGIN_BOTTOM = DEFAULT_CLEAR_MARGIN;
	public static final int CLEAR_MARGIN_LEFT = DEFAULT_CLEAR_MARGIN;
	public static final int CLEAR_MARGIN_RIGHT = DEFAULT_CLEAR_MARGIN;
	public static final int CLEAR_HORIZONTAL_SPACING = 0;
	public static final int CLEAR_VERTICAL_SPACING = 0;
	public static final int CLEAR_MARGIN_HEIGHT = 0;
	public static final int CLEAR_MARGIN_WIDTH = 0;

	// FORM PANE
	public static final int FORM_PANE_MARGIN_TOP = 0;
	public static final int FORM_PANE_MARGIN_BOTTOM = 0;
	public static final int FORM_PANE_MARGIN_LEFT = 0;
	public static final int FORM_PANE_MARGIN_RIGHT = 0;
	public static final int FORM_PANE_HORIZONTAL_SPACING = FORM_BODY_HORIZONTAL_SPACING;
	public static final int FORM_PANE_VERTICAL_SPACING = FORM_BODY_VERTICAL_SPACING;
	public static final int FORM_PANE_MARGIN_HEIGHT = 0;
	public static final int FORM_PANE_MARGIN_WIDTH = 0;

	// MASTER DETAILS
	public static final int MASTER_DETAILS_MARGIN_TOP = 0;
	public static final int MASTER_DETAILS_MARGIN_BOTTOM = 0;
	// Used only by masters part. Details part margin dynamically calculated
	public static final int MASTER_DETAILS_MARGIN_LEFT = 0;
	// Used only by details part. Masters part margin dynamically calcualated
	public static final int MASTER_DETAILS_MARGIN_RIGHT = 1;
	public static final int MASTER_DETAILS_HORIZONTAL_SPACING = FORM_BODY_HORIZONTAL_SPACING;
	public static final int MASTER_DETAILS_VERTICAL_SPACING = FORM_BODY_VERTICAL_SPACING;
	public static final int MASTER_DETAILS_MARGIN_HEIGHT = 0;
	public static final int MASTER_DETAILS_MARGIN_WIDTH = 0;

	/**
	 * 
	 */
	private FormLayoutFactory() {
		// NO-OP
	}

	/**
	 * For form bodies.
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static GridLayout createFormGridLayout(boolean makeColumnsEqualWidth, int numColumns) {
		GridLayout layout = new GridLayout();

		layout.marginHeight = FORM_BODY_MARGIN_HEIGHT;
		layout.marginWidth = FORM_BODY_MARGIN_WIDTH;

		layout.marginTop = FORM_BODY_MARGIN_TOP;
		layout.marginBottom = FORM_BODY_MARGIN_BOTTOM;
		layout.marginLeft = FORM_BODY_MARGIN_LEFT;
		layout.marginRight = FORM_BODY_MARGIN_RIGHT;

		layout.horizontalSpacing = FORM_BODY_HORIZONTAL_SPACING;
		layout.verticalSpacing = FORM_BODY_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For miscellaneous grouping composites. For sections (as a whole - header plus
	 * client).
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static GridLayout createClearGridLayout(boolean makeColumnsEqualWidth, int numColumns) {
		GridLayout layout = new GridLayout();

		layout.marginHeight = CLEAR_MARGIN_HEIGHT;
		layout.marginWidth = CLEAR_MARGIN_WIDTH;

		layout.marginTop = CLEAR_MARGIN_TOP;
		layout.marginBottom = CLEAR_MARGIN_BOTTOM;
		layout.marginLeft = CLEAR_MARGIN_LEFT;
		layout.marginRight = CLEAR_MARGIN_RIGHT;

		layout.horizontalSpacing = CLEAR_HORIZONTAL_SPACING;
		layout.verticalSpacing = CLEAR_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For form bodies.
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static TableWrapLayout createFormTableWrapLayout(boolean makeColumnsEqualWidth, int numColumns) {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = FORM_BODY_MARGIN_TOP;
		layout.bottomMargin = FORM_BODY_MARGIN_BOTTOM;
		layout.leftMargin = FORM_BODY_MARGIN_LEFT;
		layout.rightMargin = FORM_BODY_MARGIN_RIGHT;

		layout.horizontalSpacing = FORM_BODY_HORIZONTAL_SPACING;
		layout.verticalSpacing = FORM_BODY_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For composites used to group sections in left and right panes.
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static TableWrapLayout createFormPaneTableWrapLayout(boolean makeColumnsEqualWidth, int numColumns) {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = FORM_PANE_MARGIN_TOP;
		layout.bottomMargin = FORM_PANE_MARGIN_BOTTOM;
		layout.leftMargin = FORM_PANE_MARGIN_LEFT;
		layout.rightMargin = FORM_PANE_MARGIN_RIGHT;

		layout.horizontalSpacing = FORM_PANE_HORIZONTAL_SPACING;
		layout.verticalSpacing = FORM_PANE_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For composites used to group sections in left and right panes.
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static GridLayout createFormPaneGridLayout(boolean makeColumnsEqualWidth, int numColumns) {
		GridLayout layout = new GridLayout();

		layout.marginHeight = FORM_PANE_MARGIN_HEIGHT;
		layout.marginWidth = FORM_PANE_MARGIN_WIDTH;

		layout.marginTop = FORM_PANE_MARGIN_TOP;
		layout.marginBottom = FORM_PANE_MARGIN_BOTTOM;
		layout.marginLeft = FORM_PANE_MARGIN_LEFT;
		layout.marginRight = FORM_PANE_MARGIN_RIGHT;

		layout.horizontalSpacing = FORM_PANE_HORIZONTAL_SPACING;
		layout.verticalSpacing = FORM_PANE_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For miscellaneous grouping composites. For sections (as a whole - header plus
	 * client).
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static TableWrapLayout createClearTableWrapLayout(boolean makeColumnsEqualWidth, int numColumns) {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = CLEAR_MARGIN_TOP;
		layout.bottomMargin = CLEAR_MARGIN_BOTTOM;
		layout.leftMargin = CLEAR_MARGIN_LEFT;
		layout.rightMargin = CLEAR_MARGIN_RIGHT;

		layout.horizontalSpacing = CLEAR_HORIZONTAL_SPACING;
		layout.verticalSpacing = CLEAR_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For master sections belonging to a master details block.
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static GridLayout createMasterGridLayout(boolean makeColumnsEqualWidth, int numColumns) {
		GridLayout layout = new GridLayout();

		layout.marginHeight = MASTER_DETAILS_MARGIN_HEIGHT;
		layout.marginWidth = MASTER_DETAILS_MARGIN_WIDTH;

		layout.marginTop = MASTER_DETAILS_MARGIN_TOP;
		layout.marginBottom = MASTER_DETAILS_MARGIN_BOTTOM;
		layout.marginLeft = MASTER_DETAILS_MARGIN_LEFT;
		// Cannot set layout on a sash form.
		// In order to replicate the horizontal spacing between sections,
		// divide the amount by 2 and set the master section right margin to
		// half the amount and set the left details section margin to half
		// the amount. The default sash width is currently set at 3.
		// Minus 1 pixel from each half. Use the 1 left over pixel to separate
		// the details section from the vertical scollbar.
		int marginRight = MASTER_DETAILS_HORIZONTAL_SPACING;
		if (marginRight > 0) {
			marginRight = marginRight / 2;
			if (marginRight > 0) {
				marginRight--;
			}
		}
		layout.marginRight = marginRight;

		layout.horizontalSpacing = MASTER_DETAILS_HORIZONTAL_SPACING;
		layout.verticalSpacing = MASTER_DETAILS_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For details sections belonging to a master details block.
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static GridLayout createDetailsGridLayout(boolean makeColumnsEqualWidth, int numColumns) {
		GridLayout layout = new GridLayout();

		layout.marginHeight = MASTER_DETAILS_MARGIN_HEIGHT;
		layout.marginWidth = MASTER_DETAILS_MARGIN_WIDTH;

		layout.marginTop = MASTER_DETAILS_MARGIN_TOP;
		layout.marginBottom = MASTER_DETAILS_MARGIN_BOTTOM;
		// Cannot set layout on a sash form.
		// In order to replicate the horizontal spacing between sections,
		// divide the amount by 2 and set the master section right margin to
		// half the amount and set the left details section margin to half
		// the amount. The default sash width is currently set at 3.
		// Minus 1 pixel from each half. Use the 1 left over pixel to separate
		// the details section from the vertical scollbar.
		int marginLeft = MASTER_DETAILS_HORIZONTAL_SPACING;
		if (marginLeft > 0) {
			marginLeft = marginLeft / 2;
			if (marginLeft > 0) {
				marginLeft--;
			}
		}
		layout.marginLeft = marginLeft;
		layout.marginRight = MASTER_DETAILS_MARGIN_RIGHT;

		layout.horizontalSpacing = MASTER_DETAILS_HORIZONTAL_SPACING;
		layout.verticalSpacing = MASTER_DETAILS_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For composites set as section clients. For composites containg form text.
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static GridLayout createSectionClientGridLayout(boolean makeColumnsEqualWidth, int numColumns) {
		GridLayout layout = new GridLayout();

		layout.marginHeight = SECTION_CLIENT_MARGIN_HEIGHT;
		layout.marginWidth = SECTION_CLIENT_MARGIN_WIDTH;

		layout.marginTop = SECTION_CLIENT_MARGIN_TOP;
		layout.marginBottom = SECTION_CLIENT_MARGIN_BOTTOM;
		layout.marginLeft = SECTION_CLIENT_MARGIN_LEFT;
		layout.marginRight = SECTION_CLIENT_MARGIN_RIGHT;

		layout.horizontalSpacing = SECTION_CLIENT_HORIZONTAL_SPACING;
		layout.verticalSpacing = SECTION_CLIENT_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * For composites set as section clients. For composites containg form text.
	 * 
	 * @param makeColumnsEqualWidth
	 * @param numColumns
	 */
	public static TableWrapLayout createSectionClientTableWrapLayout(boolean makeColumnsEqualWidth, int numColumns) {
		TableWrapLayout layout = new TableWrapLayout();

		layout.topMargin = SECTION_CLIENT_MARGIN_TOP;
		layout.bottomMargin = SECTION_CLIENT_MARGIN_BOTTOM;
		layout.leftMargin = SECTION_CLIENT_MARGIN_LEFT;
		layout.rightMargin = SECTION_CLIENT_MARGIN_RIGHT;

		layout.horizontalSpacing = SECTION_CLIENT_HORIZONTAL_SPACING;
		layout.verticalSpacing = SECTION_CLIENT_VERTICAL_SPACING;

		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		layout.numColumns = numColumns;

		return layout;
	}

	/**
	 * Debug method.
	 * 
	 * MAGENTA = 11 CYAN = 13 GREEN = 5
	 * 
	 * @param container
	 * @param color
	 */
	public static void visualizeLayoutArea(Composite container, int color) {
		container.setBackground(Display.getCurrent().getSystemColor(color));
	}
}
