    CREATE TABLE checkout (
        total numeric(38,2) not null,
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        checkout_id uuid not null,
        payment_method_id uuid unique,
        user_id uuid unique,
        checkout_address_id varchar(36) unique,
        primary key (checkout_id)
    );

    CREATE TABLE checkout_address (
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        address_id varchar(36) not null,
        city varchar(255) not null,
        country varchar(255) not null,
        postal_code varchar(255) not null,
        street varchar(255) not null,
        primary key (address_id)
    );

    CREATE TABLE checkout_detail (
        quantity_to_buy integer not null,
        subtotal numeric(38,2) not null,
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        checkout_detail_id uuid not null,
        checkout_id uuid,
        product_id uuid,
        primary key (checkout_detail_id)
    );

    CREATE TABLE delivery (
        total_paid numeric(38,2) not null,
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        address_id uuid not null unique,
        delivery_id uuid not null,
        primary key (delivery_id)
    );

    CREATE TABLE delivery_address (
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        address_id uuid not null,
        city varchar(255) not null,
        country varchar(255) not null,
        postal_code varchar(255) not null,
        street varchar(255) not null,
        primary key (address_id)
    );

    CREATE TABLE order_address (
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        address_id uuid not null,
        city varchar(255) not null,
        country varchar(255) not null,
        postal_code varchar(255) not null,
        street varchar(255) not null,
        primary key (address_id)
    );

    CREATE TABLE order_detail (
        quantity_to_buy integer,
        subtotal numeric(38,2),
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        order_detail_id uuid not null,
        order_id uuid,
        product_id uuid,
        primary key (order_detail_id)
    );

    CREATE TABLE orders (
        total numeric(38,2) not null,
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        order_address_id uuid unique,
        order_id uuid not null,
        order_payment_id uuid,
        user_id uuid,
        primary key (order_id)
    );

    CREATE TABLE payment_method (
        deposit numeric(38,2) not null,
        expire_month integer,
        expire_year integer,
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        id uuid not null,
        user_id uuid,
        payment_type varchar(31) not null,
        card_holder_name varchar(255),
        card_number varchar(255),
        card_type varchar(255),
        email varchar(255),
        primary key (id)
    );

    CREATE TABLE product (
        price numeric(38,2) not null,
        quantity integer not null,
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        product_id uuid not null,
        brand varchar(255) not null,
        name varchar(255) not null,
        primary key (product_id)
    );

    CREATE TABLE user_address (
        created_date timestamp(6),
        last_modified_date timestamp(6),
        version bigint,
        address_id uuid not null,
        user_id uuid,
        city varchar(255) not null,
        country varchar(255) not null,
        postal_code varchar(255) not null,
        street varchar(255) not null,
        primary key (address_id)
    );

    CREATE TABLE users (
        created_date timestamp(6) not null,
        last_modified_date timestamp(6) not null,
        version bigint not null,
        user_id uuid not null,
        email varchar(255) not null unique,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        primary key (user_id)
    );

    ALTER TABLE IF EXISTS checkout
        ADD CONSTRAINT checkout_address_id
            FOREIGN KEY (checkout_address_id)
                REFERENCES checkout_address;

    ALTER TABLE IF EXISTS checkout
        ADD CONSTRAINT payment_method_id
            FOREIGN KEY (payment_method_id)
                REFERENCES payment_method;

    ALTER TABLE IF EXISTS checkout
        ADD CONSTRAINT user_id_checkout
            FOREIGN KEY (user_id)
                REFERENCES users;

    ALTER TABLE IF EXISTS checkout_detail
        ADD CONSTRAINT checkout_id
            FOREIGN KEY (checkout_id)
                REFERENCES checkout;

    ALTER TABLE IF EXISTS checkout_detail
        ADD CONSTRAINT product_id_checkout
            FOREIGN KEY (product_id)
                REFERENCES product;

    ALTER TABLE IF EXISTS delivery
        ADD CONSTRAINT address_id_delivery
            FOREIGN KEY (address_id)
                REFERENCES delivery_address;

    ALTER TABLE IF EXISTS order_detail
        ADD CONSTRAINT order_id
            FOREIGN KEY (order_id)
                REFERENCES orders;

    ALTER TABLE IF EXISTS order_detail
        ADD CONSTRAINT product_id_order_detail
            FOREIGN KEY (product_id)
                REFERENCES product;

    ALTER TABLE IF EXISTS orders
        ADD CONSTRAINT order_address_id
            FOREIGN KEY (order_address_id)
                REFERENCES order_address;

    ALTER TABLE IF EXISTS orders
        ADD CONSTRAINT order_payment_id
            FOREIGN KEY (order_payment_id)
                REFERENCES payment_method;

    ALTER TABLE IF EXISTS orders
        ADD CONSTRAINT user_id_orders
            FOREIGN KEY (user_id)
                REFERENCES users;

    ALTER TABLE IF EXISTS payment_method
        ADD CONSTRAINT user_id_payment_method
            FOREIGN KEY (user_id)
                REFERENCES users;

    ALTER TABLE IF EXISTS user_address
        ADD CONSTRAINT user_id_user_address
            FOREIGN KEY (user_id)
                REFERENCES users;

