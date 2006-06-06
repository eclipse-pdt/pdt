package org.eclipse.php.core.documentModel.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.documentModel.parser.structregions.PHPStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.provisional.events.NoChangeEvent;
import org.eclipse.wst.sse.core.internal.provisional.events.StructuredDocumentEvent;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.sse.core.internal.text.CoreNodeList;
import org.eclipse.wst.xml.core.internal.parser.XMLStructuredDocumentReParser;

/**
 * Handles the php region when reparsing an XML/PHP structured document
 * @author Roy, 2006
 */
public class PhpStructuredDocumentReParser extends XMLStructuredDocumentReParser {

	public PhpStructuredDocumentReParser() {
		super();
	}

	/**
	 * Adding the support to php comments
	 */
	protected StructuredDocumentEvent checkForComments() {
		StructuredDocumentEvent result = checkForCriticalKey("/*");
		if (result == null) {
			result = checkForCriticalKey("*/");
		}
		return result != null ? result : super.checkForComments();
	}
	
	/**
	 * adds the Php structured documents regions reparsing...
	 */
	protected StructuredDocumentEvent core_reparse(int rescanStart, int rescanEnd, CoreNodeList oldNodes, boolean firstTime) {
		assert oldNodes != null;

		// if first time - we do not need to restore the php states... 
		// so resume as usual 
		if (firstTime)
			return super.core_reparse(rescanStart, rescanEnd, oldNodes, firstTime);

		// Gets the first region 
		IStructuredDocumentRegion head = oldNodes.getLength() == 0 ? null : oldNodes.item(0);

		// Gets the current parser 
		final RegionParser parser = fStructuredDocument.getParser();
		assert parser instanceof PhpSourceParser;
		PhpSourceParser phpSourceParser = ((PhpSourceParser) parser);

		// if it is a Php structured document region, 
		// we want to restore the previous state of the Php Lexer, 
		// then the PhpTokenizer will be able to resume 
		// tokenzing the edited text. 

		// if it is an XML code - reset the php states
		// and resume as usual ...		
		if (head.getType()  != PHPRegionTypes.PHP_CONTENT) {
			phpSourceParser.setLastStates(null, false);
			return super.core_reparse(rescanStart, rescanEnd, oldNodes, firstTime);
		}
		
		// Gets the old region (to restore the last state)
		PHPStructuredDocumentRegion oldPhpStructureDocument = (PHPStructuredDocumentRegion) head;
		
		// finally... set the old php lexer 
		// (before parsing of the regions) 
		phpSourceParser.setLastStates(oldPhpStructureDocument.lexerState, oldPhpStructureDocument.inPhpState);

		// set the old structured regions 
		lastOldNode = oldNodes.item(oldNodes.getLength() - 1);
		firstOldNode = oldPhpStructureDocument;

		// reset the new region list
		newRegionList.clear();

		// do the reparse
		super.core_reparse(rescanStart, rescanEnd, oldNodes, firstTime);

		// now we should check if we need to resume to 
		// the next structured documents...
		IStructuredDocumentRegion next = lastOldNode.getNext();
		
		// resume reparsing if the states were changes 
		// incomparison to the old ones
		boolean inEnhancedReparseStep = true; // start the enhanced reparsing phase
		while (inEnhancedReparseStep && next != null && next instanceof PHPStructuredDocumentRegion) {
			// go to the next old php region - safe cast
			oldPhpStructureDocument = (PHPStructuredDocumentRegion) next;

			// if the state was changed comparing the oldNodes states - resume reparsing 
			if (oldPhpStructureDocument.inPhpState != phpSourceParser.isLastPhpTokenizerState() || 
			   (oldPhpStructureDocument.lexerState != null && !oldPhpStructureDocument.lexerState.equals(phpSourceParser.getLastPhpLexerState()) || 
		       (phpSourceParser.getLastPhpLexerState() != null && oldPhpStructureDocument.lexerState == null)) )   {

				// update the last node if we extends the dirty end we got
				lastOldNode = oldPhpStructureDocument;

				// resume to next region
				next = oldPhpStructureDocument.getNext();

				// reparse next region
				rescanStart = rescanEnd;
				rescanEnd = rescanStart + oldPhpStructureDocument.getLength();
				super.core_reparse(rescanStart, rescanEnd, new CoreNodeList(oldPhpStructureDocument, oldPhpStructureDocument), firstTime);

			} else {
				// else stop enhanced reparsing.
				inEnhancedReparseStep = false;
			}
		}

		// implement minimumEvent method over all new nodes
		final StructuredDocumentEvent structuredDocumentEvent = super.minimumEvent(new CoreNodeList(firstOldNode, lastOldNode), toUnionList(newRegionList));
		structuredDocumentEvent.setDeletedText(fDeletedText);
		return structuredDocumentEvent;
	}

	/**
	 * Originaly this method finds the lowest set of nodes that should be changed.
	 * But here I delay the action till the end of the reparse action, since 
	 * I want to get the minimum event above ALL nodes
	 * TRICKY: Aggregate the new nodes into the newRegionList. 
	 * At the end of the reparse call the super.minimumEvent with the aggregated data   
	 */
	private IStructuredDocumentRegion firstOldNode;
	private IStructuredDocumentRegion lastOldNode;
	private final List newRegionList = new ArrayList();

	protected StructuredDocumentEvent minimumEvent(CoreNodeList oldNodes, CoreNodeList newNodes) {
		assert newNodes != null;

		// if the first old node is an XML then resume XML reparsing phase 
		if (oldNodes.item(0).getType() != PHPRegionTypes.PHP_CONTENT) 
			return super.minimumEvent(oldNodes, newNodes);
		
		final int lastIndex = newRegionList.size();
		if (lastIndex != 0) {
			// safe cast since all the nodes are from the structured region family
			final IStructuredDocumentRegion last = (IStructuredDocumentRegion) newRegionList.get(lastIndex - 1);
			final IStructuredDocumentRegion newStructuredDocumentRegion = newNodes.item(0);
			last.setNext(newStructuredDocumentRegion);
			newStructuredDocumentRegion.setPrevious(last);
		}
		newRegionList.addAll(toList(newNodes));

		return new NoChangeEvent(fStructuredDocument, fRequester, fChanges, fStart, fLengthToReplace);
	}

	/**
	 * Converts a CoreNodeList to list
	 * @param newNodes
	 */
	private List toList(CoreNodeList newNodes) {
		assert newNodes != null;

		final int length = newNodes.getLength();
		List result = new ArrayList(length);
		for (int i = 0; i < length; ++i) result.add(newNodes.item(i));
		return result;
	}

	/**
	 * This method returns a unioned list from the newNodes.
	 * The purpose here is to union some structured document that can be merged
	 * This is done in order to save a contract that all regions are seperated by close region
	 * or semicolon region 
	 * @param newNodes
	 */
	private CoreNodeList toUnionList(List newNodes) {
		assert newNodes != null;
		
		// fast checking if the new Nodes are empty list
		if (newNodes.size() == 0) {
			return new CoreNodeList(null);
		}
		
		int i = 0;
		while (i + 1 < newNodes.size()) { // while we have next node  
			final IStructuredDocumentRegion current = (IStructuredDocumentRegion) newNodes.get(i);
			final String type = current.getLastRegion().getType();

			IStructuredDocumentRegion next = (IStructuredDocumentRegion) newNodes.get(i + 1);
			
			// if we can merge the current 
			// region with the next region - merge it!
			if (current.getType() == PHPRegionContext.PHP_CONTENT && type != PHPRegionTypes.PHP_SEMICOLON && // it is php content not ending with semicolon 
				type != PHPRegionTypes.PHP_CLOSETAG && type != PHPRegionTypes.PHP_OPENTAG && // or close/open tag 
				next != null && next.getFirstRegion().getType() != PHPRegionTypes.PHP_CLOSETAG) { // or the next region is close tag 
				 
				final int itemLength = current.getLength();

				// here, we add all regions of the next document 
				// this is done by adjusting the start position of the region by 
				// the length of the item's length and adding it to the item's list
				final ITextRegionList regions = next.getRegions();
				for (int j = 0; j < regions.size(); ++j) {
					final ITextRegion textRegion = regions.get(j);
					textRegion.adjustStart(itemLength);
					current.addRegion(textRegion);
				}

				// at last we can adjust the length of the item by the next's sd length 
				current.adjustLength(next.getLength());
				newNodes.remove(i + 1); // remove the nexr structured document
				
				// update links 
				if (i + 1 < newNodes.size()) {
					next = (IStructuredDocumentRegion) newNodes.get(i + 1);				
					current.setNext(next);
					next.setPrevious(current);
				} else 
					current.setNext(null);
				
			} else {
				// here do not increment the index i since we want to 
				// unite the next ostructured document with the current one.

				i++;
			}
			
		}
		// now build the new CoreNodeList from the final list 
		// the list may changed so get the list size again...
		return new CoreNodeList((IStructuredDocumentRegion) newNodes.get(0), (IStructuredDocumentRegion) newNodes.get(newNodes.size() - 1));
	}
}
