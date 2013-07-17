package org.easy.loggy.core;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;

import java.io.IOException;

@AtmosphereHandlerService(path = "/resource/*",
		                         interceptors = {AtmosphereResourceLifecycleInterceptor.class,
				                                        BroadcastOnPostAtmosphereInterceptor.class
		                         })
public class UserHandler implements AtmosphereHandler {


	@Override
	public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
		Broadcaster broadcaster = atmosphereResource.getBroadcaster();
//		broadcaster.broadcast("hello broadcast");
	}

	@Override
	public void onStateChange(AtmosphereResourceEvent atmosphereResourceEvent) throws IOException {
		Object message = atmosphereResourceEvent.getMessage();
		atmosphereResourceEvent.write(message.toString().getBytes());
	}

	@Override
	public void destroy() {
		System.out.println("Client gitti ya la");
	}
}