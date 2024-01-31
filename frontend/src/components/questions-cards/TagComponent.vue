<script setup lang="js">
const props = defineProps({
  tag: {
    type: String,
    required: true
  }
})

const getTagLink = () => {
  return `https://stackoverflow.com/questions/tagged/${props.tag}`
}
</script>

<template>
  <a
    :href="getTagLink()"
    target="_blank"
    class="card text-white bg-secondary d-inline-block link-no-underline"
  >
    <p class="m-2">{{ props.tag }}</p>
  </a>
</template>

<style scoped>
.card {
  margin: 5px;
}

.link-no-underline {
  text-decoration: none;
}
</style>
