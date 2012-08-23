package com.vialette.maxime.test.wicket6;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class RssReaderPage extends WebPage {

    private List<String> postALire = new ArrayList<String>();

    private MyRSSListViewPanel panelRSSViewer;
    private Label nbElementNonLu;

    public RssReaderPage() {
        super();

        // Math.round(Math.random() * 10)
        for (int i = 0; i < 30; i++) {
            postALire.add("post n° " + i);
        }

        IModel<String> imodelNbElementALire = new IModel<String>() {

            @Override
            public void detach() {
                // TODO Auto-generated method stub

            }

            @Override
            public String getObject() {
                return "Il vous reste encore " + panelRSSViewer.getNombreDePostEncoreALire() + " à lire sur "
                        + postALire.size() + " posts reçu.";
            }

            @Override
            public void setObject(String object) {

            }
        };

        nbElementNonLu = new Label("nbElementNonLu", imodelNbElementALire);
        nbElementNonLu.setOutputMarkupId(true);
        add(nbElementNonLu);

        panelRSSViewer = new MyRSSListViewPanel("unPanelReader", postALire);
        panelRSSViewer.setOutputMarkupId(true);
        add(panelRSSViewer);

        AjaxLink previous = new AjaxLink("previous") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                panelRSSViewer.decrementerStartIndex();
                target.add(panelRSSViewer);
                target.add(nbElementNonLu);
            }
        };
        add(previous);

        AjaxLink ajaxLink = new AjaxLink("next") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                panelRSSViewer.incrementerStartIndex();
                target.add(panelRSSViewer);
                target.add(nbElementNonLu);
            }
        };
        add(ajaxLink);
    }
}
