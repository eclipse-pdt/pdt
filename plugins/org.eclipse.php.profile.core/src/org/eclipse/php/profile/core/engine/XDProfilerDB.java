package org.eclipse.php.profile.core.engine;

import java.util.*;

import org.eclipse.php.internal.core.util.collections.IntHashtable;
import org.eclipse.php.internal.debug.core.xdebug.cachegrind.Tree;
import org.eclipse.php.internal.debug.core.xdebug.cachegrind.Tree.InvocationEntry;
import org.eclipse.php.profile.core.data.*;

public class XDProfilerDB implements ProfilerDB {

	private IntHashtable fFunctionsHash = new IntHashtable(); // hashtable of
	// funtion_id
	// and
	// functionData

	private Map<String, ProfilerFileData> fFilesHash = new Hashtable<String, ProfilerFileData>(); // hashtable
	// of
	// file
	// name

	private ProfilerCallTrace fCallTrace = new ProfilerCallTrace();

	private ProfilerGlobalData fGlobalData = new ProfilerGlobalData();

	private Date fProfileDate;

	public XDProfilerDB(Tree tree) {
		buildProfileDataFromTree(tree);
		fProfileDate = new Date();
	}

	private void buildProfileDataFromTree(Tree tree) {
		fFilesHash.clear();
		fFunctionsHash.clear();

		for (InvocationEntry entry : tree.getInvocationEntries()) {
			ProfilerFileData file;
			if (this.fFilesHash.containsKey(entry.getFileName())) {
				file = new ProfilerFileData(entry.getFileName(), entry.getFileName(), entry.getInvocationCnt(),
						entry.getSummedInclusiveCost(), new ArrayList<>());
				this.fFilesHash.put(entry.getFileName(), file);
			} else {
				file = this.fFilesHash.get(entry.getFileName());
			}
			ProfilerFunctionData function = new ProfilerFunctionData(entry.getFileName(), entry.getFunctionName(),
					entry.getLine(), entry.getFunctionIndex(), 0, Long.valueOf(entry.getSummedSelfCost()).intValue(), 0,
					Long.valueOf(entry.getSummedInclusiveCost()).intValue(), entry.getInvocationCnt());
			file.addFunction(function);
			fFunctionsHash.put(entry.getFunctionIndex(), function);

			this.fGlobalData.addFileName(entry.getFileName());
			this.fGlobalData.setFileCount(fGlobalData.getFileCount() + 1);

			this.fCallTrace.addLayer(new ProfilerCallTraceLayer(1, entry.getLine(), entry.getFunctionIndex(), 0,
					Long.valueOf(entry.getSummedInclusiveCost()).intValue()));
		}

		this.fGlobalData.setPath(tree.getHeader().getCommand());
	}

	/**
	 * Gets the global data of the profiler
	 */
	@Override
	public ProfilerGlobalData getGlobalData() {
		return fGlobalData;
	}

	/**
	 * @param id
	 *            of the function as recieved from the debugger
	 * @return data of the function
	 */
	@Override
	public ProfilerFunctionData getFunctionData(int id) {
		if (this.fFunctionsHash.containsKey(id)) {
			return (ProfilerFunctionData) fFunctionsHash.get(id);
		}
		return null;
	}

	/**
	 * Get the file data from the profilerdata or from the debugger
	 * 
	 * @param fileName
	 *            - the file name to be requested
	 * @return the filedata
	 */
	@Override
	public ProfilerFileData getFileData(String fileName) {
		if (this.fFilesHash.containsKey(fileName)) {
			return this.fFilesHash.get(fileName);
		}
		return null;
	}

	/**
	 * Get the file data from the profilerdata or from the debugger
	 * 
	 * @param fileNumber
	 *            - the file number to be requested
	 * @return the filedata
	 */
	@Override
	public ProfilerFileData getFileData(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * get the call trace from the database as recieved from the debugger
	 * 
	 * @return the callTrace
	 */
	@Override
	public ProfilerCallTrace getCallTrace() {
		return fCallTrace;
	}

	/**
	 * Clear all information from database and reset profiler
	 */
	@Override
	public void clearAll() {
		fFunctionsHash.clear();
		fFilesHash.clear();
		fCallTrace = new ProfilerCallTrace();
	}

	@Override
	public ProfilerData getProfilerData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProfilerData(ProfilerData data) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets all the files from the profiler
	 * 
	 * @return the files as array
	 */
	@Override
	public ProfilerFileData[] getFiles() {
		List<ProfilerFileData> files = this.getFilesList();
		return files.toArray(new ProfilerFileData[files.size()]);
	}

	/**
	 * Gets all the files from the profiler
	 * 
	 * @return the files as list
	 */
	@Override
	public List<ProfilerFileData> getFilesList() {
		List<ProfilerFileData> files = new ArrayList<>();
		files.addAll(this.fFilesHash.values());
		return files;
	}

	/**
	 * Returns profile date
	 */
	@Override
	public Date getProfileDate() {
		return fProfileDate;
	}

}
