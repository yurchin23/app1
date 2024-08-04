FROM jenkins/jenkins:lts

USER root

# Установка Maven
RUN apt-get update && apt-get install -y maven

# Установка Docker
RUN apt-get update && apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    echo "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list && \
    apt-get update && \
    apt-get install -y docker-ce docker-ce-cli containerd.io

# Установка kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && \
    chmod +x ./kubectl && \
    mv ./kubectl /usr/local/bin/kubectl

# Установка Helm
RUN curl -fsSL -o helm.tar.gz https://get.helm.sh/helm-v3.10.0-linux-amd64.tar.gz && \
    tar -zxvf helm.tar.gz && \
    mv linux-amd64/helm /usr/local/bin/helm && \
    rm -f helm.tar.gz && \
    rm -rf linux-amd64


# Добавление пользователя Jenkins в группу Docker
RUN usermod -aG docker jenkins

USER jenkins
WORKDIR /var/jenkins_home