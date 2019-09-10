package RandomMatte;

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

    public double findDistributionFunction(double[] values, double[] probability, double x) {
        double[] function = new double[values.length];
        for (int i = 1; i < values.length; i++) {
            function[i] = (values[i] * probability[i]) + (values[i-1] * probability[i-1]);
        }
        return 0;
    }

}
