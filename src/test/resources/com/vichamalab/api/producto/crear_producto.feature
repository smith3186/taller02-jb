# language: es
@Crear
Requisito: Crear un nuevo producto usando la api /api/v1/product

  @Happypath
  Esquema del escenario: Validar código 200 para crear un producto con éxito
    Dada una API válida con ruta "/api/v1/product/" y formato "application/json"
    Cuando se hace una petición "POST" con el nombre "<nombre>"
    * y la descripción "<descripcion>"
    * y el precio <precio>
    * a la API
    Entonces se recibe una respuesta "exitosa" con código <codigo>
    * y el mensaje "<mensaje>"

    Ejemplos:
      | nombre     | descripcion           | precio | codigo | mensaje                           |
      | Myphone 11 | Un smartphone MyPhone | 1100   | 201    | El producto fue creado con éxito! |
      | Myphone 11 | Un smartphone MyPhone | 1100   | 201    | El producto fue creado con éxito! |


  @Sadpath
  Esquema del escenario: Validar código 400 para crear un producto con nombre vacío
    Dada una API válida con ruta "/api/v1/product/" y formato "application/json"
    Cuando se hace una petición "POST" con el nombre "<nombre>"
    * y la descripción "<descripcion>"
    * y el precio <precio>
    * a la API
    Entonces se recibe una respuesta "fallida" con código <codigo>
    * y el mensaje "<mensaje>"

    Ejemplos:
      | nombre | descripcion           | precio | codigo | mensaje                                     |
      |        | Un smartphone MyPhone | 1100   | 400    | El nombre del producto no fue proporcionado |
      |        | Un smartphone MyPhone | 1100   | 400    | El nombre del producto no fue proporcionado |


  @Sadpath
  Esquema del escenario: Validar código 400 para crear un producto con descripción vacío
    Dada una API válida con ruta "/api/v1/product/" y formato "application/json"
    Cuando se hace una petición "POST" con el nombre "<nombre>"
    * y la descripción "<descripcion>"
    * y el precio <precio>
    * a la API
    Entonces se recibe una respuesta "fallida" con código <codigo>
    * y el mensaje "<mensaje>"

    Ejemplos:
      | nombre     | descripcion | precio | codigo | mensaje                                          |
      | Myphone 11 |             | 1100   | 400    | La descripción del producto no fue proporcionada |
      | Myphone 11 |             | 1100   | 400    | La descripción del producto no fue proporcionada |