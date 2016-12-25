package ru.sbt.accountservice.utils;

import org.springframework.stereotype.Component;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.core.common.Validator;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для проверки вводимых данных на корректность
 */
@Component
public class SimpleStringValidator implements Validator<String> {

    /**
     * Проверить вводимые данные
     * @param data данные для проверки
     * @return результат проверки
     */
    @Override
    public boolean validate(String data){
        if (Objects.isNull(data)) {
            throw new AccountServiceException("значение не может быть пустым");
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{3,25}$");
        Matcher match = pattern.matcher(data);
        if (!match.find()) {
            throw new AccountServiceException("значение должно быть из символов и цифр, начинаться с буквы и иметь длинну от 3 до 25 символов");
        }
        return true;
    }

}
