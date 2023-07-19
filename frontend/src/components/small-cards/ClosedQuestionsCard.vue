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

const closedQuestions = ref(null)
const totalQuestions = ref(null)
const percentageOfClosedQuestions = ref(null)

// Make a request to the server to retrieve data
const fetchData = async () => {
  try {
    // if the programmingLanguage is 'all', then we want to get all questions
    if (props.programmingLanguage === 'all') {
      const closedQuestionResponse = await axiosInstance.get('number-of-closed-questions')
      const totalQuestionsResponse = await axiosInstance.get('number-of-asked-questions')
      if (closedQuestionResponse.status === 200) {
        closedQuestions.value = await closedQuestionResponse.data
        totalQuestions.value = await totalQuestionsResponse.data
        percentageOfClosedQuestions.value = Math.round(
          (closedQuestions.value / totalQuestions.value) * 100
        )
        console.log('closedQuestions.value:', closedQuestions.value)
        console.log('totalQuestions.value:', totalQuestions.value)
        console.log('percentageOfClosedQuestions.value:', percentageOfClosedQuestions.value)
      } else {
        console.error('Error status:', closedQuestionResponse.status)
        console.error('Error:', closedQuestionResponse)
      }
      return
    } else {
      // otherwise, we want to get questions for a specific programming language
      const response = await axiosInstance.get(
        'number-of-closed-questions/' + props.programmingLanguage
      )
      if (response.status === 200) {
        closedQuestions.value = await response.data
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
    <div class="card shadow border-start-info py-2">
      <div class="card-body">
        <div class="row align-items-center no-gutters">
          <div class="col me-2">
            <div class="text-uppercase text-warning fw-bold text">
              <span> this week Closed questions</span>
            </div>
            <div class="row g-0 align-items-center">
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
