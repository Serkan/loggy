package org.easy.loggy.core;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/7/13
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LogFileDetector {

	public Map<String, List<ObservableResource>> extractLogFileNames();

}
