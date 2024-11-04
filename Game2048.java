import java.util.Random;
import java.util.Scanner;

public class Game2048 {
    private int[][] board;
    private int size;
    private Random rand;
    private boolean gameOver;

    public Game2048(int size) {
        setSize(size);
        board = new int[size][size];
        rand = new Random();
        gameOver = false;
        spawnTile();
        spawnTile();
    }

    public void setSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size <= 0");
        }
        this.size = size;
    }

    private void spawnTile() {
        int row, col;
        do {
            row = rand.nextInt(size);
            col = rand.nextInt(size);
        } while (board[row][col] != 0);

        board[row][col] = rand.nextInt(10) < 9 ? 2 : 4;
    }

    private void printBoard() {
        int maxTile = getMaxTile();
        int cellWidth = String.valueOf(maxTile).length();
        String lineSeparator = constructLineSeparator(cellWidth);

        for (int i = 0; i < size; i++) {
            System.out.println(lineSeparator);
            System.out.print("|");
            for (int j = 0; j < size; j++) {
                String cellValue = board[i][j] == 0 ? "" : String.valueOf(board[i][j]);
                System.out.printf("%" + cellWidth + "s|", cellValue);
            }
            System.out.println();
        }
        System.out.println(lineSeparator);
    }

    private String constructLineSeparator(int cellWidth) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < size; i++) {
            line.append("+");
            for (int j = 0; j < cellWidth; j++) {
                line.append("-");
            }
        }
        line.append("+");
        return line.toString();
    }

    private int getMaxTile() {
        int max = 2;
        int maxPossible = 2 << (size * size);
        while (max < maxPossible) {
            max <<= 1;
        }
        return max >> 1;
    }

    private boolean move(char direction) {
        boolean moved = false;
        switch (direction) {
            case 'w':
                moved = moveUp();
                break;
            case 's':
                moved = moveDown();
                break;
            case 'a':
                moved = moveLeft();
                break;
            case 'd':
                moved = moveRight();
                break;
        }
        return moved;
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < size; col++) {
            int[] currentColumn = new int[size];
            int index = 0;
            boolean merged = false;
            for (int row = 0; row < size; row++) {
                if (board[row][col] != 0) {
                    if (index > 0 && currentColumn[index - 1] == board[row][col] && !merged) {
                        currentColumn[index - 1] *= 2;
                        merged = true;
                        if (currentColumn[index - 1] == 2048) {
                            System.out.println("Congrats, now you can keep playing for a higher score.");
                        }
                    } else {
                        currentColumn[index++] = board[row][col];
                        merged = false;
                    }
                    if (row != index - 1) {
                        moved = true;
                    }
                }
            }
            for (int row = 0; row < size; row++) {
                board[row][col] = currentColumn[row];
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < size; col++) {
            int[] currentColumn = new int[size];
            int index = size - 1;
            boolean merged = false;
            for (int row = size - 1; row >= 0; row--) {
                if (board[row][col] != 0) {
                    if (index < size - 1 && currentColumn[index + 1] == board[row][col] && !merged) {
                        currentColumn[index + 1] *= 2;
                        merged = true;
                        if (currentColumn[index + 1] == 2048) {
                            System.out.println("Congrats, now you can keep playing for a higher score.");
                        }
                    } else {
                        currentColumn[index--] = board[row][col];
                        merged = false;
                    }
                    if (row != index + 1) {
                        moved = true;
                    }
                }
            }
            for (int row = 0; row < size; row++) {
                board[row][col] = currentColumn[row];
            }
        }
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int row = 0; row < size; row++) {
            int[] currentRow = new int[size];
            int index = 0;
            boolean merged = false;
            for (int col = 0; col < size; col++) {
                if (board[row][col] != 0) {
                    if (index > 0 && currentRow[index - 1] == board[row][col] && !merged) {
                        currentRow[index - 1] *= 2;
                        merged = true;
                        if (currentRow[index - 1] == 2048) {
                            System.out.println("Congrats, now you can keep playing for a higher score.");
                        }
                    } else {
                        currentRow[index++] = board[row][col];
                        merged = false;
                    }
                    if (col != index - 1) {
                        moved = true;
                    }
                }
            }
            for (int col = 0; col < size; col++) {
                board[row][col] = currentRow[col];
            }
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int row = 0; row < size; row++) {
            int[] currentRow = new int[size];
            int index = size - 1;
            boolean merged = false;
            for (int col = size - 1; col >= 0; col--) {
                if (board[row][col] != 0) {
                    if (index < size - 1 && currentRow[index + 1] == board[row][col] && !merged) {
                        currentRow[index + 1] *= 2;
                        merged = true;
                        if (currentRow[index + 1] == 2048) {
                            System.out.println("Congrats, now you can keep playing for a higher score.");
                        }
                    } else {
                        currentRow[index--] = board[row][col];
                        merged = false;
                    }
                    if (col != index + 1) {
                        moved = true;
                    }
                }
            }
            for (int col = 0; col < size; col++) {
                board[row][col] = currentRow[col];
            }
        }
        return moved;
    }

    private boolean canMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0)
                    return true;
                if (i < size - 1 && board[i][j] == board[i + 1][j])
                    return true;
                if (j < size - 1 && board[i][j] == board[i][j + 1])
                    return true;
            }
        }
        return false;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        printBoard();
        while (!gameOver) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("exit")) {
                break;
            } else if (input.matches("[wasd]")) {
                boolean moved = move(input.charAt(0));
                if (moved) {
                    spawnTile();
                    printBoard();
                    if (!canMove()) {
                        System.out.println("Game over");
                        gameOver = true;
                    }
                } else {
                    System.out.println("Invalid move. No tiles can move that direction. Try again.");
                }
            } else {
                System.out.println("Invalid command. Please use \"w\", \"a\", \"s\" and \"d\" for up, left, down, and right. Or \"exit\" to quit.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        int size = 4;
        if (args.length == 1) {
            try {
                size = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument, must be an int.");
                return;
            }
        } else if (args.length > 1) {
            System.out.println("Invalid number of arguments. Please provide one argument for size or none for default settings.");
            return;
        }

        try {
            Game2048 game = new Game2048(size);
            game.play();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}