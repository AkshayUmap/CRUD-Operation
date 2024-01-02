package com.example.machinetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.machinetest")
public class MachineTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MachineTestApplication.class, args);
	}

}
