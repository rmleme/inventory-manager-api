package br.com.becommerce.inventory.manager.api.inventoryitem.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;

public class QuantitiesValidator implements ConstraintValidator<ValidQuantities, InventoryItem> {

	@Override
	public boolean isValid(InventoryItem inventoryItem, ConstraintValidatorContext context) {
		return inventoryItem.getReservedQuantity() <= inventoryItem.getTotalQuantity();
	}
}