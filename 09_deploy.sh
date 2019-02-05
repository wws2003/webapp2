#!/bin/bash
mvn cargo:redeploy -ff -DskipTests=true -pl web-all

