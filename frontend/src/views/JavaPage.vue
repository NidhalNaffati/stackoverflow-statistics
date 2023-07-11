<script setup lang="ts">
import TodayQuestionCard from '@/components/small-cards/TodayQuestionsCard.vue'
import QuestionsPerDay from '@/components/charts/QuestionsPerDayBarChart.vue'
import LinearChartComponent from '@/components/charts/LinearChartComponent.vue'
import WeekQuestionsCard from '@/components/small-cards/WeekQuestionsCard.vue'
import AnsweredQuestionsCard from '@/components/small-cards/AnsweredQuestionsCard.vue'
import UnansweredQuestionsCard from '@/components/small-cards/UnansweredQuestionsCard.vue'
import ClosedQuestionsCard from '@/components/small-cards/ClosedQuestionsCard.vue'
import OpenQuestions from '@/components/small-cards/OpenQuestions.vue'
import PieChart from '@/components/charts/PieChart.vue'
</script>

<template class="g-sidenav-show bg-gray-100">
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <div class="container-fluid py-4">
      <!--div for the small cards -->
      <div class="row">
        <TodayQuestionCard programmingLanguage="java" />
        <WeekQuestionsCard programmingLanguage="java" />
        <AnsweredQuestionsCard programmingLanguage="java" />
        <UnansweredQuestionsCard programmingLanguage="java" />
        <ClosedQuestionsCard programmingLanguage="java" />
        <OpenQuestions programmingLanguage="java" />
      </div>
      <div class="row">
        <QuestionsPerDay programmingLanguage="java" />
        <PieChart programmingLanguage="java" />
      </div>
      <div class="row">
        <LinearChartComponent />
      </div>
      <!--div for the big cards -->
    </div>
  </main>
</template>

<style scoped></style>
