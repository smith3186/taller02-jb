@CreateProduct
Feature: Crear un nuevo producto usando la api /api/v1/product

  @HappyPath
  Scenario: Crear un producto con éxito
    Given una API válida
    When se hace una petición con el nombre "Iphone 15"
    And la descripción "Telefono de alta gama"
    And el precio 3500
    And a la url "/api/v1/product/"
    Then se recibe una respuesta exitosa con código 201
    And el mensaje "El producto fue creado con éxito!"
