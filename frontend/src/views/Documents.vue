<template>
  <div>
    <v-dialog v-model="dialog" max-width="290">
      <v-card class="text-center">
        <v-card-title class="headline"> Oh no! </v-card-title>

        <v-card-text>
          It looks like you spent all your coins. Please answer some question or
          upload some document before continue downloading.
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="green darken-1" text @click="dialog = false">
            Ok
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-row>
      <v-col cols="3">
        <v-navigation-drawer
          permanent
          class="full_heigth"
          style="height: 100%; width: 100%"
          dark
        >
          <v-list dense>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>University</v-list-item-title>
                <v-text-field
                  v-model="searchUni"
                  placeholder="insert some keywords"
                  solo
                  class="mt-2"
                ></v-text-field>
              </v-list-item-content>
            </v-list-item>

            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Course</v-list-item-title>
                <v-text-field
                  v-model="searchCourse"
                  placeholder="insert some keywords"
                  solo
                  class="mt-2"
                ></v-text-field>
              </v-list-item-content>
            </v-list-item>

            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Typology</v-list-item-title>
                <v-text-field
                  v-model="searchType"
                  placeholder="insert some keywords"
                  solo
                  class="mt-2"
                ></v-text-field>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Year</v-list-item-title>
                <v-text-field
                  v-model="searchYear"
                  placeholder="insert some keywords"
                  solo
                  class="mt-2"
                ></v-text-field>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-btn @click="searchDocuments()" class="center"> Search </v-btn>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>
      </v-col>
      <v-col cols="9">
        <v-card
          v-for="(document, index) in documents"
          :key="index"
          color="#1548e094"
          class="mt-2 document"
        >
          <v-card-title class="headline"> {{ document.title }}</v-card-title>

          <v-card-text>{{ document.user.username }}</v-card-text>

          <v-card-actions class="card-actions">
            <v-btn
              @click="
                downloadIt(document.id, document.nameFile, document.price)
              "
            >
              Download
            </v-btn>
          </v-card-actions>
          <v-card-actions class="card-actions2">
            <v-btn text disabled>
              {{ convertDate(document.creationData) }}
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Documents",
  components: {},
  watch: {
    searchUni: function () {
      axios
        .get(
          "http://localhost:8085/api/documents/search/" +
            this.searchUni +
            "/" +
            this.searchCourse +
            "/" +
            this.searchType +
            "/" +
            this.searchYear
        )
        .then((response) => {
          this.documents = response.data;
        });
    },
    searchCourse: function () {
      axios
        .get(
          "http://localhost:8085/api/documents/search/" +
            this.searchUni +
            "/" +
            this.searchCourse +
            "/" +
            this.searchType +
            "/" +
            this.searchYear
        )
        .then((response) => {
          this.documents = response.data;
        });
    },
    searchType: function () {
      axios
        .get(
          "http://localhost:8085/api/documents/search/" +
            this.searchUni +
            "/" +
            this.searchCourse +
            "/" +
            this.searchType +
            "/" +
            this.searchYear
        )
        .then((response) => {
          this.documents = response.data;
        });
    },
    searchYear: function () {
      axios
        .get(
          "http://localhost:8085/api/documents/search/" +
            this.searchUni +
            "/" +
            this.searchCourse +
            "/" +
            this.searchType +
            "/" +
            this.searchYear
        )
        .then((response) => {
          this.documents = response.data;
        });
    },
  },
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
    convertDate(dateString) {
      dateString = dateString.toString();
      return dateString.replace(/(\d{4})(\d\d)(\d\d)/g, "$2/$3/$1");
    },
    downloadIt(id, namefile, cost) {
      var idUser = this.check_cookie_value("id");
      var idGoogle = this.check_cookie_value("Token");
      this.formData = new FormData();

      axios
        .get("http://localhost:8085/api/utenti/" + idGoogle)
        .then((response) => {
          if (response.data.credits > cost) {
            this.formData.append("credits", response.data.credits - cost);
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
                    this.formData
                  )
                  .then((response) => {
                    console.log(response.data.credits);
                  });
              });
          } else {
            this.dialog = true;
          }
        });
    },
    searchDocuments() {
      axios
        .get(
          "http://localhost:8085/api/documents/search/" +
            this.searchUni +
            "/" +
            this.searchCourse +
            "/" +
            this.searchType +
            "/" +
            this.searchYear
        )
        .then((response) => {
          this.documents = response.data;
        });
    },
  },
  data() {
    return {
      dialog: false,
      searchUni: null,
      searchCourse: null,
      searchType: null,
      searchYear: null,
      documents: [],
    };
  },
  mounted() {
    axios.get("http://localhost:8085/api/documents/").then((response) => {
      this.documents = response.data;
    });
  },
};
</script>
<style scoped>
.document {
  height: 30vh;
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
}
.card-actions2 {
  position: absolute;
  bottom: 0;
}
.card-actions {
  position: absolute;
  bottom: 0;
  right: 0;
}
.center {
  margin-left: auto;
  margin-right: auto;
}
</style>
