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

| Parámetro   | Tipo   | Descripción          |
|-------------|--------|----------------------|
| `stockName` | String | Nombre de la tienda. |

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

| Parámetro      | Tipo    | Descripción                   |
|----------------|---------|-------------------------------|
| `itemName`     | String  | Nombre del producto.          |
| `itemCode`     | String  | Código del producto.          |
| `itemCountMin` | Integer | Cantidad mínima del producto. |
| `itemCountMax` | Integer | Cantidad máxima del producto. |

*Ejemplo*

`item/find?itemName=Trum`

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

| Parámetro       | Tipo    | Descripción                  |
|-----------------|---------|------------------------------|
| `entryCountMin` | Integer | Cantidad mínima del listado. |
| `entryCountMax` | Integer | Cantidad máxima del listado. |
| `entryPriceMin` | Float   | Precio mínimo del listado.   |
| `entryPriceMax` | Float   | Precio máximo del listado.   |

*Ejemplo*

`stock/entry/find?entryCountMin=100&entryCountMax=200`

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

| Parámetro      | Tipo  | Descripción                |
|----------------|-------|----------------------------|
| `salePriceMin` | Float | Precio mínimo de la venta. |
| `salePriceMax` | Float | Precio máximo de la venta. |

*Ejemplo*

`sale/find?salePriceMin=100&salePriceMax=200`

#### Reporte

| URL           | Método | Cuerpo | Respuesta           |
|---------------|--------|--------|---------------------|
| `sale/report` | GET    | N/A    | Archivo Excel .xlsx |

| Parámetro     | Tipo      | Descripción                                         |
|---------------|-----------|-----------------------------------------------------|
| `saleDateMin` | LocalDate | Piso del intervalo de tiempo del reporte de ventas. |
| `saleDateMax` | LocalDate | Tope del intervalo de tiempo del reporte de ventas. |

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

| Parámetro     | Tipo   | Descripción            |
|---------------|--------|------------------------|
| `contactName` | String | Nombre del contacto.   |
| `contactMail` | String | Teléfono del contacto. |
| `contactCall` | String | Correo del contacto.   |

*Ejemplo*

`contact/find?contactName=Vino-M`

## Alerta
Una Alerta (Alert) notifica ante cualquier faltante de stock, tanto particular (para un Listado) o para un Producto. Una Alerta comienza como "sin ver" y ante la interacción del usuario podrá ser "vista".

#### Descartar

| URL                       | Método | Cuerpo | Respuesta        |
|---------------------------|--------|--------|------------------|
| `alert/{alertID}/discard` | POST   | N/A    | Alert (Singular) |

| Parámetro   | Tipo    | Descripción      |
|-------------|---------|------------------|
| `{alertID}` | Integer | ID de la alerta. |

*Ejemplo*

`alert/1/discard`