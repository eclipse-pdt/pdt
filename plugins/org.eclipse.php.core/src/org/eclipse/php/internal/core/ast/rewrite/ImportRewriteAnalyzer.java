/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.rewrite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;

public final class ImportRewriteAnalyzer {

	private final ISourceModule sourceModule;
	private final ArrayList<PackageEntry> packageEntries;

	private final List<String> importsCreated;

	private final int currentPosition;

	private final IRegion replaceRange;

	private boolean filterImplicitImports;

	private int flags = 0;

	private static final int F_NEEDS_LEADING_DELIM = 2;
	private static final int F_NEEDS_TRAILING_DELIM = 4;

	public ImportRewriteAnalyzer(ISourceModule sourceModule, Program root, int position, String[] importOrder,
			boolean restoreExistingImports) {
		this.sourceModule = sourceModule;
		this.currentPosition = position;

		this.filterImplicitImports = true;

		this.packageEntries = new ArrayList<>(20);
		this.importsCreated = new ArrayList<>();
		this.flags = 0;

		this.replaceRange = evaluateReplaceRange(root);
		if (restoreExistingImports) {
			addExistingImports(root);
		}

		PackageEntry[] order = new PackageEntry[importOrder.length];
		for (int i = 0; i < order.length; i++) {
			String curr = importOrder[i];
			order[i] = new PackageEntry(curr, curr); // normal import group
		}

		addPreferenceOrderHolders(order);
	}

	private void addPreferenceOrderHolders(PackageEntry[] preferenceOrder) {
		if (this.packageEntries.isEmpty()) {
			// all new: copy the elements
			for (int i = 0; i < preferenceOrder.length; i++) {
				this.packageEntries.add(preferenceOrder[i]);
			}
		} else {
			// match the preference order entries to existing imports
			// entries not found are appended after the last successfully
			// matched entry

			PackageEntry[] lastAssigned = new PackageEntry[preferenceOrder.length];

			// find an existing package entry that matches most
			for (int k = 0; k < this.packageEntries.size(); k++) {
				PackageEntry entry = this.packageEntries.get(k);
				if (!entry.isComment()) {
					String currName = entry.getName();
					int currNameLen = currName.length();
					int bestGroupIndex = -1;
					int bestGroupLen = -1;
					for (int i = 0; i < preferenceOrder.length; i++) {
						String currPrefEntry = preferenceOrder[i].getName();
						int currPrefLen = currPrefEntry.length();
						if (currName.startsWith(currPrefEntry) && currPrefLen >= bestGroupLen) {
							if (currPrefLen == currNameLen
									|| currName.charAt(currPrefLen) == NamespaceReference.NAMESPACE_SEPARATOR) {
								if (bestGroupIndex == -1 || currPrefLen > bestGroupLen) {
									bestGroupLen = currPrefLen;
									bestGroupIndex = i;
								}
							}
						}
					}
					if (bestGroupIndex != -1) {
						entry.setGroupID(preferenceOrder[bestGroupIndex].getName());
						lastAssigned[bestGroupIndex] = entry; // remember last
						// entry
					}
				}
			}
			// fill in not-assigned categories, keep partial order
			int currAppendIndex = 0;
			for (int i = 0; i < lastAssigned.length; i++) {
				PackageEntry entry = lastAssigned[i];
				if (entry == null) {
					PackageEntry newEntry = preferenceOrder[i];
					if (currAppendIndex == 0) {
						currAppendIndex = getIndexAfterStatics();
					}
					this.packageEntries.add(currAppendIndex, newEntry);
					currAppendIndex++;
				} else {
					currAppendIndex = this.packageEntries.indexOf(entry) + 1;
				}
			}
		}
	}

	private static String getQualifier(String decl) {
		String namesapceName = PHPModelUtils.extractNameSpaceName(decl);
		if (namesapceName == null) {
			return "global namespace"; //$NON-NLS-1$
		}
		return namesapceName;
	}

	private static String getFullName(UseStatement decl) {
		return decl.parts().get(0).getName().getName();
	}

	private void addExistingImports(Program root) {
		List<UseStatement> decls = root.getUseStatements(currentPosition);
		if (decls.isEmpty()) {
			return;
		}
		PackageEntry currPackage = null;

		UseStatement curr = decls.get(0);
		int currOffset = curr.getStart();
		int currLength = curr.getLength();
		int currEndLine = root.getLineNumber(currOffset + currLength);

		for (int i = 1; i < decls.size(); i++) {
			String name = getFullName(curr);
			String packName = getQualifier(name);
			if (currPackage == null || currPackage.compareTo(packName) != 0) {
				currPackage = new PackageEntry(packName, null);
				this.packageEntries.add(currPackage);
			}

			UseStatement next = decls.get(i);
			int nextOffset = next.getStart();
			int nextLength = next.getLength();
			int nextOffsetLine = root.getLineNumber(nextOffset + 1);

			// if next import is on a different line, modify the end position to
			// the next line begin offset
			if (currEndLine < nextOffsetLine) {
				currEndLine++;
				nextOffset = getPostion(root, currEndLine);
			}
			currPackage.add(new ImportDeclEntry(name, new Region(currOffset, nextOffset - currOffset)));
			currOffset = nextOffset;
			curr = next;

			// add a comment entry for spacing between imports
			if (currEndLine < nextOffsetLine) {
				nextOffset = getPostion(root, nextOffsetLine);

				currPackage = new PackageEntry(); // create a comment package
				// entry for this
				this.packageEntries.add(currPackage);
				currPackage.add(new ImportDeclEntry(null, new Region(currOffset, nextOffset - currOffset)));

				currOffset = nextOffset;
			}
			currEndLine = root.getLineNumber(nextOffset + nextLength);
		}

		String name = getFullName(curr);
		String packName = getQualifier(name);
		if (currPackage == null || currPackage.compareTo(packName) != 0) {
			currPackage = new PackageEntry(packName, null);
			this.packageEntries.add(currPackage);
		}
		int length = this.replaceRange.getOffset() + this.replaceRange.getLength() - curr.getStart();
		currPackage.add(new ImportDeclEntry(name, new Region(curr.getStart(), length)));
	}

	/**
	 * Sets that implicit imports (types in default package, CU- package and
	 * 'java.lang') should not be created. Note that this is a heuristic filter
	 * and can lead to missing imports, e.g. in cases where a type is forced to
	 * be specified due to a name conflict. By default, the filter is enabled.
	 * 
	 * @param filterImplicitImports
	 *            The filterImplicitImports to set
	 */
	public void setFilterImplicitImports(boolean filterImplicitImports) {
		this.filterImplicitImports = filterImplicitImports;
	}

	private static class PackageMatcher {
		private String newName;
		private String bestName;
		private int bestMatchLen;

		public PackageMatcher() {
			// initialization in 'initialize'
		}

		public void initialize(String newImportName, String bestImportName) {
			this.newName = newImportName;
			this.bestName = bestImportName;
			this.bestMatchLen = getCommonPrefixLength(bestImportName, newImportName);
		}

		public boolean isBetterMatch(String currName, boolean preferCurr) {
			boolean isBetter;
			int currMatchLen = getCommonPrefixLength(currName, this.newName);
			int matchDiff = currMatchLen - this.bestMatchLen;
			if (matchDiff == 0) {
				if (currMatchLen == this.newName.length() && currMatchLen == currName.length()
						&& currMatchLen == this.bestName.length()) {
					// duplicate entry and complete match
					isBetter = preferCurr;
				} else {
					isBetter = sameMatchLenTest(currName);
				}
			} else {
				isBetter = (matchDiff > 0); // curr has longer match
			}
			if (isBetter) {
				this.bestName = currName;
				this.bestMatchLen = currMatchLen;
			}
			return isBetter;
		}

		private boolean sameMatchLenTest(String currName) {
			int matchLen = this.bestMatchLen;
			// known: bestName and currName differ from newName at position
			// 'matchLen'
			// currName and bestName don't have to differ at position 'matchLen'

			// determine the order and return true if currName is closer to
			// newName
			char newChar = getCharAt(this.newName, matchLen);
			char currChar = getCharAt(currName, matchLen);
			char bestChar = getCharAt(this.bestName, matchLen);

			if (newChar < currChar) {
				if (bestChar < newChar) { // b < n < c
					return (currChar - newChar) < (newChar - bestChar); // -> (c
					// - n)
					// < (n
					// - b)
				} else { // n < b && n < c
					if (currChar == bestChar) { // longer match between curr and
						// best
						return false; // keep curr and best together, new should
						// be before both
					} else {
						return currChar < bestChar; // -> (c < b)
					}
				}
			} else {
				if (bestChar > newChar) { // c < n < b
					return (newChar - currChar) < (bestChar - newChar); // -> (n
					// - c)
					// < (b
					// - n)
				} else { // n > b && n > c
					if (currChar == bestChar) { // longer match between curr and
						// best
						return true; // keep curr and best together, new should
						// be ahead of both
					} else {
						return currChar > bestChar; // -> (c > b)
					}
				}
			}
		}
	}

	/* package */static int getCommonPrefixLength(String s, String t) {
		int len = Math.min(s.length(), t.length());
		for (int i = 0; i < len; i++) {
			if (s.charAt(i) != t.charAt(i)) {
				return i;
			}
		}
		return len;
	}

	/* package */static char getCharAt(String str, int index) {
		if (str.length() > index) {
			return str.charAt(index);
		}
		return 0;
	}

	private PackageEntry findBestMatch(String newName, boolean isStatic) {
		if (this.packageEntries.isEmpty()) {
			return null;
		}
		String groupId = null;
		int longestPrefix = -1;
		// find the matching group
		for (int i = 0; i < this.packageEntries.size(); i++) {
			PackageEntry curr = this.packageEntries.get(i);
			String currGroup = curr.getGroupID();
			if (currGroup != null && newName.startsWith(currGroup)) {
				int prefixLen = currGroup.length();
				if (prefixLen == newName.length()) {
					return curr; // perfect fit, use entry
				}
				if ((newName.charAt(prefixLen) == NamespaceReference.NAMESPACE_SEPARATOR || prefixLen == 0)
						&& prefixLen > longestPrefix) {
					longestPrefix = prefixLen;
					groupId = currGroup;
				}
			}
		}
		PackageEntry bestMatch = null;
		PackageMatcher matcher = new PackageMatcher();
		matcher.initialize(newName, ""); //$NON-NLS-1$
		for (int i = 0; i < this.packageEntries.size(); i++) { // find the best
			// match with
			// the same
			// group
			PackageEntry curr = this.packageEntries.get(i);
			if (!curr.isComment()) {
				if (groupId == null || groupId.equals(curr.getGroupID())) {
					boolean preferrCurr = (bestMatch == null)
							|| (curr.getNumberOfImports() > bestMatch.getNumberOfImports());
					if (matcher.isBetterMatch(curr.getName(), preferrCurr)) {
						bestMatch = curr;
					}
				}
			}
		}
		return bestMatch;
	}

	private boolean isImplicitImport(String qualifier, ISourceModule cu) {
		IType currentNamespace = null;
		try {
			IType[] types = cu.getTypes();
			if (types == null || types.length == 0) {
				return false;
			}
			for (IType type : types) {
				if (type.getSourceRange().getOffset() <= currentPosition
						&& type.getSourceRange().getOffset() + type.getSourceRange().getLength() >= currentPosition) {
					if (PHPFlags.isNamespace(type.getFlags())) {
						currentNamespace = type;
						break;
					}
				}
			}
		} catch (ModelException e) {
			return false;
		}
		String packageName;
		if (currentNamespace == null) {
			packageName = "global namespace"; //$NON-NLS-1$
		} else {
			packageName = currentNamespace.getTypeQualifiedName(NamespaceReference.NAMESPACE_DELIMITER);
		}
		if (qualifier.equals(packageName)) {
			return true;
		}
		return false;
	}

	public void addImport(String fullTypeName, boolean isStatic) {
		String typeContainerName = getQualifier(fullTypeName);
		ImportDeclEntry decl = new ImportDeclEntry(fullTypeName, null);
		sortIn(typeContainerName, decl, isStatic);
	}

	public boolean removeImport(String qualifiedName) {
		String containerName = getQualifier(qualifiedName);

		int nPackages = this.packageEntries.size();
		for (int i = 0; i < nPackages; i++) {
			PackageEntry entry = this.packageEntries.get(i);
			if (entry.compareTo(containerName) == 0) {
				if (entry.remove(qualifiedName)) {
					return true;
				}
			}
		}
		return false;
	}

	private int getIndexAfterStatics() {
		return this.packageEntries.size();
	}

	private void sortIn(String typeContainerName, ImportDeclEntry decl, boolean isStatic) {
		PackageEntry bestMatch = findBestMatch(typeContainerName, isStatic);
		if (bestMatch == null) {
			PackageEntry packEntry = new PackageEntry(typeContainerName, null);
			packEntry.add(decl);
			int insertPos = getIndexAfterStatics();
			this.packageEntries.add(insertPos, packEntry);
		} else {
			int cmp = typeContainerName.compareTo(bestMatch.getName());
			if (cmp == 0) {
				bestMatch.sortIn(decl);
			} else {
				// create a new package entry
				String group = bestMatch.getGroupID();
				if (group != null) {
					if (!typeContainerName.startsWith(group)) {
						group = null;
					}
				}
				PackageEntry packEntry = new PackageEntry(typeContainerName, group);
				packEntry.add(decl);
				int index = this.packageEntries.indexOf(bestMatch);
				if (cmp < 0) { // insert ahead of best match
					this.packageEntries.add(index, packEntry);
				} else { // insert after best match
					this.packageEntries.add(index + 1, packEntry);
				}
			}
		}
	}

	private IRegion evaluateReplaceRange(Program root) {
		List<UseStatement> imports = root.getUseStatements(currentPosition);
		if (!imports.isEmpty()) {
			UseStatement first = imports.get(0);
			UseStatement last = imports.get(imports.size() - 1);

			int startPos = first.getStart();
			int endPos = root.getExtendedStartPosition(last) + root.getExtendedLength(last);
			int endLine = root.getLineNumber(endPos);
			if (endLine > 0) {
				int nextLinePos = getPostion(root, endLine + 1);
				if (nextLinePos >= 0) {
					int firstTypePos = getFirstTypeBeginPos(root);
					if (firstTypePos != -1 && firstTypePos < nextLinePos) {
						endPos = firstTypePos;
					} else {
						endPos = nextLinePos;
					}
				}
			}
			return new Region(startPos, endPos - startPos);
		} else {
			int start = getPackageStatementEndPos(root);
			return new Region(start, 0);
		}
	}

	public MultiTextEdit getResultingEdits(IProgressMonitor monitor) throws ModelException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			int importsStart = this.replaceRange.getOffset();
			int importsLen = this.replaceRange.getLength();

			String lineDelim = getLineDelimiterUsed(sourceModule.getScriptProject());
			IBuffer buffer = this.sourceModule.getBuffer();

			int currPos = importsStart;
			MultiTextEdit resEdit = new MultiTextEdit();

			if ((this.flags & F_NEEDS_LEADING_DELIM) != 0) {
				// new import container
				resEdit.addChild(new InsertEdit(currPos, lineDelim));
			}

			ArrayList<String> stringsToInsert = new ArrayList<String>();

			int nPackageEntries = this.packageEntries.size();
			for (int i = 0; i < nPackageEntries; i++) {
				PackageEntry pack = this.packageEntries.get(i);
				int nImports = pack.getNumberOfImports();

				if (this.filterImplicitImports && isImplicitImport(pack.getName(), this.sourceModule)) {
					pack.removeAllNew();
					nImports = pack.getNumberOfImports();
				}
				if (nImports == 0) {
					continue;
				}

				for (int k = 0; k < nImports; k++) {
					ImportDeclEntry currDecl = pack.getImportAt(k);
					IRegion region = currDecl.getSourceRange();

					if (region == null) { // new entry
						String str = getNewImportString(currDecl.getElementName(), lineDelim);
						stringsToInsert.add(str);
					} else {
						int offset = region.getOffset();
						removeAndInsertNew(buffer, currPos, offset, stringsToInsert, resEdit);
						stringsToInsert.clear();
						currPos = offset + region.getLength();
					}
				}
			}

			int end = importsStart + importsLen;
			removeAndInsertNew(buffer, currPos, end, stringsToInsert, resEdit);

			if (importsLen == 0) {
				if (!this.importsCreated.isEmpty()) { // new
					// import
					// container
					if ((this.flags & F_NEEDS_TRAILING_DELIM) != 0) {
						resEdit.addChild(new InsertEdit(currPos, lineDelim));
					}
				} else {
					return new MultiTextEdit(); // no changes
				}
			}
			return resEdit;
		} finally {
			monitor.done();
		}
	}

	private void removeAndInsertNew(IBuffer buffer, int contentOffset, int contentEnd,
			ArrayList<String> stringsToInsert, MultiTextEdit resEdit) {
		int pos = contentOffset;
		for (int i = 0; i < stringsToInsert.size(); i++) {
			String curr = stringsToInsert.get(i);
			int idx = findInBuffer(buffer, curr, pos, contentEnd);
			if (idx != -1) {
				if (idx != pos) {
					resEdit.addChild(new DeleteEdit(pos, idx - pos));
				}
				pos = idx + curr.length();
			} else {
				resEdit.addChild(new InsertEdit(pos, curr));
			}
		}
		if (pos < contentEnd) {
			resEdit.addChild(new DeleteEdit(pos, contentEnd - pos));
		}
	}

	private int findInBuffer(IBuffer buffer, String str, int start, int end) {
		int pos = start;
		int len = str.length();
		if (pos + len > end || str.length() == 0) {
			return -1;
		}
		char first = str.charAt(0);
		int step = str.indexOf(first, 1);
		if (step == -1) {
			step = len;
		}
		while (pos + len <= end) {
			if (buffer.getChar(pos) == first) {
				int k = 1;
				while (k < len && buffer.getChar(pos + k) == str.charAt(k)) {
					k++;
				}
				if (k == len) {
					return pos; // found
				}
				if (k < step) {
					pos += k;
				} else {
					pos += step;
				}
			} else {
				pos++;
			}
		}
		return -1;
	}

	private String getNewImportString(String importName, String lineDelim) {
		StringBuffer buf = new StringBuffer();
		buf.append("use "); //$NON-NLS-1$
		buf.append(importName);
		buf.append(';'); // $NON-NLS-1$
		buf.append(lineDelim);

		this.importsCreated.add(importName);
		return buf.toString();
	}

	private int getFirstTypeBeginPos(Program root) {
		List<Statement> statements = root.statements();
		if (!statements.isEmpty()) {
			ASTNode node = null;
			boolean isAfterUseStatements = false;
			if (root.getUseStatements(currentPosition).size() == 0) {
				isAfterUseStatements = true;
			}
			NamespaceDeclaration namespace = root.getNamespaceDeclaration(currentPosition);
			if (namespace != null) {
				statements = namespace.getBody().statements();
			}
			for (Statement s : statements) {
				if (s instanceof UseStatement) {
					isAfterUseStatements = true;
					continue;
				}
				if (isAfterUseStatements) {
					node = s;
					break;
				}
			}
			return root.getExtendedStartPosition(node);
		}
		return -1;
	}

	private int getPackageStatementEndPos(Program root) {
		NamespaceDeclaration namespace = root.getNamespaceDeclaration(currentPosition);
		if (namespace != null) {
			NamespaceName packDecl = namespace.getName();
			int afterPackageStatementPos = -1;
			int lineNumber = root.getLineNumber(packDecl.getStart() + packDecl.getLength());
			if (lineNumber >= 0) {
				int lineAfterPackage = lineNumber + 1;
				afterPackageStatementPos = getPostion(root, lineAfterPackage);
			}
			if (afterPackageStatementPos < 0) {
				this.flags |= F_NEEDS_LEADING_DELIM;
				return packDecl.getStart() + packDecl.getLength();
			}
			int firstTypePos = getFirstTypeBeginPos(root);
			if (firstTypePos != -1 && firstTypePos <= afterPackageStatementPos) {
				if (firstTypePos <= afterPackageStatementPos) {
					this.flags |= F_NEEDS_TRAILING_DELIM;
					if (firstTypePos == afterPackageStatementPos) {
						this.flags |= F_NEEDS_LEADING_DELIM;
					}
					return firstTypePos;
				}
			}
			this.flags |= F_NEEDS_LEADING_DELIM;
			return afterPackageStatementPos; // insert a line after after
			// package statement
		}
		this.flags |= F_NEEDS_TRAILING_DELIM;
		return getPostion(root, 2);
	}

	private int getPostion(Program root, int line) {
		return getPostion(root, line, 0);
	}

	private int getPostion(Program root, int line, int column) {
		return root.getPosition(line, column) - 1;
	}

	public String toString() {
		int nPackages = this.packageEntries.size();
		StringBuffer buf = new StringBuffer("\n-----------------------\n"); //$NON-NLS-1$
		for (int i = 0; i < nPackages; i++) {
			PackageEntry entry = this.packageEntries.get(i);
			buf.append(entry.toString());
		}
		return buf.toString();
	}

	private static final class ImportDeclEntry {

		private String elementName;
		private IRegion sourceRange;

		public ImportDeclEntry(String elementName, IRegion sourceRange) {
			this.elementName = elementName;
			this.sourceRange = sourceRange;
		}

		public String getElementName() {
			return this.elementName;
		}

		public int compareTo(String fullName) {
			return this.elementName.compareTo(fullName);
		}

		public String getSimpleName() {
			return PHPModelUtils.extractElementName(elementName);
		}

		public boolean isNew() {
			return this.sourceRange == null;
		}

		public boolean isComment() {
			return this.elementName == null;
		}

		public IRegion getSourceRange() {
			return this.sourceRange;
		}

	}

	/*
	 * Internal element for the import structure: A container for imports of all
	 * types from the same package
	 */
	private final static class PackageEntry {
		private String name;
		private ArrayList<ImportDeclEntry> importEntries;
		private String group;

		/**
		 * Comment package entry
		 */
		public PackageEntry() {
			this("!", null); //$NON-NLS-1$
		}

		/**
		 * @param name
		 *            Name of the package entry. e.g. org.eclipse.jdt.ui,
		 *            containing imports like org.eclipse.jdt.ui.JavaUI.
		 * @param group
		 *            The index of the preference order entry assigned different
		 *            group id's will result in spacers between the entries
		 */
		public PackageEntry(String name, String group) {
			this.name = name;
			this.importEntries = new ArrayList<>(5);
			this.group = group;
		}

		public int compareTo(String otherName) {
			return this.name.compareTo(otherName);
		}

		public void sortIn(ImportDeclEntry imp) {
			String fullImportName = imp.getElementName();
			int insertPosition = -1;
			int nInports = this.importEntries.size();
			for (int i = 0; i < nInports; i++) {
				ImportDeclEntry curr = getImportAt(i);
				if (!curr.isComment()) {
					int cmp = curr.compareTo(fullImportName);
					if (cmp == 0) {
						return; // exists already
					} else if (cmp > 0 && insertPosition == -1) {
						insertPosition = i;
					}
				}
			}
			if (insertPosition == -1) {
				this.importEntries.add(imp);
			} else {
				this.importEntries.add(insertPosition, imp);
			}
		}

		public void add(ImportDeclEntry imp) {
			this.importEntries.add(imp);
		}

		public boolean remove(String fullName) {
			int nInports = this.importEntries.size();
			for (int i = 0; i < nInports; i++) {
				ImportDeclEntry curr = getImportAt(i);
				if (!curr.isComment() && curr.compareTo(fullName) == 0) {
					this.importEntries.remove(i);
					return true;
				}
			}
			return false;
		}

		public void removeAllNew() {
			int nInports = this.importEntries.size();
			for (int i = nInports - 1; i >= 0; i--) {
				ImportDeclEntry curr = getImportAt(i);
				if (curr.isNew()) {
					this.importEntries.remove(i);
				}
			}
		}

		public ImportDeclEntry getImportAt(int index) {
			return this.importEntries.get(index);
		}

		public int getNumberOfImports() {
			return this.importEntries.size();
		}

		public String getName() {
			return this.name;
		}

		public String getGroupID() {
			return this.group;
		}

		public void setGroupID(String groupID) {
			this.group = groupID;
		}

		public boolean isComment() {
			return "!".equals(this.name); //$NON-NLS-1$
		}

		public String toString() {
			StringBuffer buf = new StringBuffer();
			if (isComment()) {
				buf.append("comment\n"); //$NON-NLS-1$
			} else {
				buf.append(this.name);
				buf.append(", groupId: "); //$NON-NLS-1$
				buf.append(this.group);
				buf.append("\n"); //$NON-NLS-1$
				int nImports = getNumberOfImports();
				for (int i = 0; i < nImports; i++) {
					ImportDeclEntry curr = getImportAt(i);
					buf.append("  "); //$NON-NLS-1$
					buf.append(curr.getSimpleName());
					if (curr.isNew()) {
						buf.append(" (new)"); //$NON-NLS-1$
					}
					buf.append("\n"); //$NON-NLS-1$
				}
			}
			return buf.toString();
		}
	}

	public String[] getCreatedImports() {
		return this.importsCreated.toArray(new String[this.importsCreated.size()]);
	}

	/**
	 * Returns the line delimiter which is used in the specified project.
	 * 
	 * @param project
	 *            the java project, or <code>null</code>
	 * @return the used line delimiter
	 */
	private static String getLineDelimiterUsed(IScriptProject project) {
		return getProjectLineDelimiter(project);
	}

	private static String getProjectLineDelimiter(IScriptProject project) {
		if (project == null) {
			assert false;
			return null;
		}

		String lineDelimiter = getLineDelimiterPreference(project);
		if (lineDelimiter != null)
			return lineDelimiter;

		return System.getProperty("line.separator", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String getLineDelimiterPreference(IScriptProject project) {
		IScopeContext[] scopeContext;
		if (project != null) {
			// project preference
			scopeContext = new IScopeContext[] { new ProjectScope(project.getProject()) };
			String lineDelimiter = Platform.getPreferencesService().getString(Platform.PI_RUNTIME,
					Platform.PREF_LINE_SEPARATOR, null, scopeContext);
			if (lineDelimiter != null)
				return lineDelimiter;
		}
		// workspace preference
		scopeContext = new IScopeContext[] { InstanceScope.INSTANCE };
		String platformDefault = System.getProperty("line.separator", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		return Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR,
				platformDefault, scopeContext);
	}

}
