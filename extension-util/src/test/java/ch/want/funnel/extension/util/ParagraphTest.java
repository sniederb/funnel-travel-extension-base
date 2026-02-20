package ch.want.funnel.extension.util;

import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ParagraphTest {

    @ParameterizedTest
    @CsvSource(value = {
        // single sentence, should return everything
        "Graden Route Adventure mit Intrepid inkl. Aktivitäten, Halbpension und Transfer#Graden Route Adventure mit Intrepid inkl. Aktivitäten, Halbpension und Transfer",
        // Longer text, should return first sentence
        "Buchen Sie jetzt Ihre geführte Besichtigung der Alhambra und besuchen Sie die Hauptanlagen mit einem offiziellen Führer. Betreten Sie die Alzacaba, die Nasridengärten und den Generalife.#Buchen Sie jetzt Ihre geführte Besichtigung der Alhambra und besuchen Sie die Hauptanlagen mit einem offiziellen Führer.",
        // Longer text with sentence ending in different punctuation. Should return first sentence
        "If New Zealand's been calling your name, why not answer with a side of Australia? This 30-day adventure takes you from the tip...#If New Zealand's been calling your name, why not answer with a side of Australia?", //
        // Long text with dots ('.') as part of the text. Should return first sentence
        "Sind Sie ein Fan von Harry Potter und J. K. Rowlings magischer Welt? Dann ist diese Tour durch die Warner Bros. Studios in London#Sind Sie ein Fan von Harry Potter und J. K. Rowlings magischer Welt?", //
        // Another example of dots ('.') as part of the text
        "Warner Bros. Studio Tour London – The Making of Harry Potter ab Victoria London#Warner Bros. Studio Tour London – The Making of Harry Potter ab Victoria London", //
        // Another example of dots ('.') as part of the text
        "ORCA Dive Club Coral Garden - BOOT Special buchbar bis 30.1.#ORCA Dive Club Coral Garden - BOOT Special buchbar bis 30.1.",
        //
        "Guadeloupe (01.03.26. - 08.03.26)#Guadeloupe (01.03.26. - 08.03.26)"
    }, delimiter = '#')
    void getFirstSentence(final String input, final String expected) {
        final Paragraph testee = new Paragraph(input, Locale.ENGLISH, Locale.GERMAN, Locale.FRENCH);
        final String actual = testee.getFirstSentence();
        Assertions.assertEquals(expected, actual);
    }
}
