# Desafío Java EY
***

## Tecnologías utilizadas:
* Java 17
* Spring Boot 3
* Lombok 1.18
* BBDD H2 en memoria
* Autenticación JWT
* Swagger

## Accesos
Acceso Swagger: http://localhost:8080/swagger-ui/index.html

URLs de api públicas:
* http://localhost:8080/swagger-ui/index.html
* http://localhost:8080/auth/login
* http://localhost:8080/h2-console


URLs de api privadas:
* http://localhost:8080/api/**


## Instrucciones para iniciar la aplicación:
### Requisitos
Tener instalado:
* Java 17 o superior
* Gradle 8.7

No es necesario ejecutar algun script SQL, dado que la base de datos que se utiliza esta en memoria (H2)

### Pasos
1. Descargar código fuente desde repositorio GIT:

```
git clone https://github.com/manusaez/ejercicio-java-api-rest.git
```

2. En el mismo directorio donde se descargó la aplicación, empaquetar la aplicación:

```
gradle clean build
```

3. Iniciar la aplicación:

```
java -jar build/libs/apirest-0.0.1-SNAPSHOT.jar
```



La aplicación inicia cargando la BBDD con un usuario por defecto:

```json lines
{
  "name":"Juan Rodriguez",
  "email":"juan@rodriguez.org",
  "password":"hunter2",
  "phones":[
    {
      "number":"1234567",
      "citycode":"1",
      "contrycode":"57"
    }
  ]
}
```

### Acceso a BBDD H2 iniciada en memoria una vez que se inicia la aplicación:

      URL consola: http://localhost:8080/h2-console
   
      Accesos (sin password):
        JDBC URL: jdbc:h2:mem:apidb
        user: sa
        pass:

## Pruebas a través de Postman

### 1. Autentificar usuario:
```
URL: http://localhost:8080/auth/login
Método: POST
```

#### JSON de entrada:

```json
   {
      "email":"juan@rodriguez.org",
      "password":"hunter2"
   }
```

#### JSON de salida:
`HTTP Status: 200 OK`

```json
   {
      "id":"13e408b5-ee97-4748-a6b9-7a54c97f8445",
      "name":"Juan Rodriguez",
      "email":"juan@rodriguez.org",
      "password":"$2a$10$.fsf53fIP/mbwZa0KVgZi.e.aEPlkWcuBoNB.cH21csmNAQI3/IZO",
      "phones":[
         {
            "id":"17553ff7-3cae-40f2-b18e-41d4054659c3",
            "number":"1234567",
            "cityCode":"1",
            "countryCode":"57"
         }
      ],
      "created":"2024-04-22T12:28:48.238602",
      "modified":null,
      "lastLogin":"2024-04-22T12:28:56.195585",
      "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE3MTM4MDMzMzYsImV4cCI6MTcxMzgzMjEzNn0.cssvwAntxTPEBps4ENLqbg1LAR5ElLp9xKekvapohk4",
      "isActive":true
   }
```

### 2. Con el token obtenido, se puede acceder al resto de la API:
#### Creación de usuario:
````
   
   URL: http://localhost:8080/api
   Método: POST
````
#### JSON de entrada:
```json
{
   "name":"Pedro Rodriguez",
   "email":"pedro@rodriguez.org",
   "password":"p!assWord1",
   "phones":[
      {
         "number":"1111111",
         "cityCode":41,
         "countryCode":56
      },
      {
         "number":"2222222",
         "cityCode":42,
         "countryCode":57
      }
   ]
}
```

#### JSON de salida:
`HTTP Status: 201 CREATED`

```json
{
   "id":"d1dbfb73-fa20-49f9-b58d-7b6e29a358d0",
   "name":"Pedro Rodriguez",
   "email":"pedro@rodriguez.org",
   "password":"$2a$10$tF74UmZ0B5FhD2POcoUkHuecsDOHzTGN3AJSCSdogr5F0Jn5LIOpq",
   "phones":[
      {
         "id":"07982955-8922-41c1-a38f-157c242c1d2f",
         "number":"1234567",
         "cityCode":"1",
         "countryCode":"57"
      },
      {
         "id":"fce37ac1-f8d5-4528-beeb-ab5166d7d873",
         "number":"1234567",
         "cityCode":"1",
         "countryCode":"57"
      }
   ],
   "created":"2024-04-22T13:15:15.111599",
   "modified":null,
   "lastLogin":"2024-04-22T13:15:15.111599",
   "token":null,
   "isActive":true
}
```
#### Obtener todos los usuarios disponibles:
````
URL: http://localhost:8080/api
Método: GET
````
#### JSON de entrada: vacío

#### JSON de salida:
`HTTP Status: 200 OK`

```json
[
   {
      "id":"343bccce-18a0-46c1-97a0-24b76c51fd66",
      "name":"Juan Rodriguez",
      "email":"juan@rodriguez.org",
      "password":"$2a$10$gzkJYFDln/Eiq2EfDBGYwu/SI8Ig53/Et0.4efMYWdv0l8SyYBjkW",
      "phones":[
         {
            "id":"9920973b-673c-4d8c-a2d8-e8b0cecdfc5f",
            "number":"1234567",
            "cityCode":"1",
            "countryCode":"57"
         }
      ],
      "created":"2024-04-22T13:12:58.944672",
      "modified":null,
      "lastLogin":"2024-04-22T13:14:26.078103",
      "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE3MTM4MDYwNjYsImV4cCI6MTcxMzgzNDg2Nn0.DFJ6zDV_DpeCC_6NtXSyA2SbyCXkaAkpkrGrQYPnLoI",
      "isActive":true
   },
   {
      "id":"d1dbfb73-fa20-49f9-b58d-7b6e29a358d0",
      "name":"Pedro Rodriguez",
      "email":"pedro@rodriguez.org",
      "password":"$2a$10$tF74UmZ0B5FhD2POcoUkHuecsDOHzTGN3AJSCSdogr5F0Jn5LIOpq",
      "phones":[
         {
            "id":"07982955-8922-41c1-a38f-157c242c1d2f",
            "number":"1234567",
            "cityCode":"1",
            "countryCode":"57"
         },
         {
            "id":"fce37ac1-f8d5-4528-beeb-ab5166d7d873",
            "number":"1234567",
            "cityCode":"1",
            "countryCode":"57"
         }
      ],
      "created":"2024-04-22T13:15:15.111599",
      "modified":null,
      "lastLogin":"2024-04-22T13:15:15.111599",
      "token":null,
      "isActive":true
   }
]
```
#### Obtener un usuario:
````
URL: http://localhost:8080/api/8e073f32-ef3a-4e84-b7aa-d09fa3d9fd5d
   Se debe indicar en la URL el UUID del usuario buscado
Método: GET
````
#### JSON de Entrada: Vacío

#### JSON de Salida:
`HTTP Status: 200 OK`
```json
{
   "id":"8e073f32-ef3a-4e84-b7aa-d09fa3d9fd5d",
   "name":"Juan Rodriguez",
   "email":"juan@rodriguez.org",
   "password":"$2a$10$RVtJBpKWKoDPkAch9BrVpOR7M34hZzEOPFvlnTOn.DN7ldBv8DJf.",
   "phones":[
      {
         "id":"2cf09cfa-c7e2-4ef1-8e12-6731bb41d780",
         "number":"1234567",
         "cityCode":"1",
         "countryCode":"57"
      }
   ],
   "created":"2024-04-22T14:27:24.937938",
   "modified":null,
   "lastLogin":"2024-04-22T14:30:42.0168",
   "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE3MTM4MTA2NDEsImV4cCI6MTcxMzgzOTQ0MX0.3dyegiQbaOysbNmg9DY4g2j4tL6mgepnk013U1EmPgc",
   "isActive":true
}
```
#### Modificar el usuario pedro@rodriguez.org con password inválido:
```
URL: http://localhost:8080/api/d1dbfb73-fa20-49f9-b58d-7b6e29a358d0
   Se debe indicar en la URL el UUID por cada usuario que se desea modificar.
Método: PUT
```
#### JSON de entrada:

```json
{
   "name":"Pedro Rodriguez",
   "email":"pedropablo@rodriguez.org",
   "password":"Password1",
   "phones":[
      {
         "number":"33333",
         "cityCode":"A1",
         "countryCode":"56"
      },
      {
         "number":"44444",
         "cityCode":"A2",
         "countryCode":"56"
      }
   ],
   "isActive":true
}
```
#### JSON de salida:
`HTTP Status: 403 FORBIDDEN`
```json
{
   "mensaje": "Formato de password inválido"
}
```
#### Modificar el usuario pedro@rodriguez.org con email inválido:
```
URL: http://localhost:8080/api/d1dbfb73-fa20-49f9-b58d-7b6e29a358d0
   Se debe indicar en la URL el UUID por cada usuario que se desea modificar.
Método: PUT
```
#### JSON de entrada:
```json
{
   "name": "Pedro Rodriguez",
   "email": "pedropablorodriguez.org",
   "password": "Password1",
   "phones": [
       {
           "number": "33333",
           "cityCode": "A1",
           "countryCode": "56"
       },
       {
           "number": "44444",
           "cityCode": "A2",
           "countryCode": "56"
       }
   ],
   "isActive": true
}
```
#### JSON de salida:
`HTTP Status: 403 FORBIDDEN`
```json
{
   "mensaje": "Formato de correo inválido"
}
```
#### Modificar el usuario pedro@rodriguez.org correctamente:
```
URL: http://localhost:8080/api/d1dbfb73-fa20-49f9-b58d-7b6e29a358d0
   Se debe indicar en la URL el UUID por cada usuario que se desea modificar.
Método: PUT
```
#### JSON de entrada:
```json
{
   "name": "Pedro Rodriguez",
   "email": "pedropablo@rodriguez.org",
   "password": "Password1!",
   "phones": [
       {
           "number": "33333",
           "cityCode": "A1",
           "countryCode": "56"
       },
       {
           "number": "44444",
           "cityCode": "A2",
           "countryCode": "56"
       }
   ],
   "isActive": true
}
```
#### JSON de salida:
`HTTP Status: 200 OK`
```json
{
   "id": "d1dbfb73-fa20-49f9-b58d-7b6e29a358d0",
   "name": "Pedro Rodriguez",
   "email": "pedropablo@rodriguez.org",
   "password": "$2a$10$iHrZvv52Z6gbzdqwy3n77utV3ZoOItaS.TtoGgsx/0hvf0R1QbHyW",
   "phones": [
       {
           "id": "04dee984-869e-4f50-bf49-b0e9a3c956d9",
           "number": "33333",
           "cityCode": "A1",
           "countryCode": "56"
       },
       {
           "id": "c3d28232-b76b-4fb6-9a20-22c6ffcd2b31",
           "number": "44444",
           "cityCode": "A2",
           "countryCode": "56"
       }
   ],
   "created": "2024-04-22T13:15:15.111599",
   "modified": "2024-04-22T13:24:35.609407",
   "lastLogin": "2024-04-22T13:15:15.111599",
   "token": null,
   "isActive": true
}
```

#### Desactivar usuario pedro@rodriguez.org:
```
URL: http://localhost:8080/api/d1dbfb73-fa20-49f9-b58d-7b6e29a358d0
   Se debe indicar en la URL el UUID por cada usuario que se desea desactivar.
Método: DELETE
```

#### JSON de entrada: vacío

#### JSON de salida: vacío
`HTTP Status: 204 NO_CONTENT`

### 3. Los validadores para email y contraseña son parametrizables a través del archivo constants.properties

En primera instancia, el password debe seguir las siguientes reglas:
   * Al menos una letra minúscula
   * Al menos una letra mayúscula
   * Al menos un número
   * Al menos un carácter no alfanumérico (@#$!%^&+=)
   * Al menos 8 caracteres

   La validación del email intenta cumplir completamente el estándar Internet Message Format Section 3.4 de la RFC 5322


