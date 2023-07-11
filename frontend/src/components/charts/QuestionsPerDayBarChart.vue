<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/api/axiosInstance'

const props = defineProps({
  programmingLanguage: {
    type: String,
    default: 'all',
    required: false
  }
})

const chartData = ref(null)

const fetchData = async () => {
  try {
    // if the programmingLanguage is 'all', then we want to get all questions
    if (props.programmingLanguage === 'all') {
      const response = await axiosInstance.get('number-of-questions-per-day')
      if (response.status === 200) {
        chartData.value = await response.data
        createChart()
      } else {
        console.error('Error status:', response.status)
        console.error('Error:', response)
      }
    } else {
      // otherwise, we want to get questions for a specific programming language
      const response = await axiosInstance.get(
        'number-of-questions-per-day/' + props.programmingLanguage
      )
      if (response.status === 200) {
        chartData.value = await response.data
        createChart()
      } else {
        console.error('Error status:', response.status)
        console.error('Error:', response)
      }
    }
  } catch (error) {
    console.error('Error:', error)
  }
}

function createChart() {
  const chartElement = document.getElementById('chart-bars')
  const ctx = chartElement.getContext('2d')

  const data = Object.values(chartData.value)
  const labels = Object.keys(chartData.value)
  // const labels = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [
        {
          label: 'Number of Questions asked',
          tension: 0.4,
          borderWidth: 0,
          borderRadius: 4,
          borderSkipped: false,
          backgroundColor: '#fff',
          data: data,
          maxBarThickness: 6
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false
        }
      },
      interaction: {
        intersect: false,
        mode: 'index'
      },
      scales: {
        y: {
          grid: {
            drawBorder: false,
            display: false,
            drawOnChartArea: false,
            drawTicks: false
          },
          ticks: {
            suggestedMin: 0,
            suggestedMax: Math.max(...data) + 50,
            beginAtZero: true,
            padding: 15,
            font: {
              size: 14,
              family: 'Open Sans',
              style: 'normal',
              lineHeight: 2
            },
            color: '#fff'
          }
        },
        x: {
          grid: {
            drawBorder: false,
            display: false,
            drawOnChartArea: false,
            drawTicks: false
          },
          ticks: {
            font: {
              size: 14,
              family: 'Open Sans',
              style: 'normal',
              lineHeight: 2
            },
            color: '#fff',
            display: true
          }
        }
      }
    }
  })
}

onMounted(() => {
  fetchData()
})
</script>

<template>
  <div class="col-lg-5 mb-lg-0 mb-4">
    <div class="card z-index-2">
      <div class="card-body p-3">
        <div class="bg-gradient-danger border-radius-lg py-3 pe-1 mb-3">
          <div class="chart">
            <canvas id="chart-bars" class="chart-canvas" height="170"></canvas>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
