/**
 * 
 */
package com.hangfu.foodtruck.webservices.impl;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hangfu.foodtruck.webservices.cache.FoodTruckDTOCache;
import com.hangfu.foodtruck.webservices.cache.FoodTypeCache;
import com.hangfu.foodtruck.webservices.dto.FoodTruckDTO;
import com.hangfu.foodtruck.webservices.enums.FoodType;
import com.hangfu.foodtruck.webservices.enums.OperationResult;
import com.hangfu.foodtruck.webservices.response.FoodTruckResponse;

/**
 * @author hangfu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:implApplicationContext.xml" })
public class FoodTruckServiceImplTest extends AbstractTestNGSpringContextTests {

	private static final String TEST_FOODTRUCK_NAME_1 = "name1";
	private static final String TEST_FOODTRUCK_NAME_2 = "name2";
	private static final String TEST_FOODTRUCK_ADDR_1 = "address1";
	private static final String TEST_FOODTRUCK_ADDR_2 = "address2";
	private static final FoodType TEST_FOOD_TYPE_1 = FoodType.SANDWICH;
	private static final FoodType TEST_FOOD_TYPE_2 = FoodType.CHINESE;
	private static final FoodType TEST_FOOD_TYPE_3 = FoodType.NOODLE;
	private static final FoodTruckDTO TEST_TRUCK_1 = new FoodTruckDTO();
	private static final FoodTruckDTO TEST_TRUCK_2 = new FoodTruckDTO();

	@Autowired
	private FoodTruckServiceImpl foodTruckService;

	private FoodTruckDTOCache foodTruckDTOCache;

	private FoodTypeCache foodTypeCache;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		foodTruckDTOCache = new FoodTruckDTOCache();
		foodTypeCache = new FoodTypeCache();
		foodTruckService.setFoodTruckDTOCache(foodTruckDTOCache);
		foodTruckService.setFoodTypeCache(foodTypeCache);
		foodTruckDTOCache.setCacheMap(new HashMap<String, FoodTruckDTO>());
		foodTypeCache.setCacheMap(new HashMap<String, List<FoodTruckDTO>>());

		TEST_TRUCK_1.setName(TEST_FOODTRUCK_NAME_1);
		TEST_TRUCK_1.setAddress(TEST_FOODTRUCK_ADDR_1);
		TEST_TRUCK_1.setFoodTypes(Arrays.asList(new FoodType[] { TEST_FOOD_TYPE_1, TEST_FOOD_TYPE_2 }));

		TEST_TRUCK_2.setName(TEST_FOODTRUCK_NAME_2);
		TEST_TRUCK_2.setAddress(TEST_FOODTRUCK_ADDR_2);
		TEST_TRUCK_2.setFoodTypes(Arrays.asList(new FoodType[] { TEST_FOOD_TYPE_2, TEST_FOOD_TYPE_3 }));

		foodTruckDTOCache.put(foodTruckDTOCache.getIndex(TEST_FOODTRUCK_NAME_1, TEST_FOODTRUCK_ADDR_1), TEST_TRUCK_1);
		foodTruckDTOCache.put(foodTruckDTOCache.getIndex(TEST_FOODTRUCK_NAME_2, TEST_FOODTRUCK_ADDR_2), TEST_TRUCK_2);

		foodTypeCache.add(TEST_FOOD_TYPE_1.name(), TEST_TRUCK_1);
		foodTypeCache.add(TEST_FOOD_TYPE_2.name(), TEST_TRUCK_1);
		foodTypeCache.add(TEST_FOOD_TYPE_2.name(), TEST_TRUCK_2);
		foodTypeCache.add(TEST_FOOD_TYPE_3.name(), TEST_TRUCK_2);
	}

	@Test
	public void testGetFoodTrucks() {
		FoodTruckResponse response = foodTruckService.getFoodTrucks(null);
		assertEquals(response.getOperationResult(), OperationResult.Success);
		assertEquals(response.getData().size(), 2);

		response = foodTruckService.getFoodTrucks(Arrays.asList(new String[] { TEST_FOOD_TYPE_1.name(),
				TEST_FOOD_TYPE_2.name() }));
		assertEquals(response.getOperationResult(), OperationResult.Success);
		assertEquals(response.getData().size(), 2);

		response = foodTruckService.getFoodTrucks(Arrays.asList(new String[] { TEST_FOOD_TYPE_3.name() }));
		assertEquals(response.getOperationResult(), OperationResult.Success);
		assertEquals(response.getData().size(), 1);

		response = foodTruckService.getFoodTrucks(Arrays.asList(new String[] { TEST_FOOD_TYPE_1.name(),
				TEST_FOOD_TYPE_2.name(), TEST_FOOD_TYPE_3.name() }));
		assertEquals(response.getOperationResult(), OperationResult.Success);
		assertEquals(response.getData().size(), 2);
	}

	@Test
	public void testGetFoodTypes() {
		List<String> response = foodTruckService.getFoodTypes();
		assertEquals(response.size(), FoodType.values().length);
	}
}
