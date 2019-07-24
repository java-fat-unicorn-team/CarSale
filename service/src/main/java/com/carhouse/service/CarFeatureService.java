package com.carhouse.service;

import com.carhouse.model.CarFeature;

import java.util.List;

/**
 * The interface of carFeature service.
 * provides methods to get CarFeature models.
 * @author Katuranau Maksimilyan
 * @see CarFeature
 */
public interface CarFeatureService {
    /**
     * Gets all features of car with id which is provided.
     *
     * @param carId the car id
     * @return the list of car features
     */
    List<CarFeature> getCarFeatures(int carId);
}