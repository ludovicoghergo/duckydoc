<template>
  <div>
    <v-card elevation="2"  dark outlined color="#1548e094" class="mt-2 Question">
      <v-card-title class="justify-center">
        Login
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
        ><h3>Join our wonderful community!</h3>
      </v-card-text>

  <v-row justify="center">
    <v-col
      cols="3"
    >
    <v-card>
      <v-img
        :src="`https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80`"
        aspect-ratio="1.5"
        class="grey lighten-2"
      >
      </v-img>
       <v-card-title class=" text-center title">
              Lucia
            </v-card-title>
          </v-card>
    </v-col>
    <v-col
      cols="3"
    >
    <v-card>
      <v-img
        :src="`https://images.unsplash.com/photo-1542103749-8ef59b94f47e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80`"
        aspect-ratio="1.5"
        class="grey lighten-2"
      >
      </v-img>
      <v-card-title class=" text-center title">
              Sofia
            </v-card-title>
          </v-card>
    </v-col>
    <v-col
      cols="3"
    >
    <v-card>
      <v-img
        :src="`https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80`"
        aspect-ratio="1.5"
        class="grey lighten-2"
      >
      </v-img>
       <v-card-title class=" text-center title">
              Victor
            </v-card-title>
          </v-card>
    </v-col>
  </v-row>
  
  <v-row class="mt-4" justify="center">
    <v-col
      cols="3"
    >
    <v-card>
      <v-img
        :src="`https://images.unsplash.com/photo-1552058544-f2b08422138a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=644&q=80`"
        aspect-ratio="1.5"
        class="grey lighten-2"
      >
      </v-img>
       <v-card-title class=" text-center title">
              Igor
            </v-card-title>
          </v-card>
    </v-col>
    <v-col
      cols="3"
    >
    <v-card>
      <v-img
        :src="`https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80`"
        aspect-ratio="1.5"
        class="grey lighten-2"
      >
      </v-img>
       <v-card-title class=" text-center title">
              Hector
            </v-card-title>
          </v-card>
    </v-col>
    <v-col
      cols="3"
    >
    <v-card>
      <v-img
        :src="`https://images.unsplash.com/photo-1499952127939-9bbf5af6c51c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1955&q=80`"
        aspect-ratio="1.5"
        class="grey lighten-2"
      >
      </v-img>
       <v-card-title class=" text-center title">
              Jessie
            </v-card-title>
          </v-card>
    </v-col>
  </v-row>


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
      var vm = this;
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
          vm.goTo("/");
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
