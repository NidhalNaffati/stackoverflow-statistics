<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/api/axiosInstance'
import TagComponent from '@/components/questions-cards/TagComponent.vue'

const props = defineProps({
  link: {
    type: String,
    required: true
  },
  title: {
    type: String,
    required: true
  }
})

const questions = ref([])

onMounted(async () => {
  try {
    const response = await axiosInstance.get(props.link)
    questions.value = response.data
  } catch (error) {
    console.error('Failed to fetch data:', error)
  }
})
</script>

<template>
  <div class="col-lg-7 col-xl-9">
    <div class="card shadow mb-4">
      <div class="card-header d-flex justify-content-between align-items-center">
        <h6 class="text-primary fw-bold m-0">{{ props.title }}</h6>
      </div>
      <div class="card-body">
        <ul class="list-group list-group-flush">
          <li v-for="question in questions" :key="question.question_id" class="list-group-item">
            <div class="row align-items-center no-gutters">
              <div class="col-xxl-2 me-2">
                <h6 class="mb-0">
                  <strong>{{ question.view_count }} views</strong>
                </h6>
                <h6 class="mb-0">
                  <strong>{{ question.score }} votes</strong>
                </h6>
                <h6 class="mb-0">
                  <strong>{{ question.is_answered ? 'Answered' : 'Not Answered' }}</strong>
                </h6>
              </div>
              <div class="col me-2">
                <h5 class="mb-0">
                  <strong>
                    <a :href="question.link" target="_blank" class="link-no-underline">{{
                      question.title
                    }}</a>
                  </strong>
                </h5>
                <div class="d-flex flex-wrap">
                  <tag-component v-for="tag in question.tags" :key="tag" :tag="tag" />
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.link-no-underline {
  text-decoration: none;
}
</style>
