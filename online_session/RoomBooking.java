import java.util.*;

enum Facility {
    PROJECTOR, VIDEO_CALL, WHITEBOARD, SPEAKER_PHONE, AC
}

class ConferenceRoom {
    private String id;
    private String name;
    private int capacity;
    private EnumSet<Facility> amenities;

    public ConferenceRoom(String id, String name, int capacity, EnumSet<Facility> amenities) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.amenities = amenities;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EnumSet<Facility> getAmenities() {
        return amenities;
    }
}

class ReservationSystem {
    private Map<String, ConferenceRoom> roomMap;

    public ReservationSystem() {
        this.roomMap = new HashMap<>();
    }

    public void registerRoom(ConferenceRoom room) {
        roomMap.put(room.getId(), room);
        System.out.println("Room Registered: " + room.getName() + " (ID: " + room.getId() + ")");
    }

    public String reserveRoom(String id, EnumSet<Facility> neededFacilities) {
        ConferenceRoom room = roomMap.get(id);
        if (room != null && room.getAmenities().containsAll(neededFacilities)) {
            return "Reservation confirmed for room " + id;
        }
        return "Room " + id + " does not match the requested facilities.";
    }

    public List<String> findAvailableRooms(EnumSet<Facility> neededFacilities) {
        List<String> matchingRooms = new ArrayList<>();
        for (ConferenceRoom room : roomMap.values()) {
            if (room.getAmenities().containsAll(neededFacilities)) {
                matchingRooms.add(room.getName());
            }
        }
        System.out.println("Rooms available with " + neededFacilities + ": " + matchingRooms);
        return matchingRooms;
    }
}

public class RoomBooking {
    public static void main(String[] args) {
        ReservationSystem system = new ReservationSystem();

        system.registerRoom(new ConferenceRoom("101", "Executive Boardroom", 20, EnumSet.of(Facility.PROJECTOR, Facility.SPEAKER_PHONE, Facility.AC)));
        system.registerRoom(new ConferenceRoom("102", "Innovation Hub", 10, EnumSet.of(Facility.WHITEBOARD, Facility.AC)));

        System.out.println(system.reserveRoom("101", EnumSet.of(Facility.PROJECTOR, Facility.SPEAKER_PHONE)));
        system.findAvailableRooms(EnumSet.of(Facility.AC));
    }
}
