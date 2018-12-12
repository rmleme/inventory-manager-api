package br.com.becommerce.inventory.manager.api.inventoryitem.model;

import org.junit.Assert;

public class InventoryItemTest {

	public static void assertEquals(InventoryItem expected, InventoryItem actual) {
		if (expected == null) {
			Assert.assertNull(actual);
			return;
		}
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getShoeId(), actual.getShoeId());
		Assert.assertEquals(expected.getDescription(), actual.getDescription());
		Assert.assertEquals(expected.getTotalQuantity(), actual.getTotalQuantity(), 0);
		Assert.assertEquals(expected.getReservedQuantity(), actual.getReservedQuantity());
		Assert.assertEquals(expected.isAvailable(), actual.isAvailable());
	}
}