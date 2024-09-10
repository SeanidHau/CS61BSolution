public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> dq = new ArrayDeque<>();
        int size = word.length();
        for (int i = 0; i < size; i++) {
            dq.addLast(word.charAt(i));
        }
        return dq;
    }

    public boolean isPalindrome(String word) {
        int size = word.length();
        if (size <= 1 || word == null) return true;
        for (int i = 0; i < size / 2; i++) {
            if (word.charAt(i) != word.charAt(size - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int size = word.length();
        if (size <= 1 || word == null) return true;
        for (int i = 0; i < size / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(size - i - 1))) {
                return false;
            }
        }
        return true;
    }
}
