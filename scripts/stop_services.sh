#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

pkill -9 -f spring-petclinic-customers-service
pkill -9 -f spring-petclinic-visits-service
pkill -9 -f spring-petclinic-vets-service