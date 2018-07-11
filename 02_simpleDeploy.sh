# Make war file for web-all
mvn clean package war:war -pl web-all -am

# Deploy web-all war artifact
mvn integration-test -pl web-all
