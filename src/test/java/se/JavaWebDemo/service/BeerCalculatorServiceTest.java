package se.JavaWebDemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
public class BeerCalculatorServiceTest {

    private BeerCalculatorService beerCalculatorService;

    @BeforeEach
    void setUp() {
        beerCalculatorService = new BeerCalculatorService();
    }

    @Test
    @DisplayName("Beräkna standarddrinkar för en typisk starköl")
    void calculateStandardDrinksForTypicalBeer() {
        double drinks = beerCalculatorService.calculateStandardDrinks(500, 5.0);
        assertEquals(1.64, drinks, 0.1);
    }

    @Test
    @DisplayName("Kasta exception för ogiltig volym")
    void throwExceptionForInvalidVolume() {
        assertThrows(IllegalArgumentException.class, () ->
                beerCalculatorService.calculateStandardDrinks(-100, 5.0));
    }

    @Test
    @DisplayName("Kasta exception för ogiltig alkoholprocent")
    void throwExceptionForInvalidAlcoholPercentage() {
        assertThrows(IllegalArgumentException.class, () ->
                beerCalculatorService.calculateStandardDrinks(500, 101.0));
    }

    @Test
    @DisplayName("Beräkna BAC för en man")
    void calculateBACForMale() {
        double bac = beerCalculatorService.calculateBAC(2.0, 80.0, 1.0, true);
        assertTrue(bac > 0);
        assertTrue(bac < 0.1);
    }

    @Test
    @DisplayName("Beräkna BAC för en kvinna")
    void calculateBACForFemale() {
        double bac = beerCalculatorService.calculateBAC(2.0, 60.0, 1.0, false);
        assertTrue(bac > 0);
        assertTrue(bac < 0.15);
    }

    @Test
    @DisplayName("BAC ska vara 0 efter lång tid")
    void bacShouldBeZeroAfterLongTime() {
        double bac = beerCalculatorService.calculateBAC(2.0, 80.0, 10.0, true);
        assertEquals(0.0, bac, 0.001);
    }
}
