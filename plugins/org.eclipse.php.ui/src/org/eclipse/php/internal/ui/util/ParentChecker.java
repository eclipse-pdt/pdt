package org.eclipse.php.internal.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;


public class ParentChecker {
	private IResource[] fResources;
	private PHPCodeData[] fPHPElements;

	public ParentChecker(IResource[] resources, PHPCodeData[] phpElements) {
		fResources= resources;
		fPHPElements= phpElements;
	}
	
	public boolean haveCommonParent() {
		return getCommonParent() != null;
	}
	
	public Object getCommonParent(){
		if (fPHPElements.length == 0 && fResources.length == 0)
			return null;
		if (! resourcesHaveCommonParent() || ! phpElementsHaveCommonParent())
			return null;
		if (fPHPElements.length == 0){
			IResource commonResourceParent= getCommonResourceParent();
			PHPCodeData convertedToPHP=  getPHPCodeData(commonResourceParent);
			if (convertedToPHP != null  )
				return convertedToPHP;
			else
				return commonResourceParent;
		}
		if (fResources.length == 0)
			return getCommonPHPElementParent();
			
		IResource commonResourceParent= getCommonResourceParent();
		PHPCodeData commonPHPElementParent= getCommonPHPElementParent();
		PHPCodeData convertedToPHP= getPHPCodeData(commonResourceParent);
		if (convertedToPHP == null || 
			! commonPHPElementParent.equals(convertedToPHP))
			return null;
		return commonPHPElementParent;	
	}
	
	private static PHPCodeData getPHPCodeData(IResource resource)
	{
	 return PHPWorkspaceModelManager.getInstance().getModelForFile((IFile)resource,false);
	}

	private PHPCodeData getCommonPHPElementParent() {
		return fPHPElements[0].getContainer();
	}

	private IResource getCommonResourceParent() {
		return fResources[0].getParent();
	}

	private boolean phpElementsHaveCommonParent() {
		if (fPHPElements.length == 0)
			return true;
		PHPCodeData firstParent= fPHPElements[0].getContainer();
		for (int i= 1; i < fPHPElements.length; i++) {
			if (! firstParent.equals(fPHPElements[i].getContainer()))
				return false;
		}
		return true;
	}

	private boolean resourcesHaveCommonParent() {
		if (fResources.length == 0)
			return true;
		IResource firstParent= fResources[0].getParent();
		for (int i= 1; i < fResources.length; i++) {
			if (! firstParent.equals(fResources[i].getParent()))
				return false;
		}
		return true;
	}
	
	public IResource[] getResources(){
		return fResources;
	}		
		
	public PHPCodeData[] getPHPElements(){
		return fPHPElements;
	}

	public void removeElementsWithAncestorsOnList(boolean removeOnlyPHPElements) {
		if (! removeOnlyPHPElements){
			removeResourcesDescendantsOfResources();
			removeResourcesDescendantsOfPHPElements();
		}
		removePHPElementsDescendantsOfPHPElements();
//		removePHPElementsChildrenOfResources(); //this case is covered by removeUnconfirmedArchives
	}
				
	private void removeResourcesDescendantsOfPHPElements() {
		List subResources= new ArrayList(3);
		for (int i= 0; i < fResources.length; i++) {
			IResource subResource= fResources[i];
			for (int j= 0; j < fPHPElements.length; j++) {
				PHPCodeData superElements= fPHPElements[j];
				if (isDescendantOf(subResource, superElements))
					subResources.add(subResource);
			}
		}
		removeFromSetToDelete((IResource[]) subResources.toArray(new IResource[subResources.size()]));
	}

	private void removePHPElementsDescendantsOfPHPElements() {
		List subElements= new ArrayList(3);
		for (int i= 0; i < fPHPElements.length; i++) {
			PHPCodeData subElement= fPHPElements[i];
			for (int j= 0; j < fPHPElements.length; j++) {
				PHPCodeData superElement= fPHPElements[j];
				if (isDescendantOf(subElement, superElement))
					subElements.add(subElement);
			}
		}
		removeFromSetToDelete((PHPCodeData[]) subElements.toArray(new PHPCodeData[subElements.size()]));
	}

	private void removeResourcesDescendantsOfResources() {
		List subResources= new ArrayList(3);
		for (int i= 0; i < fResources.length; i++) {
			IResource subResource= fResources[i];
			for (int j= 0; j < fResources.length; j++) {
				IResource superResource= fResources[j];
				if (isDescendantOf(subResource, superResource))
					subResources.add(subResource);
			}
		}
		removeFromSetToDelete((IResource[]) subResources.toArray(new IResource[subResources.size()]));
	}

	public static boolean isDescendantOf(IResource subResource, PHPCodeData superElement) {
		IResource parent= subResource.getParent();
		while(parent != null){
			PHPCodeData el= getPHPCodeData(parent);
			if (el != null   && el.equals(superElement))
				return true;
			parent= parent.getParent();
		}
		return false;
	}

	public static boolean isDescendantOf(PHPCodeData subElement, PHPCodeData superElement) {
		if (subElement.equals(superElement))
			return false;
		PHPCodeData parent= subElement.getContainer();
		while(parent != null){
			if (parent.equals(superElement))
				return true;
			parent= parent.getContainer();
		}
		return false;
	}

	public static boolean isDescendantOf(IResource subResource, IResource superResource) {
		return ! subResource.equals(superResource) && superResource.getFullPath().isPrefixOf(subResource.getFullPath());
	}

	private void removeFromSetToDelete(IResource[] resourcesToNotDelete) {
		fResources= CollectionUtils.setMinus(fResources, resourcesToNotDelete);
	}
	
	private void removeFromSetToDelete(PHPCodeData[] elementsToNotDelete) {
		fPHPElements= CollectionUtils.setMinus(fPHPElements, elementsToNotDelete);
	}
}
