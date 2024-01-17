package org.ticketsystem.ticketdecorator;

import org.ticketsystem.Ticket;

// TODO dokończ implementację dekoratora dodającego posiłek do biletu
public class MealTicketDecorator extends TicketDecorator {

    public MealTicketDecorator(Ticket decoratedTicket) {
        super(decoratedTicket);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Meal";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 20.0;
    }
}
