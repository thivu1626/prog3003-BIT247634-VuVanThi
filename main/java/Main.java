/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hhehe
 */
public class Main {
    public static void main(String[] args) {

        // Cộng
        MathOperation add = (a, b) -> a + b;

        // Trừ
        MathOperation subtract = (a, b) -> a - b;

        // Nhân
        MathOperation multiply = (a, b) -> a * b;

        // Chia
        MathOperation divide = (a, b) -> {
            if (b == 0) {
                System.out.println("Không thể chia cho 0!");
                return 0;
            }
            return a / b;
        };

        int a =10, b = 5;

        System.out.println("Cộng: " + add.compute(a, b));
        System.out.println("Trừ: " + subtract.compute(a, b));
        System.out.println("Nhân: " + multiply.compute(a, b));
        System.out.println("Chia: " + divide.compute(a, b));
    }
}
