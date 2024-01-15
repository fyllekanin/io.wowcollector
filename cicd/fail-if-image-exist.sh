#!/bin/bash

repository="tovven"
image="wowcollector"

# Make a GET request to the Docker Hub API to check if the image exists
response=$(curl -s -o /dev/null -w "%{http_code}" "https://hub.docker.com/v2/repositories/$repository/$image/tags/$1/")

# Check the HTTP response code
if [ "$response" -eq 200 ]; then
    echo "Image exists on Docker Hub."
    exit 1
else
    echo "Image does not exist on Docker Hub. Exiting the script."
fi