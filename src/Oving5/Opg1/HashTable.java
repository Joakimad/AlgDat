package Oving5.Opg1;

public class HashTable {

    private Node[] list;
    private int collisions = 0;

    public HashTable(int size) {
        list = new Node[size];
    }

    public void addToTable(String data) {
        int key = convert(data);
        int index = divHash(key, list.length);
        Node entry = new Node(data);

        if (list[index] != null) {
            Node next = list[index];
            //linked list from node on inde
            boolean isEmpty = false;
            do {
                collisions++;
                System.out.println("Kollisjon: " + next.getData());
                if (next.getNext() == null) {
                    isEmpty = true;
                    next.setNext(entry);
                } else {
                    next = next.getNext();
                }
            } while (!isEmpty);

        } else {
            list[index] = entry;
        }
    }

    private int divHash(int k, int m) {
        return k % m;
    }


    private int convert(String s) {
        int key = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = s.charAt(i);
            // Tar ascii koden og ganger med plass i stringen +1
            key += ascii * (i + 1);
        }

        return key;
    }

    public int countUsed() {
        int sum = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                sum++;

            }
        }
        return sum;
    }

    public int countAll() {
        int sum = 0;

        for (int i = 0; i < list.length; i++) {
            Node next = list[i];
            if (list[i] != null) {
                boolean isEmpty = false;
                do {
                    next = next.getNext();
                    sum++;
                    if (next == null) {
                        isEmpty = true;
                    }
                } while (!isEmpty);
            }
        }
        return sum;
    }

    public int getCollisions() {
        return collisions;
    }
}
