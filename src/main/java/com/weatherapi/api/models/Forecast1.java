/*
 * WeatherAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package com.weatherapi.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

@JsonInclude(Include.ALWAYS)
public class Forecast1 
        implements java.io.Serializable {
    private static final long serialVersionUID = -130339190716898983L;
    private List<com.weatherapi.api.models.Forecastday1> forecastday;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("forecastday")
    public List<com.weatherapi.api.models.Forecastday1> getForecastday ( ) {
        return this.forecastday;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("forecastday")
    public void setForecastday (List<Forecastday1> value) {
        this.forecastday = value;
    }
 
}
