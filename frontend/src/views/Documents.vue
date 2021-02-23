<template>
  <div>
    <v-navigation-drawer permanent style="height: 100vh" dark>
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
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "Documents",
  components: {},
  data() {
    return {
      searchUni: "",
    };
  },
};
</script>
<style scoped>
</style>
