<template>
  <div>
    <v-card elevation="2" outlined color="#faf6d6" class="mt-2 Question">
      <v-card-title> Domanda </v-card-title>

      <v-card-text>{{ question.text }} </v-card-text>
      <v-card-actions class="card-actions">
        <v-btn text> Answer </v-btn>
      </v-card-actions>
    </v-card>
    <Answers :id="id_number" />
  </div>
</template>

<script>
import Answers from "../components/Answers.vue";
import axios from "axios";
export default {
  name: "view_Question",
  components: { Answers },
  data() {
    return {
      id_number: this.$route.params.id,
      question: [],
    };
  },
  mounted() {
    var vm = this;
    axios
      .get("http://localhost:8081/api/querys/" + vm.id_number)
      .then(function (response) {
        vm.question = response.data;
      })
      .catch((error) => {
        if (error.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // The request was made but no response was received
          // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
          // http.ClientRequest in node.js
          console.log(error.request);
        } else {
          // Something happened in setting up the request that triggered an Error
          console.log("Error", error.message);
        }
      });
  },
};
</script>
<style scoped>
.Question {
  height: 50vh;
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
}
.card-actions {
  position: absolute;
  bottom: 0;
}
</style>
