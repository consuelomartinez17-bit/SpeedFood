# SpeedFood

SpeedFood es un programa que busca ayudar a la empresa de reparto SpeedFast,
automatizando la asignación de repartidores para los distintos tipos de pedido
según sus características propias.

## Tecnologías usadas

- Java (JDK 25)
- IntelliJ IDEA

## Estructura de clases

- **Pedido**: clase abstracta que representa un pedido genérico dentro del sistema de reparto de SpeedFood. Define los atributos comunes (`idPedido`, `direccionEntrega`, `distanciaKm`), el método `mostrarResumen()` y el método abstracto `calcularTiempoEntrega()`, que cada subclase implementa con su propia lógica.
- **PedidoComida**: representa un pedido de comida; hereda de Pedido, valida si se requiere mochila térmica para el transporte y calcula su tiempo de entrega en base a 15 minutos base más 2 minutos por kilómetro.
- **PedidoEncomienda**: representa un pedido de encomienda; hereda de Pedido, valida el peso y el embalaje del paquete, y calcula su tiempo de entrega en base a 20 minutos base más 1.5 minutos por kilómetro (redondeado).
- **PedidoExpress**: representa un pedido express; hereda de Pedido, valida la disponibilidad y cercanía del repartidor, y calcula su tiempo de entrega en base a 10 minutos, sumando 5 minutos extra si la distancia supera los 5 km.
- **Main**: clase principal que instancia los distintos tipos de pedido y demuestra el uso de sobrecarga y sobreescritura del método `asignarRepartidor()`, aplicando polimorfismo mediante `instanceof` y casting, además de invocar `mostrarResumen()` y `calcularTiempoEntrega()` en cada pedido.

## Cómo ejecutar el programa

1. Clonar o descomprimir el proyecto.
2. Abrir la carpeta en IntelliJ IDEA.
3. Ejecutar la clase `Main.java`.

## Autor

Consuelo