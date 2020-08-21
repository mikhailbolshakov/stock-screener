#!/bin/sh

export ID=$(cat ~/.ssh/id_rsa|base64)
export IDPUB=$(cat ~/.ssh/id_rsa.pub|base64)
export CFG="$(cat ./configuration-provider/git2consul-config.json)"

docker-compose up
