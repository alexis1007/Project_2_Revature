INSERT INTO loans.user_types
  (user_type)
VALUES
  ('manager'),
  ('regular');

INSERT INTO loans.users
  (username, password_hash, user_types_id)
VALUES
  ('emmaj', '$2a$04$gRoPogr8qFGt58x4KEb9Duwy8s1LuULHvh.Xze5hrcce6kqaz3yWu', 1),
  ('liams', '$2a$04$YFFqoqTnjK/1gxVX/vsIKOoqhk7lGLQf9RXaJrOQNyaVxQ/b7LBHq', 1),
  ('oliviaw', '$2a$04$S/he2Uvu0FYzFWFfV1h9quNAC4hbHYQLbHtyLKvSiAcEyg.9MbHsq', 2),
  ('noahb', '$2a$04$Z/oPBD3YiLp.Lk/zK/4BHOQLH63//tQaV/l97M1SE/Gy9Gb9bV7Um', 2),
  ('avaj', '$2a$04$CobRPWEJSzuF9d2c2x/ggeVd2fn8Ql8jd79jh6WCVOmjk06hNwDUm', 2),
  ('williamg', '$2a$04$QjZ2hLj808B.g4t0k9QPFON7DnWP3Z5aswH8Oy8AqoIgjq9E3L/IG', 2),
  ('sophiam', '$2a$04$aP3LqweqxNFBiPOsSADfq.CapVqaP6Pa6Tp.FSJJ7hm0o8bv/hjVa', 2),
  ('jamesd', '$2a$04$H9dFI7rd2ZbWY4S0ZJ8KnON71.7YjwHajUSja4rLLYW/WwEH7uEJu', 2),
  ('isabellar', '$2a$04$cf7yoqGITOn9ynkCPfk/g.2cyQF3INhKVVwdnFJstWvM/KClpLiIW', 2),
  ('benjaminm', '$2a$04$ollQKS9dfx5gUfpfTmkkvubFKiTNrPLtBnWlP2FbghSLUzW3eyM0m', 2);
-- User passwords: passwordemma, passwordliam, passwordolivia, passwordnoah, passwordava,passwordwilliam,
--                 passwordsophia, passwordjames, passwordisabella, passwordbenjamin
  INSERT INTO loans.mailing_addresses
  (city, country, state, street, zip)
VALUES
  ('New York', 'USA', 'NY', '123 Park Ave', '10001'),
  ('Los Angeles', 'USA', 'CA', '456 Sunset Blvd', '90001'),
  ('Chicago', 'USA', 'IL', '789 Lake Shore Dr', '60601'),
  ('Houston', 'USA', 'TX', '101 Main St', '77002'),
  ('London', 'United Kingdom', 'Greater London', '200 Oxford Street', 'W1A 1AA'),
  ('Sydney', 'Australia', 'NSW', '42 George St', '2000'),
  ('Toronto', 'Canada', 'Ontario', '360 Yonge St', 'M5B 2M5'),
  ('Paris', 'France', 'Île-de-France', '5 Rue de Rivoli', '75001'),
  ('Berlin', 'Germany', 'Berlin', 'Unter den Linden 77', '10117'),
  ('Tokyo', 'Japan', 'Tokyo', '1-2 Chome Marunouchi', '100-0005');

INSERT INTO loans.user_profiles 
  (users_id, mailing_addresses_id, first_name, last_name, phone_number, credit_score, birth_date)
VALUES
  (1, 1, 'Emma', 'Johnson', '(555) 123-4567', 720, '1985-03-15'),
  (2, 2, 'Liam', 'Smith', '555-234-5678', 680, '1990-07-22'),
  (3, 3, 'Olivia', 'Williams', '+1 555 345 6789', 710, '1988-12-01'),
  (4, 4, 'Noah', 'Brown', '555.456.7890', 650, '1995-05-18'),
  (5, 5, 'Ava', 'Jones', '(555) 567-8901 ext123', 740, '1982-09-30'),
  (6, 6, 'William', 'Garcia', '5556789012', 690, '1993-02-14'),
  (7, 7, 'Sophia', 'Miller', '555-789-0123', 780, '1979-11-05'),
  (8, 8, 'James', 'Davis', '555 890 1234', 710, '1987-04-25'),
  (9, 9, 'Isabella', 'Rodriguez', '1-555-901-2345', 630, '1998-08-12'),
  (10, 10, 'Benjamin', 'Martinez', '5550123456', 720, '1991-06-09');

  -- Insert into loan_type table
INSERT INTO loans.loan_type (loan_type)
VALUES ('Home'), ('Auto'), ('Business');

-- Insert into application_statuses table
INSERT INTO loans.application_statuses (application_statuses, description)
VALUES ('Pending', 'Application is under review'),
       ('Approved', 'Application approved'),
       ('Rejected', 'Application rejected');

-- Insert into loan_applications table
INSERT INTO loans.loan_applications (loan_type_id, application_statuses_id, user_profiles_id, principal_balance, interest, term_length, total_balance)
VALUES (2, 2, 1, 15000.00, 3.75, 48, 16425.00),
       (3, 3, 1, 5000.00, 6.5, 24, 5800.00),
       (1, 2, 2, 80000.00, 4.1, 240, 140800.00),
       (2, 2, 3, 20000.00, 3.2, 60, 23200.00),
       (3, 3, 3, 7000.00, 5.8, 36, 8206.00),
       (1, 2, 4, 120000.00, 4.3, 300, 215400.00),
       (2, 1, 4, 28000.00, 3.6, 48, 30048.00),
       (3, 1, 4, 4000.00, 6.2, 24, 4592.00),
       (1, 3, 5, 95000.00, 4.05, 240, 163800.00),
       (2, 1, 5, 32000.00, 3.4, 60, 37840.00),
       (3, 1, 5, 6500.00, 6.1, 36, 7687.00),
       (1, 2, 5, 110000.00, 4.2, 300, 199600.00),
       (2, 1, 5, 17000.00, 3.8, 48, 18928.00),
       (3, 3, 6, 9000.00, 5.6, 24, 10296.00),
       (1, 1, 6, 160000.00, 4.5, 300, 288000.00),
       (2, 1, 6, 22000.00, 3.1, 60, 25420.00),
       (3, 1, 6, 5500.00, 5.9, 36, 6479.00),
       (1, 2, 7, 280000.00, 4.15, 360, 508400.00),
       (2, 2, 7, 38000.00, 3.3, 48, 40032.00),
       (3, 3, 7, 11000.00, 6.3, 24, 12372.00),
       (1, 1, 7, 320000.00, 4.4, 360, 580800.00),
       (2, 1, 7, 25000.00, 3.55, 60, 29412.50),
       (3, 2, 8, 16000.00, 3.1, 48, 17488.00),
       (1, 2, 8, 190000.00, 4.6, 300, 342200.00),
       (2, 3, 8, 21000.00, 3.7, 60, 24945.00),
       (3, 1, 8, 7500.00, 6.8, 36, 8930.00),
       (1, 2, 9, 230000.00, 3.95, 360, 410600.00),
       (2, 1, 9, 19000.00, 3.05, 48, 20888.00),
       (3, 1, 9, 5000.00, 7.2, 24, 5860.00),
       (1, 1, 9, 270000.00, 4.1, 360, 486000.00),
       (2, 1, 9, 26000.00, 3.35, 60, 30395.00),
       (3, 3, 10, 13000.00, 6.4, 36, 15992.00),
       (1, 3, 10, 200000.00, 4.7, 300, 382000.00),
       (2, 2, 10, 16000.00, 3.25, 48, 17520.00),
       (3, 1, 10, 9500.00, 5.7, 24, 10636.00),
       (1, 1, 10, 240000.00, 4.55, 300, 435600.00),
       (2, 1, 10, 29000.00, 3.45, 60, 33402.50);