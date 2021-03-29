<template>
  <div>
    <v-row v-for="(answer, index) in answers" :key="index">
      <v-col cols="12" class="d-flex justify-center">
        <v-card v-if="answer.correct" color="#f6febd80" class="mt-2 answer">
          <v-card-title class="headline">
            {{ answer.user.username }}</v-card-title
          >

          <v-card-text>{{ answer.text }}</v-card-text>

          <v-card-actions class="card-actions">
            <v-btn
              v-if="
                check_cookie_value('id') == answer.query.user.id &&
                !answer.correct
              "
              @click="corrAnswer(answer.id, answer.user.id)"
              text
            >
              Select as correct</v-btn
            >
            <v-btn v-if="answer.correct" disabled text>
              Marked as correct.</v-btn
            >
          </v-card-actions>
          <v-card-actions class="card-actions2">
            <v-btn text disabled> {{ convertDate(answer.date) }} </v-btn>
          </v-card-actions>
        </v-card>
        <v-card v-else color="#063ee50a" class="mt-2 answer">
          <v-card-title class="headline">
            {{ answer.user.username }}</v-card-title
          >

          <v-card-text>{{ answer.text }}</v-card-text>

          <v-card-actions class="card-actions">
            <v-btn
              v-if="
                check_cookie_value('id') == answer.query.user.id &&
                !answer.correct
              "
              @click="corrAnswer(answer.id, answer.user.id)"
              text
            >
              Select as correct</v-btn
            >
            <v-btn v-if="answer.correct" disabled text>
              Marked as correct.</v-btn
            >
          </v-card-actions>
          <v-card-actions class="card-actions2">
            <v-btn text disabled> {{ convertDate(answer.date) }} </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Answers",
  components: {},
  props: ["id"],
  data() {
    return {
      answers: [],
    };
  },
  methods: {
    convertDate(dateString) {
      dateString = dateString.toString();
      return dateString.replace(/(\d{4})(\d\d)(\d\d)/g, "$2/$3/$1");
    },
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
    corrAnswer(idAns, idUser) {
      var formData = new FormData();
      axios
        .put("http://localhost:8085/api/answers/correct/" + idAns)
        .then(function () {
          axios
            .get("http://localhost:8085/api/utenti/alt/" + idUser)
            .then(function (response) {
              console.log(response);
              formData.append("credits", response.data.credits + 20);
              axios
                .put(
                  "http://localhost:8085/api/utenti/" +
                    idUser +
                    "/updatecredit",
                  formData
                )
                .then((response) => {
                  console.log(response.data.credits);
                });
            });
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
            console.log("Errorripdd", error.message);
          }
        });
    },
  },
  mounted() {
    var vm = this;
    axios
      .get("http://localhost:8085/api/queries/" + vm.id + "/answers")
      .then(function (response) {
        vm.answers = response.data;
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
          console.log("Errorripdd", error.message);
        }
      });
  },
};
</script>
<style scoped>
.answer {
  height: 30vh;
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
}
.card-actions {
  position: absolute;
  bottom: 0;
}
.card-actions2 {
  position: absolute;
  bottom: 0;
  right: 0;
}
</style>

