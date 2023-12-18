import java.util.*;

public class Main {
    private static final String PARAGRAPH = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";
    private static final Set<String> PUNCTUATION_MARK = Set.of(".", "?", "!");
    private static final Set<String> VOWELS = Set.of("a", "e", "i", "o", "u");


    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = getThread1();
        Thread thread2 = getThread2();
        Thread thread3 = getThread3();
        Thread thread4 = getThread4();
        Thread thread5 = getThread5();
        Thread thread6 = getThread6();

        System.out.println("\nIzvorni tekst:\n" + PARAGRAPH + "\n");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
    }

    private static Thread getThread6() {
        return new Thread() {
            public void run() {
                System.out.println("\nMetoda 6 - statistika po rijecima:\n" + countVowelsInWords(PARAGRAPH) + "\n");
            }
        };
    }

    private static Thread getThread5() {
        return new Thread() {
            public void run() {
                System.out.println("\nMetoda 5 - statistika po samoglasnicima:\n" + countVowelsIndividually(PARAGRAPH) + "\n");
            }
        };
    }

    private static Thread getThread4() {
        return new Thread() {
            public void run() {
                List<String> sentences = splitParagraphToSentences(PARAGRAPH);
                System.out.println("\nMetoda 4 - obrnuti recenice:\n" + invertSentences(sentences) + "\n");
            }
        };
    }

    private static Thread getThread3() {
        return new Thread() {
            public void run() {
                List<String> sentences = splitParagraphToSentences(PARAGRAPH);
                System.out.println("\nMetoda 3 - obrnuti rijeci u recenicama:\n" + invertWordsInSentences(sentences) + "\n");
            }
        };
    }

    private static Thread getThread2() {
        return new Thread() {
            public void run() {
                List<String> sentences = splitParagraphToSentences(PARAGRAPH);
                System.out.println("\nMetoda 2 - obrnuti slova u svakoj rijeci:\n" + invertCharsInWords(sentences) + "\n");
            }
        };
    }

    private static Thread getThread1() {
        return new Thread() {
            public void run() {
                List<String> sentences = splitParagraphToSentences(PARAGRAPH);
                System.out.println("\nMetoda 1 - izmjesati slova u svakoj rijeci:\n" + shuffleCharsInWords(sentences) + "\n");
            }
        };
    }

    private static Map<String, Integer> countVowelsInWords(String paragraph) {
        Map<String, Integer> wordsMap = new HashMap<>();
        List<String> wordList = splitSentenceToWords(paragraph);
        Set<String> wordSet = new HashSet<>(wordList);

        for (String word : wordSet) {
            word = word.toLowerCase();
            for (int i = 0; i < word.length(); i++) {
                if (VOWELS.contains(String.valueOf(word.charAt(i))))
                    wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
            }
        }
        return wordsMap;
    }

    private static Map<Character, Integer> countVowelsIndividually(String paragraph) {
        Map<Character, Integer> charMap = new HashMap<>();
        paragraph = paragraph.replaceAll("[^a-zA-Z]", "").toLowerCase();

        for (int i = 0; i < paragraph.length(); i++) {
            if (VOWELS.contains(String.valueOf(paragraph.charAt(i))))
                charMap.put(paragraph.charAt(i), charMap.getOrDefault(paragraph.charAt(i), 0) + 1);
        }
        return charMap;
    }

    private static String invertSentences(List<String> sentences) {
        String result = "";

        for (String sentence : sentences) {
            result = sentence + " " + result;
        }
        return result;
    }

    private static String invertWordsInSentences(List<String> sentences) {
        StringBuilder result = new StringBuilder();

        for (String sentence : sentences) {
            String punctuationMark = String.valueOf(sentence.charAt(sentence.length() - 1));
            result.append(reverseWordsInSentence(splitSentenceToWords(sentence))).append(punctuationMark).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }

    private static String reverseWordsInSentence(List<String> words) {
        String result = "";

        for (String word : words) {
            boolean punctuation = String.valueOf(word.charAt(word.length() - 1)).equals(",");
            word = punctuation ? word.substring(0, word.length() - 1) : word;
            String tempWord = word.toLowerCase() + (punctuation ? ", " : " ");
            result = tempWord + result;
        }
        return result.substring(0, 1).toUpperCase() + result.substring(1, result.length() - 1);
    }

    private static String invertCharsInWords(List<String> sentences) {
        StringBuilder result = new StringBuilder();

        for (String sentence : sentences) {
            String punctuationMark = String.valueOf(sentence.charAt(sentence.length() - 1));
            result.append(reverseLettersInWords(splitSentenceToWords(sentence))).append(punctuationMark).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }

    private static String reverseLettersInWords(List<String> words) {
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            boolean punctuation = String.valueOf(word.charAt(word.length() - 1)).equals(",");
            word = punctuation ? word.substring(0, word.length() - 1) : word;
            result.append(reverseOrder(word)).append(punctuation ? "," : "").append(" ");
        }
        return result.substring(0, result.length() - 1);
    }

    private static String reverseOrder(String word) {
        StringBuilder result = new StringBuilder();
        boolean isCapital = Character.isUpperCase(word.charAt(0));

        for (int i = 0; i < word.length(); i++) {
            if (i == word.length() - 1 && isCapital) {
                result.insert(0, String.valueOf(word.charAt(i)).toUpperCase());
            } else {
                result.insert(0, String.valueOf(word.charAt(i)).toLowerCase());
            }
        }
        return result.toString();
    }

    private static String shuffleCharsInWords(List<String> sentences) {
        StringBuilder result = new StringBuilder();

        for (String sentence : sentences) {
            String punctuationMark = String.valueOf(sentence.charAt(sentence.length() - 1));
            result.append(mixLettersInWords(splitSentenceToWords(sentence))).append(punctuationMark).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }

    private static String mixLettersInWords(List<String> words) {
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            boolean punctuation = String.valueOf(word.charAt(word.length() - 1)).equals(",");
            word = punctuation ? word.substring(0, word.length() - 1) : word;
            result.append(shuffle(word)).append(punctuation ? "," : "").append(" ");
        }
        return result.substring(0, result.length() - 1);
    }

    private static String shuffle(String word) {
        Random random = new Random();
        char[] charArray = word.toCharArray();

        for (int i = 1; i < charArray.length - 1; i++) {
            int j = random.nextInt(charArray.length - 2);
            char temp = charArray[i];
            charArray[i] = charArray[j + 1];
            charArray[j + 1] = temp;
        }
        return new String(charArray);
    }

    private static List<String> splitSentenceToWords(String sentence) {
        return Arrays.asList(sentence.split("[ .!?]"));
    }

    private static List<String> splitParagraphToSentences(String paragraph) {
        List<String> sentences = new ArrayList<>();
        int start = 0;

        for (int i = 0; i < paragraph.length(); i++) {
            if (PUNCTUATION_MARK.contains(String.valueOf(paragraph.charAt(i)))) {
                sentences.add(paragraph.substring(start, i + 1));
                start = i + 2;
            }
        }
        return sentences;
    }
}