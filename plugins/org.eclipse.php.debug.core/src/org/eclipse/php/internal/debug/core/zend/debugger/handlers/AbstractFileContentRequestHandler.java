/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import java.util.zip.Adler32;

import org.eclipse.php.debug.core.debugger.handlers.IDebugRequestHandler;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.FileContentExtendedRequest;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.FileContentRequest;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.FileContentResponse;

/**
 * Abstract implementation of file content request handler.
 * 
 * @author michael
 */
public abstract class AbstractFileContentRequestHandler implements IDebugRequestHandler {

	protected void setResponseContent(FileContentResponse response, FileContentRequest request, byte[] content) {
		if (request instanceof FileContentExtendedRequest) {
			FileContentExtendedRequest extendedRequest = (FileContentExtendedRequest) request;
			if (filesAreIdentical(extendedRequest.getSize(), extendedRequest.getCheckSum(), content)) {
				response.setStatus(FileContentResponse.FILES_IDENTICAL);
				return;
			}
		}
		response.setContent(content);
	}

	private static boolean filesAreIdentical(int requestSize, int requestChecksum, byte[] content) {
		int checksum;
		if (requestSize == content.length) {
			checksum = calcCheckSum(content);
			return (requestChecksum == checksum);
		}
		try {
			// checking if the difference is just the line ending - \n instead
			// of \r\n or \n\r
			int numOfNewLine = 0;
			byte slashR = 13;
			byte slashN = 10;
			// counting lineBreaks
			for (byte element : content) {
				if (element == slashN) {
					numOfNewLine++;
				}
			}
			if (requestSize == content.length + numOfNewLine) {
				byte newContent[] = new byte[content.length + numOfNewLine];
				int newContentIndex = 0;
				// changing every \n to \r\n
				for (byte element : content) {
					if (element == slashN) {
						newContent[newContentIndex] = slashR;
						newContentIndex++;
					}
					newContent[newContentIndex] = element;
					newContentIndex++;
				}
				checksum = calcCheckSum(newContent);
				if (requestChecksum == checksum) {
					return true;
				}
				// changing \r\n to \n\r
				for (int index = 0; index < content.length; index++) {
					if (content[index] == slashN) {
						newContent[index] = slashR;
					} else if (content[index] == slashR) {
						newContent[index] = slashN;
					}
				}
				checksum = calcCheckSum(newContent);
				return (requestChecksum == checksum);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return false;
	}

	private static int calcCheckSum(byte[] content) {
		Adler32 checksumCalculator = new Adler32();
		checksumCalculator.update(content);
		return (int) checksumCalculator.getValue();
	}

}
