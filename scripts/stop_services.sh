#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

pkill -f spring-petclinic-customers-service
pkill -f spring-petclinic-visits-service
pkill -f spring-petclinic-vets-service