package org.easy.loggy.core;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/7/13
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LogFileDetector {

	public List<ObservableResource> extractLogFileNames();

}
