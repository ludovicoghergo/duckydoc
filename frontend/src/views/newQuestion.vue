<template>
  <div>
    <v-card elevation="2" outlined color="#faf6d6" class="mt-2 Question">
      <v-card-title class="justify-center">
        Making a new question...
      </v-card-title>
      <v-card-text>
        <v-textarea
          v-model="question_txt"
          label="Insert your question"
          outlined
          height="50vh"
          clearable
        ></v-textarea>
        <v-card-actions class="card-actions">
          <v-btn text @mousedown="sendQuestion()"> Send </v-btn>
        </v-card-actions>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "newQuestion",
  methods: {
    check_cookie_value(name) {
      var match = document.cookie.match(
        new RegExp("(^| )" + name + "=([^;]+)")
      );
      if (match) {
        return match[2];
      } else {
        return -1;
      }
    },
    sendQuestion() {
      var id = this.check_cookie_value("id");
      var day = new Date().toISOString().slice(0, 10).replace(/-/g, "");
      axios
        .post("http://localhost:8082/api/queries/create", {
          user: id,
          text: this.question_txt,
          date: day,
        })
        .then(function (response) {
          console.log(response);
        })
        .catch((error) => {
          if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log("Error", error.message);
          }
        });
    },
  },
  data() {
    return {
      question_txt: "",
    };
  },
  mounted() {},
};
</script>
<style scoped>
.Question {
  height: 70vh;
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
}
.card-actions {
  position: absolute;
  bottom: 0;
}
</style>
