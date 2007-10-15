/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceInit;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.AutoPathMapper;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpoint;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpPreferences;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.extensionpoints.FilenameMapperRegistry;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.extensionpoints.IFileMapper;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.Base64;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpCommand;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpUtils;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSession;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBGpTarget extends DBGpElement implements IDebugTarget, IStep,
      IBreakpointManagerListener, DBGpSessionListener {

   // used to identify this debug target with the associated
   // script being debugged.
   private String sessionID;

   private String ideKey;

   private boolean webLaunch = false;

   // required for EXE target support
   private IProcess process;

   // required for Web target support
   private IWebBrowser browser;

   private String stopDebugURL;

   // debug target state
   private volatile int targetState;

   private static final int STATE_CREATE = 0; // target creation

   private static final int STATE_INIT_SESSION_WAIT = 1; // waiting for 1st session

   private static final int STATE_STARTED_SUSPENDED = 2; // suspended

   private static final int STATE_STARTED_RUNNING = 3; // running

   private static final int STATE_STARTED_SESSION_WAIT = 4; // web launch waiting for next session

   private static final int STATE_TERMINATING = 5; // ASync stop request made

   private static final int STATE_TERMINATED = 6; // terminated

   private static final int STATE_DISCONNECTED = 7; // disconnected

   // the script being run, or initial web script
   private String scriptName;

   // launch object
   private ILaunch launch;

   // threads
   private DBGpThread langThread;

   private IThread[] allThreads;

   // stack frame tracking
   private boolean refreshStackFrames;

   private int currentStackLevel;

   private IStackFrame[] stackFrames;

   private DBGpBreakpointFacade bpFacade;   
   
   // superglobal variable support, these are immutable, they cannot be changed
   private IVariable[] superGlobalVars;

   // used to cache dbgp commands to program while it is running
   private Vector DBGpCmdQueue = new Vector();

   // dbgp session support
   private volatile DBGpSession session;

   private DBGpPreferences sessionPreferences;

   private Object sessionMutex = new Object();

   private TimedEvent te = new TimedEvent();
   
   // debug config settings
   private boolean stopAtStart;

   private boolean asyncSupported;

   private boolean stepping;

   private int maxChildren = 0;
   
   private AutoPathMapper autoMapper = null;
   
   
   /**
    * Base constructor
    * 
    */
   private DBGpTarget() {
      super(null);
      setState(STATE_CREATE);
      ideKey = DBGpSessionHandler.getInstance().getIDEKey();
      allThreads = new IThread[0]; // needs to be defined when target is added to launch
      fireCreationEvent();
   }

   /**
    * Target that handles PHP Exe launches
    * 
    * @param launch
    * @param process
    * @param scriptName
    * @param stopAtStart
    * @throws CoreException
    */
   public DBGpTarget(ILaunch launch, String scriptName, String ideKey,
         String sessionID, boolean stopAtStart) throws CoreException {
      this();
      this.stopAtStart = stopAtStart;
      this.launch = launch;
      this.scriptName = scriptName;
      this.ideKey = ideKey;
      this.webLaunch = false;
      this.sessionID = sessionID;
      this.process = null; // this will be set later
      this.stopDebugURL = null; // never set
      this.browser = null; // never set
   }

   /**
    * target that handles invocation via a web browser
    * 
    * @param launch
    * @param workspaceRelativeScript
    * @param stopDebugURL
    * @param sessionID
    * @param stopAtStart
    */
   public DBGpTarget(ILaunch launch, String workspaceRelativeScript, String stopDebugURL,
         String ideKey, boolean stopAtStart, IWebBrowser browser) {
      this();
      this.stopAtStart = stopAtStart;
      this.launch = launch;
      this.scriptName = workspaceRelativeScript;
      this.ideKey = ideKey;
      this.webLaunch = true;
      this.sessionID = null; // in the web launch we have no need for the session ID.
      this.stopDebugURL = stopDebugURL;
      this.browser = browser;
      this.process = null; // no process indicates a web launch
   }

   /**
    * wait for the initial dbgp session to be established
    * 
    * @param launchMonitor
    */
   public void waitForInitialSession(DBGpBreakpointFacade facade, DBGpPreferences sessionPrefs,
         IProgressMonitor launchMonitor) {
      bpFacade = facade;
      sessionPreferences = sessionPrefs;
      setState(STATE_INIT_SESSION_WAIT);

      try {
         while (session == null && !launch.isTerminated() && !isTerminating()
               && !launchMonitor.isCanceled()) {

            // if we got here then session has not been updated
            // by the other thread yet, so wait. We wait for
            // an event or a timeout. Even if we timeout we could
            // still get the session before we re-enter the loop.
//            te.waitForEvent(DBGpSessionHandler.getInstance().getTimeout());
            te.waitForEvent(XDebugPreferenceInit.getTimeoutDefault());
         }

         if (session != null && session.isActive()) {
            if (!isTerminating() && !launch.isTerminated() && !launchMonitor.isCanceled()) {
               langThread = new DBGpThread(this);
               allThreads = new IThread[] {langThread};
               langThread.fireCreationEvent();
               DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
               autoMapper = new AutoPathMapper();
               autoMapper.init(scriptName, session.getInitialScript());
               initiateSession();
            }
            else {
               session.endSession();
               terminateDebugTarget(true);
            }
         }
         else {
            terminateDebugTarget(true);
         }
      }
      catch (Exception e) {
         // cannot proceed any further as we will never be able to get a
         // session. The exception doesn't need logging.
         terminateDebugTarget(true);
      }
   }

   /**
    * initiate the session, this cannot be called from the DBGpSession
    * response handler thread as we install breakpoints synchronously and block
    * waiting for the response thread to pick them up, so we will deadlock
    * 
    */
   private void initiateSession() {
      if (targetState != STATE_INIT_SESSION_WAIT && targetState != STATE_STARTED_SESSION_WAIT) {
         DBGpLogger.logWarning("initiateSession in Wrong State: " + targetState, this, null);
      }
      refreshStackFrames = true;
      superGlobalVars = null;
      session.startSession();
      // we are effectively suspended once the session has handshaked until we
      // run
      setState(STATE_STARTED_SUSPENDED);

      try {
         negotiateDBGpFeatures();    	  
         loadPredefinedBreakpoints();
         if (!stopAtStart) {
            //resume();

            if (session != null) {
               session.sendAsyncCmd(DBGpCommand.run);
               setState(STATE_STARTED_RUNNING);
            }

         }
         else {
            // try to say we have suspended, then do a step_into
            suspended(DebugEvent.BREAKPOINT);
            stepInto();
         }
      }
      catch (DebugException e) {
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDebugTarget#getProcess()
    */
   public IProcess getProcess() {
      return process;
   }

   /**
    * set the process
    * 
    * @param proc
    */
   public void setProcess(IProcess proc) {
      process = proc;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDebugTarget#getThreads()
    */
   public IThread[] getThreads() throws DebugException {
      return allThreads;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDebugTarget#hasThreads()
    */
   public boolean hasThreads() throws DebugException {
      boolean hasThreads = allThreads.length > 0;
      return hasThreads;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDebugTarget#getName()
    */
   public String getName() throws DebugException {
	  if (webLaunch) {
		  return "Remote Launch";
	  }
	  else {
		  if (scriptName == null) {
            return "Unknown PHP Program";
         }
      }
      return scriptName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDebugElement#getDebugTarget()
    */
   public IDebugTarget getDebugTarget() {
      return this;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDebugElement#getLaunch()
    */
   public ILaunch getLaunch() {
      return launch;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
    */
   public boolean canTerminate() {

      // can only terminate if we have not terminated (allow for terminating
      // state as well to be safe).
      boolean canTerminate = (STATE_TERMINATED != targetState && STATE_CREATE != targetState && STATE_DISCONNECTED != targetState);
      if (process != null) {
         canTerminate = canTerminate && process.canTerminate();
      }
      return canTerminate;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
    */

   public boolean isTerminated() {
      boolean terminated = (STATE_TERMINATED == targetState);
      if (process != null) {
         return process.isTerminated();
      }
      else {
         return terminated;
      }
   }

   /**
    * returns if we have terminated or in the process of terminating
    * 
    * @return
    */
   public boolean isTerminating() {
      boolean terminating = (targetState == STATE_TERMINATED) || (targetState == STATE_TERMINATING);
      return terminating;
   }

   /**
    * returns is the debug target has started and is not terminating
    * 
    * @return
    */
   public boolean hasStarted() {
      boolean started = (STATE_STARTED_RUNNING == targetState)
            || (STATE_STARTED_SESSION_WAIT == targetState)
            || (STATE_STARTED_SUSPENDED == targetState);
      return started;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ITerminate#terminate()
    */
   public void terminate() throws DebugException {
      if (isTerminating()) {
         // just in case we had some problem sending the stop command, allow
         // terminate to still work.
         if (session == null && STATE_TERMINATING == targetState) {
            terminateDebugTarget(true);
         }
         return;
      }

      // we won't accept any more sessions, so stop listening
      DBGpSessionHandler.getInstance().removeSessionListener(this);

      if (STATE_STARTED_SUSPENDED == targetState) {
         // we are suspended, so we can send the stop request to do a clean
         // termination
         synchronized (sessionMutex) {
            if (session != null) {
               setState(STATE_TERMINATING);
               session.sendAsyncCmd(DBGpCommand.stop);
               // we don't terminateDebugTarget here, we wait for the
               // response from the program under debug
            }
            else {
               terminateDebugTarget(true);
            }
         }
      }
      else {
         // we cannot terminate cleanly so we terminate as best we can
         terminateDebugTarget(true);
         if (isWebLaunch()) {
            // We were a web launch so we must now send the stop url
            sendStopDebugURL();
         }
      }
   }

   /**
    * 
    *
    */
   private void sendStopDebugURL() {
      if (stopDebugURL == null) {
         return;
      }

      try {
         if (browser != null) {
            DBGpLogger.debug("browser is not null, sending " + stopDebugURL);
            browser.openURL(new URL(stopDebugURL));
         }
         else {
            DBGpUtils.openInternalBrowserView(stopDebugURL);
         }
      }
      catch (PartInitException e) {
         DBGpLogger.logException("Failed to send stop URL: " + stopDebugURL, this, e);
      }
      catch (MalformedURLException e) {
         // this should never happen, if it does I want it in the log
         // as something will need to be fixed
         DBGpLogger.logException(null, this, e);
      }
   }

   /**
    * Called by DBGpSession when the session terminates. The session
    * terminates if we explicitly stop the session, or the script completes
    * process then it will be a remote server version, so we don't want to
    * terminate the debug target, but the session will have ended. This target
    * needs to either be terminated manually or wait for another debug session
    * to be attached.
    */
   public void sessionEnded() {
      synchronized (sessionMutex) {
         session = null;
      }
      if (STATE_TERMINATING == targetState) {
         // we are terminating, if we are a web launch, we need to issue the
         // stop URL
         if (isWebLaunch()) {
            sendStopDebugURL();
         }
         terminateDebugTarget(true);
      }
      else {

         // ok, the session ended and we are not terminating, if we are a web
         // launch the we wait for the next session, otherwise terminate the
         // debugtarget
         if (isWebLaunch()) {
            setState(STATE_STARTED_SESSION_WAIT);
            stepping = false;
            langThread.setBreakpoints(null);
         }
         else {
            terminateDebugTarget(true);
         }
      }
   }

   /**
    * Terminate this debug target, either because we
    * are terminating the thing being debugged or we
    * are disconnecting
    * @param isTerminate if we are terminating
    */
   public void terminateDebugTarget(boolean isTerminate) {
      // check we haven't already terminated
      if (STATE_TERMINATED != targetState) {
         DBGpSessionHandler.getInstance().removeSessionListener(this);
         DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);

         if (isTerminate && STATE_STARTED_RUNNING == targetState) {
            setState(STATE_TERMINATING);
            if (process != null) {
               try {

                  // terminate the process even if Eclipse may also
                  // attempt this depending on what the user selected
                  // to terminate.
                  process.terminate();
               }
               catch (DebugException e) {
                  // ignore any exceptions here
               }
            }
            else {
               if (session != null) {
                  session.endSession();
               }
            }
         }

         setState(STATE_TERMINATED);
         if (session != null) {
            session.endSession();
         }
         if (langThread != null) {
            langThread.fireTerminateEvent();
         }
         stepping = false;
         fireTerminateEvent();
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#supportsStorageRetrieval()
    */
   public boolean supportsStorageRetrieval() {
      return false;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#getMemoryBlock(long,
    *      long)
    */
   public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
      return null;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
    */
   public boolean canResume() {
      return !isTerminated() && isSuspended();
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
    */
   public boolean canSuspend() {
      return asyncSupported;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
    */
   public boolean isSuspended() {
      boolean isSuspended = (STATE_STARTED_SUSPENDED == targetState);
      return isSuspended;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IStep#canStepInto()
    */
   public boolean canStepInto() {
      return isSuspended();
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IStep#canStepOver()
    */
   public boolean canStepOver() {
      return isSuspended();
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IStep#canStepReturn()
    */
   public boolean canStepReturn() {
      // can only step return if there is a method above it, ie there is
      // at least one stack frame above the current stack frame.   
      try {
         if (isSuspended() && getStackFrames().length > 1) {
            return true;
         }
      }
      catch (DebugException e) {
         // ignore the exception if it fails then we cannot stepReturn
      }
      return false;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IStep#isStepping()
    */
   public boolean isStepping() {
      return stepping;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IStep#stepInto()
    */
   public void stepInto() throws DebugException {
      stepping = true;
      resumed(DebugEvent.STEP_INTO);
      session.sendAsyncCmd(DBGpCommand.stepInto);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IStep#stepOver()
    */
   public void stepOver() throws DebugException {
      stepping = true;
      resumed(DebugEvent.STEP_OVER);
      session.sendAsyncCmd(DBGpCommand.stepOver);

   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IStep#stepReturn()
    */
   public void stepReturn() throws DebugException {
      stepping = true;
      resumed(DebugEvent.STEP_RETURN);
      session.sendAsyncCmd(DBGpCommand.StepOut);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ISuspendResume#resume()
    */
   public void resume() throws DebugException {
      stepping = false;
      resumed(DebugEvent.RESUME);
      // bug in eclipse 3.2. When I issue a resume when a disconnect
      // is done, the resume button can still be pressed which
      // wouldn't work as the session has gone.
      if (session != null) {
         session.sendAsyncCmd(DBGpCommand.run);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
    */
   public void suspend() throws DebugException {
      session.sendAsyncCmd(DBGpCommand.suspend);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDisconnect#canDisconnect()
    */
   public boolean canDisconnect() {
      boolean canDisconnect = STATE_STARTED_RUNNING == targetState
            || STATE_STARTED_SUSPENDED == targetState;
      return canDisconnect;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDisconnect#disconnect()
    */
   public void disconnect() throws DebugException {
      if (isTerminating()) {
         return;
      }
      if (STATE_STARTED_RUNNING == targetState || STATE_STARTED_SUSPENDED == targetState) {
         // we are in the middle of a debug session, single or multi
         // makes no difference, we should stop it
         setState(STATE_DISCONNECTED);
         if (session != null) {
            if (process != null) {
               session.sendAsyncCmd(DBGpCommand.detach);
               terminateDebugTarget(false);
            }
            else {
               // try this first
               session.sendSyncCmd(DBGpCommand.stop);
               stepping = false;
               langThread.setBreakpoints(null);
               setState(STATE_STARTED_SESSION_WAIT);
               resumed(DebugEvent.RESUME);
            }
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDisconnect#isDisconnected()
    */
   public boolean isDisconnected() {
      return STATE_DISCONNECTED == targetState || STATE_TERMINATED == targetState;
   }

   /**
    * fire a resume event
    * 
    * @param detail
    */
   private void resumed(int detail) {
      setState(STATE_STARTED_RUNNING);
      fireResumeEvent(detail);
      langThread.fireResumeEvent(detail);
   }

   /**
    * fire a suspend event
    * 
    * @param detail
    */
   public void suspended(int detail) {
      setState(STATE_STARTED_SUSPENDED);
      processQueuedBpCmds();
      refreshStackFrames = true;
      fireSuspendEvent(detail);
      langThread.fireSuspendEvent(detail);
   }

   /**
    * setup DBGp specific features, or get information about environment
    */
   private void negotiateDBGpFeatures() {
      DBGpResponse resp;
      resp = session.sendSyncCmd(DBGpCommand.featureSet, "-n show_hidden -v 1");
      // check the responses, but keep going.
      DBGpUtils.isGoodDBGpResponse(this, resp);
      resp = session.sendSyncCmd(DBGpCommand.featureSet, "-n max_depth -v " + getMaxDepth());
      DBGpUtils.isGoodDBGpResponse(this, resp);
      resp = session.sendSyncCmd(DBGpCommand.featureGet, "-n max_children");
      if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
         Node child = resp.getParentNode().getFirstChild();
         if (child != null) {
            String data = child.getNodeValue();
            try {
               maxChildren = Integer.parseInt(data);
            }
            catch (NumberFormatException nfe) {
               maxChildren = -1;
            }
         }
      }
      resp = session.sendSyncCmd(DBGpCommand.featureGet, "-n encoding");
      if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
         Node child = resp.getParentNode().getFirstChild();
         if (child != null) {
            String data = child.getNodeValue();
            try {
               "abcdefg".getBytes(data);
               session.setSessionEncoding(data);
            }
            catch (UnsupportedEncodingException uee) {
               DBGpLogger.logWarning("encoding from debug engine invalid", this, uee);
            }
         }
      }

      asyncSupported = false;
      resp = session.sendSyncCmd(DBGpCommand.featureGet, "-n supports_async");
      if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
         Node child = resp.getParentNode().getFirstChild();
         String supported = DBGpResponse.getAttribute(child, "supported");
         if (supported != null && supported.equals("1")) {
            asyncSupported = true;
         }
      }

      // TODO: Improvement: add debug output support for remote debugging
      /*
       * if (multiSession) { resp = session.sendSyncCmd(DBGpCommand.stdout,
       * "-c 1"); DBGpUtils.isGoodDBGpResponse(this, resp); }
       */
   }

   /**
    * Returns the current stack frames in the target.
    * 
    * @return the current stack frames in the target
    * @throws DebugException
    *             if unable to perform the request
    */
   protected synchronized IStackFrame[] getStackFrames() throws DebugException {
      /*
       * <response command="stack_get" transaction_id="transaction_id"> <stack
       * level="{NUM}" type="file|eval|?" filename="..." lineno="{NUM}"
       * where="" cmdbegin="line_number:offset" cmdend="line_number:offset"/>
       * <stack level="{NUM}" type="file|eval|?" filename="..."
       * lineno="{NUM}"> <input level="{NUM}" type="file|eval|?"
       * filename="..." lineno="{NUM}"/> </stack> </response>
       */

      // this can be called from multiple threads, as the data it manages
      // is global across the debug target, you could end up with 2 threads
      // doing this at the same time on will be getting the data and the other
      // will not and returning null as the data is not yet ready.
      if (refreshStackFrames) {
         refreshStackFrames = false;
         DBGpResponse resp = session.sendSyncCmd(DBGpCommand.stackGet);
         if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
            Node parent = resp.getParentNode();
            NodeList stackNodes = parent.getChildNodes(); // <stack>
            // entries
            stackFrames = new IStackFrame[stackNodes.getLength()];
            for (int i = 0; i < stackNodes.getLength(); i++) {
               Node stackNode = stackNodes.item(i);
               stackFrames[i] = new DBGpStackFrame(langThread, stackNode);
            }
            currentStackLevel = stackNodes.getLength() - 1;
         }
         else {
            currentStackLevel = 0;
            stackFrames = new IStackFrame[0];
         }
      }
      return stackFrames;
   }

   /**
    * get the local variables at a particular stack level.
    * Never returns null (IVariable[0]).
    * @param level
    * @return
    */
   private IVariable[] getContextLocalVars(String level) {
      DBGpResponse resp = session.sendSyncCmd(DBGpCommand.contextGet, "-d " + level);
      return parseVarResp(resp, level);
   }

   /**
    * get the super globals. never returns null (IVariable[0]).
    * @return
    */
   private IVariable[] getSuperGlobalVars() {
      if (superGlobalVars == null) {
         DBGpResponse resp = session.sendSyncCmd(DBGpCommand.contextGet, "-c 1");
         // Parse this into a variables block, switch on preload just for
         // this
         superGlobalVars = parseVarResp(resp, "-1");
      }
      return superGlobalVars;
   }

   /**
    * get all variables to be displayed. Never returns null (IVariable[0])
    * @param level
    * @return
    */
   public IVariable[] getAllVars(String level) {
      boolean getSuperGlobals = showGLobals();
      IVariable[] globals = null;
      if (getSuperGlobals) {
         globals = getSuperGlobalVars();
      }
      else {
         globals = new IVariable[0];
      }
      IVariable[] locals = getContextLocalVars(level);
      int totalLength = globals.length + locals.length;

      IVariable[] merged = new IVariable[totalLength];

      if (globals.length > 0) {
         System.arraycopy(globals, 0, merged, 0, globals.length);
      }
      if (locals.length > 0) {
         System.arraycopy(locals, 0, merged, globals.length, locals.length);
      }

      return merged;
   }

   /**
    * parse each variable request response, never returns null (IVariable[0])
    * @param resp
    * @param reportedLevel
    * @return
    */
   private IVariable[] parseVarResp(DBGpResponse resp, String reportedLevel) {
      IVariable[] variables = new IVariable[0];

      // If you cannot get a property, then a single variable is created with
      // no information as their is a child node, if there are no variables
      // this method creates a 0 size array which is good.
      if (resp.getErrorCode() == DBGpResponse.ERROR_OK) {
         Node parent = resp.getParentNode();
         NodeList properties = parent.getChildNodes();
         variables = new DBGpVariable[properties.getLength()];
         for (int i = 0; i < properties.getLength(); i++) {
            Node property = properties.item(i);
            variables[i] = new DBGpVariable((DBGpTarget)getDebugTarget(), property, reportedLevel);
         }
      }
      return variables;
   }

   /**
    * set a variable to a particular value
    * @param fullName
    * @param stackLevel
    * @param data
    * @return
    */
   public boolean setProperty(DBGpVariable var, String data) {

      // XDebug expects all data to be base64 encoded.
      String encoded = new String(Base64.encode(data.getBytes()));
      String fullName = var.getFullName();
      String stackLevel = var.getStackLevel();
      String args = "-n " + fullName + " -d " + stackLevel + " -l " + encoded.length() + " -- "
            + encoded;
      try {
         if (var.getReferenceTypeName().equals(DBGpVariable.PHP_STRING)) {
            // this ensures XDebug doesn't use eval
            args = "-t string " + args;
         }
      }
      catch (DebugException e) {

      }
      DBGpResponse resp = session.sendSyncCmd(DBGpCommand.propSet, args);
      if (resp.getTopAttribute("success").equals("1")) {
         return true;
      }
      else {
         return false;
      }
   }

   /**
    * get a variable at a particular stack level and page number
    * @param fullName
    * @param stackLevel
    * @param page
    * @return
    */
   public Node getProperty(String fullName, String stackLevel, int page) {
      String args = "-n " + fullName + " -d " + stackLevel + " -p " + page;
      if (stackLevel.equals("-1")) {
         // the following line should work but doesn't in 2.0.0rc1 of XDebug
         // args = "-n " + fullName + " -c 1 -p " + page;
         // but the following works for both rc1 and beyond so will keep it
         // like this for now.
         args = "-n " + fullName + " -d " + getCurrentStackLevel() + " -p " + page;
      }
      DBGpResponse resp = session.sendSyncCmd(DBGpCommand.propGet, args);
      return resp.getParentNode().getFirstChild();
   }

   /**
    * perform an eval request
    * @param toEval
    * @return
    */
   public Node eval(String toEval) {
      // XDebug expects all data to be base64 encoded.
      String encoded = new String(Base64.encode(toEval.getBytes()));
      String args = "-- " + encoded;
      DBGpResponse resp = session.sendSyncCmd(DBGpCommand.eval, args);
      return resp.getParentNode().getFirstChild();
   }

   /**
    * set the state of the debug target
    * @param newState
    */
   private synchronized void setState(int newState) {
      // TODO: Improvement: build a proper finite state machine with tests
      if (DBGpLogger.debugState()) {
         String newStateStr = "";
         switch (newState) {
            case STATE_CREATE:
               newStateStr = "STATE_CREATE";
               break;
            case STATE_DISCONNECTED:
               newStateStr = "STATE_DISCONNECTED";
               break;
            case STATE_INIT_SESSION_WAIT:
               newStateStr = "INIT_SESSION_WAIT";
               break;
            case STATE_STARTED_RUNNING:
               newStateStr = "STATE_STARTED_RUNNING";
               break;
            case STATE_STARTED_SESSION_WAIT:
               newStateStr = "STATE_STARTED_SESSION_WAIT";
               break;
            case STATE_STARTED_SUSPENDED:
               newStateStr = "STATE_STARTED_SUSPENDED";
               break;
            case STATE_TERMINATED:
               newStateStr = "STATE_TERMINATED";
               break;
            case STATE_TERMINATING:
               newStateStr = "STATE_TERMINATING";
               break;

         }
         DBGpLogger.debug("State Change: " + newStateStr);
      }
      targetState = newState;
   }

   /**
    * get current stack depth
    * @return
    */
   public int getCurrentStackLevel() {
      return currentStackLevel;
   }

   /*
    * get the max number of children
    */
   public int getMaxChildren() {
      return maxChildren;
   }

   // --------------------------------------------------------------------------
   // Break point support section
   // --------------------------------------------------------------------------

   public String mapInboundFileIfRequired(String decodedFile) {
      String mappedFile = decodedFile;
      if (webLaunch) {
         IFileMapper mapper = FilenameMapperRegistry.getRegistry().getActiveMapper();
         
         // if no external mapper, see if we should use the automapper.
         if (mapper == null && autoMapper.isMappingRequired()) {
        	 mapper = autoMapper;
         }
         if (mapper != null) {

            try {
               mappedFile = mapper.mapExternalFileToWorkspace(decodedFile, launch
                     .getLaunchConfiguration());
            }
            catch (Exception e) {
               // ingore any user generated exceptions
            }
         }
      }
      return mappedFile;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.IDebugTarget#supportsBreakpoint(org.eclipse.debug.core.model.IBreakpoint)
    */
   public boolean supportsBreakpoint(IBreakpoint breakpoint) {
      // cannot use this method to reject a breakpoint appearing
      // in the editor.
      return bpFacade.supportsBreakpoint(breakpoint);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.IBreakpointListener#breakpointAdded(org.eclipse.debug.core.model.IBreakpoint)
    */
   public void breakpointAdded(IBreakpoint breakpoint) {
      if (!DebugPlugin.getDefault().getBreakpointManager().isEnabled()) {
         return;
      }

      if (supportsBreakpoint(breakpoint)) {
         try {
            if (breakpoint.isEnabled()) {
               DBGpBreakpoint bp = bpFacade.createDBGpBreakpoint(breakpoint);
               if (asyncSupported || isSuspended()) {
                  // we are suspended or async mode is supported so send the
                  // breakpoint
                  if (DBGpLogger.debugBP()) {
                     DBGpLogger.debug("Breakpoint Add requested immediately");
                  }
                  sendBreakpointAddCmd(bp, false);
               }
               else {

                  // we are not suspended and async mode is not supported
                  // so if we send a breakpoint command we may not get a
                  // response at all or may get a response when the script
                  // suspends on another breakpoint which will hang the 
                  // gui if we send it synchronously.
                  // Async would require the read thread to handle
                  // the response, locate the breakpoint from the txn_id
                  // and add the id to the runtimeBreakpoint. 
                  // We cannot guarantee that the debug server will hold
                  // the request until the script suspends and even queue
                  // multiple requests, so the best bet is to 
                  // queue the requests until we suspend. 
                  if (DBGpLogger.debugBP()) {
                     DBGpLogger.debug("Breakpoint Add deferred until suspended");
                  }
                  DBGpBreakpointCmd bpSet = new DBGpBreakpointCmd(DBGpCommand.breakPointSet, bp);
                  queueBpCmd(bpSet);
               }
            }
         }
         catch (CoreException e) {
            DBGpLogger.logException("Exception adding breakpoint", this, e);
         }
      }
   }

   /**
    * create and send the breakpoint add command
    * @param bp
    * @param onResponseThread
    */
   private void sendBreakpointAddCmd(DBGpBreakpoint bp, boolean onResponseThread) {
      bp.resetConditionChanged();
      String fileName = bp.getFileName();
      int lineNumber = bp.getLineNumber();

      // create the add breakpoint command
      String debugMsg = null;
      if (DBGpLogger.debugBP()) {
         debugMsg = "adding breakpoint to file:" + fileName + ", at Line Number: " + lineNumber;
      }
      DBGpBreakpointCondition condition = new DBGpBreakpointCondition(bp);

      // if it is a weblaunch, see if we have an active mapper to map the workspace
      // file to the absolute path of the remote script.
      if (webLaunch) {
         IFileMapper mapper = FilenameMapperRegistry.getRegistry().getActiveMapper();
         if (mapper == null && autoMapper.isMappingRequired()) {
        	 mapper = autoMapper;
         }         
         if (mapper != null) {
            try {
               String mappedFileName = mapper.mapWorkspaceFileToExternal(bp.getIFile(), launch
                     .getLaunchConfiguration());
               if (mappedFileName != null) {
                  fileName = mappedFileName;
               }
            }
            catch (Exception e) {
               // protect against any exceptions in the user API.
            }
         }
      }

      String args = "-t line -f " + DBGpUtils.getFileURIString(fileName) + " -n " + lineNumber;
      if (condition.getType() == DBGpBreakpointCondition.EXPR) {
         if (debugMsg != null) {
            debugMsg += " with expression:" + condition.getExpression();
         }
         args += " -- " + new String(Base64.encode(condition.getExpression().getBytes()));
      }
      else if (condition.getType() == DBGpBreakpointCondition.HIT) {
         if (debugMsg != null) {
            debugMsg += " with hit :" + condition.getHitCondition() + condition.getHitValue();
         }
         args += " -h " + condition.getHitValue() + " -o " + condition.hitCondition;
      }
      if (debugMsg != null) {
         DBGpLogger.debug(debugMsg);
      }

      DBGpResponse resp;
      if (onResponseThread) {
         resp = session.sendSyncCmdOnResponseThread(DBGpCommand.breakPointSet, args);
      }
      else {
         resp = session.sendSyncCmd(DBGpCommand.breakPointSet, args);
      }
      if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
         /*
          * <response command="breakpoint_set"
          * transaction_id="TRANSACTION_ID" state="STATE"
          * id="BREAKPOINT_ID"/>
          */
         // TODO: note that you don't get state from XDebug even though the
         // document says so, assume optional and if not provided, it is enabled.
         String bpId = resp.getTopAttribute("id");
         // luckily even though it is a string, the XDebug implementation
         // defines the id as being a c int.
         bp.setID(Integer.parseInt(bpId));
         if (DBGpLogger.debugBP()) {
            DBGpLogger.debug("Breakpoint installed with id: " + bpId);
         }
      }
      else {
         // we have already logged the issue as an error
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.IBreakpointListener#breakpointRemoved(org.eclipse.debug.core.model.IBreakpoint,
    *      org.eclipse.core.resources.IMarkerDelta)
    */
   public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
      if (supportsBreakpoint(breakpoint)) {
         DBGpBreakpoint bp = bpFacade.createDBGpBreakpoint(breakpoint);
         if (asyncSupported || isSuspended()) {

            // aysnc mode or we are suspended so send the remove request
            if (DBGpLogger.debugBP()) {
               DBGpLogger.debug("Immediately removing of breakpoint with ID: " + bp.getID());
            }
            sendBreakpointRemoveCmd(bp, false);
         }
         else {

            // no async mode and not suspended so queue the request.
            if (DBGpLogger.debugBP()) {
               DBGpLogger.debug("Deferring Removing of breakpoint with ID: " + bp.getID());
            }
            DBGpBreakpointCmd bpRemove = new DBGpBreakpointCmd(DBGpCommand.breakPointRemove, bp);
            queueBpCmd(bpRemove);
         }

      }
   }

   /**
    * create and send the breakpoint remove command
    * @param bp
    * @param onResponseThread
    */
   private void sendBreakpointRemoveCmd(DBGpBreakpoint bp, boolean onResponseThread) {
      // we are suspended
      String args = "-d " + bp.getID();
      if (DBGpLogger.debugBP()) {
         DBGpLogger.debug("Removing breakpoint with ID: " + bp.getID());
      }
      DBGpResponse resp;
      if (onResponseThread) {
         resp = session.sendSyncCmdOnResponseThread(DBGpCommand.breakPointRemove, args);
      }
      else {
         resp = session.sendSyncCmd(DBGpCommand.breakPointRemove, args);
      }
      DBGpUtils.isGoodDBGpResponse(this, resp); // used to log the
      // result
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.IBreakpointListener#breakpointChanged(org.eclipse.debug.core.model.IBreakpoint,
    *      org.eclipse.core.resources.IMarkerDelta)
    */
   public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
      IBreakpointManager bmgr = DebugPlugin.getDefault().getBreakpointManager();
      if (!bmgr.isEnabled()) {
         return;
      }
      int deltaLNumber = delta.getAttribute(IMarker.LINE_NUMBER, 0);
      IMarker marker = breakpoint.getMarker();
      int lineNumber = marker.getAttribute(IMarker.LINE_NUMBER, 0);
      if (supportsBreakpoint(breakpoint)) {
         try {

            // did the condition change ?
            DBGpBreakpoint bp = bpFacade.createDBGpBreakpoint(breakpoint);
            if (bp.hasConditionChanged()) {
               if (DBGpLogger.debugBP()) {
                  DBGpLogger.debug("condition changed for breakpoint with ID: " + bp.getID());
               }
               bp.resetConditionChanged();
               if (breakpoint.isEnabled()) {
                  breakpointRemoved(breakpoint, null);
               }
               else {
                  return;
               }
            }

            // did the line number change ?
            if (lineNumber != deltaLNumber) {
               if (DBGpLogger.debugBP()) {
                  DBGpLogger.debug("line number changed for breakpoint with ID: " + bp.getID());
               }

               if (breakpoint.isEnabled()) {
                  breakpointRemoved(breakpoint, null);
               }
               else {
                  return;
               }
            }

            // add or remove the break point depending on whether it was
            // enabled or not
            if (breakpoint.isEnabled()) {
               breakpointAdded(breakpoint);
            }
            else {
               breakpointRemoved(breakpoint, null);
            }
         }
         catch (CoreException e) {
            DBGpLogger.logException("Exception Changing Breakpoint", this, e);
         }
      }
   }

   /**
    * Notification a breakpoint was encountered. Determine which breakpoint was
    * hit and fire a suspend event.
    * 
    * @param event
    *            debug event
    */
   public void breakpointHit(String filename, int lineno) {
      // useful method to be called by the response listener when a
      // break point has occurred
      IBreakpoint breakpoint = findBreakpointHit(filename, lineno);
      if (breakpoint != null) {
         langThread.setBreakpoints(new IBreakpoint[] {breakpoint});
      }
      else {
         // set it to an empty set as specified by the API.
         langThread.setBreakpoints(new IBreakpoint[0]);
      }

      // fire event once everything has been established
      suspended(DebugEvent.BREAKPOINT);
   }

   /**
    * find which breakpoint we have suspended at
    * @param filename
    * @param lineno
    * @return
    */
   private IBreakpoint findBreakpointHit(String filename, int lineno) {
      return bpFacade.findBreakpointHit(filename, lineno);
   }

   /**
    * setup the currently defined breakpoints before the execution of the
    * script.
    */
   private void loadPredefinedBreakpoints() {
      IBreakpointManager bmgr = DebugPlugin.getDefault().getBreakpointManager();
      if (!bmgr.isEnabled()) {
         return;
      }
      IBreakpoint[] breakpoints = bmgr.getBreakpoints(bpFacade.getBreakpointModelID());
      for (int i = 0; i < breakpoints.length; i++) {
         breakpointAdded(breakpoints[i]);
      }
   }

   /**
    * request a run to line
    * @param fileName
    * @param lineNumber
    */
   public void runToLine(IFile fileName, int lineNumber) {
      if (DBGpLogger.debugBP()) {
         DBGpLogger.debug("runtoline: " + fileName + " " + lineNumber);
      }

      if (isSuspended()) {
         try {
            IBreakpoint breakpoint = bpFacade.createRunToLineBreakpoint(fileName, lineNumber);
            IBreakpointManager bmgr = DebugPlugin.getDefault().getBreakpointManager();
            bmgr.addBreakpoint(breakpoint);
            resume();
         }
         catch (DebugException e) {
            DBGpLogger.logException("Unexpected DebugException", this, e);
         }
         catch (CoreException e) {
            DBGpLogger.logException("Unexpected CoreException", this, e);
         }
      }
   }

   /*
    * (non-Javadoc)
    * @see org.eclipse.debug.core.IBreakpointManagerListener#breakpointManagerEnablementChanged(boolean)
    */
   public void breakpointManagerEnablementChanged(boolean enabled) {
      IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(
            bpFacade.getBreakpointModelID());
      for (int i = 0; i < breakpoints.length; i++) {
         if (supportsBreakpoint(breakpoints[i])) {
            if (enabled) {
               // ((PHPLineBreakpoint)breakpoints[i]).setConditionChanged(false);
               breakpointAdded(breakpoints[i]);
            }
            else {
               breakpointRemoved(breakpoints[i], null);
            }
         }
      }
   }

   /**
    * queue a breakpoint command to be actioned once the script suspends
    * @param bpCmd
    */
   private void queueBpCmd(DBGpBreakpointCmd bpCmd) {
      // Rules are
      // 1. a remove can delete a previous add and not be queued
      // 2. all other removes must be honoured
      // 3. all adds must be honoured (they cannot delete a remove)
      // 4. problem with the sequence of add/remove is that the remove
      // cannot work because the add has not been done yet so a
      // remove must always rebuild the the argument set.
      // I hope that if we have removed a breakpoint so long as we
      // hold the reference we can still use the information such as
      // marker information ?
      // the queue must only ever have a single remove, add or remove/add (not
      // add/remove)
      // for a specific file and line
      if (bpCmd.getCmd().equals(DBGpCommand.breakPointRemove)) {
         // search vector in reverse to see if there is an add that matches
         // and remove it
         boolean foundAdd = false;
         if (DBGpCmdQueue.size() > 0) {

            for (int i = DBGpCmdQueue.size() - 1; i >= 0 && !foundAdd; i--) {
               DBGpBreakpointCmd entry = (DBGpBreakpointCmd)DBGpCmdQueue.get(i);
               if (entry.getCmd().equals(DBGpCommand.breakPointSet)) {
                  if (bpCmd.getBp().getFileName().equals(entry.getBp().getFileName())
                        && bpCmd.getBp().getLineNumber() == entry.getBp().getLineNumber()) {

                     // ok we have an entry that is an Add, the filename and lineNumber are
                     // the same so we found the entry so let's remove it.
                     foundAdd = true;
                     DBGpCmdQueue.remove(i);
                     if (DBGpLogger.debugBP()) {
                        DBGpLogger.debug("removed a breakpoint command: " + entry);
                     }
                  }
               }
            }
         }
         if (!foundAdd) {
            // add the remove as no add found
            DBGpCmdQueue.add(bpCmd);
         }
      }
      else {
         // always add an add
         DBGpCmdQueue.add(bpCmd);
      }
   }

   /**
    * process any queued breakpoint commands
    * 
    */
   private void processQueuedBpCmds() {
      // we must assume we are running on the Session listener thread so
      // cannot
      // use sync commands.....
      if (DBGpLogger.debugBP()) {
         DBGpLogger.debug("processing deferred BP cmds");
      }

      for (int i = 0; i < DBGpCmdQueue.size(); i++) {
         DBGpBreakpointCmd bpCmd = (DBGpBreakpointCmd)DBGpCmdQueue.get(i);
         if (bpCmd.getCmd().equals(DBGpCommand.breakPointSet)) {
            sendBreakpointAddCmd(bpCmd.getBp(), true);
         }
         else if (bpCmd.getCmd().equals(DBGpCommand.breakPointRemove)) {
            sendBreakpointRemoveCmd(bpCmd.getBp(), true);
         }
      }
      DBGpCmdQueue.clear();
   }

   private static class DBGpBreakpointCmd {
      private String cmd;
      private DBGpBreakpoint bp;

      public DBGpBreakpointCmd(String cmd, DBGpBreakpoint bp) {
         this.cmd = cmd;
         this.bp = bp;
      }

      public String getCmd() {
         return cmd;
      }

      public DBGpBreakpoint getBp() {
         return bp;
      }

   }

   /**
    * class to manage breakpoint conditions
    * 
    */
   private static class DBGpBreakpointCondition {

      private String hitCondition;
      private String hitValue;
      private String expression;

      private int type;
      public static final int NONE = 0;
      public static final int HIT = 1;
      public static final int EXPR = 2;
      public static final int INVALID = 3;

      public DBGpBreakpointCondition(DBGpBreakpoint bp) {
         type = NONE;
         if (bp.isConditional() && bp.isConditionEnabled()) {

            // supported
            // - expression
            // - hit(condition value)
            String bpExpression = bp.getExpression().trim();
            if (bpExpression.endsWith(")")
                  && (bpExpression.startsWith("hit(") || bpExpression.startsWith("HIT("))) {
               if (bpExpression.length() > 5) {
                  // support the following formats
                  // - >= x, >=x
                  // - ==x == x
                  // - %x % x
                  type = HIT;
                  String internal = bpExpression.substring(4, bpExpression.length() - 1).trim();
                  if (internal.startsWith("%")) {
                     hitCondition = "%";
                     hitValue = internal.substring(1).trim();
                  }
                  else {
                     hitCondition = internal.substring(0, 2);
                     if (hitCondition.equals("==") || hitCondition.equals(">=")) {
                        hitValue = internal.substring(2).trim();
                     }
                     else {
                        type = INVALID;
                     }
                  }

                  if (type != INVALID) {
                     try {
                        Integer.parseInt(hitValue);
                     }
                     catch (NumberFormatException nfe) {
                        type = INVALID;
                     }
                  }
               }
               else {
                  type = INVALID;
               }
            }
            else if (bpExpression.length() == 0) {
               type = NONE;
               expression = bpExpression;
            }
            else {
               type = EXPR;
               expression = bpExpression;
            }
         }
      }

      public String getExpression() {
         return expression;
      }

      public String getHitCondition() {
         return hitCondition;
      }

      public String getHitValue() {
         return hitValue;
      }

      public int getType() {
         return type;
      }
   }

   /*
    * (non-Javadoc)
    * @see org.eclipse.php.xdebug.core.dbgp.session.DBGpSessionListener#SessionCreated(org.eclipse.php.xdebug.core.session.DBGpSession)
    */
   public boolean SessionCreated(DBGpSession session) {
      // need to determine if the session is one we want, but only if we
      // are looking for a session, this session may be for us but if
      // we already have a session, the debugtarget is would have to be
      // reset to handle this new session, so safer to ignore it.
      boolean isMine = false;
      isMine = DBGpSessionHandler.getInstance().isCorrectSession(session, this);
      if (isMine) {
         if (this.session == null && !isTerminating()) {
            session.setDebugTarget(this);
            this.session = session;

            if (STATE_INIT_SESSION_WAIT == targetState || STATE_CREATE == targetState) {

               // if we are in initial session wait, fire the event to
               // unblock if we haven't even got that far, fire the event so that
               // when we do enter initial session wait, we just go straight
               // through.
               te.signalEvent();
            }
            else {
               initiateSession();
            }
         }
         else {
            // it was for me but I am currently processing a session, or
            // terminating, so end it. In fact we could just return false
            // here as well.
            //session.endSession();
            // well it is mine, but I am already handling a session so so it isn't mine and it
            // will be terminated.
            isMine = false;
         }
      }
      return isMine;
   }

   /**
    * return the IDEKey
    * @return
    */
   public String getIdeKey() {
      return ideKey;
   }

   /**
    * return the session Id
    * @return
    */
   public String getSessionID() {
      return sessionID;
   }

   /**
    * return if this is a web launch
    * @return
    */
   public boolean isWebLaunch() {
      return webLaunch;
   }

   private int getMaxDepth() {
      if (sessionPreferences != null) {
         return sessionPreferences.getInt(DBGpPreferences.DBGP_MAX_DEPTH_PROPERTY,
               DBGpPreferences.DBGP_MAX_DEPTH_DEFAULT);
      }
      return DBGpPreferences.DBGP_MAX_DEPTH_DEFAULT;
   }

   private boolean showGLobals() {
      if (sessionPreferences != null) {
         return sessionPreferences.getBoolean(DBGpPreferences.DBGP_SHOW_GLOBALS_PROPERTY,
               DBGpPreferences.DBGP_SHOW_GLOBALS_DEFAULT);
      }
      return DBGpPreferences.DBGP_SHOW_GLOBALS_DEFAULT;
   }
}
