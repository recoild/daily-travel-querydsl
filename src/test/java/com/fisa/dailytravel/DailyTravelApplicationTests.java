package com.fisa.dailytravel;

import com.fisa.dailytravel.config.CustomSpringBootTest;
import com.fisa.dailytravel.config.TestcontainersConfiguration;
import com.fisa.dailytravel.global.config.P6spyLogMessageFormatConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@CustomSpringBootTest
class DailyTravelApplicationTests {

    @Test
    void contextLoads() {
    }

}
