package dacd.torrealba.practica1.model;

import java.time.Instant;

public class Weather {
    private Instant ts;
    private double rain;
    private double temp;
    private double wind;
    private int humidity;
    private Location location;

    public Weather(Instant ts, double rain, double temp, double wind, int humidity, Location location) {
        this.ts = ts;
        this.rain = rain;
        this.temp = temp;
        this.wind = wind;
        this.humidity = humidity;
        this.location = location;
    }

    public Instant getTs() {
        return ts;
    }

    public void setTs(Instant ts) {
        this.ts = ts;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
