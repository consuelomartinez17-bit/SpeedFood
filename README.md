# SpeedFood

SpeedFood es un programa que busca ayudar a la empresa de reparto SpeedFast,
automatizando la asignación de repartidores para los distintos tipos de pedido
según sus características propias.

## Tecnologías usadas

- Java (JDK 25)
- IntelliJ IDEA

## Estructura de clases

- **Pedido**: clase abstracta que representa un pedido genérico dentro del sistema de reparto de SpeedFood. Define los atributos comunes (`idPedido`, `direccionEntrega`, `distanciaKm`), el método `mostrarResumen()` y el método abstracto `calcularTiempoEntrega()`, que cada subclase implementa con su propia lógica.
- **PedidoComida**: representa un pedido de comida; hereda de Pedido, valida si se requiere mochila térmica para el transporte y calcula su tiempo de entrega en base a 15 minutos base más 2 minutos por kilómetro, más 3 minutos extra si requiere mochila térmica.
- **PedidoEncomienda**: representa un pedido de encomienda; hereda de Pedido, valida el peso y el embalaje del paquete, y calcula su tiempo de entrega en base a 20 minutos base más 1.5 minutos por kilómetro, más 0.5 minutos por cada kilo de peso (redondeado).
- **PedidoExpress**: representa un pedido express; hereda de Pedido, valida la disponibilidad del repartidor, y calcula su tiempo de entrega en base a 10 minutos, sumando 5 minutos extra si la distancia supera los 5 km, y 8 minutos extra si no hay repartidor disponible.
- **Main**: clase principal que instancia los distintos tipos de pedido, demuestra sobrecarga y sobreescritura de `asignarRepartidor()`, invoca `calcularTiempoEntrega()` y `mostrarResumen()`, y simula el flujo completo de despacho, cancelación e historial mediante `ControladorDeEnvios`.

## Interfaces (semana 3)

- **Despachable**: define el método `despachar()`, que marca el pedido como en ruta hacia el cliente.
- **Cancelable**: define el método `cancelar(String motivo)`, que cancela el pedido siempre que no haya sido despachado previamente.
- **Rastreable**: define el método `verHistorial()`, que retorna la lista de eventos registrados del pedido.

Las tres interfaces son implementadas directamente por `PedidoComida`, `PedidoEncomienda` y `PedidoExpress`.

## ControladorDeEnvios (semana 3)

Clase orquestadora (package `cl.speedfood.gestores`) que mantiene listas de tipo `Despachable`, `Cancelable` y `Rastreable`, y permite despachar, cancelar y consultar el historial de todos los pedidos registrados sin conocer su tipo concreto.

## Diagrama de clases

![Diagrama de clases](diagrama-clases-speedfood.png)

## Contribución del diseño a la calidad del software

**Reutilización**: la clase abstracta `Pedido` centraliza los atributos comunes
y el método `mostrarResumen()`, evitando que este código se repita en cada
subclase.

**Escalabilidad**: si en el futuro se necesita agregar un nuevo tipo de pedido,
basta con crear una nueva subclase que extienda `Pedido` e implemente las
interfaces `Despachable`, `Cancelable` y `Rastreable`. No es necesario modificar
`ControladorDeEnvios`, ya que este trabaja con los tipos genéricos de las
interfaces y no con clases concretas.

**Mantenibilidad**: las interfaces desacoplan responsabilidades específicas
(despacho, cancelación, historial) de la lógica particular de cada tipo de
pedido, permitiendo modificar una operación sin afectar el resto del sistema.

## Cómo ejecutar el programa

1. Clonar o descomprimir el proyecto.
2. Abrir la carpeta en IntelliJ IDEA.
3. Ejecutar la clase `Main.java`.

## Autor

Consuelo