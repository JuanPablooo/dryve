<h1 align="center"> 
    DRYVE 
</h1>

#### Rest API para cadastros de veículos 
<p align="center"> api para cadastro de veículos com seu preço de tabela</p>

<div align="center">

<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white"/>
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"/>
<img  src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
</div>
<h4 align="center"> 
	🚧  Cadastro de veículos em construção...  🚧
</h4>

## Arquitetura
### Relacionamento entre entidades
<div align="center">
  <img src="./readmeimg/dryve.png" width="350" title="tela de login">
</div>


### Para executar você precisa 
#### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [Docker](https://www.docker.com/products/docker-desktop),
[Maven](https://maven.apache.org/download.cgi)

### 🎲 Rodando o Back End (servidor)
```bash
# Clone este repositório
$ git clone <https://github.com/JuanPablooo/dryve>

# Acesse a pasta do projeto no terminal/cmd
$ cd dryve

# se usar sistema unix
make up 

# ou qualquer sistema operacional com docker
$ mvn clean install
$ docker-compose up --build --force-recreate
 

# O servidor inciará na porta:8080 - acesse <http://localhost:8080>
```
<img align="center" height="20px" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>

# Tecnologias 
### Linguagem  : <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
### Framework : <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
### Banco de Dados : <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"/>
### Versionamento de dados : <img src="https://img.shields.io/badge/flyway-0060A8?style=for-the-badge&logo=flyway&logoColor=white"/>
### Integração : <img src="https://img.shields.io/badge/feign-client-0060A8?style=for-the-badge&logo=feign&logoColor=white">
### Docker : <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white"/>
### Testes : <img src="https://img.shields.io/badge/jUnit%205-ED8939?style=for-the-badge&logo=feign&logoColor=white"/>






