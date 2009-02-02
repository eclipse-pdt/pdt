/**
 * 
 */
package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;

import org.eclipse.dltk.internal.ui.scriptview.LayoutActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.ui.actions.GenerateActionGroup;
import org.eclipse.ui.actions.ActionGroup;

/**
 * @author nir.c
 * PHPExplorerActionGroup class extends DLTK's ScriptExplorerActionGroup, 
 * His purpose is to add "include path" actions to the popUp menu, similar to "build path" actions.
 * 
 */
public class PHPExplorerActionGroup extends ScriptExplorerActionGroup {

	public PHPExplorerActionGroup(ScriptExplorerPart part) {
		super(part);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.actions.CompositeActionGroup#setGroups(org.eclipse.ui.actions.ActionGroup[])
	 */
	@Override
	protected void setGroups(ActionGroup[] groups) {
		// aggregate the PHP Explorer actions
		final ArrayList<ActionGroup> filtered = new ArrayList<ActionGroup>(groups.length - 1);
		for (int i = 0; i < groups.length; i++) {
			if (!(groups[i] instanceof LayoutActionGroup || groups[i] instanceof GenerateActionGroup)) {
				filtered.add(groups[i]);
			}
		}
		filtered.add(new GenerateIncludePathActionGroup(getPart()));

		super.setGroups(filtered.toArray(new ActionGroup[filtered.size()]));
	}

}
