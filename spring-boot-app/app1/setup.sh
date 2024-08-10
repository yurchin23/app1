set -e
mvn clean package
docker build .  -t myapp1:latest
docker tag myapp1:latest localhost:5000/myapp1:latest
docker push localhost:5000/myapp1:latest
