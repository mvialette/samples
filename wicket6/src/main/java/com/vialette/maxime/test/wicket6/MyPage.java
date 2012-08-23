package com.vialette.maxime.test.wicket6;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.WebSocketRequestHandler;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.apache.wicket.protocol.ws.api.message.TextMessage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MyPage extends WebPage {

    private String currentTypeMessage;

    String applicationName;
    String sessionId;
    Integer pageId;

    public MyPage(PageParameters pageParameters) {
        super(pageParameters);

        // applicationName = pageParameters.get("applicationName").toString();
        // sessionId = pageParameters.get("sessionId").toString();
        // pageId = pageParameters.get("pageId").toInteger();

        Form maForm = new Form("myChatForm");
        add(maForm);

        IModel<String> tfModel = new IModel<String>() {

            @Override
            public void detach() {
                // TODO Auto-generated method stub

            }

            @Override
            public void setObject(String object) {
                currentTypeMessage = object;
            }

            @Override
            public String getObject() {
                return currentTypeMessage;
            }
        };

        TextField textField = new TextField("monMessageTF", tfModel);
        maForm.add(textField);

        AjaxSubmitLink postMessageSubmit = new AjaxSubmitLink("postMessageSubmit") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                // super.onSubmit(target, form);
                target.appendJavaScript("Wicket.WebSocket.send(\"" + currentTypeMessage + "\");");
            }
        };
        maForm.add(postMessageSubmit);

        add(new WebSocketBehavior() {
            @Override
            protected void onMessage(WebSocketRequestHandler handler, TextMessage message) {

                WicketApplication.eventSystem.broadcastMessage(message.getText());
                //
                // WicketApplication.messagesAyantEtePostes.add(message.getText());
                // System.out.println(WicketApplication.lastMessagePost);

                // handler.push(message.getText());
            }

            @Override
            protected void onConnect(ConnectedMessage message) {
                super.onConnect(message);

                String applicationName = message.getApplication().getName();
                String sessionId = message.getSessionId();
                Integer pageId = message.getPageId();

                WicketApplication.eventSystem.clientConnected(applicationName, sessionId, pageId);

            }
        });

        AjaxLink lienEnvoiMessage = new AjaxLink("send_message_2") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // target.appendJavaScript("Wicket.WebSocket.send(\"message depuis MyPage\");");
                target.appendJavaScript("Wicket.Event.subscribe(\"/websocket/message\", function(jqEvent, message) {alert(message);});");
            }
        };
        add(lienEnvoiMessage);
    }
}