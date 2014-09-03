/**
 * 
 */
package com.hangfu.foodtruck.webservices.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hangfu.foodtruck.webservices.cache.FoodTruckDTOCache;
import com.hangfu.foodtruck.webservices.cache.FoodTypeCache;
import com.hangfu.foodtruck.webservices.dto.FoodTruckDTO;
import com.hangfu.foodtruck.webservices.util.WebUtil;

/**
 * @author hangfu
 * 
 */
@PrepareForTest({ WebUtil.class })
public class CacheServiceImplTest extends PowerMockTestCase {

	private CacheServiceImpl cacheService;
	private CacheServiceImpl serviceSpy;

	private FoodTruckDTOCache foodTruckDTOCache;

	private FoodTypeCache foodTypeCache;

	@BeforeMethod
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		cacheService = new CacheServiceImpl();
		foodTruckDTOCache = new FoodTruckDTOCache();
		foodTruckDTOCache.setCacheMap(new HashMap<String, FoodTruckDTO>());
		foodTypeCache = new FoodTypeCache();
		foodTypeCache.setCacheMap(new HashMap<String, List<FoodTruckDTO>>());
		cacheService.setFoodTruckDTOCache(foodTruckDTOCache);
		cacheService.setFoodTypeCache(foodTypeCache);
		serviceSpy = Mockito.spy(cacheService);
		PowerMockito.mockStatic(WebUtil.class);

		try {
			Mockito.when(WebUtil.callURL(Mockito.anyString())).thenReturn(buildJsonData());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBuildCache() {
		serviceSpy.buildCache(null, null, null);
		assertEquals(foodTruckDTOCache.size(), 2);
		assertTrue(foodTruckDTOCache.containsIndex("Cupkates Bakery, LLC|50 01ST ST"));
		assertTrue(foodTruckDTOCache.containsIndex("Cheese Gone Wild|400 HOWARD ST"));

		assertEquals(foodTypeCache.size(), 3);
		assertTrue(foodTypeCache.containsIndex("DRINK"));
		assertTrue(foodTypeCache.containsIndex("SANDWICH"));
		assertTrue(foodTypeCache.containsIndex("GRILL"));
		assertEquals(foodTypeCache.get("DRINK").size(), 1);
		assertEquals(foodTypeCache.get("SANDWICH").size(), 2);
		assertEquals(foodTypeCache.get("GRILL").size(), 1);
	}

	private String buildJsonData() {
		return "[{\"location\" : {\"needs_recoding\" : false,\"longitude\" : \"-122.398658184594\",\"latitude\" : \"37.7901490874965\"},\"status\" : \"APPROVED\",\"expirationdate\" : \"2015-03-15T00:00:00\",\"permit\" : \"14MFF-0102\",\"block\" : \"3708\",\"received\" : \"Jun 2 2014 12:23PM\",\"facilitytype\" : \"Truck\",\"blocklot\" : \"3708055\",\"locationdescription\" : \"01ST ST: STEVENSON ST to JESSIE ST (21 - 56)\",\"cnn\" : \"101000\",\"priorpermit\" : \"0\",\"approved\" : \"2014-06-02T15:32:00\",\"schedule\" : \"http://bsm.sfdpw.org/PermitsTracker/reports/report.aspx?title=schedule&report=rptSchedule&params=permit=14MFF-0102&ExportPDF=1&Filename=14MFF-0102_schedule.pdf\",\"address\" : \"50 01ST ST\",\"applicant\" : \"Cupkates Bakery, LLC\",\"lot\" : \"055\",\"fooditems\" : \"Cold Truck: sandwiches: corndogs: tacos: yogurt: snacks: candy: hot and cold drinks\",\"longitude\" : \"-122.398658184604\",\"latitude\" : \"37.7901490737255\",\"objectid\" : \"546631\",\"y\" : \"2115738.283\",\"x\" : \"6013063.33\"}, {\"location\" : {\"needs_recoding\" : false,\"longitude\" : \"-122.395881039325\",\"latitude\" : \"37.7891192214396\"},\"status\" : \"APPROVED\",\"expirationdate\" : \"2015-03-15T00:00:00\",\"permit\" : \"14MFF-0035\",\"block\" : \"3720\",\"received\" : \"Mar 13 2014 12:14PM\",\"facilitytype\" : \"Truck\",\"blocklot\" : \"3720008\",\"locationdescription\" : \"01ST ST: NATOMA ST to HOWARD ST (165 - 199)\",\"cnn\" : \"106000\",\"priorpermit\" : \"0\",\"approved\" : \"2014-03-13T12:25:46\",\"schedule\" : \"http://bsm.sfdpw.org/PermitsTracker/reports/report.aspx?title=schedule&report=rptSchedule&params=permit=14MFF-0035&ExportPDF=1&Filename=14MFF-0035_schedule.pdf\",\"address\" : \"400 HOWARD ST\",\"applicant\" : \"Cheese Gone Wild\",\"lot\" : \"008\",\"fooditems\" : \"Grilled Cheese Sandwiches\",\"longitude\" : \"-122.395881039335\",\"latitude\" : \"37.7891192076677\",\"objectid\" : \"526147\",\"y\" : \"2115347.09492\",\"x\" : \"6013858.05956\"}]";
	}

}
