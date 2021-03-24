package com.stackroute.hotelapp.model;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * *TODO**
 * This class represents the Hotel entity to be persisted in database
 * Use the annotations of Datastax driver mapper module for mapping the class to an entity
 * Apply appropriate annotation to the class to make it an Entity
 * Map the table name as 'hotels_by_state_city'
 */

@Entity
@CqlName("hotels_by_state_city")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    /* **TODO**
     * Apply appropriate annotation to below fields to achieve the following mapping
     *   state      -> partitioning key
     *   city       -> clustering column
     *   hotelId    -> clustering column with column name as 'hotel_id'
     *   hotelName  -> map with column name 'hotel_name
     *   websiteUrl -> map with column name 'website_url
     */
    @PartitionKey
    private String state;
    @ClusteringColumn(1)
    private String city;
    @ClusteringColumn(2)
    @CqlName("hotel_id")
    private int hotelId;
    @CqlName("hotel_name")
    private String hotelName;
    @CqlName("website_url")
    private String websiteUrl;
}