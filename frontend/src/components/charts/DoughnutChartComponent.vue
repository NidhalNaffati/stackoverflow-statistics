<script setup lang="js">
import { onMounted, ref } from 'vue'
import axiosInstance from '@/api/axiosInstance'
import Chart from 'chart.js/auto'

const props = defineProps({
  programmingLanguage: {
    type: String,
    default: 'all',
    required: false
  }
})

const serverData = ref(null)

const hasError = ref(false) // flag to track if there is an error
const errorMessage = ref('')

onMounted(() => {
  fetchData()
})

async function fetchData() {
  try {
    const endpoint = props.programmingLanguage === 'all'
      ? 'number-of-answered-and-unanswered-questions'
      : `number-of-answered-and-unanswered-questions/${props.programmingLanguage}`

    const response = await axiosInstance.get(endpoint)

    if (response.status === 200) {
      serverData.value = await response.data
      createChart()
    } else {
      hasError.value = true
      errorMessage.value = response.data.message
    }
  } catch (error) {
    console.error('Error:', error.message)
    hasError.value = true
    errorMessage.value = error.message
  }
}

function createChart() {
  const chartElement = document.getElementById('chart-doughnut')
  const ctx = chartElement.getContext('2d')

  const labels = ['Answered', 'Unanswered']
  const data = [serverData.value.answered, serverData.value.unanswered]

  new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: labels,
      datasets: [
        {
          label: '',
          backgroundColor: ['#4e73df', '#36b9cc'],
          borderColor: ['#ffffff', '#ffffff'],
          data: data
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
      }
    }
  })
}
</script>

<template>
  <div class="col-lg-5 col-xl-4">
    <div class="card shadow mb-4">
      <div class="card-header d-flex justify-content-between align-items-center">
        <h6 class="text-primary fw-bold m-0">Answered & Unanswered questions</h6>
      </div>
      <div class="card-body">
        <div class="chart-area">
          <p v-if="hasError" class="alert alert-danger" role="alert">
            {{ errorMessage }}
          </p>
          <canvas v-else id="chart-doughnut" class="chart-canvas"></canvas>
        </div>
        <div class="text-center small mt-4">
          <span class="me-2"> <i class="fas fa-circle text-primary"></i>Answered </span>
          <span class="me-2"> <i class="fas fa-circle text-info"></i>Unanswered</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
