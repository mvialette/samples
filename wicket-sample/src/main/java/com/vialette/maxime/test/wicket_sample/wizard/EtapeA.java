package com.vialette.maxime.test.wicket_sample.wizard;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanelLink;

public class EtapeA extends BreadCrumbPanel {

    public EtapeA(String id, IBreadCrumbModel breadCrumbModel) {
        super(id, breadCrumbModel);

        add(new BreadCrumbPanelLink("linkToSecond", this, EtapeB.class));
    }

    @Override
    public String getTitle() {
        return "Etape A";
    }

}
