<template>
  <div>
    <v-row
      v-for="(question, index) in questions.slice(n_page, parseInt(n_page) + 2)"
      :key="index"
    >
      <v-col cols="12" class="d-flex justify-center">
        <v-card
          color="#1548e094"
          dark
          class="question"
          @mousedown="goTo('/view_Question/' + question.id)"
        >
          <v-card-title> {{ question.title }}?</v-card-title>

          <v-card-subtitle>{{ question.user.username }}</v-card-subtitle>
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
  props: ["n_page", "txt_search"],
  data() {
    return {
      questions: [],
      limit: 0,
    };
  },
  methods: {
    goTo(address) {
      this.$router.push(address);
    },
  },
  watch: {
    txt_search: function () {
      var vm = this;
      if (vm.txt_search == "") {
        axios
          .get("http://localhost:8085/api/queries")
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
      } else {
        axios
          .get("http://localhost:8085/api/queries/find/" + vm.txt_search)
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
      }
    },
  },
  mounted() {
    var vm = this;
    axios
      .get("http://localhost:8085/api/queries")
      .then(function (response) {
        vm.questions = response.data;
        console.log(vm.questions);
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