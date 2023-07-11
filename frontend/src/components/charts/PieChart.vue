<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axiosInstance from '@/api/axiosInstance'

const props = defineProps({
  programmingLanguage: {
    type: String,
    default: 'all',
    required: false
  }
})

const serverData = ref(null)

async function fetchData() {
  try {
    // if the programmingLanguage is 'all', then we want to get all questions
    if (props.programmingLanguage === 'all') {
      const response = await axiosInstance.get('number-of-answered-and-unanswered-questions')
      if (response.status === 200) {
        serverData.value = await response.data
        // serverData.value : { "answered": 2384,"unanswered": 7132}
        createChart()
      } else {
        console.error('Error status:', response.status)
        console.error('Error:', response)
      }
    } else {
      // otherwise, we want to get questions for a specific programming language
      const response = await axiosInstance.get(
        'number-of-answered-and-unanswered-questions/' + props.programmingLanguage
      )
      if (response.status === 200) {
        serverData.value = await response.data
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
  const chartElement = document.getElementById('chart-pie')
  const ctx = chartElement.getContext('2d')

  const labels = ['Answered', 'Unanswered']
  const data = [serverData.value.answered, serverData.value.unanswered]

  const total = data.reduce((a, b) => a + b, 0)
  const answeredPercentage = ((data[0] / total) * 100).toFixed(2)
  const unansweredPercentage = ((data[1] / total) * 100).toFixed(2)

  const percentageData = [answeredPercentage, unansweredPercentage]

  new Chart(ctx, {
    type: 'pie',
    data: {
      labels: labels,
      datasets: [
        {
          label: 'Pie Chart',
          backgroundColor: ['#ff0000', '#00ff00'],
          data: percentageData
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false
        },
        tooltip: {
          callbacks: {
            label: function (context) {
              const labelIndex = context.dataIndex
              return (
                labels[labelIndex] +
                ': ' +
                data[labelIndex] +
                ' (' +
                percentageData[labelIndex] +
                '%)'
              )
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
  <div class="col-lg-5 mb-lg-0 mb-4">
    <div class="card z-index-2">
      <div class="card-body p-3">
        <div class="bg-gradient-primary border-radius-lg py-3 pe-1 mb-3">
          <div class="chart">
            <canvas id="chart-pie" class="chart-canvas" height="170"></canvas>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
