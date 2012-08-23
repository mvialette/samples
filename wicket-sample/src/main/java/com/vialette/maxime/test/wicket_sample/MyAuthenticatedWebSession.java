package com.vialette.maxime.test.wicket_sample;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class MyAuthenticatedWebSession extends AuthenticatedWebSession {

    @SpringBean(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    public MyAuthenticatedWebSession(Request request) {
        super(request);
        injectDependencies();
        ensureDependenciesNotNull();
    }

    private void ensureDependenciesNotNull() {
        if (authenticationManager == null) {
            throw new IllegalStateException("AdminSession requires an authenticationManager.");
        }
    }

    private void injectDependencies() {
        Injector.get().inject(this);
    }

    @Override
    public boolean authenticate(String username, String password) {
        boolean authenticated = false;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            this.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // SecurityContextHolder.setContext(securityContext);
            authenticated = authentication.isAuthenticated();
        } catch (AuthenticationException e) {
            System.out.println("User '" + username + "' failed to login. Reason: " + e.getMessage());
            authenticated = false;
        }
        return authenticated;
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        getRolesIfSignedIn(roles);
        return roles;
    }

    public Authentication getConnectedUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication() == null ? "guest" : SecurityContextHolder
                .getContext().getAuthentication().getName();
    }

    private void getRolesIfSignedIn(Roles roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            addRolesFromAuthentication(roles, authentication);
        }
    }

    private void addRolesFromAuthentication(Roles roles, Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
    }

}