# Intrucciones para correr el proyecto

## Antes de Iniciar

Podemos generar hashes md5 de prueba con [MD5 TOOL](https://emn178.github.io/online-tools/md5.html)

Es necesario contar con la version 21 de java, tener docker y docker compose y contar con aws-cli previamente instalados

También es necesario settear las variables de entorno AWS_ACCESS_KEY_ID y AWS_SECRET_ACCESS_KEY, para este ejemplo:

```
export AWS_ACCESS_KEY_ID=fake
export AWS_SECRET_ACCESS_KEY=fake
```
### Iniciar RabbitMQ y DynamoDB
```
docker-compose up -d
```
### Preparar Dynamo

Si es la primera vez que levantamos el contenedor usamos: 

```
aws dynamodb create-table \
  --table-name Stats \
  --attribute-definitions AttributeName=timestamp,AttributeType=S \
  --key-schema AttributeName=timestamp,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
  --endpoint-url http://localhost:8000 \
  --region us-east-1
```
### Iniciar microservicio

Una vez hecho lo anterior podemos levantar el servicio desde la carpeta donde clonamos el repositorio usando:

```
./gradlew bootRun
```
### Probar Endpoint

Podemos probar la respuesta del endpoint con una petición a http://localhost:8080/stats con el cuerpo:
```
'{
    "totalContactoClientes": 250,
    "motivoReclamo": 25,
    "motivoGarantia": 10,
    "motivoDuda": 100,
    "motivoCompra": 100,
    "motivoFelicitaciones": 7,
    "motivoCambio": 8,
    "hash": "5484062a4be1ce5645eb414663e14f59"
}'
```
También podemos usar curl directamente:
```
curl -X POST http://localhost:8080/stats \
  -H "Content-Type: application/json" \
  -d '{
    "totalContactoClientes": 666,
    "motivoReclamo": 1,
    "motivoGarantia": 2,
    "motivoDuda": 3,
    "motivoCompra": 4,
    "motivoFelicitaciones": 5,
    "motivoCambio": 6,
    "hash": "d216e744dde50d97c2cb997bed3e3e65"
  }'
```
Ambos son validos, si quieremos probar una bad Request podemos hacerlo cambiando el hash.

### Las pruebas se ejecutan con:

```
./gradlew test jacocoTestReport
```
O para mejor visualización:
```
./gradlew test jacocoMergedReport
```
