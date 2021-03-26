<template>
  <div>
    <v-card elevation="2" outlined color="#faf6d6" class="mt-2 Question">
      <v-card-title class="justify-center">
        Pagina di Accesso Login
      </v-card-title>
      <div class="googlebutton">
        <GoogleLogin
          :params="params"
          :onSuccess="onSuccess"
          :onFailure="onFailure"
          :renderParams="renderParams"
          >Login</GoogleLogin
        >
      </div>
      <v-card-text class="text-center"
        ><h3>Perchè effettuare il login?</h3>
        1. Potrai bla bla bla <br />2. è meglio <br />3. fallo e basta
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import GoogleLogin from "vue-google-login";
import { LoaderPlugin } from "vue-google-login";
import Vue from "vue";
import axios from "axios";
import { mapActions } from "vuex";
Vue.use(LoaderPlugin, {
  client_id:
    "566387838712-mkstlbr8qaa6md7c9d5lq5oe0n9uq2hs.apps.googleusercontent.com",
});
Vue.GoogleAuth.then((auth2) => {
  console.log(auth2.isSignedIn.get());
  console.log(auth2.currentUser.get());
});
export default {
  name: "Login",
  methods: {
    ...mapActions(["updatelogin"]),
    onSuccess(googleUser) {
      var googlelogin = googleUser.getBasicProfile();
      console.log("successo");
      this.updatelogin(true);
      axios
        .get("http://localhost:8085/api/utenti/" + googlelogin.getId())
        .then(function (response) {
          if (response.data != "") {
            console.log("trovata");
          } else {
            axios
              .post("http://localhost:8085/api/utenti/create", {
                idGoogle: googlelogin.getId().toString(),
                name: googlelogin.getGivenName(),
                email: googlelogin.getEmail(),
                surname: googlelogin.getFamilyName(),
                isMod: false,
                credits: 100,
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
          }

          let d = new Date();
          d.setTime(d.getTime() + 1 * 24 * 60 * 60 * 1000);
          let expires = "expires=" + d.toUTCString();
          document.cookie =
            "Token=" +
            googlelogin.getId().toString() +
            ";" +
            expires +
            ";path=/";
          document.cookie =
            "name=" + googlelogin.getGivenName() + ";" + expires + ";path=/";
          document.cookie =
            "id=" + response.data.id + ";" + expires + ";path=/";
          console.log("cookie pookie");
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
      width: 300,
      height: 50,
      longtitle: true,
    },
  }),
};
</script>
<style scoped>
.googlebutton {
  width: 300px;
  height: 50px;
  margin-left: auto;
  margin-right: auto;
}
.Question {
  height: 85vh;
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
}
</style>
