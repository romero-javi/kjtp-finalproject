# Checkout REST API

¿Que es esta API?
----------------
Esta API fue desarrollada para la conclusión del entrenamiento impartido por Applaudo Studios y KODIGO.
Implementada mediante Java con Spring Boot y desplegada mediante contenedores Docker.

La API respalda un checkout completamente funcional, incluyendo procesamiento de órdenes.
Además, integrada eficientemente con `PostgreSQL/MySQL` (**puedes decidir con que proveedor desplegar**) para garantizar un rendimiento óptimo y escalable.

Desplegar
---------
El despliegue puede realizarse en Linux, OSX, Windows u otros sistemas operativos que soporten el uso de docker,
es tan simple como:

1- Entrar a la ruta raíz:
   
     % cd KJTP-FinalProject

2- Ejecutar:
    
    % docker compose up -d

Por defecto desplegada con una instancia de `PostgreSQL`, puedes hacer cambios en el archivo `docker-compose.yml` para desplegar con una instancia `MySQL` como indicado al final de esta guía.


Variables de entorno customizables:
-----------------------------------

| Nombre              | Descripción                                                                                                        | Valor por defecto |
|---------------------|--------------------------------------------------------------------------------------------------------------------|-------------------|
| PORT                | Indica el puerto de la API, por ejemplo `8080`                                                                     | 9000              |
| KEYCLOAK_PROTOCOL   | Define el protocolo de keycloak a usar, el valor puede ser `http` o  `https`                                       | http              |
| KEYCLOAK_HOST       | Host de la instancia de keycloak a usar, el valor puede ser `localhost` o `nombre del container de keycloak`       | kjtp-keycloak     |
| KEYCLOAK_PORT       | Indica el puerto de la instancia de keycloak, por ejemplo `8080`                                                   | 8080              |
| DATABASE_VENDOR     | Define el proveedor de la base de datos a conectar, puede ser `mysql` o `postgresql`                               | postgresql        |
| DATABASE_HOST       | Host de la base de datos a usar, el valor puede ser `localhost` o `nombre del container de keycloak`               | kjtp-app-db       |
| DATABASE_PORT       | Indica el puerto de la instancia de la base de datos, puede ser `3306` para **Mysql** o `5432` para **PostgreSQL** | 5432              |
| DATABASE_NAME       | Define el nombre de la base de datos a usar, por ejemplo `myDatabase`                                              | kjtp-app_db       |
| DATABASE_USER       | Indica el usuario con acceso a la base de datos definida, por ejemplo `myUser`                                     | kjtp-admin        |
| DATABASE_PASSWORD   | Define la contraseña de acceso a la base de datos definida, por ejemplo `myPassword`                               | 873264ygy!@E      |

Conectar a otra base de datos:
------------------------------
Puedes levantar `MySQL` en un contenedor de docker o tener una instancia local de `PostgreSQL/Mysql` que deseas conectar
diferente a la proveída por defecto, para ello en el archivo `docker-compose.yml` en el contenedor de la API `kjtp-project`: 

1. Debes cambiar el valor de las variables de entorno `DATABASE_VENDOR`, `DATABASE_HOST`, `DATABASE_PORT`, `DATABASE_NAME`, `DATABASE_USER`, `DATABASE_PASSWORD`.
2. En el apartado `depends_on` debes especificar `el nombre del contenedor` en caso la instancia de la base de datos esté desplegada mediante docker, **omitir este paso si es una instancia local**.

Ejemplo de despliegue con una instancia local `MySQL`:

```yml
  app:
    container_name: kjtp-app
    image: javierromeroup/kjtp-app:1.0.0
    ports:
      - "9000:9000"
    environment:
      - KEYCLOAK_HOST=kjtp-keycloak
      - DATABASE_VENDOR=mysql
      - DATABASE_HOST=mysql-db
      - DATABASE_NAME=myDatabase
      - DATABASE_PORT=3306
      - DATABASE_USER=myUser
      - DATABASE_PASSWORD=myPassword
    depends_on:
      - mysql-db
      - keycloak-db
      - keycloak
    networks:
      - kjtp-network
    restart: always
```

