package main;
import menu.Menu;
import service.LoginService;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        LoginService loginService = new LoginService();

        System.out.println("======================================");
        System.out.println("       SMART PARKING MANAGEMENT");
        System.out.println("======================================");
        System.out.println("            ADMIN LOGIN");
        System.out.println("======================================");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (loginService.login(username, password)) {

            System.out.println("\nLogin successful!");
            System.out.println("Welcome, Admin.");

            Menu menu = new Menu();
            menu.showMenu();

        } else {

            System.out.println("\nInvalid username or password.");
            System.out.println("Access denied.");
        }

        scanner.close();
    }
}
