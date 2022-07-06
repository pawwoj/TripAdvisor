/*
 * WeatherAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package com.weatherapi.api.models;

public class ForecastdayBuilder {
    //the instance to build
    private com.weatherapi.api.models.Forecastday forecastday;

    /**
     * Default constructor to initialize the instance
     */
    public ForecastdayBuilder() {
        forecastday = new com.weatherapi.api.models.Forecastday();
    }

    /**
     * Forecast date
     */
    public ForecastdayBuilder date(String date) {
        forecastday.setDate(date);
        return this;
    }

    /**
     * Forecast date as unix time.
     */
    public ForecastdayBuilder dateEpoch(Integer dateEpoch) {
        forecastday.setDateEpoch(dateEpoch);
        return this;
    }

    /**
     * See day element
     */
    public ForecastdayBuilder day(Day day) {
        forecastday.setDay(day);
        return this;
    }

    public ForecastdayBuilder astro(Astro astro) {
        forecastday.setAstro(astro);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public Forecastday build() {
        return forecastday;
    }
}