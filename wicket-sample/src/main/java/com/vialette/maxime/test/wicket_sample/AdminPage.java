package com.vialette.maxime.test.wicket_sample;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@AuthorizeInstantiation("ROLE_ADMIN")
public class AdminPage extends WebPage {

    public AdminPage(final PageParameters parameters) {
        super(parameters);
    }

}
