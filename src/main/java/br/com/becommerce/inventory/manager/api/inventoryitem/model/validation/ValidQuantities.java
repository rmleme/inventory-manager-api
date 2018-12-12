package br.com.becommerce.inventory.manager.api.inventoryitem.model.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { QuantitiesValidator.class })
public @interface ValidQuantities {

	String message() default "{br.com.becommerce.inventory.manager.api.inventoryitem.model.validation.ValidQuantities.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}