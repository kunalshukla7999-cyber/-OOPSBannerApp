public class Main {
    public static void main(String[] args) {
        Banner banner1 = new Banner("Welcome to OOPS Banner App", 30, '*');
        Banner banner2 = new Banner("Java Programming", 25, '#');
        Banner banner3 = new Banner("By Kunal Shukla", 20, '-');

        System.out.println("Banner 1:");
        banner1.display();

        System.out.println("\nBanner 2:");
        banner2.display();

        System.out.println("\nBanner 3:");
        banner3.display();
    }
}
