echo "Starting PostgreSQL"

APP_DB_USER=pgsuser
APP_DB_PASS=pgspass

# Database Name
APP_DB_NAME=schooldaily

print_db_usage () {
  echo "PRIMARY PostgreSQL database has been setup and can be accessed on port 15432"
  echo "  Host: localhost"
  echo "  Port: 15432"
  echo "  Database: $APP_DB_NAME"
  echo "  Username: $APP_DB_USER"
  echo "  Password: $APP_DB_PASS"
  echo ""
  echo "Admin access to postgres user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo ""
  echo "psql access to app database user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo "  PGUSER=$APP_DB_USER PGPASSWORD=$APP_DB_PASS psql -p 5432 -h localhost $APP_DB_NAME"
  echo ""
  echo "SECONDARY PostgreSQL database has been setup and can be accessed on port 15433"
  echo "  Host: localhost"
  echo "  Port: 15433"
  echo "  Database: $APP_DB_NAME"
  echo "  Username: $APP_DB_USER"
  echo "  Password: $APP_DB_PASS"
  echo ""
  echo "Admin access to postgres user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo ""
  echo "psql access to app database user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo "  PGUSER=$APP_DB_USER PGPASSWORD=$APP_DB_PASS psql -p 5433 -h localhost $APP_DB_NAME"
  echo ""
  echo "THIRD (Production) PostgreSQL database has been setup and can be accessed on port 15434"
  echo "  Host: localhost"
  echo "  Port: 15434"
  echo "  Database: $APP_DB_NAME"
  echo "  Username: $APP_DB_USER"
  echo "  Password: $APP_DB_PASS"
  echo ""
  echo "Admin access to postgres user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo ""
  echo "psql access to app database user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo "  PGUSER=$APP_DB_USER PGPASSWORD=$APP_DB_PASS psql -p 5434 -h localhost $APP_DB_NAME"
}

cat << EOF | sudo su postgres -c "psql -p 5432"
-- Create the database user:
CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';

-- Create the database:
CREATE DATABASE $APP_DB_NAME WITH OWNER=$APP_DB_USER
                                  LC_COLLATE='en_US.utf8'
                                  LC_CTYPE='en_US.utf8'
                                  ENCODING='UTF8'
                                  TEMPLATE=template0;
\q
EOF

cat << EOF | sudo su postgres -c "psql -p 5433"
-- Create the database user:
CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';

-- Create the database:
CREATE DATABASE $APP_DB_NAME WITH OWNER=$APP_DB_USER
                                  LC_COLLATE='en_US.UTF-8'
                                  LC_CTYPE='en_US.UTF-8'
                                  ENCODING='UTF8'
                                  TEMPLATE=template1;
\q
EOF

cat << EOF | sudo su postgres -c "psql -p 5434"
-- Create the database user:
CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';

-- Create the database:
CREATE DATABASE $APP_DB_NAME WITH OWNER=$APP_DB_USER
                                  LC_COLLATE='en_US.UTF-8'
                                  LC_CTYPE='en_US.UTF-8'
                                  ENCODING='UTF8'
                                  TEMPLATE=template1;
\q
EOF

sudo su postgres
service postgresql restart

echo "Successfully started PostgreSQL."

print_db_usage