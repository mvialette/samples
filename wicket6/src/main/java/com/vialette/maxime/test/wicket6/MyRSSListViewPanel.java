package com.vialette.maxime.test.wicket6;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class MyRSSListViewPanel extends Panel {
    private ListView<String> listView;

    private int nombreDePostEncoreALire = 0;

    private List<String> postALire;

    public MyRSSListViewPanel(String id, List<String> postALire) {
        super(id);

        this.postALire = postALire;
        nombreDePostEncoreALire = postALire.size();

        vueDesPostsALire();
    }

    private void vueDesPostsALire() {
        IModel<List<String>> imodel = new IModel<List<String>>() {

            @Override
            public void detach() {
                // TODO Auto-generated method stub

            }

            @Override
            public void setObject(List<String> object) {
                // TODO Auto-generated method stub

            }

            @Override
            public List<String> getObject() {
                return postALire;
            }
        };

        listView = new ListView<String>("listViewDesPosts", imodel) {

            @Override
            protected void populateItem(ListItem<String> item) {
                Label unPost = new Label("unPost", "<b>" + item.getDefaultModelObjectAsString() + "</b>");
                unPost.setEscapeModelStrings(false);
                item.add(unPost);
            }
        };

        // listView.setStartIndex(5);
        listView.setViewSize(10);
        listView.setOutputMarkupId(true);

        add(listView);
    }

    public void incrementerStartIndex() {
        int currentStartIndex = listView.getStartIndex();
        if (currentStartIndex < postALire.size()) {
            listView.setStartIndex(listView.getStartIndex() + 1);
            nombreDePostEncoreALire--;
        }
    }

    public void decrementerStartIndex() {
        int currentStartIndex = listView.getStartIndex();
        if (currentStartIndex > 0) {
            listView.setStartIndex(listView.getStartIndex() - 1);
            nombreDePostEncoreALire++;
        }
    }

    public int getNombreDePostEncoreALire() {
        return nombreDePostEncoreALire;
    }

    public void setNombreDePostEncoreALire(int nombreDePostEncoreALire) {
        this.nombreDePostEncoreALire = nombreDePostEncoreALire;
    }
}
