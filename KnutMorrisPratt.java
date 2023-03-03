package analyzer;

public class KnutMorrisPratt {
    boolean execute(String needle, String haystack) {

        // perform kmp algorithm

        int n = needle.length();
        int h = haystack.length();

        // longest prefix-suffix
        int[] lps = new int[n];
        int j = 0;

        longestPrefixSuffix(needle, n, lps);
        int i = 0;
        while (i < h) {
            if (needle.charAt(j) == haystack.charAt(i)) {
                j++;
                i++;
            }
            if (j == n) {
                return true;
            }
            else if (i < h && needle.charAt(j) != haystack.charAt(i)) {
                if (j != 0) {
                    j = lps[j-1];
                } else {
                    i = i + 1;
                }
            }
        }
        return false;
    }

    void longestPrefixSuffix(String needle, int n, int[] p) {
        int len = 0;
        int i = 1;
        p[0] = 0;

        while (i < n) {
            if (needle.charAt(i) == needle.charAt(len)) {
                len++;
                p[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = p[len-1];
                } else {
                    p[i] = len;
                    i++;
                }
            }
        }
    }
}
