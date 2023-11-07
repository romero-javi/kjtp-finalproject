package com.javi.kjtpfinalproject.auth;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// Class to get the means to directly interact with keycloak API

@Component
public class KeycloakProvider {
    @Value("${keycloak-url}")
    private String keycloakUrl;
    @Value("${keycloak-admin-user}")
    private String adminUser;
    @Value("${keycloak-admin-password}")
    private String adminPassword;
    @Value("${client-secret}")
    private String clientSecret;

    public RealmResource getRealmResource() {
        String realmName = "kjtp-dev";
        String realmMaster = "master";
        String clientId = "admin-cli";

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realmMaster)
                .clientId(clientId)
                .username(adminUser)
                .password(adminPassword)
                .clientSecret(clientSecret)
                .resteasyClient(
                    new ResteasyClientBuilderImpl()
                            .connectionPoolSize(10)
                            .build()
                ).build();

        return keycloak.realm(realmName);
    }

    public UsersResource getUserResource() {
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}
