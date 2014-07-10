package cz.muni.fi.sbapr.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.springframework.stereotype.Component;

@Component
public class MonitorEventListener implements UpdateListener {
    
    public String getStatement() {
        return "select * from Event";         
    }      
    
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {        
        EventBean event = newEvents[0]; 
        System.out.println(event.getUnderlying());                
    }
}
