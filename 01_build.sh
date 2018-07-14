# Install web-common (not required to build the whole app ?)
# mvn clean install -pl web-common -am

# Package (later will be integration-test to deploy)
# Note: Package goal only is desirable, but actually it only overlay web-common-lib.jar, so war:war must be added as a workaround
mvn clean package war:war
