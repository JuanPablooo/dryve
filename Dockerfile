# pega a imagem do jdk p
FROM openjdk:11
# argumento de profile do projeto
ARG PROFILE
# adicionais opt
ARG ADDITIONAL_OPT

#variavel de ambiente ela que seta qual arquivo application.yml vai ser executado
ENV PROFILE=${PROFILE}

ENV ADDITIONAL_OPT=${ADDITIONAL_OPT}

# seta em que pasta do container vou trabalhar
WORKDIR  /opt/drive

# copia os arquivos desta pasta e joga na pasta definida em WORKDIR
COPY /target/dryve*.jar drive.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 8080

EXPOSE 5005

EXPOSE 5432

CMD java ${ADDITIONAL_OPT} -jar drive.jar --spring.profiles.active=${PROFILE}