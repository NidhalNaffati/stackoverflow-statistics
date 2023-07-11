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
import PolarChart from '@/components/charts/PolarChart.vue'
</script>

<template class="g-sidenav-show bg-gray-100">
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <div class="container-fluid py-4">
      <!--div for the small cards -->
      <div class="row">
        <TodayQuestionCard programmingLanguage="all" />
        <WeekQuestionsCard programmingLanguage="all" />
        <AnsweredQuestionsCard programmingLanguage="all" />
        <UnansweredQuestionsCard programmingLanguage="all" />
        <ClosedQuestionsCard programmingLanguage="all" />
        <OpenQuestions programmingLanguage="all" />
      </div>
      <div class="row">
        <QuestionsPerDay />
        <PieChart programmingLanguage="all" />
      </div>
      <div class="row">
        <LinearChartComponent />
      </div>
      <div class="row">
        <PolarChart />
      </div>

      <!--div for the big cards -->
    </div>
  </main>
</template>

<style scoped></style>
