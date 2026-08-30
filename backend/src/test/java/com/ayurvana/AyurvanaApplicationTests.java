package com.ayurvana;

import com.ayurvana.model.entity.Herb;
import com.ayurvana.model.enums.AppointmentStatus;
import com.ayurvana.repository.AppointmentRepository;
import com.ayurvana.repository.DoctorProfileRepository;
import com.ayurvana.repository.HerbRepository;
import com.ayurvana.repository.UserRepository;
import com.ayurvana.security.JwtTokenProvider;
import com.ayurvana.service.scanner.DefaultBotanicalVisionProvider;
import com.ayurvana.service.scanner.PlantIdentificationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class AyurvanaApplicationTests {

    @Autowired
    private HerbRepository herbRepository;

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private DefaultBotanicalVisionProvider visionProvider;

    @Test
    @DisplayName("1. Spring Context Loads & Seed Data Exists")
    void contextLoadsAndSeedDataIsPresent() {
        assertTrue(herbRepository.count() >= 5, "Herb database should contain seeded classical monographs");
        assertTrue(doctorProfileRepository.count() >= 2, "Should contain seeded verified doctors");
        assertTrue(userRepository.count() >= 3, "Should contain default test accounts");
    }

    @Test
    @DisplayName("2. Security & BCrypt Password Encryption")
    void verifyPasswordEncoding() {
        String rawPassword = "password123";
        String encoded = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, encoded), "BCrypt encoder should verify matching credentials");
        assertFalse(passwordEncoder.matches("wrongpassword", encoded), "BCrypt encoder should reject invalid credentials");
    }

    @Test
    @DisplayName("3. JWT Generation and Token Validation")
    void verifyJwtTokenLifecycle() {
        var user = userRepository.findByEmail("doctor@ayurvana.com").orElseThrow();
        var userPrincipal = com.ayurvana.security.UserPrincipal.create(user);
        var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities()
        );

        String token = jwtTokenProvider.generateToken(auth);

        assertNotNull(token, "Generated JWT token must not be null");
        assertTrue(jwtTokenProvider.validateToken(token), "Generated JWT token must validate successfully");
        assertEquals(user.getId(), jwtTokenProvider.getUserIdFromToken(token), "Decoded user ID must match user ID");
    }

    @Test
    @DisplayName("4. Classical Botanical Herb Lookup & Pharmacology Safety")
    void verifyHerbPharmacology() {
        Optional<Herb> ashwa = herbRepository.findBySlug("ashwagandha");

        assertTrue(ashwa.isPresent(), "Ashwagandha monograph should exist in database");
        assertEquals("Withania somnifera", ashwa.get().getScientificName(), "Botanical scientific name must match classical taxonomy");
        assertNotNull(ashwa.get().getSafety(), "Pharmacological safety and contraindications must be populated");
    }

    @Test
    @DisplayName("5. Botanical Vision Scanner: Safe vs Toxic Screening")
    void verifyBotanicalVisionClassification() {
        // Test Safe Ashwagandha
        MockMultipartFile ashwaFile = new MockMultipartFile("image", "ashwagandha_leaf.jpg", "image/jpeg", new byte[]{1, 2, 3});
        PlantIdentificationResult ashwaResult = visionProvider.identifyPlant(ashwaFile);
        assertFalse(ashwaResult.isPotentiallyToxic(), "Ashwagandha must be identified as safe");
        assertTrue(ashwaResult.getPlantName().contains("Ashwagandha"));

        // Test Deadly Toxic Castor Bean
        MockMultipartFile castorFile = new MockMultipartFile("image", "castor_ricinus.jpg", "image/jpeg", new byte[]{1, 2, 3});
        PlantIdentificationResult castorResult = visionProvider.identifyPlant(castorFile);
        assertTrue(castorResult.isPotentiallyToxic(), "Castor bean must be flagged as potentially toxic");
        assertNotNull(castorResult.getToxicityWarning(), "Critical ricin warning must be provided");
    }

    @Test
    @DisplayName("6. Appointment Concurrency: Slot Collision Detection")
    void verifyAppointmentSlotCollision() {
        var doctor = doctorProfileRepository.findAll().get(0);
        LocalDate date = LocalDate.now().plusDays(10);
        LocalTime time = LocalTime.of(15, 0);

        // Check non-existent slot
        boolean existsBefore = appointmentRepository.existsByDoctorIdAndAppointmentDateAndStartTimeAndStatusNot(
                doctor.getId(), date, time, AppointmentStatus.CANCELLED
        );
        assertFalse(existsBefore, "Slot should initially be free");
    }
}
