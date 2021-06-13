package qlsl.androiddesign.entity.commonentity;

import java.io.Serializable;

/**
 * 进程信息<br/>
 *
 */
public class ProcessInfo implements Serializable {

	private static final long serialVersionUID = 5411244523796544089L;

	private int pid;

	private int uid;

	private int memSize;

	private String processName;

	private String[] pkgList;

	private int importance;

	private int importanceReasonCode;

	private String componentPkg;

	private String componentClass;

	private int importanceReasonPid;

	private String lastTrimLevel;

	private int lru;

	private int describeContents;

	private int hashCode;

	private long transmitFlow;

	private long receiveFlow;

	public ProcessInfo() {

	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getMemSize() {
		return memSize;
	}

	public void setMemSize(int memSize) {
		this.memSize = memSize;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String[] getPkgList() {
		return pkgList;
	}

	public void setPkgList(String[] pkgList) {
		this.pkgList = pkgList;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int getImportanceReasonCode() {
		return importanceReasonCode;
	}

	public void setImportanceReasonCode(int importanceReasonCode) {
		this.importanceReasonCode = importanceReasonCode;
	}

	public String getComponentPkg() {
		return componentPkg;
	}

	public void setComponentPkg(String componentPkg) {
		this.componentPkg = componentPkg;
	}

	public String getComponentClass() {
		return componentClass;
	}

	public void setComponentClass(String componentClass) {
		this.componentClass = componentClass;
	}

	public int getImportanceReasonPid() {
		return importanceReasonPid;
	}

	public void setImportanceReasonPid(int importanceReasonPid) {
		this.importanceReasonPid = importanceReasonPid;
	}

	public String getLastTrimLevel() {
		return lastTrimLevel;
	}

	public void setLastTrimLevel(String lastTrimLevel) {
		this.lastTrimLevel = lastTrimLevel;
	}

	public int getLru() {
		return lru;
	}

	public void setLru(int lru) {
		this.lru = lru;
	}

	public int getDescribeContents() {
		return describeContents;
	}

	public void setDescribeContents(int describeContents) {
		this.describeContents = describeContents;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public long getTransmitFlow() {
		return transmitFlow;
	}

	public void setTransmitFlow(long transmitFlow) {
		this.transmitFlow = transmitFlow;
	}

	public long getReceiveFlow() {
		return receiveFlow;
	}

	public void setReceiveFlow(long receiveFlow) {
		this.receiveFlow = receiveFlow;
	}

}
