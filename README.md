# teems.it recruitment task

Welcome to teems.it's  recruitment coding task. The task is designed to test your ability to work with Spring Boot, JPA, and REST APIs.  
This is a blank Spring Boot project with a PostgreSQL database test container and a pre-configured `application.properties` file and a sample test to get you started.
The project is built using supplied mvn wrapper and requires java 21.  
To build it locally run depending on your system:  
`./mvnw clean package`  
`./mvnw.cmd clean package`  
Feel free to add any preferred libraries or tools to the project.  
Also feel free to refactor any existing code. It's just a place to start.  
The task has no time limit.  

## Task description

The task is to collect weather data from the https://www.weatherapi.com api, store it in a database and expose it via a REST API.


1. Create a scheduled task that will fetch weather data from the https://www.weatherapi.com api at a period defined by an env var (by default 1 hour).
   1. the data should be fetched for all the locations defined by the `weather.api.locations` property
2. The api key in the application.properties is valid, if it expired please contact us or create a free account at https://www.weatherapi.com to get a fresh one.
3. The api can be tested here: https://app.swaggerhub.com/apis-docs/WeatherAPI.com/WeatherAPI/1.0.2#/APIs/realtime-weather and the sample response is as follows:

   ```json
   {
     "location": {
       "name": "London",
       "region": "City of London, Greater London",
       "country": "United Kingdom",
       "lat": 51.52,
       "lon": -0.11,
       "tz_id": "Europe/London",
       "localtime_epoch": 1719252941,
       "localtime": "2024-06-24 19:15"
     },
     "current": {
       "last_updated_epoch": 1719252000,
       "last_updated": "2024-06-24 19:00",
       "temp_c": 26.1,
       "temp_f": 79,
       "is_day": 1,
       "condition": {
         "text": "Sunny",
         "icon": "//cdn.weatherapi.com/weather/64x64/day/113.png",
         "code": 1000
       },
       "wind_mph": 5.6,
       "wind_kph": 9,
       "wind_degree": 210,
       "wind_dir": "SSW",
       "pressure_mb": 1017,
       "pressure_in": 30.03,
       "precip_mm": 0,
       "precip_in": 0,
       "humidity": 48,
       "cloud": 0,
       "feelslike_c": 27.1,
       "feelslike_f": 80.8,
       "windchill_c": 23.3,
       "windchill_f": 74,
       "heatindex_c": 25,
       "heatindex_f": 77,
       "dewpoint_c": 14.4,
       "dewpoint_f": 57.9,
       "vis_km": 10,
       "vis_miles": 6,
       "uv": 6,
       "gust_mph": 8.6,
       "gust_kph": 13.8
     }
   }
   ```

4. On each run of the scheduled task save the fetched data in the database with the following schema:
    - id - bigint auto-incremented
    - location_name - text
    - last_updated - timestamp
    - country - text
    - temperature_celsius - double
    - wind_kph - double
    - precip_mm - int

5. Create a REST endpoint that will return the data from the database in a paginated way. The endpoint should accept the following query parameters:
   - date_from (optional, defaults to now - 1 week)
   - date_to (optional, defaults to now)
   - location name (optional)

6. Create a REST endpoint that will return the average temperature for each location in the database on a given day.
   - date (required)

7. Think of what would be valuable to test.
8. Think of what would be valuable to document in this project.
9. Don't feel pressured to make this perfect.
10. At the end of your work write-down what you would do next if you had more time.
