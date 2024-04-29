
    create table addresses (
        latitude numeric(9,6),
        longitude numeric(9,6),
        id uuid not null,
        city varchar(255),
        country varchar(255),
        formatted_address varchar(255),
        postal_code varchar(255),
        state varchar(255),
        street_name varchar(255),
        street_number varchar(255),
        primary key (id)
    );

    create table catalog_categories (
        catalog_id uuid,
        category_id uuid,
        id uuid not null,
        primary key (id)
    );

    create table catalogs (
        origin smallint check (origin between 0 and 3),
        modified_at timestamp(6),
        id uuid not null,
        merchant_id uuid,
        image_path varchar(512),
        catalog_contexts smallint array,
        primary key (id)
    );

    create table categories (
        catalog_id uuid,
        id uuid not null,
        merchant_id uuid,
        name varchar(255) not null,
        primary key (id)
    );

    create table command_orders (
        command_id uuid,
        id uuid not null,
        order_id uuid unique,
        primary key (id)
    );

    create table commands (
        pending numeric(10,2),
        prepaid numeric(10,2),
        dinner_table_id uuid,
        id uuid not null,
        primary key (id)
    );

    create table dinner_tables (
        pending numeric(10,2),
        prepaid numeric(10,2),
        id uuid not null,
        primary key (id)
    );

    create table driver_routes (
        created_at date,
        driver_id uuid,
        id uuid not null,
        route_id uuid unique,
        primary key (id)
    );

    create table drivers (
        id uuid not null,
        merchant_id uuid,
        email varchar(255),
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        primary key (id)
    );

    create table ifood_catalogs (
        catalog_id uuid unique,
        id uuid not null,
        ifood_id uuid,
        acess_token varchar(255),
        refresh_token varchar(255),
        primary key (id)
    );

    create table ifood_categories (
        category_id uuid unique,
        id uuid not null,
        ifood_id uuid,
        primary key (id)
    );

    create table ifood_items (
        id uuid not null,
        ifood_item_id uuid,
        item_id uuid unique,
        primary key (id)
    );

    create table ifood_merchants (
        id uuid not null,
        ifood_merchant_id uuid,
        merchant_id uuid unique,
        ifood_acess_token varchar(255),
        ifood_refresh_token varchar(255),
        primary key (id)
    );

    create table ifood_orders (
        id uuid not null,
        ifood_event_id uuid,
        ifood_order_id uuid,
        order_id uuid unique,
        primary key (id)
    );

    create table ifood_product_option_groups (
        id uuid not null,
        ifood_id uuid,
        product_option_group_id uuid unique,
        primary key (id)
    );

    create table ifood_product_options (
        id uuid not null,
        ifood_option_id uuid,
        product_option_id uuid unique,
        primary key (id)
    );

    create table ifood_products (
        id uuid not null,
        ifood_option_id uuid,
        product_id uuid unique,
        primary key (id)
    );

    create table item_context_modifiers (
        catalog_context smallint check (catalog_context between 0 and 3),
        id uuid not null,
        item_id uuid,
        primary key (id)
    );

    create table item_shifts (
        end_time time(6),
        friday boolean,
        monday boolean,
        saturday boolean,
        start_time time(6),
        sunday boolean,
        thursday boolean,
        tuesday boolean,
        wednesday boolean,
        id uuid not null,
        item_id uuid,
        primary key (id)
    );

    create table items (
        index integer,
        status smallint check (status between 0 and 1),
        category_id uuid,
        id uuid not null,
        merchant_id uuid,
        price_id uuid unique,
        product_id uuid,
        external_code varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table merchant_user_permissions (
        merchant_user_id uuid not null,
        permission varchar(255) not null
    );

    create table merchant_users (
        id uuid not null,
        merchant_id uuid,
        document varchar(255),
        email varchar(255),
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        primary key (id)
    );

    create table merchants (
        merchant_type smallint not null check (merchant_type between 0 and 2),
        created_at timestamp(6) not null,
        address_id uuid unique,
        document varchar(16) not null,
        id uuid not null,
        corporate_name varchar(64) not null,
        name varchar(64) not null,
        description varchar(256) not null,
        primary key (id)
    );

    create table order_additional_fees (
        value numeric(10,2),
        id uuid not null,
        order_id uuid,
        type varchar(20),
        name varchar(128),
        description varchar(1024),
        primary key (id)
    );

    create table order_benefits (
        value numeric(10,2),
        id uuid not null,
        order_id uuid,
        target varchar(255) check (target in ('ITEM','DELIVERY')),
        primary key (id)
    );

    create table order_customers (
        orders_count_on_merchant integer,
        id uuid not null,
        order_id uuid unique,
        document varchar(20),
        name varchar(20),
        phone varchar(20),
        segmentation varchar(20),
        primary key (id)
    );

    create table order_deliveries (
        delivery_date_time date,
        address_id uuid unique,
        id uuid not null,
        order_id uuid unique,
        delivery_by varchar(20) check (delivery_by in ('IFOOD','MERCHANT','ROTAFOOD')),
        mode varchar(20) check (mode in ('DEFAULT','ECONOMIC','EXPRESS')),
        pickup_code varchar(256),
        primary key (id)
    );

    create table order_indoors (
        delivery_date_time date,
        id uuid not null,
        order_id uuid unique,
        mode varchar(20) check (mode in ('DEFAULT','TABLE')),
        primary key (id)
    );

    create table order_payments (
        pending numeric(10,2),
        prepaid numeric(10,2),
        value numeric(10,2),
        id uuid not null,
        order_id uuid,
        order_payment_id uuid,
        description varchar(1024),
        method varchar(255) check (method in ('CREDIT','DEBIT','CASH','PIX')),
        primary key (id)
    );

    create table order_schedules (
        delivery_date_time_end date,
        delivery_date_time_start date,
        id uuid not null,
        order_id uuid unique,
        primary key (id)
    );

    create table order_takeouts (
        takeout_date_time date,
        id uuid not null,
        order_id uuid unique,
        mode varchar(20) check (mode in ('DEFAULT','PICKUP_AREA')),
        primary key (id)
    );

    create table order_totals (
        additional_fees numeric(10,2),
        benefits numeric(10,2),
        delivery_fee numeric(10,2),
        order_amount numeric(10,2),
        sub_total numeric(10,2),
        id uuid not null,
        order_id uuid unique,
        primary key (id)
    );

    create table order_items (
        quantity integer,
        total_price numeric(10,2),
        id uuid not null,
        item_id uuid,
        option_group_id uuid,
        option_id uuid,
        order_id uuid,
        order_item_id uuid,
        primary key (id)
    );

    create table orders (
        created_at date,
        modified_at date,
        preparation_start_date_time date,
        id uuid not null,
        merchant_id uuid,
        order_timing varchar(20) check (order_timing in ('IMMEDIATE','SCHEDULED')),
        order_type varchar(20) check (order_type in ('DELIVERY','INDOOR','TAKEOUT','COMMAND')),
        sales_channel varchar(20) check (sales_channel in ('CALL','WHATSAPP','FACEBOOK','ROTAFOOD','IFOOD','AIQFOME','RAPPI')),
        extra_info TEXT,
        primary key (id)
    );

    create table payments (
        price numeric(10,2) not null,
        payment_date timestamp(6) not null,
        id uuid not null,
        subscription_id uuid,
        name varchar(100) not null,
        mercado_pago_payment_id varchar(256),
        transaction_id varchar(512),
        status varchar(255) check (status in ('PAID','PENDING','FAILED')),
        primary key (id)
    );

    create table plans (
        price numeric(38,2) not null,
        id uuid not null,
        name varchar(100) not null,
        description varchar(255) not null,
        primary key (id)
    );

    create table prices (
        original_value numeric(10,2) not null,
        value numeric(10,2) not null,
        id uuid not null,
        item_context_modifier_id uuid,
        primary key (id)
    );

    create table product_dietary_restrictions (
        product_id uuid not null,
        restriction varchar(255) not null check (restriction in ('VEGETARIAN','VEGAN','ORGANIC','GLUTEN_FREE','SUGAR_FREE','LAC_FREE','ALCOHOLIC_DRINK','NATURAL','ZERO','DIET'))
    );

    create table product_option_groups (
        average_unit integer,
        incremental integer not null,
        index integer,
        minimum integer not null,
        id uuid not null,
        merchant_id uuid,
        product_id uuid,
        external_code varchar(255),
        name varchar(255),
        option_group_type varchar(255),
        status varchar(255),
        available_units varchar(32) array not null,
        primary key (id)
    );

    create table product_options (
        index integer not null,
        id uuid not null,
        option_group_id uuid,
        price_id uuid unique,
        product_id uuid unique,
        status varchar(255) not null check (status in ('AVALIABLE','UNAVAILABLE')),
        primary key (id)
    );

    create table products (
        weight_quantity numeric(10,2) not null,
        id uuid not null,
        image_id uuid,
        merchant_id uuid,
        product_type varchar(20) check (product_type in ('ITEM','OPTION')),
        name varchar(128) not null,
        ean varchar(256) not null,
        additional_information varchar(512) not null,
        description varchar(1024) not null,
        weight_unit varchar(255) not null check (weight_unit in ('g','kg')),
        primary key (id)
    );

    create table scale_prices (
        min_quantity integer,
        price numeric(10,2) not null,
        id uuid not null,
        primary key (id)
    );

    create table subscriptions (
        end_date date not null,
        start_date date not null,
        id uuid not null,
        merchant_user_id uuid,
        plan_id uuid unique,
        status varchar(255) check (status in ('ACTIVE','CANCELED','EXPIRED')),
        primary key (id)
    );

    create table vrp_orders (
        index integer,
        id uuid not null,
        order_id uuid,
        vrp_route_id uuid,
        primary key (id)
    );

    create table vrp_routes (
        created_at date,
        seconds_to_solve numeric(10,3),
        total_distance numeric(10,2),
        total_volume numeric(10,2),
        id uuid not null,
        vrp_id uuid,
        google_maps_link varchar(2048),
        primary key (id)
    );

    create table vrps (
        created_at date,
        solved_at date,
        id uuid not null,
        merhcant_id uuid,
        primary key (id)
    );

    alter table if exists catalog_categories 
       add constraint FKne3shibxv8tfwthgufcul5xic 
       foreign key (catalog_id) 
       references catalogs;

    alter table if exists catalog_categories 
       add constraint FKm6aan0273i831bn8aba46cb1i 
       foreign key (category_id) 
       references categories;

    alter table if exists catalogs 
       add constraint FKkx2k3chn9jryg35csnqejy9h2 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists categories 
       add constraint FKmlf78btlvj3vhqfr2fy659qrh 
       foreign key (catalog_id) 
       references catalogs;

    alter table if exists categories 
       add constraint FKp88pi8dlymagblpfl01nnoly9 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists command_orders 
       add constraint FKljwifunyfgrmg209gbtk2kequ 
       foreign key (command_id) 
       references commands;

    alter table if exists command_orders 
       add constraint FKk74m2schmepmqwdksh1s5g3lc 
       foreign key (order_id) 
       references orders;

    alter table if exists commands 
       add constraint FK6g974g7xujvms925pn0nrobbh 
       foreign key (dinner_table_id) 
       references dinner_tables;

    alter table if exists driver_routes 
       add constraint FKomqeln2wl09iep7oah57xdun9 
       foreign key (driver_id) 
       references drivers;

    alter table if exists driver_routes 
       add constraint FKuyq77lr9pixboiv96pcnkvkq 
       foreign key (route_id) 
       references vrp_routes;

    alter table if exists drivers 
       add constraint FK3tfspxsg3bsr5hh0r9gpw4awi 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists ifood_catalogs 
       add constraint FKsetqruq76crpxaeseq84lccp7 
       foreign key (catalog_id) 
       references catalogs;

    alter table if exists ifood_categories 
       add constraint FKsugr8fl0j38cayr1hlhcay8p1 
       foreign key (category_id) 
       references categories;

    alter table if exists ifood_items 
       add constraint FKd6nqxp83sf2vgo8dd8sa6tjxg 
       foreign key (item_id) 
       references items;

    alter table if exists ifood_merchants 
       add constraint FKl7n6j1fbo2rmhouio4bfw5017 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists ifood_orders 
       add constraint FKqvrsge5lh5eau5813n09kk60f 
       foreign key (order_id) 
       references orders;

    alter table if exists ifood_product_option_groups 
       add constraint FK5laibnwur60bu4imj742gk1xu 
       foreign key (product_option_group_id) 
       references product_option_groups;

    alter table if exists ifood_product_options 
       add constraint FK6k69gpd0rem0ny0sui1ccasf7 
       foreign key (product_option_id) 
       references product_options;

    alter table if exists ifood_products 
       add constraint FKev5r6fnbrimfb5jduy8mfsxkb 
       foreign key (product_id) 
       references products;

    alter table if exists item_context_modifiers 
       add constraint FK5r65577ql94v59eq9y0k3q95h 
       foreign key (item_id) 
       references items;

    alter table if exists item_shifts 
       add constraint FKqta1t2yp1bxkb3htbdon6qnpn 
       foreign key (item_id) 
       references items;

    alter table if exists items 
       add constraint FKjcdcde7htb3tyjgouo4g9xbmr 
       foreign key (category_id) 
       references categories;

    alter table if exists items 
       add constraint FK4q5seic92w20pxqo49dc4s61o 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists items 
       add constraint FKin6wwltci2mldownlm4dwccke 
       foreign key (price_id) 
       references prices;

    alter table if exists items 
       add constraint FKmtk37pxnx7d5ck7fkq2xcna4i 
       foreign key (product_id) 
       references products;

    alter table if exists merchant_user_permissions 
       add constraint FK6hy2cg55mo8td33jafpqgbccp 
       foreign key (merchant_user_id) 
       references merchant_users;

    alter table if exists merchant_users 
       add constraint FKqhms11g17dld2i65gaqw1jjah 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists merchants 
       add constraint FK63fsaqfa9t6fh74ww4xr8fpj1 
       foreign key (address_id) 
       references addresses;

    alter table if exists order_additional_fees 
       add constraint FKkdtqiv10ctegpbat27kle4sty 
       foreign key (order_id) 
       references orders;

    alter table if exists order_benefits 
       add constraint FKhlfrgd0o8snx338g2fx9ryal1 
       foreign key (order_id) 
       references orders;

    alter table if exists order_customers 
       add constraint FKlpkv3od5a977sexbwelqw15me 
       foreign key (order_id) 
       references orders;

    alter table if exists order_deliveries 
       add constraint FKnh570pxxonjq73y5bsa3e4od8 
       foreign key (address_id) 
       references addresses;

    alter table if exists order_deliveries 
       add constraint FKc758f4ym69saanjgj89jocpbx 
       foreign key (order_id) 
       references orders;

    alter table if exists order_indoors 
       add constraint FK50rfou2des735gi0lrwlkj8gg 
       foreign key (order_id) 
       references orders;

    alter table if exists order_payments 
       add constraint FK3s9vxneb3dk3plhpv9s213so0 
       foreign key (order_id) 
       references orders;

    alter table if exists order_payments 
       add constraint FK1neej2ag9ag50g05ox9ksrroa 
       foreign key (order_payment_id) 
       references order_payments;

    alter table if exists order_schedules 
       add constraint FK5x1171pqbbsrtkx3um01s1e35 
       foreign key (order_id) 
       references orders;

    alter table if exists order_takeouts 
       add constraint FKm5fv24fgujjsw9sw9ekonbfkd 
       foreign key (order_id) 
       references orders;

    alter table if exists order_totals 
       add constraint FK8nvxhhhu1yhll40dnmuygjl6o 
       foreign key (order_id) 
       references orders;

    alter table if exists order_items 
       add constraint FK88tn2oqcxl1034banqif9r70x 
       foreign key (item_id) 
       references items;

    alter table if exists order_items 
       add constraint FKbioxgbv59vetrxe0ejfubep1w 
       foreign key (order_id) 
       references orders;

    alter table if exists order_items 
       add constraint FKpjfxxp4p3oux0k0eu636w9xgi 
       foreign key (option_id) 
       references product_options;

    alter table if exists order_items 
       add constraint FKqau8peu9fifivi020ka061240 
       foreign key (option_group_id) 
       references product_option_groups;

    alter table if exists order_items 
       add constraint FK2hjaie4vdenqy2nb58o42ceaw 
       foreign key (order_item_id) 
       references order_items;

    alter table if exists orders 
       add constraint FKtqvpn5gy0ajx8fip3deoidfxd 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists payments 
       add constraint FKa3xnf2o6mt8cqbewvq2ouq3rq 
       foreign key (subscription_id) 
       references subscriptions;

    alter table if exists prices 
       add constraint FKpxmlqxfkmk01dxj85onhpv5y8 
       foreign key (item_context_modifier_id) 
       references item_context_modifiers;

    alter table if exists product_dietary_restrictions 
       add constraint FKl90fuejlfbw63xskeg7iqsh15 
       foreign key (product_id) 
       references products;

    alter table if exists product_option_groups 
       add constraint FKpliyu9n0fdacwlcl7uoep3j44 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists product_option_groups 
       add constraint FKdux2pxpy018siooo4j11odfo8 
       foreign key (product_id) 
       references products;

    alter table if exists product_options 
       add constraint FKb73qd7jnikjd86nsns2m8f0wn 
       foreign key (option_group_id) 
       references product_option_groups;

    alter table if exists product_options 
       add constraint FKd2ajcaqmkf8rqpk0jyko1hixg 
       foreign key (price_id) 
       references prices;

    alter table if exists product_options 
       add constraint FK8vv4f8fru80wxocwgxwsrow61 
       foreign key (product_id) 
       references products;

    alter table if exists products 
       add constraint FK6qr76g32syawj8yn8rdigocg 
       foreign key (image_id) 
       references catalogs;

    alter table if exists products 
       add constraint FKt1yvv81v320ba41fq28k7had2 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists subscriptions 
       add constraint FKb1uf5qnxi6uj95se8ykydntl1 
       foreign key (plan_id) 
       references plans;

    alter table if exists subscriptions 
       add constraint FKct7p82efq7u73jdxticea5csi 
       foreign key (merchant_user_id) 
       references merchant_users;

    alter table if exists vrp_orders 
       add constraint FKr2o8wntwwkxi7750nk7y5jds6 
       foreign key (order_id) 
       references orders;

    alter table if exists vrp_orders 
       add constraint FKo42d15fi4iu4qaerw8v4y83id 
       foreign key (vrp_route_id) 
       references vrp_routes;

    alter table if exists vrp_routes 
       add constraint FKt0na9mb4lon2bd457phhgp5bc 
       foreign key (vrp_id) 
       references vrps;

    alter table if exists vrps 
       add constraint FK3h340mjb1347co602kd48q4tw 
       foreign key (merhcant_id) 
       references merchants;
