package kg.dpa.gov.evaluation.enums;

import lombok.Getter;

@Getter
public enum Language {
    RUS("ru"),
    KYR("ky");

    private final String lang;

    Language(String lang) {
        this.lang = lang;
    }
}
