(function($) {

	var map,
		infowindow,
		foodtruckMarkers = [],
		failureCount = 0;


	function initialize() {
		initializeMap();
		initializeWidget();
	}


	// initialize Google map with search box
	function initializeMap() {
		// initialize google map
		var markers = [];
		var center = new google.maps.LatLng(37.7671996,-122.4243821);
		var mapOptions = {
			zoom: 12,
			center: center
		};
		map = new google.maps.Map($('.content #map-canvas').get(0), mapOptions);
		
		// add search box
		var input = $('.content #map-input').get(0);
		map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
		var searchBox = new google.maps.places.SearchBox((input));

		// add handler for search box
		google.maps.event.addListener(searchBox, 'places_changed', function() {
			var places = searchBox.getPlaces();

			if (places.length == 0) {
				return;
			}
			for (var i = 0, marker; marker = markers[i]; i++) {
				marker.setMap(null);
			}
			markers = [];

			var bounds = new google.maps.LatLngBounds();
			for (var i = 0, place; place = places[i]; i++) {
				var marker = new google.maps.Marker({
					map: map,
					name: place.name,
					position: place.geometry.location
				});					
				markers.push(marker);
				bounds.extend(place.geometry.location);

				// set place details
				var contentString = "<h4 class=\"place-name\">" + place.name + "</h4>" + "<p class=\"more-details\">" +
									place.formatted_address + "</p>";
				if (place.formatted_phone_number) {
					contentString = contentString + "<p>" +
									place.formatted_phone_number + "</p>";
				}
				if (place.opening_hours) {
					if (place.opening_hours.open_now) {
						contentString = contentString + "<p class=\"more-details open\">" +
										"Open Now" + "</p>";
					} else {
						contentString = contentString + "<p class=\"more-details close\">" +
										"Close Now" + "</p>";
					}
				}

				// set info window
				google.maps.event.addListener(marker, 'click', function(content, mark, map) {
					return function() {
						if(infowindow) infowindow.close();
						infowindow = new google.maps.InfoWindow({
							content: content,
							maxWidth: 200
						});

						infowindow.open(map, mark);
					};
				}(contentString, marker, map));		
			}

			map.fitBounds(bounds);
		});

		// update bounds with search box
		google.maps.event.addListener(map, 'bounds_changed', function() {
			var bounds = map.getBounds();
			searchBox.setBounds(bounds);
		});
	}


	function initializeWidget() {
		$('.food-type').prop("checked", true);
		$('.all-type').prop("checked", true);

		$('#advanced-search-form input').click(widgetHandler);
	}


	google.maps.event.addDomListener(window, 'load', initialize);


	// get and load foodtruck data
	$.ajax({
		url: "/foodtruck/foodtruck",
		type: "GET",
		success: loadFoodtruckData
	});

	function loadFoodtruckData(data) {
		// fail over
		if ((failureCount === 0) && data.result.operationResult === "Failure") {
			failureCount++;
			$.ajax({
				url: "/foodtruck/foodtruck",
				type: "GET",
				success: loadFoodtruckData
			});
		}

		var foodtruckData = data.result.data;

		if(!foodtruckData)
			return;

		for (var i = 0, marker; marker = foodtruckMarkers[i]; i++) {
			marker.setMap(null);
		}
		foodtruckMarkers = [];

		var bounds = new google.maps.LatLngBounds();
		for (var i=0; i<foodtruckData.length; i++) {
			var foodtruckPos = new google.maps.LatLng(foodtruckData[i].latitude, foodtruckData[i].longitude);
			var foodtruckMarker = new google.maps.Marker({
				map: map,
				position: foodtruckPos,
				icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
			});
			foodtruckMarkers.push(foodtruckMarker);
			bounds.extend(foodtruckPos);

			// set place details
			var contentString = "<h4 class=\"place-name\">" + foodtruckData[i].name + "</h4>" + 
								"<p class=\"more-details\">" + foodtruckData[i].address + "</p>" + 
								"<p class=\"more-details\">" + foodtruckData[i].foodItems + "</p>" + 
								"<a href=\"" + foodtruckData[i].schedule + "\">schedule</a>";
			
			// set info window
			google.maps.event.addListener(foodtruckMarker, 'click', function(content, mark, map) {
				return function() {
					if(infowindow) infowindow.close();
					infowindow = new google.maps.InfoWindow({
						content: content,
						maxWidth: 200
					});

					infowindow.open(map, mark);
				};
			}(contentString, foodtruckMarker, map));
		}
	}
		

	// widget click handler
	function widgetHandler(event) {
		var types = $('.food-type'),
			query = '',
			allChecked = true,
			i;

		// clicked on food types
		if(event.target.className === 'food-type') {
			for (i=0; i<types.length; i++) {
				if(types.get(i).checked) {
					if(query !== '') {
						query = query + '&type=' + types.get(i).value;
					} else {
						query = types.get(i).value;
					}
				} else {
					allChecked = false;
				}
			}

			if(allChecked) {
				$('.all-type').prop("checked", true);
			} else {
				$('.all-type').prop("checked", false);
			}

		} else if($('.all-type').prop("checked")) {
			// check all types
			$('.food-type').prop("checked", true);

			for (i=0; i<types.length; i++) {
				if(i === 0) {
					query = types.get(i).value;
				} else {
					query = query + '&type=' + types.get(i).value;
				}
			}

		} else if(!$('.all-type').prop("checked")) {
			// uncheck all types
			$('.food-type').prop("checked", false);
		}

		// request data if query is not null
		if (query === '') {
			var marker;
			for (i = 0, marker; marker = foodtruckMarkers[i]; i++) {
				marker.setMap(null);
			}
		} else {
			query = "/foodtruck/foodtruck?type=" + query;

			$.ajax({
				url: query,
				type: "GET",
				success: loadFoodtruckData
			});
		}
	}
	
}($));