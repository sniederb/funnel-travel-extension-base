package ch.want.funnel.extension.util;

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * This class provides a method {@link #getFirstSentence()} to shorten a lengthy description for representation in systems which might
 * require a succinct description.
 *
 * <dt>Technical</dt>
 * <dd>The Java {@link BreakIterator} doesn't work well for German and French texts. From the Javadoc: <em>Sentence boundary analysis allows
 * selection with correct interpretation of periods within numbers and abbreviations, and trailing punctuation marks such as quotation marks
 * and parentheses. </em> This highlights the lack of support for abbreviations. The most flexible approach would be based on NLP, which
 * exceeds the scope here.</dd>
 *
 * @see <a href='https://icu-project.org/docs/papers/text_boundary_analysis_in_java/'>Text Boundary Analysis in Java</a>
 */
class Paragraph {

    private static final Set<Character> PUNCTUATION = Set.of('.', '!', '?');
    private static final Map<String, List<String>> ABBREV_PER_LANGUAGE = new HashMap<>();
    private final String wholeText;
    private final Set<String> abbreviations = new HashSet<>();
    static {
        ABBREV_PER_LANGUAGE.put("de", List.of("inkl", "bzw", "vgl", "ca", "bzgl", "allg", "st", "mt", "bros"));
        ABBREV_PER_LANGUAGE.put("en", List.of("incl", "etc", "st", "mt", "mr", "mrs", "bros", "ltd"));
        ABBREV_PER_LANGUAGE.put("fr", List.of("av", "mm", "cie", "env", "etc"));
    }

    Paragraph(final String s, final Locale... locales) {
        this.wholeText = s;
        if ((locales == null) || locales.length == 0) {
            ABBREV_PER_LANGUAGE.values().forEach(abbreviations::addAll);
        } else {
            Arrays.stream(locales)
                .map(Locale::getLanguage)
                .forEach(l -> Optional.ofNullable(ABBREV_PER_LANGUAGE.get(l)).ifPresent(abbreviations::addAll));
        }
    }

    String getFirstSentence() {
        if (wholeText == null) {
            return null;
        }
        int posPunctuation = indexOfPunctuation(0);
        while (posPunctuation > 0 && isAbbreviation(posPunctuation)) {
            posPunctuation = indexOfPunctuation(posPunctuation + 1);
        }
        if (posPunctuation >= 0) {
            return wholeText.substring(0, posPunctuation + 1);
        }
        return wholeText;
    }

    /**
     * Returns true if the characters between the previous space and {@code posPunctuation}:
     * <ul>
     * <li>are included in {@link #abbreviations}</li>
     * <li>are purely numerical (30. October 2026)</li>
     * <li>have just 1 letter (J.K. Rowling)</li>
     * </ul>
     *
     * @param posPunctuation
     * @return
     */
    private boolean isAbbreviation(final int posPunctuation) {
        final int closestSpace = closestIndexOf(0, posPunctuation, ' ');
        if (closestSpace >= 0) {
            final String wordBeforePunctuation = wholeText.substring(closestSpace + 1, posPunctuation);
            return wordBeforePunctuation.length() == 1 ||
                abbreviations.contains(wordBeforePunctuation.toLowerCase().trim()) ||
                StringUtils.isNumeric(wordBeforePunctuation);
        }
        return false;
    }

    private int indexOfPunctuation(final int startIndex) {
        final int max = wholeText.length();
        for (int i = startIndex; i < max; i++) {
            if (PUNCTUATION.contains(wholeText.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private int closestIndexOf(final int startIndex, final int lastIndex, final char ch) {
        int result = -1;
        for (int i = startIndex; i < lastIndex; i++) {
            if (wholeText.charAt(i) == ch) {
                result = i;
            }
        }
        return result;
    }
}
