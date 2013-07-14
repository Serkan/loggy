package org.easy.loggy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.easy.loggy.core.ObservableResource;
import org.easy.loggy.core.WLSLogFileDetector;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/6/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJava {

	public static void main(String[] args) {
		Map<String, List<ObservableResource>> serverMap = WLSLogFileDetector.getInstance().extractLogFileNames();
		Set<String> domains = serverMap.keySet();
		for (String domain : domains) {
			System.out.println(domain);
			List<ObservableResource> observableResources = serverMap.get(domain);
			for (ObservableResource observableResource : observableResources) {
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
				System.out.println(gson.toJson(observableResource));

			}
		}
//		String[] domainHomeList = WLSLogFileDetector.getDomainHomeList();
//		for (String s : domainHomeList) {
//			System.out.println(s);
//		}

	}


}
