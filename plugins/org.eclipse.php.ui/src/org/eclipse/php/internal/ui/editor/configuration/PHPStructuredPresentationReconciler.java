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
	private static final String CSS_STYLE = "org.eclipse.wst.css.STYLE"; //$NON-NLS-1$
	private static final String JS_SCRIPT = "org.eclipse.wst.html.SCRIPT"; //$NON-NLS-1$
	private Map fRepairers;
	private static final Set<String> fTypeSet = new HashSet<String>();
	static {
		fTypeSet.add(CSS_STYLE);
		fTypeSet.add(JS_SCRIPT);
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
					jumpto = getFollowingCSS(wholePartitions, i,
							originalRegion.getType());
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

			// TextPresentation presentation = new TextPresentation(damage,
			// 1000);

			ITypedRegion[] partitions = TextUtilities.computePartitioning(
					document, getDocumentPartitioning(), damage.getOffset(),
					validLength, false);

			if (containSpecialType(partitions)) {
				// when modify editor content the damage region is not equal to
				// document's region,so we need to adjust the damage region's
				// start and length
				if (partitions != null && partitions.length > 0) {
					int start = damage.getOffset();
					int length = validLength;
					ITypedRegion[] wholePartitions = TextUtilities
							.computePartitioning(document,
									getDocumentPartitioning(), 0,
									document.getLength(), false);
					// determine start
					ITypedRegion startTypedRegion = partitions[0];
					ITypedRegion endTypedRegion = partitions[partitions.length - 1];
					if (PHPPartitionTypes.PHP_DEFAULT.equals(startTypedRegion
							.getType()) && partitions.length >= 2) {
						startTypedRegion = partitions[1];
					}
					ITypedRegion newRegion = getWholeRegion(wholePartitions,
							startTypedRegion);
					if (newRegion != null) {
						start = newRegion.getOffset();
						length = endTypedRegion.getOffset()
								+ endTypedRegion.getLength() - start;
					}
					if (PHPPartitionTypes.PHP_DEFAULT.equals(endTypedRegion
							.getType()) && partitions.length >= 2) {
						endTypedRegion = partitions[partitions.length - 2];
					}
					newRegion = getWholeRegion(wholePartitions, endTypedRegion);
					if (newRegion != null) {
						length = newRegion.getOffset() + newRegion.getLength()
								- start;
					}
					if (start != damage.getOffset() || length != validLength) {
						partitions = TextUtilities.computePartitioning(
								document, getDocumentPartitioning(), start,
								length, false);
					}

				}

				List<StyleRange> fRangeSet = new LinkedList<StyleRange>();

				int jumpto = -1;
				for (int i = 0; i < partitions.length; i++) {
					ITypedRegion r = partitions[i];
					if (fTypeSet.contains(r.getType())
							&& (i + 2 < partitions.length)
							&& PHPPartitionTypes.PHP_DEFAULT
									.equals(partitions[i + 1].getType())
							&& r.getType().equals(partitions[i + 2].getType())) {
						if (i > jumpto) {
							jumpto = getFollowingCSS(partitions, i, r.getType());
							r = new SimpleStructuredTypedRegion(r.getOffset(),
									partitions[jumpto].getOffset()
											+ partitions[jumpto].getLength()
											- r.getOffset(), r.getType());
							IPresentationRepairer repairer = getRepairer(r
									.getType());
							if (repairer != null) {
								TextPresentation presentation = new TextPresentation(
										damage, 1000);
								repairer.createPresentation(presentation, r);
								for (Iterator iterator = presentation
										.getAllStyleRangeIterator(); iterator
										.hasNext();) {
									StyleRange styleRange = (StyleRange) iterator
											.next();
									// the styleRange's scope may be out of
									// the
									// region see
									// https://bugs.eclipse.org/bugs/attachment.cgi?id=179715
									if (styleRange.start < r.getOffset()
											|| (styleRange.start
													+ styleRange.length > r
													.getOffset()
													+ r.getLength())) {
										continue;
									}

									for (int j = i + 1; j < jumpto; j = j + 2) {
										ITypedRegion typedRegion = partitions[j];
										if (styleRange.start < typedRegion
												.getOffset()
												&& styleRange.start
														+ styleRange.length > typedRegion
														.getOffset()
														+ typedRegion
																.getLength()) {
											int end = styleRange.start
													+ styleRange.length;
											styleRange.length = typedRegion
													.getOffset()
													- styleRange.start;
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
													.getOffset()
													- styleRange.start;
											break;
										} else if (styleRange.start >= typedRegion
												.getOffset()
												&& styleRange.start
														+ styleRange.length <= typedRegion
														.getOffset()
														+ typedRegion
																.getLength()) {
											styleRange = null;
											break;
										} else if (styleRange.start > typedRegion
												.getOffset()
												&& styleRange.start < typedRegion
														.getOffset()
														+ typedRegion
																.getLength()
												&& styleRange.start
														+ styleRange.length > typedRegion
														.getOffset()
														+ typedRegion
																.getLength()) {
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
						if (i > jumpto
								|| i < jumpto
								&& !r.getType().equals(
										partitions[jumpto].getType())) {// jumpto
																		// partition
																		// has
																		// been
																		// added
							IPresentationRepairer repairer = getRepairer(r
									.getType());
							if (repairer != null) {
								TextPresentation presentation = new TextPresentation(
										damage, 1000);
								repairer.createPresentation(presentation, r);
								for (Iterator iterator = presentation
										.getAllStyleRangeIterator(); iterator
										.hasNext();) {
									StyleRange styleRange = (StyleRange) iterator
											.next();
									fRangeSet.add(styleRange);
								}
							}
						}

					}
				}
				if (fRangeSet.isEmpty()) {
					return null;
				}
				Collections.sort(fRangeSet, new Comparator<StyleRange>() {

					public int compare(StyleRange o1, StyleRange o2) {
						return o1.start - o2.start;
					}

				});
				List<StyleRange> fRanges = new ArrayList<StyleRange>();
				StyleRange[] rangeArray = fRangeSet
						.toArray(new StyleRange[fRangeSet.size()]);
				StyleRange lastRange = rangeArray[0];
				fRanges.add(lastRange);
				for (int i = 1; i < rangeArray.length; i++) {
					StyleRange styleRange = rangeArray[i];
					// do not add duplicate ranges
					if (styleRange.start == lastRange.start
							&& styleRange.length == lastRange.length) {
						continue;
					} else {
						fRanges.add(styleRange);
						lastRange = styleRange;
					}
				}
				TextPresentation presentation = new TextPresentation(damage,
						1000);
				presentation = new TextPresentation(damage, fRanges.size());
				for (Iterator iterator = fRanges.iterator(); iterator.hasNext();) {
					StyleRange styleRange = (StyleRange) iterator.next();
					if (styleRange.start + styleRange.length <= damage
							.getOffset()) {
						continue;
					} else if (styleRange.start <= damage.getOffset()
							&& styleRange.start + styleRange.length > damage
									.getOffset()
							&& styleRange.start + styleRange.length <= damage
									.getOffset() + validLength) {
						int rangeEnd = styleRange.start + styleRange.length;
						styleRange.start = damage.getOffset();
						styleRange.length = rangeEnd - damage.getOffset();
						addStyleRange(presentation, styleRange);
					} else if (styleRange.start >= damage.getOffset()
							&& styleRange.start < damage.getOffset()
									+ validLength
							&& styleRange.start + styleRange.length > damage
									.getOffset() + validLength) {
						styleRange.length = damage.getOffset() + validLength
								- styleRange.start;
						addStyleRange(presentation, styleRange);
					} else if (styleRange.start >= damage.getOffset()
							&& styleRange.start + styleRange.length <= damage
									.getOffset() + validLength) {
						addStyleRange(presentation, styleRange);
					}

				}
				return presentation;
			} else {
				TextPresentation presentation = new TextPresentation(damage,
						1000);

				List<ITypedRegion> damagedPartitions = findDamagedPartitions(
						partitions, damage);

				// performance optimisation: if only PHPScriptRegion has been
				// damaged and not fully reparsed we can try to create
				// presentation for updated tokens only.
				if (damagedPartitions.size() == 1) {
					ITypedRegion r = damagedPartitions.get(0);
					IPresentationRepairer repairer = getRepairer(damagedPartitions
							.get(0).getType());
					if (repairer != null) {
						if (r.getType().equals(PHPPartitionTypes.PHP_DEFAULT)) {
							if (repairer instanceof StructuredDocumentDamagerRepairer) {
								TextPresentation newPresentation = ((StructuredDocumentDamagerRepairer) repairer)
										.getPresentation(r, damage);
								if (newPresentation != null) {
									presentation = newPresentation;
								}
							}
						}
						repairer.createPresentation(presentation, r);
					}
				} else {
					for (int i = 0; i < damagedPartitions.size(); i++) {
						ITypedRegion r = damagedPartitions.get(i);
						IPresentationRepairer repairer = getRepairer(r
								.getType());
						if (repairer != null)
							repairer.createPresentation(presentation, r);
					}
				}

				// OLD CODE
				// for (int i = 0; i < partitions.length; i++) {
				// ITypedRegion r = partitions[i];
				// IPresentationRepairer repairer = getRepairer(r.getType());
				// if (repairer != null)
				// repairer.createPresentation(presentation, r);
				// }
				return presentation;
			}

		} catch (BadLocationException x) {
			/* ignored in platform PresentationReconciler, too */
		}
		return null;
	}

	private List<ITypedRegion> findDamagedPartitions(ITypedRegion[] partitions,
			IRegion damage) {
		List<ITypedRegion> damagedPartitions = new ArrayList<ITypedRegion>();
		int damageEnd = damage.getOffset() + damage.getLength();
		for (int i = 0; i < partitions.length; i++) {
			ITypedRegion p = partitions[i];
			int pEnd = p.getOffset() + p.getLength();
			// damage starts within current partition
			if (damage.getOffset() >= p.getOffset()
					&& damage.getOffset() <= pEnd) {
				damagedPartitions.add(p);
				if (damageEnd < pEnd) {
					// damage covers only this one partition
					break;
				}
			}
			// damages starts in previous partition and ends here
			else if (damage.getOffset() < p.getOffset() && damageEnd <= pEnd) {
				damagedPartitions.add(p);
				break;
			}
			// damage starts in previous partition and ends in next one
			else if (damage.getOffset() < p.getOffset() && damageEnd > pEnd) {
				damagedPartitions.add(p);
			}
		}
		return damagedPartitions;
	}

	private boolean containSpecialType(ITypedRegion[] partitions) {
		for (int i = 0; i < partitions.length; i++) {
			ITypedRegion r = partitions[i];
			if (fTypeSet.contains(r.getType())) {
				return true;
			}
		}
		return false;
	}

	private void addStyleRange(TextPresentation presentation,
			StyleRange styleRange) {
		if (styleRange.length > 0) {
			presentation.addStyleRange(styleRange);
		}
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

	private int getFollowingCSS(ITypedRegion[] partitions, int i, String type) {
		int result = i;
		i++;
		for (; i < partitions.length; i = i + 2) {
			if (i + 1 < partitions.length
					&& partitions[i].getType().equals(
							PHPPartitionTypes.PHP_DEFAULT)
					&& partitions[i + 1].getType().equals(type)) {
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
