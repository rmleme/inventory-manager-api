package br.com.becommerce.inventory.manager.api.inventoryitem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

	List<InventoryItem> findByShoeId(long shoeId);
}