# Documentación de uso general

Esta documentación está orientada hacia el uso cotidiano de la REST API del sistema de venta/compra. No es una documentación exhaustiva de la API, sino la documentación de su utilización de uso general desde el front-end.

## Estructura general

Cada controlador relacionado con un modelo (Tienda, Producto, etc.) siempre dispone de un endpoint para recibir cada instancia del modelo en la base de datos, como para crear, o eliminar, una instancia. Solo está listada la creación general y algún que otro endpoint que requiera más detalle.

| URL                   | Método | Cuerpo               | Respuesta         |
|-----------------------|--------|----------------------|-------------------|
| `{modelo}/{modeloID}` | GET    | N/A                  | Modelo (Singular) |
| `{modelo}/`           | GET    | N/A                  | Modelo (Múltiple) |
| `{modelo}/find`       | GET    | N/A                  | Modelo (Múltiple) |
| `{modelo}/`           | POST   | Modelo (Múltiple)    | Modelo (Múltiple) |
| `{modelo}/{modeloID}` | DELETE | N/A                  | String            |
| `{modelo}/{modeloID}` | PATCH  | JSONPATCH (Singular) | Modelo (Singular) |

#### Método PATCH

Para aplicar el método PATCH a algún objeto, se requiere seguir el siguiente formato en el cuerpo del Request;

```json
{
  "op": "replace",
  "path": "/{atributo}",
  "value": "{valorNuevo}"
}
```

Este ejemplo remplaza el valor de "atributo" con el valor "valorNuevo". Para más información, [esta guía](https://www.baeldung.com/spring-rest-json-patch) contiene el resto de las operaciones útiles, cómo añadir, remover, etc.

## Tienda
Una Tienda (internamente "Stock") es un punto de venta que dispone de un nombre y una colección de tipo Listado  ("StockEntry") el que se encarga de enlistar qué producto vende la tienda, a qué precio, y cuánto stock dispone del mismo.

#### Creación

| URL      | Método | Cuerpo           | Respuesta        |
|----------|--------|------------------|------------------|
| `stock/` | POST   | Stock (Múltiple) | Stock (Múltiple) |

*Ejemplo*

```json
[
  {
    "stockName": "Tienda Ituzaingo"
  },
  {
    "stockName": "Tienda Moron"
  }
]
```

#### Búsqueda

| URL          | Método | Cuerpo | Respuesta        |
|--------------|--------|--------|------------------|
| `stock/find` | GET    | N/A    | Stock (Múltiple) |

| Parámetro   | Clase | Tipo   | Descripción          |
|-------------|-------|--------|----------------------|
| `stockName` | Query | String | Nombre de la tienda. |

*Ejemplo*

`stock/find?stockName=Itu`

## Producto
Un Producto ("Item") es un producto que dispone de un nombre, un código SKU, una cantidad de stock general del mismo, una categoría (o más) de tipo Atributo ("ItemAttribute"), y una bodega de tipo Compañía ("ItemCompany"). Un Producto tiene una relación de dependencia con Atributo y Compañía, por lo cual siempre se debe definir un Atributo y Compañía para la generación de un nuevo Producto.

### Atributo

#### Creación

| URL              | Método | Cuerpo                   | Respuesta                |
|------------------|--------|--------------------------|--------------------------|
| `item/attribute` | POST   | ItemAttribute (Múltiple) | ItemAttribute (Múltiple) |

*Ejemplo*

```json
[
  {
    "attributeName": "Bebida"
  },
  {
    "attributeName": "Accesorio"
  }
]
```

### Compañía

#### Creación

| URL            | Método | Cuerpo                 | Respuesta              |
|----------------|--------|------------------------|------------------------|
| `item/company` | POST   | ItemCompany (Múltiple) | ItemCompany (Múltiple) |

*Ejemplo*

```json
[
  {
    "companyName": "Trumpeter"
  },
  {
    "companyName": "Rutini"
  },
  {
    "companyName": "La Celia"
  },
  {
    "companyName": "Fernet & Co."
  }
]
```

### Producto

#### Creación

| URL     | Método | Cuerpo          | Respuesta       |
|---------|--------|-----------------|-----------------|
| `item/` | POST   | Item (Múltiple) | Item (Múltiple) |

*Ejemplo*

```json
[
  {
    "itemName": "Vino Malbec Trumpeter",
    "itemCode": "MAL-TRU",
    "itemCount": 12,
    "itemAttribute":
    [
      {
        "attributeID": 1
      }
    ],
    "itemCompany":
    {
      "companyID": 1
    }
  }
]
```

#### Búsqueda

| URL         | Método | Cuerpo | Respuesta       |
|-------------|--------|--------|-----------------|
| `item/find` | GET    | N/A    | Item (Múltiple) |

| Parámetro      | Clase | Tipo    | Descripción                   |
|----------------|-------|---------|-------------------------------|
| `itemName`     | Query | String  | Nombre del producto.          |
| `itemCode`     | Query | String  | Código del producto.          |
| `itemCountMin` | Query | Integer | Cantidad mínima del producto. |
| `itemCountMax` | Query | Integer | Cantidad máxima del producto. |

*Ejemplo*

`item/find?itemName=Trum`

#### Actualizar cantidad

Permite actualizar la cantidad de un producto. Funciona como una diferencia (e.j. "10" se entiende como la cantidad actual + 10).

| URL                   | Método | Cuerpo | Respuesta |
|-----------------------|--------|--------|-----------|
| `item/{itemID}/count` | PATCH  | N/A    | String    |

| Parámetro   | Clase | Tipo    | Descripción             |
|-------------|-------|---------|-------------------------|
| `{itemID}`  | Path  | Integer | ID del producto.        |
| `itemCount` | Query | Integer | Diferencia de cantidad. |

*Ejemplo*

`item/1/count?itemCount=10`

## Listado
Un Listado ("StockEntry") se ocupa de conectar un Producto y una Tienda, agregando información adicional como por cuánto se vende el Producto y cuánto de ese producto hay para esa Tienda.

#### Creación

| URL           | Método | Cuerpo                | Respuesta             |
|---------------|--------|-----------------------|-----------------------|
| `stock/entry` | POST   | StockEntry (Múltiple) | StockEntry (Múltiple) |

*Ejemplo*

```json
[
  {
    "entryCount": 10,
    "entryPrice": 100.0,
    "entryItem":
    {
      "itemID": 1
    },
    "entryStock":
    {
      "stockID": 1
    }
  }
]
```

#### Búsqueda

| URL                | Método | Cuerpo | Respuesta             |
|--------------------|--------|--------|-----------------------|
| `stock/entry/find` | GET    | N/A    | StockEntry (Múltiple) |

| Parámetro       | Clase | Tipo    | Descripción                  |
|-----------------|-------|---------|------------------------------|
| `entryCountMin` | Query | Integer | Cantidad mínima del listado. |
| `entryCountMax` | Query | Integer | Cantidad máxima del listado. |
| `entryPriceMin` | Query | Float   | Precio mínimo del listado.   |
| `entryPriceMax` | Query | Float   | Precio máximo del listado.   |

*Ejemplo*

`stock/entry/find?entryCountMin=100&entryCountMax=200`

#### Actualizar cantidad

Permite actualizar la cantidad de un listado. Funciona como una diferencia (e.j. "10" se entiende como la cantidad actual + 10).

| URL                           | Método | Cuerpo | Respuesta |
|-------------------------------|--------|--------|-----------|
| `stock/entry/{entryID}/count` | PATCH  | N/A    | String    |

| Parámetro    | Clase | Tipo    | Descripción             |
|--------------|-------|---------|-------------------------|
| `{entryID}`  | Path  | Integer | ID del listado.         |
| `entryCount` | Query | Integer | Diferencia de cantidad. |

*Ejemplo*

`stock/entry/1/count?entryCount=10`

#### Actualizar precio

Permite actualizar el precio de un listado. Funciona como una diferencia (e.j. "10" se entiende como el precio actual + 10).

| URL                           | Método | Cuerpo | Respuesta |
|-------------------------------|--------|--------|-----------|
| `stock/entry/{entryID}/price` | PATCH  | N/A    | String    |

| Parámetro    | Clase | Tipo  | Descripción           |
|--------------|-------|-------|-----------------------|
| `{entryID}`  | Path  | Float | ID del listado.       |
| `entryPrice` | Query | Float | Diferencia de precio. |

*Ejemplo*

`stock/entry/1/price?entryPrice=10`

#### Reporte

| URL                  | Método | Cuerpo | Respuesta           |
|----------------------|--------|--------|---------------------|
| `stock/entry/report` | GET    | N/A    | Archivo Excel .xlsx |

## Compra
Una Compra ("Sale") describe la venta de un Listado (o mas), agregando información como fecha y hora de la compra, nombre, teléfono, y correo del cliente, y precio final de la venta.

#### Creación

| URL     | Método | Cuerpo          | Respuesta       |
|---------|--------|-----------------|-----------------|
| `sale/` | POST   | Sale (Singular) | Sale (Singular) |

*Ejemplo*

```json
{
  "salePayment":
  {
    "paymentID": 1
  },
  "saleAmount": 10000.0,
  "saleName": "Fulanito Menganito",
  "saleMail": "fulanitomenganito@mail.com",
  "saleCall": "1234-5678",
  "saleDate": "2024-01-01",
  "saleTime": "18:00:00",
  "saleOrder":
  [
    {
      "orderAmount": 8,
      "orderEntry":
      {
        "entryID": 1
      }
    },
    {
      "orderAmount": 4,
      "orderEntry":
      {
        "entryID": 2
      }
    }
  ]
}
```

#### Búsqueda

| URL         | Método | Cuerpo | Respuesta       |
|-------------|--------|--------|-----------------|
| `sale/find` | GET    | N/A    | Sale (Múltiple) |

| Parámetro      | Clase | Tipo  | Descripción                |
|----------------|-------|-------|----------------------------|
| `salePriceMin` | Query | Float | Precio mínimo de la venta. |
| `salePriceMax` | Query | Float | Precio máximo de la venta. |

*Ejemplo*

`sale/find?salePriceMin=100&salePriceMax=200`

#### Reporte

| URL           | Método | Cuerpo | Respuesta           |
|---------------|--------|--------|---------------------|
| `sale/report` | GET    | N/A    | Archivo Excel .xlsx |

| Parámetro     | Clase | Tipo      | Descripción                                         |
|---------------|-------|-----------|-----------------------------------------------------|
| `saleDateMin` | Query | LocalDate | Piso del intervalo de tiempo del reporte de ventas. |
| `saleDateMax` | Query | LocalDate | Tope del intervalo de tiempo del reporte de ventas. |

*Ejemplo*

`sale/report?saleDateMin=2024-03-10&saleDateMax=2024-03-12`

## Contacto
Un Contacto ("Contact") describe un contacto, que está asociado a una Compañía, con nombre, teléfono, y correo del contacto.

#### Creación

| URL        | Método | Cuerpo             | Respuesta          |
|------------|--------|--------------------|--------------------|
| `contact/` | POST   | Contact (Múltiple) | Contact (Múltiple) |

*Ejemplo*

```json
[
  {
    "contactName": "Distribuidora Lo' Cra' S.A.",
    "contactMail": "andarásbienporla66@mail.com",
    "contactCall": "9999-1111",
    "contactCompany":
    [
      {
        "companyID" : 1
      },
      {
        "companyID" : 2
      }
    ]
  },
  {
    "contactName": "Distribuidora Vino-Mania",
    "contactMail": "adondevauncolor@mail.com",
    "contactCall": "1111-9999",
    "contactCompany":
    [
      {
        "companyID" : 3
      },
      {
        "companyID" : 4
      }
    ]
  }
]
```

#### Búsqueda

| URL            | Método | Cuerpo | Respuesta       |
|----------------|--------|--------|-----------------|
| `contact/find` | GET    | N/A    | Sale (Múltiple) |

| Parámetro     | Clase | Tipo   | Descripción            |
|---------------|-------|--------|------------------------|
| `contactName` | Query | String | Nombre del contacto.   |
| `contactMail` | Query | String | Teléfono del contacto. |
| `contactCall` | Query | String | Correo del contacto.   |

*Ejemplo*

`contact/find?contactName=Vino-M`

## Alerta
Una Alerta (Alert) notifica ante cualquier faltante de stock, tanto particular (para un Listado) o para un Producto. Una Alerta comienza como "sin ver" y ante la interacción del usuario podrá ser "vista".

#### Descartar

| URL                       | Método | Cuerpo | Respuesta        |
|---------------------------|--------|--------|------------------|
| `alert/{alertID}/discard` | POST   | N/A    | Alert (Singular) |

| Parámetro   | Clase | Tipo    | Descripción      |
|-------------|-------|---------|------------------|
| `{alertID}` | Path  | Integer | ID de la alerta. |

*Ejemplo*

`alert/1/discard`

## Historial

Un Historial (History) denota una actividad en particular que se tomó en la base de datos (actualización de producto, ya sea precio o cantidad, o actualización/eliminación de listado) con información adicional como fecha y hora de la actividad.

#### Búsqueda

| URL            | Método | Cuerpo | Respuesta          |
|----------------|--------|--------|--------------------|
| `history/find` | GET    | N/A    | History (Múltiple) |

| Parámetro        | Clase | Tipo      | Descripción                                |
|------------------|-------|-----------|--------------------------------------------|
| `historyDateMin` | Query | LocalDate | Fecha mínima de la actividad de historial. |
| `historyDateMax` | Query | LocalDate | Fecha máxima de la actividad de historial. |
| `historyTimeMin` | Query | LocalTime | Hora mínima de la actividad de historial.  |
| `historyTimeMax` | Query | LocalTime | Hora máxima de la actividad de historial.  |

*Ejemplo*

`history/find?historyDateMin=100&entryCountMax=200`