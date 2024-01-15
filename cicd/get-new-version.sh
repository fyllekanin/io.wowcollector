#!/bin/bash

######### Arguments
# 1. Type of stepping
# 2. Current version
#########

# Get the current version from
CURRENT_VERSION="$2"

# Remove the -SNAPSHOT suffix
VERSION_WITHOUT_SNAPSHOT="${CURRENT_VERSION%-SNAPSHOT}"

# Split the version into major, minor, and patch parts
IFS='.' read -ra VERSION_PARTS <<< "$VERSION_WITHOUT_SNAPSHOT"

# Initialize variables for major, minor, and patch versions
MAJOR_VERSION="${VERSION_PARTS[0]}"
MINOR_VERSION="${VERSION_PARTS[1]}"
PATCH_VERSION="${VERSION_PARTS[2]}"

# Update the version based on the value of $1
case "$1" in
  "major")
    ((MAJOR_VERSION++))
    ;;
  "minor")
    ((MINOR_VERSION++))
    ;;
  "patch")
    ((PATCH_VERSION++))
    ;;
  *)
    echo "Error: Unknown version type '$1'. Valid values are 'major', 'minor', or 'patch'."
    exit 1
    ;;
esac

# Create the new version
NEW_VERSION="$MAJOR_VERSION.$MINOR_VERSION.$PATCH_VERSION"

echo $NEW_VERSION