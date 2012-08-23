package com.vialette.maxime.test.wicket_sample;

import java.util.Collection;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;

import com.vialette.maxime.test.wicket_sample.service.MonService;

public class SecondPage extends WebPage {

    @SpringBean
    private MonService monService;
    //
    @SpringBean
    private RoleHierarchy roleHierarchy;

    // @SpringBean
    // private RoleHierarchyVoter voter;

    public SecondPage(final PageParameters parameters) {
        super(parameters);

        Collection<? extends GrantedAuthority> ga = null;
        MyAuthenticatedWebSession webSession = ((MyAuthenticatedWebSession) Session.get());

        String lesRoles = webSession.getRoles().toString();
        if (lesRoles.length() == 0) {
            System.out.println("ko");
        } else {
            ga = roleHierarchy.getReachableGrantedAuthorities(AuthorityUtils.createAuthorityList(lesRoles));
        }

        StringBuffer sb = new StringBuffer();
        for (GrantedAuthority role : ga) {
            sb.append(role.getAuthority());
            sb.append(",");
        }

        Label labelNom = new Label("nomDuUser", webSession.getUsername());
        add(labelNom);

        Label labelMesDroits = new Label("mesDroits", sb.toString());
        add(labelMesDroits);

        String b = "";
        if (webSession.getRoles().hasRole("ROLE_ADMIN")) {
            b = "role admin aquis";

            // ga = roleHierarchy.getReachableGrantedAuthorities(AuthorityUtils
            // .createAuthorityList("ROLE_ADMIN"));
        } else {
            b = "role admin ko";
        }
        Label labelpanelB = new Label("panelB", b);
        add(labelpanelB);

        // System.out.println("**************");
        String a = null;
        for (GrantedAuthority grantedAuthority : ga) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                a = "role user aquis";
                break;
            }
        }
        if (a == null) {
            a = "role user ko";
        }

        Label labelpanelA = new Label("panelA", a);
        add(labelpanelA);

        String texteDynamiqueParRole = "pas les droits";
        try {
            texteDynamiqueParRole = monService.methodePourUser();
            texteDynamiqueParRole = monService.methodePourAdmin();
        } catch (AccessDeniedException accessDeniedException) {
            System.out.println("il faudrait logguer une erreur");
        }

        Label labelpanelAppelService = new Label("panelAppelService", texteDynamiqueParRole);
        add(labelpanelAppelService);

        Link link = new Link("panelAppelServiceBis") {
            @Override
            public void onClick() {
                PageParameters pp = new PageParameters();
                setResponsePage(new AdminPage(pp));
            }
        };
        if (webSession.getRoles().hasRole("ROLE_ADMIN")) {
            link.setVisible(true);
        } else {
            // link.setVisible(false);
            link.setVisible(true);
        }

        add(link);

        Link link2 = new Link("lienAuth") {

            @Override
            public void onClick() {

                SecurityContext securityContext = (SecurityContext) ((MyAuthenticatedWebSession) Session.get())
                        .getAttribute("SPRING_SECURITY_CONTEXT");

                // webSession.getUsername()Roles().toString()
                PageParameters pp = new PageParameters();
                setResponsePage(new HomePage(pp));
            }
        };
        add(link2);
    }
}
