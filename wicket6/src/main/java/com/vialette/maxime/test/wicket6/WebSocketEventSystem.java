package com.vialette.maxime.test.wicket6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.ws.api.IWebSocketConnection;
import org.apache.wicket.protocol.ws.api.IWebSocketConnectionRegistry;
import org.apache.wicket.protocol.ws.api.SimpleWebSocketConnectionRegistry;

public class WebSocketEventSystem {

    private List<String> applicationsNames = new ArrayList<String>();
    private List<String> sessionIds = new ArrayList<String>();
    private List<Integer> pageIds = new ArrayList<Integer>();

    public void clientConnected(String applicationName, String sessionId, Integer pageId) {
        boolean newClient = false;

        if (!applicationsNames.contains(applicationName)) {
            applicationsNames.add(applicationName);
            newClient = true;
        }
        if (!sessionIds.contains(sessionId)) {
            sessionIds.add(sessionId);
            newClient = true;
        }
        if (!pageIds.contains(pageId)) {
            pageIds.add(pageId);
            newClient = true;
        }

        if (newClient) {
            System.out.println("new client connected applicationName=[" + applicationName + "], sessionId=["
                    + sessionId + "], pageId=[" + pageId + "]");
        }
    }

    public void broadcastMessage(String message) {
        for (int i = 0; i < applicationsNames.size(); i++) {
            String applicationName = applicationsNames.get(i);
            String sessionId = sessionIds.get(i);
            Integer pageId = pageIds.get(i);

            IWebSocketConnectionRegistry registry = new SimpleWebSocketConnectionRegistry();
            Application application = Application.get(applicationName);
            IWebSocketConnection wsConnection = registry.getConnection(application, sessionId, pageId);

            if (wsConnection != null && wsConnection.isOpen()) {
                try {
                    wsConnection.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
