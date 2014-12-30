#!/bin/bash

ctx logger info "Debian based MYSQL install 5..."

sudo apt-get update || error_exit $? "Failed on: sudo apt-get update"
while fuser /var/lib/dpkg/lock >/dev/null 2>&1 ; do
  ctx logger info "Waiting for other software managers to finish..."
  sleep 2
done
sudo rm -f /var/lib/dpkg/lock
sudo DEBIAN_FRONTEND=noninteractive apt-get -y install mysql-server-5.5 pwgen || error_exit $? "Failed on: sudo apt-get install -y -q mysql-server-5.5 pwgen"

sudo /etc/init.d/mysql stop
sudo rm -rf /var/lib/apt/lists/*
sudo rm -rf /var/lib/mysql/*
ctx logger info "MySQL Installation complete."
