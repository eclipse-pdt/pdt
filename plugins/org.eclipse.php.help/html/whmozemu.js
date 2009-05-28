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
//	WebHelp 5.10.002
if (! window.gbIE4 && (window.gbNav6 || gbSafari3) && !document.childNodes[0].insertAdjacentHTML){

HTMLElement.prototype.insertAdjacentElement = function(where,parsedNode)
{
	switch (where){
	case 'beforeBegin':
		this.parentNode.insertBefore(parsedNode,this);
		break;
	case 'afterBegin':
		this.insertBefore(parsedNode,this.firstChild);
		break;
	case 'beforeEnd':
		this.appendChild(parsedNode);
		break;
	case 'afterEnd':
		if (this.nextSibling){
		this.parentNode.insertBefore(parsedNode,this.nextSibling);
		} else {
		this.parentNode.appendChild(parsedNode);
		}
		break;
	}
}

HTMLElement.prototype.insertAdjacentHTML = function(where,htmlStr){

	var r = this.ownerDocument.createRange();
	r.setStartBefore(this);
	var parsedHTML = r.createContextualFragment(htmlStr);
	this.insertAdjacentElement(where,parsedHTML);
}


HTMLElement.prototype.insertAdjacentText = function(where,txtStr){

	var parsedText = document.createTextNode(txtStr);
	this.insertAdjacentElement(where,parsedText);
}
}

function testScroll() {
	// Initialize scrollbar cache if necessary
	if (window._pageXOffset==null) {
		window._pageXOffset = window.pageXOffset;
		window._pageYOffset = window.pageYOffset;
	}
	// Expose Internet Explorer compatible object model
	document.scrollTop = window.pageYOffset;
	document.scrollLeft = window.pageXOffset;
	window.document.scrollHeight = document.height;
	window.document.scrollWidth = document.width;
	window.document.clientWidth = window.innerWidth;
	window.document.clientHeight = window.innerHeight;

	// If cache!=current values, call the onscroll event
	if (((window.pageXOffset!=window._pageXOffset) || (window.pageYOffset!=window._pageYOffset)) && (window.onscroll)) 
		window.onscroll();
	// Cache new values
	window._pageXOffset = window.pageXOffset;
	window._pageYOffset = window.pageYOffset;
	}

// Create compatibility layer for Netscape
if (window.gbNav6 && !window.gbNav7) {
	setInterval("testScroll()",50)
}