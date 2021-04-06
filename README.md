# Postgresql & PgAdmin in docker-compose
## Requirements:
This setup assumes you already have docker-compose and docker (using docker toolbox) installed.
* docker >= 17.12.0+
* docker-compose

## Quick Start
* navigate to `environment-setup` directory with terminal and run this command `docker-compose up -d` if required use `sudo docker-compose up -d` and it will pull and run PostgreSQL and RabbitMQ in background  


## Environments
This Compose file contains the following environment variables:

* `POSTGRES_USER` the default value is **postgres**
* `POSTGRES_PASSWORD` the default value is **changeme**
* `PGADMIN_PORT` the default value is **5050**
* `PGADMIN_DEFAULT_EMAIL` the default value is **pgadmin4@pgadmin.org**
* `PGADMIN_DEFAULT_PASSWORD` the default value is **admin**

## Access to postgres: 
* `localhost:5432`
* **Username:** postgres (as a default)
* **Password:** changeme (as a default)

## Access to PgAdmin: 
* **URL:** `http://localhost:5050`
* **Username:** pgadmin4@pgadmin.org (as a default)
* **Password:** admin (as a default)

## Add a new server in PgAdmin:
* **Host name/address** `postgres`
* **Port** `5432`
* **Username** as `POSTGRES_USER`, by default: `postgres`
* **Password** as `POSTGRES_PASSWORD`, by default `change`

--- 

# RabbitMQ in docker-compose
## Play
Open [http://localhost:15672/](http://localhost:15672/) (or what ever IP you get when you run `docker-machine ip`)

```
open http://$(docker-machine ip default):15672/
```
and use the login

```
username: rabbit
password: rabbit
```
