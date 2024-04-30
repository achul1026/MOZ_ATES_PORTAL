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
        _map.on('load', function() {
            // user location tracking
            if(_geoLocationTrigger) _geoLocate?.trigger();
            if(isInitDrawCenterMarker) {
                _core.drawMarker([center_lng, center_lat]);
            }
        });
    }

    _core.drawMarker = function(lngLat){
        new mapboxgl.Marker({ color: 'red'})
            .setLngLat(lngLat)
            .addTo(_map);
    }

    _core.getLng = function(){
        return _userLng;
    }

    _core.getLat = function(){
        return _userLat;
    }
    _core.init();
    return _core;
};