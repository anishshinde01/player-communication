#!/usr/bin/env bash

MAX_MESSAGES=4
USE_THREADS=true

echo "*** Player Communication Starter ***"
echo ""
echo "Choose mode of execution:"
echo "1) Multithreading (default)"
echo "2) Multiprocessing"

# logic to choose mode of execution
read -rp "Press Enter to use default[1], or enter the number 1 or 2: " mode

# empty spaces are also valid as they are just all trimmed
while [[ "$mode" != "1" && "$mode" != "2" && -n "$mode" ]]; do
  echo "Invalid Input - Press Enter[for default] OR 1 OR 2"
  read -rp "Press Enter to use default[1], or enter the number 1 or 2: " mode
done

if [[ "$mode" == "2" ]]; then
  USE_THREADS=false
fi

read -rp "Enter number of max messages to be sent and received ['stop condition'] (press Enter to use default $MAX_MESSAGES): " maxMessages

# empty spaces are also valid as they are just all trimmed
while [[ -n "$maxMessages" && ! $maxMessages =~ ^[1-9][0-9]*$ ]]; do
    echo "Invalid input. Please enter a number greater than 0, or press Enter to use default."
    read -rp "Enter max messages: " maxMessages
done

if [[ -n "$maxMessages" ]]; then
    MAX_MESSAGES=$maxMessages
fi

echo ""
echo "Running with the following configuration:"
if $USE_THREADS; then
    echo "- Mode: Multithreading"
else
    echo "- Mode: Multiprocessing"
fi
echo "- Max messages: $MAX_MESSAGES"
echo ""

if $USE_THREADS; then
    java -cp target/classes com.anishshinde.App $USE_THREADS "$MAX_MESSAGES"
else
    java -cp target/classes com.anishshinde.App $USE_THREADS "$MAX_MESSAGES"
fi