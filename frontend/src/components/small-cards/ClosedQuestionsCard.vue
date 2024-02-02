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

const closedQuestions = ref(null)
const totalQuestions = ref(null)
const percentageOfClosedQuestions = ref(null)

const hasError = ref(false) // flag to track if there is an error
const errorMessage = ref('')

async function fetchData() {
  try {
    const endpointForClosedQuestions = props.programmingLanguage === 'all'
      ? 'number-of-closed-questions'
      : `number-of-closed-questions/${props.programmingLanguage}`

    const endpointForAskedQuestions = props.programmingLanguage === 'all'
      ? 'number-of-asked-questions'
      : `number-of-asked-questions/${props.programmingLanguage}`

    const closedQuestionResponse = await axiosInstance.get(endpointForClosedQuestions)
    const totalQuestionsResponse = await axiosInstance.get(endpointForAskedQuestions)

    if (closedQuestionResponse.status === 200 && totalQuestionsResponse.status === 200) {
      closedQuestions.value = await closedQuestionResponse.data
      totalQuestions.value = await totalQuestionsResponse.data
      percentageOfClosedQuestions.value = Math.round(
        (closedQuestions.value / totalQuestions.value) * 100
      )
    } else {
      hasError.value = true
      errorMessage.value = closedQuestionResponse.data.message
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
    <div class="card shadow border-start-info py-2">
      <div class="card-body">
        <div class="row align-items-center no-gutters">
          <div class="col me-2">
            <div class="text-uppercase text-warning fw-bold text">
              <span> this week Closed questions</span>
            </div>
            <p v-if="hasError" class="alert alert-danger" role="alert">
              {{ errorMessage }}
            </p>
            <div v-else class="row g-0 align-items-center">
              <div class="col-auto">
                <div class="text-dark fw-bold h5 mb-0 me-3">
                  <span>{{ closedQuestions }}</span>
                </div>
              </div>
              <div class="col">
                <div class="progress progress">
                  <div
                    class="progress-bar bg-warning"
                    aria-valuenow="{{ percentageOfClosedQuestions }}"
                    aria-valuemin="0"
                    aria-valuemax="100"
                    :style="{ width: percentageOfClosedQuestions + '%' }"
                  >
                    <span class="visually">{{ percentageOfClosedQuestions }}%</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-auto">
            <i class="fa-solid fa-rectangle-xmark fa-2x text-gray-300"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
