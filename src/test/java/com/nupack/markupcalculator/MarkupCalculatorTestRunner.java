package test.java.com.nupack.markupcalculator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MarkupCalculatorTestRunner {
    public static void main (String [] args) {
        Result results = JUnitCore.runClasses(MarkupCalculatorTest.class);
        for (Failure failure : results.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
