<template>
  <div class="home">
    <SlideShow />
    <v-row class="mt-4 ml-4 mr-4">
      <v-col cols="4">
        <v-card hover class="question" @mousedown="goTo('/Documents')">
          <v-img
            src="https://images.unsplash.com/photo-1553714191-c89281730c67?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
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
            src="https://images.unsplash.com/photo-1557318041-1ce374d55ebf?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80"
            height="200px"
          ></v-img>
          <v-card-title>Questions </v-card-title>

          <v-card-subtitle> Any doubts? Check Here! </v-card-subtitle>
        </v-card>
      </v-col>
      <v-col cols="4">
        <v-card hover class="question" @mousedown="goTo('/Shop')">
          <v-img
            src="https://images.unsplash.com/photo-1532619187608-e5375cab36aa?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80"
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
