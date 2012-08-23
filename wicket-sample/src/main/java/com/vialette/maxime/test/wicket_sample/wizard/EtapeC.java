package com.vialette.maxime.test.wicket_sample.wizard;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;

public class EtapeC extends BreadCrumbPanel {

    public EtapeC(String id, IBreadCrumbModel breadCrumbModel) {
        super(id, breadCrumbModel);
    }

    @Override
    public String getTitle() {
        return "Etape C";
    }

}
