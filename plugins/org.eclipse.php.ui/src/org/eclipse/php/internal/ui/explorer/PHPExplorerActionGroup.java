/**
 * 
 */
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
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
		// TODO Auto-generated method stub
		super.setGroups(groups);

		ActionGroup includePathActionGroup = new GenerateIncludePathActionGroup(getPart());
		addGroup(includePathActionGroup);

	}

}
