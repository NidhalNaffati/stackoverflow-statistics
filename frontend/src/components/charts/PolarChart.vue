<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/api/axiosInstance'

const chartData = ref(null)

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
      console.error('Error status:', response.status)
      console.error('Error:', response)
    }
  } catch (error) {
    console.error('Error:', error)
  }
}

function createChart() {
  const ctx = document.getElementById('chart-polar').getContext('2d')

  const labels = Object.keys(chartData.value)
  const data = Object.values(chartData.value)

  new Chart(ctx, {
    type: 'polarArea',
    data: {
      labels: labels,
      datasets: [
        {
          label: 'Number of Questions',
          data: data,
          backgroundColor: [
            '#b07219',
            '#3572A5',
            '#f1e05a',
            '#f34b7d',
            '#178600',
            '#00ADD8',
            '#A97BFF',
            '#4F5D95',
            '#F05138',
            '#701516'
          ],
          borderWidth: 0
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
      scale: {
        ticks: {
          beginAtZero: true
        }
      }
    }
  })
}
</script>

<template>
  <div class="col-lg-7 col-xl-3">
    <div class="card shadow mb-4" id="custom">
      <div class="card-header d-flex justify-content-between align-items-center">
        <h6 class="text-primary fw-bold m-0">Number of questions per programming language</h6>
      </div>
      <div class="card-body">
        <div class="chart-area polar-chart">
          <canvas id="chart-polar" class="chart-canvas"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.polar-chart {
  height: 500px !important;
}
</style>
