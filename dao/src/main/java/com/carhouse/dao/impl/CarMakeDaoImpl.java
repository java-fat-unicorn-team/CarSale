package com.carhouse.dao.impl;

import com.carhouse.dao.CarMakeDao;
import com.carhouse.dao.mappers.CarMakeMapper;
import com.carhouse.model.CarMake;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class provides methods to manage CarMake model.
 * The class stores date in database
 * It is realisation of CarMakeDao interface
 * @see CarMakeDao
 * @author Katuranau Maksimilyan
 */
@Repository
public class CarMakeDaoImpl implements CarMakeDao {
    /**
     * SQL query to get car make.
     */
    @Value("${car.make.get}")
    private String GET_CAR_MAKE_SQL;
    /**
     * SQL query to get list of car makes.
     */
    @Value("${car.makes.list.get}")
    private String GET_LIST_CARS_MAKES_SQL;

    /**
     * named parameter JDBC template.
     */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * mapper to get CarMake object.
     */
    private final CarMakeMapper carMakeMapper;
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(CarMakeDaoImpl.class);

    /**
     * Instantiates a new Car make dao.
     *
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param carMakeMapper              the car make mapper
     */
    public CarMakeDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          final CarMakeMapper carMakeMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.carMakeMapper = carMakeMapper;
    }

    /**
     * Gets car makes.
     *
     * @return the list of car makes
     */
    @Override
    public List<CarMake> getCarMakes() {
        LOGGER.debug("method getCarMakes");
        return namedParameterJdbcTemplate.query(GET_LIST_CARS_MAKES_SQL, carMakeMapper);
    }

    /**
     * Gets car make by id.
     *
     * @param id the car make id
     * @return the car make
     */
    @Override
    public CarMake getCarMake(final int id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        LOGGER.debug("method getCarMake with parameter: [[}]", id);
        return namedParameterJdbcTemplate.queryForObject(GET_CAR_MAKE_SQL, parameters, carMakeMapper);
    }
}
