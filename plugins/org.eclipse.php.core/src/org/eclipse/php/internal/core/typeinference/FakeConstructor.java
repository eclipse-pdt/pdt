package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class FakeConstructor extends FakeMethod {
	/**
	 * the code assist if happens in the class itself
	 * for example 
	 * class Foo{
	 * 		function Foo($name){}
	 * 		function clone(){return new Foo("foo")}
	 * }
	 */
	private boolean isEnclosingClass;
	public FakeConstructor(ModelElement parent, String name, int offset,
			int length, int nameOffset, int nameLength, boolean isEnclosingClass) {
		super(parent, name, offset, length, nameOffset, nameLength);
		this.isEnclosingClass = isEnclosingClass;
	}

	public boolean isConstructor() throws ModelException {
		return true;
	}

	public boolean isEnclosingClass() {
		return isEnclosingClass;
	}

	public void setEnclosingClass(boolean isEnclosingClass) {
		this.isEnclosingClass = isEnclosingClass;
	}

	public static FakeMethod createFakeMethod(IMethod ctor, IType type, boolean isEnclosingClass) {
		ISourceRange sourceRange;
		try {
			sourceRange = type.getSourceRange();
			FakeMethod ctorMethod = new FakeConstructor((ModelElement) type,
					type.getElementName(), sourceRange.getOffset(), sourceRange
							.getLength(), sourceRange.getOffset(), sourceRange
							.getLength(),isEnclosingClass);
			if(ctor != null){
				ctorMethod.setParameters(ctor.getParameters());
			}

			return ctorMethod;
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return null;
	}
}
