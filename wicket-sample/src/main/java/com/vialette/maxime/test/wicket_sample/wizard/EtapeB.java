package com.vialette.maxime.test.wicket_sample.wizard;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanelLink;

public class EtapeB extends BreadCrumbPanel {

    public EtapeB(String id, IBreadCrumbModel breadCrumbModel) {
        super(id, breadCrumbModel);

        add(new BreadCrumbPanelLink("linkToThree", this, EtapeC.class));
    }

    @Override
    public String getTitle() {
        return "Etape B";
    }

}
