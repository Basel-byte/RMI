#!/bin/bash

# Number of clients to start
NUM_CLIENTS=$1

# Path to the JAR file
JAR_PATH="out/artifacts/client_jar/RMI.jar"

# Start the clients
for ((i=1; i<=NUM_CLIENTS; i++))
do
    echo "Starting client $i"
    java -jar "$JAR_PATH" &
done

echo "Started $NUM_CLIENTS clients."

