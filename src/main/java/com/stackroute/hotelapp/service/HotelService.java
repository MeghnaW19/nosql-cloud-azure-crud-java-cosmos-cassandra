package com.stackroute.hotelapp.service;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.stackroute.hotelapp.dao.HotelDao;
import com.stackroute.hotelapp.dao.HotelMapper;
import com.stackroute.hotelapp.dao.HotelMapperBuilder;
import com.stackroute.hotelapp.model.Hotel;
import com.stackroute.hotelapp.util.PropertyConfig;

import java.util.List;
import java.util.Optional;

/**
 * This is a service class having methods which use the HotelDao object
 * to perform CRUD operations on the Hotel entity
 */
public class HotelService {

    private HotelDao hotelDao;
    private String keyspace = PropertyConfig.getProperty("keyspace.name");

    /* **TODO**
     * Initialize the HotelDao object using the HotelMapper interface
     */
    public HotelService(CqlSession cqlSession) {
        HotelMapper hotelMapper = new HotelMapperBuilder(cqlSession).build();
        hotelDao = hotelMapper.getHotelDao(CqlIdentifier.fromCql(keyspace));
    }

    /* **TODO**
     * Implement the method for getting a hotel by hotelId
     */
    public Optional<Hotel> getHotelById(String state, String city, int hotelId) {
        return hotelDao.findById(state, city, hotelId);
    }

    /* **TODO**
     * Implement the method getting all hotels for given
     * state and city
     */
    public List<Hotel> getHotelsByStateAndCity(String state, String city) {
        return hotelDao.findByStateAndCity(state, city).all();
    }

    /* **TODO**
     * Implement the method for saving a hotel entity
     */

    public void createHotel(Hotel hotel) {
        hotelDao.save(hotel);
    }

    /* **TODO**
     * Implement the method for deleting a hotel entity
     */
    public void removeHotel(Hotel hotel) {
        hotelDao.deleteHotel(hotel);
    }

    /* **TODO**
     * Implement the method for deleting all hotels for given
     * state and city
     */
    public void removeHotelsByStateAndCity(String state, String city) {
        hotelDao.deleteByStateAndCity(state, city);
    }

    /* **TODO**
     * Implement the method for updating hotel entity
     */
    public void modifyHotel(Hotel hotel) {
        hotelDao.update(hotel);
    }

}
