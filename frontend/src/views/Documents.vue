<template>
  <div>
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
              <v-btn @click="searchDocuments()" class="center">
                Search your params
              </v-btn>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>
      </v-col>
      <v-col cols="9">
        <v-card
          v-for="(document, index) in documents"
          :key="index"
          color="#f4ecb4"
          class="mt-2 document"
        >
          <v-card-title class="headline"> {{ document.title }}</v-card-title>

          <v-card-text>{{ document.user.username }}</v-card-text>

          <v-card-actions class="card-actions">
            <v-btn @click="downloadIt(document.id, document.nameFile)">
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
  methods: {
    convertDate(dateString) {
      dateString = dateString.toString();
      return dateString.replace(/(\d{4})(\d\d)(\d\d)/g, "$2/$3/$1");
    },
    downloadIt(id, namefile) {
      axios
        .get("http://localhost:8081/documents/" + id, { responseType: "blob" })
        .then((response) => {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          console.log(response);
          console.log(response.headers["content-type"]);
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", namefile);
          document.body.appendChild(link);
          link.click();
        });
    },
    searchDocuments() {
      axios
        .get(
          "http://localhost:8081/documents/search/" +
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
