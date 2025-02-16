package ru.practice.weathersensorrestapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @JsonIgnore
    private int id;

    @Column(name = "name")
    @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters.")
    private String name;

    @OneToMany(mappedBy = "sensor")
    //@JsonIgnore
    private List<Measurement> measurements;

    public Sensor() {
    }

    public Sensor(int id, String name, List<Measurement> measurements) {
        this.id = id;
        this.name = name;
        this.measurements = measurements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }
}
