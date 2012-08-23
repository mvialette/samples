package com.vialette.maxime.test.wicket_sample.service;

import org.apache.wicket.Session;

import com.vialette.maxime.test.wicket_sample.MyAuthenticatedWebSession;

public class MonServiceImpl implements MonService {

    @Override
    public String afficherMonRoleLePlusEleve() {
        MyAuthenticatedWebSession webSession = ((MyAuthenticatedWebSession) Session.get());

        if (webSession.getRoles().hasRole("ROLE_ADMIN")) {
            return "vous etes administrateur";
        } else if (webSession.getRoles().hasRole("ROLE_USER")) {
            return "vous etes utilisateur";
        } else {
            return "vous n'avez aucun role";
        }
    }

    @Override
    public String methodePourUser() {
        return "du texte retourné si vous avez le role utilisateur";
    }

    @Override
    public String methodePourAdmin() {
        return "du texte retourné si vous avez le role administrateur";
    }

}
