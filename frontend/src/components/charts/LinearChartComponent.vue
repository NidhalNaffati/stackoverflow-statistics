<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/api/axiosInstance'

const javaData = ref(null)
const pythonData = ref(null)
const javascriptData = ref(null)
const cppData = ref(null)
const csharpData = ref(null)
const phpData = ref(null)
const goData = ref(null)
const rubyData = ref(null)
const swiftData = ref(null)
const kotlinData = ref(null)

// Fetch data from the server
async function fetchData() {
  try {
    const javaResponse = await axiosInstance.get('number-of-questions-per-day/java')
    const pythonResponse = await axiosInstance.get('number-of-questions-per-day/python')
    const javascriptResponse = await axiosInstance.get('number-of-questions-per-day/javascript')
    const cppResponse = await axiosInstance.get('number-of-questions-per-day/c++')
    const csharpResponse = await axiosInstance.get('number-of-questions-per-day/c#')
    const phpResponse = await axiosInstance.get('number-of-questions-per-day/php')
    const goResponse = await axiosInstance.get('number-of-questions-per-day/go')
    const rubyResponse = await axiosInstance.get('number-of-questions-per-day/ruby')
    const swiftResponse = await axiosInstance.get('number-of-questions-per-day/swift')
    const kotlinResponse = await axiosInstance.get('number-of-questions-per-day/kotlin')

    if (javaResponse.status === 200) {
      // set the chartData to the values of the response
      javaData.value = Object.values(javaResponse.data)
      pythonData.value = Object.values(pythonResponse.data)
      javascriptData.value = Object.values(javascriptResponse.data)
      cppData.value = Object.values(cppResponse.data)
      csharpData.value = Object.values(csharpResponse.data)
      phpData.value = Object.values(phpResponse.data)
      goData.value = Object.values(goResponse.data)
      rubyData.value = Object.values(rubyResponse.data)
      swiftData.value = Object.values(swiftResponse.data)
      kotlinData.value = Object.values(kotlinResponse.data)

      createChart() // Call createChart after setting chartData
    }
  } catch (error) {
    console.error(error)
  }
}

function createChart() {
  const ctx2 = document.getElementById('chart-line').getContext('2d')

  const labels = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']

  new Chart(ctx2, {
    type: 'line',
    data: {
      labels: labels,
      datasets: [
        {
          label: 'Java',
          tension: 0.4,
          pointRadius: 0,
          // set the color for the line brown
          borderColor: '#763f04',
          borderWidth: 3,
          fill: true,
          data: javaData.value,
          maxBarThickness: 6
        },
        {
          label: 'Python',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#8154f3',
          borderWidth: 3,
          fill: true,
          data: pythonData.value,
          maxBarThickness: 6
        },
        {
          label: 'Javascript',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#FFC107FF',
          borderWidth: 3,
          fill: true,
          data: javascriptData.value,
          maxBarThickness: 6
        },
        {
          label: 'C++',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#e834c5',
          borderWidth: 3,
          fill: true,
          data: cppData.value,
          maxBarThickness: 6
        },
        {
          label: 'C#',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#47ba19',
          borderWidth: 3,
          fill: true,
          data: csharpData.value,
          maxBarThickness: 6
        },
        {
          label: 'Go',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#1944ba',
          borderWidth: 3,
          fill: true,
          data: goData.value,
          maxBarThickness: 6
        },
        {
          label: 'Kotlin',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#1944ba',
          borderWidth: 3,
          fill: true,
          data: kotlinData.value,
          maxBarThickness: 6
        },
        {
          label: 'PHP',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#4a557b',
          borderWidth: 3,
          fill: true,
          data: phpData.value,
          maxBarThickness: 6
        },
        {
          label: 'Swift',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#ee345e',
          borderWidth: 3,
          fill: true,
          data: swiftData.value,
          maxBarThickness: 6
        },
        {
          label: 'Ruby',
          tension: 0.4,
          pointRadius: 0,
          borderColor: '#640b22',
          borderWidth: 3,
          fill: true,
          data: goData.value,
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
            display: true,
            drawOnChartArea: true,
            drawTicks: false,
            borderDash: [5, 5]
          },
          ticks: {
            display: true,
            padding: 10,
            color: '#b2b9bf',
            font: {
              size: 11,
              family: 'Open Sans',
              style: 'normal',
              lineHeight: 2
            }
          }
        },
        x: {
          grid: {
            drawBorder: false,
            display: false,
            drawOnChartArea: false,
            drawTicks: false,
            borderDash: [5, 5]
          },
          ticks: {
            display: true,
            color: '#b2b9bf',
            padding: 20,
            font: {
              size: 11,
              family: 'Open Sans',
              style: 'normal',
              lineHeight: 2
            }
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
  <div class="col-lg-7">
    <div class="card z-index-2">
      <div class="card-header pb-0">
        <h6>Rating by questions for each day</h6>
        <p class="text-sm">
          <i class="fa fa-arrow-up text-success"></i>
          <span class="font-weight-bold">idk</span> This week
        </p>
      </div>
      <div class="card-body p-3">
        <div class="chart">
          <canvas id="chart-line" class="chart-canvas" height="450"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
