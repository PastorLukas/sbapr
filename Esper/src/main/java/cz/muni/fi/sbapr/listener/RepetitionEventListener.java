package cz.muni.fi.sbapr.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.springframework.stereotype.Component;

@Component
public class RepetitionEventListener implements UpdateListener {
    
    public static String getStatement() {              
        return "select fst, snd from pattern[every fst=Event -> snd=Event(value=fst.value) where timer:within(6 sec)]";
    }     
    
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {                       
        EventBean event = newEvents[0];                                                        
        System.out.println("Repetition Event : ");
        System.out.println("\t" + event.get("fst"));
        System.out.println("\t" + event.get("snd"));                                                     
    }      
}

