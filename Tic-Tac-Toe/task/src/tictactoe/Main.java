package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Сделал заполнение пробелами, только потому что не проходил тест. Можно просто создать массив [3][3].
        char[][] gameMatrix = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        // Рендер первого пустого поля.
        renderField(gameMatrix);

        // Core Game Loop
        char player = 'X';
        byte gameStateCode;
        // Пока не вернётся код состояния игры отличный от "Игра не окончена", продолжать игру.
        while ((gameStateCode = stateOfTheGameCode(gameMatrix)) == 0) {
            System.out.print("Enter the coordinates: ");
            char[] coordinatesArray = new char[2];
            coordinatesArray[0] = scanner.next().charAt(0);
            coordinatesArray[1] = scanner.next().charAt(0);

            // Проверка ввода координат для заполнения.
            byte correctInputCode;
            while ((correctInputCode = getCorrectInputCode(coordinatesArray, gameMatrix)) != 0) {
                switch (correctInputCode) {
                    case -1:
                        System.out.println("You should enter numbers!");
                        break;

                    case -2:
                        System.out.println("Coordinates should be from 1 to 3!");
                        break;

                    case -3:
                        System.out.println("This cell is occupied! Choose another one!");
                        break;
                }
                System.out.print("Enter the coordinates: ");
                coordinatesArray[0] = scanner.next().charAt(0);
                coordinatesArray[1] = scanner.next().charAt(0);
            }

            // Заполнение элемента, рендер обновлённого поля и смена игрока.
            gameMatrix[3 - Character.getNumericValue(coordinatesArray[1])][Character.getNumericValue(coordinatesArray[0]) - 1] = player;
            renderField(gameMatrix);
            if (player == 'X') player = 'O';
            else player = 'X';
        }

        // В зависимости от последнего кода состояния, вывод исхода игры.
        if (gameStateCode == 1) System.out.println("X wins");
        else if (gameStateCode == 2) System.out.println("O wins");
        else System.out.println("Draw");
    }

    /**
     * Метод возвращает код корректности ввода.
     * @param coordinatesArray
     * @param matrix
     * @return
     */
    private static byte getCorrectInputCode(char[] coordinatesArray, char[][] matrix) {
        if (!Character.isDigit(coordinatesArray[0]) || !Character.isDigit(coordinatesArray[1])) {
            return -1; // "You should enter numbers!"
        } else if (Character.getNumericValue(coordinatesArray[0]) < 1 || Character.getNumericValue(coordinatesArray[0]) > 3 ||
                Character.getNumericValue(coordinatesArray[1]) < 1 || Character.getNumericValue(coordinatesArray[1]) > 3) {
            return -2; // "Coordinates should be from 1 to 3!"
        }

        if (matrix[3 - Character.getNumericValue(coordinatesArray[1])][Character.getNumericValue(coordinatesArray[0]) - 1] != ' ' &&
                matrix[3 - Character.getNumericValue(coordinatesArray[1])][Character.getNumericValue(coordinatesArray[0]) - 1] != '_' &&
                matrix[3 - Character.getNumericValue(coordinatesArray[1])][Character.getNumericValue(coordinatesArray[0]) - 1] != '\u0000') {
            return -3; // "This cell is occupied! Choose another one!"
        }

        return 0;
    }

    /**
     * Метод рендерит матрицу в консоли.
     * @param matrix
     */
    private static void renderField(char[][] matrix) {
        System.out.println("---------");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println("| " + matrix[i][0] + " " + matrix[i][1] + " " + matrix[i][2] + " |");
        }
        System.out.println("---------");
    }

    /**
     * Метод возвращает код текущего состояния игры.
     * @param matrix
     * @return
     */
    private static byte stateOfTheGameCode(char[][] matrix) {
        // Флаги. Может ли один из игроков быть победителем.
        boolean isXcanBeWinner = false;
        boolean isOcanBeWinner = false;

        // Проверки по всем путям на победу.
        // Проверка первой строки, первого столбца и первой диагонали
        if (matrix[0][0] != '\u0000' && matrix[0][0] != ' ' && matrix[0][0] != '_') {
            if (matrix[0][0] == matrix[0][1] && matrix[0][1] == matrix[0][2]) {
                if (matrix[0][0] == 'X') isXcanBeWinner = true;
                else isOcanBeWinner = true;
            } else if (matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2]) {
                if (matrix[0][0] == 'X') isXcanBeWinner = true;
                else isOcanBeWinner = true;
            } else if (matrix[0][0] == matrix[1][0] && matrix[1][0] == matrix[2][0]) {
                if (matrix[0][0] == 'X') isXcanBeWinner = true;
                else isOcanBeWinner = true;
            }
        }
        // Проверка второго столбца, второй строки и второй диагонали
        if (matrix[1][1] != '\u0000' && matrix[1][1] != ' ' && matrix[1][1] != '_') {
            if (matrix[0][1] == matrix[1][1] && matrix[1][1] == matrix[2][1]) {
                if (matrix[0][1] == 'X') isXcanBeWinner = true;
                else isOcanBeWinner = true;
            } else if (matrix[1][0] == matrix[1][1] && matrix[1][1] == matrix[1][2]) {
                if (matrix[1][0] == 'X') isXcanBeWinner = true;
                else isOcanBeWinner = true;
            } else if (matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0]) {
                if (matrix[0][2] == 'X') isXcanBeWinner = true;
                else isOcanBeWinner = true;
            }
        }
        // Проверка третьего столбца и третьей строки
        if (matrix[2][2] != '\u0000' && matrix[2][2] != ' ' && matrix[2][2] != '_') {
            if (matrix[2][0] == matrix[2][1] && matrix[2][1] == matrix[2][2]) {
                if (matrix[2][0] == 'X') isXcanBeWinner = true;
                else isOcanBeWinner = true;
            } else if (matrix[0][2] == matrix[1][2] && matrix[1][2] == matrix[2][2]) {
                if (matrix[0][2] == 'X') isXcanBeWinner = true;
                else isOcanBeWinner = true;
            }
        }

        // Первые возвраты. На невозможное событие и на победы игрков.
        if (isOcanBeWinner && isXcanBeWinner) return -1; // "Impossible" state
        if (isXcanBeWinner) return 1; // "X wins"
        if (isOcanBeWinner) return 2; // "O wins"

        // Подсчёт крестов, кругов и пропусков.
        int Xcounter = 0;
        int Ocounter = 0;
        int spaceCounter = 0;
        for (char[] array : matrix) {
            for (char element : array) {
                if (element == 'X') {
                    Xcounter++;
                }
                else if (element == 'O') {
                    Ocounter++;
                }
                else spaceCounter++;
            }
        }

        // Если крестов или кругов много больше знаков противника, то невозможный исход
        // Если после всех проверок, оказалось, что свободного места нет, то ничья.
        if (Xcounter - Ocounter >= 2 || Ocounter - Xcounter >= 2) return -1; // "Impossible" state
        if (spaceCounter == 0) return 3; // "Draw"

        return 0; // "Game not finished"
    }
}
