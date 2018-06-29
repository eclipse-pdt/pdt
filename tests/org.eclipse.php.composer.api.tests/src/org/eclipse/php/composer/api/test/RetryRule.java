/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.test;

import java.net.ConnectException;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryRule implements TestRule {
	private final int sleep;
	private final int attempts;

	public RetryRule(int sleep, int attempts) {
		assert (attempts > 0);
		this.sleep = sleep;
		this.attempts = attempts;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {
			private int attempt = 0;

			@Override
			public void evaluate() throws Throwable {
				Throwable lastError;
				do {
					try {
						base.evaluate();
						lastError = null;
					} catch (ConnectException e) {
						lastError = e;
						Thread.sleep(sleep); // wait a moment
					}
				} while (attempt++ < attempts);
				if (lastError != null) {
					throw lastError;
				}
			}
		};
	}

}
