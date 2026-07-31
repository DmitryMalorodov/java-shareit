package ru.practicum.shareit;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

public class GeneralAssertions {

    public static <T> void isEqualTo(T actValue, T expValue, String errorMessage, SoftAssertions softAssert) {
        softAssert.assertThat(actValue)
                .as(String.format(errorMessage, actValue, expValue))
                .isEqualTo(expValue);
    }

    public static <T> void isNotNull(T actValue, String errorMessage, SoftAssertions softAssert) {
        softAssert.assertThat(actValue)
                .as(String.format(errorMessage, actValue))
                .isNotNull();
    }

    public static <T> void isEqualTo(T actValue, T expValue, String errorMessage) {
        Assertions.assertThat(actValue)
                .as(String.format(errorMessage, actValue, expValue))
                .isEqualTo(expValue);
    }

    public static void isTrue(Boolean actValue, String errorMessage) {
        Assertions.assertThat(actValue)
                .as(String.format(errorMessage, actValue))
                .isTrue();
    }
}
