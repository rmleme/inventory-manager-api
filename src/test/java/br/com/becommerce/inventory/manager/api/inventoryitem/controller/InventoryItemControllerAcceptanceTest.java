package br.com.becommerce.inventory.manager.api.inventoryitem.controller;

import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.AVAILABLE;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.DESCRIPTION;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.RESERVED_QUANTITY;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.SHOE_ID;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.TOTAL_QUANTITY;
import static br.com.becommerce.inventory.manager.api.inventoryitem.model.Constants.URI;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.becommerce.inventory.manager.api.Application;
import br.com.becommerce.inventory.manager.api.inventoryitem.model.InventoryItem;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = { InventoryItemControllerAcceptanceTest.Initializer.class })
public class InventoryItemControllerAcceptanceTest {

	@ClassRule
	public static MySQLContainer mysql = new MySQLContainer();

	private InventoryItem inventoryItem;

	private HttpHeaders headers;

	private String serverUrl;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int serverPort;

	@Before
	public void setUp() throws Exception {
		inventoryItem = new InventoryItem();
		inventoryItem.setShoeId(SHOE_ID);
		inventoryItem.setDescription(DESCRIPTION);
		inventoryItem.setTotalQuantity(TOTAL_QUANTITY);
		inventoryItem.setReservedQuantity(RESERVED_QUANTITY);
		inventoryItem.setAvailable(AVAILABLE);

		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		serverUrl = "http://localhost:" + serverPort + URI;
	}

	@Test
	public void testCrud() throws Exception {
		// Create
		createInventoryItem();

		// Retrieve
		ResponseEntity<String> resultGet = restTemplate.getForEntity(new URI(serverUrl), String.class);
		List<InventoryItem> inventoryItems = objectMapper.readValue(resultGet.getBody(),
				new TypeReference<List<InventoryItem>>() {
				});
		assertEquals(HttpStatus.OK.value(), resultGet.getStatusCodeValue());
		assertEquals(1, inventoryItems.size());

		// Update
		inventoryItems.get(0).setDescription("anotherDescription");
		HttpEntity<InventoryItem> request = new HttpEntity<>(inventoryItems.get(0), headers);
		ResponseEntity<InventoryItem> resultPut = restTemplate.exchange(serverUrl + "/{id}", HttpMethod.PUT, request,
				InventoryItem.class, inventoryItems.get(0).getId());
		assertEquals(HttpStatus.OK.value(), resultPut.getStatusCodeValue());
		assertEquals(inventoryItems.get(0).getDescription(), resultPut.getBody().getDescription());

		// Delete
		ResponseEntity<String> resultDelete = restTemplate.exchange(serverUrl + "/{id}", HttpMethod.DELETE, null,
				String.class, inventoryItems.get(0).getId());
		assertEquals(HttpStatus.NO_CONTENT.value(), resultDelete.getStatusCodeValue());
	}

	@Test
	public void testRetrieveAllFilteredByShoeId() throws Exception {
		createInventoryItem();
		createInventoryItem();
		inventoryItem.setShoeId(SHOE_ID + 1);
		createInventoryItem();

		ResponseEntity<String> resultGet = restTemplate.getForEntity(new URI(serverUrl + "?shoeId=" + SHOE_ID),
				String.class);
		List<InventoryItem> inventoryItems = objectMapper.readValue(resultGet.getBody(),
				new TypeReference<List<InventoryItem>>() {
				});
		assertEquals(HttpStatus.OK.value(), resultGet.getStatusCodeValue());
		assertEquals(2, inventoryItems.size());
		assertEquals(SHOE_ID, inventoryItems.get(0).getShoeId());
		assertEquals(SHOE_ID, inventoryItems.get(1).getShoeId());
	}

	private void createInventoryItem() throws Exception {
		HttpEntity<InventoryItem> request = new HttpEntity<>(inventoryItem, headers);
		ResponseEntity<String> resultPost = restTemplate.postForEntity(new URI(serverUrl), request, String.class);
		assertEquals(HttpStatus.CREATED.value(), resultPost.getStatusCodeValue());
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues
					.of("spring.datasource.url=" + mysql.getJdbcUrl(),
							"spring.datasource.username=" + mysql.getUsername(),
							"spring.datasource.password=" + mysql.getPassword())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}