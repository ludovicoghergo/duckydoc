<template>
  <v-app>
    <v-app-bar app color="primary" fixed dark>
      <v-btn text @mousedown="goTo('/')">
        <span class="mr-2">DuckyDoc</span>
      </v-btn>

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

      <span v-if="getLoggedIn" class="mr-2">
        <v-menu offset-y>
          <template v-slot:activator="{ on, attrs }">
            <v-btn text v-bind="attrs" v-on="on"> Account </v-btn>
          </template>
          <v-list>
            <v-list-item>
              <v-btn text @mousedown="logout()">
                <GoogleLogin
                  :params="params"
                  :onSuccess="onSuccess"
                  :logoutButton="true"
                  >LOGOUT</GoogleLogin
                ></v-btn
              >
            </v-list-item>
            <v-list-item>
              <v-btn text @mousedown="goTo('/newQuestion')">
                New question
              </v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn text @mousedown="goTo('/newUpload')"> New Upload </v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </span>
      <span v-else class="mr-2"
        ><v-btn text @mousedown="goTo('/Login')">
          <span class="mr-2">Login</span>
        </v-btn>
      </span>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import GoogleLogin from "vue-google-login";
import { mapGetters, mapActions } from "vuex";
export default {
  name: "App",
  methods: {
    ...mapActions(["updatelogin"]),
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
    onSuccess() {
      this.updatelogin(false);
    },
    logout() {
      document.cookie = "Token= ; expires = Thu, 01 Jan 1970 00:00:00 GMT";
      document.cookie = "name= ; expires = Thu, 01 Jan 1970 00:00:00 GMT";
      document.cookie = "id= ; expires = Thu, 01 Jan 1970 00:00:00 GMT";
    },
    goTo(address) {
      this.$router.push(address);
    },
  },
  computed: {
    ...mapGetters(["getLoggedIn"]),
  },
  components: { GoogleLogin },
  mounted() {
    if (this.check_cookie_value("Token") != -1) this.updatelogin(true);
  },
  data: () => ({
    params: {
      client_id:
        "566387838712-mkstlbr8qaa6md7c9d5lq5oe0n9uq2hs.apps.googleusercontent.com",
    },
  }),
};
</script>
