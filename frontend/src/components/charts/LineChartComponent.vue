<script setup lang="ts">
import {ref, onMounted} from 'vue'
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
          pointRadius: 2,
          borderColor: '#763f04',
          data: javaData.value,
        },
        {
          label: 'Python',
          pointRadius: 2,
          borderColor: '#8154f3',
          data: pythonData.value,
        },
        {
          label: 'Javascript',
          pointRadius: 2,
          borderColor: '#FFC107FF',
          data: javascriptData.value,
        },
        {
          label: 'C++',
          pointRadius: 2,
          borderColor: '#e834c5',
          data: cppData.value,
        },
        {
          label: 'C#',
          pointRadius: 2,
          borderColor: '#47ba19',
          data: csharpData.value,

        },
        {
          label: 'Go',
          pointRadius: 2,
          borderColor: '#1944ba',
          data: goData.value,

        },
        {
          label: 'Kotlin',
          pointRadius: 2,
          borderColor: '#1944ba',
          data: kotlinData.value,

        },
        {
          label: 'PHP',
          pointRadius: 2,
          borderColor: '#4a557b',
          data: phpData.value,

        },
        {
          label: 'Swift',
          pointRadius: 2,
          borderColor: '#ee345e',
          data: swiftData.value,
        },
        {
          label: 'Ruby',
          pointRadius: 2,
          borderColor: '#640b22',
          data: goData.value,
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
            Number of daily questions asked for each programming languages
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
