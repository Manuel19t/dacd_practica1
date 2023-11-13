package dacd.torrealba.practica1.model;

import java.time.Instant;

public interface WeatherStore {
    void save(Weather weather);
    Weather get(Location location, Instant instant);
}
