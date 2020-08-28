#!/bin/bash

docker-compose -f ~/postgres/docker-compose.yml -f ~/kafka/kafka-docker/docker-compose-local.yml up -d

