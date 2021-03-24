package com.stackroute.hotelapp.test;

import com.datastax.oss.driver.api.core.CqlSession;
import com.stackroute.hotelapp.model.Hotel;
import com.stackroute.hotelapp.service.HotelService;
import com.stackroute.hotelapp.util.DatabaseUtility;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HotelServiceITTests {
    public static final String KARNATAKA = "karnataka";
    public static final String BANGALORE = "bangalore";
    public static final String DELHI = "delhi";
    public static final String NEWDELHI = "newdelhi";
    private static DatabaseUtility dbUtility;
    private static CqlSession session;
    private HotelService hotelService;
    private Hotel hotelOne, hotelTwo, hotelThree;


    @BeforeAll
    public static void databaseSetup() throws InterruptedException {
        dbUtility = new DatabaseUtility();
        session = dbUtility.getSession();
        dbUtility.createKeyspace(session);
        Thread.sleep(20000);
        dbUtility.createTable(session);
        Thread.sleep(40000);
    }

    @AfterAll
    public static void cleanUpResources() {
        session.close();
        dbUtility = null;
    }

    @BeforeEach
    public void setUp() {
        hotelService = new HotelService(session);
        hotelOne = new Hotel(KARNATAKA, BANGALORE, 101, "bangalore inn", "www.bangaloreinn.com");
        hotelTwo = new Hotel(KARNATAKA, BANGALORE, 102, "oberoi", "www.oberoi.com");
        hotelThree = new Hotel(DELHI, NEWDELHI, 103, "delhi inn", "www.delhiinn.com");
    }

    @AfterEach
    public void tearDown() {
        hotelService = null;
        hotelOne = null;
        hotelTwo = null;
        hotelThree = null;
    }

    @Test
    public void givenHotelObjectsWhenSavedToDbThenShouldBeInsertedInTable() {
        hotelService.createHotel(hotelOne);
        Optional<Hotel> optionalHotel = hotelService.getHotelById(KARNATAKA, BANGALORE, 101);
        assertThat(optionalHotel.isEmpty(), is(false));
        Hotel hotel = optionalHotel.get();
        assertThat(hotel, is(hotelOne));
        hotelService.removeHotel(hotelOne);
    }

    @Test
    public void givenHotelIdStateAndCityWhenHotelExistsThenReturnTheHotelFromDb() {
        hotelService.createHotel(hotelThree);
        Optional<Hotel> optionalHotel = hotelService.getHotelById(DELHI, NEWDELHI, 103);
        assertThat(optionalHotel.isEmpty(), is(false));
        Hotel hotel = optionalHotel.get();
        assertThat(hotel, is(hotelThree));
        hotelService.removeHotel(hotelThree);
    }

    @Test
    public void givenHotelIdStateAndCityWhenHotelDoesNotExistThenReturnEmptyOptional() {
        Optional<Hotel> optionalHotel = hotelService.getHotelById(KARNATAKA, NEWDELHI, 999);
        assertThat(optionalHotel.isEmpty(), is(true));
    }

    @Test
    public void givenStateAndCityThenReturnListOfHotels() {
        hotelService.createHotel(hotelOne);
        hotelService.createHotel(hotelTwo);
        List<Hotel> hotels = hotelService.getHotelsByStateAndCity(KARNATAKA, BANGALORE);
        assertThat(hotels, hasSize(2));
        assertThat(hotels, hasItems(hotelOne, hotelTwo));
        hotelService.removeHotelsByStateAndCity(KARNATAKA, BANGALORE);
    }

    @Test
    public void givenExistingHotelObjectWithModifiedPropertiesThenUpdateInDatabase() {
        hotelService.createHotel(hotelOne);
        Hotel hotelOneModified = new Hotel(KARNATAKA, BANGALORE, 101, "new bangalore inn", "www.newbangaloreinn.com");
        hotelService.modifyHotel(hotelOneModified);
        Optional<Hotel> optionalHotel = hotelService.getHotelById(KARNATAKA, BANGALORE, 101);
        assertThat(optionalHotel.isEmpty(), is(false));
        Hotel hotel = optionalHotel.get();
        assertThat(hotel, is(hotelOneModified));
        hotelService.removeHotel(hotelOneModified);
    }

    @Test
    public void givenExistingHotelThenRemoveFromDatabase() {
        hotelService.createHotel(hotelThree);
        hotelService.removeHotel(hotelThree);
        Optional<Hotel> optionalHotel = hotelService.getHotelById(DELHI, NEWDELHI, 103);
        assertThat(optionalHotel.isEmpty(), is(true));
    }

    @Test
    public void givenStateAndCityThenRemoveAllHotelsFromDatabase() {
        hotelService.createHotel(hotelOne);
        hotelService.createHotel(hotelTwo);
        hotelService.removeHotelsByStateAndCity(KARNATAKA, BANGALORE);
        List<Hotel> hotels = hotelService.getHotelsByStateAndCity(KARNATAKA, BANGALORE);
        assertThat(hotels, is(empty()));
        hotelService.removeHotelsByStateAndCity(KARNATAKA, BANGALORE);
    }
}