package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.RoundUtil;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        calculateFare(ticket, true);
    }

    public void calculateFare(Ticket ticket, boolean discount) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();

        long durationEnMilliseconde = outHour - inHour;

        double duration = (double) durationEnMilliseconde / (1000 * 60 * 60);

        if (duration < Fare.FREE_IN_HOUR) {
            duration = 0;
        }

        double coefficient = 1;

        if (discount) {
            coefficient = coefficient - Fare.DISCUNT_PERCENTAGE;
        }

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                double price = RoundUtil.threeDigitRender(duration * Fare.CAR_RATE_PER_HOUR * coefficient);
                ticket.setPrice(price);
                break;
            }
            case BIKE: {
                double price = RoundUtil.threeDigitRender(duration * Fare.BIKE_RATE_PER_HOUR * coefficient);
                ticket.setPrice(price);
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}