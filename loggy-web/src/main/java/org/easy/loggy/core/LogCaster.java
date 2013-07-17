package org.easy.loggy.core;

import org.apache.commons.io.input.Tailer;
import org.atmosphere.cpr.MetaBroadcaster;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Log File list
 * <li>
 * <ul>server/*.log</ul>
 * <ul>server/*.out</ul>
 * <ul>nodemanager/nohup.out</ul>
 * <ul>domain/nohup.out|domain/bin/nohup.out</ul>
 * <p/>
 * </li>
 */
public class LogCaster extends HttpServlet {

	private ExecutorService executorService;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		initThreads();
	}

	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		executorService.shutdownNow();
		ConfigManager.getInstance().restart();
		initThreads();
	}

	private void initThreads() {
		List<ObservableResource> observableResources = WLSLogFileDetector.getInstance().extractLogFileNames();
		int size = observableResources.size();
		if (size != 0) {
			//FIXME pooling dugru calismiyor
			executorService = Executors.newFixedThreadPool(size);
		}
		for (ObservableResource observableResource : observableResources) {
			File logFile = new File(observableResource.getLogFileLoc());
			if (logFile.exists()) {
				Tailer tailer = new Tailer(logFile, new LogTailerAdapter(observableResource) {

					@Override
					public void handleLog(String headers, String logLine) {
						ObservableResource logTemplate = getObservableResource();
						logTemplate.setLogHeaders(headers);
						logTemplate.setLogLine(logLine);
						String log = toJson(logTemplate);
						MetaBroadcaster.getDefault().broadcastTo("/*", log);
					}
				}, 100, true);
				executorService.execute(tailer);
			}
		}
	}
}





