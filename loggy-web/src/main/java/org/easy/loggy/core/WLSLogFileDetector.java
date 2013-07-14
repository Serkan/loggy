package org.easy.loggy.core;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/6/13
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class WLSLogFileDetector implements LogFileDetector {

    private static LogFileDetector instance;

    private WLSLogFileDetector() {
    }

    public static LogFileDetector getInstance() {
        if (instance == null) {
            instance = new WLSLogFileDetector();
        }
        return instance;
    }

    public Map<String, List<ObservableResource>> extractLogFileNames() {
        Map<String, List<ObservableResource>> serverMap = new HashMap<String, List<ObservableResource>>();
        List<String> domainHomes = getDomainHomeList();
        for (String domainHome : domainHomes) {
            String domainName = domainHome.substring(domainHome.lastIndexOf(File.separator) + 1, domainHome.length());
            domainHome = domainHome.replaceAll("[\n\r]", "");
            File config = new File(domainHome + "/config/config.xml");
            List<ObservableResource> observableResources = new ArrayList<ObservableResource>();
            try {
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder().parse(config);
                NodeList serverNodes = doc.getElementsByTagName("server");
                for (int i = 0; i < serverNodes.getLength(); i++) {
                    Node item = serverNodes.item(i);
                    // server configuration iteration
                    // server properties
                    NodeList serverProperties = item.getChildNodes();
                    ObservableResource instance = getServer(domainHome, domainName, serverProperties);
                    observableResources.add(instance);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // FIXME  if exists, node manager nohut.out
//			ObservableResource r1 = new ObservableResource();
//			r1.setDomainName(domainName);
//			r1.setName("Node Manager");
//			r1.setLogFileLoc(weblogicHome + "\\server\\bin\\nohup.out");
            // check if exists, domain starter nohup.out
            String r2Loc = domainHome + "/nohup.out";
            File r2File = new File(r2Loc);
            if (r2File.exists()) {
                ObservableResource r2 = new ObservableResource();
                r2.setDomainName(domainName);
                r2.setName("Domain Starter");
                r2.setLogFileLoc(r2Loc);
                observableResources.add(r2);
            }
            serverMap.put(domainHome, observableResources);

        }
        return serverMap;
    }

    private ObservableResource getServer(String domainHome, String domainName, NodeList serverProperties) {
        ObservableResource instance = new ObservableResource();
        instance.setDomainName(domainName);
        String listenAdress = null;
        String listenPort = null;
        String xmlFileLoc = null;
        for (int k = 0; k < serverProperties.getLength(); k++) {
            Node property = serverProperties.item(k);
            String nodeName = property.getNodeName();
            if (nodeName.equals("name")) {
                instance.setName(property.getFirstChild().getNodeValue());
            } else if (nodeName.equals("listen-address")) {
                if (property.getFirstChild() != null) {
                    listenAdress = property.getFirstChild()
                            .getNodeValue();
                }
            } else if (nodeName.equals("listen-port")) {
                listenPort = property.getFirstChild().getNodeValue();
            } else if (nodeName.equals("log")) {
                NodeList logProps = property.getChildNodes();
                for (int l = 0; l < logProps.getLength(); l++) {
                    Node logNode = logProps.item(l);
                    if (logNode.getNodeName().equals("file-name")) {
                        xmlFileLoc = logNode.getFirstChild().getNodeValue();
                    }
                }
            }
            // set listen address
            if (listenAdress == null) {
                String hostName = null;
                try {
                    hostName = InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                instance.setListenAddress(hostName);
            } else {
                instance.setListenAddress(listenAdress);
            }
            // set listen port
            if (listenPort == null) {
                instance.setListenPort("7001");
            } else {
                instance.setListenPort(listenPort);
            }
            //set log file location
            if (xmlFileLoc == null) {
                instance.setLogFileLoc(domainHome + "/servers/" + instance.getName() + "/logs/" + instance.getName() + ".log");
            } else {
                if (xmlFileLoc.startsWith("/")) {
                    instance.setLogFileLoc(xmlFileLoc);
                } else {
//                xmlFileLoc = xmlFileLoc.replace("/", "\\");
                    // you must set absolute path of log life
                    instance.setLogFileLoc(domainHome + "/servers/" + instance.getName() + "/" + xmlFileLoc);
                }
            }
        }
        System.out.println("Log detected : " + "domain : " + instance.getDomainName() + " listenaddress : " + instance.getListenAddress() + " listen-port : " + instance.getListenPort() + " name : " + instance.getName() + " log_loc : " + instance.getLogFileLoc());
        return instance;
    }

    private List<String> getDomainHomeList() {
        return ConfigManager.getInstance().getDomainHomes();
    }


}
