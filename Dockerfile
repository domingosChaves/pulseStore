FROM openjdk:17-jdk-slim AS build

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o pom.xml e o script do Maven Wrapper
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src

# Torne o script mvnw executável
RUN chmod +x mvnw

# Execute o Maven Wrapper para compilar o projeto
RUN ./mvnw clean package -DskipTests

# Usar a imagem JDK para execução
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Define o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]