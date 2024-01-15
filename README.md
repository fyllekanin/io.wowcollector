## io.wowcollector.project

### About

This project is aimed at collectors within the game of World Of Warcraft and
will provide an interface for these users to see what they have and what they have not yet
collected in the game.

### Current features

- Mount collection
- Achievement collection

### Planned features

- Toy collection
- Making personal and sharable collection views

### Building and testing docker file

- Build the image (from root)

``
docker build -t <tag> -f ./docker/Dockerfile .
``

- Start the image

``
docker run -d -p 80:80 --name <name> <tag> --network host
``