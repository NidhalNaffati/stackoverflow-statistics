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

onMounted(() => {
  fetchData()
})

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
        display: false,
        labels: {
          fontStyle: 'normal'
        }
      },
      title: {
        fontStyle: 'normal'
      },
      scales: {
        xAxes: [
          {
            gridLines: {
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
          }
        ],
        yAxes: [
          {
            gridLines: {
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
        ]
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
          <canvas id="chart-bar" class="chart-canvas"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
