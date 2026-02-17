public class Banner {
    private String text;
    private int width;
    private char borderChar;

    public Banner(String text, int width, char borderChar) {
        this.text = text;
        this.width = width;
        this.borderChar = borderChar;
    }

    public void display() {
        printBorder();
        printText();
        printBorder();
    }

    private void printBorder() {
        for (int i = 0; i < width; i++) {
            System.out.print(borderChar);
        }
        System.out.println();
    }

    private void printText() {
        System.out.println(borderChar + " " + text + " " + borderChar);
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getWidth() { return width; }
    public char getBorderChar() { return borderChar; }
}
