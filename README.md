# SpeedFood

SpeedFood es un programa que busca ayudar a la empresa de reparto SpeedFast,
automatizando la asignación de repartidores para los distintos tipos de pedido
según sus características propias.

## Tecnologías usadas

- Java (JDK 25)
- IntelliJ IDEA

## Estructura del repositorio

- **semana 3/**: entrega archivada correspondiente a la Semana 3 (jerarquía de clases, polimorfismo e interfaces).
- **semana 4/**: entrega archivada correspondiente a la Semana 4 (concurrencia básica con Repartidor y ExecutorService).
- **src/**: código fuente activo, con las entregas de Semana 3, 4 y 5 integradas.

## Estructura de clases (paquete `cl.speedfood`)

- **Pedido**: clase abstracta que representa un pedido genérico dentro del sistema de reparto de SpeedFood. Define los atributos comunes (`idPedido`, `direccionEntrega`, `distanciaKm`), el método `mostrarResumen()`, el método abstracto `calcularTiempoEntrega()` (implementado por cada subclase), el método abstracto `isCancelado()` y valida sus parámetros en el constructor, lanzando `IllegalArgumentException` ante datos inválidos.
- **PedidoComida**: representa un pedido de comida; hereda de Pedido, valida si se requiere mochila térmica para el transporte y calcula su tiempo de entrega en base a 15 minutos base más 2 minutos por kilómetro, más 3 minutos extra si requiere mochila térmica.
- **PedidoEncomienda**: representa un pedido de encomienda; hereda de Pedido, valida el peso (no puede ser negativo) y el embalaje del paquete, y calcula su tiempo de entrega en base a 20 minutos base más 1.5 minutos por kilómetro, más 0.5 minutos por cada kilo de peso (redondeado).
- **PedidoExpress**: representa un pedido express; hereda de Pedido, valida la disponibilidad del repartidor, y calcula su tiempo de entrega en base a 10 minutos, sumando 5 minutos extra si la distancia supera los 5 km, y 8 minutos extra si no hay repartidor disponible.
- **Repartidor** *(semana 4)*: implementa `Runnable`. Representa a un repartidor que procesa su lista de pedidos asignados de forma secuencial dentro de un hilo independiente, simulando el tiempo de entrega con `Thread.sleep()` y omitiendo pedidos nulos o ya cancelados.
- **Main**: clase principal que instancia los distintos tipos de pedido, demuestra sobrecarga y sobreescritura de `asignarRepartidor()`, invoca `calcularTiempoEntrega()` y `mostrarResumen()`, simula el flujo completo de despacho, cancelación e historial mediante `ControladorDeEnvios`, y coordina la simulación concurrente de entregas mediante `Repartidor` y un `ExecutorService`.

## Interfaces (semana 3)

- **Despachable**: define el método `despachar()`, que marca el pedido como en ruta hacia el cliente.
- **Cancelable**: define el método `cancelar(String motivo)`, que cancela el pedido siempre que no haya sido despachado previamente.
- **Rastreable**: define el método `verHistorial()`, que retorna la lista de eventos registrados del pedido.

Las tres interfaces son implementadas directamente por `PedidoComida`, `PedidoEncomienda` y `PedidoExpress`.

## ControladorDeEnvios (semana 3)

Clase orquestadora (package `cl.speedfood.gestores`) que mantiene listas de tipo `Despachable`, `Cancelable` y `Rastreable`, y permite despachar, cancelar y consultar el historial de todos los pedidos registrados sin conocer su tipo concreto.

## Concurrencia (semana 4)

Se agregó una simulación de entregas concurrentes utilizando:

- **Repartidor**: hilo productor-consumidor que recorre su lista de pedidos, imprime el avance de cada entrega (`[Repartidor: Nombre] Entregando TipoPedido #id...`) y simula el tiempo de reparto con una espera aleatoria.
- **ExecutorService**: en `Main`, se instancian 3 repartidores con al menos 2 pedidos cada uno y se ejecutan en paralelo mediante un pool de hilos, con `awaitTermination` para un cierre controlado.
- **Manejo de excepciones**: se agregaron pedidos con datos inválidos a propósito en `Main`, capturados mediante `try-catch`, demostrando que el programa continúa su ejecución sin caerse ante errores.
- **Resumen final con Streams**: al cierre de la simulación, se calcula mediante `Stream` la cantidad de pedidos cancelados versus activos/entregados.

## Sincronización — Zona de Carga (semana 5, paquete `cl.speedfood.zonacarga`)

Paquete independiente que aborda un problema de sincronización distinto: evitar que múltiples repartidores retiren el mismo pedido de un recurso compartido.

- **EstadoPedido** (enum): representa los estados posibles de un pedido: `PENDIENTE`, `EN_REPARTO`, `ENTREGADO`.
- **Pedido**: versión simplificada para este caso, con `id`, `direccionEntrega` y `estado`, junto a sus getters y setters. El método `setEstado(String)` convierte el texto recibido al enum correspondiente mediante `EstadoPedido.valueOf(...)`.
- **ZonaDeCarga**: recurso compartido que encapsula una `BlockingQueue<Pedido>`. Sus métodos `agregarPedido()` y `retirarPedido()` son `synchronized`, garantizando que ningún pedido sea retirado por más de un repartidor. `retirarPedido()` usa `poll(1, TimeUnit.SECONDS)` para esperar brevemente por nuevos pedidos antes de indicar que ya no queda trabajo (retornando `null`), anunciando `"[Zona de carga vacía]"` por consola una única vez.
- **Repartidor**: hilo que retira pedidos en un ciclo continuo hasta que la zona de carga se queda sin pedidos, momento en el que finaliza su ejecución de forma natural (sin necesidad de ser interrumpido). Por cada pedido, imprime su avance en 4 etapas: retiro, cambio a `EN_REPARTO`, inicio de entrega y cambio a `ENTREGADO`.
- **Main**: crea la zona de carga, agrega 5 pedidos, lanza 3 repartidores concurrentes mediante `ExecutorService`, y espera su cierre ordenado con `shutdown()` y `awaitTermination()`.

## Diagrama de clases

![Diagrama de clases](diagrama-clases-speedfood.png)

## Contribución del diseño a la calidad del software

**Reutilización**: la clase abstracta `Pedido` centraliza los atributos comunes,
el método `mostrarResumen()` y la validación de datos, evitando que este código
se repita en cada subclase.

**Escalabilidad**: si en el futuro se necesita agregar un nuevo tipo de pedido,
basta con crear una nueva subclase que extienda `Pedido` e implemente las
interfaces `Despachable`, `Cancelable` y `Rastreable`. No es necesario modificar
`ControladorDeEnvios`, ya que este trabaja con los tipos genéricos de las
interfaces y no con clases concretas. De la misma forma, `Repartidor` trabaja
únicamente con referencias `Pedido`, sin conocer el tipo concreto de cada uno.

**Mantenibilidad**: las interfaces desacoplan responsabilidades específicas
(despacho, cancelación, historial) de la lógica particular de cada tipo de
pedido, permitiendo modificar una operación sin afectar el resto del sistema.

**Aislamiento de paquetes**: el paquete `cl.speedfood.zonacarga` (semana 5)
convive con `cl.speedfood` sin conflictos, ya que ambos definen su propia
clase `Pedido` con propósitos distintos, demostrando cómo el sistema de
paquetes de Java permite evolucionar un proyecto sin romper lo ya construido.

## Cómo ejecutar el programa

1. Clonar o descomprimir el proyecto.
2. Abrir la carpeta en IntelliJ IDEA.
3. Ejecutar `cl.speedfood.Main` para las entregas de Semana 3 y 4, o `cl.speedfood.zonacarga.Main` para la Semana 5.

## Autor

Consuelo