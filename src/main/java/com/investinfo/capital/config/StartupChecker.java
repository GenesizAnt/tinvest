package com.investinfo.capital.config;

import java.util.Scanner;

public class StartupChecker {

    private static final String CODE_WORD = "2"; // Замените на ваше кодовое слово

    public static void checkCodeWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите кодовое слово: ");
        String input = scanner.nextLine();

        if (!CODE_WORD.equals(input)) {
            System.out.println("Неверное кодовое слово. Приложение будет закрыто.");
            System.exit(1); // Завершение работы приложения с кодом 1
        }

        System.out.println("Кодовое слово верно. Приложение запускается...");
    }
}
