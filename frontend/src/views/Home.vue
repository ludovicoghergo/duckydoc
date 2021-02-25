<template>
  <div class="home">
    <SlideShow />
    <v-row class="mt-4 ml-4 mr-4">
      <v-col cols="4">
        <v-card hover class="question" @mousedown="goTo('/Documents')">
          <v-img
            src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
            height="200px"
          ></v-img>
          <v-card-title>Documents </v-card-title>

          <v-card-subtitle>
            Where you can find all the documents you need!
          </v-card-subtitle>
        </v-card>
      </v-col>
      <v-col cols="4">
        <v-card hover class="question" @mousedown="goTo('/QA')">
          <v-img
            src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
            height="200px"
          ></v-img>
          <v-card-title>Questions </v-card-title>

          <v-card-subtitle> Any doubts? Check Here! </v-card-subtitle>
        </v-card>
      </v-col>
      <v-col cols="4">
        <v-card hover class="question" @mousedown="goTo('/Shop')">
          <v-img
            src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
            height="200px"
          ></v-img>
          <v-card-title>Tutor </v-card-title>

          <v-card-subtitle> You need additional help? </v-card-subtitle>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
// @ is an alias to /src
import SlideShow from "@/components/SlideShow.vue";

export default {
  name: "Home",
  components: {
    SlideShow,
  },
  methods: {
    goTo(address) {
      this.$router.push(address);
    },
  },
};
</script>
