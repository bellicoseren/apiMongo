# Api Mongo
[![N|Solid](https://perlmaven.com/img/mongodb-logo.png)](https://www.mongodb.com/)

Funciones comunes para el uso de mongo DB

## Uso local

Compilar el proyecto 
```
mvn clean install
```

## Subir artefacto al repositorio remoto


Para que pueda ser utilizado como una dependencia, es necesario instalar el proyecto en el repositorio remoto realizando las siguientes acciones:

1.- Configurar el settings.xml de maven

```xml
    <server>
      <id>nexus-repository</id>
      <username>middleware</username>
      <password>m1ddl3w4r3</password>
    </server>
```
2.- Agregar el siguiente plugin al **pom.xml**

```xml
    <distributionManagement>
    <repository>
      <id>nexus-repository</id>
      <url>http://200.39.24.141:8081/repository/ADMiddleware/</url>
    </repository>
  </distributionManagement>
```

3.- Empaquetar el proyecto

```
mvn clean deploy
```

## Uso como dependecia 

Para utilizar la libreria como dependencia es necesario agregar al **pom.xml** del proyecto las siguientes etiquetas:

```xml
      <dependency>
         <groupId>mx.com.beo</groupId>
         <artifactId>apiMongo</artifactId>
         <version>${mongo-component.version}</version>
      </dependency>
```

```xml
   <repositories>
      <repository>
        <id>nexus-repository</id>
        <url>http://200.39.24.141:8081/repository/GrupoMiddleware</url>
      </repository>
   </repositories>  
```


## Variables de ambiente

Previo a la ejecucion del programa es necesario configurar la siguientes variables de ambiente:

```
USER_MONGO=<Usuario de mongo>
PASSWORD_USER_MONGO=<Contraseña de mongo>
SOURCE_USER_MONGO=<Source del usuario>
BASEDATOS_MONGO=<Nombre de la base de datos>
HOST_MONGO=<Dirección del host de mongo>
COLECCION_MONGO=<Coleccion>
PUERTO_MONGO=<Puerto de mongo>
```