<script setup lang="ts">
import QuestionsPerDay from '@/components/charts/QuestionsPerDayBarChart.vue'
import LinearChartComponent from '@/components/charts/LineChartComponent.vue'
import WeekQuestionsCard from '@/components/small-cards/WeekQuestionsCard.vue'
import AnsweredQuestionsCard from '@/components/small-cards/AnsweredQuestionsCard.vue'
import ClosedQuestionsCard from '@/components/small-cards/ClosedQuestionsCard.vue'
import PolarChart from '@/components/charts/PolarChart.vue'
import DoughnutChartComponent from '@/components/charts/DoughnutChartComponent.vue'
import TodayQuestionsCard from '@/components/small-cards/TodayQuestionsCard.vue'
</script>

<template>
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <div class="container-fluid py-4">
      <!--div for the small cards -->
      <div class="row">
        <TodayQuestionsCard programming-language="java" />
        <WeekQuestionsCard programmingLanguage="java" />
        <AnsweredQuestionsCard programmingLanguage="java" />
        <ClosedQuestionsCard programmingLanguage="java" />
      </div>

      <div class="row">
        <LinearChartComponent />
      </div>

      <div class="row">
        <QuestionsPerDay programming-language="all" />
        <DoughnutChartComponent programming-language="all" />
      </div>

      <div class="row">
        <PolarChart />
      </div>

      <!--div for the big cards -->
    </div>
  </main>
</template>

<style scoped></style>
