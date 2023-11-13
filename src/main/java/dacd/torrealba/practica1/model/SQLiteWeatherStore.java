package dacd.torrealba.practica1.model;

import java.sql.*;
import java.time.Instant;
import java.util.List;

public class SQLiteWeatherStore implements WeatherStore {
    private static final String DB = "jdbc:sqlite:Weather.db";
    private List<Location> locations;

    public SQLiteWeatherStore(List<Location> locations) {
        this.locations = locations;
        createTablesIfNotExits();
    }

    @Override
    public void save(Weather weather) {
        String tableName = getTableName(weather.getLocation().getIsland());
        String selectQuery = "SELECT * FROM " + tableName + "WHERE TimeStamp = ?";

        try (Connection connection = DriverManager.getConnection(DB);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setObject(1, Timestamp.from(weather.getTs()));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String updateQuery = "UPDATE " + tableName + " SET Temperature = ?, Rain = ?, " +
                        "Humidity = ?, Wind = ? WHERE TimeStamp = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setDouble(1, weather.getTemp());
                    updateStatement.setDouble(2, weather.getRain());
                    updateStatement.setInt(3, weather.getHumidity());
                    updateStatement.setDouble(4, weather.getWind());
                    updateStatement.setObject(5, Timestamp.from(weather.getTs()));
                    updateStatement.executeUpdate();
                }
            } else {
                String insertQuery = "INSERT INTO " + tableName + " (Location, Lat, Lon, Temperature, " +
                        "Rain, Humidity, Wind, TimeStamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, weather.getLocation().getIsland());
                    insertStatement.setDouble(2, weather.getLocation().getLat());
                    insertStatement.setDouble(3, weather.getLocation().getLon());
                    insertStatement.setDouble(4, weather.getTemp());
                    insertStatement.setDouble(5, weather.getRain());
                    insertStatement.setInt(6, weather.getHumidity());
                    insertStatement.setDouble(7, weather.getWind());
                    insertStatement.setObject(8, Timestamp.from(weather.getTs()));
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Weather get(Location location, Instant instant) {
        String tableName =getTableName(location.getIsland());
        String selectQuery = "SELECT * FROM " + tableName + " WHERE TimeStamp = ?";

        try (Connection connection = DriverManager.getConnection(DB);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setObject(1, Timestamp.from(instant));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double temp = resultSet.getDouble("Temperature");
                double rain = resultSet.getDouble("Rain");
                int humidity = resultSet.getInt("Humidity");
                double wind = resultSet.getDouble("Wind");
                double lat = resultSet.getDouble("Lat");
                double lon = resultSet.getDouble("Lon");
                String island = resultSet.getString("Island");
                Instant ts = resultSet.getTimestamp("TimeStamp").toInstant();

                return new Weather(ts, rain, temp, wind, humidity, new Location(island, lat, lon));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private void createTablesIfNotExits() {
        try (Connection connection = DriverManager.getConnection(DB)) {
            for (Location location : locations) {
                String tableName = getTableName(location.getIsland());
                String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "Location TEXT," +
                        "Lat REAL," +
                        "Lon REAL," +
                        "Temperature REAL," +
                        "Rain REAL," +
                        "Humidity INTEGER," +
                        "Wind REAL," +
                        "TimeStamp TIMESTAMP)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery)) {
                    preparedStatement.execute();}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTableName(String island) {
        return "Weather_" + island.replace(" ", "_");
    }

}
