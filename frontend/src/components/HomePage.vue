<template>
  <v-card class="mx-auto" max-width="50%">
    <div v-if="locatedAt">
      <v-card-item title="TERU TERU BOZU 👻">
        <template v-slot:subtitle>
          {{ new Date(locatedAt) }} <br />
          {{ LatLonText }}
        </template>
      </v-card-item>
    </div>
    <div v-else>
      <v-card-item title="Locating..."> </v-card-item>
    </div>
    <KakaoMap v-if="locatedAt" :lat="coords.latitude" :lon="coords.longitude" />
    <!-- <KakaoMap :lat="37.2962453" :lon="126.8389087" /> -->

    <v-card-text class="py-0">
      <v-row align="center" no-gutters>
        <!-- 온도 -->
        <v-col class="text-h2" cols="6">
          {{ weatherMonitor.temp[0].fcstValue }}&deg;C
        </v-col>

        <!-- 구름상태-->
        <v-col
          v-if="weatherMonitor.sky[0].fcstValue == 1"
          cols="6"
          class="text-right"
        >
          <v-icon color="error" icon="mdi-weather-sunny" size="88"></v-icon>
        </v-col>

        <v-col
          v-else-if="
            weatherMonitor.sky[0].fcstValue == 4 &&
            weatherMonitor.pty[0].fcstValue > 0
          "
          cols="6"
          class="text-right"
        >
          <v-icon color="error" icon="mdi-weather-pouring" size="88"></v-icon>
        </v-col>

        <v-col
          v-else-if="
            weatherMonitor.sky[0].fcstValue == 3 ||
            weatherMonitor.sky[0].fcstValue == 4
          "
          cols="6"
          class="text-right"
        >
          <v-icon color="error" icon="mdi-weather-cloudy" size="88"></v-icon>
        </v-col>
      </v-row>
    </v-card-text>

    <div class="d-flex py-3 justify-space-between">
      <!-- 풍속 -->
      <v-list-item density="compact" prepend-icon="mdi-weather-windy">
        <v-list-item-subtitle
          >{{ weatherMonitor.wsd[0].fcstValue }} m/s</v-list-item-subtitle
        >
      </v-list-item>

      <!-- 강수량 -->
      <v-list-item density="compact" prepend-icon="mdi-water-check">
        <v-list-item-subtitle
          >{{ weatherMonitor.pty[0].fcstValue }} mm</v-list-item-subtitle
        >
      </v-list-item>
    </div>

    <v-expand-transition>
      <div v-if="expand && locatedAt">
        <LineChart :weatherData="weatherMonitor.temp" :flag="'Temperature'" />
        <LineChart :weatherData="weatherMonitor.pty" :flag="'Precipitation'" />
      </div>
    </v-expand-transition>

    <v-divider></v-divider>

    <v-card-actions>
      <v-btn @click="expand = !expand">
        {{ !expand ? 'Full Report' : 'Hide Report' }}
      </v-btn>
    </v-card-actions>
    <!-- <v-expand-transition>
      <div v-if="expand2">
        <div class="py-2">
          <v-slider
            v-model="time"
            :max="6"
            :step="1"
            :ticks="labels"
            class="mx-4"
            color="primary"
            density="compact"
            hide-details
            show-ticks="always"
            thumb-size="10"
          ></v-slider>
        </div>

        <v-list class="bg-transparent">
          <v-list-item
            v-for="item in forecast"
            :key="item.day"
            :title="item.day"
            :append-icon="item.icon"
            :subtitle="item.temp"
          >
          </v-list-item>
        </v-list>
      </div>
    </v-expand-transition>

    <v-card-actions>
      <v-btn @click="expand2 = !expand2">
        {{ !expand2 ? 'test1' : 'test2' }}
      </v-btn>
    </v-card-actions> -->
  </v-card>
</template>

<script>
import { useGeolocation } from '@vueuse/core';
import KakaoMap from './KakaoMap.vue';
import axios from 'axios';
import LineChart from './LineChart.vue';

const { coords, locatedAt, error, resume, pause } = useGeolocation();

export default {
  name: 'HomePage',
  data: () => ({
    expand: false,
    expand2: false,
    weather: {
      temp: [
        {
          category: '',
          fcstDate: '',
          fcstTime: '',
          fcstValue: '',
          nx: '',
          ny: '',
        },
      ],
      sky: [
        {
          category: '',
          fcstDate: '',
          fcstTime: '',
          fcstValue: '',
          nx: '',
          ny: '',
        },
      ],
      wsd: [
        {
          category: '',
          fcstDate: '',
          fcstTime: '',
          fcstValue: '',
          nx: '',
          ny: '',
        },
      ],
      pty: [
        {
          category: '',
          fcstDate: '',
          fcstTime: '',
          fcstValue: '',
          nx: '',
          ny: '',
        },
      ],
    },
  }),
  components: {
    KakaoMap,
    LineChart,
  },
  computed: {
    LatLonText() {
      return (
        'Latitude: ' +
        this.coords.latitude +
        ' / ' +
        'Longitude: ' +
        this.coords.longitude
      );
    },
    weatherMonitor() {
      return this.weather;
    },
  },
  watch: {
    coords: {
      handler: function (val) {
        if (val) {
          this.getWeather();
        }
      },
    },
  },
  setup() {
    return { coords, locatedAt, error, resume, pause };
  },
  methods: {
    getWeather() {
      axios
        .post('http://localhost:9000/api/weather', {
          lat: this.coords.latitude,
          lon: this.coords.longitude,
        })
        .then((res) => {
          if (res.data.data.length != 0) {
            this.weather = {
              temp: [],
              sky: [],
              wsd: [],
              pty: [],
            };
            res.data.data.forEach((element) => {
              if (element.category === 'T1H') {
                this.weather.temp.push(JSON.parse(JSON.stringify(element)));
              } else if (element.category === 'SKY') {
                this.weather.sky.push(JSON.parse(JSON.stringify(element)));
              } else if (element.category === 'WSD') {
                this.weather.wsd.push(JSON.parse(JSON.stringify(element)));
              } else if (element.category === 'PTY') {
                this.weather.pty.push(JSON.parse(JSON.stringify(element)));
              }
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
