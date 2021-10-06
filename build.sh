#!/bin/bash

./gradlew clean

./gradlew runtime

docker build . --tag jamesrmenzies/robots