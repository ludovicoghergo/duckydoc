<template>
  <div>
    <v-dialog v-model="dialog_coin" max-width="290">
      <v-card  class="text-center">
        <v-card-title class="headline"> Oh no! </v-card-title>

        <v-card-text>
          It looks like you spent all your coins. Please answer some question or
          upload some document before continue downloading.
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="green darken-1" text @click="dialog_coin = false">
            Ok
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-card elevation="2"  dark outlined color="#1548e094" class="mt-2 Question">
      <v-card-title>
        {{ document.title }}
        <v-dialog v-model="dialog_rep" persistent max-width="600px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              icon
              color="primary"
              class="report"
              dark
              v-bind="attrs"
              v-on="on"
            >
              <v-icon> mdi-alert-octagram </v-icon>
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline" v-if="document.user != undefined"
                >Report {{ document.user.username }}'s doc</span
              >
            </v-card-title>
            <v-card-text>
              <v-container>
                <p>Please tell us what's wrong.</p>
                <v-row>
                  <v-col>
                    <v-textarea
                      v-model="report_txt"
                      label="Write here ..."
                      outlined
                      required
                    ></v-textarea>
                  </v-col>
                </v-row>
              </v-container>
              <small>*Thank you for your contribute.</small>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="dialog_rep = false">
                Close
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click="(dialog_rep = false), sendReport()"
              >
                Send
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card-title>

      <v-card-text>
        <h3>
          <b>Description: </b>
        </h3>
        <p>{{ document.description }}</p>

        <h3>
          <b>University: </b>
        </h3>
        {{ document.university }}
        <h3>
          <b>Course: </b>
        </h3>
        {{ document.course }}
        <h3>
          <b>Year: </b>
        </h3>
        {{ document.year }}
        <h3>
          <b>Price: </b>
        </h3>
        {{ document.price }}
      </v-card-text>

      <v-card-actions class="card-actions">
        <v-btn
          color="primary"
          dark
          @click="downloadIt(document.id, document.nameFile, document.price)"
        >
          {{ document.price }} $</v-btn
        >
      </v-card-actions>
      <v-card-actions class="card-actions2">
        <v-btn text disabled>
          {{ convertDate(document.creationData) }}
        </v-btn>
      </v-card-actions>

      <v-card-actions class="card-actions3">
        <v-dialog v-model="dialog" persistent max-width="600px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark v-bind="attrs" v-on="on">
              Review it
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline" v-if="document.user != undefined"
                >Review {{ document.user.username }}'s doc</span
              >
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-rating
                  class="text-center"
                  v-model="rating"
                  background-color="purple lighten-3"
                  color="purple"
                  large
                ></v-rating>
                <v-row>
                  <v-col>
                    <v-textarea
                      v-model="review_txt"
                      label="Write here your review"
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
                @click="(dialog = false), reviewDocument()"
              >
                Send
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card-actions>
    </v-card>
    <Reviews :id="$route.params.id" />
  </div>
</template>

<script>
import Reviews from "../components/Reviews.vue";
import axios from "axios";
export default {
  name: "view_Question",
  components: { Reviews },
  methods: {
    reviewDocument() {
      var id = this.check_cookie_value("id");
      var username = this.check_cookie_value("name");
      var day = new Date().toISOString().slice(0, 10).replace(/-/g, "");
      axios
        .post("http://localhost:8085/api/reviews/create", {
          user: { id, username },
          text: this.review_txt,
          vote: this.rating,
          document: this.document,
          data: day,
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
    sendReport() {
      var vm = this;
      var idUser = this.check_cookie_value("id");
      axios
        .get("http://localhost:8085/api/utenti/alt/" + idUser)
        .then(function (response) {
          console.log(response.data);
          axios
            .post("http://localhost:8085/api/reports/" + idUser + "/create", {
              documentId: document.id,
              description: vm.report_txt,
              status: "pending",
              user: {
                credits: response.data.credits,
                email: response.data.email,
                id: response.data.id,
                idGoogle: response.data.idGoogle,
                isMod: response.data.mod,
                name: response.data.name,
                reports: response.data.reports,
                surname: response.data.surname,
              },
            })
            .then((response) => {
              console.log(response);
            });
        });
    },

    convertDate(dateString) {
      dateString = dateString.toString();
      return dateString.replace(/(\d{4})(\d\d)(\d\d)/g, "$2/$3/$1");
    },
    downloadIt(id, namefile, cost) {
      var idUser = this.check_cookie_value("id");
      var idGoogle = this.check_cookie_value("Token");
      this.formData = new FormData();
      console.log("ciao");
      axios
        .get("http://localhost:8085/api/utenti/" + idGoogle)
        .then((response) => {
          if (response.data.credits > cost) {
            var money = response.data.credits - cost;

            axios
              .get("http://localhost:8085/api/documents/download/" + id, {
                responseType: "blob",
              })
              .then((response) => {
                const url = window.URL.createObjectURL(
                  new Blob([response.data])
                );
                console.log(response.data);
                const link = document.createElement("a");
                link.href = url;

                link.setAttribute("download", namefile);
                document.body.appendChild(link);
                link.click();
                axios
                  .put(
                    "http://localhost:8085/api/utenti/" +
                      idUser +
                      "/updatecredit",
                    { credits: money }
                  )
                  .then((response) => {
                    console.log(response.data.credits);
                  });
              });
          } else {
            this.dialog_coin = true;
          }
        });
    },
  },
  data() {
    return {
      id_doc: this.$route.params.id,
      document: [],
      author: "",
      dialog: false,
      review_txt: "",
      report_txt: "",
      dialog_rep: false,
      dialog_coin: false,
      rating: 0,
    };
  },
  mounted() {
    var vm = this;
    axios
      .get("http://localhost:8085/api/documents/" + vm.id_doc)
      .then(function (response) {
        vm.document = response.data;
        console.log(vm.document);
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
  right: 0;
}
.card-actions2 {
  position: absolute;
  left: 40%;
  right: 40%;
  bottom: 0;
}
.card-actions3 {
  position: absolute;
  bottom: 0;
}
.rate_rev {
  left: auto;
  right: auto;
}
.report {
  position: absolute;
  right: 5%;
}
</style>
