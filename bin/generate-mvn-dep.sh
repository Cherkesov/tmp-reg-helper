#!/usr/bin/env bash

currentBranch=$(git rev-parse --abbrev-ref HEAD)

git checkout repository
git merge master

artifactId=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate \
    -Dexpression=project.artifactId | grep -v '\[')
version=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate \
    -Dexpression=project.version | grep -v '\[')

echo "Build ${artifactId}:${version}"

mvn package
mvn install:install-file \
	-DgroupId=com.racoonberus.hotelHero \
	-DartifactId=tplRegHelper \
	-Dversion=${version} \
	-Dfile=target/${artifactId}-${version}.jar \
	-Dpackaging=jar \
	-DgeneratePom=true \
	-DlocalRepositoryPath=. \
	-DcreateChecksum=true
git branch

git add com/racoonberus/*
git commit -m "Build ${version}"
git push -u origin repository

git checkout ${currentBranch}