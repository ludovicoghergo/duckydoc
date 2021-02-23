<template>
  <v-carousel hide-delimiters>
    <v-carousel-item
      v-for="(item, i) in items"
      :key="i"
      :src="item.src"
      reverse-transition="fade-transition"
      transition="fade-transition"
    >
      <v-container fill-height>
        <v-layout align-end>
          <v-flex>
            <h1 class="text-center slide_desc">PROVAA</h1>
          </v-flex>
        </v-layout>
      </v-container>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
export default {
  name: "SlideShow",
  props: {},
  data() {
    return {
      items: [
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/bird.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg"
        }
      ]
    };
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
