import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        NameBot bot = new NameBot();
        System.out.print("Olá, eu sou seu bot!\nComo você gostaria de me chamar? ");
        bot.setName(sc.nextLine());
        System.out.println("\nPerfeito! A partir de agora serei " + bot.getName() + "\n");

        YourName user = new YourName();
        System.out.print("Qual o seu nome? ");
        user.setName(sc.nextLine());
        System.out.println("Que lindo nome! Prazer em te conhecer, " + user.getName() + "\n");

        System.out.println("Escolha um número e posso te mostrar minhas habilidades!\n");

        boolean running = true;
        while (running) {
            System.out.printf("%s, escolha:%n1 - Adivinhar sua idade%n2 - Fazer um cálculo%n3 - Sair%n> ", user.getName());
            int option = getIntInput(sc);

            switch (option) {
                case 1 -> new GuessAge().start(sc);
                case 2 -> new Calculate().start(sc);
                case 3 -> running = false;
                default -> System.out.println("Opção inválida!");
            }

            System.out.println(running ? "\nDe volta ao menu!\n" : "\nFoi um prazer, até a próxima!");
        }

        sc.close();
    }

    private static int getIntInput(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Por favor, digite um número válido: ");
            sc.next();
        }
        return sc.nextInt();
    }
}

class NameBot {
    private String name;
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
}

class YourName {
    private String name;
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
}

class GuessAge {
    private final Random random = new Random();
    private int min = 1, max = 120, guess = random.nextInt(1, 120);

    public void start(Scanner sc) {
        System.out.printf("%nOk! Digite 'mais', 'menos' ou 'acertou'%nMeu primeiro palpite é %d anos.%n", guess);
        while (true) {
            String hint = sc.next().toLowerCase();
            if (hint.equals("acertou")) {
                System.out.printf("Acertei! Você tem %d anos.%n", guess);
                break;
            }
            adjustGuess(hint);
            System.out.printf("Sua idade é %d anos?%n", guess);
        }
    }

    private void adjustGuess(String hint) {
        try {
            switch (hint) {
                case "mais" -> min = guess;
                case "menos" -> max = guess;
                default -> {
                    System.out.println("Entrada inválida!");
                    return;
                }
            }
            guess = random.nextInt(min, max);
        } catch (IllegalArgumentException e) {
            reset();
        }
    }

    private void reset() {
        min = 1; max = 120; guess = random.nextInt(min, max);
        System.out.println("Algo deu errado! Vamos tentar de novo.");
    }
}

class Calculate {
    public void start(Scanner sc) {
        System.out.println("\nVamos calcular!");
        while (true) {
            System.out.print("Digite: <num1> <operação> <num2>\n> ");
            double result = calc(getInt(sc), sc.next(), getInt(sc));
            System.out.println("Resultado: " + result);

            System.out.print("Outra operação? (S/N): ");
            if (sc.next().equalsIgnoreCase("N")) break;
        }
    }

    private double calc(int a, String op, int b) {
        if ("/".equals(op) && b == 0) {
            System.out.println("Erro: divisão por zero!");
            return Double.NaN;
        }
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "x", "*" -> a * b;
            case "/" -> (double) a / b;
            default -> { System.out.println("Operador inválido!"); yield Double.NaN; }
        };
    }

    private int getInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Número inválido, tente novamente: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
