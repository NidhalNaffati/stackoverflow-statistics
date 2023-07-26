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

const answeredQuestions = ref(null)
const totalQuestions = ref(null)
const percentageOfAnsweredQuestions = ref(null)

// Make a request to the server to retrieve data
async function fetchData() {
  try {
    // if the programmingLanguage is 'all', then we want to get all questions
    if (props.programmingLanguage === 'all') {
      const answeredQuestionsResponse = await axiosInstance.get('number-of-answered-questions')
      const totalQuestionsResponse = await axiosInstance.get('number-of-asked-questions')
      if (answeredQuestionsResponse.status === 200 && totalQuestionsResponse.status === 200) {
        answeredQuestions.value = await answeredQuestionsResponse.data
        totalQuestions.value = await totalQuestionsResponse.data
        percentageOfAnsweredQuestions.value = Math.round(
          (answeredQuestions.value / totalQuestions.value) * 100
        )
      } else {
        console.error('Error status:', answeredQuestionsResponse.status)
        console.error('Error:', answeredQuestionsResponse)
      }
    } else {
      // otherwise, we want to get questions for a specific programming language
      const answeredQuestionsResponse = await axiosInstance.get(
        'number-of-answered-questions/' + props.programmingLanguage
      )
      const totalQuestionsResponse = await axiosInstance.get(
        'number-of-asked-questions/' + props.programmingLanguage
      )
      if (answeredQuestionsResponse.status === 200 && totalQuestionsResponse.status === 200) {
        answeredQuestions.value = await answeredQuestionsResponse.data
        totalQuestions.value = await totalQuestionsResponse.data
        percentageOfAnsweredQuestions.value = Math.round(
          (answeredQuestions.value / totalQuestions.value) * 100
        )
      } else {
        console.error('Error status:', answeredQuestionsResponse.status)
        console.error('Error:', answeredQuestionsResponse)
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
                  <span>{{ answeredQuestions }}</span>
                </div>
              </div>
              <div class="col">
                <div class="progress progress">
                  <div
                    class="progress-bar bg-primary"
                    aria-valuenow="{{ percentageOfAnsweredQuestions }}"
                    aria-valuemin="0"
                    aria-valuemax="100"
                    :style="{ width: percentageOfAnsweredQuestions + '%' }"
                  >
                    <span class="visually">{{ percentageOfAnsweredQuestions }}%</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-auto">
            <i class="fa-regular fa-lightbulb fa-2x text-gray-300"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
