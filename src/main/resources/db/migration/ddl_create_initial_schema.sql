
    alter table if exists packagings 
       alter column lenght_cm set data type numeric(10,2);

    alter table if exists packagings 
       alter column thickness_cm set data type numeric(10,2);

    alter table if exists packagings 
       alter column width_cm set data type numeric(10,2);

    alter table if exists product_packagings 
       add column use_lateral_bag boolean;
