package ru.sbt.authservice.utils;

import org.springframework.stereotype.Component;
import ru.sbt.authservice.exceptions.AuthServiceException;
import ru.sbt.authservice.interfaces.utils.PasswordManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * hash менеджер паролей
 */
@Component
public class HashPasswordManager implements PasswordManager {
    private static final Logger LOGGER = Logger.getLogger(HashPasswordManager.class);

    /**
     * Получение хэша пароля
     * @param data пароль
     * @return преобразованный пароль
     */
    @Override
    public String getPassword(String data) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            result = new String(messageDigest.digest(data.getBytes()), "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.error("failed getting hash", e);
            throw new AuthServiceException("failed getting hash", e);
        }
        return result;
    }
}
