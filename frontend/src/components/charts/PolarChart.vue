<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/api/axiosInstance'

const chartData = ref(null)

onMounted(() => {
  fetchData()
})

const fetchData = async () => {
  try {
    const response = await axiosInstance.get('number-of-questions-per-day')
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

  const labels = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']

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
            'rgba(255, 99, 132, 0.7)',
            'rgba(54, 162, 235, 0.7)',
            'rgba(255, 206, 86, 0.7)',
            'rgba(75, 192, 192, 0.7)',
            'rgba(153, 102, 255, 0.7)',
            'rgba(255, 159, 64, 0.7)',
            'rgba(94, 184, 186, 0.7)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)',
            'rgba(94, 184, 186, 1)'
          ],
          borderWidth: 1
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
        <h6 class="text-primary fw-bold m-0">Number of questions per day</h6>
      </div>
      <div class="card-body">
        <div class="chart-area">
          <canvas id="chart-polar" class="chart-canvas"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
