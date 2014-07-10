package cz.muni.fi.sbapr.util;

import cz.muni.fi.sbapr.EventHandler;
import cz.muni.fi.sbapr.event.Event;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventGenerator {

    @Autowired
    private EventHandler handler;

    public void startGeneratingEvents(final File file, long initialDelay, long period, TimeUnit unit) {

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            executor.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {

                    try {
                        String line = null;                  
                        if ((line = bufferedReader.readLine()) == null) {
                            System.out.println("End of file reached : " + file.getName());
                            closeQuietly(bufferedReader);
                            closeQuietly(inputStreamReader);
                            closeQuietly(fileInputStream);
                            executor.shutdown();
                            return;
                        }

                        long value = Long.parseLong(line.trim());
                        handler.handle(new Event(value)); 
                        
                    } catch (IOException | NumberFormatException ex) {
                        java.util.logging.Logger.getLogger(EventGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }, initialDelay, period, unit);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(EventGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
