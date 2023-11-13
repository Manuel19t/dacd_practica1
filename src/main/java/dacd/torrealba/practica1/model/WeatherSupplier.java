package dacd.torrealba.practica1.model;

import java.time.Instant;
import java.util.List;

public interface WeatherSupplier {
    List<Weather> getWeather(Location location, Instant ts);
}
