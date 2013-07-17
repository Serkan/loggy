package org.easy.loggy.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.input.TailerListenerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/6/13
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class LogTailerAdapter extends TailerListenerAdapter {

	private ObservableResource observableResource;

	private Gson gson;


	public LogTailerAdapter(ObservableResource observableResource) {
		this.observableResource = observableResource;
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
	}

	public String toJson(ObservableResource logInstance) {
		return gson.toJson(logInstance);
	}

	public ObservableResource getObservableResource() {
		return observableResource;
	}

	public void setObservableResource(ObservableResource observableResource) {
		this.observableResource = observableResource;
	}

	/**
	 * Handles a line from a Tailer.
	 *
	 * @param line the line.
	 */
	public void handle(String line) {
		// FIXME regex olana kadar
		String[] parts = line.split("<");
		int length = parts.length;
		StringBuffer logHeaders = new StringBuffer();
		if (length == 0) {
			handleLog(null, line);
		} else {
			for (int i = 0; i < length - 1; i++) {
				String temp = parts[i].replaceAll(">", "");
				if (temp != null && !temp.trim().equals("")) {
					logHeaders.append(temp);
					logHeaders.append("&lt;br /&gt;");
				}
			}
			String logLine = parts[length - 1].replaceAll(">", "");
			handleLog(logHeaders.toString(), logLine);
		}
	}

	public abstract void handleLog(String logHeaders, String logLine);


}
