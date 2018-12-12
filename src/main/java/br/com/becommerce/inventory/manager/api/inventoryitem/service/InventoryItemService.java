package br.com.becommerce.inventory.manager.api.inventoryitem.service;

import java.util.List;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;

public interface InventoryItemService {

	List<InventoryItem> retrieveAll(Long shoeId);

	InventoryItem retrieve(long id);

	InventoryItem create(InventoryItem inventoryItem);

	InventoryItem update(InventoryItem inventoryItem);

	boolean delete(long id);
}