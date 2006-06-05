/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.core.communication;

import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.php.core.util.BlockingQueue;
import org.eclipse.php.core.util.collections.IntHashtable;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.DebugMessagesRegistry;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugRequestHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

public class DebuggerCommunicationKit {

	private CommunicationClient communicationClient;
	private CommunicationAdministrator administrator;

	protected ServerSocket serverSocket;
	protected Socket communicationSocket;

	protected DataInputStream in;
	protected DataOutputStream out;

	private IntHashtable requestsTable;
	private IntHashtable responseTable;
	private Hashtable responseHandlers;

	private InputManager inputManager;
	private InputMessageHandler inputMessageHandler;
	private ConnectionCreator connectionCreator;

	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	private DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

	private int lastRequestID = 1000;
	protected int port = -1;
	protected int peerResponseTimeout = 10000; // 10 seconds.

	private Object CONNECTION_CLOSED_MSG = new Object();

	protected boolean isDebugMode = System.getProperty("loggingDebug") != null;
	protected boolean isInitialized = false;

	/**
	 * Creates new DefaultCommunicationKit
	 */
	public DebuggerCommunicationKit() {

		requestsTable = new IntHashtable();
		responseTable = new IntHashtable();
		responseHandlers = new Hashtable();
	}

	public int getPeerResponseTimeout() {
		return peerResponseTimeout;
	}

	public void setPeerResponseTimeout(int peerResponseTimeout) {
		this.peerResponseTimeout = peerResponseTimeout;
	}

	public void setCommunicationClient(CommunicationClient client) {
		communicationClient = client;
	}

	public CommunicationClient getCommunicationClient() {
		return communicationClient;
	}

	public void setCommunicationAdministrator(CommunicationAdministrator admin) {
		administrator = admin;
	}

	public CommunicationAdministrator getCommunicationAdministrator() {
		return administrator;
	}

	/**
	 * deliver a Notification.
	 *
	 * @param msg The delivered notification message.
	 */
	public void sendNotification(Object msg) {
		try {
			synchronized (byteArrayOutputStream) {
				byteArrayOutputStream.reset();
				((IDebugMessage) msg).serialize(dataOutputStream);
				synchronized (out) {
					out.writeInt(byteArrayOutputStream.size());
					if (isDebugMode) {
						System.out.println("sending notification request size=" + byteArrayOutputStream.size());
					}
					byteArrayOutputStream.writeTo(out);
					out.flush();
				}
			}
		} catch (Exception exc) {
			PHPDebugPlugin.log(exc);
		}

	}

	/**
	 * deliver a request & wait for a response
	 *
	 * @param request The delivered Request message.
	 * @return A response for the delivered request.
	 */
	public Object sendRequest(Object request) throws Exception {
		try {
			IDebugRequestMessage theMsg = (IDebugRequestMessage) request;
			synchronized (byteArrayOutputStream) {
				byteArrayOutputStream.reset();
				theMsg.setID(lastRequestID++);
				theMsg.serialize(dataOutputStream);

				int messageSize = byteArrayOutputStream.size();

				if (isDebugMode) {
					System.out.println("sending message request size=" + messageSize);
				}
				synchronized (out) {
					requestsTable.put(theMsg.getID(), theMsg);
					//System.out.println("Request table has " +requestsTable.size() +" items");
					out.writeInt(messageSize);
					byteArrayOutputStream.writeTo(out);
					out.flush();
				}
			}

			IDebugResponseMessage response = null;
			while (response == null && isConnected()) {
				synchronized (request) {
					response = (IDebugResponseMessage) responseTable.remove(theMsg.getID());
					if (response == null) {
						request.wait(peerResponseTimeout);
					} else if (isDebugMode) {
						System.out.println("waiting for response " + response.getID());
					}
				}
				if (response == null) {
					response = (IDebugResponseMessage) responseTable.remove(theMsg.getID());
				}

				// if the responce is null. it meens that there is no answer from the server.
				// This can be because on the peerResponseTimeout.
				if (response == null && isConnected()) {
					if (isDebugMode) {
						System.out.println("Communication problems");
					}
					// Handle time out will stop the communication if need to stop.
					//System.out.println("handleto");
					handlePeerResponseTimeout();
					if (!isConnected())
						break;
					//System.out.println("rewaiting");
				}
			}
			if (isDebugMode) {
				System.out.println("response received by client: " + response);
			}
			return response;

		} catch (IOException exc) { // retrun null for any exception
			//Log.writeLog("No Connection");
			//Log.writeLog(exc);

		} catch (InterruptedException exc) {// retrun null for any exception
			//Log.writeLog(exc);
		}
		return null;
	}

	public void sendRequest(Object request, ResponseHandler responseHandler) {
		//Integer msgId = new Integer(lastRequestID++);
		int msgId = lastRequestID++;
		IDebugRequestMessage theMsg = (IDebugRequestMessage) request;
		try {
			//System.out.println("sending:"+request);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
			//msg.setID(msgId.intValue());
			theMsg.setID(msgId);
			theMsg.serialize(dataOutputStream);

			int messageSize = byteArrayOutputStream.size();
			if (isDebugMode) {
				System.out.println("sending message request size=" + messageSize);
			}

			synchronized (out) {
				requestsTable.put(msgId, request);
				responseHandlers.put(new Integer(msgId), responseHandler);
				out.writeInt(messageSize);
				byteArrayOutputStream.writeTo(out);
				out.flush();
			}
		} catch (Exception exc) { // retrun null for any exception
			System.out.println("Exception for request no." + theMsg.getType() + exc.toString());
			responseHandler.handleResponse(request, null);
			responseHandlers.remove(new Integer(msgId));
		}
	}

	/**
	 * In case of Multiple bindings which means that the two instances of the ide
	 * are trying to connect on the same port we'll let the client handle it.
	 */
	protected void handleMultipleBindings() {
		//Log.writeLog("handleBindException");
		//cleanSocket();
		getCommunicationClient().handleMultipleBindings();
	}

	/**
	 * In case of a peerResponseTimeout exception we let the communication client handle the
	 * logic of the peerResponseTimeout.
	 */
	private void handlePeerResponseTimeout() {
		getCommunicationClient().handlePeerResponseTimeout();
	}

	/**
	 * Opens a thread that waits for a connection while the current thread continues
	 * without interuption.
	 */
	public void openConnection() {
		openConnection(port, this.peerResponseTimeout);
	}

	/**
	 * Opens a thread that waits for a connection while the current thread continues
	 * without interuption.
	 *
	 * @param port the port number which the thread will wait on.
	 */
	public void openConnection(int port) {
		openConnection(port, this.peerResponseTimeout);
	}

	public void openConnection(int port, int peerResponseTimeout) {
		if (isConnectionExists(port, peerResponseTimeout)) {
			return;
		}

		this.peerResponseTimeout = peerResponseTimeout;
		cleanSocket();
		if (connectionCreator != null && connectionCreator.isAlive()) {
			connectionCreator.exit();
		}

		connectionCreator = new ConnectionCreator(port);

		//System.out.println("ConnectionCreator("+port+")");
	}

	/**
	 * Returns true if the connection exists. 
	 * The basic check is done on the port, the peer response timeout and on the connection thread itself.
	 * @param port
	 * @param peerResponseTimeout
	 * @return True, iff the connection is alive.
	 */
	protected boolean isConnectionExists(int port, int peerResponseTimeout) {
		return (this.port == port && this.peerResponseTimeout == peerResponseTimeout && connectionCreator != null && connectionCreator.isAlive());
	}

	public int getPort() {
		try {
			return serverSocket.getLocalPort();
		} catch (Exception exc) {
			return -1;
		}
	}

	/**
	 * Closes the connection.
	 * Destroys the server socket without cleaning the the request/response tables.
	 * The cleaning will be done by InputMessageHandler on termination.
	 */
	public synchronized void closeConnection() {
        Logger.debugMSG("DebuggerCommunicationKit: Starting closeConnection");
		cleanSocket();

        Logger.debugMSG("DebuggerCommunicationKit: connectionCreator exit");
        if (connectionCreator != null && connectionCreator.isAlive()) {
            connectionCreator.exit();
        }
        
        Logger.debugMSG("DebuggerCommunicationKit: closing server socket");
        if (serverSocket != null) {
            try {
                if (!serverSocket.isClosed())
                    serverSocket.close();
            } catch (Exception exc) {
                PHPDebugPlugin.log(exc);
            }
            serverSocket = null;
        } 
		
		// TODO - Check: Commented out - Causing for the second launch of debug to hang
		
/*        Logger.debugMSG("DebuggerCommunicationKit: Terminating inputManager");
		if (inputManager != null && inputManager.isAlive()) {
			inputManager.terminate();
		}
        Logger.debugMSG("DebuggerCommunicationKit: Terminating inputMessageHandler");
		if (inputMessageHandler != null && inputMessageHandler.isAlive()) {
			inputMessageHandler.terminate();
		} */

	}

	/**
	 * This method Waits for the peer to connect which means that the current thread
	 * waits for the connection and only then initializes the input manager and the
	 * input message handler threads.
	 *
	 * @param port the port integer which the socket waits on.
	 */
	public void waitForConnection(int port) {
		try {
			cleanSocket();
			this.checkServerSocket(port);
			Socket communicationSocket = serverSocket.accept();
			communicationSocket.setTcpNoDelay(true);
			DataInputStream in = new DataInputStream(communicationSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(communicationSocket.getOutputStream());
			restartInputMessageHandler(out);
			restartInputManager(in);
			DebuggerCommunicationKit.this.in = in;
			DebuggerCommunicationKit.this.out = out;
			DebuggerCommunicationKit.this.communicationSocket = communicationSocket;
			isInitialized = true;
		} catch (Exception exc) {
			PHPDebugPlugin.log(exc);
		}
	}

	/**
	 * Connect to a peer
	 */
	public void connectToPeer(String host, int port) {
		try {
			cleanSocket();
			Socket communicationSocket = createSocket(host, port);
			DataInputStream in = new DataInputStream(communicationSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(communicationSocket.getOutputStream());
			restartInputMessageHandler(out);
			restartInputManager(in);
			DebuggerCommunicationKit.this.in = in;
			DebuggerCommunicationKit.this.out = out;
			DebuggerCommunicationKit.this.communicationSocket = communicationSocket;
			isInitialized = true;
			//isConnected = true;
		} catch (Exception exc) {
			PHPDebugPlugin.log(exc);
		}
	}

	/**
	 * This method initializes the input message handler thread.
	 * If it exists it reinitializes the thread.
	 */
	protected void restartInputMessageHandler(DataOutputStream out) {
		if (inputMessageHandler == null) {
			inputMessageHandler = new InputMessageHandler(out);
		} else {
			//inputMessageHandler.start(out,true);
			inputMessageHandler.waitForStart(out, true);
		}
	}

	/**
	 * This method initializes the input manager thread.
	 * If it exists it reinitializes the thread.
	 */
	protected void restartInputManager(DataInputStream in) {
		try {
			if (inputManager == null) {
				inputManager = new InputManager(in);
			} else {
				//inputManager.start(in,out);
				inputManager.waitForStart(in);
			}
		} catch (Exception exc) {
			PHPDebugPlugin.log(exc);
		}
	}

	public boolean isConnected() {
		if (in != null) {
			try {
				in.available();
				return true;
			} catch (IOException ioe) {
			}
		}
		return false;
	}

	/**
	 * Destroys the socket and initialize it to null.
	 */
	protected void cleanSocket() {
		if (!isInitialized) {
			return;
		}
		//        System.out.println("close connection");
		if (communicationSocket != null) {
			try {
				communicationSocket.shutdownInput();
			} catch (Exception exc) {
			}

			try {
				communicationSocket.shutdownOutput();
			} catch (Exception exc) {
			}
		}
		if (out != null) {
			try {
				synchronized (out) {
					out.close();
				}
			} catch (Exception exc) {
			}
		}

		if (communicationSocket != null) {
			try {
				communicationSocket.close();
			} catch (Exception exc) {
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (Exception exc) {
			}
		}

		communicationSocket = null;
		in = null;
        if (out != null) {
            synchronized (out) {
                out = null;
            }
        }
	}

	/**
	 * This thread manages the Communication initiated by the peer.
	 * All the messages that arrive form the peer are read by the ImputManager.
	 * The InputManager will then handle the message by the message type.
	 * It the message type is a {@link IDebugRequestMessage}. The input manager will also write out
	 * the {@link IDebugResponseMessage}
	 */
	private class InputManager implements Runnable {

		private DataInputStream in;

		private boolean inWork = false;
		private boolean isAlive = true;
		private Thread theThread;
		private Object READY_FOR_RESTART_LOCK = new Object();

		/**
		 * Create an InputManager in a separate thread.
		 */
		InputManager(DataInputStream in) {
			this.in = in;
			inWork = true;
			isAlive = true;
			theThread = new Thread(this);
			theThread.start();
			//Log.writeLog("Input Manager is started");
		}

		/*
		 * This method will block the reader until the end of processing of the current
		 * message.
		 */
		public synchronized void start(DataInputStream in) {
			stop();
			this.in = in;
			inWork = true;

			// on start we do not need to interrupt since we are sure
			// the thread is in wait on this.
			notifyAll();
		}

		public boolean isAlive() {
			return isAlive;
		}

		public void waitForStart(DataInputStream in) {
			if (inWork) {
				synchronized (READY_FOR_RESTART_LOCK) {
					try {
						READY_FOR_RESTART_LOCK.wait();
					} catch (InterruptedException e) {
					}
				}
			}
			start(in);
		}

		/**
		 * Stop this thread from reading from the socket without killing it.
		 * This method will block the reader until the end of processing of the current
		 * message.
		 */
		public synchronized void stop() {
			if (!inWork)
				return;
			//System.out.println("InputManager "+"stop");
			//Thread.currentThread().dumpStack();
			inWork = false;
			isAlive = true;
			theThread.interrupt();
		}

		/**
		 * Terminate this thread
		 */
		public synchronized void terminate() {
			//System.out.println("InputManager "+"stop");
			//Thread.currentThread().dumpStack();
			inWork = false;
			isAlive = false;
//            System.out.println("InputManager shutdown");
//			shutDown();
//            System.out.println("theThread.interrupt()");
			theThread.interrupt();
		}

		/**
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			while (isAlive) {

				while (!inWork && isAlive) {
					try {
						synchronized (this) {
							synchronized (READY_FOR_RESTART_LOCK) {
								READY_FOR_RESTART_LOCK.notify(); // release one thread.

							}
							wait();
							// This way we will start working untill the connectionCreator
							// hase finished updating the in + out.
							synchronized (in) {
							}
						}
					} catch (InterruptedException e) {
					}
				}

				try {

					if (!isAlive) {
						break;
					}

					// reads the length
					int num = in.readInt();
					if (isDebugMode) {
						System.out.println("recieved message size = " + num);
					}
					if (num < 0) {
						shutDown();
						System.out.println("Socket error (length is negative): possibly Server is SSL, Client is not.");
					}
					// We have a new message. process it !!.
					// This part is synchronized since we do not want the thread to be stoped
					// when in processing of a message.
					synchronized (this) {

						int messageType = in.readShort();
						if (isDebugMode) {
							System.out.println("message type=" + messageType);
						}

						IDebugMessage message = DebugMessagesRegistry.getMessage(messageType);
						if (message == null) {
							System.out.println("Cannot create relevant message for type=" + messageType);
						}

						// handle the message
						if (message instanceof IDebugNotificationMessage) {
							if (isDebugMode) {
								System.out.println("Starting to read notification ");
							}
							message.deserialize(in);
							if (isDebugMode) {
								System.out.println("End reading of notification " + message);
							}
							//getCommunicationClient().handleNotification((Notification)message);
							//PUT NOTIFICATION TO NOTIFICATION QUEUE
							inputMessageHandler.queueIn(message);
						} else if (message instanceof IDebugResponseMessage) {
							if (isDebugMode) {
								System.out.println("Starting to read response");
							}
							message.deserialize(in);
							int idd = ((IDebugResponseMessage) message).getID();
							if (isDebugMode) {
								System.out.println("End reading of response " + message);
							}
							//responseQueue.queueIn(message);
							//INSERT RESPONSE TO TABLE AND RELEASE THE THREAD WAITING FOR THE REQUEST
							ResponseHandler handler = (ResponseHandler) responseHandlers.get(new Integer(idd)); // find the handler.
							if (handler == null) {
								responseTable.put(/*requestId*/idd, message);
								IDebugRequestMessage req = (IDebugRequestMessage) requestsTable.remove(idd); // find the request.
								synchronized (req) {
									req.notifyAll(); // Notify the response is here.
								}
							} else {
								inputMessageHandler.queueIn(message);
							}
						} else if (message instanceof IDebugRequestMessage) { // this is a request.
							if (isDebugMode) {
								System.out.println("Starting to read request");
							}
							message.deserialize(in);
							if (isDebugMode) {
								System.out.println("End reading of request " + message);
							}
							//Response response =  getCommunicationClient().handleRequest((Request)message);
							inputMessageHandler.queueIn(message);
						}
					} // end of synchronized part.

				} catch (EOFException exc) {
					shutDown();
				} catch (SocketException exc) {
					shutDown();
				} catch (IOException exc) {
					PHPDebugPlugin.log(exc);
					shutDown();
				} catch (Exception exc) {
					PHPDebugPlugin.log(exc);
				}
			}
		}

		private void shutDown() {
			inWork = false;
			cleanSocket();
			inputMessageHandler.connectionClosed();
		}
	}

	/**
	 * Creates a Socket with the given host and port.
	 * 
	 * @param host 	The host to connect
	 * @param port	The port to connect
	 * @return	A Socket
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	protected Socket createSocket(String host, int port) throws UnknownHostException, IOException {
		return new Socket(host, port);
	}
	
	/**
	 * Returns a ServerSocket that was newly created, returns an active server socket if no
	 * change is needed. 
	 * @param port The port to listen on.
	 * @return A ServerSocket that can accept connections on the given port.
	 */
	protected ServerSocket checkServerSocket(int port) {
		if (serverSocket != null && (port != this.port)) {
			try {
				serverSocket.close();
			} catch (Exception exc) {
			}
			serverSocket = null;
		}

		if (serverSocket == null && port >= 0) {
			try {
				serverSocket = new ServerSocket(port);
				// this is the correct place to set the port.
				// since this function can run in a thread (ConnectionCreator)
				this.port = port;
			} catch (BindException exc) {
				handleMultipleBindings();
			} catch (Exception e) {
				PHPDebugPlugin.log(e);
				// there is some problem with the creation of the socket (wrong font?).
				// let the function return null;
			}
		}

		return serverSocket;
	}

	/**
	 * The connection creator is user when we do not want to block the thread
	 * that asked for the creation of the connection.
	 */
	private class ConnectionCreator implements Runnable {

		private boolean isAlive = true;
		private Thread theThread;
		int port;

		public ConnectionCreator(int port) {
			theThread = new Thread(this);
			this.port = port;
            checkServerSocket(port);
			theThread.start();
			//System.out.println("ConnectionCreator "+this);
		}

		public synchronized void exit() {
			isAlive = false;
			theThread.interrupt();
		}

		public boolean isAlive() {
			boolean rv = theThread.isAlive();
			rv = rv && isAlive;
			return rv;
		}

		public void run() {
			while (isAlive) {
				try {
					ServerSocket serverSocket;
					synchronized (this) {
						serverSocket = checkServerSocket(port);
						if (serverSocket == null)
							isAlive = false;
					}

					if (!isAlive)
						break;
					//Log.writeLog("Waiting for connection");
					//System.out.println("ConnectionCreator waiting "+this);

					//wait for another connection to be established
					Socket communicationSocket = serverSocket.accept();
					communicationSocket.setTcpNoDelay(true);
					//System.out.println("New Script is waiting in debug queue");
					synchronized (this) { // can not exit while this block
						try {
							//System.out.println("ConnectionCreator not waiting "+this);
							DataInputStream in = new DataInputStream(communicationSocket.getInputStream());
							DataOutputStream out = new DataOutputStream(communicationSocket.getOutputStream());
							synchronized (in) {
								//These 2 methods are blocked until the threads
								//will stop working and will be free to handle
								//another connection.
								restartInputMessageHandler(out);
								restartInputManager(in);
								DebuggerCommunicationKit.this.in = in;
								DebuggerCommunicationKit.this.out = out;
								DebuggerCommunicationKit.this.communicationSocket = communicationSocket;
								isInitialized = true;
							}
							//                            System.out.println("open connection");
							getCommunicationAdministrator().connectionEstablished();
						} catch (Exception exc) {
							PHPDebugPlugin.log(exc);
						}
					}
				} catch (SocketException exc) {
					//Log.writeLog(exc);
				} catch (Exception exc) {
					if (isAlive)
						PHPDebugPlugin.log(exc);
					// else exit the thread by exit().
				}
			} // end loop

			//this.isAlive = false;
			//System.out.println("ConnectionCreator EXIT "+this);
		}
	}

	/**
	 * This thread handles the requests and notification that are inserted into the
	 * Queues by the InputManager thread.
	 */
	private class InputMessageHandler implements Runnable {

		private BlockingQueue inputMessageQueue = new BlockingQueue(100);

		private boolean shouldExit = false;
		private boolean isAlive = true;
		private boolean inWork = true;
		private Thread theThread;
		private DataOutputStream out;
		private Object STOP_MSG = new Object();

		private ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		private DataOutputStream outArray = new DataOutputStream(byteArray);

		private Object WAIT = new Object();
		private Object READY_FOR_RESTART_LOCK = new Object();

		public InputMessageHandler(DataOutputStream out) {
			this.out = out;
			isAlive = true;
			inWork = true;
			shouldExit = false;
			theThread = new Thread(this);
			// This makes the printings much faster.
			theThread.setPriority(1);//theThread.getPriority()+1);
			theThread.start();
		}

		/**
		 * start the thread on a new output.
		 * This will block untill end of processing of all the messages.
		 */
		public synchronized void start(DataOutputStream out, boolean clearQueue) {
			if (clearQueue)
				inputMessageQueue.clear();
			this.out = out;
			ensureStarted();
		}

		/**
		 * This function will block the calling thread untill the the InputMessageHandler
		 * becomes inactive.
		 */
		public void waitForStart(DataOutputStream out, boolean clearQueue) {
			if (inWork) {
				synchronized (READY_FOR_RESTART_LOCK) {
					try {
						READY_FOR_RESTART_LOCK.wait();
					} catch (InterruptedException e) {
					}
				}
			}
			start(out, clearQueue);
		}

		/* just make sure the thread is working */
		public synchronized void ensureStarted() {
			synchronized (WAIT) {
				inWork = true;
				isAlive = true;
				shouldExit = false;
				WAIT.notifyAll();
			}
		}

		public synchronized void stopImmediately(boolean clearQueue) {
			if (!inWork)
				return;
			inWork = false;
			isAlive = true;
			inputMessageQueue.releaseReaders();
			if (clearQueue)
				inputMessageQueue.clear();
			synchronized (WAIT) {
				WAIT.notifyAll();
			}
		}

		public void terminate() {
			inWork = false;
			isAlive = false;
			inputMessageQueue.releaseReaders();
			inputMessageQueue.clear();
			theThread.interrupt();
		}

		public boolean isAlive() {
			return isAlive;
		}

		public void queueIn(IDebugMessage m) {
			inputMessageQueue.queueIn(m);
		}

		private void queueIn(Object m) {
			inputMessageQueue.queueIn(m);
		}

		/**
		 * This method is called by the input manager so that the message handler
		 * will queueIn an internal protocal message for the closure of connection.
		 */
		public synchronized void connectionClosed() {
			ensureStarted(); // make sure the thread will process the stop message
			queueIn(CONNECTION_CLOSED_MSG);
		}

		private synchronized void resetCommunication() {
			// Now we can stop the input manager.
			if (inputManager != null)
				inputManager.stop();

			// first stop the input messages handler. So if the inputMessagesHandler
			// is sending a request the inputManager will exists to read the response.
			stopImmediately(true);

			synchronized (requestsTable) {
				Iterator i = requestsTable.values().iterator();
				while (i.hasNext()) {
					IDebugRequestMessage r = (IDebugRequestMessage) i.next();
					synchronized (r) {
						r.notifyAll();
					}
				}
			}

			requestsTable.clear();
			responseTable.clear();
			responseHandlers.clear();
		}

		public void run() {

			while (isAlive) {

				while (!inWork && isAlive) {
					try {
						synchronized (WAIT) {
							synchronized (READY_FOR_RESTART_LOCK) {
								READY_FOR_RESTART_LOCK.notify(); // release one thread.
							}
							WAIT.wait();
						}

					} catch (InterruptedException e) {
					}
				}

				if (!isAlive)
					break;

				try {

					Object newInputMessage = inputMessageQueue.queueOut();

					//System.out.println("InputMessageHandler handle: " + newInputMessage);

					// do not stop untill the message is processed.
					synchronized (this) {
						try {
							if (newInputMessage instanceof IDebugNotificationMessage) {
								//System.out.println("Processing notification:"+ newInputMessage);
								getCommunicationClient().handleNotification(newInputMessage);
							} else if (newInputMessage instanceof IDebugRequestMessage) {

								int reqId = ((IDebugRequestMessage) newInputMessage).getID();
								IDebugMessageHandler requestHandler = DebugMessagesRegistry.getHandler((IDebugRequestMessage) newInputMessage);

								if (requestHandler instanceof IDebugRequestHandler) {
									requestHandler.handle((IDebugRequestMessage) newInputMessage);
									IDebugResponseMessage response = ((IDebugRequestHandler) requestHandler).getResponseMessage();

									//Log.writeLog("Client Sending response: " +response);
									//ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
									//DataOutputStream outArray = new DataOutputStream (byteArray );
									byteArray.reset();
									response.serialize(outArray);
									if (isDebugMode) {
										System.out.println("sending message size=" + byteArray.size());
									}
									synchronized (out) {
										out.writeInt(byteArray.size());
										byteArray.writeTo(out);
										out.flush();
									}
								} else {
									// error, we could not find the relevant request message handler for the recieved message.
								}
							} else if (newInputMessage instanceof IDebugResponseMessage) {
								IDebugResponseMessage r = (IDebugResponseMessage) newInputMessage;
								//                                Object requestId = new Integer(r.getID()); // take the req id from the response.
								int requestId = r.getID(); // take the req id from the response.
								IDebugRequestMessage req = (IDebugRequestMessage) requestsTable.remove(requestId); // find the request.
								ResponseHandler handler = (ResponseHandler) responseHandlers.remove(new Integer(requestId)); // find the handler.
								handler.handleResponse(req, r);
							} else if (newInputMessage == STOP_MSG) {
								synchronized (STOP_MSG) {
									inWork = false;
									STOP_MSG.notifyAll();
									if (shouldExit) {
										isAlive = false;
										inputMessageQueue.releaseReaders(); // why do we need this??
										//notifyAll();
									}
								}
							} else if (newInputMessage == CONNECTION_CLOSED_MSG) {
								handleConnectionClosed();
								//inWork = false;
							}
							//else if (newInputMessage == MULTIPLE_BINDINGS_MSG){
							//    handleMultipleBindingsMessage();
							//}
							//else if (newInputMessage == PEER_RESPONSE_TIMEOUT_MSG){
							//    handlePeerResponseTimeoutMessage();
							//}

						} catch (Exception exc) { // error processing the current message.
							PHPDebugPlugin.log(exc);
						}
					}

				} catch (Exception exc) {
					//inWork = false;
					PHPDebugPlugin.log(exc);
				}
			}
		}

		private void handleConnectionClosed() {
			resetCommunication();
			getCommunicationAdministrator().connectionClosed();
		}
	}

}
