package cz.muni.fi.sbapr;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import cz.muni.fi.sbapr.event.Event;
import cz.muni.fi.sbapr.listener.MonitorEventListener;
import cz.muni.fi.sbapr.listener.RepetitionEventListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class EventHandler implements InitializingBean {

    private EPServiceProvider epService;          
    private EPStatement repetitionEventStatement;
    private EPStatement monitorEventStatement;
    
    @Autowired    
    private RepetitionEventListener repetitionEventListener;
    
    @Autowired   
    private MonitorEventListener monitorEventListener;        
              
    public void initService() {
                
        Configuration config = new Configuration();
        config.addEventTypeAutoName("cz.muni.fi.sbapr.event");    
        epService = EPServiceProviderManager.getDefaultProvider(config);                
        createRepetitionEventStatement();
        createMonitorEventStatement();
    }               

    private void createRepetitionEventStatement() {                  
        repetitionEventStatement = epService.getEPAdministrator().createEPL(repetitionEventListener.getStatement());
        repetitionEventStatement.addListener(repetitionEventListener);        
    }

    private void createMonitorEventStatement() {                 
        monitorEventStatement = epService.getEPAdministrator().createEPL(monitorEventListener.getStatement());
        monitorEventStatement.addListener(monitorEventListener);  
    }
    
    public void handle(Event event) {  
        epService.getEPRuntime().sendEvent(event);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initService();
    }
}
