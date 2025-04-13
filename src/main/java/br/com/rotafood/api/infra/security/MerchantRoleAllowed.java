package br.com.rotafood.api.infra.security;


import br.com.rotafood.api.domain.entity.merchant.MerchantUserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Target({ElementType.METHOD, ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface MerchantRoleAllowed {
    MerchantUserRole[] value();
}