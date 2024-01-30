
package com.javi.kjtpfinalproject.patterns.checkoutchain;

import com.javi.kjtpfinalproject.entities.Checkout;
import com.javi.kjtpfinalproject.entities.User;
import com.javi.kjtpfinalproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveCheckoutHandler extends CheckoutHandler {
    private final UserService userService;

    @Override
    public boolean doHandleCheckout(Checkout checkout) {
        User user = userService.findUser(checkout.getUser().getUserId().toString());
        user.setCheckout(null);
        userService.saveUser(user);

        return false;
    }
}
