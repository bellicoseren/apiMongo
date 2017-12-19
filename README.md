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
    <plugin>
      <artifactId>maven-assembly-plugin</artifactId>
      <configuration>
        <archive>
          <manifest>
            <mainClass>fully.qualified.MainClass</mainClass>
          </manifest>
        </archive>
        <descriptorRefs>
          <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
        <appendAssemblyId>false</appendAssemblyId>
      </configuration>
    </plugin>
```

3.- Empaquetar el proyecto

```
mvn clean compile assembly:single
```

4.- Subir el artefacto al repositorio remoto

```sh
mvn deploy:deploy-file -DgroupId=mx.com.beo \
  -DartifactId=apiMongo \
  -Dversion=1.0 \
  -Dpackaging=jar \
  -Dfile=<ruta-jar> \
  -DrepositoryId=nexus-repository \
  -Durl=http://200.39.24.141:8081/repository/ADMiddleware/
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

Previo a la ejecucion del programa es necesario configurar variables de ambiente

Configuración de parámetros para la API de MongoDB

```
USER_MONGO=<Usuario de mongo>
PASSWORD_USER_MONGO=<Contraseña de mongo>
SOURCE_USER_MONGO=<Source del usuario>
BASEDATOS_MONGO=<Nombre de la base de datos>
HOST_MONGO=<Dirección del host de mongo>
COLECCION_MONGO=configuracionImportacion
PUERTO_MONGO=<Puerto de mongo>
```