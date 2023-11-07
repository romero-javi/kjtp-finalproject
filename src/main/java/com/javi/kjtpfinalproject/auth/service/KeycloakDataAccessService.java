package com.javi.kjtpfinalproject.auth.service;

import com.javi.kjtpfinalproject.auth.KeycloakProvider;
import com.javi.kjtpfinalproject.customer.dto.CustomerRegistrationDto;
import com.javi.kjtpfinalproject.shared.exceptions.DuplicatedResourceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakDataAccessService implements AuthService {
    public static final String USER_CREATION_ERROR_MESSAGE = "User already exists, enter a different email";
    private final KeycloakProvider keycloakProvider;

    /*
        Method to create keycloak user
        @return KeycloakResponseDto
     */
    @Override
    public String createUser(@Valid @NonNull CustomerRegistrationDto newUser) {
        int status;
        UsersResource usersResource = keycloakProvider.getUserResource();

        // Create the user and get the response back from the keycloak API
        Response keycloakResponse = usersResource.create(
                createUserRepresentation(
                        newUser.firstName(),
                        newUser.lastName(),
                        newUser.email(),
                        newUser.password()
                )
        );

        status = keycloakResponse.getStatus();

        if (status == 201) {
            String userId = getResponseUserId(keycloakResponse);

            RealmResource realmResource = keycloakProvider.getRealmResource();
            assignRolesToUser(realmResource, userId);

            return userId;
        } else if (status == 409) {
            log.debug(USER_CREATION_ERROR_MESSAGE);
            throw   new DuplicatedResourceException(USER_CREATION_ERROR_MESSAGE);
        } else {
            log.debug("Error while creating the user");
        }

        return null;
    }


    /*
        Method to delete keycloak user by their id
        @return void
     */
    @Override
    public void deleteUser(String userId) {
        keycloakProvider
                .getUserResource()
                .get(userId)
                .remove();
    }

    /*
        Method to create keycloak user
        @return void
     */
    @Override
    public void updateUser(String userId, @NonNull CustomerRegistrationDto user) {
        UserRepresentation userRepresentation = createUserRepresentation(user.firstName(), user.lastName(), user.email(), user.password());

        keycloakProvider
                .getUserResource()
                .get(userId)
                .update(userRepresentation);
    }

    private String getResponseUserId(Response response) {
        String path = response.getLocation().getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /*
        Method to create a UserRepresentation needed by keycloak,
        keycloak manages many things by representations
     */
    private UserRepresentation createUserRepresentation(String firstName, String lastName, String username, String password) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(username);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(
                Collections.singletonList(
                        createUsersCredentials(password)
                )
        );

        return userRepresentation;
    }

    /*
        Users are created without credentials, therefore we need to create a representation
        of the credential and assign them to the user,
    */
    private CredentialRepresentation createUsersCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    /*
        Users are created without credentials, therefore we need to create a representation
        of the credential and assign them to the user,
    */
    private void assignRolesToUser(RealmResource realmResource, String userId) {
        realmResource
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(
                        List.of(
                                realmResource.roles().get("user").toRepresentation()
                        )
                );
    }
}
