<script setup lang="ts">
import { ref, computed } from 'vue';
import { ArrowDownLeftCircleIcon, ShoppingCartIcon, CircleArrowDownLeftIcon } from 'vue-tabler-icons';

const tab = ref('1');

const chartOptions1 = computed(() => {
  return {
    chart: {
      type: 'bar',
      height: 90,
      fontFamily: `inherit`,
      foreColor: '#a1aab2',
      sparkline: {
        enabled: true
      }
    },
    dataLabels: {
      enabled: false
    },
    colors: ['#fff'],
    fill: {
      type: 'solid',
      opacity: 1
    },
    stroke: {
      curve: 'smooth',
      width: 3
    },
    yaxis: {
      min: 0,
      max: 100000
    },
    tooltip: {
      fixed: {
        enabled: false
      },
      x: {
        show: false
      },
      y: {
        title: {
            formatter: () => '한화시스템 일 주가'
        }
      },
      marker: {
        show: false
      }
    }
  };
});

// chart 1
const lineChart1 = {
  series: [
    {
      name: 'series1',
      data: [32500, 32500, 55000, 32500, 36030, 26500, 31500, 36500]
    }
  ]
};

// chart 2
const chartOptions2 = computed(() => {
  return {
    chart: {
      type: 'bar',
      height: 90,
      fontFamily: `inherit`,
      foreColor: '#a1aab2',
      sparkline: {
        enabled: true
      }
    },
    dataLabels: {
      enabled: false
    },
    colors: ['#fff'],
    fill: {
      type: 'solid',
      opacity: 1
    },
    stroke: {
      curve: 'smooth',
      width: 3
    },
    yaxis: {
      min: 0,
      max: 100
    },
    tooltip: {
      fixed: {
        enabled: false
      },
      x: {
        show: false
      },
      y: {
        title: {
          formatter: () => '한화시스템 주 주가'
        }
      },
      marker: {
        show: false
      }
    }
  };
});

// chart 1
const lineChart2 = {
  series: [
    {
      name: 'series2',
      data: [35, 44, 9, 54, 45, 66, 41, 69]
    }
  ]
};
</script>

<template>
  <v-card elevation="0" class="bg-primary overflow-hidden bubble-shape bubble-primary-shape">
    <v-card-text>
      <div class="d-flex align-start mb-3">
        <div class="ml-auto z-1">
          <v-tabs v-model="tab" class="theme-tab" density="compact" end>
            <v-tab value="1" hide-slider color="transparent">일</v-tab>
            <v-tab value="2" hide-slider color="transparent">주</v-tab>
          </v-tabs>
        </div>
      </div>
      <v-window v-model="tab" class="z-1">
        <v-window-item value="1">
          <v-row>
            <v-col cols="6">
              <h2 class="text-h1 font-weight-medium">
                $160
                <a href="#">
                  <CircleArrowDownLeftIcon stroke-width="1.5" width="28" class="text-white" />
                </a>
              </h2>
              <span class="text-subtitle-1 text-medium-emphasis text-white">한화시스템</span>
            </v-col>
            <v-col cols="6">
              <apexchart type="line" class="text-darkText" height="90" :options="chartOptions1" :series="lineChart1.series"> </apexchart>
            </v-col>
          </v-row>
        </v-window-item>
        <v-window-item value="2">
          <v-row>
            <v-col cols="6">
              <h2 class="text-h1 font-weight-medium">
                $961
                <a href="#">
                  <ArrowDownLeftCircleIcon stroke-width="1.5" width="28" class="text-white" />
                </a>
              </h2>
              <span class="text-subtitle-1 text-medium-emphasis text-white">한화시스템</span>
            </v-col>
            <v-col cols="6">
              <apexchart type="line" class="text-darkText" height="90" :options="chartOptions2" :series="lineChart2.series"> </apexchart>
            </v-col>
          </v-row>
        </v-window-item>
      </v-window>
    </v-card-text>
  </v-card>
</template>
