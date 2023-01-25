# Customer

## Leverantör

Sundsvalls kommun

## Beskrivning
Customer är en tjänst som listar en persons eller organisations kundrelationer till olika kommunala bolag.


## Tekniska detaljer

### Integrationer
Tjänsten integrerar mot:

* Mikrotjänst DataWarehouseReader

### Starta tjänsten

|Konfigurationsnyckel|Beskrivning|
|---|---|
| **DataWarehouseReader**||
|`integration.datawarehousereader.url`|URL för endpoint till DataWarehouseReader-tjänsten i WSO2|
|`spring.security.oauth2.client.registration.datawarehousereader.authorization-grant-type`|Grant type som ska användas för DataWarehouseReader-tjänsten|
|`spring.security.oauth2.client.registration.datawarehousereader.client-id`|Klient-ID som ska användas för DataWarehouseReader-tjänsten|
|`spring.security.oauth2.client.registration.datawarehousereader.client-secret`|Klient-secret som ska användas för DataWarehouseReader-tjänsten|
|`spring.security.oauth2.client.registration.datawarehousereader.provider`|Namn på provider att använda för tjänsten, ska matcha CLIENT_REGISTRATION_ID i integrationens konfiguration|
|`spring.security.oauth2.client.provider.datawarehousereader.token-uri`|URI till endpoint för att förnya token för DataWarehouseReader-tjänsten|

### Paketera och starta tjänsten
Applikationen kan paketeras genom:

```
./mvnw package
```
Kommandot skapar filen `api-service-customer-<version>.jar` i katalogen `target`. Tjänsten kan nu köras genom kommandot `java -jar target/api-service-customer-<version>.jar`.

### Bygga och starta med Docker
Exekvera följande kommando för att bygga en Docker-image:

```
docker build -f src/main/docker/Dockerfile -t api.sundsvall.se/ms-customer:latest .
```

Exekvera följande kommando för att starta samma Docker-image i en container:

```
docker run -i --rm -p8080:8080 api.sundsvall.se/ms-customer

```

#### Kör applikationen lokalt

Exekvera följande kommando för att bygga och starta en container i sandbox mode:  

```
docker-compose -f src/main/docker/docker-compose-sandbox.yaml build && docker-compose -f src/main/docker/docker-compose-sandbox.yaml up
```


## 
Copyright (c) 2021 Sundsvalls kommun