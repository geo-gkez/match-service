package com.github.geo_gkez.match_service.integration_test;

import com.github.geo_gkez.match_service.MatchServiceApplication;
import org.springframework.boot.SpringApplication;

public class TestMatchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MatchServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
