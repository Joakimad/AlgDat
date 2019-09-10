package RandomRealfag;

public class Main {

    public static void main(String[] args) {

        Probability p = new Probability();

        double[] arr1 = {0,0.3,0.5,0.6,0.7,0.8,1.0,1.2,1.6};
        double[] arr2 = {(double)1/28,(double)2/28,(double)5/28,(double)8/28,(double)5/28,(double)3/28,(double)2/28,(double)3/56,(double)1/56};

        System.out.println("Forventningsverdi: " +p.findExpectedValue(arr1,arr2));
        System.out.println("Varians: " + p.findVariance(arr1,arr2));
        System.out.println("Standardavvik: " + p.findStandardDeviation(arr1,arr2));
        System.out.println("Distribusjonfunksjonen: " + p.findDistributionFunction(arr1,arr2,0.7,1.2));
    }
}
