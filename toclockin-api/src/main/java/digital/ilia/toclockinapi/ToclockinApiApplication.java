package digital.ilia.toclockinapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ToclockinApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToclockinApiApplication.class, args);
    }

}
