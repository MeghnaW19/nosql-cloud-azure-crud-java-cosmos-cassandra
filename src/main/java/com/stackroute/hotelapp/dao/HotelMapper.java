package com.stackroute.hotelapp.dao;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

/**
 * *TODO**
 * This interface is Mapper interface which acts as a factory of DAO objects
 * that will be used to execute requests
 * Apply annotation to make this interface as a Mapper Interface
 */
@Mapper
public interface HotelMapper {

    /* **TODO**
     * apply annotations to this method and parameter to define
     * it as a factory of HotelDao object
     */
    @DaoFactory
    HotelDao getHotelDao(@DaoKeyspace CqlIdentifier keyspace);
}
