#!/bin/sh

#export ID=$(cat ~/.ssh/id_rsa|base64)
export ID="$(cat ~/.ssh/id_rsa)"
#export IDPUB=$(cat ~/.ssh/id_rsa.pub|base64)
export IDPUB="$(cat ~/.ssh/id_rsa.pub)"
export CFG="$(cat ./configuration-provider/git2consul-config.json)"

docker-compose up
