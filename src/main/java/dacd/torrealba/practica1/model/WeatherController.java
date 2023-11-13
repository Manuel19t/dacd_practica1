package dacd.torrealba.practica1.model;

import java.time.Instant;
import java.util.List;

public class WeatherController {
    private final WeatherSupplier weatherSupplier;
    private final WeatherStore weatherStore;

    public WeatherController(WeatherSupplier weatherSupplier, WeatherStore weatherStore) {
        this.weatherSupplier = weatherSupplier;
        this.weatherStore = weatherStore;
    }

    public void execute(Location location, Instant ts) {
        List<Weather> weatherList = weatherSupplier.getWeather(location, ts);

        for (Weather weather : weatherList) {
            Weather weatherRepeat = weatherStore.get(location, weather.getTs());

            if (weatherRepeat != null) {
                weatherRepeat.setTemp(weather.getTemp());
                weatherRepeat.setWind(weather.getWind());
                weatherRepeat.setHumidity(weather.getHumidity());
                weatherRepeat.setRain(weather.getRain());

                weatherStore.save(weatherRepeat);
                System.out.println("Predicciones actualizadas en la base de datos");
            } else {
                weatherStore.save(weather);
                System.out.println("Predicciones guardadas en la base de datos");
            }
        }
        System.out.println("-----------------------------------------------");
    }
}
