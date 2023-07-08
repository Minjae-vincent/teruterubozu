<template>
  <div>
    <h2>카카오 맵 보기</h2>
    <div id="map"></div>
    <v-btn @click="getWeather">날씨 가져오기</v-btn>
  </div>
</template>
<style scoped>
#map {
  width: 50%;
  height: 400px;
  margin: 0 auto;
  text-align: center;
}
</style>
<script>
import axios from 'axios';

export default {
  name: 'KakaoMap',
  props: {
    lat: Number,
    lon: Number,
  },
  data() {
    return {
      map: null,
    };
  },
  setup() {},
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  unmounted() {},
  methods: {
    loadScript() {
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
        level: 1,
      };

      this.map = new window.kakao.maps.Map(container, options);
      this.loadMaker();
    },
    loadMaker() {
      const markerPosition = new window.kakao.maps.LatLng(this.lat, this.lon);

      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
      });

      marker.setMap(this.map);
    },
    getWeather() {
      axios
        .post('http://localhost:9000/api/weather', {
          lat: this.lat,
          lon: this.lon,
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
