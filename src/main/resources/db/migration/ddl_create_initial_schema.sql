
    create table addresses (
        latitude numeric(9,6),
        longitude numeric(9,6),
        id uuid not null,
        city varchar(255),
        complement varchar(255),
        country varchar(255),
        formatted_address varchar(255),
        neighborhood varchar(255),
        postal_code varchar(255),
        state varchar(255),
        street_name varchar(255),
        street_number varchar(255),
        primary key (id)
    );

    create table catalog_categories (
        catalog_id uuid not null,
        category_id uuid not null,
        id uuid not null,
        primary key (id)
    );

    create table catalogs (
        modified_at timestamp(6),
        i_food_catalog_id uuid,
        id uuid not null,
        merchant_id uuid,
        catalog_context varchar(255) check (catalog_context in ('TABLE','DELIVERY','IFOOD')),
        status varchar(255) check (status in ('AVALIABLE','UNAVAILABLE')),
        primary key (id)
    );

    create table categories (
        index integer not null,
        status smallint not null check (status between 0 and 1),
        i_food_category_id uuid,
        id uuid not null,
        merchant_id uuid,
        name varchar(255) not null,
        template varchar(255) check (template in ('DEFAULT','PIZZA')),
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

    create table context_modifiers (
        id uuid not null,
        item_id uuid,
        option_id uuid,
        parent_option_modifier_id uuid,
        price_id uuid unique,
        catalog_context varchar(255) check (catalog_context in ('TABLE','DELIVERY','IFOOD')),
        status varchar(255) check (status in ('AVALIABLE','UNAVAILABLE')),
        primary key (id)
    );

    create table default_products (
        id uuid not null,
        name varchar(128) not null,
        ean varchar(256),
        description varchar(1024) not null,
        i_food_image_path varchar(255),
        image_path varchar(255),
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

    create table images (
        i_food_image_id uuid,
        id uuid not null,
        merchant_id uuid,
        i_food_image_path varchar(255),
        path varchar(255),
        primary key (id)
    );

    create table items (
        index integer,
        category_id uuid,
        i_food_item_id uuid,
        id uuid not null,
        merchant_id uuid,
        product_id uuid not null unique,
        status varchar(255) check (status in ('AVALIABLE','UNAVAILABLE')),
        type varchar(255) check (type in ('DEFAULT','PIZZA')),
        primary key (id)
    );

    create table merchant_users (
        has_owner boolean not null,
        id uuid not null,
        merchant_id uuid,
        email varchar(255),
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        merchant_permissions text[] not null,
        primary key (id)
    );

    create table merchants (
        merchant_type smallint not null check (merchant_type between 0 and 2),
        created_at timestamp(6) not null,
        address_id uuid unique,
        document varchar(16),
        id uuid not null,
        corporate_name varchar(64),
        name varchar(64),
        description varchar(256),
        document_type varchar(255) check (document_type in ('CPF','CNPJ')),
        image_path varchar(255),
        online_name varchar(255),
        primary key (id)
    );

    create table option_groups (
        i_food_option_group_id uuid,
        id uuid not null,
        merchant_id uuid,
        name varchar(255),
        option_group_type varchar(255) check (option_group_type in ('DEFAULT','SIZE','TOPPING','CRUST','EDGE')),
        status varchar(255) check (status in ('AVALIABLE','UNAVAILABLE')),
        primary key (id)
    );

    create table options (
        index integer not null,
        i_food_option_id uuid,
        id uuid not null,
        option_group_id uuid,
        product_id uuid unique,
        status varchar(255) not null check (status in ('AVALIABLE','UNAVAILABLE')),
        fractions integer[],
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

    create table packagings (
        lenght_cm numeric(10,2) not null,
        thickness_cm numeric(10,2) not null,
        width_cm numeric(10,2) not null,
        id uuid not null,
        merchant_id uuid not null,
        name varchar(64) not null,
        image_path varchar(255),
        primary key (id)
    );

    create table parent_option_modifiers (
        id uuid not null,
        option_id uuid unique,
        price_id uuid unique,
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
        primary key (id)
    );

    create table product_option_group (
        index integer,
        max integer,
        min integer,
        id uuid not null,
        option_group_id uuid,
        product_id uuid,
        primary key (id)
    );

    create table product_packagings (
        quantity_per_package integer,
        id uuid not null,
        packaging_id uuid,
        product_id uuid,
        primary key (id)
    );

    create table products (
        use_side_bag boolean,
        id uuid not null,
        merchant_id uuid not null,
        name varchar(128) not null,
        ean varchar(256),
        additional_information varchar(512),
        description varchar(1024) not null,
        image_path varchar(255),
        serving varchar(255) not null check (serving in ('NOT_APPLICABLE','SERVES_1','SERVES_2','SERVES_3','SERVES_4')),
        dietary_restrictions text[],
        multiple_images text[],
        tags text[],
        primary key (id)
    );

    create table scale_prices (
        min_quantity integer not null,
        value numeric(10,2) not null,
        id uuid not null,
        price_id uuid,
        primary key (id)
    );

    create table selling_options (
        average_unit numeric(38,2) not null,
        incremental numeric(38,2) not null,
        minimum numeric(38,2) not null,
        id uuid not null,
        product_id uuid,
        available_units text[] not null,
        primary key (id)
    );

    create table shifts (
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
        merchant_id uuid,
        primary key (id)
    );

    create table subscriptions (
        end_date timestamp(6) not null,
        start_date timestamp(6) not null,
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
        distance_meters numeric(10,2),
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

    create table weights (
        quantity integer not null,
        unit smallint not null check (unit between 0 and 1),
        id uuid not null,
        product_id uuid unique,
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

    alter table if exists context_modifiers 
       add constraint FKamcatp9f3yjuc04dgr89w9maf 
       foreign key (item_id) 
       references items;

    alter table if exists context_modifiers 
       add constraint FKfmu9ao7cd0c1nf2ho8bb7llx1 
       foreign key (option_id) 
       references options;

    alter table if exists context_modifiers 
       add constraint FKru5if278r7mqhfpyo7os9xubm 
       foreign key (parent_option_modifier_id) 
       references parent_option_modifiers;

    alter table if exists context_modifiers 
       add constraint FKqby6ip2wukh6b6qeciuf5udvc 
       foreign key (price_id) 
       references prices;

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

    alter table if exists images 
       add constraint FK9iot8c0njp5fku7gvkdiavups 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists items 
       add constraint FKjcdcde7htb3tyjgouo4g9xbmr 
       foreign key (category_id) 
       references categories;

    alter table if exists items 
       add constraint FK4q5seic92w20pxqo49dc4s61o 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists items 
       add constraint FKmtk37pxnx7d5ck7fkq2xcna4i 
       foreign key (product_id) 
       references products;

    alter table if exists merchant_users 
       add constraint FKqhms11g17dld2i65gaqw1jjah 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists merchants 
       add constraint FK63fsaqfa9t6fh74ww4xr8fpj1 
       foreign key (address_id) 
       references addresses;

    alter table if exists option_groups 
       add constraint FKirun1bkvvvtfv5y5l46upqrhe 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists options 
       add constraint FK3d2wqyaloehtbx2xpc0qbe91s 
       foreign key (option_group_id) 
       references option_groups;

    alter table if exists options 
       add constraint FK2m37jcoh1yt06dsivrphak718 
       foreign key (product_id) 
       references products;

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
       add constraint FKei5ne8fxxpihl6vo3fee0j90w 
       foreign key (option_id) 
       references options;

    alter table if exists order_items 
       add constraint FKia966c7ygbl0g0g7o8p2nhpah 
       foreign key (option_group_id) 
       references option_groups;

    alter table if exists order_items 
       add constraint FK2hjaie4vdenqy2nb58o42ceaw 
       foreign key (order_item_id) 
       references order_items;

    alter table if exists orders 
       add constraint FKtqvpn5gy0ajx8fip3deoidfxd 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists packagings 
       add constraint FK9pflxabp2k40s1n66ov294ndh 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists parent_option_modifiers 
       add constraint FK7jjebkg01p2qrier6wmjjwn77 
       foreign key (option_id) 
       references options;

    alter table if exists parent_option_modifiers 
       add constraint FKjs196g4irly7t845i8vpss8y2 
       foreign key (price_id) 
       references prices;

    alter table if exists payments 
       add constraint FKa3xnf2o6mt8cqbewvq2ouq3rq 
       foreign key (subscription_id) 
       references subscriptions;

    alter table if exists product_option_group 
       add constraint FKaeh0ld6gggusg2pfq5o3hd056 
       foreign key (option_group_id) 
       references option_groups;

    alter table if exists product_option_group 
       add constraint FKq3ymvu7uw91ddr7gyds8dhrr4 
       foreign key (product_id) 
       references products;

    alter table if exists product_packagings 
       add constraint FKsmryvekk23d990a2frkvw0w3k 
       foreign key (packaging_id) 
       references packagings;

    alter table if exists product_packagings 
       add constraint FK4g7qg98oyqh3a5tna21q34o0s 
       foreign key (product_id) 
       references products;

    alter table if exists products 
       add constraint FKt1yvv81v320ba41fq28k7had2 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists scale_prices 
       add constraint FK41c2icku1gau5d08hoeta3m75 
       foreign key (price_id) 
       references prices;

    alter table if exists selling_options 
       add constraint FKpngv5jnjwj1a3i8tndh32xn06 
       foreign key (product_id) 
       references products;

    alter table if exists shifts 
       add constraint FKj37msba48037erxhxp0xyh7qr 
       foreign key (item_id) 
       references items;

    alter table if exists shifts 
       add constraint FK6bx4ph95hwbebowtse3o5ldu7 
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

    alter table if exists weights 
       add constraint FKimwah0owad2wrhqwd78iwbgnh 
       foreign key (product_id) 
       references products;

    create table addresses (
        latitude numeric(9,6),
        longitude numeric(9,6),
        id uuid not null,
        city varchar(255),
        complement varchar(255),
        country varchar(255),
        formatted_address varchar(255),
        neighborhood varchar(255),
        postal_code varchar(255),
        state varchar(255),
        street_name varchar(255),
        street_number varchar(255),
        primary key (id)
    );

    create table catalog_categories (
        catalog_id uuid not null,
        category_id uuid not null,
        id uuid not null,
        primary key (id)
    );

    create table catalogs (
        modified_at timestamp(6),
        i_food_catalog_id uuid,
        id uuid not null,
        merchant_id uuid,
        catalog_context varchar(255) check (catalog_context in ('TABLE','DELIVERY','IFOOD')),
        status varchar(255) check (status in ('AVALIABLE','UNAVAILABLE')),
        primary key (id)
    );

    create table categories (
        index integer not null,
        status smallint not null check (status between 0 and 1),
        i_food_category_id uuid,
        id uuid not null,
        merchant_id uuid,
        name varchar(255) not null,
        template varchar(255) check (template in ('DEFAULT','PIZZA')),
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

    create table context_modifiers (
        id uuid not null,
        item_id uuid,
        option_id uuid,
        parent_option_modifier_id uuid,
        price_id uuid unique,
        catalog_context varchar(255) check (catalog_context in ('TABLE','DELIVERY','IFOOD')),
        status varchar(255) check (status in ('AVALIABLE','UNAVAILABLE')),
        primary key (id)
    );

    create table default_products (
        id uuid not null,
        name varchar(128) not null,
        ean varchar(256),
        description varchar(1024) not null,
        i_food_image_path varchar(255),
        image_path varchar(255),
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

    create table images (
        i_food_image_id uuid,
        id uuid not null,
        merchant_id uuid,
        i_food_image_path varchar(255),
        path varchar(255),
        primary key (id)
    );

    create table items (
        index integer,
        category_id uuid,
        i_food_item_id uuid,
        id uuid not null,
        merchant_id uuid,
        product_id uuid not null unique,
        status varchar(255) check (status in ('AVALIABLE','UNAVAILABLE')),
        type varchar(255) check (type in ('DEFAULT','PIZZA')),
        primary key (id)
    );

    create table merchant_users (
        has_owner boolean not null,
        id uuid not null,
        merchant_id uuid,
        email varchar(255),
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        merchant_permissions text[] not null,
        primary key (id)
    );

    create table merchants (
        merchant_type smallint not null check (merchant_type between 0 and 2),
        created_at timestamp(6) not null,
        address_id uuid unique,
        document varchar(16),
        id uuid not null,
        corporate_name varchar(64),
        name varchar(64),
        description varchar(256),
        document_type varchar(255) check (document_type in ('CPF','CNPJ')),
        image_path varchar(255),
        online_name varchar(255),
        primary key (id)
    );

    create table option_groups (
        i_food_option_group_id uuid,
        id uuid not null,
        merchant_id uuid,
        name varchar(255),
        option_group_type varchar(255) check (option_group_type in ('DEFAULT','SIZE','TOPPING','CRUST','EDGE')),
        status varchar(255) check (status in ('AVALIABLE','UNAVAILABLE')),
        primary key (id)
    );

    create table options (
        index integer not null,
        i_food_option_id uuid,
        id uuid not null,
        option_group_id uuid,
        product_id uuid unique,
        status varchar(255) not null check (status in ('AVALIABLE','UNAVAILABLE')),
        fractions integer[],
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

    create table packagings (
        lenght_cm numeric(10,2) not null,
        thickness_cm numeric(10,2) not null,
        width_cm numeric(10,2) not null,
        id uuid not null,
        merchant_id uuid not null,
        name varchar(64) not null,
        image_path varchar(255),
        primary key (id)
    );

    create table parent_option_modifiers (
        id uuid not null,
        option_id uuid unique,
        price_id uuid unique,
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
        primary key (id)
    );

    create table product_option_group (
        index integer,
        max integer,
        min integer,
        id uuid not null,
        option_group_id uuid,
        product_id uuid,
        primary key (id)
    );

    create table product_packagings (
        quantity_per_package integer,
        id uuid not null,
        packaging_id uuid,
        product_id uuid,
        primary key (id)
    );

    create table products (
        use_side_bag boolean,
        id uuid not null,
        merchant_id uuid not null,
        name varchar(128) not null,
        ean varchar(256),
        additional_information varchar(512),
        description varchar(1024) not null,
        image_path varchar(255),
        serving varchar(255) not null check (serving in ('NOT_APPLICABLE','SERVES_1','SERVES_2','SERVES_3','SERVES_4')),
        dietary_restrictions text[],
        multiple_images text[],
        tags text[],
        primary key (id)
    );

    create table scale_prices (
        min_quantity integer not null,
        value numeric(10,2) not null,
        id uuid not null,
        price_id uuid,
        primary key (id)
    );

    create table selling_options (
        average_unit numeric(38,2) not null,
        incremental numeric(38,2) not null,
        minimum numeric(38,2) not null,
        id uuid not null,
        product_id uuid,
        available_units text[] not null,
        primary key (id)
    );

    create table shifts (
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
        merchant_id uuid,
        primary key (id)
    );

    create table subscriptions (
        end_date timestamp(6) not null,
        start_date timestamp(6) not null,
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
        distance_meters numeric(10,2),
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

    create table weights (
        quantity integer not null,
        unit smallint not null check (unit between 0 and 1),
        id uuid not null,
        product_id uuid unique,
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

    alter table if exists context_modifiers 
       add constraint FKamcatp9f3yjuc04dgr89w9maf 
       foreign key (item_id) 
       references items;

    alter table if exists context_modifiers 
       add constraint FKfmu9ao7cd0c1nf2ho8bb7llx1 
       foreign key (option_id) 
       references options;

    alter table if exists context_modifiers 
       add constraint FKru5if278r7mqhfpyo7os9xubm 
       foreign key (parent_option_modifier_id) 
       references parent_option_modifiers;

    alter table if exists context_modifiers 
       add constraint FKqby6ip2wukh6b6qeciuf5udvc 
       foreign key (price_id) 
       references prices;

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

    alter table if exists images 
       add constraint FK9iot8c0njp5fku7gvkdiavups 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists items 
       add constraint FKjcdcde7htb3tyjgouo4g9xbmr 
       foreign key (category_id) 
       references categories;

    alter table if exists items 
       add constraint FK4q5seic92w20pxqo49dc4s61o 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists items 
       add constraint FKmtk37pxnx7d5ck7fkq2xcna4i 
       foreign key (product_id) 
       references products;

    alter table if exists merchant_users 
       add constraint FKqhms11g17dld2i65gaqw1jjah 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists merchants 
       add constraint FK63fsaqfa9t6fh74ww4xr8fpj1 
       foreign key (address_id) 
       references addresses;

    alter table if exists option_groups 
       add constraint FKirun1bkvvvtfv5y5l46upqrhe 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists options 
       add constraint FK3d2wqyaloehtbx2xpc0qbe91s 
       foreign key (option_group_id) 
       references option_groups;

    alter table if exists options 
       add constraint FK2m37jcoh1yt06dsivrphak718 
       foreign key (product_id) 
       references products;

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
       add constraint FKei5ne8fxxpihl6vo3fee0j90w 
       foreign key (option_id) 
       references options;

    alter table if exists order_items 
       add constraint FKia966c7ygbl0g0g7o8p2nhpah 
       foreign key (option_group_id) 
       references option_groups;

    alter table if exists order_items 
       add constraint FK2hjaie4vdenqy2nb58o42ceaw 
       foreign key (order_item_id) 
       references order_items;

    alter table if exists orders 
       add constraint FKtqvpn5gy0ajx8fip3deoidfxd 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists packagings 
       add constraint FK9pflxabp2k40s1n66ov294ndh 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists parent_option_modifiers 
       add constraint FK7jjebkg01p2qrier6wmjjwn77 
       foreign key (option_id) 
       references options;

    alter table if exists parent_option_modifiers 
       add constraint FKjs196g4irly7t845i8vpss8y2 
       foreign key (price_id) 
       references prices;

    alter table if exists payments 
       add constraint FKa3xnf2o6mt8cqbewvq2ouq3rq 
       foreign key (subscription_id) 
       references subscriptions;

    alter table if exists product_option_group 
       add constraint FKaeh0ld6gggusg2pfq5o3hd056 
       foreign key (option_group_id) 
       references option_groups;

    alter table if exists product_option_group 
       add constraint FKq3ymvu7uw91ddr7gyds8dhrr4 
       foreign key (product_id) 
       references products;

    alter table if exists product_packagings 
       add constraint FKsmryvekk23d990a2frkvw0w3k 
       foreign key (packaging_id) 
       references packagings;

    alter table if exists product_packagings 
       add constraint FK4g7qg98oyqh3a5tna21q34o0s 
       foreign key (product_id) 
       references products;

    alter table if exists products 
       add constraint FKt1yvv81v320ba41fq28k7had2 
       foreign key (merchant_id) 
       references merchants;

    alter table if exists scale_prices 
       add constraint FK41c2icku1gau5d08hoeta3m75 
       foreign key (price_id) 
       references prices;

    alter table if exists selling_options 
       add constraint FKpngv5jnjwj1a3i8tndh32xn06 
       foreign key (product_id) 
       references products;

    alter table if exists shifts 
       add constraint FKj37msba48037erxhxp0xyh7qr 
       foreign key (item_id) 
       references items;

    alter table if exists shifts 
       add constraint FK6bx4ph95hwbebowtse3o5ldu7 
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

    alter table if exists weights 
       add constraint FKimwah0owad2wrhqwd78iwbgnh 
       foreign key (product_id) 
       references products;
