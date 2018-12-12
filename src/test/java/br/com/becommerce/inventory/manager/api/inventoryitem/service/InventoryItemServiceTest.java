package br.com.becommerce.inventory.manager.api.inventoryitem.service;

import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.SHOE_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;
import br.com.becommerce.inventory.manager.api.inventoryitem.repository.InventoryItemRepository;

@RunWith(MockitoJUnitRunner.class)
public class InventoryItemServiceTest {

	@Mock
	private InventoryItemRepository mockInventoryItemRepository;

	@InjectMocks
	private InventoryItemServiceImpl service;

	@Before
	public void setUp() {
	}

	@Test
	public void retrieveAllNullShoeId() {
		List<InventoryItem> inventoryItems = new ArrayList<>();
		when(mockInventoryItemRepository.findAll()).thenReturn(inventoryItems);

		assertEquals(inventoryItems, service.retrieveAll(null));

		verify(mockInventoryItemRepository).findAll();
	}

	@Test
	public void retrieveAllNonNullShoeId() {
		List<InventoryItem> inventoryItems = new ArrayList<>();
		when(mockInventoryItemRepository.findByShoeId(SHOE_ID)).thenReturn(inventoryItems);

		assertEquals(inventoryItems, service.retrieveAll(SHOE_ID));

		verify(mockInventoryItemRepository).findByShoeId(SHOE_ID);
	}
}