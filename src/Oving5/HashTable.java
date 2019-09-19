package Oving5;

public class HashTable {

    private Node[] list;

    public HashTable(int size) {
        list = new Node[size];
    }

    public void addToTable(String data) {
        int key = convert(data);
        int index = divHash(key, list.length);
        Node entry = new Node(data);

        if (list[index] != null) {
            Node next = list[index].getNext();
            //linked list from node on index
            System.out.println(list[index].getData());
            boolean isEmpty = false;
            do {
                next = next.getNext();
                if (next == null) {
                    isEmpty = true;
                    list[index].setNext(entry);
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

    public Node[] getHashedList() {
        return list;
    }

    public int countUsed() {
        int sum = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                sum++;
                System.out.println(list[i].getData());
            }
        }
        return sum;
    }

    public int countAll() {
        int sum = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                boolean isEmpty = false;
                do {
                    sum++;
                    System.out.println(list[i].getData());
                    if (list[i].getNext() == null) {
                        isEmpty = true;
                    }
                } while (!isEmpty);
            }
        }
        return sum;
    }
}
