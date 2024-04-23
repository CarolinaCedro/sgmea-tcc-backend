package tcc.sgmeabackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SgmeaBackendApplication {

    protected static final Logger logger = LogManager.getLogger();


    public static void main(String[] args) {
        logger.info("Hello World!");

        SpringApplication.run(SgmeaBackendApplication.class, args);
    }


}
