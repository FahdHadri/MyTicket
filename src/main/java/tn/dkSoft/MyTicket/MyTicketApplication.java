package tn.dkSoft.MyTicket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "tn.dkSoft.MyTicket")
public class MyTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTicketApplication.class, args);
    }
}
