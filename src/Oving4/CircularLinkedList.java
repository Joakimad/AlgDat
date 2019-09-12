package Oving4;

public class CircularLinkedList {

    //Declaring head and tail pointer as null.
    public Node head = null;
    public Node tail = null;

    //This function will add the new node at the end of the list.
    public void add(int data) {
        //Create new node
        Node newNode = new Node(data);
        //Checks if the list is empty.
        if (head == null) {
            //If list is empty, both head and tail would point to new node.
            head = newNode;
            tail = newNode;
            newNode.next = head;
        } else {
            //tail will point to new node.
            tail.next = newNode;
            //New node will become new tail.
            tail = newNode;
            //Since, it is circular linked list tail will point to head.
            tail.next = head;
        }
    }

    public void remove(int key) {
        if (head == null) {
        }

        // Find the required node
        Node curr = head, prev = new Node(0);
        while (curr.data != key) {
            if (curr.next == head) {
                System.out.printf("\nGiven node is not found"
                        + " in the list!!!");
                break;
            }
            prev = curr;
            curr = curr.next;
        }

        // Check if node is only node
        if (curr.next == head) {
            head = null;
        }

        // If more than one node, check if
        // it is first node
        if (curr == head) {
            prev = head;
            while (prev.next != head)
                prev = prev.next;
            head = curr.next;
            prev.next = head;
        }

        // check if node is last node
        else if (curr.next == head) {
            prev.next = head;
        } else {
            prev.next = curr.next;
        }
    }

    //Displays all the nodes in the list
    public void display() {
        Node current = head;
        if (head == null) {
            System.out.println("List is empty");
        } else {
            System.out.println("Nodes of the circular linked list: ");
            do {
                //Prints each node by incrementing pointer.
                System.out.print(" " + current.data);
                current = current.next;
            } while (current != head);
            System.out.println();
        }
    }

    public void killSoldiers(int intervall) {
        Node current = head;
        if (head == null) {
            System.out.println("List is empty");
        } else {
            System.out.println("Nodes of the circular linked list: ");
            do {
                //Prints each node by incrementing pointer.
                System.out.print(" " + current.data);

                for (int i = 0; i < intervall; i++) {
                    current = current.next;
                }


            } while (current != head);
            System.out.println();
        }
    }
}

