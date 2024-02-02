<script setup lang="js">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/api/axiosInstance'
import Chart from 'chart.js/auto'

const props = defineProps({
  programmingLanguage: {
    type: String,
    default: 'all',
    required: false
  }
})

const chartData = ref(null)

const hasError = ref(false) // Added a flag to track if there is an error
const errorMessage = ref('')

onMounted(() => {
  fetchData()
})

const fetchData = async () => {
  try {
    const endpoint = props.programmingLanguage === 'all'
      ? 'number-of-questions-per-day'
      : `number-of-questions-per-day/${props.programmingLanguage}`

    const response = await axiosInstance.get(endpoint)

    if (response.status === 200) {
      chartData.value = await response.data
      createChart()
    } else {
      hasError.value = true
      errorMessage.value = response.data.message
    }
  } catch (error) {
    console.error('Error:', error)
    hasError.value = true
    errorMessage.value = error.message
  }
}

function createChart() {
  const ctx2 = document.getElementById('chart-bar').getContext('2d')

  const labels = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']

  const data = Object.values(chartData.value)

  new Chart(ctx2, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [
        {
          label: 'Questions',
          fill: true,
          data: data,
          backgroundColor: 'rgb(54,185,204)',
          borderColor: 'rgba(78, 115, 223, 1)'
        }
      ]
    },
    options: {
      maintainAspectRatio: false,
      legend: {
        display: true,
        labels: {
          fontStyle: 'normal'
        }
      },
      title: {
        fontStyle: 'normal'
      },
      scales: {
        x: {
          grid: {
            color: 'rgb(234, 236, 244)',
            zeroLineColor: 'rgb(234, 236, 244)',
            drawBorder: false,
            drawTicks: false,
            borderDash: ['2'],
            zeroLineBorderDash: ['2'],
            drawOnChartArea: false
          },
          ticks: {
            fontColor: '#858796',
            fontStyle: 'normal',
            padding: 20
          }
        },
        y: {
          grid: {
            color: 'rgb(234, 236, 244)',
            zeroLineColor: 'rgb(234, 236, 244)',
            drawBorder: false,
            drawTicks: false,
            borderDash: ['2'],
            zeroLineBorderDash: ['2']
          },
          ticks: {
            fontColor: '#858796',
            fontStyle: 'normal',
            padding: 20
          }
        }
      }
    }
  })
}
</script>

<template>
  <div class="col-lg-7 col-xl-8">
    <div class="card shadow mb-4" id="custom">
      <div class="card-header d-flex justify-content-between align-items-center">
        <h6 class="text-primary fw-bold m-0">Number of questions per day</h6>
      </div>
      <div class="card-body">
        <div class="chart-area">
          <p v-if="hasError" class="alert alert-danger" role="alert">
            {{ errorMessage }}
          </p>
          <canvas v-else id="chart-bar" class="chart-canvas"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
