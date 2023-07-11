<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axiosInstance from '@/api/axiosInstance'
import Chart from 'chart.js/auto'

const totalNumberOfQuestionsPerProgrammingLanguage = ref({})

// Fetch data from the server
const fetchData = async () => {
  try {
    const totalNumberOfQuestionsPerProgrammingLanguageResponse = await axiosInstance.get(
      'number-of-questions-per-programming-language'
    )
    if (totalNumberOfQuestionsPerProgrammingLanguageResponse.status === 200) {
      totalNumberOfQuestionsPerProgrammingLanguage.value =
        totalNumberOfQuestionsPerProgrammingLanguageResponse.data
      createChart()
    }
  } catch (error) {
    console.error(error)
  }
}

function createChart() {
  const chartElement = document.getElementById('chart-polar')
  const ctx = chartElement.getContext('2d')

  const programmingLanguages = Object.keys(totalNumberOfQuestionsPerProgrammingLanguage.value)
  const dataValues = Object.values(totalNumberOfQuestionsPerProgrammingLanguage.value)
  const backgroundColors = [
    'rgba(255, 0, 0, 0.7)',
    'rgba(0, 255, 0, 0.7)',
    'rgba(0, 0, 255, 0.7)',
    'rgba(255, 255, 0, 0.7)',
    'rgba(255, 0, 255, 0.7)',
    'rgba(0, 255, 255, 0.7)',
    'rgba(128, 0, 0, 0.7)',
    'rgba(0, 128, 0, 0.7)',
    'rgba(0, 0, 128, 0.7)',
    'rgba(128, 128, 0, 0.7)'
  ]

  new Chart(ctx, {
    type: 'polarArea',
    data: {
      labels: programmingLanguages,
      datasets: [
        {
          data: dataValues,
          backgroundColor: backgroundColors
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        title: {
          display: true,
          text: 'Number of Asked Questions by Programming Language'
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
            <canvas id="chart-polar" class="chart-canvas" height="300"></canvas>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
