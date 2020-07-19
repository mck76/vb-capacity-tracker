package com.vb.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;


@SpringBootTest
class DemoApplicationTests {

    private final CapacityRecordService capacityRecordService;

    @Autowired
    DemoApplicationTests(CapacityRecordService capacityRecordService) {
        this.capacityRecordService = capacityRecordService;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void verifyRetrievingCorrectValuesFromStudios() throws Exception {
        Path path = Paths.get("", "src/test/resources/response_ka_postgalerie.html");
        Path path2 = Paths.get("", "src/test/resources/response_ka_south.html");
        String studio1 = Files.readString(path);
        String studio2 = Files.readString(path2);
        assertThat(CapacityRecordService.processResponseAndFilterForCapacity(studio1), comparesEqualTo(125));
        assertThat(CapacityRecordService.processResponseAndFilterForCapacity(studio2), comparesEqualTo(94));
    }

}
