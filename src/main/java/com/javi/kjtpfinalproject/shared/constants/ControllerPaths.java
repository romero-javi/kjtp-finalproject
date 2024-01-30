package com.javi.kjtpfinalproject.shared.constants;

public final class ControllerPaths {
    // ----- USER RELATED -----
    public static final String USERS = "/api/v1/users";
    public static final String USER_ID = USERS + "/{userId}";
    public static final String CURRENT_USER = USERS + "/me";
    public static final String CURRENT_USER_ADDRESSES = CURRENT_USER + "/addresses";
    public static final String CURRENT_USER_ADDRESS_ID = CURRENT_USER_ADDRESSES + "/{addressId}";
    public static final String CURRENT_USER_PAYMENT_METHODS = CURRENT_USER + "/payment-methods";
    public static final String CURRENT_USER_PAYMENT_METHOD_ID = CURRENT_USER_PAYMENT_METHODS + "/{paymentMethodId}";



    // ----- PRODUCTS RELATED -----
    public static final String PRODUCTS = "/api/v1/products";
    public static final String PRODUCT_ID = PRODUCTS + "/{productId}";


    // ----- CHECKOUT RELATED -----
    public static final String CHECKOUT = "/api/v1/checkout";
    public static final String CHECKOUT_ID = CHECKOUT + "/{checkoutId}";
    public static final String CURRENT_USER_CHECKOUT = CURRENT_USER + "/checkout";
    public static final String CURRENT_USER_CHECKOUT_ADDRESS = CURRENT_USER_CHECKOUT + "/address";
    public static final String CURRENT_USER_CHECKOUT_PAYMENT_METHOD = CURRENT_USER_CHECKOUT + "/payment-method";
    public static final String CURRENT_USER_CHECKOUT_PRODUCT = CURRENT_USER_CHECKOUT + "/product";
    public static final String CURRENT_USER_CHECKOUT_PRODUCT_ID = CURRENT_USER_CHECKOUT_PRODUCT + "/{productId}";
    public static final String CURRENT_USER_CHECKOUT_PROCESS = CURRENT_USER_CHECKOUT + "/process";


    // ----- ORDERS RELATED -----
    public static final String CURRENT_USER_ORDERS = CURRENT_USER + "/orders";

    private ControllerPaths() {
    }
}
