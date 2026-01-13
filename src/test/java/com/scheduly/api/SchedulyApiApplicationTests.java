package com.scheduly.api;

import com.scheduly.api.config.SecurityTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(SecurityTestConfig.class)
public class SchedulyApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
