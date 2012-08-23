package com.vialette.maxime.test.wicket_sample;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;

import com.vialette.maxime.test.wicket_sample.wizard.WizardPanel;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    @SpringBean(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    public HomePage(final PageParameters parameters) {
        super(parameters);
        
        Label unLabel = new Label("du.texte", new ResourceModel("du.texte"));
        add(unLabel);

        // Authentication a = SecurityContextHolder.getContext().getAuthentication();
        MyAuthenticatedWebSession webSession = ((MyAuthenticatedWebSession) Session.get());
        System.out.println(">>" + webSession.getRoles());

        Form monForm = new Form("monForm");
        add(monForm);

        TextField<String> usernameTF = new TextField<String>("username", new PropertyModel<String>(this, "username"));
        monForm.add(usernameTF);

        TextField<String> passwordTF = new TextField<String>("password", new PropertyModel<String>(this, "password"));
        monForm.add(passwordTF);

        Button monBoutton = new Button("monBoutton") {

            @Override
            public void onSubmit() {

                boolean auth = ((MyAuthenticatedWebSession) Session.get()).authenticate(username,
                        password);

                if (auth) {
                    // Session session = Session.get();
                    // session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

                    PageParameters pp = new PageParameters();
                    setResponsePage(new SecondPage(pp));
                } else {
                    System.out.println("ko");
                }

                System.out.println(username);
                System.out.println(password);

            }
        };

        monForm.add(monBoutton);

        WizardPanel wizardPanel = new WizardPanel("leWizard");
        add(wizardPanel);

        List<String> lesDonnees = new ArrayList<String>();
        lesDonnees.add("maxime");
        lesDonnees.add("laurent");
        lesDonnees.add("cédric");
        lesDonnees.add("christophe");

        PageableListView maListe = new PageableListView("maListe", lesDonnees, 1) {

            @Override
            protected void populateItem(ListItem item) {
                String string = (String) item.getModelObject();
                item.add(new Label("unLabel", "text : " + string));

            }
        };
        add(maListe);

        maListe.setCurrentPage(2);

        PagingNavigator navigator = new PagingNavigator("navigator", maListe);
        add(navigator);

        // maListe.setStartIndex(2);
        // maListe.set
    }

    @Override
    public boolean hasAssociatedMarkup() {
        // TODO Auto-generated method stub
        return super.hasAssociatedMarkup();
    }

}
