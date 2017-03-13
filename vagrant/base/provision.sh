#!/bin/sh -e

#ETH0:x VIRTUAL INTERFACE

echo "Configuring network interfaces"
for i in 1 2 3 4 5
do
	sudo cat > "/etc/network/interfaces.d/eth0:$i.cfg"

	IP=$(((10*i)+4))
	echo "auto eth0:$i" > "/etc/network/interfaces.d/eth0:$i.cfg"
	echo "iface eth0:$i inet static" >> "/etc/network/interfaces.d/eth0:$i.cfg"
	echo "address 192.168.1.$IP" >> "/etc/network/interfaces.d/eth0:$i.cfg"
	echo "netmask 255.255.255.0" >> "/etc/network/interfaces.d/eth0:$i.cfg"
	echo "network 192.168.1.1" >> "/etc/network/interfaces.d/eth0:$i.cfg"

	sudo ifup eth0:$i
done

echo "Network interfaces are up"

#POSTGRES

echo "Installing PostreSQL"

# PostgreSQL Version
PG_VERSION=9.4

export DEBIAN_FRONTEND=noninteractive

PG_REPO_APT_SOURCE=/etc/apt/sources.list.d/pgdg.list

# Add PG apt repo:
echo "deb http://apt.postgresql.org/pub/repos/apt/ trusty-pgdg main" > "$PG_REPO_APT_SOURCE"

# Add PGDG repo key:
wget --quiet -O - https://apt.postgresql.org/pub/repos/apt/ACCC4CF8.asc | apt-key add -

# Update package list and upgrade all packages
apt-get update
apt-get -y upgrade

apt-get -y install "postgresql-$PG_VERSION" "postgresql-contrib-$PG_VERSION"

PG_CONF="/etc/postgresql/$PG_VERSION/main/postgresql.conf"
PG_HBA="/etc/postgresql/$PG_VERSION/main/pg_hba.conf"
PG_DIR="/var/lib/postgresql/$PG_VERSION/main"

# Edit postgresql.conf to change listen address to '*':
sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" "$PG_CONF"

# Append to pg_hba.conf to add password auth:
echo "host    all             all             all                     md5" >> "$PG_HBA"

# Explicitly set default client_encoding
echo "client_encoding = utf8" >> "$PG_CONF"

service postgresql restart

echo "Successfully created PostgreSQL dev virtual machine."

echo "Cleaning for box exporting"

sudo apt-get clean
# sudo dd if=/dev/zero of=/EMPTY bs=1M
# sudo rm -f /EMPTY
# cat /dev/null > ~/.bash_history && history -c

echo "Cleaning finished"