# Make war file for web-all
echo "==========================================Packaging war artifact=========================================="
mvn clean package war:war -DskipTests=true -pl web-all -am

# Deploy web-all war artifact
echo "==========================================Deploying to Glassfish server=========================================="
mvn integration-test -DskipTests=true  -pl web-all
