#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

pkill -9 -f spring-petclinic-api-gateway