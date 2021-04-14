<template>
  <div>
    <v-row v-for="(review, index) in reviews" :key="index">
      <v-col cols="12" class="d-flex justify-center">
        <v-card dark color="#1548e094" class="mt-2 answer">
          <v-card-title class="headline">
            {{ review.user.username }}
            <p class="vote">
              <v-rating readonly length="5" :value="review.vote"></v-rating>
            </p>
          </v-card-title>

          <v-card-text>{{ review.text }}</v-card-text>
          <v-card-actions class="card-actions2">
            <v-btn text disabled> {{ convertDate(review.data) }} </v-btn>
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
      reviews: [],
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
  },
  mounted() {
    var vm = this;
    axios
      .get("http://localhost:8085/api/documents/" + vm.id + "/reviews")
      .then(function (response) {
        vm.reviews = response.data;
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
.vote {
  position: absolute;
  right: 0;
}
</style>

