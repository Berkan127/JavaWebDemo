package se.JavaWebDemo.service;

import org.springframework.stereotype.Service;

@Service
public class BeerCalculatorService {
    
    private static final double STANDARD_DRINK_ALCOHOL_GRAMS = 12.0;
    private static final double ALCOHOL_DENSITY = 0.789; // g/ml
    
    /**
     * Beräknar antal standarddrinkar i en öl
     * @param volumeInMl volym i milliliter
     * @param alcoholPercentage alkoholhalt i procent
     * @return antal standarddrinkar
     */
    public double calculateStandardDrinks(double volumeInMl, double alcoholPercentage) {
        if (volumeInMl <= 0 || alcoholPercentage <= 0 || alcoholPercentage > 100) {
            throw new IllegalArgumentException("Ogiltiga värden för volym eller alkoholhalt");
        }
        
        double alcoholVolumeInMl = volumeInMl * (alcoholPercentage / 100.0);
        double alcoholGrams = alcoholVolumeInMl * ALCOHOL_DENSITY;
        return alcoholGrams / STANDARD_DRINK_ALCOHOL_GRAMS;
    }
    
    /**
     * Beräknar ungefärlig BAC (Blood Alcohol Content)
     * @param standardDrinks antal standarddrinkar
     * @param weightInKg vikt i kg
     * @param hoursFromDrinking timmar sedan drickande
     * @param isMale true om man, false om kvinna
     * @return uppskattad BAC
     */
    public double calculateBAC(double standardDrinks, double weightInKg, double hoursFromDrinking, boolean isMale) {
        if (standardDrinks < 0 || weightInKg <= 0 || hoursFromDrinking < 0) {
            throw new IllegalArgumentException("Ogiltiga värden för beräkning");
        }
        
        double alcoholGrams = standardDrinks * STANDARD_DRINK_ALCOHOL_GRAMS;
        double widmarkFactor = isMale ? 0.68 : 0.55;
        double bac = (alcoholGrams / (weightInKg * 1000 * widmarkFactor)) * 100;
        
        // Subtrahera metaboliserad alkohol (ca 0.015% per timme)
        bac -= 0.015 * hoursFromDrinking;
        
        return Math.max(0, bac);
    }
} 