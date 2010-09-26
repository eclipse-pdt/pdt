package org.eclipse.php.internal.ui.editor.configuration;

import java.util.*;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.wst.sse.core.internal.text.rules.SimpleStructuredTypedRegion;
import org.eclipse.wst.sse.ui.internal.provisional.style.StructuredPresentationReconciler;

public class PHPStructuredPresentationReconciler extends
		StructuredPresentationReconciler {
	private static final String CSS_STYLE = "org.eclipse.wst.css.STYLE";
	private Map fRepairers;
	private static final Set<String> fTypeSet = new HashSet<String>();
	static {
		fTypeSet.add(CSS_STYLE);
	}

	ITypedRegion getWholeRegion(ITypedRegion[] wholePartitions,
			ITypedRegion originalRegion) {
		if (fTypeSet.contains(originalRegion.getType())) {
			int i = 0;
			int jumpto = -1;
			while (i < wholePartitions.length) {
				ITypedRegion r = wholePartitions[i];
				if (wholePartitions[i].getType().equals(
						originalRegion.getType())) {
					jumpto = getFollowingCSS(wholePartitions, i);
					r = new SimpleStructuredTypedRegion(r.getOffset(),
							wholePartitions[jumpto].getOffset()
									+ wholePartitions[jumpto].getLength()
									- r.getOffset(), originalRegion.getType());
					if (originalRegion.getOffset() >= r.getOffset()
							&& originalRegion.getOffset()
									+ originalRegion.getLength() <= r
									.getOffset() + r.getLength()) {
						return r;
					} else {
						i = jumpto + 2;
					}
				} else {
					i++;
				}

			}
		}
		return null;
	}

	@Override
	protected TextPresentation createPresentation(IRegion damage,
			IDocument document) {
		try {
			int validLength = Math.min(damage.getLength(), document.getLength()
					- damage.getOffset());

			if (fRepairers == null || fRepairers.isEmpty()) {
				TextPresentation presentation = new TextPresentation(damage, 1);
				presentation.setDefaultStyleRange(new StyleRange(damage
						.getOffset(), validLength, null, null));
				return presentation;
			}

			TextPresentation presentation = new TextPresentation(damage, 1000);

			ITypedRegion[] partitions = TextUtilities.computePartitioning(
					document, getDocumentPartitioning(), damage.getOffset(),
					validLength, false);

			// when modify editor content the damage region is not equal to
			// document's region,so we need to adjust the damage region's start
			// and length
			if (partitions != null && partitions.length > 0) {
				int start = damage.getOffset();
				int length = validLength;
				ITypedRegion[] wholePartitions = TextUtilities
						.computePartitioning(document,
								getDocumentPartitioning(), 0,
								document.getLength(), false);
				// determine start
				ITypedRegion newRegion = getWholeRegion(wholePartitions,
						partitions[0]);
				if (newRegion != null) {
					start = newRegion.getOffset();
				}
				newRegion = getWholeRegion(wholePartitions,
						partitions[partitions.length - 1]);
				if (newRegion != null) {
					length = newRegion.getOffset() + newRegion.getLength()
							- start;
				}
				// if (partitions[0].getType().equals(CSS_STYLE)) {
				// int i = 0;
				// int jumpto = -1;
				// while (i < wholePartitions.length) {
				// ITypedRegion r = wholePartitions[i];
				// if (wholePartitions[i].getType().equals(CSS_STYLE)) {
				// jumpto = getFollowingCSS(wholePartitions, i);
				// r = new SimpleStructuredTypedRegion(r.getOffset(),
				// wholePartitions[jumpto].getOffset()
				// + wholePartitions[jumpto]
				// .getLength()
				// - r.getOffset(), CSS_STYLE);
				// if (partitions[0].getOffset() >= r.getOffset()
				// && partitions[0].getOffset()
				// + partitions[0].getLength() <= r
				// .getOffset()
				// + r.getLength()) {
				// start = r.getOffset();
				// break;
				// } else {
				// i = jumpto + 2;
				// }
				// } else {
				// i++;
				// }
				//
				// }
				// }
				// // determine end
				// if (partitions[partitions.length - 1].getType().equals(
				// CSS_STYLE)) {
				// int i = 0;
				// int jumpto = -1;
				// while (i < wholePartitions.length) {
				// ITypedRegion r = wholePartitions[i];
				// if (wholePartitions[i].getType().equals(CSS_STYLE)) {
				// jumpto = getFollowingCSS(wholePartitions, i);
				// r = new SimpleStructuredTypedRegion(r.getOffset(),
				// wholePartitions[jumpto].getOffset()
				// + wholePartitions[jumpto]
				// .getLength()
				// - r.getOffset(), CSS_STYLE);
				// if (partitions[partitions.length - 1].getOffset() >= r
				// .getOffset()
				// && partitions[partitions.length - 1]
				// .getOffset()
				// + partitions[partitions.length - 1]
				// .getLength() <= r
				// .getOffset()
				// + r.getLength()) {
				// length = r.getOffset() + r.getLength() - start;
				// break;
				// } else {
				// i = jumpto + 2;
				// }
				// } else {
				// i++;
				// }
				//
				// }
				// }
				partitions = TextUtilities.computePartitioning(document,
						getDocumentPartitioning(), start, length, false);

			}

			Set<StyleRange> fRangeSet = new HashSet<StyleRange>();

			int jumpto = -1;
			for (int i = 0; i < partitions.length; i++) {
				ITypedRegion r = partitions[i];
				// IPresentationRepairer repairer = getRepairer(r.getType());
				// if (repairer != null)
				// repairer.createPresentation(presentation, r);
				if (r.getType().equals(CSS_STYLE)) {
					if (i > jumpto) {
						jumpto = getFollowingCSS(partitions, i);
						r = new SimpleStructuredTypedRegion(r.getOffset(),
								partitions[jumpto].getOffset()
										+ partitions[jumpto].getLength()
										- r.getOffset(), CSS_STYLE);
						IPresentationRepairer repairer = getRepairer(r
								.getType());
						if (repairer != null) {
							repairer.createPresentation(presentation, r);
							for (Iterator iterator = presentation
									.getAllStyleRangeIterator(); iterator
									.hasNext();) {
								StyleRange styleRange = (StyleRange) iterator
										.next();

								for (int j = i + 1; j < jumpto; j = j + 2) {
									ITypedRegion typedRegion = partitions[j];
									if (styleRange.start < typedRegion
											.getOffset()
											&& styleRange.start
													+ styleRange.length > typedRegion
													.getOffset()
													+ typedRegion.getLength()) {
										int end = styleRange.start
												+ styleRange.length;
										styleRange.length = typedRegion
												.getOffset() - styleRange.start;
										fRangeSet.add(styleRange);
										styleRange = new StyleRange(
												typedRegion.getOffset()
														+ typedRegion
																.getLength(),
												end
														- (typedRegion
																.getOffset() + typedRegion
																.getLength()),
												styleRange.foreground,
												styleRange.background,
												styleRange.fontStyle);
									} else if (styleRange.start < typedRegion
											.getOffset()
											&& styleRange.start
													+ styleRange.length > typedRegion
													.getOffset()) {
										styleRange.length = typedRegion
												.getOffset() - styleRange.start;
										break;
									} else if (styleRange.start >= typedRegion
											.getOffset()
											&& styleRange.start
													+ styleRange.length <= typedRegion
													.getOffset()
													+ typedRegion.getLength()) {
										styleRange = null;
										break;
									} else if (styleRange.start > typedRegion
											.getOffset()
											&& styleRange.start < typedRegion
													.getOffset()
													+ typedRegion.getLength()
											&& styleRange.start
													+ styleRange.length > typedRegion
													.getOffset()
													+ typedRegion.getLength()) {
										styleRange.length = styleRange.start
												+ styleRange.length
												- (typedRegion.getOffset() + typedRegion
														.getLength());
										styleRange.start = typedRegion
												.getOffset()
												+ typedRegion.getLength();

									} else if (styleRange.start
											+ styleRange.length < typedRegion
											.getOffset()) {
										break;
									}
								}
								if (styleRange != null) {
									fRangeSet.add(styleRange);
								}
							}
						}
					}

				} else {
					int oldLength = 0;
					for (Iterator iterator = presentation
							.getAllStyleRangeIterator(); iterator.hasNext();) {
						iterator.next();
						oldLength++;
					}
					IPresentationRepairer repairer = getRepairer(r.getType());
					if (repairer != null)
						repairer.createPresentation(presentation, r);
					int newLength = 0;
					for (Iterator iterator = presentation
							.getAllStyleRangeIterator(); iterator.hasNext();) {
						StyleRange styleRange = (StyleRange) iterator.next();
						oldLength--;
						if (oldLength < 0) {
							fRangeSet.add(styleRange);
						}
					}

				}
			}
			List<StyleRange> fRanges = new ArrayList<StyleRange>();
			for (Iterator iterator = fRangeSet.iterator(); iterator.hasNext();) {
				StyleRange styleRange = (StyleRange) iterator.next();
				fRanges.add(styleRange);
			}
			Collections.sort(fRanges, new Comparator<StyleRange>() {

				public int compare(StyleRange o1, StyleRange o2) {
					return o1.start - o2.start;
				}

			});
			presentation = new TextPresentation(damage, fRanges.size());
			for (Iterator iterator = fRanges.iterator(); iterator.hasNext();) {
				StyleRange styleRange = (StyleRange) iterator.next();
				if (styleRange.start + styleRange.length <= damage.getOffset()) {
					continue;
				} else if (styleRange.start <= damage.getOffset()
						&& styleRange.start + styleRange.length > damage
								.getOffset()
						&& styleRange.start + styleRange.length <= damage
								.getOffset() + validLength) {
					int rangeEnd = styleRange.start + styleRange.length;
					styleRange.start = damage.getOffset();
					styleRange.length = rangeEnd - damage.getOffset();
					presentation.addStyleRange(styleRange);
				} else if (styleRange.start >= damage.getOffset()
						&& styleRange.start < damage.getOffset() + validLength
						&& styleRange.start + styleRange.length > damage
								.getOffset() + validLength) {
					styleRange.length = damage.getOffset() + validLength
							- styleRange.start;
					presentation.addStyleRange(styleRange);
				} else if (styleRange.start >= damage.getOffset()
						&& styleRange.start + styleRange.length <= damage
								.getOffset() + validLength) {
					presentation.addStyleRange(styleRange);
				}

			}
			return presentation;

		} catch (BadLocationException x) {
			/* ignored in platform PresentationReconciler, too */
		}
		return null;
	}

	// protected TextPresentation createPresentation(IRegion damage,
	// IDocument document) {
	// try {
	// int validLength = Math.min(damage.getLength(), document.getLength()
	// - damage.getOffset());
	//
	// if (fRepairers == null || fRepairers.isEmpty()) {
	// TextPresentation presentation = new TextPresentation(damage, 1);
	// presentation.setDefaultStyleRange(new StyleRange(damage
	// .getOffset(), validLength, null, null));
	// return presentation;
	// }
	//
	// TextPresentation presentation = new TextPresentation(damage, 1000);
	//
	// ITypedRegion[] partitions = TextUtilities.computePartitioning(
	// document, getDocumentPartitioning(), damage.getOffset(),
	// validLength, false);
	// for (int i = 0; i < partitions.length; i++) {
	// ITypedRegion r = partitions[i];
	// IPresentationRepairer repairer = getRepairer(r.getType());
	// if (repairer != null)
	// repairer.createPresentation(presentation, r);
	// }
	//
	// return presentation;
	//
	// } catch (BadLocationException x) {
	// /* ignored in platform PresentationReconciler, too */
	// }
	//
	// return null;
	// }

	private int getFollowingCSS(ITypedRegion[] partitions, int i) {
		int result = i;
		i++;
		for (; i < partitions.length; i = i + 2) {
			if (i + 1 < partitions.length
					&& partitions[i].getType().equals(
							PHPPartitionTypes.PHP_DEFAULT)
					&& partitions[i + 1].getType().equals(CSS_STYLE)) {
				result = result + 2;
			} else {
				break;
			}
		}
		return result;
	}

	public void setRepairer(IPresentationRepairer repairer, String contentType) {
		super.setRepairer(repairer, contentType);
		Assert.isNotNull(contentType);

		if (fRepairers == null)
			fRepairers = new HashMap();

		if (repairer == null)
			fRepairers.remove(contentType);
		else
			fRepairers.put(contentType, repairer);
	}
}
