package RandomMatte;

public class Main {

    public static void main(String[] args) {

        Probability p = new Probability();

        double[] arr1 = {1,2,3,4,5,6};
        double[] arr2 = {0.20,0.17,0.17,0.17,0.17,0.12};

        System.out.println("Forventningsverdi: " +p.findExpectedValue(arr1,arr2));
        System.out.println("Varians: " + p.findVariance(arr1,arr2));
        System.out.println("Standardavvik: " + p.findStandardDeviation(arr1,arr2));
    }
}
