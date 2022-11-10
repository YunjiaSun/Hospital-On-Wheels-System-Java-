package model.campaign;


import model.location.Site;

import java.util.ArrayList;

public class EventCatalog {
    Campaign campaign;
    ArrayList<Event> events;

    EventCatalog(Campaign c) {
        campaign = c;
        events = new ArrayList<>();
    }

    public Event newEvent(Campaign campaign, Site site){
        Event e = new Event(campaign, site);
        events.add(e);
        return e;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
