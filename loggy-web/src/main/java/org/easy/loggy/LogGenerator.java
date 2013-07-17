package org.easy.loggy;

import org.apache.commons.lang.RandomStringUtils;
import org.easy.loggy.core.ObservableResource;
import org.easy.loggy.core.WLSLogFileDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/12/13
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogGenerator {


	private static AtomicInteger count = new AtomicInteger(0);

	public static void main(String[] args) throws FileNotFoundException {
		List<ObservableResource> logList = WLSLogFileDetector.getInstance().extractLogFileNames();
		for (ObservableResource observableResource : logList) {
			File file = new File(observableResource.getLogFileLoc());
			final FileOutputStream fos = new FileOutputStream(file);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						String random = RandomStringUtils.randomAlphabetic(200);
						random = "<Test -1> <WLS-34242341><WINAMP-32434>" + random + "\n";
						System.out.println(count.addAndGet(1));
						try {
							fos.write(random.getBytes());
							Thread.sleep(100);
						} catch (IOException e) {
							throw new RuntimeException(e);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				}
			});
			thread.start();
		}
	}
}
