package com.taxibooking.booking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { "com.taxibooking" })
public class TaxiBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiBookingServiceApplication.class, args);
	}
}

@Component
class DummyDataCLR implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {

	}

}