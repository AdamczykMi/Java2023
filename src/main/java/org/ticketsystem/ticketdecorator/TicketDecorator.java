package org.ticketsystem.ticketdecorator;

import org.ticketsystem.Ticket;

// TODO dokończ implementację dekoratora
public abstract class TicketDecorator implements Ticket {

    protected final Ticket decoratedTicket;

    public TicketDecorator(Ticket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    @Override
    public String getDescription() {
        return decoratedTicket.getDescription();
    }

    @Override
    public double getPrice() {
        return decoratedTicket.getPrice();
    }
}
