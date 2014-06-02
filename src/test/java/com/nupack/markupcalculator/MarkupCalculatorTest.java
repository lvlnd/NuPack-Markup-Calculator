package test.java.com.nupack.markupcalculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import main.java.com.nupack.markupcalculator.MarkupCalculator;
import main.java.com.nupack.markupcalculator.ProductType;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.hamcrest.Matchers;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class MarkupCalculatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testFood() {
        BigDecimal expectedValue = new BigDecimal(1591.58, new MathContext(6));
        BigDecimal actualValue = MarkupCalculator.determinePrice(new BigDecimal(1299.99),
                                                                 BigInteger.valueOf(3),
                                                                 ProductType.FOOD)
            .round(new MathContext(6));
        
        Assert.assertEquals("failure - food test produced the wrong result",
                            expectedValue,
                            actualValue);
    }
    
    @Test
    public void testDrugs() {
        BigDecimal expectedValue = new BigDecimal(6199.81, new MathContext(6));
        BigDecimal actualValue = MarkupCalculator.determinePrice(new BigDecimal(5432.00),
                                                                 BigInteger.valueOf(1),
                                                                 ProductType.PHARMA)
            .round(new MathContext(6));
        
        Assert.assertEquals("failure - drugs test produced the wrong result",
                            expectedValue,
                            actualValue);
    }
    
    @Test
    public void testBooks() {
        BigDecimal expectedValue = new BigDecimal(13707.63, new MathContext(6));
        BigDecimal actualValue = MarkupCalculator.determinePrice(new BigDecimal(12456.95),
                                                                 BigInteger.valueOf(4),
                                                                 ProductType.OTHER)
            .round(new MathContext(6));
        
        Assert.assertEquals("failure - books test produced the wrong result",
                            expectedValue,
                            actualValue);
    }
    
    @Test
    public void testElectronics() {
        BigDecimal expectedValue = new BigDecimal(1134.001, new MathContext(6));
        BigDecimal actualValue = MarkupCalculator.determinePrice(new BigDecimal(1000.00),
                                                                 BigInteger.valueOf(5),
                                                                 ProductType.ELECTRONICS)
            .round(new MathContext(6));
        
        Assert.assertEquals("failure - electronics test produced the wrong result",
                            expectedValue,
                            actualValue);
    }

    @Test
    public void testNegativePersons() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(Matchers.containsString("people"));
        MarkupCalculator.determinePrice(new BigDecimal(0.0), BigInteger.valueOf(-1), ProductType.OTHER);    
    }

    @Test
    public void testNegativePrice() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(Matchers.containsString("price"));
        MarkupCalculator.determinePrice(new BigDecimal(-1.0), BigInteger.valueOf(2), ProductType.OTHER);
    }

    @Test
    public void testLargeNumberOfPersons() {
        MathContext digits = new MathContext(10);
        BigInteger numberOfPersons = BigInteger.valueOf(9223372036854775807L).multiply(BigInteger.valueOf(2));
        BigDecimal expectedResult = new BigDecimal(2.7891709468).multiply(new BigDecimal(10.0).pow(20))
            .round(digits);
        BigDecimal actualResult = MarkupCalculator.determinePrice(new BigDecimal(1200.01),
                                                                  numberOfPersons, ProductType.FOOD)
            .round(digits);

        //We will use compareTo, and then check its value as opposed to assertEquals, since
        //using .equals is not the right way to check equality of tqo BigDecimals, as per the docs
        int result = actualResult.compareTo(expectedResult);

        Assert.assertTrue("failure - large numbers test failed: " + result,
                          result == 0);
    }

    @Test
    public void testLargePrice() {
        MathContext digits = new MathContext(10);
        BigDecimal price = new BigDecimal(1200.01).multiply(new BigDecimal (10.0).pow(100));
        BigDecimal expectedResult = new BigDecimal(1.454052117).multiply(new BigDecimal(10.0).pow(103))
            .round(digits);
        BigDecimal actualResult = MarkupCalculator.determinePrice(price, BigInteger.valueOf(2), ProductType.FOOD)
            .round(digits);

        int result = actualResult.compareTo(expectedResult);

        Assert.assertTrue("failure - large price test failed:" + result,
                          result == 0);
    }

    @Test
    public void testNoPersons() {
        BigDecimal expectedValue = new BigDecimal(1542.44, new MathContext(6));
        BigDecimal actualValue =  MarkupCalculator.determinePrice(new BigDecimal(1299.99),
                                                                  BigInteger.valueOf(0),
                                                                  ProductType.FOOD)
            .round(new MathContext(6));
        
        Assert.assertEquals("failure - no persons test produced the wrong result",
                            expectedValue,
                            actualValue);
    }

    @Test
    public void testZeroPrice() {
        //this test will use double values for the final result, since BigDecimal zeroes are hard to round
        double expectedValue = new BigDecimal(0.0, new MathContext(5)).doubleValue();
        double actualValue = MarkupCalculator.determinePrice(new BigDecimal(0.0),
                                                             BigInteger.valueOf(5),
                                                             ProductType.FOOD)
            .round(new MathContext(5)).doubleValue();
        
        Assert.assertEquals("failure - zero price test produced the wrong result",
                            expectedValue, 
                            actualValue, 0.001);
    }
}
