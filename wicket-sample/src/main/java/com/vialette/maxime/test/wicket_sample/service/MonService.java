package com.vialette.maxime.test.wicket_sample.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service("monService")
public interface MonService {

    public String afficherMonRoleLePlusEleve();

    @Secured("ROLE_USER")
    public String methodePourUser();

    @Secured("ROLE_ADMIN")
    public String methodePourAdmin();

}
