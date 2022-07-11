package com.orderservice.util;

import com.orderservice.domain.model.output.User;
import com.orderservice.service.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSecurity {
    private final UserClient userClient;

    /**
     * When user is authenticated, they only can access their own information
     * This function retrieves authentication info in security context and get user principal,
     * then when user request user info at route user/:userId,
     * it checks for whether the user has userId in the route is the user that authenticated.
     * @param authentication Authentication
     * @param userId User id
     * @return true or false
     */
    public boolean hasUserId(Authentication authentication, Long userId) {
        User user = userClient.findByUserId(userId).getData();
        return user.getUsername().equals(authentication.getPrincipal());
    }
}
