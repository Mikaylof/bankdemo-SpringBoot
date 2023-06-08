package az.mushfigm.bankdemoboot;

import az.mushfigm.bankdemoboot.schedule.MyThread;
import az.mushfigm.bankdemoboot.schedule.MyThread2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class BankdemobootApplication {


    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        /*System.out.println(passwordEncoder.encode("ruslan123"));
        System.out.println(passwordEncoder.encode("mushfig123"));
        System.out.println(passwordEncoder.encode("naile123"));*/
        /*MyThread myThread = new MyThread();
        myThread.start();
        MyThread2 myThread2 = new MyThread2();
        myThread2.start();*/
        SpringApplication.run(BankdemobootApplication.class, args);
    }

}
