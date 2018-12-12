package br.com.becommerce.inventory.manager.api.inventoryitem.controller;

import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.AVAILABLE;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.DESCRIPTION;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.ID;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.RESERVED_QUANTITY;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.SHOE_ID;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.TOTAL_QUANTITY;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.URI;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;
import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItemTest;
import br.com.becommerce.inventory.manager.api.inventoryitem.service.InventoryItemService;

@RunWith(MockitoJUnitRunner.class)
public class InventoryItemControllerTest {

	private InventoryItem inventoryItem;

	@Mock
	private InventoryItemService mockInventoryItemService;

	@InjectMocks
	private InventoryItemController controller;

	@Before
	public void setUp() throws Exception {
		inventoryItem = new InventoryItem();
		inventoryItem.setId(ID);
		inventoryItem.setShoeId(SHOE_ID);
		inventoryItem.setDescription(DESCRIPTION);
		inventoryItem.setTotalQuantity(TOTAL_QUANTITY);
		inventoryItem.setReservedQuantity(RESERVED_QUANTITY);
		inventoryItem.setAvailable(AVAILABLE);
	}

	@Test
	public void retrieveAllOk() throws Exception {
		List<InventoryItem> inventoryitems = Collections.singletonList(inventoryItem);
		when(mockInventoryItemService.retrieveAll(null)).thenReturn(inventoryitems);

		assertEquals(ResponseEntity.ok(inventoryitems), controller.retrieveAll(null));

		verify(mockInventoryItemService).retrieveAll(null);
	}

	@Test
	public void retrieveAllNoContentsNull() throws Exception {
		when(mockInventoryItemService.retrieveAll(null)).thenReturn(null);

		assertEquals(ResponseEntity.noContent().build(), controller.retrieveAll(null));

		verify(mockInventoryItemService).retrieveAll(null);
	}

	@Test
	public void retrieveAllNoContentsEmpty() throws Exception {
		when(mockInventoryItemService.retrieveAll(null)).thenReturn(new ArrayList<>());

		assertEquals(ResponseEntity.noContent().build(), controller.retrieveAll(null));

		verify(mockInventoryItemService).retrieveAll(null);
	}

	@Test
	public void retrieveOk() throws Exception {
		when(mockInventoryItemService.retrieve(ID)).thenReturn(inventoryItem);

		assertEquals(ResponseEntity.ok(inventoryItem), controller.retrieve(ID));

		verify(mockInventoryItemService).retrieve(ID);
	}

	@Test
	public void retrieveNoContents() throws Exception {
		when(mockInventoryItemService.retrieve(ID)).thenReturn(null);

		assertEquals(ResponseEntity.notFound().build(), controller.retrieve(ID));

		verify(mockInventoryItemService).retrieve(ID);
	}

	@Test
	public void createOk() throws Exception {
		when(mockInventoryItemService.create(inventoryItem)).thenReturn(inventoryItem);

		assertEquals(ResponseEntity.created(new URI(URI + "/" + ID)).build(),
				controller.create(inventoryItem, UriComponentsBuilder.newInstance()));

		verify(mockInventoryItemService).create(inventoryItem);
	}

	@Test
	public void updateOk() throws Exception {
		when(mockInventoryItemService.retrieve(ID)).thenReturn(inventoryItem);
		when(mockInventoryItemService.update(inventoryItem)).thenReturn(inventoryItem);

		ResponseEntity<InventoryItem> result = controller.update(ID, inventoryItem);

		assertEquals(ResponseEntity.ok(inventoryItem), result);
		InventoryItemTest.assertEquals(inventoryItem, result.getBody());

		verify(mockInventoryItemService).retrieve(ID);
		verify(mockInventoryItemService).update(inventoryItem);
	}

	@Test
	public void updateInventoryItemNotFound() throws Exception {
		when(mockInventoryItemService.retrieve(ID)).thenReturn(null);

		assertEquals(ResponseEntity.notFound().build(), controller.update(ID, inventoryItem));

		verify(mockInventoryItemService).retrieve(ID);
		verify(mockInventoryItemService, never()).update(inventoryItem);
	}

	@Test
	public void deleteOk() throws Exception {
		when(mockInventoryItemService.delete(ID)).thenReturn(true);

		assertEquals(ResponseEntity.noContent().build(), controller.delete(ID));

		verify(mockInventoryItemService).delete(ID);
	}

	@Test
	public void deleteInventoryItemNotFound() throws Exception {
		when(mockInventoryItemService.delete(ID)).thenReturn(false);

		assertEquals(ResponseEntity.notFound().build(), controller.delete(ID));

		verify(mockInventoryItemService).delete(ID);
	}
}