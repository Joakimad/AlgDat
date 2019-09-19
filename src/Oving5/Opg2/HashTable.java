package Oving5.Opg2;

public class HashTable {

    private Integer[] list;
    private int collisions = 0;

    public HashTable(int size) {
        list = new Integer[size];
    }

    public void addToTable(int key) {
        int index = h1(key, list.length);

        boolean foundEmpty = false;

        do {
            if (list[index] != null) {
                collisions++;
                index = h2(key, list.length);
                if (list[index] == null) {
                    foundEmpty = true;
                }
            } else {
                list[index] = key;
            }
        } while (!foundEmpty);
    }

    private int h1(int k, int m) {
        return k % m;
    }

    private int h2(int k, int m) {
        return k % (m - 1) + 1;
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

    public int getCollisions() {
        return collisions;
    }
}
