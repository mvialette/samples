package com.vialette.maxime.test.wicket6;

import java.util.concurrent.TimeUnit;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.WebSocketRequestHandler;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.protocol.ws.api.IWebSocketConnection;
import org.apache.wicket.protocol.ws.api.IWebSocketConnectionRegistry;
import org.apache.wicket.protocol.ws.api.SimpleWebSocketConnectionRegistry;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.message.TextMessage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;
    protected String applicationName;
    protected String sessionId;
    protected Integer pageId;

    private Label leLabel = null;

    private String contenuDuMessage = "";

    public HomePage(final PageParameters parameters) {
        super(parameters);

        Label label = new Label("du.texte", new ResourceModel("du.texte"));
        add(label);

        constructionDesBoutonsDeNotification();

        add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

        add(new AjaxLink("subscribe") {

            @Override
            public void onClick(AjaxRequestTarget target) {

                target.appendJavaScript("Wicket.Event.subscribe(\"/websocket/message\", function(jqEvent, message) {alert(message);});");
            }
        });

        add(new WebSocketBehavior() {
            @Override
            protected void onMessage(WebSocketRequestHandler handler, TextMessage message) {

                // WicketApplication.eventSystem.broadcastMessage(message.getText());

                System.out.println("received message = " + message.getText());

                WicketApplication.messagesAyantEtePostes.add(message.getText());
                handler.add(leLabel);

                // handler.push("Hello : " + message.getText());
            }

            // @Override
            // protected void onConnect(ConnectedMessage message) {
            // super.onConnect(message);
            //
            // applicationName = message.getApplication().getName();
            // sessionId = message.getSessionId();
            // pageId = message.getPageId();
            //
            // WicketApplication.eventSystem.clientConnected(applicationName, sessionId, pageId);
            //
            // // String[] tabMessage=
            // UpdateTask updateTask = new UpdateTask(message.getApplication(), sessionId, pageId);
            // Executors.newScheduledThreadPool(1).schedule(updateTask, 1, TimeUnit.SECONDS);
            // }
        });

        IModel<String> modelDuLabel = new AbstractReadOnlyModel<String>() {

            @Override
            public String getObject() {
                return (WicketApplication.messagesAyantEtePostes.size() == 0) ? ""
                        : WicketApplication.messagesAyantEtePostes
                        .get(WicketApplication.messagesAyantEtePostes.size() - 1);
                // return leDernierMessage;
            }
        };
        leLabel = new Label("labelMessage", modelDuLabel);
        leLabel.setMarkupId("labelMessage");
        leLabel.setOutputMarkupId(true);
        add(leLabel);

        Link nextPage = new Link("nextPage") {

            @Override
            public void onClick() {

                PageParameters pp = new PageParameters();

                // parameters.add("applicationName", applicationName);
                // parameters.add("sessionId", sessionId);
                // parameters.add("pageId", pageId);

                setResponsePage(MyPage.class, parameters);
            }
        };
        add(nextPage);

        add(new AjaxLink("rssReaderLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                setResponsePage(RssReaderPage.class);
            }
        });
    }

    private void constructionDesBoutonsDeNotification() {
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel") {
            // @Override
            // public boolean isVisible() {
            // return anyMessage();
            // }
        };
        feedbackPanel.setEscapeModelStrings(false);
        feedbackPanel.setOutputMarkupId(true);
        this.add(feedbackPanel);

        AjaxLink lienA = new AjaxLink("lienA") {

            @Override
            public void onClick(AjaxRequestTarget arg0) {
                error("<div class=\"alert-box alert\">  This is a error box.  This is a error box.  This is a error box.  This is a error box.  This is a error box.  This is a error box.  This is a error box.  <a href=\"\" class=\"close\"><img src=\"images/close.png\" title=\"fermer la reveal\" alt=\"image 'fermeture'\" /></a></div>");
                arg0.add(feedbackPanel);
            }
        };
        add(lienA);

        AjaxLink lienB = new AjaxLink("lienB") {

            @Override
            public void onClick(AjaxRequestTarget arg0) {
                warn("<div class=\"alert-box\">  This is an warn box.    <a href=\"\" class=\"close\"><img src=\"images/close.png\" title=\"fermer la reveal\" alt=\"image 'fermeture'\" /></a></div>");
                arg0.add(feedbackPanel);
            }
        };
        add(lienB);

        AjaxLink lienC = new AjaxLink("lienC") {

            @Override
            public void onClick(AjaxRequestTarget arg0) {
                info("<div class=\"alert-box success\">  This is an success box.    <a href=\"\" class=\"close\"><img src=\"images/close.png\" title=\"fermer la reveal\" alt=\"image 'fermeture'\" /></a></div>");
                arg0.add(feedbackPanel);
            }
        };
        add(lienC);
    }

    /**
     * A task that sends data to the client by pushing it to the web socket connection
     */
    private static class UpdateTask implements Runnable {

        /**
         * The following fields are needed to be able to lookup the IWebSocketConnection from
         * SimpleWebSocketConnectionRegistry
         */
        private final String applicationName;
        private final String sessionId;
        private final int pageId;

        private UpdateTask(Application application, String sessionId, int pageId) {
            this.applicationName = application.getName();
            this.sessionId = sessionId;
            this.pageId = pageId;
        }

        @Override
        public void run() {
            IWebSocketConnectionRegistry webSocketConnectionRegistry = new SimpleWebSocketConnectionRegistry();
            int dataIndex = 0;

            while (true && dataIndex < WicketApplication.messagesAyantEtePostes.size()) {
                Application application = Application.get(applicationName);
                IWebSocketConnection connection = webSocketConnectionRegistry.getConnection(application, sessionId,
                        pageId);
                if (connection == null || !connection.isOpen()) {
                    // stp if the web socket connection is closed
                    return;
                }

                try {
                    String record = WicketApplication.messagesAyantEtePostes.get(dataIndex++);
                    connection.sendMessage(record);

                    // sleep for a while to simulate work
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception x) {
                    x.printStackTrace();
                    return;
                }
            }
        }
    }
}
