package dacd.torrealba.practica1.control;

import dacd.torrealba.practica1.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        String apiKey = "fa7f5fe528f627f90faafbc54fa3d58d";

        List<Location> locations = new ArrayList<>();
        locations.add(new Location("La Palma", 28.65, -17.91));
        locations.add(new Location("La Gomera", 28.08, -17.33));
        locations.add(new Location("El Hierro", 27.75, -18.01));
        locations.add(new Location("Tenerife", 28.46, -16.24));
        locations.add(new Location("Las Palmas", 28.13, -15.43));
        locations.add(new Location("Fuerteventura", 28.50, -13.86));
        locations.add(new Location("Lanzarote", 28.96, -13.55));

        WeatherSupplier weatherSupplier = new OpenWeatherMapSupplier(apiKey, locations);
        WeatherStore weatherStore = new SQLiteWeatherStore(locations);
        WeatherController weatherController = new WeatherController(weatherSupplier, weatherStore);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                for (Location location : locations) {
                    weatherController.execute(location, Instant.now());
                }
            }
        };

        long delay = 0;
        long period = 60 * 60 * 1000;

        timer.scheduleAtFixedRate(task, delay, period);

    }
}