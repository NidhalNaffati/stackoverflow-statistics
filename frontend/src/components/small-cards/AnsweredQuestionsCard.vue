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

// Make a request to the server to retrieve data
const fetchData = async () => {
  try {
    // if the programmingLanguage is 'all', then we want to get all questions
    if (props.programmingLanguage === 'all') {
      const response = await axiosInstance.get('number-of-answered-questions')
      if (response.status === 200) {
        serverData.value = await response.data
      } else {
        console.error('Error status:', response.status)
        console.error('Error:', response)
      }
    } else {
      // otherwise, we want to get questions for a specific programming language
      const response = await axiosInstance.get(
        'number-of-answered-questions/' + props.programmingLanguage
      )
      if (response.status === 200) {
        serverData.value = await response.data
      } else {
        console.error('Error status:', response.status)
        console.error('Error:', response)
      }
    }
  } catch (error) {
    console.error('Error:', error)
  }
}

onMounted(() => {
  fetchData()
})
</script>

<template>
  <div class="col-md-6 col-xl-3 mb-4">
    <div class="card shadow border-start-primary py-2">
      <div class="card-body">
        <div class="row align-items-center no-gutters">
          <div class="col me-2">
            <div class="text-uppercase text-primary fw-bold text">
              <span>this week answered questions</span>
            </div>
            <div class="row g-0 align-items-center">
              <div class="col-auto">
                <div class="text-dark fw-bold h5 mb-0 me-3">
                  <span>{{ serverData }}</span>
                </div>
              </div>
              <div class="col">
                <div class="progress progress-sm">
                  <div
                    class="progress-bar bg-primary"
                    aria-valuenow="25"
                    aria-valuemin="0"
                    aria-valuemax="100"
                    style="width: 25%"
                  >
                    <span class="visually">25%</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-auto"><i class="fas fa-calendar fa-2x text-gray-300"></i></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
