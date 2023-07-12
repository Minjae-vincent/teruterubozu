<template>
  <div id="test">
    <Line :data="chartData" :options="chartOptions" />
  </div>
</template>

<script>
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Line } from 'vue-chartjs';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export default {
  name: 'TmpComponent',
  props: {
    weatherData: Object,
    flag: String,
  },
  components: {
    Line,
  },
  data() {
    return {
      chartData: {
        labels: [],
        datasets: [
          {
            label: this.flag,
            backgroundColor:
              this.flag === 'Temperature' ? '#f87979' : '#5882FA',
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: true,
        scales: {
          y: {
            title: {
              display: true,
              text: this.flag,
            },
            beginAtZero: this.flag === 'Temperature' ? false : true,
          },
          x: {
            title: {
              display: true,
              text: 'Time',
            },
          },
        },
      },
    };
  },
  beforeMount() {
    for (let a of this.weatherData) {
      this.chartData.labels.push(a.fcstDate + a.fcstTime);
      this.chartData.datasets[0].data.push(a.fcstValue);
    }
  },
};
</script>
<style>
#test {
  width: 90%;
  /* height: 200px; */
  text-align: center;
  display: block;
  margin: 0 auto;
}
</style>
