package ru.practice.weathersensorrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practice.weathersensorrestapi.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    public int countByRainingIsTrue();
}
