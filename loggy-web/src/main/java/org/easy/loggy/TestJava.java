package org.easy.loggy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.easy.loggy.core.ObservableResource;
import org.easy.loggy.core.WLSLogFileDetector;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/6/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJava {

	public static void main(String[] args) {
		List<ObservableResource> observableResources = WLSLogFileDetector.getInstance().extractLogFileNames();
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