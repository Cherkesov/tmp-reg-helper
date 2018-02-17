mvn install:install-file \
	-DgroupId=com.racoonberus.tplRegHelper \
	-DartifactId=tplRegHelper \
	-Dversion=1.0 \
	-Dfile=target/tpl-reg-helper-1.0-SNAPSHOT.jar \
	-Dpackaging=jar \
	-DgeneratePom=true \
	-DlocalRepositoryPath=. \
	-DcreateChecksum=true
