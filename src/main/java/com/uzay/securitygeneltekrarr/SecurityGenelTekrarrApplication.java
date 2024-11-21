package com.uzay.securitygeneltekrarr;

import com.uzay.securitygeneltekrarr.audit.AuditAwareConfig;
import com.uzay.securitygeneltekrarr.entity.Car;
import com.uzay.securitygeneltekrarr.property.ConfigProperty;
import com.uzay.securitygeneltekrarr.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Arrays;
@EnableConfigurationProperties(ConfigProperty.class)
@EnableJpaAuditing(auditorAwareRef = "AuditAwareConfig")
@SpringBootApplication(scanBasePackages = "com.uzay.securitygeneltekrarr")
public class SecurityGenelTekrarrApplication implements CommandLineRunner {

    @Value("${degiskenler.isim}")
    private String isim;




    private static final Logger log = LoggerFactory.getLogger(SecurityGenelTekrarrApplication.class);
    private final ApplicationContext applicationContext;
    private final Environment environment;
    private final Car car;
    private final ConfigProperty configProperty;

    public SecurityGenelTekrarrApplication(ApplicationContext applicationContext, Environment environment, Car car, ConfigProperty configProperty) {
        this.applicationContext = applicationContext;
        this.environment = environment;
        this.car = car;
        this.configProperty = configProperty;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("program başarıyla başlatıldı 2");

    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityGenelTekrarrApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CarRepository carRepository){
        return  args -> {

            System.out.println(configProperty.getIsim());
            System.out.println("----------");
            configProperty.getSehirler().stream().forEach(System.out::println);
            System.out.println("----------");
            configProperty.getUlke().stream().forEach(System.out::println);
            System.out.println("----------");
            configProperty.getOkul().keySet().stream().forEach(System.out::println);
            System.out.println("----------");
            configProperty.getOkul().values().stream().forEach(System.out::println);
            System.out.println("----------");

            System.out.println("***************");
            System.out.println("***************");
            System.out.println("***************");

            System.out.println(isim);

            System.out.println("/////////////////////");
            System.out.println("/////////////////////");

            System.out.println(environment.getProperty("degiskenler.isim"));




        };
    }


}
