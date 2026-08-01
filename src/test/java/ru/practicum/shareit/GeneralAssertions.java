package ru.practicum.shareit;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.within;

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

    public static void isCloseTo(LocalDateTime actValue, LocalDateTime expValue, String errorMessage,
                                 SoftAssertions softAssert) {
        softAssert.assertThat(actValue)
                .as(String.format(errorMessage, actValue, expValue))
                .isCloseTo(expValue, within(3, ChronoUnit.SECONDS));
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
