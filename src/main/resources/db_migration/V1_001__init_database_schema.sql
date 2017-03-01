CREATE TABLE users (
  id BIGSERIAL NOT NULL,
  username VARCHAR(50) NOT NULL unique,
  password VARCHAR(500),
  PRIMARY KEY (id)
);

CREATE TABLE authority (
  id SERIAL NOT NULL,
  name VARCHAR(50) NOT NULL unique,
  PRIMARY KEY (id)
);

CREATE TABLE user_authority (
    id SERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (authority) REFERENCES authority (name)
);

create unique index user_authority_idx_1 on user_authority (id, authority);

CREATE TABLE oauth_access_token (
  id SERIAL NOT NULL,
  token_id VARCHAR(256) DEFAULT NULL,
  token bytea,
  authentication_id VARCHAR(256) DEFAULT NULL,
  user_name VARCHAR(256) DEFAULT NULL,
  client_id VARCHAR(256) DEFAULT NULL,
  authentication bytea,
  refresh_token VARCHAR(256) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token bytea,
  authentication bytea,
  PRIMARY KEY (token_id)
);

CREATE TABLE oauth_client_details (
      client_id VARCHAR(256) PRIMARY KEY,
      resource_ids VARCHAR(256),
      client_secret VARCHAR(256),
      scope VARCHAR(256),
      authorized_grant_types VARCHAR(256),
      web_server_redirect_uri VARCHAR(256),
      authorities VARCHAR(256),
      access_token_validity INTEGER,
      refresh_token_validity INTEGER,
      additional_information VARCHAR(4096),
      autoapprove VARCHAR(256)
);
