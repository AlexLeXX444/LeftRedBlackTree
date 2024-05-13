package ru.app;

import java.util.Scanner;

/**
 * Необходимо превратить собранное на семинаре дерево поиска в полноценное левостороннее красно-черное дерево.
 * И реализовать в нем метод добавления новых элементов с балансировкой.
 *
 * Красно-черное дерево имеет следующие критерии:
 * • Каждая нода имеет цвет (красный или черный);
 * • Корень дерева всегда черный;
 * • Новая нода всегда красная;
 * • Красные ноды могут быть только левым ребенком;
 * • У красной ноды все дети черного цвета.
 *
 * Соответственно, чтобы данные условия выполнялись, после добавления элемента в дерево необходимо произвести балансировку,
 * благодаря которой все критерии выше станут валидными.
 * Для балансировки существует 3 операции – левый малый поворот, правый малый поворот и смена цвета.
 */

public class Main {
    private static Node root = null;

    public static void main(String[] args) {
        LeftRedBlackTree node = new LeftRedBlackTree();
        Scanner scan = new Scanner(System.in);
        boolean isNext = true;

        while (isNext) {
            try {
                System.out.println("\nВведите целое число");
                int num = scan.nextInt();
                root = node.insert(root, num);
                node.inorder(root);
            } catch (Exception e) {
                System.out.println("Закончили");
                isNext = false;
            }
        }
    }

    static class Node {

        Node left, right;
        int data;

        // красный == true, черный == false
        boolean color;

        Node(int data) {
            this.data = data;
            left = null;
            right = null;

            // Новый узел всегда красного цвета.
            color = true;
        }
    }

    public static class LeftRedBlackTree {

        // Поворот узла против часовой стрелки.
        Node rotateLeft(Node myNode) {
            System.out.print("<== повернули влево.\n");
            Node child = myNode.right;
            Node childLeft = child.left;

            child.left = myNode;
            myNode.right = childLeft;

            return child;
        }

        // Поворот узла по часовой стрелке.
        Node rotateRight(Node myNode) {
            System.out.print("==> повернули вправо.\n");
            Node child = myNode.left;
            Node childRight = child.right;

            child.right = myNode;
            myNode.left = childRight;

            return child;
        }

        // Проверяем, красный узел или нет.
        boolean isRed(Node myNode) {
            if (myNode == null) {
                return false;
            }
            return (myNode.color);
        }

        // Изменение цвета двух узлов.
        void swapColors(Node node1, Node node2) {
            boolean temp = node1.color;
            node1.color = node2.color;
            node2.color = temp;
        }

        // Вставка элемента.
        Node insert(Node myNode, int data) {
            // Обычный код вставки для любого двоичного дерева.
            if (myNode == null) {
                return new Node(data);
            }

            if (data < myNode.data) {
                myNode.left = insert(myNode.left, data);
            } else if (data > myNode.data) {
                myNode.right = insert(myNode.right, data);
            } else {
                return myNode;
            }

            // Случай 1.
            // Правый дочерний элемент красный, а левый дочерний элемент черный или не существует.
            if (isRed(myNode.right) && !isRed(myNode.left)) {

                // Повернуть узел влево.
                myNode = rotateLeft(myNode);

                // Поменять местами цвета дочернего узла. Всегда должен быть красным.
                swapColors(myNode, myNode.left);
            }

            // Случай 2.
            // Левый ребенок, а также левый внук выделены красным цветом.
            if (isRed(myNode.left) && isRed(myNode.left.left)) {
                // Повернуть узел в право.
                myNode = rotateRight(myNode);
                swapColors(myNode, myNode.right);
            }

            // Случай 3.
            // И левый, и правый дочерние элементы окрашены в красный цвет.
            if (isRed(myNode.left) && isRed(myNode.right)) {
                // Изменить цвет узла - это левый и правый дети.
                myNode.color = !myNode.color;
                myNode.left.color = false;
                myNode.right.color = false;
            }

            return myNode;
        }

        // Обход дерева по порядку.
        void inorder(Node node) {
            if (node != null) {
                inorder(node.left);
                char c = '●';
                if (!node.color) {
                    c = '◯';
                }
                System.out.print(node.data + "" + c + " ");
                inorder(node.right);
            }
        }
    }
}