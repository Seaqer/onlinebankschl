package ru.sbt.authservice.utils;


import org.springframework.stereotype.Component;
import ru.sbt.authservice.exceptions.AuthServiceException;
import ru.sbt.core.common.Validator;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для проверки вводимых данных на корректность
 */
@Component
public class CommonValidatorService implements Validator<String> {
    private static final Logger LOGGER = Logger.getLogger(CommonValidatorService.class);

    /**
     * Проверить вводимые данные
     * @param data данные для проверки
     * @return результат проверки
     */
    @Override
    public boolean validate(String data){
        if (Objects.isNull(data)) {
            LOGGER.trace("CommonValidatorService:validate - data:null");
            throw new AuthServiceException("поле логина не может быть пустым");
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{3,25}$");
        Matcher match = pattern.matcher(data);
        if (!match.find()) {
            LOGGER.trace("CommonValidatorService:validate - failed");
            throw new AuthServiceException("должен состоять из символов и цифр, начинаться с буквы и иметь длинну от 3 до 25 символов");
        }
        return true;
    }

}
