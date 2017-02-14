INSERT INTO users (username, password) VALUES ('admin', '$2a$10$TXwK4iA7TlN3dIjejiS/LuwL9VQR1WolUt2/7pUbixvSUZo9O8Diu');
INSERT INTO users (username, password) VALUES ('user', '$2a$10$TXwK4iA7TlN3dIjejiS/LuwL9VQR1WolUt2/7pUbixvSUZo9O8Diu');
INSERT INTO users (username, password) VALUES ('aga', '$2a$10$TXwK4iA7TlN3dIjejiS/LuwL9VQR1WolUt2/7pUbixvSUZo9O8Diu');
INSERT INTO users (username, password) VALUES ('client-with-registered-redirect', '$2a$10$TXwK4iA7TlN3dIjejiS/LuwL9VQR1WolUt2/7pUbixvSUZo9O8Diu');

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (username,authority) VALUES ('aga', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('user', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_authority (username,authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_authority (username,authority) VALUES ('client-with-registered-redirect', 'ROLE_USER');

INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity,
      additional_information, autoapprove) VALUES ('aga', null, 'secret', 'read,write', 'password,refresh_token', null, 'ROLE_ADMIN, ROLE_USER', 60, null, null, null);
INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity,
      additional_information, autoapprove) VALUES ('client-with-registered-redirect', null, 'secret', 'read,trust', 'authorization_code,refresh_token', 'http://localhost:8092/client/',
      'ROLE_ADMIN, ROLE_USER', null, null, null, null);
INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity,
      additional_information, autoapprove) VALUES ('my-client-with-secret', null, 'secret', 'read,write,trust', 'client_credentials', null, 'ROLE_CLIENT', null, null, null, null);
INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity,
      additional_information, autoapprove) VALUES ('my-trusted-client', null, 'secret', 'read', 'implicit', null, 'ROLE_ADMIN, ROLE_USER', null, null, null, null);
