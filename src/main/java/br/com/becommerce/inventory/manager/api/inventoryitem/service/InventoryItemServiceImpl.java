package br.com.becommerce.inventory.manager.api.inventoryitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;
import br.com.becommerce.inventory.manager.api.inventoryitem.repository.InventoryItemRepository;

@Service
public class InventoryItemServiceImpl implements InventoryItemService {

	@Autowired
	private InventoryItemRepository inventoryItemRepository;

	@Override
	public List<InventoryItem> retrieveAll(Long shoeId) {
		if (shoeId == null) {
			return inventoryItemRepository.findAll();
		} else {
			return inventoryItemRepository.findByShoeId(shoeId);
		}
	}

	@Override
	public InventoryItem retrieve(long id) {
		return inventoryItemRepository.findById(id).orElse(null);
	}

	@Override
	public InventoryItem create(InventoryItem inventoryItem) {
		// TODO: invoke product catalog API (through RestTemplate) to verify whether
		// inventoryItem.shoeId exists
		return inventoryItemRepository.save(inventoryItem);
	}

	@Override
	public InventoryItem update(InventoryItem inventoryItem) {
		return inventoryItemRepository.save(inventoryItem);
	}

	@Override
	public boolean delete(long id) {
		try {
			inventoryItemRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
}