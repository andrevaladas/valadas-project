<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Google Maps JavaScript API v3</title>
		<link href="https://developers.google.com/maps/documentation/javascript/examples/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript">
      		var script = '<script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/infobubble/src/infobubble';
      		if (document.location.search.indexOf('compiled') !== -1) {
        		script += '-compiled';
     		}
      		script += '.js"><' + '/script>';
      		document.write(script);
    	</script>
		<script type="text/javascript">
			var mapCenter;
			var $deviceNames = [];
			var $locationTimeline = [];
			var locations = [];
			var map;
			var markers = [];
			var poly;
			var iterator = 0;
			var infoBubble;
			var showInfo = false;
			var showRoute = false;

			$(document).ready(function(){
			    <c:forEach items="${locationList}" var="location" varStatus="status">
					$deviceNames.push('${location.device.name}');
					$locationTimeline.push('${location.timeline}');
					locations.push(new google.maps.LatLng(${location.latitude}, ${location.longitude}));
					<c:if test="${status.first}">mapCenter = new google.maps.LatLng(${location.latitude}, ${location.longitude})</c:if>
				</c:forEach>

				initialize();

				setTimeout(function() {
					drop();
		    	}, 5000);
			});

			function initialize() {
				var mapOptions = {
				    zoom: 13,
				    mapTypeId: google.maps.MapTypeId.ROADMAP,
				    center: mapCenter
			  	};
			  	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

				poly = new google.maps.Polyline({
					path: [],
					strokeColor: "#FF0000",
					strokeOpacity: 1.0,
					strokeWeight: 2
                });
				poly.setMap(map);
			}

			function drop() {
				for (var i = 0; i < locations.length; i++) {
			    	setTimeout(function() {
			      		addMarker();
			    	}, i * 500);
				}
			}
			
			function addMarker() {
				var $marker = new google.maps.Marker({
			    	position: locations[iterator],
			    	title: $deviceNames[iterator]+'\n'+$locationTimeline[iterator],
			    	map: map,
			    	draggable: false,
			    	animation: google.maps.Animation.DROP
			  	});
				markers.push($marker);
				
				if (iterator == 0) {
					setTimeout(function() {
						toggleBounce($marker);
			    	}, 5000);
				}

				//show route
				if (showRoute) {
					setTimeout(function() {
						var path = poly.getPath();
						path.push(locations[iterator]);
			    	}, 500);
				}

				google.maps.event.addListener($marker, 'click', function(event) {
					toggleBounce(this);

					//show info
					if (showInfo) {
						var content = "<div><b>"+this.title+"<b/></div>";
						infoBubble = new InfoBubble({
					          map: map,
					          content: content,
					          position: event.latLng,
					          shadowStyle: 0,
					          padding: 10,
					          borderRadius: 10,
					          borderWidth: 1,
					          borderColor: '#ccc',
					          backgroundColor: '#fff',
					          maxWidth: 200,
					          maxHeight: 50,
					          arrowSize: 10,
					          arrowPosition: 50,
					          disableAutoPan: true,
					          arrowStyle: 2
					        });
						infoBubble.open();
					}
				});
			  	iterator++;
			}
			function toggleBounce(marker) {
				if (marker.getAnimation() != null) {
				    marker.setAnimation(null);
				} else {
				    marker.setAnimation(google.maps.Animation.BOUNCE);
				}
			}
		</script>
	</head>
	<body>
		<div id="map_canvas" style="margin: 0; padding: 0; height: 100%;"></div>
	</body>
</html>
