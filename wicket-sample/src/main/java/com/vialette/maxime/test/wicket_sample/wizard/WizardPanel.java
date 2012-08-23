package com.vialette.maxime.test.wicket_sample.wizard;

import org.apache.wicket.extensions.breadcrumb.BreadCrumbBar;
import org.apache.wicket.markup.html.panel.Panel;

public class WizardPanel extends Panel {

    public WizardPanel(String id) {
        super(id);

        BreadCrumbBar breadCrumbBar = new BreadCrumbBar("breadCrumbBar");
        add(breadCrumbBar);

        EtapeA a = new EtapeA("panelAInjecter", breadCrumbBar);
        add(a);

        breadCrumbBar.setActive(a);
    }
}
