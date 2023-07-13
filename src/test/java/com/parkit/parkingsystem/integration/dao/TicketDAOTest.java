package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TicketDAOTest {

    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private ParkingSpotDAO parkingSpotDAO;

    @BeforeAll
    public static void setUp() throws Exception {
        ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    public void setUpPerTest() throws Exception {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    void saveTicket() {
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);

        Ticket ticket = new Ticket();
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setInTime(inTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(0.0);
        ticketDAO.saveTicket(ticket);
        assertEquals(1, ticketDAO.getNbTicket("ABCDEF"));
    }

    @Test
    void getTicket() {
        saveTicket();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        assertEquals(1, ticket.getId());
    }

    @Test
    void updateTicket() {
        saveTicket();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ticket.setPrice(3.5);
        ticket.setOutTime(new Date());
        ticketDAO.updateTicket(ticket);
        Ticket ticket2 = ticketDAO.getTicket("ABCDEF");
        assertEquals(3.5, ticket2.getPrice());
    }

    @Test
    void getNbTicket() {
        saveTicket();
        assertEquals(1, ticketDAO.getNbTicket("ABCDEF"));
    }
}