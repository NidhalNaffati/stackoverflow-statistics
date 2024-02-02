<script setup lang="js">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/api/axiosInstance'
import programmingLanguageColors from '@/assets/js/programmingLanguageColors'
import Chart from 'chart.js/auto';

const programmingLanguages = [
  'java', 'python', 'javascript', 'c++', 'c#', 'php', 'go', 'ruby', 'swift', 'kotlin'
];

// Create refs for each programming language
const chartData = programmingLanguages.reduce((acc, lang) => {
  acc[lang] = ref(null);
  return acc;
}, {});

const hasError = ref(false) // Added a flag to track if there is an error
const errorMessage = ref('')

onMounted(() => {
  fetchData()
})

// Fetch data from the server
async function fetchData() {
  try {
    const requests = programmingLanguages.map(lang =>
      axiosInstance.get(`number-of-questions-per-day/${lang}`)
    );

    // Wait for all requests to finish
    const responses = await Promise.all(requests);

    // Set chartData
    responses.forEach((response, index) => {
      const lang = programmingLanguages[index];

      if (response.status === 200) {
        chartData[lang].value = Object.values(response.data);
      } else {
        hasError.value = true;
        errorMessage.value = response.data.message;
      }
    });

    createChart(); // Call createChart after setting chartData
  } catch (error) {
    console.error('Error:', error);
    hasError.value = true;
    errorMessage.value = error.message;
  }
}

function createChart() {
  const ctx2 = document.getElementById('chart-line').getContext('2d');

  const labels = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

  new Chart(ctx2, {
    type: 'line',
    data: {
      labels: labels,
      datasets: programmingLanguages.map(lang => ({
        label: lang.charAt(0).toUpperCase() + lang.slice(1), // Capitalize first letter
        pointRadius: 2,
        borderColor: programmingLanguageColors[lang],
        data: chartData[lang].value
      }))
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
      scales: {
        x: {
          grid: {
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
        },
        y: {
          grid: {
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
      }
    }
  });
}
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
            <p v-if="hasError" class="alert alert-danger" role="alert">
              {{ errorMessage }}
            </p>
            <canvas v-else id="chart-line" class="chart-canvas"></canvas>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.chart-area {
  height: 400px;
}
</style>
