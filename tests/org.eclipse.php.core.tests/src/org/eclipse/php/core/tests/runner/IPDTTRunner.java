package org.eclipse.php.core.tests.runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IPDTTRunner {
	/**
	 * Annotate any public static Bundle getBundle() method
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface Context {

	}
}
