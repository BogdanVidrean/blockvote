#!/bin/bash

if [ -z "$1" ]; then
  echo "No network id supplied."
  exit 1
fi

if [ -z "$2" ]; then
  echo "No Go Ethereum port supplied."
  exit 1
fi

if [ -z "$3" ]; then
  echo "No Go Ethereum rpc port supplied."
  exit 1
fi

./geth --datadir "../node" \
  --networkid "$1" \
  --port "$2" \
  --rpc --rpcapi "eth,web3,personal,net,miner,admin,debug" --rpcport "$3" --rpcaddr "127.0.0.1" \
  --rpccorsdomain "*" --gcmode "archive" &>/dev/null & echo $!
