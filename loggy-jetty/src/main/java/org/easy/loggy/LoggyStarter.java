package org.easy.loggy;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;

/**
 * Starts embedded jetty server.
 */
public class LoggyStarter {

    public static void main(String[] args) throws Exception {
        String port = args[0];
        Server server = new Server(Integer.valueOf(port));

        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath("/loggy");

        ProtectionDomain protectionDomain = LoggyStarter.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        context.setWar(location.toExternalForm());
        server.setHandler(context);

        server.start();
        server.join();
    }

}
