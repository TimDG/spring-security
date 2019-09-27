insert into post(author, title, content, external_id)
values ( 'Boris Johnson', 'How to win at Brexit', 'Blah blah blah blah blah blah blah blah', 'b380088f-93b0-4ecc-b3a8-58913f008874' );


insert into blog_role(name)
values ('READER'),
       ('WRITER'),
       ('EDITOR');

-- Password: 111111

insert into blog_user(name, email, password)
values('Boris Johnson', 'bojo@pm.gov.uk', '$2a$10$ABsoSWtA0.ynkdSF3O8Ije8kXg3wJ3N6YwQnxzEYl1LRICQYUJ2tK'),
       ('Charles Michel', 'pm@belgium.be', '$2a$10$ABsoSWtA0.ynkdSF3O8Ije8kXg3wJ3N6YwQnxzEYl1LRICQYUJ2tK'),
       ('Angela Merkel', 'mutti@germany.de', '$2a$10$ABsoSWtA0.ynkdSF3O8Ije8kXg3wJ3N6YwQnxzEYl1LRICQYUJ2tK');


insert into users_roles(role_id, user_id)
values ((select id from blog_role where name='READER'), (select id from blog_user where email='bojo@pm.gov.uk')),
       ((select id from blog_role where name='WRITER'), (select id from blog_user where email='bojo@pm.gov.uk')),
       ((select id from blog_role where name='EDITOR'), (select id from blog_user where email='bojo@pm.gov.uk')),
       ((select id from blog_role where name='READER'), (select id from blog_user where email='pm@belgium.be')),
       ((select id from blog_role where name='WRITER'), (select id from blog_user where email='pm@belgium.be')),
       ((select id from blog_role where name='READER'), (select id from blog_user where email='mutti@germany.de')),
       ((select id from blog_role where name='WRITER'), (select id from blog_user where email='mutti@germany.de'));

