package cz.muni.fi.sbapr.event;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Event {
        
    private final Date time;
    private final long value;    

    public Event(long value) {
        this.value = value;
        this.time = new Date(System.currentTimeMillis());
    }

    public Date getTime() {
        return time;
    }
           
    public long getValue() {
        return value;
    }                
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        sb.append("Event{time = ").append(new SimpleDateFormat("HH:mm:ss.SSS").format(time)).append(", ");
        sb.append("value = ").append(value).append('}');        
        return sb.toString();
    }    
}
