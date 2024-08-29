const Env = {
    icons : [
        {"url" : "/images/facility_TFT000.png", "id" : "TFT000"},
        {"url" : "/images/facility_TFT001.png", "id" : "TFT001"},
        {"url" : "/images/facility_TFT003.png", "id" : "TFT003"},
        {"url" : "/images/facility_TFT004.png", "id" : "TFT004"},
        {"url" : "/images/facility_TFT005.png", "id" : "TFT005"},
        {"url" : "/images/facility_TFT006.png", "id" : "TFT006"},
        {"url" : "/images/facility_TFT006.png", "id" : "TFT007"},
        {"url" : "/images/facility_TFT008.png", "id" : "TFT008"},
        {"url" : "/images/facility_TFT009.png", "id" : "TFT009"},
        {"url" : "/images/facility_TFT010.png", "id" : "TFT010"},
        {"url" : "/images/facility_TFT011.png", "id" : "TFT011"},
        {"url" : "/images/facility_TFT012.png", "id" : "TFT012"},
        {"url" : "/images/facility_TFT015.png", "id" : "TFT015"},
        {"url" : "/images/facility_TFT016.png", "id" : "TFT016"},
        {"url" : "/images/facility_TFT018.png", "id" : "TFT018"},
        {"url" : "/images/facility_TFT019.png", "id" : "TFT019"},
        { "url" : "/images/equipment_EQP001.png", "id" : "EQT001" },
        {"url" : "/images/organization_TFT900.png", "id" : "TFT900"}
    ],
    source : {
        warning : "MOZ_ATES_WARNING",
        accident : "MOZ_ATES_ACCIDENT",
        facility : "MOZ_ATES_FACILITY",
        equipment : "MOZ_ATES_EQUIPMENT",
        organization : "MOZ_ATES_ORGANIZATION"
    },
    layer : {
        warning : "MOZ_ATES_WARNING",
        accident : "MOZ_ATES_ACCIDENT_LAYER",
        facility : "MOZ_ATES_FACILITY_LAYER",
        equipment : "MOZ_ATES_EQUIPMENT_LAYER",
        organization : "MOZ_ATES_ORGANIZATION_LAYER"
    }
}
/**
 * MozAtes Map Core js
 * @param elementId
 * @param center_lng
 * @param center_lat
 * @param useGeoLocation
 * @param isInitDrawCenterMarker
 * @returns {MozAtesMap}
 * @constructor
 */
const MozAtesMap = function({elementId, center_lng, center_lat, useGeoLocation = false ,isInitDrawCenterMarker}){
    const _core = this;
    const _pbkey = "pk.eyJ1IjoiZGVzaW1pbjIiLCJhIjoiY2xvbzMwN2t3Mm52dzJrcXR6em5lZ3hmMyJ9.pu7IdtCJVHme2QXzu4sT7w";
    let _center = center_lng && center_lat ? [center_lng, center_lat] : [32.609310,-25.907068];
    let _map = null;
    let _userLng = null;
    let _userLat = null;
    let _geoLocationTrigger = useGeoLocation;
    let _geoLocate = null;
    const popup = new mapboxgl.Popup({
        closeButton: false,
        closeOnClick: false,
        maxWidth : "none"
    });
    let loadSource = async function(){
        for(const icon of Env.icons) {
            await _map.loadImage(icon.url, function(err, image){
                if(icon.sdf === true) {
                    _map.addImage(icon.id, image, {sdf : true});
                }else{
                    _map.addImage(icon.id, image);
                }
            });
        }
    }
    
    function handlePermission() {
        navigator.permissions.query({name:'geolocation'}).then(function(result) {
            if (result.state === 'granted') {
                getGelocation();
            } else if (result.state === 'prompt') {
                report(result.state);
                getGelocation();
            } else if (result.state === 'denied') {
                report(result.state);
            }
            result.addEventListener('change', function() {
                report(result.state);
            });
        });
    }
    function report(state) {
        console.log('Permission ' + state);
    }
//	handlePermission();
    // Geolocation API에 액세스할 수 있는지를 확인
    function getGelocation(){
        if (navigator.geolocation) {
            //위치 정보를 얻기
            navigator.geolocation.getCurrentPosition (function(pos) {
                if(typeof pos == "undefined") {
                    alert("Not support geolocation.");
                    return
                }
                _userLat = pos.coords.latitude;
                _userLng = pos.coords.longitude;
                _center = [pos.coords.longitude,pos.coords.latitude];
            });
        } else {
//			alert("Not support geolocation.")
        }
    }
    if(_geoLocationTrigger) getGelocation();

    _core.trigger = function(){
        _geoLocationTrigger = true;
    }
    _core.addGeolocate = function(){
        let geolocate = new mapboxgl.GeolocateControl({
            positionOptions: {
                enableHighAccuracy: true
            },
            trackUserLocation: true,
            showUserHeading: true
        })
        _map.addControl(geolocate, "bottom-right");
        geolocate.on('geolocate', (event) => {
            _userLng = event.coords.longitude;
            _userLat = event.coords.latitude;
        });
        return geolocate;
    }

    _core.init = function(){
        const element = document.getElementById(elementId);
        if(!element) {
            console.error("Not found element.");
            return;
        }
        mapboxgl.accessToken = _pbkey;
        _map = new mapboxgl.Map({
            container: elementId, // container ID
            style: 'mapbox://styles/mapbox/streets-v12', // style URL
            center: _center, // starting position [lng, lat]
            zoom: 11, // starting zoom
        });
        if(_geoLocationTrigger) _geoLocate = _core.addGeolocate();
        loadSource().then();
        _map.on('load', function() {
            // user location tracking
            if(_geoLocationTrigger) _geoLocate?.trigger();
            if(isInitDrawCenterMarker) {
                _core.drawMarker([center_lng, center_lat]);
			}
			
			// 시설물 정보
			_core.facility = new Facility();
			_core.facility.getSource().then(() => {
                _core.facility.drawFacility('TFT000');
                _core.facility.drawFacility('TFT001');
                _core.facility.drawFacility('TFT003');
                _core.facility.drawFacility('TFT004');
                _core.facility.drawFacility('TFT005');
                _core.facility.drawFacility('TFT006');
                _core.facility.drawFacility('TFT007');
                _core.facility.drawFacility('TFT008');
                _core.facility.drawFacility('TFT009');
                _core.facility.drawFacility('TFT010');
                _core.facility.drawFacility('TFT011');
                _core.facility.drawFacility('TFT012');
                _core.facility.drawFacility('TFT015');
                _core.facility.drawFacility('TFT016');
                _core.facility.drawFacility('TFT018');
                _core.facility.drawFacility('TFT019');
			});
			
			_core.accident = new Accident();
			
            _core.equipment = new Equipment();
			_core.equipment.getSource().then(() => {
				_core.equipment.drawEquipment('EQT001');
			});

            // 유관기관
            _core.organization = new Organization();
            _core.organization.getSource().then(() => {
                _core.organization.drawOrganization('TFT900');
            })
		});
    }
    
    _core.drawIcon = function(icon, sourceName,layerName, filter, callback){
		
        if(typeof _map.getLayer(layerName) !== "undefined") return;
        let obj = {
            'id': layerName,
            'type': 'symbol',
            'source': sourceName,
            'maxzoom': 22,
            'minzoom': 9,
            'layout': {
                'visibility' : "visible",
                'icon-allow-overlap': true,
                'icon-image': icon,
                "icon-size": [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    10, 0.3,
                    15, 0.55
                ]
            }
        }
        if(filter){
            obj.filter = filter;
        }
        _map.addLayer(obj);
        if(typeof callback === 'function') typeof callback(obj);
    }
    
    _core.drawMarker = function(lngLat){
        new mapboxgl.Marker({ color: 'red'})
            .setLngLat(lngLat)
            .addTo(_map);
    }
    _core.drawCluster = function(geoJSON,sourceName,layerName,key,pointColor){
        if(_map.getSource(sourceName)){
            _map.getSource(sourceName).setData(geoJSON);
        }else{
            const clusterOption = {
                cluster : true,
                clusterRadius: 35,
                clusterProperties: {
                    'total': ["+", ["get", key]],
                },
                tolerance : 0.5
            }
            let clusterData = Object.assign({
                type : "geojson",
                data : geoJSON
            },clusterOption);
            _map.addSource(sourceName, clusterData);
        }
        if(typeof _map.getLayer(layerName+"_CLUSTER") !== "undefined") return;
        let clusterLayer = {
            id: layerName+"_CLUSTER",
            type: 'circle',
            source: sourceName,
            paint: {
                'circle-color': [
                    'interpolate',
                    ['linear'],
                    ['get', 'total'],
                    0, '#51bbd6',
                    100, '#ec1346',
                ],
                'circle-radius': 35,
                'circle-opacity' : 0.8,
                'circle-stroke-color' : pointColor,
                'circle-stroke-width' : 2
            },
            filter : ['has', 'point_count']
        }
        let clusterTextLayer = {
            id: layerName+"_CLUSTER_TEXT",
            type: 'symbol',
            source: sourceName,
            layout: {
                'text-allow-overlap' : true,
                'text-field': ['number-format',['get', 'total'],{locale:'en'}],
                'text-size': 14
            },
            filter : ['has', 'point_count']
        }
        let unClusterLayer = {
            id: layerName+"_UNCLUSTER",
            type: 'circle',
            source: sourceName,
            paint: {
                'circle-color': [
                    'interpolate',
                    ['linear'],
                    ['get', key],
                    0, '#51bbd6',
                    100, '#ec1346',
                ],
                'circle-radius': 20,
                'circle-opacity' : 0.8,
                'circle-stroke-color' : pointColor,
                'circle-stroke-width' : 2
            },
            'filter': ['!=', 'cluster', true]
        }
        let unclusterTextLayer = {
            id: layerName+"_UNCLUSTER_TEXT",
            type: 'symbol',
            source: sourceName,
            layout: {
                'text-allow-overlap' : true,
                'text-field': ['number-format',['get', key],{locale:'en'}],
                'text-size': 10
            },
            'filter': ['!=', 'cluster', true]
        }

        _map.addLayer(clusterLayer);
        _map.addLayer(clusterTextLayer);
        _map.addLayer(unClusterLayer);
        _map.addLayer(unclusterTextLayer);

    }
    _core.drawHeatmap = function(geoJSON,sourceName,layerName,key){
        if(_map.getSource(sourceName)){
            _map.getSource(sourceName).setData(geoJSON);
        }else{
            _map.addSource(sourceName, {
                type : "geojson",
                data : geoJSON
            });
        }
        if(typeof _map.getLayer(layerName+"_HEATMAP") !== "undefined") return;
        let heatmapLayer = {
            'id': layerName+"_HEATMAP",
            'type': 'heatmap',
            'source': sourceName,
            'paint': {
                'heatmap-weight': [
                    'interpolate',
                    ['linear'],
                    ["get",key],
                    0, 0,
                    10, 1,
                ],
                // 줌 level 강도
                'heatmap-intensity': [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    0,
                    3,
                    9,
                    8
                ],
                // 밀도에 따라 색상값 할당
                'heatmap-color': [
                    'interpolate',
                    ['linear'],
                    ['heatmap-density'],
                    0,
                    'rgba(33,102,172,0)',
                    0.2,
                    'rgb(103,169,207)',
                    0.4,
                    'rgb(209,229,240)',
                    0.6,
                    'rgb(253,219,199)',
                    0.8,
                    'rgb(239,138,98)',
                    1,
                    'rgb(178,24,43)'
                ],
                // 줌에 맞게 크기 변경
                'heatmap-radius':[
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    0,
                    10,
                    9,
                    20
                ],
                // 줌에 맞게 투명도 조절
                'heatmap-opacity': [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    7,
                    1,
                    9,
                    1,
                    13,
                    0.7
                ]
            }
        }
        let circleLayer = {
            'id': layerName+"_HEATMAP_CIRCLE",
            'type': 'circle',
            'source': sourceName,
            'minzoom': 13,
            'paint': {
                'circle-radius': [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    7,
                    ['interpolate', ['linear'], ['get', key], 1, 1, 6, 1],
                    16,
                    ['interpolate', ['linear'], ['get', key], 1, 2, 6, 5]
                ],
                'circle-color': [
                    'interpolate',
                    ['linear'],
                    ['get', key],
                    1,
                    'rgba(33,102,172,0)',
                    2,
                    'rgb(103,169,207)',
                    3,
                    'rgb(209,229,240)',
                    4,
                    'rgb(253,219,199)',
                    5,
                    'rgb(239,138,98)',
                    6,
                    'rgb(178,24,43)'
                ],
                'circle-stroke-color': 'white',
                'circle-stroke-width': 1,
                'circle-opacity': [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    7,
                    0,
                    8,
                    1
                ]
            }
        }
        _map.addLayer(heatmapLayer);
        _map.addLayer(circleLayer);
    }
    _core.getLng = function(){
        return _userLng;
    }

    _core.getLat = function(){
        return _userLat;
    }
    _core.init();
    
	class Facility {
		constructor() {

		}

		async getSource() {
			await fetch("/stat/mng/facMngGeojson.ajax", {})
				.then((response) => {
					return response.json()
				})
				.then((geoJson) => {
					this.geoJson = geoJson;
					if (_map.getSource(Env.source.facility)) {
						_map.getSource(Env.source.facility).setData(geoJson);
					} else {
						_map.addSource(Env.source.facility, {
							type: "geojson",
							data: geoJson
						});
					}
				})
				.catch((err) => {
					console.error(err);
					// alert("An error occurred while retrieving facility information. Please contact the administrator");
				})
		}
		toggleFacilityLayer = function(typeCode) {
			_core.toggleLayer(Env.layer.facility + "_" + typeCode);
		}
		drawFacility = function(typeCode) {
			const _this = this;
			_core.drawIcon(['get', 'CD_ID'], Env.source.facility, Env.layer.facility + "_" + typeCode, ['==', 'CD_ID', typeCode], function(...layerObj) {
				for (const obj of layerObj) {
					_map.off("mouseenter", obj.id, _this.hoverEvent);
					_map.off("mouseleave", obj.id, _this.leaveEvent);
					_map.on("mouseenter", obj.id, _this.hoverEvent);
					_map.on("mouseleave", obj.id, _this.leaveEvent);
				}
			});
		}
		hoverEvent(e) {
			_map.getCanvas().style.cursor = 'pointer';

			// Copy coordinates array.
			const coordinates = e.features[0].geometry.coordinates.slice();
			const prop = e.features[0].properties;


			const html = `
                <div class="mkPopWrap">
                    <dl class="mkCon">
                        <dt class="mkTitle">Tipo</dt>
                        <dd class="mkContents">${prop.CD_NM}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Nome</dt>
                        <dd class="mkContents">${prop.FACILITY_NM}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Estrada</dt>
                        <dd class="mkContents">${prop.ROAD_ADDR}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Status</dt>
                        <dd class="mkContents">${prop.FACILITY_STTS === 'Y' ? 'Activate' : 'Deactivate'}</dd>
                    </dl>
                </div>
            `
			while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
				coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
			}
			


			// Populate the popup and set its coordinates
			// based on the feature found.
			popup.setLngLat(coordinates).setHTML(html).addTo(_map);
		}
		leaveEvent() {
			_map.getCanvas().style.cursor = '';
			popup.remove();
		}
	}

    class Organization{
        constructor() {

        }

        async getSource() {
            await fetch("/stat/mng/orgnzMngGeojson.ajax", {})
                .then((response) => {
                    return response.json()
                })
                .then((geoJson)=>{
                    this.geoJson = geoJson;
                    if(_map.getSource(Env.source.facility)){
                        _map.getSource(Env.source.facility).setData(geoJson);
                    }else{
                        _map.addSource(Env.source.facility, {
                            type : "geojson",
                            data : geoJson
                        });
                    }
                })
                .catch((err)=>{
                    console.error(err);
                    // alert("An error occurred while retrieving facility information. Please contact the administrator");
                })
        }
        toggleOrganizationLayer = function(typeCode) {
            _core.toggleLayer(Env.layer.facility+"_"+typeCode);
        }
        drawOrganization = function(typeCode){
            const _this = this;
            _core.drawIcon(['get','CD_ID'],Env.source.facility,Env.layer.facility+"_"+typeCode, ['==','CD_ID',typeCode], function(...layerObj){
                for(const obj of layerObj){
                    _map.off("mouseenter",obj.id,_this.hoverEvent);
                    _map.off("mouseleave",obj.id,_this.leaveEvent);
                    _map.on("mouseenter",obj.id,_this.hoverEvent);
                    _map.on("mouseleave",obj.id,_this.leaveEvent);
                }
            });
        }
        hoverEvent(e) {
            _map.getCanvas().style.cursor = 'pointer';

            // Copy coordinates array.
            const coordinates = e.features[0].geometry.coordinates.slice();
            const prop = e.features[0].properties;
            const html = `
                <div class="mkPopWrap">
                    <dl class="mkCon">
                        <dt class="mkTitle">Tipo</dt>
                        <dd class="mkContents">${prop.CD_NM}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Nome</dt>
                        <dd class="mkContents">${prop.FACILITY_NM}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Estrada</dt>
                        <dd class="mkContents">${prop.ROAD_ADDR}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Status</dt>
                        <dd class="mkContents">${prop.FACILITY_STTS}</dd>
                    </dl>
                </div>
            `
            while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
                coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
            }

            // Populate the popup and set its coordinates
            // based on the feature found.
            popup.setLngLat(coordinates).setHTML(html).addTo(_map);
        }
        leaveEvent() {
            _map.getCanvas().style.cursor = '';
            popup.remove();
        }
    }
    class Accident{
        startDate = null;
        endDate = null;
        statType = null;
        async getSource(){
            let url = '/stat/accident';
            if(this.startDate && this.endDate){
                url = url+ `?startDate=${this.startDate}&endDate=${this.endDate}&statType=${this.statType}`;
            }
            await fetch(url, {})
                .then((response) => {
                    return response.json()
                })
                .then((geoJson)=>{
                    this.geoJson = geoJson;
                })
                .catch((err)=>{
                    console.error(err);
                    // alert("An error occurred while retrieving accident information. Please contact the administrator");
                    if(_core.accidentInterval) clearInterval(_core.accidentInterval);
                })
        }
        doInterval(time = 10*1000) {
            let _d = this;
            if(_core.accidentInterval) clearInterval(_core.accidentInterval);
            this.getSource().then(()=>{
                _d.drawCluster();
            })
            return _core.accidentInterval = setInterval(()=>{
                this.getSource().then(()=>{
                    _d.drawCluster();
                })
            },time);
        }
        removeData (){
            _core.removeSource(Env.source.accident)
        }
        drawCluster() {
            _core.drawCluster(this.geoJson, Env.source.accident,Env.layer.accident,"ACDNT_CNT", "#f13131");
        }
        drawHeatmap() {
            _core.drawHeatmap(this.geoJson, Env.source.accident,Env.layer.accident,"ACDNT_CNT");
        }
    }
    
    class Equipment{
        constructor() {

        }
        async getSource() {
            await fetch("/stat/mng/eqpMngGeojson.ajax", {})
                .then((response) => {
                    return response.json()
                })
                .then((geoJson)=>{
                    this.geoJson = geoJson;
                    if(_map.getSource(Env.source.equipment)){
                        _map.getSource(Env.source.equipment).setData(geoJson);
                    }else{
                        _map.addSource(Env.source.equipment, {
                            type : "geojson",
                            data : geoJson
                        });
                    }
                })
                .catch((err)=>{
                    console.error(err);
                    // alert("An error occurred while retrieving facility information. Please contact the administrator");
                })
        }
        
        toggleEquipmentLayer = function(typeCode) {
            _core.toggleLayer(Env.layer.equipment+"_"+typeCode);
        }
        
        drawEquipment = function(typeCode){
            const _this = this;
            _core.drawIcon(['get','CD_ID'],Env.source.equipment,Env.layer.equipment+"_"+typeCode, ['==','CD_ID',typeCode], function(...layerObj){
                for(const obj of layerObj){
                    _map.off("mouseenter",obj.id,_this.hoverEvent);
                    _map.off("mouseleave",obj.id,_this.leaveEvent);
                    _map.on("mouseenter",obj.id,_this.hoverEvent);
                    _map.on("mouseleave",obj.id,_this.leaveEvent);
                }
            });
        }
        hoverEvent(e) {
            _map.getCanvas().style.cursor = 'pointer';

            // Copy coordinates array.
            const coordinates = e.features[0].geometry.coordinates.slice();
            const prop = e.features[0].properties;
            

            const html = `
           		<div class="mkPopWrap">
                    <dl class="mkCon">
                        <dt class="mkTitle">Tipo</dt>
                        <dd class="mkContents">${prop.CD_NM}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Nome</dt>
                        <dd class="mkContents">${prop.EQP_NM}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Estrada</dt>
                        <dd class="mkContents">${prop.ROAD_ADDR}</dd>
                    </dl>
                    <dl class="mkCon">
                        <dt class="mkTitle">Status</dt>
                        <dd class="mkContents">${prop.FACILITY_STTS === 'Y' ? 'Activate' : 'Deactivate'}</dd>
                    </dl>
                </div>
            `
            while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
                coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
            }

            // Populate the popup and set its coordinates
            // based on the feature found.
            popup.setLngLat(coordinates).setHTML(html).addTo(_map);
        }
        leaveEvent() {
            _map.getCanvas().style.cursor = '';
            popup.remove();
        }
    }
    
    _core.drawCluster = function(geoJSON,sourceName,layerName,key,pointColor){
        if(_map.getSource(sourceName)){
            _map.getSource(sourceName).setData(geoJSON);
        }else{
            const clusterOption = {
                cluster : true,
                clusterRadius: 35,
                clusterProperties: {
                    'total': ["+", ["get", key]],
                },
                tolerance : 0.5
            }
            let clusterData = Object.assign({
                type : "geojson",
                data : geoJSON
            },clusterOption);
            _map.addSource(sourceName, clusterData);
        }
        if(typeof _map.getLayer(layerName+"_CLUSTER") !== "undefined") return;
        let clusterLayer = {
            id: layerName+"_CLUSTER",
            type: 'circle',
            source: sourceName,
            paint: {
                'circle-color': [
                    'interpolate',
                    ['linear'],
                    ['get', 'total'],
                    0, '#51bbd6',
                    100, '#ec1346',
                ],
                'circle-radius': 35,
                'circle-opacity' : 0.8,
                'circle-stroke-color' : pointColor,
                'circle-stroke-width' : 2
            },
            filter : ['has', 'point_count']
        }
        let clusterTextLayer = {
            id: layerName+"_CLUSTER_TEXT",
            type: 'symbol',
            source: sourceName,
            layout: {
                'text-allow-overlap' : true,
                'text-field': ['number-format',['get', 'total'],{locale:'en'}],
                'text-size': 14
            },
            filter : ['has', 'point_count']
        }
        let unClusterLayer = {
            id: layerName+"_UNCLUSTER",
            type: 'circle',
            source: sourceName,
            paint: {
                'circle-color': [
                    'interpolate',
                    ['linear'],
                    ['get', key],
                    0, '#51bbd6',
                    100, '#ec1346',
                ],
                'circle-radius': 20,
                'circle-opacity' : 0.8,
                'circle-stroke-color' : pointColor,
                'circle-stroke-width' : 2
            },
            'filter': ['!=', 'cluster', true]
        }
        let unclusterTextLayer = {
            id: layerName+"_UNCLUSTER_TEXT",
            type: 'symbol',
            source: sourceName,
            layout: {
                'text-allow-overlap' : true,
                'text-field': ['number-format',['get', key],{locale:'en'}],
                'text-size': 10
            },
            'filter': ['!=', 'cluster', true]
        }

        _map.addLayer(clusterLayer);
        _map.addLayer(clusterTextLayer);
        _map.addLayer(unClusterLayer);
        _map.addLayer(unclusterTextLayer);

    }
    _core.drawHeatmap = function(geoJSON,sourceName,layerName,key){
        if(_map.getSource(sourceName)){
            _map.getSource(sourceName).setData(geoJSON);
        }else{
            _map.addSource(sourceName, {
                type : "geojson",
                data : geoJSON
            });
        }
        if(typeof _map.getLayer(layerName+"_HEATMAP") !== "undefined") return;
        let heatmapLayer = {
            'id': layerName+"_HEATMAP",
            'type': 'heatmap',
            'source': sourceName,
            'paint': {
                'heatmap-weight': [
                    'interpolate',
                    ['linear'],
                    ["get",key],
                    0, 0,
                    10, 1,
                ],
                // 줌 level 강도
                'heatmap-intensity': [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    0,
                    3,
                    9,
                    8
                ],
                // 밀도에 따라 색상값 할당
                'heatmap-color': [
                    'interpolate',
                    ['linear'],
                    ['heatmap-density'],
                    0,
                    'rgba(33,102,172,0)',
                    0.2,
                    'rgb(103,169,207)',
                    0.4,
                    'rgb(209,229,240)',
                    0.6,
                    'rgb(253,219,199)',
                    0.8,
                    'rgb(239,138,98)',
                    1,
                    'rgb(178,24,43)'
                ],
                // 줌에 맞게 크기 변경
                'heatmap-radius':[
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    0,
                    10,
                    9,
                    20
                ],
                // 줌에 맞게 투명도 조절
                'heatmap-opacity': [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    7,
                    1,
                    9,
                    1,
                    13,
                    0.7
                ]
            }
        }
        let circleLayer = {
            'id': layerName+"_HEATMAP_CIRCLE",
            'type': 'circle',
            'source': sourceName,
            'minzoom': 13,
            'paint': {
                'circle-radius': [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    7,
                    ['interpolate', ['linear'], ['get', key], 1, 1, 6, 1],
                    16,
                    ['interpolate', ['linear'], ['get', key], 1, 2, 6, 5]
                ],
                'circle-color': [
                    'interpolate',
                    ['linear'],
                    ['get', key],
                    1,
                    'rgba(33,102,172,0)',
                    2,
                    'rgb(103,169,207)',
                    3,
                    'rgb(209,229,240)',
                    4,
                    'rgb(253,219,199)',
                    5,
                    'rgb(239,138,98)',
                    6,
                    'rgb(178,24,43)'
                ],
                'circle-stroke-color': 'white',
                'circle-stroke-width': 1,
                'circle-opacity': [
                    'interpolate',
                    ['linear'],
                    ['zoom'],
                    7,
                    0,
                    8,
                    1
                ]
            }
        }
        _map.addLayer(heatmapLayer);
        _map.addLayer(circleLayer);
    }
	return _core;
};

