package az.mushfigm.bankdemoboot.schedule;

import az.mushfigm.bankdemoboot.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MySchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySchedule.class);
    /*@Scheduled(fixedDelay = 3000)
    public void fixedDelay(){
        System.out.println(("Hello Schedule: "));
    }*/
}
