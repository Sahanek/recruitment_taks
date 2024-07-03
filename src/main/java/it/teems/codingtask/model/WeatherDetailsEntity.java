package it.teems.codingtask.model;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigInteger;
import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(
        name = "WeatherDetailsEntity.findAvgTemperatureByLocationsBetweenDates",
        query =
                "Select t.locationName, AVG(t.temperatureCelsius) from WeatherDetailsEntity t where t.lastUpdate between :from and :to group by t.locationName")
@Table(name = "weather_details")
public class WeatherDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_details_id")
    @SequenceGenerator(
            name = "weather_details_id",
            sequenceName = "weather_id_seq",
            allocationSize = 1)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @Column(name = "country")
    private String country;

    @Column(name = "temperature_celsius")
    private Double temperatureCelsius;

    @Column(name = "wind_kph")
    private Double windKph;

    @Column(name = "precip_mm")
    private Integer precipMm;
}
