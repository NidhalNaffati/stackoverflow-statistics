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
      const response = await axiosInstance.get('number-of-asked-questions')
      if (response.status === 200) {
        serverData.value = await response.data
      } else {
        console.error('Error status:', response.status)
        console.error('Error:', response)
      }
      return
    } else {
      // otherwise, we want to get questions for a specific programming language
      const response = await axiosInstance.get(
        'number-of-asked-questions/' + props.programmingLanguage
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
    <div class="card shadow border-start-success py-2">
      <div class="card-body">
        <div class="row align-items-center no-gutters">
          <div class="col me-2">
            <div class="text-uppercase text-success fw-bold text">
              <span>This week questions</span>
            </div>
            <div class="text-dark fw-bold h5 mb-0">
              <span>{{ serverData }}</span>
            </div>
          </div>
          <div class="col-auto">
            <i class="fas fa-calendar-week fa-2x text-gray-300"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
