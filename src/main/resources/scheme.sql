CREATE TABLE account
(
  id bigint,
  name text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account
  OWNER TO pgsuser;