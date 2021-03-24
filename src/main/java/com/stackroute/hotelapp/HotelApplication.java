package com.stackroute.hotelapp;

import com.datastax.oss.driver.api.core.CqlSession;
import com.stackroute.hotelapp.util.DatabaseUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is provided with template to manually test your code
 * for self verification. Actual testing of your implementations
 * will be done using the Test classes provided
 */
@Slf4j
public class HotelApplication {

    public static final String HOTEL_KEYSPACE = "hotel_keyspace";

    public static void main(String[] args) {
        DatabaseUtility dbUtility = new DatabaseUtility();
        try (CqlSession session = dbUtility.getSession()) {
            dbUtility.createKeyspace(session);
            /* **TODO**
             * You may use this class to manually test the HotelService methods
             * for the CRUD operations. This class is for your self verification only
             */

        }
    }
}