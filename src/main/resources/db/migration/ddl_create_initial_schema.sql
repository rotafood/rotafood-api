
    create table product_option_group (
        id uuid not null,
        index integer,
        max integer,
        min integer,
        option_group_id uuid,
        product_id uuid,
        primary key (id)
    );

    alter table if exists product_option_group 
       add constraint FKaeh0ld6gggusg2pfq5o3hd056 
       foreign key (option_group_id) 
       references option_groups;

    alter table if exists product_option_group 
       add constraint FKq3ymvu7uw91ddr7gyds8dhrr4 
       foreign key (product_id) 
       references products;

    alter table if exists context_modifiers 
       add column option_id uuid;

    alter table if exists context_modifiers 
       add column parent_option_modifier_id uuid;

    create table parent_option_modifiers (
        id uuid not null,
        option_id uuid,
        price_id uuid,
        primary key (id)
    );

    alter table if exists parent_option_modifiers 
       drop constraint if exists UK_6vjrir0bok3vs9cxh8u1e7f17;

    alter table if exists parent_option_modifiers 
       add constraint UK_6vjrir0bok3vs9cxh8u1e7f17 unique (option_id);

    alter table if exists parent_option_modifiers 
       drop constraint if exists UK_bcxvpvammiloj1ybs932ylywb;

    alter table if exists parent_option_modifiers 
       add constraint UK_bcxvpvammiloj1ybs932ylywb unique (price_id);

    alter table if exists context_modifiers 
       add constraint FKfmu9ao7cd0c1nf2ho8bb7llx1 
       foreign key (option_id) 
       references options;

    alter table if exists context_modifiers 
       add constraint FKru5if278r7mqhfpyo7os9xubm 
       foreign key (parent_option_modifier_id) 
       references parent_option_modifiers;

    alter table if exists parent_option_modifiers 
       add constraint FK7jjebkg01p2qrier6wmjjwn77 
       foreign key (option_id) 
       references options;

    alter table if exists parent_option_modifiers 
       add constraint FKjs196g4irly7t845i8vpss8y2 
       foreign key (price_id) 
       references prices;

    alter table if exists options 
       add column fractions integer[];

    create table default_products (
        id uuid not null,
        description varchar(1024) not null,
        ean varchar(256),
        i_food_image_path varchar(255),
        image_path varchar(255),
        name varchar(128) not null,
        primary key (id)
    );

    alter table if exists packagings 
       add column image_path varchar(255);

    alter table if exists packagings 
       add column image_path varchar(255);

    alter table if exists packagings 
       add column image_path varchar(255);

    alter table if exists packagings 
       add column image_path varchar(255);
