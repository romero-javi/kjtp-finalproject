    CREATE TABLE checkout (
        total DECIMAL(38,2) NOT NULL,
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        checkout_id BINARY(16) NOT NULL,
        payment_method_id BINARY(16),
        user_id BINARY(16),
        checkout_address_id VARCHAR(36),
        PRIMARY KEY (checkout_id)
    ) ENGINE=InnoDB;

    CREATE TABLE checkout_address (
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        address_id VARCHAR(36) NOT NULL,
        city VARCHAR(255) NOT NULL,
        country VARCHAR(255) NOT NULL,
        postal_code VARCHAR(255) NOT NULL,
        street VARCHAR(255) NOT NULL,
        PRIMARY KEY (address_id)
    ) ENGINE=InnoDB;

    CREATE TABLE checkout_detail (
        quantity_to_buy INTEGER NOT NULL,
        subtotal DECIMAL(38,2) NOT NULL,
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        checkout_detail_id BINARY(16) NOT NULL,
        checkout_id BINARY(16),
        product_id BINARY(16),
        PRIMARY KEY (checkout_detail_id)
    ) ENGINE=InnoDB;

    CREATE TABLE delivery (
        total_paid DECIMAL(38,2) NOT NULL,
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        address_id BINARY(16) NOT NULL,
        delivery_id BINARY(16) NOT NULL,
        PRIMARY KEY (delivery_id)
    ) ENGINE=InnoDB;

    CREATE TABLE delivery_address (
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        address_id BINARY(16) NOT NULL,
        city VARCHAR(255) NOT NULL,
        country VARCHAR(255) NOT NULL,
        postal_code VARCHAR(255) NOT NULL,
        street VARCHAR(255) NOT NULL,
        PRIMARY KEY (address_id)
    ) ENGINE=InnoDB;

    CREATE TABLE order_address (
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        address_id BINARY(16) NOT NULL,
        city VARCHAR(255) NOT NULL,
        country VARCHAR(255) NOT NULL,
        postal_code VARCHAR(255) NOT NULL,
        street VARCHAR(255) NOT NULL,
        PRIMARY KEY (address_id)
    ) ENGINE=InnoDB;

    CREATE TABLE order_detail (
        quantity_to_buy INTEGER,
        subtotal DECIMAL(38,2),
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        order_detail_id BINARY(16) NOT NULL,
        order_id BINARY(16),
        product_id BINARY(16),
        PRIMARY KEY (order_detail_id)
    ) ENGINE=InnoDB;

    CREATE TABLE orders (
        total DECIMAL(38,2) NOT NULL,
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        order_address_id BINARY(16),
        order_id BINARY(16) NOT NULL,
        order_payment_id BINARY(16),
        user_id BINARY(16),
        PRIMARY KEY (order_id)
    ) ENGINE=InnoDB;

    CREATE TABLE payment_method (
        deposit DECIMAL(38,2) NOT NULL,
        expire_month INTEGER,
        expire_year INTEGER,
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        id BINARY(16) NOT NULL,
        user_id BINARY(16),
        payment_type VARCHAR(31) NOT NULL,
        card_holder_name VARCHAR(255),
        card_number VARCHAR(255),
        card_type VARCHAR(255),
        email VARCHAR(255),
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;

    CREATE TABLE product (
        price DECIMAL(38,2) NOT NULL,
        quantity INTEGER NOT NULL,
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        product_id BINARY(16) NOT NULL,
        brand VARCHAR(255) NOT NULL,
        name VARCHAR(255) NOT NULL,
        PRIMARY KEY (product_id)
    ) ENGINE=InnoDB;

    CREATE TABLE user_address (
        created_date DATETIME(6),
        last_modified_date DATETIME(6),
        version BIGINT,
        address_id BINARY(16) NOT NULL,
        user_id BINARY(16),
        city VARCHAR(255) NOT NULL,
        country VARCHAR(255) NOT NULL,
        postal_code VARCHAR(255) NOT NULL,
        street VARCHAR(255) NOT NULL,
        PRIMARY KEY (address_id)
    ) ENGINE=InnoDB;

    CREATE TABLE users (
        created_date DATETIME(6) NOT NULL,
        last_modified_date DATETIME(6) NOT NULL,
        version BIGINT NOT NULL,
        user_id BINARY(16) NOT NULL,
        email VARCHAR(255) NOT NULL,
        first_name VARCHAR(255) NOT NULL,
        last_name VARCHAR(255) NOT NULL,
        PRIMARY KEY (user_id)
    ) ENGINE=InnoDB;

    ALTER TABLE checkout
        ADD CONSTRAINT payment_method_id
            UNIQUE (payment_method_id);

    ALTER TABLE checkout
        ADD CONSTRAINT user_id
            UNIQUE (user_id);

    ALTER TABLE checkout
        ADD CONSTRAINT checkout_address_id
            UNIQUE (checkout_address_id);

    ALTER TABLE delivery
        ADD CONSTRAINT address_id
            UNIQUE (address_id);

    ALTER TABLE orders
        ADD CONSTRAINT order_address_id
            UNIQUE (order_address_id);

    ALTER TABLE users
        ADD CONSTRAINT email
            UNIQUE (email);

    ALTER TABLE checkout
        ADD CONSTRAINT checkout_address_id
            FOREIGN KEY (checkout_address_id)
                REFERENCES checkout_address (address_id);

    ALTER TABLE checkout
        ADD CONSTRAINT payment_method_id
            FOREIGN KEY (payment_method_id)
                REFERENCES payment_method (id);

    ALTER TABLE checkout
        ADD CONSTRAINT user_id
            FOREIGN KEY (user_id)
                REFERENCES users (user_id);

    ALTER TABLE checkout_detail
        ADD CONSTRAINT checkout_id
            FOREIGN KEY (checkout_id)
                REFERENCES checkout (checkout_id);

    ALTER TABLE checkout_detail
        ADD CONSTRAINT product_id
            FOREIGN KEY (product_id)
                REFERENCES product (product_id);

    ALTER TABLE delivery
        ADD CONSTRAINT address_id
            FOREIGN KEY (address_id)
                REFERENCES delivery_address (address_id);

    ALTER TABLE order_detail
        ADD CONSTRAINT order_id
            FOREIGN KEY (order_id)
                REFERENCES orders (order_id);

    ALTER TABLE order_detail
        ADD CONSTRAINT product_id_order
            FOREIGN KEY (product_id)
                REFERENCES product (product_id);

    ALTER TABLE orders
        ADD CONSTRAINT order_address_id
            FOREIGN KEY (order_address_id)
                REFERENCES order_address (address_id);

    ALTER TABLE orders
        ADD CONSTRAINT order_payment_id
            FOREIGN KEY (order_payment_id)
                REFERENCES payment_method (id);

    ALTER TABLE orders
        ADD CONSTRAINT user_id_order
            FOREIGN KEY (user_id)
                REFERENCES users (user_id);

    ALTER TABLE payment_method
        ADD CONSTRAINT user_id_payment
            FOREIGN KEY (user_id)
                REFERENCES users (user_id);

    ALTER TABLE user_address
        ADD CONSTRAINT user_id_address
            FOREIGN KEY (user_id)
                REFERENCES users (user_id);
