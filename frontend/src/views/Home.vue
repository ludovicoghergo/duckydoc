<template>
  <div class="home">
    <SlideShow />
  </div>
</template>

<script>
// @ is an alias to /src
import SlideShow from "@/components/SlideShow.vue";

export default {
  name: "Home",
  components: {
    SlideShow
  }
};
</script>
