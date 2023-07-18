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
          label: 'Earnings',
          fill: true,
          data: ['400', '200', '170', '333', '415', '180', '280'],
          backgroundColor: 'rgba(78, 115, 223, 0.05)',
          borderColor: 'rgba(78, 115, 223, 1)'
        },
        {
          label: 'Java',
          tension: 0.4,
          pointRadius: 0,
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

onMounted(() => {
  fetchData()
})
</script>

<template>
  <div class="row">
    <div class="col-lg-7 col-xl-8 col-xxl-12">
      <div class="card shadow mb-4" id="custom">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h6 class="text-primary fw-bold m-0">
            Number of questions asked for each programming languages (daily)
          </h6>
        </div>
        <div class="card-body">
          <div class="chart-area">
            <canvas id="chart-line" class="chart-canvas"></canvas>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
