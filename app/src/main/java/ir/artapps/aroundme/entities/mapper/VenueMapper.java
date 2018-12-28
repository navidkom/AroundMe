package ir.artapps.aroundme.entities.mapper;

import ir.artapps.aroundme.entities.Venue;
import ir.artapps.aroundme.entities.VenueFoursquareEntity;
import ir.artapps.aroundme.entities.VenueRoomEntity;

public class VenueMapper {

    public static  Venue VenueForsquareToVenue(VenueFoursquareEntity venue) {
        Venue entity = new Venue();

        entity.setLatitude( venue.getLocation().getLat() );
        entity.setLongitude( venue.getLocation().getLng() );

        entity.setCanonicalUrl( venue.getCanonicalUrl() );
        entity.setCategories( venue.getCategories() );
        entity.setContact( venue.getContact() );
        entity.setCategories( venue.getCategories() );
        entity.setCreatedAt( venue.getCreatedAt() );
        entity.setDislike( venue.getDislike() );
        entity.setId( venue.getId() );
        entity.setLocation( venue.getLocation() );
        entity.setName( venue.getName() );
        entity.setOk( venue.getOk() );
        entity.setRating( venue.getRating() );
        entity.setRatingColor( venue.getRatingColor() );
        entity.setRatingSignals( venue.getRatingSignals() );
        entity.setTimeZone( venue.getTimeZone() );
        entity.setVerified( venue.getVerified() );
        entity.setUrl( venue.getUrl() );
        entity.setShortUrl( venue.getShortUrl() );
        entity.setBestPhoto(venue.getBestPhoto());

        if(venue.getCategories() != null && venue.getCategories().size() > 0 ) {
            entity.setIcon(venue.getCategories().get(0).icon.getUrl());
        }

        return entity;
    }

    public static  Venue VenueRoomToVenue(VenueRoomEntity venue) {
        Venue entity = new Venue();

        entity.setLatitude( venue.getLocation().getLat() );
        entity.setLongitude( venue.getLocation().getLng() );
        entity.setCanonicalUrl( venue.getCanonicalUrl() );
        entity.setCategories( venue.getCategories() );
        entity.setContact( venue.getContact() );
        entity.setCategories( venue.getCategories() );
        entity.setCreatedAt( venue.getCreatedAt() );
        entity.setDislike( venue.getDislike() );
        entity.setId( venue.getId() );
        entity.setLocation( venue.getLocation() );
        entity.setName( venue.getName() );
        entity.setOk( venue.getOk() );
        entity.setRating( venue.getRating() );
        entity.setRatingColor( venue.getRatingColor() );
        entity.setRatingSignals( venue.getRatingSignals() );
        entity.setTimeZone( venue.getTimeZone() );
        entity.setVerified( venue.getVerified() );
        entity.setUrl( venue.getUrl() );
        entity.setShortUrl( venue.getShortUrl() );
        entity.setIcon(venue.getIcon());

        return entity;
    }

    public static  VenueRoomEntity VenueToVenueRoom(Venue venue) {
        VenueRoomEntity entity = new VenueRoomEntity();

        entity.setLatitude( venue.getLocation().getLat() );
        entity.setLongitude( venue.getLocation().getLng() );
        entity.setCanonicalUrl( venue.getCanonicalUrl() );
        entity.setCategories( venue.getCategories() );
        entity.setContact( venue.getContact() );
        entity.setCategories( venue.getCategories() );
        entity.setCreatedAt( venue.getCreatedAt() );
        entity.setDislike( venue.getDislike() );
        entity.setId( venue.getId() );
        entity.setLocation( venue.getLocation() );
        entity.setName( venue.getName() );
        entity.setOk( venue.getOk() );
        entity.setRating( venue.getRating() );
        entity.setRatingColor( venue.getRatingColor() );
        entity.setRatingSignals( venue.getRatingSignals() );
        entity.setTimeZone( venue.getTimeZone() );
        entity.setVerified( venue.getVerified() );
        entity.setUrl( venue.getUrl() );
        entity.setShortUrl( venue.getShortUrl() );
        entity.setIcon(venue.getIcon());

        return entity;
    }
}
