package com.javi.kjtpfinalproject.events;

import com.javi.kjtpfinalproject.entities.User;
import com.javi.kjtpfinalproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthServerEventListener {
    private final UserRepository userRepository;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Jwt jwt = (Jwt) event.getAuthentication().getPrincipal();

        String id = jwt.getClaim("sub");
        String email = jwt.getClaim("email");
        String firstName = jwt.getClaim("given_name");
        String lastName = jwt.getClaim("family_name");

        userRepository.findById(UUID.fromString(id)).ifPresentOrElse(
                user -> {
                    if (
                            user.getEmail().equals(email)
                                    && user.getFirstName().equals(firstName)
                                    && user.getLastName().equals(lastName)
                    ) return;

                    // Update the user if their info changed
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);

                    userRepository.save(user);
                },
                () -> userRepository.save(
                        User.builder()
                                .userId(UUID.fromString(id))
                                .email(email)
                                .firstName(firstName)
                                .lastName(lastName)
                                .version(1L)
                                .build()
                )
        );
    }
}
