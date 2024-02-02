<template>
  <div class="col-lg-7 col-xl-3">
    <div class="card shadow mb-4" id="custom">
      <div class="card-header d-flex justify-content-between align-items-center">
        <h6 class="text-primary fw-bold m-0">Percentage of Programming Languages Questions</h6>
      </div>
      <div class="card-body">
        <div class="chart-area pie-chart">
          <p v-if="hasError" class="alert alert-danger" role="alert">
            {{ errorMessage }}
          </p>
          <canvas v-else id="chart-pie" class="chart-canvas"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="js">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/api/axiosInstance'
import programmingLanguageColors from '@/assets/js/programmingLanguageColors'
import Chart from 'chart.js/auto';

const chartData = ref(null)

const hasError = ref(false) // Added a flag to track if there is an error
const errorMessage = ref('')

onMounted(() => {
  fetchData()
})

const fetchData = async () => {
  try {
    const response = await axiosInstance.get('number-of-questions-per-programming-language')
    if (response.status === 200) {
      chartData.value = await response.data
      createChart()
    } else {
      hasError.value = true;
      errorMessage.value = response.data.message
    }
  } catch (error) {
    console.error('Error:', error)
    hasError.value = true;
    errorMessage.value = error.message
  }
}

function createChart() {
  const ctx = document.getElementById('chart-pie').getContext('2d')

  const labels = Object.keys(chartData.value)
  const data = Object.values(chartData.value)

  // Calculate the total number of questions
  const totalQuestions = data.reduce((total, num) => total + num, 0)

  // Calculate the percentage for each programming language
  const percentages = data.map((value) => ((value / totalQuestions) * 100).toFixed(2))

  new Chart(ctx, {
    type: 'pie', // Change the chart type to 'pie'
    data: {
      labels: labels,
      datasets: [
        {
          label: 'Percentage of Programming Languages',
          data: percentages, // Use the calculated percentages here
          backgroundColor: [
            programmingLanguageColors.java,
            programmingLanguageColors.python,
            programmingLanguageColors.javascript,
            programmingLanguageColors.go,
            programmingLanguageColors.kotlin,
            programmingLanguageColors.swift,
            programmingLanguageColors.php,
            programmingLanguageColors.ruby,
            programmingLanguageColors.cpp,
            programmingLanguageColors.csharp
          ],
          borderWidth: 1
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
      }
    }
  })
}
</script>

<style scoped>
.pie-chart {
  height: 500px !important;
}
</style>
