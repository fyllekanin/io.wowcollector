## Development

This file is intended for when working with the project.

### [Swagger](http://localhost:8000/api/v1/swagger)

### Configuration

Copy the .env-local file and rename it to .env

As this project communicates with the Blizzard API you need to provide some credentials.

- https://develop.battle.net/
- Login and create a new application
- Set the BATTLE_NET_CLIENT_ID and BATTLE_NET_CLIENT_SECRET in the .env file

### Development

#### Tools
[Java 17+](https://www.java.com/sv/)
[Maven](https://maven.apache.org/)
[NodeJS 18+](https://nodejs.org/en)

[Quarkus CLI - not mandatory](https://quarkus.io/guides/cli-tooling)

#### Start the back-end

- mvn quarkus:dev

#### Build front-end

- cd ui
- npm install
- npm run dev

#### Run UI

#### Database

The project is expecting a **mongodb** database to be present, the simplest setup locally
is to setup a docker.

In the root there is a docker-compose.yml which starts up
a mongodb server with:

- port
    - 27017
- username
    - root
- password
    - example

admin interface reachable at:
[http://localhost:8081](http://localhost:8081)

``
