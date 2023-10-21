create table customer
(
    id              uuid not null
        primary key,
    email           varchar(255)
        constraint uk_dwk6cx0afu8bs9o4t536v1j5v
            unique,
    is_deleted      boolean,
    name            varchar(255),
    password        varchar(255),
    submission_time date,
    surname         varchar(255)
);

create table oauth2client_token
(
    id           uuid not null
        primary key,
    jwt_token    varchar(255),
    customer_id  uuid
        constraint uk_lvol746x803urxrwxob8du8fe
            unique
        constraint fk9rykg42gcq3pa0efjtmf571dc
            references customer,
    j_session_id varchar(255)
);

create table role
(
    id   uuid not null
        primary key,
    role varchar(255)
);

create table customer_role
(
    customer_id uuid not null
        constraint fkipr3etk2mjwkv6ahb4x4vwqy3
            references customer,
    role_id     uuid not null
        constraint fkmwml8muyov9xhw4423lp88n2u
            references role,
    primary key (customer_id, role_id)
);

create table refresh_token
(
    id          uuid not null
        primary key,
    expiry_date timestamp(6) with time zone,
    token       varchar(255),
    customer_id uuid
        constraint uk_lkiact1vrl4cqy99j4v3yiwgq
            unique
        constraint fkthlbvd34s3un1d4pxxqv2ni6c
            references customer
);

create table dish_category
(
    id       uuid not null
        primary key,
    category varchar(255)
        constraint uk_1088477bkfr56wxdlhcyt5a5b
            unique
);

create table ingredient
(
    id   uuid not null
        primary key,
    name varchar(255)
        constraint uk_bcuaj97y3iu3t2vj26jg6hijj
            unique
);

create table location
(
    id        uuid           not null
        primary key,
    latitude  numeric(32, 10) not null,
    longitude numeric(32, 10) not null
);

create table ownership
(
    id          uuid not null
        primary key,
    customer_id uuid not null
        constraint uk_sg3gbsd9jawcuhvaeqlbfkexs
            unique
        constraint fk3ldmasbm1ly7vdygl056vhxgl
            references customer
);

create table restaurant
(
    id             uuid not null
        primary key,
    contact_email  varchar(255),
    contact_number integer,
    description    varchar(255),
    is_deleted     boolean,
    name           varchar(100),
    website        varchar(255)
);

create table address
(
    id                 uuid not null
        primary key,
    additional_details varchar(255),
    city               varchar(255),
    country            varchar(255),
    postal_code        varchar(255),
    street             varchar(255),
    street_number      varchar(255),
    restaurant_id      uuid not null
        constraint uk_c0bw7sjl8xudemjctqw9dr33r
            unique
        constraint fk6218puogn7aamlck6quuf39ll
            references restaurant
);

create table business_hour
(
    id            uuid    not null
        primary key,
    closing_hour  time(6) not null,
    day_of_week   integer not null
        constraint business_hour_day_of_week_check
            check ((day_of_week >= 1) AND (day_of_week <= 7)),
    opening_hour  time(6) not null,
    is_unknown    boolean not null,
    restaurant_id uuid    not null
        constraint fkin1qvmlk3n7dta0ussafe9i4m
            references restaurant
);

create table customer_restaurants
(
    customer_id    uuid not null
        constraint fkrhwfap4lhpp94npe513qed9pv
            references customer,
    restaurants_id uuid not null
        constraint uk_5qhacrnx1idlfola2ln0kusfw
            unique
        constraint fkqhun04fgk5kiv5yhgsrjtf5wa
            references restaurant,
    primary key (customer_id, restaurants_id)
);

create table dish
(
    id            uuid           not null
        primary key,
    dish_name     varchar(255),
    price         numeric(38, 2) not null,
    restaurant_id uuid           not null
        constraint fkt13glsbe9ivpka00hbeg539cv
            references restaurant
);

create table dish_dish_category
(
    dish_id          uuid not null
        constraint fkbh7ktjmlb5s8xai4mew8ijpfh
            references dish,
    dish_category_id uuid not null
        constraint fkkgixvbn9atnohcgrxa3ve6af6
            references dish_category,
    primary key (dish_id, dish_category_id)
);

create table dish_ingredients
(
    dish_id       uuid not null
        constraint fkelmeucji4r7uhw0afsvfbxs88
            references dish,
    ingredient_id uuid not null
        constraint fkdd6d9d9n1v2budoftxsvvfh23
            references ingredient,
    primary key (dish_id, ingredient_id)
);

create table image
(
    id            uuid not null
        primary key,
    path_to_image varchar(255)
        constraint uk_589ljs2k91ldhfr66ckhxlrok
            unique,
    restaurant_id uuid not null
        constraint fk1sx05gapcfuchki4jyoup6wrv
            references restaurant
);

create table location_restaurants
(
    location_id    uuid not null
        constraint fk4fi8yq05yvkkfx6l668lojuw8
            references location,
    restaurants_id uuid not null
        constraint uk_klqhddxpxy7uc1kjnix15dfe6
            unique
        constraint fk8hvfgur5cjiham5h6wq6pybbe
            references restaurant,
    primary key (location_id, restaurants_id)
);

create table ownership_restaurants
(
    ownership_id   uuid not null
        constraint fk90b84a5odewt3hvcme2k9gcdr
            references ownership,
    restaurants_id uuid not null
        constraint uk_s5lshmaxwqwd9ororsu4dxnyo
            unique
        constraint fknap0ki4c8ge94qea7vqod6j12
            references restaurant,
    primary key (ownership_id, restaurants_id)
);

create table promoted_local
(
    id            uuid not null
        primary key,
    end_date      time(6),
    start_date    time(6),
    restaurant_id uuid not null
        constraint uk_pnfu67gpt0d4vi47xi9uy7h7i
            unique
        constraint fk3qt6e9f8vb7o4ognw5chphatv
            references restaurant
);

create table restaurant_category
(
    id       uuid not null
        primary key,
    category varchar(255)
        constraint uk_d6rvxolnoxv3vxc4is2lg0s3p
            unique
);

create table restaurant_restaurant_category
(
    restaurant_id          uuid not null
        constraint fkbwwc4lg8kv9sbbs266xxgufnm
            references restaurant_category,
    restaurant_category_id uuid not null
        constraint fkicu3hym6s168wlkj8e4fbih8d
            references restaurant,
    primary key (restaurant_id, restaurant_category_id)
);

create table review
(
    id              uuid    not null
        primary key,
    comment         varchar(255),
    grade           integer not null
        constraint review_grade_check
            check ((grade >= 1) AND (grade <= 10)),
    submission_time date,
    customer_id     uuid    not null
        constraint fkgce54o0p6uugoc2tev4awewly
            references customer,
    restaurant_id   uuid    not null
        constraint fk70ry7cuti298yxet366rynxch
            references restaurant
);

