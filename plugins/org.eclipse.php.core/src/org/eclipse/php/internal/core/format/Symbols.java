/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/

package org.eclipse.php.internal.core.format;

// taken from package org.eclipse.jdt.ui.text;

/**
 * Symbols for the heuristic java scanner.
 * 
 * @see Eclipse 3.0
 */
public interface Symbols {
	int TokenEOF = -1;
	int TokenLBRACE = 1;
	int TokenRBRACE = 2;
	int TokenLBRACKET = 3;
	int TokenRBRACKET = 4;
	int TokenLPAREN = 5;
	int TokenRPAREN = 6;
	int TokenSEMICOLON = 7;
	int TokenOTHER = 8;
	int TokenCOLON = 9;
	int TokenQUESTIONMARK = 10;
	int TokenCOMMA = 11;
	int TokenEQUAL = 12;
	int TokenLESSTHAN = 13;
	int TokenGREATERTHAN = 14;
	int TokenIF = 109;
	int TokenDO = 1010;
	int TokenFOR = 1011;
	int TokenTRY = 1012;
	int TokenCASE = 1013;
	int TokenELSE = 1014;
	int TokenBREAK = 1015;
	int TokenCATCH = 1016;
	int TokenWHILE = 1017;
	int TokenRETURN = 1018;
	int TokenSTATIC = 1019;
	int TokenSWITCH = 1020;
	int TokenFINALLY = 1021;
	// int TokenSYNCHRONIZED = 1022;
	int TokenGOTO = 1023;
	int TokenDEFAULT = 1024;
	int TokenNEW = 1025;
	int TokenCLASS = 1026;
	int TokenINTERFACE = 1027;
	// int TokenENUM = 1028;
	int TokenARRAY = 1029;
	int TokenTRAIT = 1030;
	int TokenENDIF = 1031;
	int TokenENDFOR = 1032;
	int TokenENDWHILE = 1033;
	int TokenENDWITCH = 1034;
	int TokenELSEIF = 1035;
	int TokenIDENT = 2000;
}
