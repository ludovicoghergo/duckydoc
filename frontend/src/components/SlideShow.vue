<template>
  <v-carousel hide-delimiters cycle>
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
            <h2 v-if="i == 0" class="text-center slide_desc">
              <p>Interact with the college community!</p>
            </h2>
            <h2 v-if="i == 1" class="text-center slide_desc">
              <p>Share Your doubt with the assistance of tutor</p>
            </h2>
            <h2 v-if="i == 2" class="text-center slide_desc">
              <p>Get the notes that you need!</p>
            </h2>
            <h2 v-if="i == 3" class="text-center slide_desc">
              <p>Upload your notes that can helps other</p>
            </h2>
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
          src:
            "https://images.unsplash.com/photo-1611095971113-9f7542655338?ixid=MXwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1351&q=80",
        },
        {
          src:
            "https://images.unsplash.com/photo-1434030216411-0b793f4b4173?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
        },
        {
          src:
            "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1266&q=80",
        },
        {
          src:
            "https://images.unsplash.com/photo-1536925155833-43e9c2b2f499?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80",
        },
      ],
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
