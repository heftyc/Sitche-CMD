package com.company;

import java.awt.*;
import java.util.Scanner;

import static com.company.Piece.*;

public class Board {

    static RuleBook ruleBook;
    static Opponent Opponent;

    static Position currentPosition;

    static boolean whitesTurn = true;

    static Scanner scanner;

    public static void main(String[] args) {
        ruleBook = new RuleBook();


        scanner = new Scanner(System.in);

        System.out.println("Welcome to Sitche Command Line!");

        System.out.print("Do you want to use multithreading? (Y/N): ");
        boolean multithreading = scanner.nextLine().startsWith("Y");

        System.out.print("Do you want to play as white? (Y/N): ");
        boolean whitePlayer = scanner.nextLine().startsWith("Y");

        Opponent = new Opponent(!whitePlayer, multithreading);


        while (true) {
            int depthValue = 0;
            System.out.print("How many moves do you want the opponent to search ahead? [1-5]: ");
            depthValue = Integer.parseInt(scanner.nextLine());
            if (depthValue < 6 && depthValue > 1) {
                Position.maxSearchDepth = depthValue;
                break;
            }
            System.out.println("Invalid!");
        }


        System.out.println("Files (columns) are numbered A-G. Ranks (rows) are numbered 1-8");
        System.out.println("Similar to chess notation, input moves like this: \"A6 E3\"");
        System.out.println("Where the first set is the file and rank to move from");
        System.out.println("and the last set is the file and rank to move to.");

        System.out.println();

        System.out.println("Pieces are represented by their characters in chess notation. (The letter that they start with, except for knights, which start with \"N\")");
        System.out.println("White pieces are uppercase, black pieces are lowercase.");

        currentPosition = new Position();

        render();
        playGame();
    }

    private static void playGame() {
        if (Opponent.IAmWhite == whitesTurn) {
            Opponent.makeMove(currentPosition);
        } else {
            requestMove();
        }
        render();
    }


    static final char border = '#';

    protected static void render() {
        for (int i = 0; i < 8 * 3; i++) {
            System.out.print(border);
        }
        System.out.println();

        for (int i = 0; i < 8; i++) {
            System.out.print(border);
            for (int j = 0; j < 8; j++) {
                System.out.print(currentPosition.pieces[j][i]. + "  ");

            }
            System.out.println();
        }
        for (int i = 0; i < 8; i++) {
            System.out.print((char) ('A' + i) + "  ");
        }
        if (Opponent.IAmWhite == whitesTurn) {
            currentPosition = Opponent.makeMove(currentPosition);
            whitesTurn = !whitesTurn;
        }
        System.out.println();
    }

    private static char getSymbol(byte code) {
        switch (code) {
            case -1:
                return 'p';
            break;
            case -2:
                return 'p';
            break;
            case -3:
                return 'p';
            break;
            case -4:
                return 'p';
            break;
            case -5:
                return 'p';
            break;
            case -6:
                return 'p';
            break;
            case 1:
                return 'P';
            break;

            default:
                break;
        }
    }


    static void requestMove() {
        while (true) {

            System.out.print("Enter your move: ");
            String input = scanner.nextLine();
            if (input.length() >= 5) {
                if (input.charAt(0) >= 'A' && input.charAt(0) <= 'G' &&
                        input.charAt(1) >= '1' && input.charAt(1) <= '8' &&
                        input.charAt(3) >= 'A' && input.charAt(3) <= 'G' &&
                        input.charAt(4) >= '1' && input.charAt(4) <= '8') {

                    int fromX = input.charAt(0) - 'A';
                    int fromY = input.charAt(1) - '1';

                    int toX = input.charAt(3) - 'A';
                    int toY = input.charAt(4) - '1';


                    if (ruleBook.isMoveLegal(currentPosition, new Point(fromX, fromY), new Point(toX, toY), false)) ;

                    Position attemptedPosition = new Position(currentPosition, fromX, fromY, toX, toY);
                    if (!attemptedPosition.kingIsInCheck(!Opponent.IAmWhite)) {
                        currentPosition = attemptedPosition;
                        whitesTurn = !whitesTurn;
                        break;
                    } else {
                        System.out.println("Moving into check!");
                    }

                }
            }
            System.out.println("Invalid!");
        }
    }
}