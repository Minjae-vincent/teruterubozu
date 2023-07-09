<template>
  <HelloWorld msg="Welcome to Your Vue.js App" />
  <TmpComponent />
</template>

<script>
import HelloWorld from './components/HelloWorld.vue';

export default {
  name: 'App',
  components: {
    HelloWorld,
  },
};
</script>
