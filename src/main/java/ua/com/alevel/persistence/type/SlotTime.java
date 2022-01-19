package ua.com.alevel.persistence.type;

public enum SlotTime {

    SLOT_TIME_1 ("9:00"),
    SLOT_TIME_2 ("10:00"),
    SLOT_TIME_3 ("11:00"),
    SLOT_TIME_4 ("12:00");

    private String time;

    SlotTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "SlotTime{" +
                "time='" + time + '\'' +
                '}';
    }
}