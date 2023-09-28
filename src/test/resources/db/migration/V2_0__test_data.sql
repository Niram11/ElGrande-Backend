-- RESTAURANT
insert into restaurant(id, name, description, website, contact_number, contact_email, is_deleted)
values ('4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5', 'Kacper', 'Desc1', 'Kacper.pl', 123, 'Kacper@wp.pl', false),
       ('c728af54-0d03-4af1-a68e-6364db2370ee', 'Andrzej', 'Desc3', 'Andrzej.pl', 456, 'Andrzej@wp.pl', false),
       ('860a757c-a704-4ac3-820c-2f3f37657a68', 'Tomek', 'Decs2', 'Tomek.pl', 321, 'Tomek@wp.pl', false);

-- ADDRESS
insert into address(id, restaurant_id, additional_details, country, city, postal_code, street, street_number)
values ('8a814d44-51b4-4a9b-a3f2-ece1775c98e0', '4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5', 'Details1', 'Germany', 'Berlin',
        '41579', 'street1', '16B'),
       ('c9ce7b69-2609-46c9-a91a-fb199ed87dc3', '860a757c-a704-4ac3-820c-2f3f37657a68', 'Details2', 'Poland', 'Kraków',
        '66420', 'street2', '15A');

-- BUSINESS HOUR
insert into business_hour(id, restaurant_id, day_of_week, closing_hour, opening_hour)
values ('c56f5aec-de67-4698-b045-7e96ef64dad0', '4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5', 1, '11:10:00', '14:40:00'),
       ('a675b170-c311-4c1d-a0b4-cd7eb0e349dd', '4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5', 2, '12:20:00', '13:30:00'),
       ('d829566a-2f23-47c4-9983-d46081f7f530', 'c728af54-0d03-4af1-a68e-6364db2370ee', 3, '13:30:00', '12:20:00'),
       ('a3fbbd05-7fcc-4a2c-916c-940a2ca940fd', 'c728af54-0d03-4af1-a68e-6364db2370ee', 4, '14:40:00', '11:10:00');

-- CUSTOMER
insert into customer(id, name, surname, email, submission_time, password, is_deleted)
values ('3662bb1f-4804-4f37-b0ac-9417e4ec385b', 'Tomek', 'Marcin', 'Tomek@gmail.com', '2012-12-12', 'pw', false),
       ('89b77b49-562f-4570-ae40-52b4e359cb5f', 'Oskar', 'Konrad', 'Oskar@wp.pl', '2011-11-11', 'pw', false);

-- DISH
insert into dish(id, price, dish_name, restaurant_id)
values ('f454d079-4179-41b5-858e-bdc5ad953256', 12.3, 'Lasagne', '4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5'),
       ('84ebf2b8-f8c7-4b63-966a-0057d4bea728', 15.3, 'Spaghetti', '4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5'),
       ('23be11d5-18a6-4823-9c29-f4728d01fa37', 10, 'Lasagne', 'c728af54-0d03-4af1-a68e-6364db2370ee'),
       ('c90ad3f6-6662-43d3-a6d4-23942840f6aa', 15, 'Spaghetti', 'c728af54-0d03-4af1-a68e-6364db2370ee');

-- INGREDIENT
insert into ingredient(id, name)
values ('7f357c2a-a0ce-4a8f-9c07-ad34e3be7497', 'Tomato'),
       ('5419e2c5-9335-4ed6-b897-81e271d339ba', 'Pasta');

-- DISH <> INGREDIENT
insert into dish_ingredients(dish_id, ingredient_id)
values ('f454d079-4179-41b5-858e-bdc5ad953256', '7f357c2a-a0ce-4a8f-9c07-ad34e3be7497'),
       ('f454d079-4179-41b5-858e-bdc5ad953256', '5419e2c5-9335-4ed6-b897-81e271d339ba');

-- DISH CATEGORY
insert into dish_category(id, category)
values ('0188a205-ed29-405e-9245-4b714a0db157', 'Meat'),
       ('d42820cf-75fe-4178-84e2-82e353b70d73', 'Lasagne');

-- DISH <> DISH CATEGORY
insert into dish_dish_category(dish_category_id, dish_id)
values ('0188a205-ed29-405e-9245-4b714a0db157', '84ebf2b8-f8c7-4b63-966a-0057d4bea728'),
       ('0188a205-ed29-405e-9245-4b714a0db157', 'c90ad3f6-6662-43d3-a6d4-23942840f6aa'),
       ('d42820cf-75fe-4178-84e2-82e353b70d73', '23be11d5-18a6-4823-9c29-f4728d01fa37'),
       ('d42820cf-75fe-4178-84e2-82e353b70d73', 'f454d079-4179-41b5-858e-bdc5ad953256');

-- IMAGE
insert into image(id, restaurant_id, path_to_image)
values ('d9ea6cf3-b18d-4a6c-a023-0747345bd275', '4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5', 'path/to'),
       ('52792893-e2cf-47b7-98fd-ef7f047b79c3', '4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5', 'path/to'),
       ('5989c2d8-8ac0-47b6-8b7c-1d335a6f2da2', 'c728af54-0d03-4af1-a68e-6364db2370ee', 'path/to'),
       ('4cf8b369-77af-489d-8967-806d4fdffab5', 'c728af54-0d03-4af1-a68e-6364db2370ee', 'path/to');