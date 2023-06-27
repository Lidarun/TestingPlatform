package kg.dpa.gov.evaluation.utils;

import java.util.List;
import java.util.Locale;

public class LanguageUtils {
    public static String getActiveLanguage(String acceptLanguage) {
        Locale.LanguageRange[] languageRanges = Locale.LanguageRange
                .parse(acceptLanguage).toArray(new Locale.LanguageRange[0]);

        Locale bestLocale = Locale.lookup(List.of(languageRanges), List.of(Locale.getAvailableLocales()));

        return bestLocale.getLanguage();
    }
}
