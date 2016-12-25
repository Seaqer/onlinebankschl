package ru.sbt.accountservice.utils;

import org.springframework.stereotype.Component;
import ru.sbt.accountservice.exception.AccountServiceException;
import ru.sbt.core.common.Validator;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;


@Component
public class SimpleBigDecimalValidator implements Validator<BigDecimal> {

    @Override
    public boolean validate(BigDecimal sum) {
        if (sum.compareTo(BigDecimal.valueOf(100000)) > 0 || sum.compareTo(ONE) <= 0) {
            throw new AccountServiceException("сумма перевода должна быть от 1 до 100000");
        }
        return true;
    }
}
