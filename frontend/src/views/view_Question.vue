<template>
  <div>
    <v-card elevation="2" outlined color="#faf6d6" class="mt-2 Question">
      <v-card-title> {{ author }}</v-card-title>

      <v-card-text>{{ question.text }} </v-card-text>
      <v-card-actions class="card-actions">
        <v-dialog v-model="dialog" persistent max-width="600px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark v-bind="attrs" v-on="on">
              Answer
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline" v-if="question.user != undefined"
                >Answer to {{ question.user.username }}</span
              >
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col>
                    <v-textarea
                      v-model="answer_txt"
                      label="Write here your answer"
                      outlined
                      required
                    ></v-textarea>
                  </v-col>
                </v-row>
              </v-container>
              <small>*Please respect the rules</small>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="dialog = false">
                Close
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click="(dialog = false), answerQuestion()"
              >
                Send
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card-actions>
    </v-card>
    <Answers :id="$route.params.id" />
  </div>
</template>

<script>
import Answers from "../components/Answers.vue";
import axios from "axios";
export default {
  name: "view_Question",
  components: { Answers },
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
    answerQuestion() {
      var id = this.check_cookie_value("id");
      var username = this.check_cookie_value("name");
      this.question.id = parseInt(this.question.id);
      var day = new Date().toISOString().slice(0, 10).replace(/-/g, "");
      axios
        .post("http://localhost:8082/api/answers/create", {
          user: { id, username },
          text: this.answer_txt,
          correct: false,
          query: {
            id: this.question.id,
            user: {
              id: this.question.user.id,
              username: this.question.user.username,
            },
            text: this.question.text,
            date: this.question.date,
            answers: this.question.answers,
          },
          date: day,
        })
        .then(function (response) {
          console.log(response);
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
  },
  data() {
    return {
      id_number: this.$route.params.id,
      question: [],
      author: "",
      dialog: false,
      answer_txt: "",
    };
  },
  mounted() {
    var vm = this;
    axios
      .get("http://localhost:8082/api/queries/" + vm.id_number)
      .then(function (response) {
        vm.question = response.data;
        vm.author = vm.question.user.username;
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
