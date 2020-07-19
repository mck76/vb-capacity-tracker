package com.vb.app;

import com.vb.model.CapacityRecord;
import com.vb.model.VeniceBeachStudio;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Component
public class CapacityRecordService {

    private static final Log LOG = LogFactory.getLog(CapacityRecordService.class);
    private static final String BASE_URI = "https://www.venicebeach-fitness.de/clubs/";
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoOperations entityManager;

    @Autowired
    public CapacityRecordService(SequenceGeneratorService sequenceGeneratorService, MongoOperations entityManager) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.entityManager = entityManager;
    }

    public static int processResponseAndFilterForCapacity(String response) {
        String[] substrings = response.split("noch <strong>", 2);
        String[] targetString = substrings[1].split(" ", 2);
        return Integer.parseInt(targetString[0]);
    }

    @Scheduled(fixedRate = 300000)
    public void reportCurrentCapacity() {
        HttpClient client = HttpClient.newHttpClient();
        Arrays.stream(VeniceBeachStudio.values()).forEach(veniceBeachStudio -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URI.concat(veniceBeachStudio.getUri())))
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(res -> {
                        try {
                            int retrievedCapacity = processResponseAndFilterForCapacity(res);
                            persistNewRecord(veniceBeachStudio, retrievedCapacity);
                        } catch (Exception e) {
                            LOG.error("Value for '" + veniceBeachStudio.getNamePretty() + "' could not be retrieved.", e);
                        }
                    })
                    .join();
        });
    }

    private void persistNewRecord(VeniceBeachStudio veniceBeachStudio, int capacity) throws Exception {
        final long id = sequenceGeneratorService.generateSequence(CapacityRecord.SEQUENCE_NAME);
        entityManager.save(new CapacityRecord(id, Date.from(Instant.now()), veniceBeachStudio, capacity));
    }
}
