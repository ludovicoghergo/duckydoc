<template>
  <div>
    <v-row
      v-for="(question, index) in questions.slice(n_page, parseInt(n_page) + 2)"
      :key="index"
    >
      <v-col cols="12" class="d-flex justify-center">
        <v-card color="#385F73" dark class="question">
          <v-card-title> Domanda {{ index }}</v-card-title>

          <v-card-subtitle>{{ question.text }}</v-card-subtitle>
          <v-card-actions>
            <v-btn text> Answer </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Questions",
  components: {},
  props: ["n_page"],
  data() {
    return {
      questions: [],
      limit: 0,
    };
  },
  mounted() {
    var vm = this;
    axios
      .get("http://localhost:8081/api/querys")
      .then(function (response) {
        vm.questions = response.data;
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
.question {
  width: 70%;
}
</style>