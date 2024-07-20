package entity;

public class Reservation {
    private String restaurantCode;
    private String restaurantName;
    private String reservedTime;
    private int numberOfPeople;

    public Reservation(String [] rows) {
        restaurantCode = rows[0];
        restaurantName = rows[1];
        reservedTime = rows[2];
        numberOfPeople = Integer.parseInt(rows[3]);
    }

    public String getCode() { return restaurantCode; }
    public String getName() { return restaurantName; }
    public String getReservedTime() { return reservedTime; }
    public int getNumbers() { return numberOfPeople; }
}
