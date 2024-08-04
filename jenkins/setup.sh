set -e
docker build .  -t jenkins
docker tag jenkins:latest localhost:5000/jenkins:latest
docker push localhost:5000/jenkins:latest