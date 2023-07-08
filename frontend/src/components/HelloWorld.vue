<template>
  <div class="hello">
    <h1>Geolocation</h1>
    <p v-if="error">Error: {{ error }}</p>
    <p v-else-if="locatedAt">Located at: {{ new Date(locatedAt) }}</p>
    <p v-else>Locating...</p>
    <p>Latitude: {{ coords.latitude }} / Longitude: {{ coords.longitude }}</p>
  </div>
  <KakaoMap v-if="locatedAt" :lat="coords.latitude" :lon="coords.longitude" />
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
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
