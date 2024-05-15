## User

### Información sobre cada User

**General**

| URL      | Método | Response       |
|----------|--------|----------------|
| `/user/` | `GET`  | `User` [Array] |

**Ejemplo [Request]**

`/user/`

**Ejemplo [Response]**

```json
[
    {
    "userID": 1,
    "userName": "Juan I.",
    "userMail": "juanI@mail.com",
    "userPass": "pass1",
    "userBirth": "2010-06-15",
    "userAttribute": [
      {
        "attributeID": 1,
        "attributeName": "Veterano"
      }
    ],
    "userCart": []
    }
]
```

### Información sobre un User individual

**General**

| URL              | Método | Response        |
|------------------|--------|-----------------| 
| `/user/{userID}` | `GET`  | `User` [Object] |

**PathVariable**

| Nombre   | Tipo      | Valor                     |
|----------|-----------|---------------------------|
| `userID` | `Integer` | El `userID` de un `User`. |

**Ejemplo [Request]**

`/user/1`

**Ejemplo [Response]**

```json
{
    "userID": 1,
    "userName": "Juan I.",
    "userMail": "juanI@mail.com",
    "userPass": "pass1",
    "userBirth": "2010-06-15",
    "userAttribute": [
    {
      "attributeID": 1,
      "attributeName": "Veterano"
    }
    ],
    "userCart": []
}
```

### Buscar User

**General**

| URL          | Método | Response       |
|--------------|--------|----------------| 
| `/user/find` | `GET`  | `User` [Array] |

**RequestParameter**

| Nombre      | Tipo        | Valor                        |
|-------------|-------------|------------------------------|
| `userName`  | `String`    | El `userName` de un `User`.  |
| `userMail`  | `String`    | El `userMail` de un `User`.  |
| `userPass`  | `String`    | El `userPass` de un `User`.  |
| `userBirth` | `LocalDate` | El `userBirth` de un `User`. |

**Ejemplo [Request]**

`/user/find?userName=Juan`

**Ejemplo [Response]**

```json
[
    {
    "userID": 1,
    "userName": "Juan I.",
    "userMail": "juanI@mail.com",
    "userPass": "pass1",
    "userBirth": "2010-06-15",
    "userAttribute": [
      {
        "attributeID": 1,
        "attributeName": "Veterano"
      }
    ],
    "userCart": []
    }
]
```

### Registrar User

**General**

| URL            | Método | Response |
|----------------|--------|----------| 
| `/user/signUp` | `POST` | `String` |

**RequestBody**

| Tipo   | Valor                   |
|--------|-------------------------|
| `User` | El cuerpo de un `User`. |

**Ejemplo [Request]**

`/user/signUp`

```json
{
    "userID": 1,
    "userName": "Juan I.",
    "userMail": "juanI@mail.com",
    "userPass": "pass1",
    "userBirth": "2010-06-15",
    "userAttribute": [
    {
      "attributeID": 1,
      "attributeName": "Veterano"
    }
    ],
    "userCart": []
}
```

**Ejemplo [Response]**

```
Sign up OK.
```

### Acceder User

**General**

| URL            | Método | Response |
|----------------|--------|----------| 
| `/user/signIn` | `POST` | `String` |

**RequestBody**

| Tipo   | Valor                   |
|--------|-------------------------|
| `User` | El cuerpo de un `User`. |

**Ejemplo [Request]**

`/user/signIn`

```json
{
    "userMail": "juanI@mail.com",
    "userPass": "pass1"
}
```

**Ejemplo [Response]**

```json
{
    "userID": 1,
    "userName": "Juan I.",
    "userMail": "juanI@mail.com",
    "userPass": "pass1",
    "userBirth": "2010-06-15",
    "userAttribute": [
      {
        "attributeID": 1,
        "attributeName": "Veterano"
      }
    ],
    "userCart": []
}
```

### Añadir ítem al Cart

**General**

| URL                   | Método | Response |
|-----------------------|--------|----------| 
| `/user/{userID}/cart` | `POST` | `User`   |

**RequestParameter**

| Nombre   | Tipo      | Valor                     |
|----------|-----------|---------------------------|
| `userID` | `Integer` | El `userID` de un `User`. |

**RequestBody**

| Tipo   | Valor                   |
|--------|-------------------------|
| `Cart` | El cuerpo de un `Cart`. |

**Ejemplo [Request]**

`/user/1/cart`

```json
{
    "cartCount": 5,
    "cartItem": {
      "itemID": 1
    }
}
```

**Ejemplo [Response]**

```json
{
  "userID": 1,
  "userName": "Juan I.",
  "userMail": "juanI@mail.com",
  "userPass": "pass1",
  "userBirth": "2010-06-15",
  "userAttribute": [
    {
      "attributeID": 1,
      "attributeName": "Veterano"
    }
  ],
  "userCart": [
    {
      "cartID": 1,
      "cartCount": 5,
      "cartItem": {
        "itemID": 1,
        "itemName": "Topline Ultra Green Mint",
        "itemCode": "7790580120702",
        "itemPrice": 250.25,
        "itemCount": 10,
        "itemRestrict": false,
        "itemAttribute": [
          {
            "attributeID": 1,
            "attributeName": "Fruta"
          }
        ],
        "itemCompany": {
          "companyID": 1,
          "companyName": "Manda Fruta"
        }
      }
    }
  ]
}
```

### Remover ítem del Cart

**General**

| URL                            | Método | Response |
|--------------------------------|--------|----------| 
| `/user/{userID}/cart/{cartID}` | `POST` | `User`   |

**RequestParameter**

| Nombre   | Tipo      | Valor                     |
|----------|-----------|---------------------------|
| `userID` | `Integer` | El `userID` de un `User`. |
| `cartID` | `Integer` | El `cartID` de un `Cart`. |

**RequestBody**

| Tipo   | Valor                   |
|--------|-------------------------|
| `Cart` | El cuerpo de un `Cart`. |

**Ejemplo [Request]**

`/user/1/cart/1`

**Ejemplo [Response]**

```json
{
  "userID": 1,
  "userName": "Juan I.",
  "userMail": "juanI@mail.com",
  "userPass": "pass1",
  "userBirth": "2010-06-15",
  "userAttribute": [
    {
      "attributeID": 1,
      "attributeName": "Veterano"
    }
  ],
  "userCart": []
}
```

## Item

### Información sobre cada Item

**General**

| URL      | Método | Response       |
|----------|--------|----------------|
| `/item/` | `GET`  | `Item` [Array] |

**Ejemplo [Request]**

`/item/`

**Ejemplo [Response]**

```json
[
  {
    "itemID": 1,
    "itemName": "Topline Ultra Green Mint",
    "itemCode": "7790580120702",
    "itemPrice": 250.25,
    "itemCount": 10,
    "itemRestrict": false,
    "itemAttribute": [
      {
        "attributeID": 1,
        "attributeName": null
      }
    ],
    "itemCompany": {
      "companyID": 1,
      "companyName": null
    }
  },
  {
    "itemID": 2,
    "itemName": "Elite Pañuelo",
    "itemCode": "7790250054344",
    "itemPrice": 250.25,
    "itemCount": 10,
    "itemRestrict": false,
    "itemAttribute": [
      {
        "attributeID": 1,
        "attributeName": null
      }
    ],
    "itemCompany": {
      "companyID": 1,
      "companyName": null
    }
  },
  {
    "itemID": 3,
    "itemName": "Lays Asado 18+ Ultra Violento",
    "itemCode": null,
    "itemPrice": 2000,
    "itemCount": 5,
    "itemRestrict": true,
    "itemAttribute": [
      {
        "attributeID": 3,
        "attributeName": null
      }
    ],
    "itemCompany": {
      "companyID": 3,
      "companyName": null
    }
  }
]
```

### Información sobre un Item individual

**General**

| URL              | Método | Response        |
|------------------|--------|-----------------| 
| `/item/{itemID}` | `GET`  | `item` [Object] |

**PathVariable**

| Nombre   | Tipo      | Valor                     |
|----------|-----------|---------------------------|
| `itemID` | `Integer` | El `itemID` de un `Item`. |

**Ejemplo [Request]**

`/item/1`

**Ejemplo [Response]**

```json
{
  "itemID": 1,
  "itemName": "Topline Ultra Green Mint",
  "itemCode": "7790580120702",
  "itemPrice": 250.25,
  "itemCount": 10,
  "itemRestrict": false,
  "itemAttribute": [
    {
      "attributeID": 1,
      "attributeName": "Fruta"
    }
  ],
  "itemCompany": {
    "companyID": 1,
    "companyName": "Manda Fruta"
  }
}
```

### Buscar Item

**General**

| URL          | Método | Response       |
|--------------|--------|----------------| 
| `/item/find` | `GET`  | `Item` [Array] |

**RequestParameter**

| Nombre         | Tipo      | Valor                                     |
|----------------|-----------|-------------------------------------------|
| `itemName`     | `String`  | El `itemName` de un `Item`.               |
| `itemCode`     | `String`  | El `itemCode` de un `Item`.               |
| `itemPriceMin` | `Float`   | Rango mínimo de `itemPrice` de un `Item`. |
| `itemPriceMax` | `Float`   | Rango máximo de `itemPrice` de un `Item`. |
| `itemCountMin` | `Integer` | Rango mínimo de `itemCount` de un `Item`. |
| `itemCountMax` | `Integer` | Rango máximo de `itemCount` de un `Item`. |
| `itemRestrict` | `Boolean` | El `itemRestrict` de un `Item`.           |

**Ejemplo [Request]**

`/item/find?itemName=Topline`

**Ejemplo [Response]**

```json
[
  {
    "itemID": 1,
    "itemName": "Topline Ultra Green Mint",
    "itemCode": "7790580120702",
    "itemPrice": 250.25,
    "itemCount": 10,
    "itemRestrict": false,
    "itemAttribute": [
      {
        "attributeID": 1,
        "attributeName": "Fruta"
      }
    ],
    "itemCompany": {
      "companyID": 1,
      "companyName": "Manda Fruta"
    }
  }
]
```