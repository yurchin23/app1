set -e
mvn clean package
docker build .  -t myapp:latest
docker tag myapp:latest localhost:5000/myapp:latest
docker push localhost:5000/myapp:latest