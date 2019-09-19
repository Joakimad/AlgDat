package Oving5.Opg2;

public class HashTable {

    private Integer[] list;
    private int collisions = 0;

    public HashTable(int size) {
        list = new Integer[size];
    }

    public int addToTable(int key) {

        int m = list.length;
        int h1 = find_h1(key, m);
        int h2 = find_h2(key, m);
        for (int i = 0; i < m; i++) {
            int j = probe(h1, h2, i, m);
            if (list[j] == null) {
                list[j] = key;
                return j;
            }
            collisions++;
        }
        return -1;
    }

    private int probe(int h1, int h2, int i, int m) {
        return (h1 + i * h2) % m;

    }

    private int find_h1(int k, int m) {
        return k % m;
    }

    private int find_h2(int k, int m) {
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
