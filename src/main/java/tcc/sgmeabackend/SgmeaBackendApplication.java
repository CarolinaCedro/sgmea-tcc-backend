package tcc.sgmeabackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class SgmeaBackendApplication {

    protected static final Logger logger = LogManager.getLogger();

    @Profile({"dev","prod"})
    public static void main(String[] args) {
        logger.info("Hello World!");

        SpringApplication.run(SgmeaBackendApplication.class, args);
    }


}
