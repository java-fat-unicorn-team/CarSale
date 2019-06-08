package com.spring.rest.model.mappers;

import com.spring.rest.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Car mapper.
 */
@Component
public class CarMapper implements RowMapper<Car> {
    /**
     * The constant CAR_ID.
     */
    public static final String CAR_ID = "car_id";
    /**
     * The constant YEAR.
     */
    public static final String YEAR = "year";
    /**
     * The constant MILEAGE.
     */
    public static final String MILEAGE = "mileage";
    private FuelTypeMapper fuelTypeMapper;
    private TransmissionMapper transmissionMapper;
    private CarModelMapper carModelMapper;

    /**
     * Instantiates a new Car mapper.
     *
     * @param fuelTypeMapper     the fuel type mapper
     * @param transmissionMapper the transmission mapper
     * @param carModelMapper     the car model mapper
     */
    @Autowired
    public CarMapper(final FuelTypeMapper fuelTypeMapper,
                     final TransmissionMapper transmissionMapper,
                     final CarModelMapper carModelMapper) {
        this.fuelTypeMapper = fuelTypeMapper;
        this.transmissionMapper = transmissionMapper;
        this.carModelMapper = carModelMapper;
    }


    @Override
    public Car mapRow(final ResultSet resultSet, final int i)
            throws SQLException {
        return new Car(resultSet.getInt(CAR_ID), resultSet.getDate(YEAR),
                resultSet.getInt(MILEAGE), fuelTypeMapper.mapRow(resultSet, i),
                transmissionMapper.mapRow(resultSet, i),
                carModelMapper.mapRow(resultSet, i));
    }
}
