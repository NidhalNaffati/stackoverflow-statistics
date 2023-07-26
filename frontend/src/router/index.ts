import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import JavaPage from '@/views/JavaPage.vue'
import PythonPage from '@/views/PythonPage.vue'
import JavascriptPage from '@/views/JavascriptPage.vue'
import GoPage from '@/views/GoPage.vue'
import KotlinPage from '@/views/KotlinPage.vue'
import SwiftPage from '@/views/SwiftPage.vue'
import PhpPage from '@/views/PhpPage.vue'
import RubyPage from '@/views/RubyPage.vue'
import CppPage from '@/views/CppPage.vue'
import CsharpPage from '@/views/CsharpPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/java',
      name: 'java',
      component: JavaPage
    },
    {
      path: '/python',
      name: 'python',
      component: PythonPage
    },
    {
      path: '/javascript',
      name: 'javascript',
      component: JavascriptPage
    },
    {
      path: '/go',
      name: 'go',
      component: GoPage
    },
    {
      path: '/python',
      name: 'python',
      component: PythonPage
    },
    {
      path: '/Kotlin',
      name: 'Kotlin',
      component: KotlinPage
    },
    {
      path: '/swift',
      name: 'swift',
      component: SwiftPage
    },
    {
      path: '/php',
      name: 'php',
      component: PhpPage
    },
    {
      path: '/python',
      name: 'python',
      component: PythonPage
    },
    {
      path: '/ruby',
      name: 'ruby',
      component: RubyPage
    },
    {
      path: '/c++',
      name: 'c++',
      component: CppPage
    },
    {
      path: '/c#',
      name: 'c#',
      component: CsharpPage
    }
  ]
})

export default router
