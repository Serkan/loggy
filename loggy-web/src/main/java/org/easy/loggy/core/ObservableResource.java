package org.easy.loggy.core;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/6/13
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class ObservableResource implements Serializable {

	@Expose
	private String domainName;

	@Expose
	private String name;

	@Expose
	private String listenPort;

	@Expose
	private String listenAddress;

	@Expose
	private String logLine;


	@Expose
	private String logHeaders;

	private String logFileLoc;

	public String getLogHeaders() {
		return logHeaders;
	}

	public void setLogHeaders(String logHeaders) {
		this.logHeaders = logHeaders;
	}

	public String getLogFileLoc() {
		return logFileLoc;
	}

	public void setLogFileLoc(String logFileLoc) {
		this.logFileLoc = logFileLoc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getListenPort() {
		return listenPort;
	}

	public void setListenPort(String listenPort) {
		this.listenPort = listenPort;
	}

	public String getListenAddress() {
		return listenAddress;
	}

	public void setListenAddress(String listenAddress) {
		this.listenAddress = listenAddress;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getLogLine() {
		return logLine;
	}

	public void setLogLine(String logLine) {
		this.logLine = logLine;
	}
}
