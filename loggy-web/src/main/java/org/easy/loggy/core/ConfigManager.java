package org.easy.loggy.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/7/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigManager {

	private static ConfigManager instance;

	private static final String OS = System.getProperty("os.name").toLowerCase();

	private static final String WIN_CONF_FILE = "C:\\loggy\\loggy.conf";

	private static final String LINUX_CONF_FILE = "/etc/loggy/loggy.conf";

	private static final String TRACKER_ADDRESS_PREFIX = "tracker_node_address";

	private static final String DOMAIN_HOME_PREFIX = "domain_home";

	private static final String WL_HOME = "wl_home";

	private List<String> trackerNodes;

	private List<String> domainHomes;

	private String wlsHome;

	private ConfigManager() {
		trackerNodes = new ArrayList<String>();
		domainHomes = new ArrayList<String>();
		File conf = null;
		if (isWindows()) {
			conf = new File(WIN_CONF_FILE);
		} else if (isUnix()) {
			conf = new File(LINUX_CONF_FILE);
		}
		FileInputStream cis;
		try {
			cis = new FileInputStream(conf);
			Properties props = new Properties();
			props.load(cis);
			Enumeration<Object> propKeys = props.keys();
			for (Object key : props.keySet()) {
				String strKey = key.toString();
				String strValue = props.get(key).toString();
				if (strKey.startsWith(TRACKER_ADDRESS_PREFIX)) {
					trackerNodes.add(strValue);
				} else if (strKey.startsWith(DOMAIN_HOME_PREFIX)) {
					domainHomes.add(strValue);
				} else if (strKey.startsWith(WL_HOME)) {
					wlsHome = strValue;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	private boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}

	public void restart() {
		instance = null;
	}

	public List<String> getTrackerNodes() {
		return trackerNodes;
	}

	public List<String> getDomainHomes() {
		return domainHomes;
	}

	public String getWlsHome() {
		return wlsHome;
	}


}
