CREATE OR REPLACE FUNCTION login(username_or_email varchar, password varchar)
    RETURNS INT8
    LANGUAGE plpgsql AS
$$
DECLARE
    saved_password varchar;
    id             int8;
BEGIN
    id := (SELECT user_id FROM "user" WHERE username = username_or_email OR email = username_or_email);
    IF id IS NULL THEN
        RETURN -1;
    ELSE
        saved_password = (SELECT hashed_password FROM "user");

        IF saved_password = password THEN
            RETURN (SELECT user_id FROM "user" WHERE username = username_or_email OR email = username_or_email);
        ELSE
            RETURN 0;
        end if;
    END IF;
END;
$$;

CREATE OR REPLACE FUNCTION register(
    user_email varchar,
    user_username varchar,
    user_firstname varchar,
    user_surname varchar,
    user_lastname varchar,
    user_hashed_password varchar)
    RETURNS INT8
    LANGUAGE plpgsql AS
$$
DECLARE
    user_role_id int2;
    id           int8;
BEGIN
    user_role_id := (SELECT role_id FROM roles WHERE title = 'USER');

    INSERT INTO "user" (firstname, surname, lastname, email, username, hashed_password)
    VALUES (user_firstname, user_surname, user_lastname, user_email, user_username, user_hashed_password)
    RETURNING user_id INTO id;

    INSERT INTO user_roles (user_id, role_id) VALUES (id, user_role_id);

    RETURN id;
END;
$$;


