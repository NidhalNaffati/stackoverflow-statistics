<script setup lang="js">
import QuestionsPerDay from '@/components/charts/QuestionsPerDayBarChart.vue'
import LinearChartComponent from '@/components/charts/LineChartComponent.vue'
import WeekQuestionsCard from '@/components/small-cards/WeekQuestionsCard.vue'
import AnsweredQuestionsCard from '@/components/small-cards/AnsweredQuestionsCard.vue'
import ClosedQuestionsCard from '@/components/small-cards/ClosedQuestionsCard.vue'
import PolarChart from '@/components/charts/PolarChart.vue'
import DoughnutChartComponent from '@/components/charts/DoughnutChartComponent.vue'
import TodayQuestionsCard from '@/components/small-cards/TodayQuestionsCard.vue'
import TopViewComponent from '@/components/questions-cards/QuestionListComponent.vue'
import PieChartComponent from '@/components/charts/PieChartComponent.vue'
</script>

<template>
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <div class="container-fluid py-4">
      <div class="row">
        <TodayQuestionsCard programming-language="kotlin" />
        <WeekQuestionsCard programmingLanguage="kotlin" />
        <AnsweredQuestionsCard programmingLanguage="kotlin" />
        <ClosedQuestionsCard programmingLanguage="kotlin" />
      </div>

      <div class="row">
        <LinearChartComponent />
      </div>

      <div class="row">
        <QuestionsPerDay programming-language="kotlin" />
        <DoughnutChartComponent programming-language="kotlin" />
      </div>

      <div class="row">
        <PolarChart />
        <TopViewComponent
          title="Top viewed questions"
          link="top-views/5"
          programming-language="kotlin"
        />
      </div>

      <div class="row">
        <TopViewComponent
          title="Top scored questions"
          link="top-score/5"
          programming-language="kotlin"
        />
        <PieChartComponent />
      </div>
    </div>
  </main>
</template>

<style scoped></style>
