import java.util.HashMap;

public class OOPSBannerApp {

    public static HashMap<Character, String[]> createCharacterMap() {
        HashMap<Character, String[]> charMap = new HashMap<>();

        charMap.put('O', new String[]{
            "   ***   ",
            " **   ** ",
            "**     **",
            "**     **",
            "**     **",
            "**     **",
            "**     **",
            " **   ** ",
            "   ***   "
        });

        charMap.put('P', new String[]{
            "*****    ",
            "**   **  ",
            "**    ** ",
            "**   **  ",
            "*****    ",
            "**       ",
            "**       ",
            "**       ",
            "**       "
        });

        charMap.put('S', new String[]{
            " *****   ",
            "**       ",
            "**       ",
            " **      ",
            "   ***   ",
            "      ** ",
            "       **",
            "      ** ",
            " *****   "
        });

        charMap.put(' ', new String[]{
            "         ",
            "         ",
            "         ",
            "         ",
            "         ",
            "         ",
            "         ",
            "         ",
            "         "
        });

        return charMap;
    }

    public static void displayBanner(String message, HashMap<Character, String[]> charMap) {
        // Dynamically determine height from any character pattern
        int patternHeight = charMap.get('O').length;

        for (int line = 0; line < patternHeight; line++) {
            StringBuilder sb = new StringBuilder();
            for (char ch : message.toUpperCase().toCharArray()) {
                if (charMap.containsKey(ch)) {
                    sb.append(charMap.get(ch)[line]).append("  ");
                } else {
                    sb.append(charMap.get(' ')[line]).append("  ");
                }
            }
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        HashMap<Character, String[]> charMap = createCharacterMap();
        String message = "OOPS";
        displayBanner(message, charMap);
    }
}