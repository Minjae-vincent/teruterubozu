<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h3>Geolocation</h3>
    <p v-if="error">Error: {{ error }}</p>
    <p v-else-if="locatedAt">Located at: {{ new Date(locatedAt) }}</p>
    <p v-else>Locating...</p>
    <p>Latitude: {{ coords.latitude }}</p>
    <p>Longitude: {{ coords.longitude }}</p>
  </div>
  <KakaoMap :lat="coords.latitude" :lon="coords.longitude" />
</template>

<script>
import { useGeolocation } from '@vueuse/core';
import KakaoMap from './KakaoMap.vue';

const { coords, locatedAt, error, resume, pause } = useGeolocation();

export default {
  name: 'HelloWorld',

  components: {
    KakaoMap,
  },

  props: {
    msg: String,
  },
  setup() {
    return { coords, locatedAt, error, resume, pause };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
