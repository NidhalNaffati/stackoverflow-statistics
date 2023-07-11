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
  <div class="col-xl-2 col-sm-6 mb-xl-0 mb-4">
    <div class="card">
      <div class="card-body p-3">
        <div class="row">
          <div class="col-8">
            <div class="numbers">
              <p class="text-sm mb-0 text-capitalize font-weight-bold">Answered Questions</p>
              <h5 class="font-weight-bolder mb-0">
                {{ serverData }}
              </h5>
            </div>
          </div>
          <div class="col-4 text-end">
            <div class="icon icon-shape bg-gradient-primary shadow text-center border-radius-md">
              <i class="ni ni-money-coins text-lg opacity-10" aria-hidden="true"></i>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
