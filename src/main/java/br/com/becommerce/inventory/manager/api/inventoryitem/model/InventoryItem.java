package br.com.becommerce.inventory.manager.api.inventoryitem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.validation.ValidQuantities;

@Entity
@ValidQuantities
public class InventoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long shoeId;

	@NotBlank
	@Size(max = 64)
	private String description;

	@PositiveOrZero
	private int totalQuantity;

	@PositiveOrZero
	private int reservedQuantity;

	private Boolean available;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getShoeId() {
		return shoeId;
	}

	public void setShoeId(long shoeId) {
		this.shoeId = shoeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getReservedQuantity() {
		return reservedQuantity;
	}

	public void setReservedQuantity(int reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}