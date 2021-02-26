<template>
  <v-app>
    <v-app-bar app color="primary" fixed dark>
      <div class="d-flex align-center">
        <h2>DuckyDoc</h2>
      </div>

      <v-spacer></v-spacer>

      <v-btn text @mousedown="goTo('/Documents')">
        <span class="mr-2">Documents</span>
      </v-btn>
      <v-btn text @mousedown="goTo('/QA')">
        <span class="mr-2">Q&A</span>
      </v-btn>
      <v-btn text @mousedown="goTo('/Shop')">
        <span class="mr-2">Shop</span>
      </v-btn>
      <GoogleLogin
        :params="params"
        :onSuccess="onSuccess"
        :onFailure="onFailure"
        :renderParams="renderParams"
        >Login</GoogleLogin
      >
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import GoogleLogin from "vue-google-login";
import { LoaderPlugin } from "vue-google-login";
import Vue from "vue";
Vue.use(LoaderPlugin, {
  client_id:
    "566387838712-mkstlbr8qaa6md7c9d5lq5oe0n9uq2hs.apps.googleusercontent.com",
});
Vue.GoogleAuth.then((auth2) => {
  console.log(auth2.isSignedIn.get());
  console.log(auth2.currentUser.get());
});
export default {
  name: "App",
  methods: {
    onSuccess(googleUser) {
      var googlelogin = googleUser.getBasicProfile();
      axios
        .get("http://localhost:8082/api/queries/" + vm.id_number)
        .then(function (response) {
          vm.question = response.data;
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

      googleUser.getBasicProfile();
    },
    onFailure() {
      console.log("failed");
    },
    goTo(address) {
      this.$router.push(address);
    },
  },
  components: { GoogleLogin },

  data: () => ({
    params: {
      client_id:
        "566387838712-mkstlbr8qaa6md7c9d5lq5oe0n9uq2hs.apps.googleusercontent.com",
    },
    renderParams: {
      width: 50,
      height: 50,
    },
  }),
};
</script>
