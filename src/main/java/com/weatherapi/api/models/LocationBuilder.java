/*
 * WeatherAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package com.weatherapi.api.models;

public class LocationBuilder {
    //the instance to build
    private com.weatherapi.api.models.Location location;

    /**
     * Default constructor to initialize the instance
     */
    public LocationBuilder() {
        location = new com.weatherapi.api.models.Location();
    }

    /**
     * Local area name.
     */
    public LocationBuilder name(String name) {
        location.setName(name);
        return this;
    }

    /**
     * Local area region.
     */
    public LocationBuilder region(String region) {
        location.setRegion(region);
        return this;
    }

    /**
     * Country
     */
    public LocationBuilder country(String country) {
        location.setCountry(country);
        return this;
    }

    /**
     * Area latitude
     */
    public LocationBuilder lat(Double lat) {
        location.setLat(lat);
        return this;
    }

    /**
     * Area longitude
     */
    public LocationBuilder lon(Double lon) {
        location.setLon(lon);
        return this;
    }

    /**
     * Time zone
     */
    public LocationBuilder tzId(String tzId) {
        location.setTzId(tzId);
        return this;
    }

    /**
     * Local date and time in unix time
     */
    public LocationBuilder localtimeEpoch(Integer localtimeEpoch) {
        location.setLocaltimeEpoch(localtimeEpoch);
        return this;
    }

    /**
     * Local date and time
     */
    public LocationBuilder localtime(String localtime) {
        location.setLocaltime(localtime);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public Location build() {
        return location;
    }
}