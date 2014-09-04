(function($) {

	var map;
	var infowindow;
	var foodtruckMarkers = [];

	$.ajax({
		url: "/foodtruck/foodtruck/foodtruck",
		type: "GET",
		success: load
	});

	function load(data) {

		var markers = [];

		var center = new google.maps.LatLng(37.7671996,-122.4243821);
		var mapOptions = {
			zoom: 12,
			center: center
		};
		map = new google.maps.Map($('.content #map-canvas').get(0), mapOptions);

		var bounds = new google.maps.LatLngBounds();
		
		var foodtruckData = data.result.data;
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
								"<p class=\"more-details\">" + foodtruckData[i].foodTypes + "</p>" + 
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


		var input = $('.content #map-input').get(0);
		map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
		sesarchBox = new google.maps.places.SearchBox((input));

		google.maps.event.addListener(sesarchBox, 'places_changed', function() {
			var places = sesarchBox.getPlaces();

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

		google.maps.event.addListener(map, 'bounds_changed', function() {
			var bounds = map.getBounds();
			sesarchBox.setBounds(bounds);
		});


		var types = $('.food-type');
		$('.food-type').prop("checked", true);
		$('.all-type').prop("checked", true);

		types.click(function() {
			var query = '';
			var allChecked = true;

			for (var i=0; i<types.length; i++) {
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

			if(query !== '') {
				query = "/foodtruck/foodtruck/foodtruck/?type=" + query;
				
				$.ajax({
					url: query,
					type: "GET",
					success: updateFoodtruckMarkers
				});
			} else {
				for (var i = 0, marker; marker = foodtruckMarkers[i]; i++) {
					marker.setMap(null);
				}
			}


			
		});

		function updateFoodtruckMarkers(data) {
			for (var i = 0, marker; marker = foodtruckMarkers[i]; i++) {
				marker.setMap(null);
			}
			foodtruckMarkers = [];

			var foodtruckData = data.result.data;

			for (var i=0; i<foodtruckData.length; i++) {
				var foodtruckPos = new google.maps.LatLng(foodtruckData[i].latitude, foodtruckData[i].longitude);
				var foodtruckMarker = new google.maps.Marker({
					map: map,
					position: foodtruckPos,
					icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
				});
				foodtruckMarkers.push(foodtruckMarker);

				// set place details
				var contentString = "<h4 class=\"place-name\">" + foodtruckData[i].name + "</h4>" + 
									"<p class=\"more-details\">" + foodtruckData[i].address + "</p>" + 
									"<p class=\"more-details\">" + foodtruckData[i].foodTypes + "</p>" + 
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



		$('.all-type').click(function() {
			var query = '';

			if ($('.all-type').prop("checked")) {
				$('.food-type').prop("checked", true);

				for (var i=0; i<types.length; i++) {
					if (i === types.length-1) {
						query = query + types.get(i).value;
					} else {
						query = query + types.get(i).value + "&type=";
					}
				}	
			} else {
				$('.food-type').prop("checked", false);
			}

			if (query !== '') {
				query = "/foodtruck/foodtruck/foodtruck/?type=" + query;
				
				$.ajax({
					url: query,
					type: "GET",
					success: updateFoodtruckMarkers
				});
			} else {
				for (var i = 0, marker; marker = foodtruckMarkers[i]; i++) {
					marker.setMap(null);
				}
			}


		});

	}
	


	
}($));