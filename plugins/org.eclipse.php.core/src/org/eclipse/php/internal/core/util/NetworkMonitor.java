/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * Utility class for monitoring the network addresses of underlying workstation.
 * The network addresses detection is triggered in a separate job right after
 * the instantiation of a class and is performed only once for an instance
 * lifetime. Clients may also use this class to verify provided network
 * addresses validity by means of existence in detected addresses.
 * 
 * @author Bartlomiej Laczkowski
 */
public class NetworkMonitor {

	private Inet4Address publicAddress;
	private List<Inet4Address> privateAddresses;
	private List<Inet4Address> allAddresses;
	private final CountDownLatch latch;
	private final Validator validator;
	private final Detector detector;

	public NetworkMonitor() {
		validator = new Validator();
		detector = new Detector();
		latch = new CountDownLatch(1);
		// Start address detection immediately
		detector.schedule();
	}

	/**
	 * Network addresses validation process listener.
	 */
	public static interface IHostsValidationListener {

		/**
		 * Notifies about the results of validation process by means of the list
		 * of invalid addresses (the ones that could not be found in the list of
		 * addresses detected by the monitor).
		 * 
		 * @param invalidAddresses
		 */
		void validated(List<String> invalidAddresses);

	}

	private class Detector extends Job {

		public Detector() {
			super(""); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
			addJobChangeListener(new JobChangeAdapter() {
				public void done(IJobChangeEvent event) {
					// Signal that addresses have been collected
					latch.countDown();
					removeJobChangeListener(this);
				};
			});
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			publicAddress = NetworkUtil.getPublicAddress();
			privateAddresses = new ArrayList<Inet4Address>();
			privateAddresses.addAll(NetworkUtil.getPrivateAddresses());
			allAddresses = new ArrayList<Inet4Address>();
			if (publicAddress != null)
				allAddresses.add(publicAddress);
			allAddresses.addAll(privateAddresses);
			allAddresses.add(NetworkUtil.LOCALHOST);
			return Status.OK_STATUS;
		}

	}

	private class Validator extends AbstractDeferredJob {

		private String[] addresses;
		private IHostsValidationListener[] validationListeners;

		public Validator() {
			super("", 200); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			try {
				// Wait for addresses to be collected
				latch.await();
			} catch (InterruptedException e) {
				return Status.CANCEL_STATUS;
			}
			List<String> invalid = new ArrayList<String>();
			for (String clientHost : addresses) {
				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;
				if (!NetworkUtil.isIPv4Address(clientHost) && !clientHost.equalsIgnoreCase("localhost")) { //$NON-NLS-1$
					// Mark invalid if it is not a literal IP address
					invalid.add(clientHost);
					continue;
				}
				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;
				Inet4Address clientHostAddress;
				try {
					clientHostAddress = (Inet4Address) InetAddress.getByName(clientHost);
				} catch (UnknownHostException e) {
					invalid.add(clientHost);
					continue;
				}
				boolean isInvalid = true;
				if (clientHostAddress != null) {
					if (NetworkUtil.isLoopbackAddress(clientHostAddress))
						// Local addresses are always valid
						continue;
					for (Inet4Address address : allAddresses) {
						if (clientHostAddress.getHostAddress().equals(address.getHostAddress())) {
							isInvalid = false;
							break;
						}
					}
				}
				if (isInvalid)
					invalid.add(clientHost);
			}
			for (IHostsValidationListener validationListener : validationListeners)
				validationListener.validated(invalid);
			return monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
		}

		void validate(String[] addresses, IHostsValidationListener[] validationListeners) {
			this.addresses = addresses;
			this.validationListeners = validationListeners;
			defer();
		}

	}

	/**
	 * Validates the list of provided addresses by means of their existence in
	 * the list of detected ones.
	 * 
	 * @param addresses
	 * @param validationListener
	 */
	public void validate(String[] addresses, IHostsValidationListener[] validationListeners) {
		validator.validate(addresses, validationListeners);
	}

	/**
	 * Returns the list of all detected network addresses.
	 * 
	 * @return list of all detected network addresses
	 */
	public List<Inet4Address> getAllAddresses() {
		try {
			// Wait for addresses to be collected
			latch.await();
		} catch (InterruptedException e) {
			return new ArrayList<Inet4Address>();
		}
		return allAddresses;
	}

	/**
	 * Returns the list of detected private network addresses.
	 * 
	 * @return list of detected private network addresses
	 */
	public List<Inet4Address> getPrivateAddresses() {
		try {
			// Wait for addresses to be collected
			latch.await();
		} catch (InterruptedException e) {
			return new ArrayList<Inet4Address>();
		}
		return privateAddresses;
	}

	/**
	 * Returns detected public address.
	 * 
	 * @return detected public address or <code>null</code> if no address could
	 *         be detected
	 */
	public Inet4Address getPublicAddress() {
		try {
			// Wait for addresses to be collected
			latch.await();
		} catch (InterruptedException e) {
			return null;
		}
		return publicAddress;
	}

}
