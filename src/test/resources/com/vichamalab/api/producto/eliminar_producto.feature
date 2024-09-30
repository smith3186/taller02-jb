# language: es
@Eliminar
Requisito: Eliminar con exito un producto previamente creado

  Antecedentes:
    Dada un producto con nombre "Eliminar"
    * y la descripción "Descripción original"
    * y el precio 1500
    * previamente creado con exito

  @HappyPath
  Escenario: Validar código 200 para eliminar un producto creado previamente con éxito
    Cuando se hace una petición "DELETE" con el nombre "Eliminar"
    * a la API
    Entonces se recibe una respuesta "exitosa" con código 200
    * y el mensaje "El producto fue eliminado con éxito"

