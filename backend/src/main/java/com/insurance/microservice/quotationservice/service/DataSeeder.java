package com.insurance.microservice.quotationservice.service;

import com.insurance.microservice.quotationservice.dto.PolicyQuoteRequestDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PolicyService policyService;
    private final Random random = new Random();

    public DataSeeder(PolicyService policyService) {
        this.policyService = policyService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Seeding database with 1000 unique dummy policy data...");
        // *** CHANGE: Generate 1000 policies instead of 50. ***
        generateDummyPolicies(1000);
        System.out.println("Data seeding complete.");
    }

    private void generateDummyPolicies(int count) {
        // 1. Generate a list of unique User IDs for the specified count.
        List<String> userIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            userIds.add(UUID.randomUUID().toString());
        }

        // 2. Data lists for randomization.
        List<String> occupations = Arrays.asList(
            "Software Engineer", "Doctor", "Graphic Designer", "Teacher", "Accountant",
            "Project Manager", "Chef", "Electrician", "Marketing Specialist", "Data Scientist"
        );
        List<String> genders = Arrays.asList("Female", "Male");
        List<String> insuranceTypes = Arrays.asList("HEALTH", "LIFE", "VEHICLE");
        List<String> vehicleTypes = Arrays.asList("CAR", "BIKE");

        // 3. Generate the data, ensuring each policy is for a unique user.
        for (int i = 0; i < count; i++) {
            PolicyQuoteRequestDto request = new PolicyQuoteRequestDto();

            // Assign the unique user ID and a programmatically generated unique name.
            request.setUserId(userIds.get(i));
            request.setCustomerName("User " + (i + 1));

            // Randomize the rest of the customer and policy details.
            request.setCustomerAge(20 + random.nextInt(50)); // Age between 20 and 69
            request.setCustomerGender(genders.get(random.nextInt(genders.size())));
            request.setCustomerIncome((long) (400000 + random.nextInt(1600000))); // Income 4L to 20L
            request.setCustomerOccupation(occupations.get(random.nextInt(occupations.size())));
            
            String insuranceType = insuranceTypes.get(random.nextInt(insuranceTypes.size()));
            request.setInsuranceType(insuranceType);
            
            if ("VEHICLE".equals(insuranceType)) {
                request.setVehicleType(vehicleTypes.get(random.nextInt(vehicleTypes.size())));
            } else {
                request.setVehicleType(null);
            }

            // Create the quote for this unique user.
            var quoteResponse = policyService.calculateQuote(request);

            // Simulate that about 70% of users accept the quote.
            if (random.nextInt(10) < 7) {
                policyService.acceptOrRejectQuote(quoteResponse.getPolicyId(), true);
            }
        }
    }
}

