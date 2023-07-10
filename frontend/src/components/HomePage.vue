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

    <v-card-text class="py-0">
      <v-row align="center" no-gutters>
        <v-col class="text-h2" cols="6">
          {{ weatherMonitor.temp[0].fcstValue }}&deg;C
        </v-col>

        <v-col cols="6" class="text-right">
          <v-icon color="error" icon="mdi-weather-hurricane" size="88"></v-icon>
        </v-col>
      </v-row>
    </v-card-text>

    <div class="d-flex py-3 justify-space-between">
      <v-list-item density="compact" prepend-icon="mdi-weather-windy">
        <v-list-item-subtitle>123 km/h</v-list-item-subtitle>
      </v-list-item>

      <v-list-item density="compact" prepend-icon="mdi-weather-pouring">
        <v-list-item-subtitle>48%</v-list-item-subtitle>
      </v-list-item>
    </div>

    <v-expand-transition>
      <div v-if="expand">
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

const { coords, locatedAt, error, resume, pause } = useGeolocation();

export default {
  name: 'HomePage',
  data: () => ({
    labels: { 0: 'SU', 1: 'MO', 2: 'TU', 3: 'WED', 4: 'TH', 5: 'FR', 6: 'SA' },
    expand: false,
    expand2: false,
    time: 0,
    forecast: [
      {
        day: 'Tuesday',
        icon: 'mdi-white-balance-sunny',
        temp: '24\xB0/12\xB0',
      },
      {
        day: 'Wednesday',
        icon: 'mdi-white-balance-sunny',
        temp: '22\xB0/14\xB0',
      },
      { day: 'Thursday', icon: 'mdi-cloud', temp: '25\xB0/15\xB0' },
    ],
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
      sky: [],
      precipitation: [],
    },
  }),
  components: {
    KakaoMap,
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
    currentTemp() {
      return this.weather.temp[0].f;
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
      console.log(this.coords.latitude, this.coords.longitude);
      axios
        .post('http://localhost:9000/api/weather', {
          lat: this.coords.latitude,
          lon: this.coords.longitude,
        })
        .then((res) => {
          this.weather = {
            temp: [],
            sky: [],
            precipitation: [],
          };
          res.data.data.forEach((element) => {
            if (element.category === 'T1H') {
              this.weather.temp.push(JSON.parse(JSON.stringify(element)));
            } else if (element.category === 'SKY') {
              this.weather.sky.push(JSON.parse(JSON.stringify(element)));
            } else if (element.category === 'RN1') {
              this.weather.precipitation.push(
                JSON.parse(JSON.stringify(element))
              );
            }
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
