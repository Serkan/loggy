package org.easy.loggy.management;

import com.google.gson.Gson;
import org.easy.loggy.core.ConfigManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: serkan
 * Date: 7/7/13
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManagementConfig extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> trackerNodes = ConfigManager.getInstance().getTrackerNodes();
//        trackerNodes.add("");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        String nodesJson = gson.toJson(trackerNodes);
        writer.write(nodesJson);
        writer.flush();
    }
}
