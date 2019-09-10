package RandomRealfag;

import java.util.Arrays;

public class Probability {

    public double findExpectedValue(double[] values, double[] probability) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i] * probability[i];
        }
        return sum;
    }

    public double findVariance(double[] values, double[] probability) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += (Math.pow(values[i],2)) * probability[i];
        }
        sum -= Math.pow(findExpectedValue(values,probability),2);
        return sum;
    }

    public double findStandardDeviation(double[] values, double[] probability) {
        return Math.sqrt(findVariance(values,probability));
    }

    public double findDistributionFunction(double[] values, double[] probability, double low, double high) {

        double sum = 0;

        for (int i = Arrays.binarySearch(values,low); i <= Arrays.binarySearch(values,high); i++) {
            sum += probability[i];
        }
        return sum;
    }

}
