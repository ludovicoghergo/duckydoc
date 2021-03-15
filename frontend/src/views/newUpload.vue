<template>
  <div>
    <v-card elevation="2" outlined color="#faf6d6" class="mt-2 Question">
      <v-card-title class="justify-center">
        Upload your duby duby files!
      </v-card-title>
      <v-card-text class="justify-center">
        <v-row>
          <v-col cols="12" sm="6">
            <v-text-field
              v-model="title"
              label="Title"
              clearable
              outlined
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="6">
            <v-text-field
              v-model="desc"
              outlined
              label="Quick Description"
              clearable
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" sm="4">
            <v-text-field
              v-model="university"
              label="University"
              clearable
              outlined
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="4">
            <v-text-field
              v-model="course"
              outlined
              label="Course"
              clearable
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="4">
            <v-text-field
              v-model="year"
              outlined
              label="Year"
              clearable
            ></v-text-field>
          </v-col>
        </v-row>
        <v-text-field type="number" outlined label="price" v-model="price" />
        <input type="file" ref="uploadImage" @change="onImageUpload()" />

        <v-card-actions class="card-actions">
          <button text @click="fileUpload()">Upload !</button>
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
    onImageUpload() {
      let file = this.$refs.uploadImage.files[0];
      this.formData = new FormData();
      this.formData.append("file", file);
      console.log("Upload");
    },
    fileUpload() {
      var id = this.check_cookie_value("id");
      var name = this.check_cookie_value("name");
      var day = new Date().toISOString().slice(0, 10).replace(/-/g, "");
      this.formData.append("desc", this.desc);
      this.formData.append("date", day);
      this.formData.append("title", this.title);
      this.formData.append("university", this.university);
      this.formData.append("year", this.year);

      this.formData.append("price", this.price);
      this.formData.append("course", this.course);
      this.formData.append("userId", id);
      this.formData.append("username", name);

      axios
        .post("http://localhost:8081/api/documents/create", this.formData)
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
      this.formData = new FormData();
    },
  },
  data() {
    return {
      question_txt: "",
      formData: null,
      desc: "",
      title: "",
      university: "",
      course: "",
      year: 0,
      price: 0,
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
