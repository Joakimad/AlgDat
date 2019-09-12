package Oving4;

public class Josepheus {

    public static void main(String[] args) {

        int soldiers = 10;
        int intervall = 4;

        CircularLinkedList cll = new CircularLinkedList();

        //Fill the list with data
        for (int i = 1; i <= soldiers; i++) {
            cll.add(i);
        }

        //Display the original
        cll.display();

        System.out.println("Killing soldiers");
        cll.killSoldiers(2);

        //Display the new list
        cll.display();


    }
}
