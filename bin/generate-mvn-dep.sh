#!/usr/bin/env bash

currentBranch=$(git rev-parse --abbrev-ref HEAD)

git checkout repository
git merge master

echo "Specify build version: "
read version

mvn install:install-file \
	-DgroupId=com.racoonberus.hotelHero \
	-DartifactId=tplRegHelper \
	-Dversion=${version} \
	-Dfile=target/tpl-reg-helper-1.0-SNAPSHOT.jar \
	-Dpackaging=jar \
	-DgeneratePom=true \
	-DlocalRepositoryPath=. \
	-DcreateChecksum=true
git branch

git add com/racoonberus/*
git commit -m "Build ${version}"
git push -u origin repository

git checkout ${currentBranch}