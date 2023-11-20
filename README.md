# Primera Practica: App para registrar predicción del tiempo

## Asignatura
Desarrollo de Aplicaciones para ciencia de datos

## Titulación
Grado en ciencia e Ingeniería de Datos

## Universidad
Universidad de Las Palmas de Gran Canaria

---

## Resumen de la Funcionalidad

Este proyecto implementa un sistema de predicción del tiempo que utiliza la API de OpenWeatherMap para obtener datos meteorológicos y almacenarlos en una base de datos SQLite. El objetivo es que cada 6 horas el programa consulte la API durante los proximos cinco dias, si los datos ya estan, se actualizan en la base de datos, y si es un dato de un dia nuevo se añadira en la base de datos.

---

## Recursos Utilizados

- **Entornos de Desarrollo:** IntelliJ IDEA
- **Herramientas de Control de Versiones:** Git y GitHub
- **Herramientas de Documentación:** Markdown

---

## Diseño

### Principios de Diseño Utilizados

El proyecto sigue los siguientes principios de diseño:

Nos hemos apoyado en el diagrama de clases expuesto en clase por el profesor, en el que en la clase Weather se implementan los datos meteorologicos, en la clase Location las ubicaciones de las islas, con estas ubicaciones usaremos la interfaz WeatherSupplier para leer la API con la clase OpenWeatherMapSupplier y luego guardarlo en la base de datos con la ayuda de la interfaz WeatherStore y la clase SQLiteStore que contiene el codigo de la base de datos. Para terminar, la clase WeatherController nos permite controllar todas las clases anteriores y ejecutarlas entre si, y en la clase Main nos encargamos de inicializar la APIKey, las localizaciones y ejecutar el codigo con sus tareas correspondientes

Las clases principales son:

- **Main:** Clase principal que inicia el sistema de predicción del tiempo.
- **Location:** Representa la ubicación geográfica de una isla.
- **Weather:** Contiene datos meteorológicos para una ubicación y momento específicos.
- **WeatherSupplier:** Interfaz para proveedores de datos meteorológicos.
- **OpenWeatherMapSupplier:** Implementación concreta de `WeatherSupplier` que utiliza la API de OpenWeatherMap.
- **WeatherStore:** Interfaz para el almacenamiento de datos meteorológicos.
- **SQLiteWeatherStore:** Implementación concreta de `WeatherStore` que utiliza una base de datos SQLite.
- **WeatherController:** Coordina la obtención y almacenamiento de datos meteorológicos.
