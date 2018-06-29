/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
var _collapsed = new Array();

function expand(e) {
	var i;
	if (!_collapsed[e.id]) {
		for (i = 0; c = document.getElementById(e.id + '_' + i); i++) {
			c.style.display = "";
			expand(c);
		}
	}
}

function collapse(e) {
	var i;
	for (i = 0; c = document.getElementById(e.id + '_' + i); i++) {
		c.style.display = "none";
		collapse(c);
	}
}

function toggle(id) {
	s = document.getElementById(id + '_s');
	if (_collapsed[id]) {
		if (s != null) {
			s.src = 'report_images/minus.png';
		}
		_collapsed[id] = false;
		expand(document.getElementById(id));
	} else {
		if (s != null) {
			s.src = 'report_images/plus.png';
		}
		_collapsed[id] = true;
		collapse(document.getElementById(id));
	}
	return false;
}
