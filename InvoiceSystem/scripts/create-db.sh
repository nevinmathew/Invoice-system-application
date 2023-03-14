#!/bin/bash

# Start CockroachDB
cockroach start --insecure &

# Wait for CockroachDB to start
sleep 3

# Create a new user and database
cockroach sql --insecure -e "
  CREATE USER IF NOT EXISTS myuser;
  CREATE DATABASE IF NOT EXISTS mydb;
  GRANT ALL ON DATABASE mydb TO myuser;
"
