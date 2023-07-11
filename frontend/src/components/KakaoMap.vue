<template>
  <div>
    <div id="map"></div>
    <br />
    <br />
  </div>
</template>
<style scoped>
#map {
  width: 90%;
  height: 400px;
  margin: 0 auto;
  text-align: center;
  box-shadow: 0px 0px 3px 3px gray;
}
</style>
<script>
export default {
  name: 'KakaoMap',
  props: {
    lat: Number,
    lon: Number,
  },
  lon: Number,
  data() {
    return {
      map: null,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    loadScript() {
      console.log(this.lat, this.lon);
      const script = document.createElement('script');
      script.src =
        '//dapi.kakao.com/v2/maps/sdk.js?appkey=f4629327dac89bf72282190258ee9716&autoload=false';
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    loadMap() {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(this.lat, this.lon),
        // center: new window.kakao.maps.LatLng(37.3018506, 126.8386982),
        level: 1,
      };

      this.map = new window.kakao.maps.Map(container, options);
      this.loadMaker();
    },
    loadMaker() {
      const markerPosition = new window.kakao.maps.LatLng(this.lat, this.lon);
      // const markerPosition = new window.kakao.maps.LatLng(
      //   37.3018506,
      //   126.8386982
      // );

      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
      });

      marker.setMap(this.map);
    },
  },
};
</script>
