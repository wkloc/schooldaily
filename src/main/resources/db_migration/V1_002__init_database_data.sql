INSERT INTO users (username, password) VALUES ('admin', '$2a$10$TXwK4iA7TlN3dIjejiS/LuwL9VQR1WolUt2/7pUbixvSUZo9O8Diu');
INSERT INTO users (username, password) VALUES ('user', '$2a$10$TXwK4iA7TlN3dIjejiS/LuwL9VQR1WolUt2/7pUbixvSUZo9O8Diu');

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (username,authority) VALUES ('user', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity,
      additional_information, autoapprove) VALUES ('schooldaily_client', null, 'secret', 'read,write,trust', 'password,refresh_token,authorization_code', null, 'ROLE_ADMIN, ROLE_USER', 60, null, null, null);
