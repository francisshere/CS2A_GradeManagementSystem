
import java.util.Scanner;
import java.util.Stack;
import java.util.InputMismatchException;

public class CS2A_Group1_Lab4 {
    public static void main(String[] args) {
        boolean MenuReturn;
        boolean retry;
        boolean validExpression;
        int userChoice;

        do {
            userChoice = mainMenu();
            MenuReturn = false;
            
            switch (userChoice) {
                case 1:
                case 2:
                case 3:
                    do {
                        retry = false;
                        System.out.print("\u000c");
                        String expressionType = (userChoice == 1) ? "INFIX TO POSTFIX" : 
                                                (userChoice == 2) ? "INFIX TO PREFIX" : 
                                                "POSTFIX TO INFIX";
                        System.out.println("============================================");
                        System.out.println("              " + expressionType + "              ");
                        System.out.println("============================================");
                        System.out.println("");
                        System.out.print("Enter the expression: ");
                        Scanner scan1 = new Scanner(System.in);
                        String userInput = scan1.nextLine();
                        userInput = fixFormat(userInput);

                        validExpression = (userChoice == 3) ? postfixValidator(userInput) : infixValidator(userInput);

                        if (validExpression) {
                            Scanner scan = new Scanner(System.in);
                            String enter;
                            boolean input = true;
                            boolean retryInner;
                            do {
                                retryInner = false;
                                System.out.println();
                                System.out.println("Press Enter to continue... ");
                                enter = scan.nextLine();
                                if (enter.equals("")) {
                                    input = true;
                                } else {
                                    retryInner = true;
                                }
                            } while (retryInner);
                            retry = input;
                        } else {
                            switch (userChoice) {
                                case 1:
                                    String postfixConverted = InfixToPostfix(userInput);
                                    System.out.println("Postfix Expression: " + postfixConverted);
                                    System.out.println("");
                                    System.out.println("============================================");
                                    System.out.println("");
                                    break;
                                case 2:
                                    String prefixConverted = InfixToPrefix(userInput);
                                    System.out.println("Prefix Expression: " + prefixConverted);
                                    System.out.println("");
                                    System.out.println("============================================");
                                    System.out.println("");
                                    break;
                                case 3:
                                    String infixConverted = PostfixToInfix(userInput);
                                    System.out.println("Infix Expression: " + infixConverted);
                                    System.out.println("");
                                    System.out.println("============================================");
                                    System.out.println("");
                                    break;
                            }
                            boolean verify = false;
                            boolean retryInner;
                            do {
                                retryInner = false;
                                Scanner scan = new Scanner(System.in);
                                System.out.print("Try Again? (Y/N) : ");
                                String input = scan.nextLine();

                                if (input.equalsIgnoreCase("Y")) {
                                    verify = true;
                                } else if (input.equalsIgnoreCase("N")) {
                                    verify = false;
                                } else {
                                    System.out.println("Invalid Choice! Please input Y/N only.");
                                    retryInner = true;
                                }
                            } while (retryInner);
                            retry = verify;
                        }
                    } while (retry);
                    MenuReturn = true;
                    break;
                case 0:
                    System.out.print("\u000c");
                    System.out.println("=============================================");
                    System.out.println("                 Goodbye!                    ");
                    System.out.println("=============================================");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\tInvalid Choice! Please try again.");
                    retry = true;
            }
        } while (MenuReturn);
    }

    public static int mainMenu() {
        boolean retry;
        int userInput;

        System.out.print("\u000c");
        System.out.println("                        *                 ");
        System.out.println("                     *// \\%");
        System.out.println("                    +//   \\@");
        System.out.println("                   o//     \\&");
        System.out.println("                  +//       \\%");
        System.out.println("                 o//         \\+");
        System.out.println("                &//           \\*");
        System.out.println("               *//             \\o");
        System.out.println("              +//               \\&");
        System.out.println("             &//                 \\*");
        System.out.println("            o// Stack Application \\&");
        System.out.println("           +//      Conversion     \\o");
        System.out.println("          +//         Menu          \\o");
        System.out.println("         *//_________________________\\+");
        System.out.println("===============================================");
        System.out.println("");
        System.out.println("\t [1] Infix to Postfix");
        System.out.println("");            
        System.out.println("\t [2] Infix to Prefix");
        System.out.println("");
        System.out.println("\t [3] Postfix to Infix");
        System.out.println("");
        System.out.println("\t [0] Stop");
        System.out.println("");
        System.out.println("===============================================");
        System.out.println("");
        
        do {
            try {
                retry = false;
                Scanner scan = new Scanner(System.in);
                System.out.print("\t Enter Choice: ");
                userInput = scan.nextInt();
                if (userInput < 0 || userInput > 3) {
                    System.out.println("\t Invalid Choice! Please try again.");
                    retry = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("\t Invalid Choice! Please try again.");
                retry = true;
                userInput = -1;
            }   
        } while (retry);
        
        return userInput;
    }

    public static String InfixToPostfix(String infixExpression) {
        String convertedPostfix = "";
        Stack<Character> operator = new Stack<>();

        for (int i = 0; i < infixExpression.length(); i++) {
            char character = infixExpression.charAt(i);
            
            switch (character) {
                case '(':
                    operator.push(character);
                    break;
                case ')':
                    while (!operator.isEmpty() && operator.peek() != '(') {
                        convertedPostfix += operator.pop();
                    }
                    operator.pop();
                    break;
                case '-':
                case '+':
                case '/':
                case '*':
                case '^':
                    while (!operator.isEmpty() && precedence(operator.peek()) >= precedence(character)) {
                        convertedPostfix += operator.pop();
                    }
                    operator.push(character);
                    break;
                default:
                    if (checkOperand(character)) {
                        convertedPostfix += character;
                    }
                    break;
            }
        }
        
        while (!operator.isEmpty()) {
            convertedPostfix += operator.pop();
        }
        
        return convertedPostfix;
    }

    public static String InfixToPrefix(String infix2) {
        if (!ParenthesesCounter(infix2)) {
            return "The numbers of opening and closing parentheses do not match";
        }

        String infix_reversed = reverseInfix(infix2);
        String infix_postfix = InfixToPostfix(infix_reversed);
        return reverseInfix(infix_postfix);
    }

    public static String PostfixToInfix(String postfix) {
        Stack<String> stack2 = new Stack<>();
        
        for (int i = 0; i < postfix.length(); i++) {
            char exp = postfix.charAt(i);

            if (Character.isAlphabetic(exp)) {
                stack2.push(String.valueOf(exp));
            } else if (checkOperator(exp)) {
                String right = stack2.pop();
                String left = stack2.pop();
                stack2.push("(" + left + exp + right + ")");
            }
        }

        return stack2.pop();
    }

    public static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
            default: return -1;
        }
    }

    public static boolean infixValidator(String infixInput) {
        Stack<Character> stack = new Stack<>();
        String infix = "";
        int parenthesisCount = 0;
        boolean validity = false;

        if (infixInput.isEmpty()) {
            System.out.println();
            System.out.println("Invalid Infix Expression!");
            System.out.println();
            System.out.println("Please input an expression.");
            return true;
        }

        if (checkOperand(infixInput.charAt(0))) {
            for (int i = 0; i < infixInput.length(); i++) {
                char ch = infixInput.charAt(i);

                switch (ch) {
                    case '(':
                    case ')':
                    case '-':
                    case '+':
                    case '/':
                    case '*':
                    case '^':
                    default:
                }
            }
        }
        return validity;
    }

    public static boolean postfixValidator(String postfixInput) { return false; }
    public static boolean checkOperator(char letter1) { return false; }
    public static boolean checkOperand(char letter2) { return false; }
    public static int parenthesis(char letter3) { return -1; }
    public static String reverseInfix(String infix3) { return ""; }
    public static String fixFormat(String input) { return input; }
    public static boolean ParenthesesCounter(String expression) { return false; }
}
