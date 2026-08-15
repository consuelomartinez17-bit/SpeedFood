# SpeedFood

SpeedFood es un programa que busca ayudar a la empresa de reparto SpeedFast,
automatizando la asignación de repartidores para los distintos tipos de pedido
según sus características propias.

## Tecnologías usadas

- Java (JDK 25)
- IntelliJ IDEA

## Estructura de clases

- **Pedido**: clase base que representa un pedido genérico dentro del sistema de reparto de SpeedFood.
- **PedidoComida**: representa un pedido de comida; hereda de Pedido y valida si se requiere mochila térmica para el transporte.
- **PedidoEncomienda**: representa un pedido de encomienda; hereda de Pedido y valida el peso y el embalaje del paquete.
- **PedidoExpress**: representa un pedido express; hereda de Pedido y valida la disponibilidad y cercanía del repartidor.
- **Main**: clase principal que instancia los distintos tipos de pedido y demuestra el uso de sobrecarga y sobreescritura del método `asignarRepartidor()`, aplicando polimorfismo mediante `instanceof` y casting.

## Cómo ejecutar el programa

1. Clonar o descomprimir el proyecto.
2. Abrir la carpeta en IntelliJ IDEA.
3. Ejecutar la clase `Main.java`.

## Autor

Consuelo