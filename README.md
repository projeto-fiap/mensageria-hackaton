# Mensageria Hackaton

Este projeto é uma aplicação desenvolvida em **Java** utilizando o framework **Spring Boot** e gerenciada com **Maven**. Ele faz parte de um desafio técnico e utiliza diversas ferramentas e tecnologias para integração, testes, análise de qualidade e deploy.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Maven**
- **Docker**
- **Kubernetes**
- **SonarQube**
- **GitHub Actions**

## Estrutura do Projeto

- **Backend**: Implementado em Java com Spring Boot.
- **CI/CD**: Configurado com GitHub Actions para build, testes, análise de qualidade e deploy.
- **Deploy**: Realizado em um cluster Kubernetes utilizando arquivos de configuração YAML.

## Configurações Importantes

### Variáveis de Ambiente

As seguintes variáveis de ambiente são utilizadas no projeto:

- `KEYCLOAK_BASE_URL`: URL do Keycloak.
- `VIDEO_SERVICE_URL`: URL do serviço de vídeos.

### Arquivos de Configuração

- **Kubernetes**: Os arquivos de configuração para deploy estão localizados em `src/main/resources/deploy/k8s/`.
   - `mensageria-hackaton-deployment.yaml`
   - `hpa-mensageria-hackaton.yaml`
   - `service-mensageria-hackaton.yaml`

- **GitHub Actions**: O pipeline de CI/CD está configurado no arquivo `.github/workflows/maven-publish.yml`.

## Pipeline de CI/CD

O pipeline está dividido em várias etapas:

1. **Build**: Compila o projeto e instala dependências.
2. **Unit Tests**: Executa testes unitários e análise de qualidade com SonarQube.
3. **Integrated Tests**: Executa testes integrados.
4. **Quality Gate**: Verifica a qualidade do código no SonarQube.
5. **Docker Build & Push**: Constrói e envia a imagem Docker para o Docker Hub.
6. **Deploy**:
   - **DEV**: Deploy automático na branch `develop`.
   - **HML**: Deploy automático na branch `release`.
   - **PRD**: Deploy automático na branch `main`.

## Como Executar Localmente

1. Certifique-se de ter o **Java 17** e o **Maven** instalados.
2. Clone o repositório:
   ```bash
   git clone https://github.com/projeto-fiap/mensageria-hackaton.git
   cd mensageria-hackaton
   ```
3. Compile o projeto:
   ```bash
   mvn clean compile
   ```
4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

## Como Contribuir

1. Faça um fork do repositório.
2. Crie uma branch para sua feature:
   ```bash
   git checkout -b minha-feature
   ```
3. Faça commit das suas alterações:
   ```bash
   git commit -m "Minha nova feature"
   ```
4. Envie para o repositório remoto:
   ```bash
   git push origin minha-feature
   ```
5. Abra um Pull Request.

## Contato

Para dúvidas ou sugestões, entre em contato com o time do projeto.

---
**Nota**: Este projeto é parte de um desafio técnico e pode conter configurações específicas para o ambiente de desenvolvimento e deploy.
```