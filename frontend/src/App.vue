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
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",
  methods: {
    goTo(address) {
      this.$router.push(address);
    }
  },
  components: {},

  data: () => ({
    //
  })
};
</script>
