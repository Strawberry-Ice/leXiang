package com.jfdh.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.transaction.annotation.Transactional("jpaTransactionManager")
public @interface JpaTransactional {

}
