package main.java.com.nupack.markupcalculator;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MarkupCalculator {

    private static BigDecimal determineMarkup(BigInteger numPeople) {
        return new BigDecimal(numPeople).multiply(new BigDecimal(0.012));
    }
    
    private static BigDecimal determineMarkup(BigInteger numPeople, ProductType type)
        throws IllegalArgumentException {
        //tests if numPeople is less than 0
        if (numPeople.compareTo(BigInteger.valueOf(0)) == -1) {
            throw new IllegalArgumentException("Number of people cannot be negative");
        }

        return determineMarkup(numPeople).add(new BigDecimal(determineMarkup(type))) ;
    }

    private static double determineMarkup(ProductType type) {
        switch (type) {
        case PHARMA:
            return 0.075;
        case FOOD:
            return 0.13;
        case ELECTRONICS:
            return 0.02;
        default:
            return 0;
        }
    }

	/**
	 * Determine the price assuming there is no markup for the type of object being purchased, and 0 people
	 * are required to work on the job.
	 *
	 * @param basePrice The price of the item being purchased
	 * @return The price of the item after markup has been applied
	 *
	 * @throws IllegalArgumentException
	 */
    public static BigDecimal determinePrice(BigDecimal basePrice) throws IllegalArgumentException {
        //tests if basePrice is less than 0
        if (basePrice.compareTo(new BigDecimal(0)) == -1) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        return basePrice.multiply(new BigDecimal(1.05));
    }

	/**
	 * Determine the price assuming there is no markup for type of object being purchased.
	 *
	 * @param basePrice The price of the item being purchased
	 * @param numPeople The number of people who need to work the job
	 * @return The price of the item after markup has been applied
	 *
	 * @throws IllegalArgumentException
	 */
    public static BigDecimal determinePrice(BigDecimal basePrice, BigInteger numPeople)
        throws IllegalArgumentException {
        
        if (numPeople.compareTo(BigInteger.valueOf(0)) == -1) {
            throw new IllegalArgumentException("Number of people cannot be negative");
        }
        
        return determinePrice(basePrice).multiply(determineMarkup(numPeople).add(new BigDecimal(1)));
    }

	/**
	 * Determine the price, with markup, of the items being purchased
	 *
	 * @param basePrice The price of the item being purchased
	 * @param numPeople The number of people who need to work on the job
	 * @param type The type of materials being purchased
	 * @return The price of the item after markup has been applied
	 *
	 * @throws IllegalArgumentException
	 */
    public static BigDecimal determinePrice(BigDecimal basePrice, BigInteger numPeople, ProductType type)
        throws IllegalArgumentException {
        
        return determinePrice(basePrice).multiply(determineMarkup(numPeople, type).add(new BigDecimal (1)));
    }
}
