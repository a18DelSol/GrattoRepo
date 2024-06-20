# Documentación de uso general

Esta documentación está orientada hacia el uso cotidiano de la REST API del sistema de venta/compra. No es una documentación exhaustiva de la API, sino la documentación de su utilización de uso general desde el front-end.

---

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

| Parámetro   | Tipo   |
|-------------|--------|
| `stockName` | String |

*Ejemplo*

`stock/find?stockName=Itu`

---

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

| Parámetro      | Tipo    |
|----------------|---------|
| `itemName`     | String  |
| `itemCode`     | String  |
| `itemCountMin` | Integer |
| `itemCountMax` | Integer |

*Ejemplo*

`item/find?itemName=Trum`

---

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

| Parámetro       | Tipo    |
|-----------------|---------|
| `entryCountMin` | Integer |
| `entryCountMax` | Integer |
| `entryPriceMin` | Integer |
| `entryPriceMax` | Integer |

*Ejemplo*

`stock/entry/find?entryCountMin=100&entryCountMax=200`

---

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
  "saleOrder": [
    {
      "orderAmount": 8,
      "orderEntry": { "entryID": 1 }
    },
    {
      "orderAmount": 4,
      "orderEntry": { "entryID": 2 }
    }
  ]
}
```

#### Búsqueda

| URL         | Método | Cuerpo | Respuesta       |
|-------------|--------|--------|-----------------|
| `sale/find` | GET    | N/A    | Sale (Múltiple) |

| Parámetro      | Tipo  |
|----------------|-------|
| `salePriceMin` | Float |
| `salePriceMax` | Float |

*Ejemplo*

`sale/find?salePriceMin=100&salePriceMax=200`

---

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
      { "companyID" : 1 },
      { "companyID" : 2 }
    ]
  },
  {
    "contactName": "Distribuidora Vino-Mania",
    "contactMail": "adondevauncolor@mail.com",
    "contactCall": "1111-9999",
    "contactCompany":
    [
      { "companyID" : 3 },
      { "companyID" : 4 }
    ]
  }
]
```

#### Búsqueda

| URL            | Método | Cuerpo | Respuesta       |
|----------------|--------|--------|-----------------|
| `contact/find` | GET    | N/A    | Sale (Múltiple) |

| Parámetro     | Tipo   |
|---------------|--------|
| `contactName` | String |
| `contactMail` | String |
| `contactCall` | String |

*Ejemplo*