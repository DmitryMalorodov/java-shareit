package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.dto.ReqUserDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.ValidMessages.NAME_BLANK_MESSAGE;
import static ru.practicum.shareit.constant.message.UserValidationMessages.*;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка валидации запросов /users")
public class UserTests extends UserTest {
    private static final Long USER_ID = 1L;

    @Test
    void checkEmailNullValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().email(null).build();
        checkValidationError(createUserResAct(userEmailNull), EMAIL_BLANK_MESSAGE);
        verifyNoInteractions(userClient);
    }

    @Test
    void checkEmailNotCorrectValidation() throws Exception {
        ReqUserDto userEmailNotCorrect = user.toBuilder().email("email").build();
        checkValidationError(createUserResAct(userEmailNotCorrect), EMAIL_NOT_CORRECT_MESSAGE);
        verifyNoInteractions(userClient);
    }

    @Test
    void checkNameNullValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().name(null).build();
        checkValidationError(createUserResAct(userEmailNull), NAME_BLANK_MESSAGE);
        verifyNoInteractions(userClient);
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().name(" ").build();
        checkValidationError(createUserResAct(userEmailNull), NAME_BLANK_MESSAGE);
        verifyNoInteractions(userClient);
    }

    @Test
    void checkUpdateEmailNullValidation() throws Exception {
        when(userClient.updateUser(any(), anyLong())).thenReturn(ResponseEntity.ok(user));

        ReqUserDto userWithNullEmail = user.toBuilder().email(null).build();
        changeUserResAct(userWithNullEmail, USER_ID)
                .andExpect(status().isOk());

        verify(userClient).updateUser(any(), anyLong());
    }

    @Test
    void checkUpdateEmailNotCorrectValidation() throws Exception {
        ReqUserDto userWithNotCorrectEmail = user.toBuilder().email("123").build();
        checkValidationError(changeUserResAct(userWithNotCorrectEmail, USER_ID), EMAIL_NOT_CORRECT_MESSAGE);
        verifyNoInteractions(userClient);
    }

    @Test
    void checkUpdateNameNullValidation() throws Exception {
        when(userClient.updateUser(any(), anyLong())).thenReturn(ResponseEntity.ok(user));

        ReqUserDto userWithNameNull = user.toBuilder().name(null).build();
        changeUserResAct(userWithNameNull, USER_ID)
                .andExpect(status().isOk());

        verify(userClient).updateUser(any(), anyLong());
    }

    @Test
    void checkUpdateNameBlankValidation() throws Exception {
        when(userClient.updateUser(any(), anyLong())).thenReturn(ResponseEntity.ok(user));

        ReqUserDto userWithNameBlank = user.toBuilder().name(" ").build();
        changeUserResAct(userWithNameBlank, USER_ID)
                .andExpect(status().isOk());

        verify(userClient).updateUser(any(), anyLong());
    }
}
