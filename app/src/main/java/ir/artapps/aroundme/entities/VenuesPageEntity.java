package ir.artapps.aroundme.entities;

import java.util.List;

/**
 * Created by navid on 28,December,2018
 */
public class VenuesPageEntity {

    List<Venue> venues;
    int page;

    public boolean isEndOfList() {
        return endOfList;
    }

    public void setEndOfList(boolean endOfList) {
        this.endOfList = endOfList;
    }

    boolean endOfList = false;

    public VenuesPageEntity(List<Venue> venues, int page) {
        this.venues = venues;
        this.page = page;
    }

    public VenuesPageEntity(List<Venue> venues, int page, boolean endOfList) {
        this.venues = venues;
        this.page = page;
        this.endOfList = endOfList;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
