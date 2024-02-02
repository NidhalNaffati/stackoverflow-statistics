<script setup lang="js">
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

const hasError = ref(false) // flag to track if there is an error
const errorMessage = ref('')

async function fetchData() {
  try {
    const endpoint = props.programmingLanguage === 'all'
      ? 'number-of-asked-questions'
      : `number-of-asked-questions/${props.programmingLanguage}`

    const response = await axiosInstance.get(endpoint)

    if (response.status === 200) {
      serverData.value = await response.data
    } else {
      hasError.value = true
      errorMessage.value = response.data.message
    }
  } catch (error) {
    console.error('Error:', error)
    hasError.value = true
    errorMessage.value = error.message
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
            <p v-if="hasError" class="alert alert-danger" role="alert">
              {{ errorMessage }}
            </p>
            <div v-else class="text-dark fw-bold h5 mb-0">
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
