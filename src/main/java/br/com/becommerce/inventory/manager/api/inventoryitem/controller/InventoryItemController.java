package br.com.becommerce.inventory.manager.api.inventoryitem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;
import br.com.becommerce.inventory.manager.api.inventoryitem.service.InventoryItemService;

@RestController
@RequestMapping("/v1/api/inventory/items")
public class InventoryItemController {

	@Autowired
	private InventoryItemService inventoryItemService;

	@GetMapping
	public ResponseEntity<List<InventoryItem>> retrieveAll(@RequestParam(required = false) Long shoeId) {
		List<InventoryItem> inventoryitems = inventoryItemService.retrieveAll(shoeId);
		if (inventoryitems == null || inventoryitems.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(inventoryitems);
	}

	@GetMapping("{id}")
	public ResponseEntity<InventoryItem> retrieve(@PathVariable long id) {
		InventoryItem inventoryitem = inventoryItemService.retrieve(id);
		if (inventoryitem == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(inventoryitem);
	}

	@PostMapping
	public ResponseEntity<InventoryItem> create(@Valid @RequestBody InventoryItem inventoryitem,
			UriComponentsBuilder ucBuilder) {
		inventoryItemService.create(inventoryitem);
		return ResponseEntity
				.created(ucBuilder.path("/v1/api/inventory/items/{id}").buildAndExpand(inventoryitem.getId()).toUri())
				.build();
	}

	@PutMapping("{id}")
	public ResponseEntity<InventoryItem> update(@Valid @PathVariable long id,
			@RequestBody InventoryItem inventoryitem) {
		InventoryItem currentInventoryItem = inventoryItemService.retrieve(id);
		if (currentInventoryItem == null) {
			return ResponseEntity.notFound().build();
		}
		currentInventoryItem.setDescription(inventoryitem.getDescription());
		currentInventoryItem.setTotalQuantity(inventoryitem.getTotalQuantity());
		currentInventoryItem.setReservedQuantity(inventoryitem.getReservedQuantity());
		currentInventoryItem.setAvailable(inventoryitem.isAvailable());
		currentInventoryItem = inventoryItemService.update(currentInventoryItem);
		return ResponseEntity.ok(currentInventoryItem);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<InventoryItem> delete(@PathVariable long id) {
		boolean deleted = inventoryItemService.delete(id);
		if (!deleted) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
}