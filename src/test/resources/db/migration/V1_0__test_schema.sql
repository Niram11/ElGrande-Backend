create table customer
(
    is_deleted      boolean,
    submission_time date,
    id              uuid not null
        primary key,
    email           varchar(255),
    name            varchar(255),
    password_hash   varchar(255),
    surname         varchar(255)
);

create table dish_category
(
    id       uuid not null
        primary key,
    category varchar(255)
        unique
);

create table ingredient
(
    id   uuid not null
        primary key,
    name varchar(255)
        unique
);

create table location
(
    latitude  numeric(38, 2),
    longitude numeric(38, 2),
    id        uuid not null
        primary key
);

create table ownership
(
    customer_id uuid not null
        unique
        constraint fk3ldmasbm1ly7vdygl056vhxgl
            references customer,
    id          uuid not null
        primary key
);

create table restaurant
(
    contact_number integer,
    is_deleted     boolean,
    id             uuid not null
        primary key,
    name           varchar(100),
    contact_email  varchar(255),
    description    varchar(255),
    website        varchar(255)
);

create table address
(
    id                 uuid not null
        primary key,
    restaurant_id      uuid not null
        unique
        constraint fk6218puogn7aamlck6quuf39ll
            references restaurant,
    additional_details varchar(255),
    city               varchar(255),
    country            varchar(255),
    postal_code        varchar(255),
    street             varchar(255),
    street_number      varchar(255)
);

create table business_hour
(
    closing_hour  time(6),
    day_of_week   integer
        constraint business_hour_day_of_week_check
            check ((day_of_week >= 1) AND (day_of_week <= 7)),
    opening_hour  time(6),
    id            uuid not null
        primary key,
    restaurant_id uuid not null
        constraint fkin1qvmlk3n7dta0ussafe9i4m
            references restaurant
);

create table customer_restaurants
(
    customer_id    uuid not null
        constraint fkrhwfap4lhpp94npe513qed9pv
            references customer,
    restaurants_id uuid not null
        unique
        constraint fkqhun04fgk5kiv5yhgsrjtf5wa
            references restaurant,
    primary key (customer_id, restaurants_id)
);

create table dish
(
    price         numeric(38, 2) not null,
    id            uuid           not null
        primary key,
    restaurant_id uuid           not null
        constraint fkt13glsbe9ivpka00hbeg539cv
            references restaurant,
    dish_name     varchar(255)
);

create table dish_dish_category
(
    dish_category_id uuid not null
        constraint fkkgixvbn9atnohcgrxa3ve6af6
            references dish_category,
    dish_id          uuid not null
        constraint fkbh7ktjmlb5s8xai4mew8ijpfh
            references dish,
    primary key (dish_category_id, dish_id)
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
    restaurant_id uuid not null
        constraint fk1sx05gapcfuchki4jyoup6wrv
            references restaurant,
    path_to_image varchar(255)
);


create table location_restaurants
(
    location_id    uuid not null
        constraint fk4fi8yq05yvkkfx6l668lojuw8
            references location,
    restaurants_id uuid not null
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
        unique
        constraint fknap0ki4c8ge94qea7vqod6j12
            references restaurant,
    primary key (ownership_id, restaurants_id)
);


create table promoted_local
(
    end_date      time(6),
    start_date    time(6),
    id            uuid not null
        primary key,
    restaurant_id uuid not null
        unique
        constraint fk3qt6e9f8vb7o4ognw5chphatv
            references restaurant
);


create table restaurant_category
(
    id       uuid not null
        primary key,
    category varchar(255)
        unique
);

create table restaurant_restaurant_category
(
    restaurant_category_id uuid not null
        constraint fkicu3hym6s168wlkj8e4fbih8d
            references restaurant,
    restaurant_id          uuid not null
        constraint fkbwwc4lg8kv9sbbs266xxgufnm
            references restaurant_category,
    primary key (restaurant_category_id, restaurant_id)
);

create table review
(
    grade           numeric(38, 2)
        constraint review_grade_check
            check ((grade >= (1)::numeric) AND (grade <= (10)::numeric)),
    submission_time date,
    customer_id     uuid not null
        constraint fkgce54o0p6uugoc2tev4awewly
            references customer,
    id              uuid not null
        primary key,
    restaurant_id   uuid not null
        constraint fk70ry7cuti298yxet366rynxch
            references restaurant,
    comment         varchar(255)
);
