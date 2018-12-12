package br.com.becommerce.inventory.manager.api.inventoryitem.controller;

import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.AVAILABLE;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.DESCRIPTION;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.RESERVED_QUANTITY;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.SHOE_ID;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.TOTAL_QUANTITY;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.URI;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;
import br.com.becommerce.inventory.manager.api.inventoryitem.service.InventoryItemService;

@RunWith(SpringRunner.class)
@WebMvcTest(InventoryItemController.class)
public class InventoryItemControllerValidationTest {

	private InventoryItem inventoryItem;

	private MockHttpServletRequestBuilder builder;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InventoryItemService mockInventoryItemService;

	@Before
	public void setUp() throws Exception {
		inventoryItem = new InventoryItem();
		inventoryItem.setShoeId(SHOE_ID);
		inventoryItem.setDescription(DESCRIPTION);
		inventoryItem.setTotalQuantity(TOTAL_QUANTITY);
		inventoryItem.setReservedQuantity(RESERVED_QUANTITY);
		inventoryItem.setAvailable(AVAILABLE);

		builder = MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void createOk() throws Exception {
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(inventoryItem)))
				.andExpect(status().isCreated());
	}

	@Test
	public void createDescriptionNull() throws Exception {
		inventoryItem.setDescription(null);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(inventoryItem)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createDescriptionBlank() throws Exception {
		inventoryItem.setDescription("");
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(inventoryItem)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createDescriptionWhitespacesOnly() throws Exception {
		inventoryItem.setDescription(" ");
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(inventoryItem)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createDescriptionGreaterThanMaximum() throws Exception {
		inventoryItem.setDescription(StringUtils.repeat('a', 65));
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(inventoryItem)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createTotalQuantityNegative() throws Exception {
		inventoryItem.setTotalQuantity(-1);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(inventoryItem)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createReservedQuantityNegative() throws Exception {
		inventoryItem.setReservedQuantity(-1);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(inventoryItem)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createReservedQuantityGreaterThanTotalQuantity() throws Exception {
		inventoryItem.setTotalQuantity(1);
		inventoryItem.setReservedQuantity(2);
		mockMvc.perform(builder.content(objectMapper.writeValueAsString(inventoryItem)))
				.andExpect(status().isBadRequest());
	}
}