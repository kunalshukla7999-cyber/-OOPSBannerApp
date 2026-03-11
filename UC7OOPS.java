public class UC7OOPS {

    
    static class CharacterPatternMap {
        private char character;
        private String[] pattern;

        public CharacterPatternMap(char character, String[] pattern) {
            this.character = character;
            this.pattern = pattern;
        }

        public char getCharacter() {
            return character;
        }

        public String[] getPattern() {
            return pattern;
        }
    }

    
    public static CharacterPatternMap[] createCharacterPatternMaps() {

        return new CharacterPatternMap[]{

                new CharacterPatternMap('O', new String[]{
                        "  ***  ",
                        " *   * ",
                        " *   * ",
                        " *   * ",
                        " *   * ",
                        " *   * ",
                        "  ***  "
                }),

                new CharacterPatternMap('P', new String[]{
                        " ****  ",
                        " *   * ",
                        " *   * ",
                        " ****  ",
                        " *     ",
                        " *     ",
                        " *     "
                }),

                new CharacterPatternMap('S', new String[]{
                        "  **** ",
                        " *    ",
                        " *     ",
                        "  ***  ",
                        "     * ",
                        "     * ",
                        " ****  "
                }),

                new CharacterPatternMap(' ', new String[]{
                        "       ",
                        "       ",
                        "       ",
                        "       ",
                        "       ",
                        "       ",
                        "       "
                })
        };
    }

   
    public static String[] getCharacterPattern(char ch, CharacterPatternMap[] charMaps) {

        for (CharacterPatternMap map : charMaps) {
            if (map.getCharacter() == ch) {
                return map.getPattern();
            }
        }

        
        return getCharacterPattern(' ', charMaps);
    }

    
    public static void printMessage(String message, CharacterPatternMap[] charMaps) {

        for (int row = 0; row < 7; row++) {

            for (int i = 0; i < message.length(); i++) {
                char ch = message.charAt(i);
                String[] pattern = getCharacterPattern(ch, charMaps);
                System.out.print(pattern[row] + "  ");
            }

            System.out.println();
        }
    }

    
    public static void main(String[] args) {

        CharacterPatternMap[] charMaps = createCharacterPatternMaps();

        String message = "OOPS";

        printMessage(message, charMaps);
    }
}